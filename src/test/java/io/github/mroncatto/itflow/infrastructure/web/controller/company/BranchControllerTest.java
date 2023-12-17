package io.github.mroncatto.itflow.infrastructure.web.controller.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.ItflowApiApplication;
import io.github.mroncatto.itflow.application.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.company.dto.BranchRequestDto;
import io.github.mroncatto.itflow.domain.company.entity.Company;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ItflowApiApplication.class
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(profiles = "test")
class BranchControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("Should create a Branch and return code 201")
    @WithMockUser(username = "admin", authorities ="HELPDESK")
    void save() throws Exception  {
        final String branchName = "Branch test";
        RequestBuilder request = MockMvcRequestBuilders.post(EndpointUrlConstant.BRANCH)
                .content(objectMapper.writeValueAsString(
                        BranchRequestDto.builder()
                                .company(Company
                                        .builder()
                                        .id(1L)
                                        .build())
                                .name(branchName)
                                .active(true)
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Is.is(branchName)));
    }

    @Test
    @Order(2)
    @DisplayName("Should update a Branch and return code 200")
    @WithMockUser(username = "admin", authorities ="HELPDESK")
    void update() throws Exception {
        final String branchName = "Branch test edited";
        RequestBuilder request = MockMvcRequestBuilders.put(EndpointUrlConstant.BRANCH)
                .content(objectMapper.writeValueAsString(
                        BranchRequestDto.builder()
                                .id(2L)
                                .company(Company.builder()
                                        .id(1L)
                                        .build())
                                .name(branchName)
                                .active(true)
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Is.is(branchName)));
    }

    @Test
    @Order(3)
    @DisplayName("Should find all branches and return code 200")
    @WithMockUser(username = "admin")
    void findAll() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get(EndpointUrlConstant.BRANCH);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @Order(4)
    @DisplayName("Should find all branches and return page format code 200")
    @WithMockUser(username = "admin")
    void findAllPagination() throws Exception {
        final String page = "/page/1";
        RequestBuilder request = MockMvcRequestBuilders.get(EndpointUrlConstant.BRANCH + page);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("pageable.pageNumber", Is.is(1)));
    }

    @Test
    @Order(5)
    @DisplayName("Should find a branch By ID and return code 200")
    @WithMockUser(username = "admin")
    void findById() throws Exception {
        final String branchName = "Branch test edited";
        final String id = "/2";
        RequestBuilder request = MockMvcRequestBuilders.get(EndpointUrlConstant.BRANCH + id);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Is.is(branchName)));
    }

    @Test
    @Order(6)
    @DisplayName("Should find and disable a branch By ID and return code 200")
    @WithMockUser(username = "admin", authorities = "MANAGER")
    void deleteById() throws Exception {
        final String id = "/2";
        RequestBuilder request = MockMvcRequestBuilders.delete(EndpointUrlConstant.BRANCH + id);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("active", Is.is(false)));
    }
}