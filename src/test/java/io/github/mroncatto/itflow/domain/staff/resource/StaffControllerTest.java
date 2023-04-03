package io.github.mroncatto.itflow.domain.staff.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.ItflowApiApplication;
import io.github.mroncatto.itflow.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.company.model.Department;
import io.github.mroncatto.itflow.domain.staff.model.Occupation;
import io.github.mroncatto.itflow.domain.staff.model.Staff;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ItflowApiApplication.class
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(profiles = "test")
class StaffControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Order(1)
    @DisplayName("Should create a Staff and return code 201")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void save() throws Exception {
        final String staffName = "Staff test";
        RequestBuilder request = post(EndpointUrlConstant.staff)
                .content(objectMapper.writeValueAsString(
                        Staff.builder()
                                .fullName(staffName)
                                .active(true)
                                .email("test@test.com")
                                .occupation(Occupation.builder()
                                        .id(1L)
                                        .build())
                                .department(Department.builder()
                                        .id(1L)
                                        .build())
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpectAll(
                status().isCreated(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("fullName", Is.is(staffName))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Should find all staff and return not empty list and code 200")
    @WithMockUser(username = "admin")
    void findAll() throws Exception {
        RequestBuilder request = get(EndpointUrlConstant.staff);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("$").exists()
        );
    }

    @Test
    @Order(3)
    @DisplayName("Should find all staff and return page format and code 200")
    @WithMockUser(username = "admin")
    void findAllPagination() throws Exception {
        final String page = "/page/1";
        RequestBuilder request = get(EndpointUrlConstant.staff + page);
        mvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(APPLICATION_JSON),
                jsonPath("pageable.pageNumber", Is.is(1)));
    }

}