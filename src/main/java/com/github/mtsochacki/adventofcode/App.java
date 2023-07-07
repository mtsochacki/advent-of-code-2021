package com.github.mtsochacki.adventofcode;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;


public class App extends Application {

    @Override
    public void start(Stage stage) {
        ListView<String> listView = new ListView<>();
        listView.getItems().add("Solve puzzles");
        listView.getItems().add("About");

        Button button = new Button("Continue");
        Button daysContinue = new Button("Continue");

        ListView<String> days = new ListView<>();
        days.getItems().add("Day 1");
        days.getItems().add("Day 2");
        days.getItems().add("Day 3");
        days.getItems().add("Day 4");
        days.getItems().add("Day 5");
        days.getItems().add("Day 6");
        days.getItems().add("Day 7");
        days.getItems().add("Day 8");
        days.getItems().add("Day 9");

        HBox hbox = new HBox(listView, button);
        HBox daysBox = new HBox(days);

        Scene scene = new Scene(hbox, 300, 120);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                hbox.getChildren().setAll(daysBox, daysContinue);
            }
        });

        daysContinue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(stage);
                FileReader fileReader = new FileReader();
                List<String> strings = fileReader.readFile(file);
                Day day =
            }
        });


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private Day createDay(int day) {
        return switch (day) {
            case 1 -> new Day01();
            case 2 -> new Day02();
            case 2 -> new Day02();
            case 2 -> new Day02();
            case 2 -> new Day02();
            case 2 -> new Day02();
            case 2 -> new Day02();
            case 2 -> new Day02();
            case 2 -> new Day02();
            case 2 -> new Day02();
            default -> new Day01();
        }
    }
}
