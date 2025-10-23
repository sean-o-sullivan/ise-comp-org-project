package com.example;

import oshi.SystemInfo;
import oshi.hardware.UsbDevice;
import java.util.List;

public class UsbInfo {
    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        List<UsbDevice> usbDevices = si.getHardware().getUsbDevices(true);

        for (UsbDevice device : usbDevices) {
            printUsbDevice(device);
            System.out.println(); // Space between devices
        }
    }

    private static void printUsbDevice(UsbDevice device) {
        System.out.println("Name: " + device.getName());
        System.out.println("Vendor: " + device.getVendor());
        System.out.println("Product ID: " + device.getProductId());
        System.out.println("Serial Number: " + device.getSerialNumber());
        System.out.println("Unique Device ID: " + device.getUniqueDeviceId());

        List<UsbDevice> children = device.getConnectedDevices();
        System.out.println("Number of Connected Devices: " + children.size());

        if (!children.isEmpty()) {
            System.out.println("Connected Devices:");
            for (UsbDevice child : children) {
                System.out.println("  - Name: " + child.getName());
                System.out.println("    Vendor: " + child.getVendor());
                System.out.println("    Product ID: " + child.getProductId());
                System.out.println("    Serial Number: " + child.getSerialNumber());
                System.out.println("    Unique Device ID: " + child.getUniqueDeviceId());
                System.out.println();
            }
        }
    }
}
