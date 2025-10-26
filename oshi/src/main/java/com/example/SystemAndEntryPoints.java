package com.example;

import java.util.Collections;
import java.util.List;

import com.example.helpers.Formatter;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.LogicalVolumeGroup;
import oshi.hardware.NetworkIF;
import oshi.hardware.PowerSource;
import oshi.hardware.Sensors;
import oshi.hardware.UsbDevice;
import oshi.software.os.FileSystem;

public class SystemAndEntryPoints {

    private final SystemInfo systemInfo;
    private final HardwareAbstractionLayer hal;

    public SystemAndEntryPoints() {
        this.systemInfo = new SystemInfo();
        this.hal = systemInfo.getHardware();
    }

    // This provides access to hardware info
    public HardwareAbstractionLayer getHardware() {
        try {
            return hal;
        } catch (Throwable t) {
            return null;
        }
    }

    public oshi.software.os.OperatingSystem getOperatingSystem() {
        try {
            return systemInfo.getOperatingSystem();
        } catch (Throwable t) {
            return null;
        }
    }

    public ComputerSystem getComputerSystem() {
        try {
            return hal.getComputerSystem();
        } catch (Throwable t) {
            return null;
        }
    }

    public CentralProcessor getProcessor() {
        try {
            return hal.getProcessor();
        } catch (Throwable t) {
            return null;
        }
    }

    // Get total memory
    public GlobalMemory getMemory() {
        try {
            return hal.getMemory();
        } catch (Throwable t) {
            return null;
        }
    }

    // List of physical storage devices
    public List<HWDiskStore> getDiskStores() {
        try {
            return hal.getDiskStores();
        } catch (Throwable t) {
            return Collections.emptyList();
        }
    }

    public FileSystem getFileSystem() {
        try {
            return systemInfo.getOperatingSystem().getFileSystem();
        } catch (Throwable t) {
            return null;
        }
    }

    // network interfaces
    public List<NetworkIF> getNetworkIFs() {
        try {
            return hal.getNetworkIFs();
        } catch (Throwable t) {
            return Collections.emptyList();
        }
    }

    public List<PowerSource> getPowerSources() {
        try {
            List<PowerSource> sources = hal.getPowerSources();
            if (sources == null) {
                return Collections.emptyList();
            }
            return sources;
        } catch (Throwable t) {
            return Collections.emptyList();
        }
    }

    public Sensors getSensors() {
        try {
            return hal.getSensors();
        } catch (Throwable t) {
            return null;
        }
    }

    // list of USB devices (tree formats as parents and children)
    public List<UsbDevice> getUsbDevices(boolean tree) {
        try {
            return hal.getUsbDevices(tree);
        } catch (Throwable t) {
            return Collections.emptyList();
        }
    }

    // get list of graphics cards (GPU controllers)
    public List<GraphicsCard> getGraphicsCards() {
        try {
            return hal.getGraphicsCards();
        } catch (Throwable t) {
            return Collections.emptyList();
        }
    }

    // Get list of logical volume groups (platform-specific/optional)
    public List<LogicalVolumeGroup> getLogicalVolumeGroups() {
        try {
            return hal.getLogicalVolumeGroups();
        } catch (Throwable t) {
            return Collections.emptyList();
        }
    }

