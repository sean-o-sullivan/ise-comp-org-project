package com.example;

import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import java.util.List;

public class GraphicsInfo {

    public String getGraphicsDetails() {
        StringBuilder info = new StringBuilder();

        try {
            SystemInfo si = new SystemInfo();
            List<GraphicsCard> gpus = si.getHardware().getGraphicsCards();

            info.append("======================\n");
            info.append("GRAPHICS CARD INFORMATION\n");
            info.append("=========================\n");

            if (gpus == null || gpus.isEmpty()) {
                info.append("No graphics cards detected.\n");
                return info.toString();
            }

            for (int i = 0; i < gpus.size(); i++) {
                GraphicsCard gpu = gpus.get(i);
                info.append(String.format("=== GPU %d ===\n", i + 1));
                info.append("Name: ").append(gpu.getName()).append("\n");
                info.append("Vendor: ").append(gpu.getVendor()).append("\n");
                info.append("VRAM (bytes): ").append(gpu.getVRam()).append("\n");
                info.append("Version Info: ").append(gpu.getVersionInfo()).append("\n");
                info.append("Device ID: ").append(gpu.getDeviceId()).append("\n");
                info.append("=========================\n\n");
            }

        } catch (Exception e) {
            info.append("Error fetching graphics card information: ")
                .append(e.getMessage())
                .append("\n");
        }

        return info.toString();
    }
}

