package com.example;
 


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class Controller implements Initializable {
    @FXML
    private TextArea outputArea;
    @FXML
    private ChoiceBox<String> myChoiceBox;
    private final String[] options = {"CPU Info", "Sensors Info", "Graphics Info", "USB Info"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myChoiceBox.getItems().addAll(options);
        myChoiceBox.setValue("CPU Info");
        myChoiceBox.setOnAction(this::onShowInfoSelected);
    }
    @FXML
    private void onShowInfoSelected(ActionEvent event) {
        String selected = myChoiceBox.getValue();

        switch (selected) {
            case "CPU Info" -> {
                CPUInfo cpuInfo = new CPUInfo();
                outputArea.setText(cpuInfo.getCPUDetails());
            }
            case "Sensors Info" -> {
                SensorsInfo sensorsInfo = new SensorsInfo();
                outputArea.setText(sensorsInfo.getSensorDetails());
            }
            case "Graphics Info" -> {
                GraphicsInfo graphicsInfo = new GraphicsInfo();
                outputArea.setText(graphicsInfo.getGraphicsDetails());
            }
            case "USB Info" -> {
                UsbInfo usbInfo = new UsbInfo();
                outputArea.setText(usbInfo.getUsbDetails());
            }
            // Add more cases for the rest of your 13 info classes
            default -> outputArea.setText("No information available for: " + selected);
        }
    }



 
}