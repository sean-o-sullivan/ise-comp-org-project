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
    private final String[] options = {"CPU", "Sensors", "Graphics", "USB", "Battery", "Computer System", "Disks and File Systems", "Memory", "Network", "Operating System","System and Entry Points"};


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
            case "CPU" -> {
                CPUInfo cpuInfo = new CPUInfo();
                outputArea.setText(cpuInfo.getCPUDetails());
            }
            case "Sensors" -> {
                SensorsInfo sensorsInfo = new SensorsInfo();
                outputArea.setText(sensorsInfo.getSensorDetails());
            }
            case "Graphics" -> {
                GraphicsInfo graphicsInfo = new GraphicsInfo();
                outputArea.setText(graphicsInfo.getGraphicsDetails());
            }
            case "USB" -> {
                UsbInfo usbInfo = new UsbInfo();
                outputArea.setText(usbInfo.getUsbDetails());
            }
            case "Battery" -> {
                BatteryInfoPrinter batteryInfo = new BatteryInfoPrinter();
                outputArea.setText(batteryInfo.getBatteryInfo());
            }
            case "Computer System" -> {
                ComputerSystemInfo computerSystemInfo = new ComputerSystemInfo();
                outputArea.setText(computerSystemInfo.getComputerSystemDetails());
            }
            case "Disks and File Systems" -> {
                DisksAndFilesystems diskInfo = new DisksAndFilesystems();
                outputArea.setText(diskInfo.getDisks().toString());
                outputArea.appendText("\n");
                outputArea.appendText(diskInfo.getFileStores().toString());
            }
            case "Memory" -> {
                MemoryInfo memoryInfo = new MemoryInfo();
                outputArea.setText(memoryInfo.getMemoryDetails());
            }
            case "Network" -> {
                NetworkInfo networkInfo = new NetworkInfo();
                outputArea.setText(networkInfo.getNetworkDetails());
            }
            case "Operating System" -> {
                OSInfo osInfo = new OSInfo();
                outputArea.setText(osInfo.buildOsInfo());
            }
            case "System and Entry Points" -> {
                SystemAndEntryPoints systemAndEntryPointsInfo = new SystemAndEntryPoints();
                outputArea.setText(systemAndEntryPointsInfo.getSystemInfo());
            }
            // Add more cases for the rest of your 13 info classes
            default -> outputArea.setText("No information available for: " + selected);
        }
    }



 
}