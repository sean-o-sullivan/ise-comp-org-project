package com.example;

import oshi.SystemInfo;
import oshi.hardware.UsbDevice;
import java.util.List;

public class UsbInfo {

    public String getUsbDetails() {
        StringBuilder info = new StringBuilder();

        try {
            SystemInfo si = new SystemInfo();
            List<UsbDevice> usbDevices = si.getHardware().getUsbDevices(true);

            info.append("======================\n");
            info.append("USB DEVICE INFORMATION\n");
            info.append("======================\n");

            if (usbDevices == null || usbDevices.isEmpty()) {
                info.append("No USB devices detected.\n");
                return info.toString();
            }

            for (UsbDevice device : usbDevices) {
                appendUsbDeviceInfo(info, device, 0);
                info.append("\n");
            }

            info.append("======================\n");

        } catch (Exception e) {
            info.append("Error fetching USB information: ").append(e.getMessage()).append("\n");
        }

        return info.toString();
    }

    /**
     * Recursively appends details of a USB device and its connected children.
     */
    private void appendUsbDeviceInfo(StringBuilder info, UsbDevice device, int indentLevel) {
        String indent = "  ".repeat(indentLevel);

        info.append(indent).append("Name: ").append(device.getName()).append("\n");
        info.append(indent).append("Vendor: ").append(device.getVendor()).append("\n");
        info.append(indent).append("Product ID: ").append(device.getProductId()).append("\n");
        info.append(indent).append("Serial Number: ").append(device.getSerialNumber()).append("\n");
        info.append(indent).append("Unique Device ID: ").append(device.getUniqueDeviceId()).append("\n");

        List<UsbDevice> children = device.getConnectedDevices();
        info.append(indent).append("Number of Connected Devices: ").append(children.size()).append("\n");

        if (!children.isEmpty()) {
            info.append(indent).append("Connected Devices:\n");
            for (UsbDevice child : children) {
                appendUsbDeviceInfo(info, child, indentLevel + 1);
                info.append("\n");
            }
        }
    }
}
