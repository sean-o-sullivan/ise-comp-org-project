package com.example;

import java.time.format.DateTimeFormatter;
import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PowerSource;

public class BatteryInfoPrinter {
    public String getBatteryInfo() {
        StringBuilder info = new StringBuilder();

        try {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        List<PowerSource> batteries = hal.getPowerSources(); // <- List instead of array

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (batteries == null || batteries.isEmpty()) {
            info.append("No batteries found.\n");
        } else {
            info.append("======================\n");
            info.append("BATTERY INFORMATION\n");
            info.append("======================\n\n");

        for (PowerSource battery : batteries) {
            info.append("Device Name: ").append(battery.getDeviceName()).append("\n");
            info.append("Power Source Name: ").append(battery.getName()).append("\n");
            info.append("Manufacturer: ").append(battery.getManufacturer()).append("\n");
            info.append("Serial Number: ").append(battery.getSerialNumber()).append("\n");
            info.append("Chemistry: ").append(battery.getChemistry()).append("\n");
            info.append("Manufacture Date: ").append(battery.getManufactureDate() != null ? battery.getManufactureDate().format(formatter) : "Unknown").append("\n");
            info.append("Design Capacity: ").append(battery.getDesignCapacity()).append(" ").append(battery.getCapacityUnits()).append("\n");
            info.append("Max Capacity: ").append(battery.getMaxCapacity()).append(" ").append(battery.getCapacityUnits()).append("\n");
            info.append("Current Capacity: ").append(battery.getCurrentCapacity()).append(" ").append(battery.getCapacityUnits()).append("\n");
            info.append("Remaining Capacity Percent: ").append(String.format("%.2f%%", battery.getRemainingCapacityPercent() * 100)).append("\n");


            info.append("Voltage (V): ").append(String.format("%.2f", battery.getVoltage())).append("\n");
            info.append("Amperage (mA): ").append(String.format("%.2f", battery.getAmperage())).append("\n");
            info.append("Power Usage Rate (mW): ").append(String.format("%.2f", battery.getPowerUsageRate())).append("\n");
            info.append("Temperature (°C): ").append(String.format("%.2f", battery.getTemperature())).append("\n");
            info.append("Estimated Time Remaining (OS): ").append(String.format("%.2f seconds", battery.getTimeRemainingEstimated())).append("\n");
            info.append("Estimated Time Remaining (Instant): ").append(String.format("%.2f seconds", battery.getTimeRemainingInstant())).append("\n");

            info.append("Charging: ").append(battery.isCharging()).append("\n");
            info.append("Discharging: ").append(battery.isDischarging()).append("\n");
            info.append("Power On Line: ").append(battery.isPowerOnLine()).append("\n");

            battery.updateAttributes();

            info.append("---------------------------------------------------");
        }
    }
} catch (Exception e) {
            info.append("Error fetching battery info: ").append(e.getMessage()).append("\n");
        }

        return info.toString();
    }

    //test
    public static void main(String[] args) {
        BatteryInfoPrinter bip = new BatteryInfoPrinter();
       System.out.println(bip.getBatteryInfo());
    }
}
