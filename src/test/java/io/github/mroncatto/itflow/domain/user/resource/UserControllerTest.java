package io.github.mroncatto.itflow.domain.user.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.domain.user.model.Role;
import io.github.mroncatto.itflow.domain.user.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static io.github.mroncatto.itflow.config.constant.SecurityConstant.BASE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
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
    void save() throws Exception {
        RequestBuilder request = post(BASE_URL + "/user")
                .content(objectMapper.writeValueAsString(
                        User.builder()
                        .fullName("SpringTests")
                        .username("springtest")
                        .avatar("")
                        .email("spring@test.com")
                        .active(true)
                        .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin")
    void testFindAll() throws Exception {
        RequestBuilder request = get(BASE_URL + "/user/page/1");
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin")
    void findAll() throws Exception {
        RequestBuilder request = get(BASE_URL + "/user");
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", authorities ="ADMIN")
    void findAllRoles() throws Exception {
        RequestBuilder request = get(BASE_URL + "/user/role");
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities ="ADMIN")
    void update() throws Exception {
        RequestBuilder request = put(BASE_URL + "/user/springtest")
                .content(objectMapper.writeValueAsString(
                                User.builder()
                                        .fullName("SpringTests updated")
                                        .username("springtest")
                                        .avatar("")
                                        .email("spring@test.com")
                                        .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(6)
    @WithMockUser(username = "admin", authorities ="ADMIN")
    void updateUserRoles() throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().id(1L).role("ADMIN").build());
        roles.add(Role.builder().id(2L).role("MANAGER").build());
        RequestBuilder request = put(BASE_URL + "/user/springtest/role")
                .content(objectMapper.writeValueAsString(roles))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(7)
    @WithMockUser(username = "springtest")
    void updateProfile() throws Exception {
        RequestBuilder request = put(BASE_URL + "/user/profile")
                .content(objectMapper.writeValueAsString(
                        User.builder()
                                .fullName("SpringTests updated profile")
                                .email("spring@test.com")
                                .avatar("")
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(8)
    @WithMockUser(username = "admin", authorities ="ADMIN")
    void delete() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete(BASE_URL + "/user/springtest");
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }
}