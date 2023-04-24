package io.github.mroncatto.itflow.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.ItflowApiApplication;
import io.github.mroncatto.itflow.application.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ItflowApiApplication.class
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(profiles = "test")
class ComputerStorageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("Should create a computer storage and return code 201")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void save() throws Exception {
        final String brandName = "Storage brand";
        RequestBuilder request = MockMvcRequestBuilders.post(EndpointUrlConstant.computerStorage)
                .content(objectMapper.writeValueAsString(
                        ComputerStorage.builder()
                                .active(true)
                                .brandName(brandName)
                                .type("test")
                                .transferRate("test")
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("brandName", Is.is(brandName))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Should update a computer storage and return code 200")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void update() throws Exception {
        final String brandName = "Storage brand edited";
        RequestBuilder request = MockMvcRequestBuilders.put(EndpointUrlConstant.computerStorage)
                .content(objectMapper.writeValueAsString(
                        ComputerStorage.builder()
                                .id(1L)
                                .active(true)
                                .brandName(brandName)
                                .type("test")
                                .transferRate("test")
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("brandName", Is.is(brandName))
        );
    }

    @Test
    @Order(3)
    @DisplayName("Should find all computer storages and return not empty list and code 200")
    @WithMockUser(username = "admin")
    void findAll() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get(EndpointUrlConstant.computerStorage);
        mvc.perform(request).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("$").exists()
        );
    }

    @Test
    @Order(4)
    @DisplayName("Should find all computer storages and return page format and code 200")
    @WithMockUser(username = "admin")
    void findAllPagination() throws Exception  {
        final String page = "/page/1";
        RequestBuilder request = MockMvcRequestBuilders.get(EndpointUrlConstant.computerStorage + page);
        mvc.perform(request).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("pageable.pageNumber", Is.is(1)));
    }

    @Test
    @Order(5)
    @DisplayName("Should find a computer storage by ID and return code 200")
    @WithMockUser(username = "admin")
    void findById() throws Exception {
        final String brandName = "Storage brand edited";
        final String id = "/1";
        RequestBuilder request = MockMvcRequestBuilders.get(EndpointUrlConstant.computerStorage + id);
        mvc.perform(request).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("brandName", Is.is(brandName))
        );
    }

    @Test
    @Order(6)
    @DisplayName("Should find and disable a computer storage by ID and return code 200")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void deleteById() throws Exception {
        final String id = "/1";
        RequestBuilder request = MockMvcRequestBuilders.delete(EndpointUrlConstant.computerStorage + id);
        mvc.perform(request).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("active", Is.is(false))
        );
    }
}