package oshi.src.main.java.com.example;

import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.Chassis;
import oshi.hardware.ComputerSystem;
import oshi.hardware.Firmware;

/**
 * Minimal OSHI demo printing computer system, baseboard, firmware, and chassis info.
 */
public class ComputerSystemInfo {
    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        ComputerSystem cs = si.getHardware().getComputerSystem();

        System.out.println("======================");
        System.out.println("Computer System");
        System.out.println("Manufacturer: " + cs.getManufacturer());
        System.out.println("Model:        " + cs.getModel());
        System.out.println("Serial:       " + cs.getSerialNumber());

        Baseboard bb = cs.getBaseboard();
        System.out.println("\nBaseboard");
        System.out.println("Manufacturer: " + bb.getManufacturer());
        System.out.println("Model:        " + bb.getModel());
        System.out.println("Version:      " + bb.getVersion());
        System.out.println("Serial:       " + bb.getSerialNumber());

        Firmware fw = cs.getFirmware();
        System.out.println("\nFirmware");
        System.out.println("Manufacturer:  " + fw.getManufacturer());
        System.out.println("Name:          " + fw.getName());
        System.out.println("Description:   " + fw.getDescription());
        System.out.println("Release Date:  " + fw.getReleaseDate());
        System.out.println("Version:       " + fw.getVersion());

        try {
            Chassis ch = cs.getChassis();
            System.out.println("\nChassis");
            System.out.println("Manufacturer: " + ch.getManufacturer());
            System.out.println("Type:         " + ch.getType());
            System.out.println("Serial:       " + ch.getSerialNumber());
            System.out.println("Version:      " + ch.getVersion());
        } catch (Throwable t) {
            System.out.println("\nChassis information not provided on this platform.");
        }
        System.out.println("======================");
    }
}