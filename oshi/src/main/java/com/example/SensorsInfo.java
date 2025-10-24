package com.example;

import oshi.SystemInfo;
import oshi.hardware.Sensors;
import java.util.Arrays;

public class SensorsInfo {
    public String getSensorDetails() {
        StringBuilder info = new StringBuilder();

        try {
            SystemInfo si = new SystemInfo();
            Sensors sensors = si.getHardware().getSensors();

            info.append("SENSOR INFORMATION\n");
            info.append("======================\n");

            //cpu temp
            double cpuTemp = sensors.getCpuTemperature();
            info.append(String.format("CPU Temperature: %.1f °C%n", cpuTemp));

            // Fan Speeds (convert to readable format)
            int[] fanSpeedsInt = sensors.getFanSpeeds();
            if (fanSpeedsInt != null && fanSpeedsInt.length > 0) {
                info.append("Fan Speeds (RPM): ").append(Arrays.toString(fanSpeedsInt)).append("\n");
            } else {
                info.append("Fan Speeds (RPM): No data available\n");
            }

            // CPU Voltage
            double cpuVoltage = sensors.getCpuVoltage();
            info.append(String.format("CPU Voltage: %.2f V%n", cpuVoltage));

            info.append("======================\n");

        } catch (Exception e) {
            info.append("Error fetching sensor information: ").append(e.getMessage()).append("\n");
        }

        return info.toString();
    }

    // Optional main method for direct testing
    public static void main(String[] args) {
        SensorsInfo sensorsInfo = new SensorsInfo();
        System.out.println(sensorsInfo.getSensorDetails());
    }
}