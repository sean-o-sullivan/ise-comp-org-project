package com.example;

import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.ComputerSystem;
import oshi.hardware.Firmware;

/**
 * Minimal OSHI demo printing computer system, baseboard, firmware, and chassis info.
 */
public class ComputerSystemInfo {
    public String getComputerSystemDetails() {
        StringBuilder info = new StringBuilder();

        try {
        SystemInfo si = new SystemInfo();
        ComputerSystem cs = si.getHardware().getComputerSystem();

        info.append("==============================\n");
        info.append("Computer System Information\n");
        info.append("==============================\n\n");

        if (cs == null) {
            info.append("Error: Unable to fetch computer system details.\n");
            return info.toString();
        }
        //computer system
        info.append("COMPUTER SYSTEM\n");
        info.append("Manufacturer: ").append(cs.getManufacturer()).append("\n");
        info.append("Model:        ").append(cs.getModel()).append("\n");
        info.append("Serial:       ").append(cs.getSerialNumber()).append("\n");
        

        Baseboard bb = cs.getBaseboard();
        info.append("==============================\n");
        info.append("Baseboard\n");
        info.append("==============================\n");
        if (bb != null) {
            info.append("Manufacturer: ").append(bb.getManufacturer()).append("\n");
            info.append("Model:        ").append(bb.getModel()).append("/n");
            info.append("Version:      ").append(bb.getVersion()).append("\n");
            info.append("Serial:       ").append(bb.getSerialNumber()).append("\n");
        } else {
            info.append("The Baseboard info not available.\n\n");
        }

        Firmware fw = cs.getFirmware();
        info.append("==============================\n");
        info.append("Firmware\n");
        info.append("==============================\n");
        if (fw != null) {
            info.append("Manufacturer:  ").append(fw.getManufacturer()).append("\n");
            info.append("Name:          ").append(fw.getName()).append("\n");
            info.append("Description:   ").append(fw.getDescription()).append("\n");
            info.append("Release Date:  ").append(fw.getReleaseDate()).append("\n");
            info.append("Version:       ").append( fw.getVersion()).append("\n");

        } else {
            info.append("The Firmware info not available.\n\n");
        }

        info.append("==============================\n");
        info.append("Chassis (approximated from system info)\n)");
        info.append("==============================\n");
        info.append("Manufacturer: ").append(cs.getManufacturer()).append("\n");
        info.append("Model:        ").append(cs.getModel()).append("\n");
        info.append("Serial:       ").append(cs.getSerialNumber()).append("\n");
        info.append("==============================\n");
    } catch (Exception e) {
        info.append("Error getting computer system info: ").append(e.getMessage()).append("\n");
    }
    return info.toString();
    }

    //test
    public static void main(String[] args) {
        ComputerSystemInfo csi = new ComputerSystemInfo();
        System.out.println(csi.getComputerSystemDetails());
    }
}