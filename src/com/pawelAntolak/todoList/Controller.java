package com.pawelAntolak.todoList;

import com.pawelAntolak.todoList.dataModel.ToDoData;
import com.pawelAntolak.todoList.dataModel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<ToDoItem> toDoItems;

    @FXML
    private ListView<ToDoItem> toDoListView;
    @FXML
    private TextArea details;
    @FXML
    private Label deadlineLabel;
    public void initialize() {

       try {
           ToDoData.getInstance().loadTodoItems();
           toDoItems = ToDoData.getInstance().getToDoItems();
       } catch (IOException e) {

       }


        ToDoData.getInstance().setToDoItems(toDoItems);
        toDoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observable, ToDoItem oldValue, ToDoItem newValue) {
                if(newValue != null) {
                    ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("d, MMMM, yyyy");
                    details.setText(item.getDetails());
                    deadlineLabel.setText(df.format(item.getDeadline()).toString());
                }
            }
        });
        toDoListView.getItems().setAll(toDoItems);
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();
        details.setText(toDoItems.get(0).getDetails());
        deadlineLabel.setText(toDoItems.get(0).getDeadline().toString());
    }
    @FXML
    public void clickListView() {
       /* ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
        details.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());
        StringBuilder sb = new StringBuilder(item.getDetails());
        sb.append("\n\n\n\n");
        sb.append("Dute to : ");
        sb.append(item.getDeadline().toString());
        details.setText(sb.toString()); */
    }
}
