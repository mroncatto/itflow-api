package io.github.mroncatto.itflow.infrastructure.web.controller.software;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.ItflowApiApplication;
import io.github.mroncatto.itflow.application.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.software.dto.SoftwareLicenseRequestDto;
import io.github.mroncatto.itflow.domain.software.dto.SoftwareRequestDto;
import io.github.mroncatto.itflow.domain.software.entity.Software;
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
class SoftwareLicenseControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("Should create a software and return code 201")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void createSoftware() throws Exception {
        final String softwareName = "Software test";
        RequestBuilder request = MockMvcRequestBuilders.post(EndpointUrlConstant.COMPUTER_SOFTWARE)
                .content(objectMapper.writeValueAsString(
                        SoftwareRequestDto.builder()
                                .name(softwareName)
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("name", Is.is(softwareName))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Should create a software license and return code 201")
    @WithMockUser(username = "admin", authorities = "HELPDESK")
    void save() throws Exception {
        final String licenseDesc = "License test";
        RequestBuilder request = MockMvcRequestBuilders.post(EndpointUrlConstant.COMPUTER_LICENSE)
                .content(objectMapper.writeValueAsString(
                        SoftwareLicenseRequestDto.builder()
                                .active(true)
                                .software(
                                        Software.builder()
                                                .id(1L)
                                                .name("Software test")
                                                .build())
                                .description(licenseDesc)
                                .build()))
                .contentType(APPLICATION_JSON_VALUE);
        mvc.perform(request).andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("description", Is.is(licenseDesc))
        );
    }


}