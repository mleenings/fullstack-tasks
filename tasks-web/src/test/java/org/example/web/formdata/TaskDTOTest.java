package org.example.web.formdata;

import org.example.domain.model.TaskModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskDTOTest {

    @Test
    public void dtoTest() {
        TaskModel model = new TaskModel();
        model.setId("super Id");
        model.setText("super Text");
        model.setDone(true);
        TaskDTO dto = new TaskDTO(model);
        assertEquals(dto.getId(), model.getId());
        assertEquals(dto.getText(), model.getText());
        assertEquals(dto.isDone(), model.isDone());
    }
}
