package api.com.medhead.controller;

import api.com.medhead.model.SpecialityGroup;
import api.com.medhead.payload.request.PatientSearchRequest;
import api.com.medhead.payload.request.SpecialityGroupRequest;
import api.com.medhead.service.SpecialityGroupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestPropertySource("classpath:test.properties")
class NhsHospitalControllerTest {

    @Autowired
    NhsHospitalController nhsHospitalController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    SpecialityGroupService specialityGroupService;

    private SpecialityGroupRequest specialityGroupRequest;
    private PatientSearchRequest patientSearchRequest;


    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer("postgres:15-alpine");


    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);

    }
    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Value("${location.search.perimeter.meters}")
    private int locationSearchPerimeters;
    @Value("${osm.location}")
    private String osmLocation;

    @Test
    void getAllHospitals() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(NhsHospitalController.PATH+"/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllSpecialities() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(NhsHospitalController.PATH+"/specialities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getSpecialitiesBySpecialityGroupName() throws Exception {
        List<SpecialityGroup> specialityGroupList = specialityGroupService.getAllSpecialityGroups();
        SpecialityGroup specialityGroup = specialityGroupList.get(0);
        this.specialityGroupRequest = new SpecialityGroupRequest();
        this.specialityGroupRequest.setId(specialityGroup.getId());
        this.specialityGroupRequest.setName(specialityGroup.getName());
        mockMvc.perform(MockMvcRequestBuilders
                        .get(NhsHospitalController.PATH+"/specialities")
                        .content(objectMapper.writeValueAsString(this.specialityGroupRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllSpecialityGroups() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(NhsHospitalController.PATH+"/speciality-groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getPerimeter() {
        int searchPerimeter=nhsHospitalController.getPerimeter();
        assertEquals(9000, searchPerimeter);
    }

    @Test
    void getNearestHospital() throws Exception {
        this.patientSearchRequest = new PatientSearchRequest();
        this.patientSearchRequest.setSpecialityId(162);
        this.patientSearchRequest.setLongitude(-0.194594);
        this.patientSearchRequest.setLatitude(51.532844);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(NhsHospitalController.PATH+"/getNearest")
                        .content(objectMapper.writeValueAsString(this.patientSearchRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}