package io.github.mroncatto.itflow.domain.computer.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.ItflowApiApplication;
import io.github.mroncatto.itflow.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.computer.model.ComputerMemory;
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
class ComputerMemoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("Should create a computer memory and return code 201")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void save() throws Exception {
        final String brandName = "Memory brand";
        RequestBuilder request = post(EndpointUrlConstant.computerMemory)
                .content(objectMapper.writeValueAsString(
                        ComputerMemory.builder()
                                .active(false)
                                .brandName(brandName)
                                .size("2000")
                                .frequency("2333")
                                .type("DDR3")
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpectAll(
                status().isCreated(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("brandName", Is.is(brandName))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Should update a computer memory and return code 200")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void update() throws Exception {
        final String brandName = "Memory brand edit";
        RequestBuilder request = put(EndpointUrlConstant.computerMemory)
                .content(objectMapper.writeValueAsString(
                        ComputerMemory.builder()
                                .id(1L)
                                .active(false)
                                .brandName(brandName)
                                .size("2000")
                                .frequency("2333")
                                .type("DDR3")
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("brandName", Is.is(brandName))
        );
    }

    @Test
    @Order(3)
    @DisplayName("Should find all computer memories and return not empty list and code 200")
    @WithMockUser(username = "admin")
    void findAll() throws Exception {
        RequestBuilder request = get(EndpointUrlConstant.computerMemory);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("$").exists()
        );
    }

    @Test
    @Order(4)
    @DisplayName("Should find all computer memories and return page format and code 200")
    @WithMockUser(username = "admin")
    void findAllPagination() throws Exception {
        final String page = "/page/1";
        RequestBuilder request = get(EndpointUrlConstant.computerMemory + page);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("pageable.pageNumber", Is.is(1)));
    }

    @Test
    @Order(5)
    @DisplayName("Should find a computer memories by ID and return code 200")
    @WithMockUser(username = "admin")
    void findById() throws Exception {
        final String brandName = "Memory brand edit";
        final String id = "/1";
        RequestBuilder request = get(EndpointUrlConstant.computerMemory + id);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("brandName", Is.is(brandName))
        );
    }

    @Test
    @Order(6)
    @DisplayName("Should find and disable a computer memories by ID and return code 200")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void deleteById() throws Exception {
        final String id = "/1";
        RequestBuilder request = delete(EndpointUrlConstant.computerMemory + id);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("active", Is.is(false))
        );
    }
}