package com.petpal.backend.controllerTest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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



}