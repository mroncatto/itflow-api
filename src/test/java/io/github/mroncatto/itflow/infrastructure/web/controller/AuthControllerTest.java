package io.github.mroncatto.itflow.infrastructure.web.controller;

import io.github.mroncatto.itflow.ItflowApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static io.github.mroncatto.itflow.application.config.constant.SecurityConstant.AUTHENTICATION_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ItflowApiApplication.class
)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnForbiddenAccess() throws Exception {
        RequestBuilder requestLogin = MockMvcRequestBuilders.post(AUTHENTICATION_URL)
                .content("")
                .param("username", "admin")
                .param("password", "123456")
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(requestLogin).andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn();
    }

    @Test
    void shouldReturnAccessToken() throws Exception {
        RequestBuilder requestLogin = MockMvcRequestBuilders.post(AUTHENTICATION_URL)
                .content("")
                .param("username", "admin")
                .param("password", "admin")
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(requestLogin)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("access_token").exists())
                .andReturn();
    }
}