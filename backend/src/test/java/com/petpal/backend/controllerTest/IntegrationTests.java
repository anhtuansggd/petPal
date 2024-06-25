package com.petpal.backend.controllerTest;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = true)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@AutoConfigureRestDocs
public class IntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;


//  NOTE!:
// This test suite contains integration tests for various API endpoints.
// Each test is capable of running locally for development and debugging purposes.
// Documentation generation is disabled by default for faster execution during development.
// To generate the API documentation:
// 1. Uncomment all the `.andDo(document(...))` lines in each test method.
// 2. Run the tests with the Maven command: `./mvnw test`
// This will produce documentation in the 'target/generated-snippets' directory.


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
//                .andDo(document("register-user",
//                        requestFields(
//                                fieldWithPath("name").description("The name of the user"),
//                                fieldWithPath("phone").description("The phone number of the user"),
//                                fieldWithPath("email").description("The email of the user"),
//                                fieldWithPath("username").description("The username of the user"),
//                                fieldWithPath("password").description("The password of the user"),
//                                fieldWithPath("isCaregiver").description("Indicates if the user is a caregiver (1) or not (0)")
//                        ),
//                        responseFields(
//                                fieldWithPath("message").description("The success message")
//                        )
//                ));
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
//                .andDo(document("register-user-conflict",
//                        responseFields(
//                                fieldWithPath("message").description("The error message indicating account already exists")
//                        )
//                ));
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
//                .andDo(document("register-user-bad-request",
//                        responseFields(
//                                fieldWithPath("message").description("The error message indicating invalid or missing fields")
//                        )
//                ));
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

        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(jsonRequest));
