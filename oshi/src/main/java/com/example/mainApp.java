package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class mainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/sceneBuilder.fxml"));
        Scene scene = new Scene(root,600,600, Color.BLACK);
        stage.setTitle("System Information");
        Image icon = new Image("appIcon.png");

        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);

       
       
        Text text = new Text();
        text.setText("This is the test text");
        text.setX(50);
        text.setY(50);
        text.setFont(Font.font("Segoe UI", 50));
        text.setFill(Color.rgb(14, 194, 213, 1));


        Line line = new Line();
        line.setStartX(70);
        line.setStartY(70);
        line.setEndX(200);
        line.setEndY(70);
        line.setStroke(Color.RED);


      


        stage.getIcons().add(icon);
        stage.setWidth(420);
        stage.setHeight(420);
        stage.setFullScreen(true );
        stage.setFullScreenExitHint("To exit full screen press q");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}