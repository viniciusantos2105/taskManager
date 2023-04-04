package com.taskManager.integrationtests.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskManager.configs.TestConfigs;
import com.taskManager.dto.NoteDTO;
import com.taskManager.enums.Priority;
import com.taskManager.integrationtests.dto.LoginDTO;
import com.taskManager.integrationtests.dto.PersonDTO;
import com.taskManager.integrationtests.dto.TokenDTO;
import com.taskManager.integrationtests.testcontainers.AbstractIntegrationTest;
import com.taskManager.model.Note;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonDTO person;

    private static NoteDTO note;

    @BeforeAll
    public static void setup() {

        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonDTO();
        note = new NoteDTO();
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

        assertEquals("Vinicius", persistedPerson.getName());
        assertEquals("vini@email.com", persistedPerson.getEmail());
        assertEquals("vini14", persistedPerson.getUsername());
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
                .setBasePath("/api/note")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(2)
    public void testCreateNote() throws JsonMappingException, JsonProcessingException {
        mockNote();
        note.setPersonId(person.getId());

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(note)
                .when()
                .post("/create")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        NoteDTO persistedNote = objectMapper.readValue(content, NoteDTO.class);
        note = persistedNote;

        assertNotNull(persistedNote);

        assertNotNull(persistedNote.getId());
        assertNotNull(persistedNote.getTitle());
        assertNotNull(persistedNote.getSituation());
        assertNotNull(persistedNote.getPriority());
        assertNotNull(persistedNote.getDescription());
        assertNotNull(persistedNote.getDate());

        assertEquals(note.getId(), persistedNote.getId());

        assertEquals("Title test", persistedNote.getTitle());
        assertEquals("Note test", persistedNote.getDescription());
        assertEquals("29/05/2023", persistedNote.getDate());
        assertEquals("MEAN", persistedNote.getPriority());
        assertEquals("OPENED", persistedNote.getSituation());
    }

    @Test
    @Order(3)
    public void testUpdatePriority() throws JsonMappingException, JsonProcessingException {
        note.setPriority("maxima");
        note.setPersonId(person.getId());

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(note)
                .when()
                .patch("/update/priority")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        NoteDTO persistedNote = objectMapper.readValue(content, NoteDTO.class);
        note = persistedNote;

        assertNotNull(persistedNote);

        assertNotNull(persistedNote.getId());
        assertNotNull(persistedNote.getTitle());
        assertNotNull(persistedNote.getSituation());
        assertNotNull(persistedNote.getPriority());
        assertNotNull(persistedNote.getDescription());
        assertNotNull(persistedNote.getDate());

        assertEquals(note.getId(), persistedNote.getId());

        assertEquals("Title test", persistedNote.getTitle());
        assertEquals("Note test", persistedNote.getDescription());
        assertEquals("29/05/2023", persistedNote.getDate());
        assertEquals("MAXIMUM", persistedNote.getPriority());
        assertEquals("OPENED", persistedNote.getSituation());
    }


    @Test
    @Order(3)
    public void testUpdateSituation() throws JsonMappingException, JsonProcessingException {
        note.setSituation("feito");
        note.setPersonId(person.getId());

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(note)
                .when()
                .patch("/update/situation")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        NoteDTO persistedNote = objectMapper.readValue(content, NoteDTO.class);
        note = persistedNote;

        assertNotNull(persistedNote);

        assertNotNull(persistedNote.getId());
        assertNotNull(persistedNote.getTitle());
        assertNotNull(persistedNote.getSituation());
        assertNotNull(persistedNote.getPriority());
        assertNotNull(persistedNote.getDescription());
        assertNotNull(persistedNote.getDate());

        assertEquals(note.getId(), persistedNote.getId());

        assertEquals("Title test", persistedNote.getTitle());
        assertEquals("Note test", persistedNote.getDescription());
        assertEquals("29/05/2023", persistedNote.getDate());
        assertEquals("MAXIMUM", persistedNote.getPriority());
        assertEquals("DONE", persistedNote.getSituation());
    }

    private void mockPerson() {
        person.setName("Vinicius");
        person.setEmail("vini@email.com");
        person.setUsername("vini14");
        person.setPassword("123456");
    }

    private void mockNote(){
        note.setPriority("media");
        note.setDate("29/05/2023");
        note.setDescription("Note test");
        note.setTitle("Title test");
    }

}