//                .andDo(document("login-user",
//                        requestFields(
//                                fieldWithPath("username").description("The username of the user"),
//                                fieldWithPath("password").description("The password of the user")
//                        ),
//                        responseFields(
//                                fieldWithPath("username").description("The username of the logged-in user"),
//                                fieldWithPath("userId").description("The ID of the user"),
//                                fieldWithPath("password").optional().description("The password of the user"),
//                                fieldWithPath("name").description("The name of the user"),
//                                fieldWithPath("email").description("The email address of the user"),
//                                fieldWithPath("phone").description("The phone number of the user"),
//                                fieldWithPath("location").optional().description("The location of the user"),
//                                fieldWithPath("isCaregiver").description("Indicates if the user is a caregiver"),
//                                fieldWithPath("authorities").description("Roles assigned to the user"),
//                                fieldWithPath("enabled").description("Status of the user's account"),
//                                fieldWithPath("accountNonExpired").description("Indicates if the account is expired"),
//                                fieldWithPath("accountNonLocked").description("Indicates if the account is locked"),
//                                fieldWithPath("credentialsNonExpired").description("Indicates if the credentials are expired"),
//                                fieldWithPath("token").optional().type(JsonFieldType.STRING).description("Authentication token")
//                        )
//                ));
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
//                .andDo(document("validate-token",
//                        responseFields(
//                                fieldWithPath("$").type(JsonFieldType.BOOLEAN).description("Validation result of the token")
//                        )
//                ));
    }

    @Order(6)
    @ParameterizedTest
    @CsvSource({
            "1, 50.1748119, 8.7324472",
            "2, 50.1855353, 8.7437073"
    })
    void testUpdateCaregiverProfile(Long caregiverId, double latitude, double longitude) throws Exception {
        String requestBody = String.format("{\n" +
                "    \"latitude\": 50.174,\n" +
                "    \"longitude\": 8.74\n" +
                "}", latitude, longitude);

        mockMvc.perform(patch("/api/users/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .param("username", "caregiver" + caregiverId))
                .andExpect(status().isOk());
//                .andDo(document("update-caregiver-profile",
//                        requestFields(
//                                fieldWithPath("latitude").description("Latitude of the caregiver's location"),
//                                fieldWithPath("longitude").description("Longitude of the caregiver's location")
//                        )
//                ));
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
//                .andDo(document("set-caregiver-availability",
//                        requestFields(
//                                fieldWithPath("frequency").description("Frequency of availability"),
//                                fieldWithPath("daysOfWeek").description("Days of the week available"),
//                                fieldWithPath("startDate").description("Start date of availability"),
//                                fieldWithPath("endDate").description("End date of availability")
//                        )
//                ));
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
//                .andDo(document("update-caregiver-pet-types",
//                        requestFields(
//                                fieldWithPath("petTypes").description("Array of pet types the caregiver can handle")
//                        ),
//                        responseFields(
//                                fieldWithPath("petTypes").description("Updated array of pet types")
//
//                        )
//                ));
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
//                .andDo(document("update-caregiver-service-types",
//                        requestFields(
//                                fieldWithPath("serviceTypes").description("Array of service types the caregiver can provide")
//                        ),
//                        responseFields(
//                                fieldWithPath("serviceTypes").description("Updated array of service types")
//                        )
//                ));
    }

    @Order(10)
    @Test
    public void testSearchCaregivers() throws Exception {
        mockMvc.perform(get("/api/caregivers/search")
            .queryParam("petTypes", "DOG,CAT")
            .queryParam("startDate", "2024-06-15")
            .queryParam("endDate", "2024-06-20")
            .queryParam("longitude", "8.7423401")
            .queryParam("latitude", "50.1863407")
            .queryParam("serviceType", "PET_HOSTING"));
//            .andDo(document("search-caregivers",
//                    queryParameters(
//                    parameterWithName("petTypes").description("Types of pets the caregiver can handle"),
//                    parameterWithName("startDate").description("Start date for service"),
//                    parameterWithName("endDate").description("End date for service"),
//                    parameterWithName("longitude").description("Longitude of the service location"),
//                    parameterWithName("latitude").description("Latitude of the service location"),
//                    parameterWithName("serviceType").description("Type of service required")
//            )));


//      PREVIOUS TEST CASE:

        // mockMvc.perform(get("/api/caregivers/search")
        //                 .param("petTypes", "DOG,CAT")
        //                 .param("startDate", "2024-06-15")
        //                 .param("endDate", "2024-06-20")
        //                 .param("longitude", "8.7423401")
        //                 .param("latitude", "50.1863407")
        //                 .param("serviceType", "PET_HOSTING"))
        //         .andExpect(status().isOk())
        //         .andExpect(jsonPath("$", hasSize(equalTo(2))))
        //         .andExpect(jsonPath("$[0].petTypes", hasItems("DOG", "CAT")))
        //         .andExpect(jsonPath("$[0].serviceTypes", hasItem("PET_HOSTING")))
        //         .andDo(result -> System.out.println("Request parameters: " + result.getRequest().getParameterMap()))
        //         .andDo(document("search-caregivers",
        //                 requestParameters(
        //                         parameterWithName("petTypes").description("Types of pets the caregiver can handle"),
        //                         parameterWithName("startDate").description("Start date for service"),
        //                         parameterWithName("endDate").description("End date for service"),
        //                         parameterWithName("longitude").description("Longitude of the service location"),
        //                         parameterWithName("latitude").description("Latitude of the service location"),
        //                         parameterWithName("serviceType").description("Type of service required")
        //                 ),
        //                 responseFields(
        //                         fieldWithPath("$").description("List of caregivers matching the criteria")
        //                 )
        //         ));
    }

    @Order(11)
    @Test
    public void testGetUserProfile() throws Exception {
        mockMvc.perform(get("/api/users/profile/{username}", "user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andDo(document("get-user-profile",
                        pathParameters(
                                parameterWithName("username").description("The username of the user")
                        ),
                        responseFields(
                                fieldWithPath("userId").description("The ID of the user"),
                                fieldWithPath("username").description("The username of the user"),
                                fieldWithPath("password").optional().description("The password of the user"),
                                fieldWithPath("name").description("The name of the user"),
                                fieldWithPath("email").description("The email address of the user"),
                                fieldWithPath("phone").description("The phone number of the user"),
                                fieldWithPath("location").optional().description("The location of the user"),
                                fieldWithPath("isCaregiver").description("Indicates if the user is a caregiver"),
                                fieldWithPath("authorities").description("Roles assigned to the user"),
                                fieldWithPath("enabled").description("Status of the user's account"),
                                fieldWithPath("accountNonExpired").description("Indicates if the account is expired"),
                                fieldWithPath("accountNonLocked").description("Indicates if the account is locked"),
                                fieldWithPath("credentialsNonExpired").description("Indicates if the credentials are expired")

                        )

                ));
    }

    @Order(12)
    @Test
    public void testGetUserPetsEmpty() throws Exception {
        mockMvc.perform(get("/api/users/{username}/pets", "user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
//                .andDo(document("get-user-pets-empty",
//                        pathParameters(
//                                parameterWithName("username").description("The username of the user")
//                        ),
//                        responseFields(
//                                fieldWithPath("$").description("List of pets owned by the user, expected to be empty")
//                        )
//                ));
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
//                .andDo(document("register-pet",
//                        requestFields(
//                                fieldWithPath("petType").description("The type of the pet"),
//                                fieldWithPath("petName").description("The name of the pet"),
//                                fieldWithPath("petAge").description("The age of the pet"),
//                                fieldWithPath("petowner_id").description("The ID of the pet owner")
//                        ),
//                        responseFields(
//                                fieldWithPath("$.message").description("Confirmation message with the pet's ID"),
//                                fieldWithPath("$.petId").description("The ID of the pet"),
//                                fieldWithPath("$.petType").description("The type of the pet"),
//                                fieldWithPath("$.petName").description("The name of the pet"),
//                                fieldWithPath("$.petAge").description("The age of the pet")
//                        )
//                   ));
    }

    @Order(14)
    @Test
    public void testUpdatePet() throws Exception {
        String jsonRequest = "{\n" +
                "    \"petType\": \"GUINEA_PIG\",\n" +
                "    \"petName\": \"Shiba_Inu\",\n" +
                "    \"petAge\": 1\n" +
                "}";
        mockMvc.perform(patch("/api/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petType").value("GUINEA_PIG"))
                .andExpect(jsonPath("$.petName").value("Shiba_Inu"))
                .andExpect(jsonPath("$.petAge").value(1));
//                .andDo(document("update-pet",
//                        pathParameters(
//                                parameterWithName("petId").description("The ID of the pet to update")
//                        ),
//                        requestFields(
//                                fieldWithPath("petType").description("The type of the pet"),
//                                fieldWithPath("petName").description("The name of the pet"),
//                                fieldWithPath("petAge").description("The age of the pet")
//                        ),
//                        responseFields(
//                                fieldWithPath("$.petType").description("Updated type of the pet"),
//                                fieldWithPath("$.petName").description("Updated name of the pet"),
//                                fieldWithPath("$.petAge").description("Updated age of the pet")
//                        )
//                ));
    }

    @Order(15)
    @Test
    public void testDeletePet() throws Exception {
        mockMvc.perform(delete("/api/pets/3"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/{username}/pets", "user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
//                .andDo(document("delete-pet",
//                        pathParameters(
//                                parameterWithName("petId").description("The ID of the pet to delete")
//                        )
//                ));
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
//                .andDo(document("create-contract",
//                        requestFields(
//                                fieldWithPath("petIds").description("List of pet IDs involved in the contract"),
//                                fieldWithPath("serviceType").description("Type of service provided"),
//                                fieldWithPath("startDate").description("Start date of the service"),
//                                fieldWithPath("endDate").description("End date of the service")
//                        ),
//                        responseFields(
//                                fieldWithPath("status").description("Status of the created contract")
//                        )
//                ));
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
//                .andDo(document("accept-contract",
//                        responseFields(
//                                fieldWithPath("status").description("Current status of the contract"),
//                                fieldWithPath("bookedDateRanges[*].startDate").description("List of start dates for booked services"),
//                                fieldWithPath("bookedDateRanges[*].endDate").description("List of end dates for booked services")
//                        )
//                ));
    }

    @Order(18)
    @Test
    public void testCompleteContract() throws Exception {
        Long contractId = 1L; // Assume this is the ID of the ongoing contract
        mockMvc.perform(patch("/api/contracts/" + contractId + "/return"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PET_RETURNED"));
//                .andDo(document("complete-contract",
//                        responseFields(
//                                fieldWithPath("status").description("Status of the contract after completion")
//                        )
//                ));
    }

    @Order(19)
    @Test
    public void testConfirmPetReturn() throws Exception {
        Long contractId = 1L; // Assume this is the ID of the ongoing contract
        mockMvc.perform(patch("/api/contracts/" + contractId + "/confirm-return"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petReturnConfirmed").value(true));
//                .andDo(document("confirm-pet-return",
//                        responseFields(
//                                fieldWithPath("petReturnConfirmed").description("Confirmation status of the pet's return")
//                        )
//                ));
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
//                .andDo(document("create-contract-with-missing-parameters",
//                        responseFields(
//                                fieldWithPath("message").description("Error message for missing required parameters")
//                        )
//                ));
    }

    @Order(21)
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
//                .andDo(document("reject-contract",
//                    responseFields(
//                            fieldWithPath("status").description("Current status of the contract after rejection")
//                    )
//                ));
    }

    @Order(22)
    @Test
    public void testSendMessage() throws Exception {
        String jsonRequest1 = "{\n" +
                "    \"senderId\": 1,\n" +
                "    \"receiverId\": 2,\n" +
                "    \"message\": \"Hello to receiver 2\"\n" +
                "}";

        String jsonRequest2 = "{\n" +
                "    \"senderId\": 1,\n" +
                "    \"receiverId\": 3,\n" +
                "    \"message\": \"Hello to receiver 3\"\n" +
                "}";

        String jsonRequest3 = "{\n" +
                "    \"senderId\": 1,\n" +
                "    \"receiverId\": 2,\n" +
                "    \"message\": \"Another message to receiver 2\"\n" +
                "}";

        mockMvc.perform(post("/api/chat/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Message sent successfully"));

        mockMvc.perform(post("/api/chat/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Message sent successfully"));

        mockMvc.perform(post("/api/chat/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Message sent successfully"))
                .andDo(document("send-message",
                        requestFields(
                                fieldWithPath("senderId").description("The ID of the user sending the message"),
                                fieldWithPath("receiverId").description("The ID of the user receiving the message"),
                                fieldWithPath("message").description("The content of the message")
                        ),
                        responseFields(
                                fieldWithPath("$").description("Confirmation message upon successful sending")
                        )));
    }

    @Order(23)
    @Test
    public void testGetMessages() throws Exception {
        Long senderId = 1L; // Assuming this is a valid sender ID
        Long receiverId2 = 2L; // Assuming this is a valid receiver ID
        Long receiverId3 = 3L; // Assuming this is a valid receiver ID

        mockMvc.perform(get("/api/chat/messages")
                        .param("senderId", senderId.toString())
                        .param("receiverId", receiverId2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Assuming there are two messages to receiver 2
                .andExpect(jsonPath("$[0].message").value("Hello to receiver 2"))
                .andExpect(jsonPath("$[1].message").value("Another message to receiver 2"));

        mockMvc.perform(get("/api/chat/messages")
                        .param("senderId", senderId.toString())
                        .param("receiverId", receiverId3.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Assuming there is one message to receiver 3
                .andExpect(jsonPath("$[0].message").value("Hello to receiver 3"));
//                .andDo(document("get-messages",
//                    responseFields(
//                            fieldWithPath("$").description("List of messages exchanged between users")
//                    )
//                ));
    }



    @Order(24)
    @Test
    public void testGetContacts() throws Exception {
        Long senderId = 1L; // Assuming this is a valid sender ID

        MvcResult result = mockMvc.perform(get("/api/chat/contacts/{senderId}", senderId))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get("/api/chat/contacts/{senderId}", senderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].userId").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[1].userId").exists())
                .andExpect(jsonPath("$[1].name").exists())
                .andDo(document("get-contacts",
                        pathParameters(
                                parameterWithName("senderId").description("The ID of the user whose contacts are being retrieved")
                        ),
                        responseFields(
                                fieldWithPath("$").description("List of users who have had conversations with the sender")
                        )));
    }

    @Order(25)
    @Test
    public void testUploadPetMainAvatar() throws Exception {
        MockMultipartFile file = new MockMultipartFile("avatar", "avatar.jpg", "image/jpeg", "test image content".getBytes());
        mockMvc.perform(multipart("/api/pets/1/avatar").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Avatar uploaded successfully"))
                .andExpect(jsonPath("$.petId").value(1));
//                .andDo(document("upload-pet-main-avatar",
//                    responseFields(
//                            fieldWithPath("message").description("Success message upon uploading"),
//                            fieldWithPath("petId").description("ID of the pet whose avatar was uploaded")
//                    )
//                ));
    }

    @Order(26)
    @Test
    public void testUploadPetAdditionalImages() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "image1 content".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("images", "image2.jpg", "image/jpeg", "image2 content".getBytes());
        mockMvc.perform(multipart("/api/pets/1/additional-images").file(file1).file(file2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Additional images uploaded successfully"))
                .andExpect(jsonPath("$.petId").value(1));
//                .andDo(document("upload-pet-additional-images",
//                    responseFields(
//                            fieldWithPath("message").description("Success message upon uploading additional images"),
//                            fieldWithPath("petId").description("ID of the pet whose additional images were uploaded")
//                    )
//                ));
    }

    @Order(27)
    @Test
    public void testUploadUserAvatar() throws Exception {
        MockMultipartFile avatarFile = new MockMultipartFile("avatar", "userAvatar.jpg", "image/jpeg", "avatar content".getBytes());
        mockMvc.perform(multipart("/api/users/profile/1/avatar").file(avatarFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Avatar uploaded successfully"))
                .andExpect(jsonPath("$.userId").value(1));
//                .andDo(document("upload-user-avatar",
//                    responseFields(
//                            fieldWithPath("message").description("Success message upon uploading"),
//                            fieldWithPath("userId").description("ID of the user whose avatar was uploaded")
//                    )
//                ));
    }

    @Order(28)
    @Test
    public void testGetPetMainAvatar() throws Exception {
        mockMvc.perform(get("/api/pets/1/avatar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
//                .andDo(document("get-pet-main-avatar",
//                    responseFields(
//                            fieldWithPath("$").description("Content of the pet's main avatar image")
//                    )
//                ));
    }

    @Order(29)
    @Test
    public void testGetUserAvatar() throws Exception {
        mockMvc.perform(get("/api/users/profile/1/avatar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
//                .andDo(document("get-user-avatar",
//                    responseFields(
//                            fieldWithPath("$").description("Content of the user's avatar image")
//                    )
//                ));
    }

    @Order(30)
    @Test
    public void testGetPetAdditionalImages() throws Exception {
        Long petId = 1L; // Assuming this is a valid pet ID with images
        mockMvc.perform(get("/api/pets/" + petId + "/additional-images"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Assuming there are 2 images
                .andExpect(jsonPath("$[0]", isA(String.class))); // Check if the response is a list of Base64 strings
//                .andDo(document("get-pet-additional-images",
//                    responseFields(
//                            fieldWithPath("$").description("List of additional images for the pet")
//                    )
//                ));
        }
}