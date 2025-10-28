package com.example;

import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.UsbDevice;

public class DetectUsb implements Runnable {

    private void showAlert(String title, String message) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    @Override
    public void run() {
        SystemInfo si = new SystemInfo();
        List<UsbDevice> usbDevices = si.getHardware().getUsbDevices(false);
        while (true) {
            List<UsbDevice> currentUsbDevices = si.getHardware().getUsbDevices(false);
            if (currentUsbDevices.size() != usbDevices.size()) {
                for (UsbDevice device : currentUsbDevices) {
                    boolean wasJustPluggedIn = true;
                    for (UsbDevice oldDevice : usbDevices) {
                        if (device.getUniqueDeviceId().equals(oldDevice.getUniqueDeviceId())) {
                            wasJustPluggedIn = false;
                            break;
                        }
                    }
                    if (wasJustPluggedIn) {
                        showAlert("New USB Device Plugged In", device.getName());
                    }
                }
                for (UsbDevice oldDevice : usbDevices) {
                    boolean wasJustUnplugged = true;
                    for (UsbDevice device : currentUsbDevices) {
                        if (device.getUniqueDeviceId().equals(oldDevice.getUniqueDeviceId())) {
                            wasJustUnplugged = false;
                            break;
                        }
                    }
                    if (wasJustUnplugged) {
                        showAlert("USB Device Unplugged", oldDevice.getName());
                    }
                }
                usbDevices = currentUsbDevices;
            }
        }
    }


}