package io.github.mroncatto.itflow.domain.user.resource;

import io.github.mroncatto.itflow.domain.user.model.User;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


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

    //TODO: Falta criar JSON BUILDERS PARA OS TESTES

    @Test
    @Order(3)
    @WithMockUser(username = "administrator")
    void findAll() throws Exception {
        RequestBuilder request = get(BASE_URL + "/user");
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "administrator")
    void testFindAll() throws Exception {
        RequestBuilder request = get(BASE_URL + "/user/page/1");
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(1)
    @WithMockUser(username = "administrator", authorities ="ADMIN")
    void save() throws Exception {
        RequestBuilder request = post(BASE_URL + "/user")
                .content("{" +
                        "    \"fullName\": \"SpringTests\"," +
                        "        \"email\": \"spring@test.com\"," +
                        "        \"username\": \"springtest\"," +
                        "        \"password\": \"123456\"," +
                        "        \"avatar\": \"\"," +
                        "        \"joinDate\": \"2022-06-12T20:46:12.769+00:00\"" +
                        "        }")
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "administrator", authorities ="ADMIN")
    void update() throws Exception {
        RequestBuilder request = put(BASE_URL + "/user/springtest")
                .content("{" +
                        "    \"fullName\": \"SpringTests updated\"," +
                        "        \"email\": \"spring@test.com\"," +
                        "        \"username\": \"springtest\"," +
                        "        \"password\": \"123456\"," +
                        "        \"avatar\": \"\"," +
                        "        \"joinDate\": \"2022-06-12T20:46:12.769+00:00\"" +
                        "        }")
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(5)
    @WithMockUser(username = "administrator", authorities ="ADMIN")
    void delete() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete(BASE_URL + "/user/springtest");
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }
}