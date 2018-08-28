package com.pawelAntolak.todoList;

import com.pawelAntolak.todoList.dataModel.ToDoData;
import com.pawelAntolak.todoList.dataModel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Controller {

    private ObservableList<ToDoItem> toDoItems;

    @FXML
    private ListView<ToDoItem> toDoListView;
    @FXML
    private TextArea details;
    @FXML
    private Label deadlineLabel;

    @FXML
    private BorderPane mainBorderPane;
    public void initialize() {
        toDoItems = ToDoData.getInstance().getToDoItems();
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
        toDoListView.setItems(ToDoData.getInstance().getToDoItems());
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();
        details.setText(toDoItems.get(0).getDetails());
        deadlineLabel.setText(toDoItems.get(0).getDeadline().toString());

        toDoListView.setCellFactory(new Callback<ListView<ToDoItem>, ListCell<ToDoItem>>() {
            @Override
            public ListCell<ToDoItem> call(ListView<ToDoItem> param) {
                ListCell<ToDoItem> cell = new ListCell<ToDoItem>() {
                    @Override
                    protected void updateItem(ToDoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setText(null);
                        } else {
                            setText(item.getShortDescription());
                            if(item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.RED);
                            } else if(item.getDeadline().equals(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.BROWN);
                            }
                        }
                    }
                };
                return cell;
            }

        });
    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new to do item");
        dialog.setHeaderText("Use this dialog to create new to do item");
        FXMLLoader fxmlLoader= new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("toDoItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e){
            System.out.println("Error occured");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("OK PRESSED");
            DialogController controller = fxmlLoader.getController();
            ToDoItem newItem = controller.processResult();
            toDoListView.getSelectionModel().select(newItem);
        } else {
            System.out.println("Cancel pressed");
        }
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
