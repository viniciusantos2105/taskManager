package com.taskManager.controller;

import com.taskManager.dto.LoginDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.dto.TokenDTO;
import com.taskManager.model.Person;
import com.taskManager.security.JwtTokenProvider;
import com.taskManager.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

    @Autowired
    private PersonService service;

    @Autowired
    private JwtTokenProvider provider;

    @Operation(summary = "Registry a new Person",
            description = "Registry a new Person by passing in a JSON representation of the person!",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/registry")
    public Person registry(@RequestBody Person personDTO){
        Person person = service.registry(personDTO);
        return service.encoderPassword(person);
    }

    @Operation(summary = "Login a Person",
            description = "Login a Person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginDTO){
        return service.loginToken(loginDTO);
    }

    @Operation(summary = "Update person's username",
            description = "Updates a username by passing in a JSON representation of the person!",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/update/username")
    public Person updateUsername(@RequestBody PersonDTO personDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), personDTO.getId());
        return service.updateUsername(personDTO);
    }

    @Operation(summary = "Update person's email",
            description = "Updates a email by passing in a JSON representation of the person!",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/update/email")
    public Person updateEmail(@RequestBody PersonDTO personDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), personDTO.getId());
        return service.updateEmail(personDTO);
    }

    @Operation(summary = "Update person's password",
            description = "Updates a password by passing in a JSON representation of the person!",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/update/password")
    public Person updatePassword(@RequestBody PersonDTO personDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), personDTO.getId());
        return service.updatePassword(personDTO);
    }

    @Operation(summary = "Deletes a Person",
            description = "Deletes a Person by passing in a JSON representation of the person!",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), id);
        service.delete(id);
    }

}
