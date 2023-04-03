package io.github.mroncatto.itflow.domain.staff.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.ItflowApiApplication;
import io.github.mroncatto.itflow.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.staff.model.Occupation;
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
class OccupationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("Should create a Occupation and return code 201")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void save() throws Exception {
        final String occupationName = "Occupation test";
        RequestBuilder request = post(EndpointUrlConstant.occupation)
                .content(objectMapper.writeValueAsString(
                        Occupation.builder()
                                .active(true)
                                .name(occupationName)
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpectAll(
                status().isCreated(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("name", Is.is(occupationName))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Should update a Occupation and return code 200")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void update() throws Exception {
        final String occupationName = "Occupation test edited";
        RequestBuilder request = put(EndpointUrlConstant.occupation)
                .content(objectMapper.writeValueAsString(
                        Occupation.builder()
                                .id(2L)
                                .active(true)
                                .name(occupationName)
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("name", Is.is(occupationName))
        );
    }

    @Test
    @Order(3)
    @DisplayName("Should find all occupations and return not empty list and code 200")
    @WithMockUser(username = "admin")
    void findAll() throws Exception {
        RequestBuilder request = get(EndpointUrlConstant.occupation);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("$").exists()
        );
    }

    @Test
    @Order(4)
    @DisplayName("Should find all occupations and return page format and code 200")
    @WithMockUser(username = "admin")
    void findAllPagination() throws Exception {
        final String page = "/page/1";
        RequestBuilder request = get(EndpointUrlConstant.occupation + page);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("pageable.pageNumber", Is.is(1)));
    }

    @Test
    @Order(5)
    @DisplayName("Should find all occupations filtered by staff and return code 200")
    @WithMockUser(username = "admin")
    void findAllUsingByStaff() throws Exception {
        RequestBuilder request = get(EndpointUrlConstant.occupation + EndpointUrlConstant.filterStaff);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON)
        );
    }

    @Test
    @Order(6)
    @DisplayName("Should find a department by ID and return code 200")
    @WithMockUser(username = "admin")
    void findById() throws Exception {
        final String occupationName = "Occupation test edited";
        final String id = "/2";
        RequestBuilder request = get(EndpointUrlConstant.occupation + id);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("name", Is.is(occupationName))
        );
    }

    @Test
    @Order(7)
    @DisplayName("Should find and disable a occupation by ID and return code 200")
    @WithMockUser(username = "admin", authorities = "MANAGER")
    void deleteById() throws Exception {
        final String id = "/2";
        RequestBuilder request = delete(EndpointUrlConstant.occupation + id);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("active", Is.is(false))
        );
    }
}