package com.taskManager.integrationtests.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskManager.configs.TestConfigs;

import com.taskManager.integrationtests.dto.LoginDTO;
import com.taskManager.integrationtests.dto.PersonDTO;
import com.taskManager.integrationtests.dto.TokenDTO;
import com.taskManager.integrationtests.testcontainers.AbstractIntegrationTest;
import com.taskManager.model.Person;
import com.taskManager.repository.PersonRepository;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonDTO person;


    @BeforeAll
    public static void setup() {

        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonDTO();
    }

    @Test
    @Order(0)
    public void testRegistry() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        var content = given().basePath("/api/persons/registry")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
        person = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getName());
        assertNotNull(persistedPerson.getEmail());
        assertNotNull(persistedPerson.getUsername());
        assertNotNull(persistedPerson.getPassword());

        assertTrue(persistedPerson.getId() > 0);

//        assertEquals("Richard", persistedPerson.getFirstName());
//        assertEquals("Stallman", persistedPerson.getLastName());
//        assertEquals("New York City, New York, US", persistedPerson.getAddress());
//        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(1)
    public void authorization() throws JsonMappingException, JsonProcessingException {

        LoginDTO user = new LoginDTO("vini14", "123456");

        var accessToken = given()
                .basePath("/api/persons/login")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenDTO.class)
                .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/api/persons")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    private void mockPerson() {
        person.setName("Vinicius");
        person.setEmail("vini@email.com");
        person.setUsername("vini14");
        person.setPassword("123456");
    }
}
