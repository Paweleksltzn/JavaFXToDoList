package com.pawelAntolak.todoList;

import com.pawelAntolak.todoList.dataModel.ToDoData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("To do list");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        try{
            ToDoData.getInstance().storeToDoItems();
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
