package com.petpal.backend.controllerTest;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = true)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class IntegrationTests {
    @Autowired
    private MockMvc mockMvc;


    @Order(1)
    @ParameterizedTest
    @CsvSource({
            "John Doe, +1234567890, john.doe@example.com, caregiver1, asdfasdf, 1",
            "John Doe, +1234567891, john.doe@example.com, caregiver2, asdfasdf, 1",
            "Jahn Doe, +1234567892, jane.doe@example.com, user, asdfasdf, 0",
    })
    public void testRegisterUser(String name, String phone, String email, String username, String password, int isCaregiver) throws Exception {
        String jsonRequest = String.format("{\n" +
                "  \"name\": \"%s\",\n" +
                "  \"phone\": \"%s\",\n" +
                "  \"email\": \"%s\",\n" +
                "  \"username\": \"%s\",\n" +
                "  \"password\": \"%s\",\n" +
                "  \"isCaregiver\": %d\n" +
                "}", name, phone, email, username, password, isCaregiver);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User register successfully"));
    }


    @Order(2)
    @Test
    public void testRegisteredUserThenReturnConflict() throws Exception {
        String jsonRequest = "{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"phone\": \"+1234567890\",\n" +
                "  \"email\": \"john.doe@example.com\",\n" +
                "  \"username\": \"user\",\n" +
                "  \"password\": \"asdfasdf\",\n" +
                "  \"isCaregiver\": 0\n" +
                "}";
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Account already existed"));
    }

    @Order(3)
    @Test
    public void testRegisterUserWithNullThenReturnBadRequest() throws Exception {
        String jsonRequest = "{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"phone\": \"+1234567890\",\n" +
                "  \"email\": \"john.doe@example.com\",\n" +
                "  \"username\": \"userWithNoPassword\",\n" +
                "  \"isCaregiver\": 1\n" +
                "}";
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid or missing fields"));
    }

    private static String jwtToken;

    @Order(4)
    @Test
    public void testLogin() throws Exception {
        String jsonRequest = "{\"username\": \"user\", \"password\": \"asdfasdf\"}";
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andReturn();

        jwtToken = result.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(result.getResponse().getHeader(HttpHeaders.AUTHORIZATION));
        System.out.println(jwtToken);
    }

    @Order(5)
    @Test
    public void testValidateToken() throws Exception {
        System.out.println(jwtToken);
        mockMvc.perform(get("/api/auth/validate")
                        .param("token", jwtToken)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }


    @Order(6)
    @ParameterizedTest
    @CsvSource({
            "1, 50.1748119, 8.7324472",
            "2, 50.1855353, 8.7437073"
    })
    void testUpdateCaregiverProfile(Long caregiverId, double latitude, double longitude) throws Exception {
        String requestBody = String.format("{\n" +
                "    \"latitude\": %f,\n" +
                "    \"longitude\": %f\n" +
                "}", latitude, longitude);

        mockMvc.perform(patch("/api/users/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .param("username", "caregiver" + caregiverId))
                .andExpect(status().isOk());
    }

    @Order(7)
    @ParameterizedTest
    @CsvSource({
            "1, weekly, 'Monday,Tuesday,Wednesday,Thursday,Friday', 2024-06-01, 2024-06-30",
            "2, weekly, 'Monday,Tuesday,Wednesday,Thursday,Friday', 2024-06-14, 2024-06-30"
    })
    void testSetCaregiverAvailability(Long caregiverId, String frequency, String daysOfWeek, LocalDate startDate, LocalDate endDate) throws Exception {
        String jsonBody = String.format("{\n" +
                "  \"frequency\": \"%s\",\n" +
                "  \"daysOfWeek\": [%s],\n" +
                "  \"startDate\": \"%s\",\n" +
                "  \"endDate\": \"%s\"\n" +
                "}", frequency, "\"" + daysOfWeek.replace(",", "\",\"") + "\"", startDate, endDate);

        mockMvc.perform(post("/api/caregivers/" + caregiverId + "/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated());
    }

    @Order(8)
    @ParameterizedTest
    @CsvSource({
            "1, 'DOG,CAT,GUINEA_PIG'",
            "2, 'DOG,CAT,GUINEA_PIG'"
    })
    public void testUpdateCaregiverPetTypes(Long caregiverId, String petTypes) throws Exception {
        String[] typesArray = petTypes.split(",");
        String requestBody = Arrays.stream(typesArray)
                .map(String::trim)
                .map(type -> "\"" + type.trim() + "\"")
                .collect(Collectors.joining(",", "[", "]"));
        mockMvc.perform(patch("/api/caregivers/" + caregiverId + "/pet-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petTypes").isArray())
                .andExpect(jsonPath("$.petTypes", hasItems(typesArray)));
    }

    @Order(9)
    @ParameterizedTest
    @CsvSource({
            "1, 'PET_SITTING,PET_HOSTING'",
            "2, 'PET_HOSTING'"
    })
    void testUpdateCaregiverServiceTypes(Long caregiverId, String serviceTypes) throws Exception {
        String[] typesArray = serviceTypes.split(",");
        String requestBody = Arrays.stream(typesArray)
                .map(String::trim)
                .map(type -> "\"" + type.trim() + "\"")
                .collect(Collectors.joining(",", "[", "]"));
        mockMvc.perform(patch("/api/caregivers/" + caregiverId + "/service-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceTypes").isArray())
                .andExpect(jsonPath("$.serviceTypes", hasItems(typesArray)));
    }


    @Order(10)
    @Test
    public void testSearchCaregivers() throws Exception {
        mockMvc.perform(get("/api/caregivers/search")
                        .param("petTypes", "DOG,CAT")
                        .param("startDate", "2024-06-15")
                        .param("endDate", "2024-06-20")
                        .param("longitude", "8.7423401")
                        .param("latitude", "50.1863407")
                        .param("serviceType", "PET_HOSTING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(equalTo(2))))
                .andExpect(jsonPath("$[0].petTypes", hasItems("DOG", "CAT")))
                .andExpect(jsonPath("$[0].serviceTypes", hasItem("PET_HOSTING")));
    }

    @Order(11)
    @Test
    public void testGetUserProfile() throws Exception {
        mockMvc.perform(get("/api/users/profile/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Order(12)
    @Test
    public void testGetUserPetsEmpty() throws Exception {
        mockMvc.perform(get("/api/users/{username}/pets", "user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Order(13)
    @ParameterizedTest
    @CsvSource({
            "'GUINEA_PIG', 'Manh Thong', 4, 3",
            "'DOG', 'Buddy', 3, 3",
            "'CAT', 'Whiskers', 5, 3"
    })
    public void testRegisterPet(String petType, String petName, int petAge, int petowner_id) throws Exception {
        String jsonRequest = String.format("{\n" +
                "    \"petType\": \"%s\",\n" +
                "    \"petName\": \"%s\",\n" +
                "    \"petAge\": %d,\n" +
                "    \"petowner_id\": %d\n" +
                "}", petType, petName, petAge, petowner_id);
        mockMvc.perform(post("/api/pets/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(containsString("Pet's id:")));
    }

    @Order(14)
    @Test
    public void testUpdatePet() throws Exception {
        String jsonRequest = "{\n" +
                "    \"petType\": \"GUINEA_PIG\",\n" +
                "    \"petName\": \"Minh Thong\",\n" +
                "    \"petAge\": 1\n" +
                "}";
        mockMvc.perform(patch("/api/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petType").value("GUINEA_PIG"))
                .andExpect(jsonPath("$.petName").value("Minh Thong"))
                .andExpect(jsonPath("$.petAge").value(1));
    }

    @Order(15)
    @Test
    public void testDeletePet() throws Exception {
        mockMvc.perform(delete("/api/pets/3"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/{username}/pets", "user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Order(16)
    @Test
    public void testCreateContract() throws Exception {
        String jsonRequest = "{\n" +
                "    \"petIds\": [1, 2],\n" +
                "    \"serviceType\": \"PET_SITTING\",\n" +
                "    \"startDate\": \"2024-06-17\",\n" +
                "    \"endDate\": \"2024-06-18\"\n" +
                "}";
        mockMvc.perform(post("/api/contracts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .param("petOwnerId", "3") // Add petOwnerId parameter
                        .param("caregiverId", "1")) // Add caregiverId parameter
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CREATED"));
    }


    @Order(17)
    @Test
    public void testAcceptContract() throws Exception {
        Long contractId = 1L;
        mockMvc.perform(patch("/api/contracts/" + contractId + "/accept")
                        .param("caregiverId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ON_GOING"));

        Long caregiverId = 1L;
        mockMvc.perform(get("/api/caregivers/" + caregiverId + "/availability"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookedDateRanges[*].startDate", hasItem("2024-06-17")))
                .andExpect(jsonPath("$.bookedDateRanges[*].endDate", hasItem("2024-06-18")));
    }

    @Order(18)
    @Test
    public void testCompleteContract() throws Exception {
        Long contractId = 1L; // Assume this is the ID of the ongoing contract
        mockMvc.perform(patch("/api/contracts/" + contractId + "/return"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PET_RETURNED"));
    }

    @Order(19)
    @Test
    public void testConfirmPetReturn() throws Exception {
        Long contractId = 1L; // Assume this is the ID of the ongoing contract
        mockMvc.perform(patch("/api/contracts/" + contractId + "/confirm-return"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petReturnConfirmed").value(true));
    }

    @Order(20)
    @Test
    public void testCreateContractWithMissingParameters() throws Exception {
        String jsonRequest = "{\n" +
                "    \"petIds\": [1, 2],\n" +
                "    \"serviceType\": \"PET_SITTING\"\n" + // Missing startDate and endDate
                "}";
        mockMvc.perform(post("/api/contracts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .param("caregiverId", "1")
                        .param("petOwnerId", "3"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Required parameters are missing"));
    }

    @Order(20)
    @Test
    public void testCreateAndRejectContract() throws Exception {
        // Create a contract
        String jsonRequestCreate = "{\n" +
                "    \"petIds\": [1, 2],\n" +
                "    \"serviceType\": \"PET_SITTING\",\n" +
                "    \"startDate\": \"2024-06-10\",\n" +
                "    \"endDate\": \"2024-06-11\"\n" +
                "}";
        MvcResult result = mockMvc.perform(post("/api/contracts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestCreate)
                        .param("petOwnerId", "3")
                        .param("caregiverId", "1"))
                .andExpect(status().isCreated())
                .andReturn();

        Long contractId = Long.valueOf(JsonPath.read(result.getResponse().getContentAsString(), "$.contractId").toString());;

        // Reject the contract
        mockMvc.perform(patch("/api/contracts/" + contractId + "/reject")
                        .param("caregiverId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REJECTED"));
    }

    @Order(21)
    @Test
    public void testSendMessage() throws Exception {
        String jsonRequest = "{\n" +
                "    \"senderId\": 1,\n" +
                "    \"receiverId\": 2,\n" +
                "    \"message\": \"Hello everyone\"\n" +
                "}";

        mockMvc.perform(post("/api/chat/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Message sent successfully"));
    }


    @Order(22)
    @Test
    public void testGetMessages() throws Exception {
        Long senderId = 1L; // Assuming this is a valid sender ID
        Long receiverId = 2L; // Assuming this is a valid receiver ID

        mockMvc.perform(get("/api/chat/messages")
                        .param("senderId", senderId.toString())
                        .param("receiverId", receiverId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Assuming there is one message
                .andExpect(jsonPath("$[0].message").value("Hello everyone"));
    }

    @Order(23)
    @Test
    public void testUploadPetMainAvatar() throws Exception {
        MockMultipartFile file = new MockMultipartFile("avatar", "avatar.jpg", "image/jpeg", "test image content".getBytes());
        mockMvc.perform(multipart("/api/pets/1/avatar").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Avatar uploaded successfully"))
                .andExpect(jsonPath("$.petId").value(1));
    }

    @Order(24)
    @Test
    public void testUploadPetAdditionalImages() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "image1 content".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("images", "image2.jpg", "image/jpeg", "image2 content".getBytes());
        mockMvc.perform(multipart("/api/pets/1/additional-images").file(file1).file(file2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Additional images uploaded successfully"))
                .andExpect(jsonPath("$.petId").value(1));
    }

    @Order(25)
    @Test
    public void testUploadUserAvatar() throws Exception {
        MockMultipartFile avatarFile = new MockMultipartFile("avatar", "userAvatar.jpg", "image/jpeg", "avatar content".getBytes());
        mockMvc.perform(multipart("/api/users/profile/1/avatar").file(avatarFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Avatar uploaded successfully"))
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Order(26)
    @Test
    public void testGetPetMainAvatar() throws Exception {
        mockMvc.perform(get("/api/pets/1/avatar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }

    @Order(27)
    @Test
    public void testGetUserAvatar() throws Exception {
        mockMvc.perform(get("/api/users/profile/1/avatar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }
}