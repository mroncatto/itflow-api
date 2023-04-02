package io.github.mroncatto.itflow.domain.user.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.ItflowApiApplication;
import io.github.mroncatto.itflow.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.user.model.Role;
import io.github.mroncatto.itflow.domain.user.model.User;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ItflowApiApplication.class
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(profiles = "test")
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities ="ADMIN")
    void UserShouldBeCreatedAndReturn201() throws Exception {
        RequestBuilder request = post(EndpointUrlConstant.user)
                .content(objectMapper.writeValueAsString(
                        User.builder()
                        .fullName("itflow")
                        .username("itflow")
                        .avatar("")
                        .email("itflow@test.com")
                        .active(true)
                        .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("username", Is.is("itflow")));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities ="ADMIN")
    void shouldChangeTheFullName() throws Exception {
        final String username = "/itflow";
        RequestBuilder request = put(EndpointUrlConstant.user + username)
                .content(objectMapper.writeValueAsString(
                        User.builder()
                                .fullName("Itflow updated")
                                .username("itflow")
                                .avatar("")
                                .email("itflow@test.com")
                                .active(true)
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("fullName", Is.is("Itflow updated")));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin")
    void shouldReturnPagination() throws Exception {
        final String page = "/page/1";
        RequestBuilder request = get(EndpointUrlConstant.user + page);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("pageable.pageNumber", Is.is(1)));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin")
    void shouldReturnForbiddenAccess() throws Exception {
        final String userRole = "/itflow/role";
        List<Role> roles = new ArrayList<>();
        RequestBuilder request = put(EndpointUrlConstant.user + userRole)
                .content(objectMapper.writeValueAsString(roles))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpect(status().isForbidden()).andReturn();
    }
}