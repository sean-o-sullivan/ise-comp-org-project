package com.example;

import oshi.SystemInfo;
import oshi.hardware.Sensors;
import java.util.Arrays;

public class SensorsInfo {

    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        Sensors sensors = si.getHardware().getSensors();

        double cpuTemp = sensors.getCpuTemperature();
        System.out.println(String.format("CPU Temperature: %.1f °C", cpuTemp));

        int[] fanSpeeds = sensors.getFanSpeeds();
        System.out.println("Fan Speeds (RPM): " + Arrays.toString(fanSpeeds));

        double cpuVoltage = sensors.getCpuVoltage();
        System.out.println(String.format("CPU Voltage: %.2f V", cpuVoltage));
    }
}