    // Print all system and hardware info
    public String getSystemInfo() {
        StringBuilder info = new StringBuilder();
        info.append("==============================\n");
        info.append("System & Hardware Information");
        info.append("==============================\n\n");

        try {
        //computer system
        ComputerSystem cs = getComputerSystem();
        info.append("COMPUTER SYSTEM\n");
        
        if (cs == null) {
            info.append("Error fetching computer system info.\n\n");
        } else {
            info.append("Manufacturer: ").append(Formatter.nullToDefault(cs.getManufacturer(), "Not Available")).append("\n");
            info.append("Model: ").append(Formatter.nullToDefault(cs.getModel(), "Not Available")).append("\n");
            info.append("Serial Number: " ).append(Formatter.nullToDefault(cs.getSerialNumber(), "Not Available")).append("\n");
            if (cs.getBaseboard() != null) {
                info.append("Baseboard: ").append(Formatter.nullToDefault(cs.getBaseboard().getModel(), "Not Available")).append("\n");
                info.append("Firmware: ").append(Formatter.nullToDefault(cs.getFirmware().getVersion(), "Not Available")).append("\n");
            }
            info.append("\n");
        }

        // Processor
        CentralProcessor cpu = getProcessor();
        info.append("PROCESSOR\n");
        if (cpu == null) {
            info.append("Error fetching processor info.\n\n");
        } else {
            info.append("Name: ").append(Formatter.nullToDefault(cpu.getProcessorIdentifier().getName(), "Not Available")).append("\n");
            info.append("Physical CPUs: ").append(cpu.getPhysicalProcessorCount()).append("\n");
            info.append("Logical CPUs: ").append("cpu.getLogicalProcessorCount()").append("\n");
            info.append("Max Frequency: ").append("Formatter.formatHertz(cpu.getMaxFreq())").append("\n");
        }

        // Memory
        GlobalMemory mem = getMemory();
        info.append("MEMORY\n");
        if (mem == null) {
            info.append("Error fetching memory info.\n\n");
        } else {
            info.append("Total: ").append(Formatter.formatBytes(mem.getTotal())).append("\n");
            info.append("Available: ").append(Formatter.formatBytes(mem.getAvailable())).append("\n");
        }

        // Disks
        List<HWDiskStore> disks = getDiskStores();
        info.append("DISK DRIVES");
        if (disks.isEmpty()) {
            info.append("No disk drives found.\n\n");
        } else {
            info.append("Disk count: ").append(disks.size()).append("\n");
            for (HWDiskStore d : disks) {
                info.append("- Model: ").append(Formatter.nullToDefault(d.getModel(), "Unknown")).append(", Size: ")
                        .append(Formatter.formatBytes(d.getSize())).append("\n");
            }
            info.append("\n");
        }


        // Network
        List<NetworkIF> nics = getNetworkIFs();
        info.append("NETWORK INTERFACES\n");
        if (nics.isEmpty()) {
            info.append("No network interfaces found.\n\n");
        } else {
            info.append("NIC count: ").append(nics.size()).append("\n");
            for (NetworkIF nic : nics) {
                info.append("- Name: ").append(Formatter.nullToDefault(nic.getName(), "Unknown"))
                        .append(", MAC: ").append(Formatter.nullToDefault(nic.getMacaddr(), "N/A")).append("\n");
            }
            info.append("\n");
        }
        
        // Power Sources
        List<PowerSource> power = getPowerSources();
        info.append("POWER SOUCES \n");
        if (power.isEmpty()) {
            info.append("No power sources found.\n\n");
        } else {
            for (PowerSource p : power) {
                info.append("- Name: ").append(Formatter.nullToDefault(p.getName(), "Unknown")).append("\n");
            }
            info.append("\n");
        }
        
        // Sensors
        Sensors sensors = getSensors();
        info.append("SENSORS\n");
        if (sensors == null) {
            info.append("Error fetching sensors info.\n\n");
        } else {
            info.append("CPU Temp: ").append(sensors.getCpuTemperature()).append(" °C\n");
            info.append("Fan Speeds: ")
                    .append(sensors.getFanSpeeds().length > 0 ? sensors.getFanSpeeds().length : "N/A").append("\n\n");
        }

        // USB Devices
        List<UsbDevice> usb = getUsbDevices(true);
        info.append("USB DEVICES\n");
        if (usb.isEmpty()) {
            info.append("No USB devices found.\n\n");
        } else {
            info.append("USB device count: ").append(usb.size()).append("\n");
            appendUsbTree(info, usb, "");
            info.append("\n");
        }

        // Graphics Cards
        List<GraphicsCard> gpus = getGraphicsCards();
        info.append("GRAPHICS CARDS\n");
        if (gpus.isEmpty()) {
            info.append("No graphics cards found.\n\n");
        } else {
            info.append("GPU count: ").append(gpus.size()).append("\n");
            for (GraphicsCard g : gpus) {
                info.append("- Name: ").append(Formatter.nullToDefault(g.getName(), "Unknown"))
                        .append(", VRAM: ").append(Formatter.formatBytes(g.getVRam())).append("\n");
            }
            info.append("\n");
        }
        
        // Logical Volume Groups
        List<LogicalVolumeGroup> lvgs = getLogicalVolumeGroups();
        info.append("LOGICAL VOLUME GROUPS");
        if (lvgs.isEmpty()) {
            info.append("No logical volume groups found.\n\n");
        } else {
            info.append("Logical Volume Group count: ").append(lvgs.size()).append("\n");
            for (LogicalVolumeGroup lvg : lvgs) {
                info.append("- Name: ").append(Formatter.nullToDefault(lvg.getName(), "Unknown")).append("\n");
            }
            info.append("\n");
        }
    
     } catch (Exception e) {
        info.append("Error fetching system: ").append(e.getMessage()).append("\n");
     }
        
     return info.toString();
}

    // Print USB devices in tree format, recursive function
    private void appendUsbTree(StringBuilder sb, List<UsbDevice> devices, String prefix) {
        for (int i = 0; i < devices.size(); i++) {
            UsbDevice device = devices.get(i);
            boolean isLast = (i == devices.size() - 1);

            String connector = isLast ? "└── " : "├── "; // Avoid Continous Connections By Changing For Last Item
            sb.append(prefix).append(connector)
                .append(Formatter.nullToDefault(device.getName(), "Unknown Device"))
                .append(" (").append(Formatter.nullToDefault(device.getVendor(), "Unknown Vendor")).append(")\n");

            if (!device.getConnectedDevices().isEmpty()) {
                appendUsbTree(sb, device.getConnectedDevices(), prefix + (isLast ? "   " : "|   ")); // Recursive Call for children
            }
        }
    }

    public static void main(String[] args) {
        SystemAndEntryPoints sys = new SystemAndEntryPoints();
        System.out.println(sys.getSystemInfo());
        
    }
}
