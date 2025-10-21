package com.example;

import oshi.SystemInfo;
import oshi.hardware.PowerSource;
import oshi.hardware.HardwareAbstractionLayer;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BatteryInfoPrinter {
    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        List<PowerSource> batteries = hal.getPowerSources(); // <- List instead of array

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (PowerSource battery : batteries) {
            System.out.printf("Device Name: %s%n", battery.getDeviceName());
            System.out.printf("Power Source Name: %s%n", battery.getName());
            System.out.printf("Manufacturer: %s%n", battery.getManufacturer());
            System.out.printf("Serial Number: %s%n", battery.getSerialNumber());
            System.out.printf("Chemistry: %s%n", battery.getChemistry());
            System.out.printf("Manufacture Date: %s%n",
                    battery.getManufactureDate() != null ? battery.getManufactureDate().format(formatter) : "Unknown");

            System.out.printf("Design Capacity: %d %s%n", battery.getDesignCapacity(), battery.getCapacityUnits());
            System.out.printf("Max Capacity: %d %s%n", battery.getMaxCapacity(), battery.getCapacityUnits());
            System.out.printf("Current Capacity: %d %s%n", battery.getCurrentCapacity(), battery.getCapacityUnits());
            System.out.printf("Remaining Capacity Percent: %.2f%%%n", battery.getRemainingCapacityPercent() * 100);

            System.out.printf("Voltage (V): %.2f%n", battery.getVoltage());
            System.out.printf("Amperage (mA): %.2f%n", battery.getAmperage());
            System.out.printf("Power Usage Rate (mW): %.2f%n", battery.getPowerUsageRate());
            System.out.printf("Temperature (°C): %.2f%n", battery.getTemperature());
            System.out.printf("Estimated Time Remaining (OS): %.2f seconds%n", battery.getTimeRemainingEstimated());
            System.out.printf("Estimated Time Remaining (Instant): %.2f seconds%n", battery.getTimeRemainingInstant());

            System.out.printf("Charging: %b%n", battery.isCharging());
            System.out.printf("Discharging: %b%n", battery.isDischarging());
            System.out.printf("Power On Line: %b%n", battery.isPowerOnLine());

            battery.updateAttributes();

            System.out.println("---------------------------------------------------");
        }
    }
}
