package com.taskManager.controller;

import com.taskManager.dto.NoteDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.model.Note;
import com.taskManager.model.Person;
import com.taskManager.security.JwtTokenProvider;
import com.taskManager.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
@Tag(name = "Notes", description = "Endpoints for Managing Notes")
public class NoteController {

    @Autowired
    private NoteService service;

    @Autowired
    private JwtTokenProvider provider;

    @Operation(summary = "Create a new Note",
            description = "Create a new Note by passing in a JSON representation of the note!",
            tags = {"Notes"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = NoteDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/create")
    public Note create(@RequestBody NoteDTO noteDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), noteDTO.getPersonId());
        return service.createNote(noteDTO);
    }

    @Operation(summary = "Update note priority",
            description = "Updates note priority by passing in a JSON representation of the note!",
            tags = {"Notes"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = NoteDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/update/priority")
    public Note updateNotePriotiry(@RequestBody NoteDTO noteDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), noteDTO.getPersonId());
        return service.updateNotePriotiry(noteDTO);
    }

    @Operation(summary = "Update note situation",
            description = "Updates note situation by passing in a JSON representation of the note!",
            tags = {"Notes"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = NoteDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/update/situation")
    public Note updateNoteSituation(@RequestBody NoteDTO noteDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), noteDTO.getPersonId());
        return service.updateNoteSituation(noteDTO);
    }


    @Operation(summary = "Finds all Notes a Person", description = "Finds all Notes a Person",
            tags = {"Notes"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = NoteDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/list/{id}")
    public List<Note> findAllNotesThePerson(@PathVariable Long id,@RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), id);
        return service.findAll(id);
    }

    @Operation(summary = "Finds a Notes By Id", description = "Finds a Notes By Id",
            tags = {"Notes"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = NoteDTO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/{idPerson}/{idNote}")
    public Note findNoteById(@PathVariable Long idPerson, @PathVariable Long idNote, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), idPerson);
        return service.findById(idPerson, idNote);
    }

    @Operation(summary = "Deletes a Note",
            description = "Deletes a Note by passing in a JSON representation of the note!",
            tags = {"Notes"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @DeleteMapping("/delete/{idPerson}/{idNote}")
    public void deleteNote(@PathVariable Long idPerson, Long idNote, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), idPerson);
        service.deleteNote(idPerson, idNote);
    }
}
