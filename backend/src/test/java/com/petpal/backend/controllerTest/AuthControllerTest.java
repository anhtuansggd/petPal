package com.petpal.backend.controllerTest;

import com.petpal.backend.controller.AuthController;
import com.petpal.backend.domain.User;
import com.petpal.backend.repository.AuthorityRepository;
import com.petpal.backend.repository.UserRepository;
import com.petpal.backend.service.CaregiverService;
import com.petpal.backend.service.UserService;
import com.petpal.backend.utility.CustomPasswordEncoder;
import com.petpal.backend.utility.JwtUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = true)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Order(1)
    @Test
    public void testRegisterCaregiver1() throws Exception {
        String jsonRequest = "{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"phone\": \"+1234567890\",\n" +
                "  \"email\": \"john.doe@example.com\",\n" +
                "  \"username\": \"caregiver1\",\n" +
                "  \"password\": \"asdfasdf\",\n" +
                "  \"isCaregiver\": 1\n" +
                "}";
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User register successfully"));


    }

    @Order(2)
    @Test
    public void testRegisterCaregiver2() throws Exception {
        String jsonRequest = "{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"phone\": \"+1234567890\",\n" +
                "  \"email\": \"john.doe@example.com\",\n" +
                "  \"username\": \"caregiver2\",\n" +
                "  \"password\": \"asdfasdf\",\n" +
                "  \"isCaregiver\": 1\n" +
                "}";
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User register successfully"));
    }



    @Order(3)
    @Test
    public void testRegisterUser() throws Exception {
        String jsonRequest = "{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"phone\": \"+1234567890\",\n" +
                "  \"email\": \"john.doe@example.com\",\n" +
                "  \"username\": \"user\",\n" +
                "  \"password\": \"asdfasdf\",\n" +
                "  \"isCaregiver\": 0\n" +
                "}";
        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User register successfully"))
                .andReturn();

    }

    @Order(4)
    @Test
    public void testRegisteredUserThenReturnConflit() throws Exception {
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

    @Order(5)
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

    @Order(6)
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

    @Order(7)
    @Test
    public void testValidateToken() throws Exception {
        System.out.println(jwtToken);
        mockMvc.perform(get("/api/auth/validate")
                        .param("token", jwtToken)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Order(8)
    @Test
    public void testUpdateCaregiver1Profile() throws Exception {
        String requestBody = "{\n" +
                "    \"latitude\": 50.1748119,\n" +
                "    \"longitude\": 8.7324472\n" +
                "}";
        mockMvc.perform(patch("/api/users/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .param("username", "caregiver1"))
                .andExpect(status().isOk());
    }

    @Order(9)
    @Test
    public void testUpdateCaregiver2Profile() throws Exception {
        String requestBody = "{\n" +
                "    \"latitude\": 50.1855353,\n" +
                "    \"longitude\": 8.7437073\n" +
                "}";
        mockMvc.perform(patch("/api/users/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .param("username", "caregiver2"))
                .andExpect(status().isOk());
    }

    @Order(10)
    @Test
    public void testSetCaregiver1Availability() throws Exception {
        String jsonBody = "{\n" +
                "  \"frequency\": \"weekly\",\n" +
                "  \"daysOfWeek\": [\"Monday\", \"Tuesday\", \"Wednesday\", \"Thursday\", \"Friday\"],\n" +
                "  \"interval\": 1,\n" +
                "  \"startDate\": \"2024-06-01\",\n" +
                "  \"endDate\": \"2024-06-30\"\n" +
                "}";

        mockMvc.perform(post("/api/caregivers/1/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated());
    }

    @Order(11)
    @Test
    public void testSetCaregiver2Availability() throws Exception {
        String jsonBody = "{\n" +
                "  \"frequency\": \"weekly\",\n" +
                "  \"daysOfWeek\": [\"Monday\", \"Tuesday\", \"Wednesday\", \"Thursday\", \"Friday\"],\n" +
                "  \"interval\": 1,\n" +
                "  \"startDate\": \"2024-06-14\",\n" +
                "  \"endDate\": \"2024-06-30\"\n" +
                "}";

        mockMvc.perform(post("/api/caregivers/2/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated());
    }

    @Order(12)
    @Test
    public void updateCaregiver1PetTypesTest() throws Exception {
        Long caregiverId = 1L;
        String requestBody = "[\"DOG\", \"CAT\", \"GUINEA_PIG\"]";

        mockMvc.perform(patch("/api/caregivers/" + caregiverId + "/pet-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petTypes").isArray())
                .andExpect(jsonPath("$.petTypes[0]").value("DOG"))
                .andExpect(jsonPath("$.petTypes[1]").value("CAT"))
                .andExpect(jsonPath("$.petTypes[2]").value("GUINEA_PIG"));
    }

    @Order(13)
    @Test
    public void updateCaregiver2PetTypesTest() throws Exception {
        Long caregiverId = 2L;
        String requestBody = "[\"DOG\", \"CAT\", \"GUINEA_PIG\"]";

        mockMvc.perform(patch("/api/caregivers/" + caregiverId + "/pet-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petTypes").isArray())
                .andExpect(jsonPath("$.petTypes[0]").value("DOG"))
                .andExpect(jsonPath("$.petTypes[1]").value("CAT"))
                .andExpect(jsonPath("$.petTypes[2]").value("GUINEA_PIG"));
    }

    @Order(14)
    @Test
    public void searchCaregiversTest() throws Exception {
        mockMvc.perform(get("/api/caregivers/search")
                        .param("petTypes", "DOG,CAT")
                        .param("startDate", "2024-06-15")
                        .param("endDate", "2024-06-20")
                        .param("longitude", "8.7423401")
                        .param("latitude", "50.1863407"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(equalTo(2)))) // Check if the response array is not empty
                .andExpect(jsonPath("$[0].petTypes", hasItems("DOG", "CAT"))); // Example check for pet types
    }



}