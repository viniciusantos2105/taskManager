package com.taskManager.mock;

import com.taskManager.dto.NoteDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.enums.Priority;
import com.taskManager.enums.Situation;
import com.taskManager.model.Note;
import com.taskManager.model.Person;

import java.util.ArrayList;
import java.util.List;

public class MockNote {

    public Note mockEntity() {
        return mockEntity(1);
    }

    public NoteDTO mockEntityDTO() {
        return mockEntityDTO(1);
    }

    public List<Note> mockEntityList() {
        List<Note> noteList = new ArrayList<Note>();
        for (int i = 0; i < 14; i++)
        {
            noteList.add(mockEntity(i));
        }
        return noteList;
    }

    public Note mockEntity(Integer number) {
        Note note = new Note();
        note.setTitle("Title test" + number);
        note.setDescription("Description test" + number);
        note.setDate("29/12/2023 test" + number);
        note.setSituation(Situation.OPENED);
        note.setPriority(Priority.MAXIMUM);
        return note;
    }

    public NoteDTO mockEntityDTO(Integer number){
        NoteDTO note = new NoteDTO();
        note.setTitle("Title test" + number);
        note.setDescription("Description test" + number);
        note.setDate("29/12/2023 test" + number);
        note.setPriority("maxima");
        return note;
    }
}
