package io.github.mroncatto.itflow.domain.computer.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.ItflowApiApplication;
import io.github.mroncatto.itflow.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.computer.model.ComputerCategory;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.http.MediaType.APPLICATION_JSON;
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
class ComputerCategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("Should create a computer category and return code 201")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void save() throws Exception {
        final String nameCategory = "Category test";
        RequestBuilder request = post(EndpointUrlConstant.computerCategory)
                .content(objectMapper.writeValueAsString(
                        ComputerCategory.builder()
                                .active(true)
                                .name(nameCategory)
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpectAll(
                status().isCreated(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("name", Is.is(nameCategory))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Should update a computer category and return code 200")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void update() throws Exception {
        final String nameCategory = "Category test edited";
        RequestBuilder request = put(EndpointUrlConstant.computerCategory)
                .content(objectMapper.writeValueAsString(
                        ComputerCategory.builder()
                                .id(1L)
                                .active(true)
                                .name(nameCategory)
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("name", Is.is(nameCategory))
        );
    }

    @Test
    @Order(3)
    @DisplayName("Should find all computer categories and return not empty list and code 200")
    @WithMockUser(username = "admin")
    void findAll() throws Exception {
        RequestBuilder request = get(EndpointUrlConstant.computerCategory);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("$").exists()
        );
    }

    @Test
    @Order(4)
    @DisplayName("Should find all computer categories and return page format and code 200")
    @WithMockUser(username = "admin")
    void findAllPagination() throws Exception {
        final String page = "/page/1";
        RequestBuilder request = get(EndpointUrlConstant.computerCategory + page);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("pageable.pageNumber", Is.is(1)));
    }

    @Test
    @Order(5)
    @DisplayName("Should find a computer category by ID and return code 200")
    @WithMockUser(username = "admin")
    void findById() throws Exception {
        final String nameCategory = "Category test edited";
        final String id = "/1";
        RequestBuilder request = get(EndpointUrlConstant.computerCategory + id);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("name", Is.is(nameCategory))
        );
    }

    @Test
    @Order(7)
    @DisplayName("Should find and disable a computer category by ID and return code 200")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void deleteById() throws Exception {
        final String id = "/1";
        RequestBuilder request = delete(EndpointUrlConstant.computerCategory + id);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("active", Is.is(false))
        );
    }
}