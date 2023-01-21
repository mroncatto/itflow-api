package io.github.mroncatto.itflow.domain.user.resource;

import io.github.mroncatto.itflow.ItflowApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static io.github.mroncatto.itflow.config.constant.SecurityConstant.AUTHENTICATION_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ItflowApiApplication.class
)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnForbiddenAccess() throws Exception {
        RequestBuilder requestLogin = post(AUTHENTICATION_URL)
                .content("")
                .param("username", "admin")
                .param("password", "admin")
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(requestLogin).andExpect(status().isUnauthorized()).andReturn();
    }
}