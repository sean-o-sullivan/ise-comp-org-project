package com.example;
 


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Scene scene;    
    private Stage stage;
    private Parent root;
    @FXML
    private TextArea outputArea;

    public void switchToScene1(ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("/com/example/sceneBuilder.fxml"));
       stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       scene = new Scene(root);
         stage.setScene(scene);
            stage.show();
        // Load Scene1 FXML and set it to the stage
    }
      public void switchToScene2(ActionEvent event) throws IOException {
                      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/scene2.fxml"));
        Parent root = loader.load();

        // ✅ Run CPU info right after scene loads
        Controller scene2Controller = loader.getController();
        scene2Controller.showCPUInfo(event);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void showCPUInfo(ActionEvent event) {
        CPUInfo cpuInfo = new CPUInfo();          // Create your CPUInfo object
        String result = cpuInfo.getCPUDetails();  // Get the full text output
        outputArea.setText(result);   
    }



 
}