package com.klu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class JwtAuthIntegrationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = webAppContextSetup(this.context)
            .apply(springSecurity())
            .build();
    }

    private String loginAndGetToken(String username, String password) throws Exception {
        String body = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
    MvcResult result = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andReturn();

        return extractToken(result.getResponse().getContentAsString());
    }

    private String extractToken(String responseBody) {
        String marker = "\"token\":\"";
        int start = responseBody.indexOf(marker);
        if (start < 0) {
            throw new IllegalStateException("Token not found in response: " + responseBody);
        }

        int tokenStart = start + marker.length();
        int tokenEnd = responseBody.indexOf('"', tokenStart);
        if (tokenEnd < 0) {
            throw new IllegalStateException("Malformed token field in response: " + responseBody);
        }

        return responseBody.substring(tokenStart, tokenEnd);
    }

    @Test
    void validLoginShouldReturnToken() throws Exception {
        String body = "{\"username\":\"admin\",\"password\":\"admin123\"}";

    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk());
    }

    @Test
    void invalidLoginShouldReturnUnauthorized() throws Exception {
        String body = "{\"username\":\"admin\",\"password\":\"wrong\"}";

    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isUnauthorized());
    }

    @Test
    void employeeProfileWithoutTokenShouldFail() throws Exception {
    mockMvc.perform(get("/employee/profile"))
        .andExpect(status().isForbidden());
    }

    @Test
    void employeeProfileWithValidEmployeeTokenShouldPass() throws Exception {
        String token = loginAndGetToken("employee", "emp123");

    mockMvc.perform(get("/employee/profile")
            .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
    }

    @Test
    void adminEndpointsShouldRejectEmployeeRole() throws Exception {
        String token = loginAndGetToken("employee", "emp123");

    mockMvc.perform(post("/admin/add")
            .param("employeeName", "john")
            .header("Authorization", "Bearer " + token))
        .andExpect(status().isForbidden());
    }

    @Test
    void adminEndpointsShouldAllowAdminRole() throws Exception {
        String token = loginAndGetToken("admin", "admin123");

    mockMvc.perform(post("/admin/add")
            .param("employeeName", "john")
            .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());

    mockMvc.perform(delete("/admin/delete")
            .param("employeeName", "john")
            .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
    }

    @Test
    void invalidTokenShouldFail() throws Exception {
    mockMvc.perform(get("/employee/profile")
            .header("Authorization", "Bearer invalid.token.value"))
        .andExpect(status().isForbidden());
    }
}
