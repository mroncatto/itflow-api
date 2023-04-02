package io.github.mroncatto.itflow.domain.company.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.ItflowApiApplication;
import io.github.mroncatto.itflow.domain.company.model.Branch;
import io.github.mroncatto.itflow.domain.company.model.Company;
import io.github.mroncatto.itflow.domain.company.model.Department;
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

import static io.github.mroncatto.itflow.config.constant.SecurityConstant.BASE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ItflowApiApplication.class
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(profiles = "test")
class CompanyControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void shouldCreateCompanyAndReturn201() throws Exception {
        RequestBuilder request = post(BASE_URL + "/company")
                .content(objectMapper.writeValueAsString(
                        Company.builder()
                                .active(true)
                                .name("Company test")
                                .document("00000000")
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", Is.is("Company test")));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void shouldCreateBranchAndReturn201() throws Exception {
        RequestBuilder request = post(BASE_URL + "/branch")
                .content(objectMapper.writeValueAsString(
                        Branch.builder()
                                .name("Branch test")
                                .active(true)
                                .company(Company.builder()
                                        .id(1l)
                                        .build())
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", Is.is("Branch test")));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void shouldCreateDepartmentAndReturn201() throws Exception {
        RequestBuilder request = post(BASE_URL + "/department")
                .content(objectMapper.writeValueAsString(
                        Department.builder()
                                .branch(Branch.builder()
                                        .id(1l)
                                        .build())
                                .name("Dept test")
                                .active(true)
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", Is.is("Dept test")));
    }

}