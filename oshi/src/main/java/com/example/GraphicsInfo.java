package com.example;

import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;

public class GraphicsInfo {
    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();

        for (GraphicsCard gpu : si.getHardware().getGraphicsCards()) {
            System.out.println("Graphics Card Name: " + gpu.getName());
            System.out.println("Vendor: " + gpu.getVendor());
            System.out.println("Video Memory (VRAM in bytes): " + gpu.getVRam());
            System.out.println("Version Info: " + gpu.getVersionInfo());
            System.out.println("Device ID: " + gpu.getDeviceId());
            System.out.println();
        }
    }
}
