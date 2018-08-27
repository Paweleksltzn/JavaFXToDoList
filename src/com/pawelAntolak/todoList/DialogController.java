package com.pawelAntolak.todoList;

import com.pawelAntolak.todoList.dataModel.ToDoData;
import com.pawelAntolak.todoList.dataModel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DialogController {
    @FXML
    private TextField shortDescriptionField;
    @FXML
    private TextArea detailsArea;
    @FXML
    private DatePicker deadlinePicker;

    public void processResult() {
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate date = deadlinePicker.getValue();
        ToDoData.getInstance().addToDoItem(new ToDoItem(shortDescription,details,date));
    }
}
