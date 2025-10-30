package com.example;
 


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class Controller implements Initializable {
    @FXML
    private javafx.scene.text.TextFlow outputFlow;
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

        String infoText;
        switch (selected) {
            case "CPU":
                infoText = new CPUInfo().getCPUDetails();
                break;
            case "Sensors":
                infoText = new SensorsInfo().getSensorDetails();
                break;
            case "Graphics":
                infoText = new GraphicsInfo().getGraphicsDetails();
                break;
            case "USB":
                infoText = new UsbInfo().getUsbDetails();
                break;
            case "Battery":
                infoText = new BatteryInfoPrinter().getBatteryInfo();
                break;
            case "Computer System":
                infoText = new ComputerSystemInfo().getComputerSystemDetails();
                break;
            case "Disks and File Systems":
                infoText = new DisksAndFilesystems().getInfo();
                break;
            case "Memory":
                infoText = new MemoryInfo().getMemoryDetails();
                break;
            case "Network":
                infoText = new NetworkInfo().getNetworkDetails();
                break;
            case "Operating System":
                infoText = new OSInfo().buildOsInfo();
                break;
            case "System and Entry Points":
                infoText = new SystemAndEntryPoints().getSystemInfo();
                break;
            default:
                infoText = "No information available for: " + selected;
                break;
        }
        outputFlow.getChildren().clear();
            for (String line : infoText.split("\n")) {
                // Calculate tree depth by counting "|" characters
                int depth = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '|') {
                        depth++;
                    }
                }
                // Lightness for tree (calculated by counting "|" sequences)
                String[] grays = {"#000000", "#444444", "#888888", "#bbbbbb", "#eeeeee", "#f5f5f5"};
                String color = depth >= 0 && depth < grays.length ? grays[depth] : grays[grays.length - 1];
                int idx = line.indexOf(":");
                if (idx > 0) {
                    javafx.scene.text.Text before = new javafx.scene.text.Text(line.substring(0, idx + 1));
                    before.setFill(javafx.scene.paint.Color.RED);
                    before.setFont(javafx.scene.text.Font.font("Consolas", javafx.scene.text.FontWeight.BOLD, 12));
                    javafx.scene.text.Text after = new javafx.scene.text.Text(line.substring(idx + 1) + "\n");
                    after.setFill(javafx.scene.paint.Color.web(color));
                    after.setFont(javafx.scene.text.Font.font("Consolas", javafx.scene.text.FontWeight.NORMAL, 12));
                    outputFlow.getChildren().addAll(before, after);
                } else {
                    javafx.scene.text.Text t = new javafx.scene.text.Text(line + "\n");
                    t.setFill(javafx.scene.paint.Color.web(color));
                    t.setFont(javafx.scene.text.Font.font("Consolas", javafx.scene.text.FontWeight.NORMAL, 12));
                    outputFlow.getChildren().add(t);
                }
            }
    }



 
}