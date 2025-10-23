package com.example;

import java.util.Collections;
import java.util.List;

import com.example.helpers.Formatter;
import com.example.helpers.Table;

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

    // Get list of graphics cards (GPU controllers)
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
    public void printSystemInfo() {
        System.out.println("System & Hardware Information");
        System.out.println("----------------------------");
        // Computer System
        ComputerSystem cs = getComputerSystem();
        System.out.println("Computer System");
        System.out.println("---------------");
        if (cs == null) {
            System.out.println("Error fetching computer system info.");
        } else {
            System.out.println("Manufacturer: " + Formatter.nullToDefault(cs.getManufacturer(), "Not Available"));
            System.out.println("Model: " + Formatter.nullToDefault(cs.getModel(), "Not Available"));
            System.out.println("Serial Number: " + Formatter.nullToDefault(cs.getSerialNumber(), "Not Available"));
            if (cs.getBaseboard() != null) {
                System.out.println(
                        "Baseboard: " + Formatter.nullToDefault(cs.getBaseboard().getModel(), "Not Available"));
                System.out.println(
                        "Firmware: " + Formatter.nullToDefault(cs.getFirmware().getVersion(), "Not Available"));
            }
        }
        System.out.println();

        // Processor
        CentralProcessor cpu = getProcessor();
        System.out.println("Processor");
        System.out.println("---------");
        if (cpu == null) {
            System.out.println("Error fetching processor info.");
        } else {
            System.out.println(
                    "Name: " + Formatter.nullToDefault(cpu.getProcessorIdentifier().getName(), "Not Available"));
            System.out.println("Physical CPUs: " + cpu.getPhysicalProcessorCount());
            System.out.println("Logical CPUs: " + cpu.getLogicalProcessorCount());
            System.out.println("Max Frequency: " + Formatter.formatHertz(cpu.getMaxFreq()));
        }
        System.out.println();

        // Memory
        GlobalMemory mem = getMemory();
        System.out.println("Memory");
        System.out.println("------");
        if (mem == null) {
            System.out.println("Error fetching memory info.");
        } else {
            System.out.println("Total: " + Formatter.formatBytes(mem.getTotal()));
            System.out.println("Available: " + Formatter.formatBytes(mem.getAvailable()));
        }
        System.out.println();

        // Disks
        List<HWDiskStore> disks = getDiskStores();
        System.out.println("Disk Drives");
        System.out.println("-----------");
        if (disks.isEmpty()) {
            System.out.println("No disk drives found.");
        } else {
            System.out.println("Disk count: " + disks.size());
            for (HWDiskStore d : disks) {
                System.out.println("Model: " + Formatter.nullToDefault(d.getModel(), "Unknown") + ", Size: "
                        + Formatter.formatBytes(d.getSize()));
            }
        }
        System.out.println();

        // File System
        FileSystem fs = getFileSystem();
        System.out.println("File System");
        System.out.println("-----------");
        if (fs == null) {
            System.out.println("Error fetching file system info.");
        } else {
            System.out.println("Mount points: " + fs.getFileStores().size());
        }
        System.out.println();

        // Network
        List<NetworkIF> nics = getNetworkIFs();
        System.out.println("Network Interfaces");
        System.out.println("------------------");
        if (nics.isEmpty()) {
            System.out.println("No network interfaces found.");
        } else {
            System.out.println("NIC count: " + nics.size());
            for (NetworkIF nic : nics) {
                System.out.println("Name: " + Formatter.nullToDefault(nic.getName(), "Unknown") + ", MAC: "
                        + Formatter.nullToDefault(nic.getMacaddr(), "N/A"));
            }
        }
        System.out.println();

        // Power Sources
        List<PowerSource> power = getPowerSources();
        System.out.println("Power Sources");
        System.out.println("-------------");
        if (power.isEmpty()) {
            System.out.println("No power sources found.");
        } else {
            System.out.println("Power source count: " + power.size());
            for (PowerSource p : power) {
                System.out.println("Name: " + Formatter.nullToDefault(p.getName(), "Unknown"));
            }
        }
        System.out.println();

        // Sensors
        Sensors sensors = getSensors();
        System.out.println("Sensors");
        System.out.println("-------");
        if (sensors == null) {
            System.out.println("Error fetching sensors info.");
        } else {
            System.out.println("CPU Temp: " + sensors.getCpuTemperature() + " °C");
            System.out.println(
                    "Fan Speeds: " + (sensors.getFanSpeeds().length > 0 ? sensors.getFanSpeeds().length : "N/A"));
        }
        System.out.println();

        // USB Devices
        List<UsbDevice> usb = getUsbDevices(true);
        System.out.println("USB Devices");
        System.out.println("-----------");
        if (usb.isEmpty()) {
            System.out.println("No USB devices found.");
        } else {
            System.out.println("USB device count: " + usb.size());
            printUsbDeviceTree(usb, "");
        }
        System.out.println();

        // Graphics Cards
        List<GraphicsCard> gpus = getGraphicsCards();
        System.out.println("Graphics Cards");
        System.out.println("--------------");
        if (gpus.isEmpty()) {
            System.out.println("No graphics cards found.");
        } else {
            System.out.println("GPU count: " + gpus.size());
            for (GraphicsCard g : gpus) {
                System.out.println("Name: " + Formatter.nullToDefault(g.getName(), "Unknown") + ", VRAM: "
                        + Formatter.formatBytes(g.getVRam()));
            }
        }
        System.out.println();

        // Logical Volume Groups
        List<LogicalVolumeGroup> lvgs = getLogicalVolumeGroups();
        System.out.println("Logical Volume Groups");
        System.out.println("---------------------");
        if (lvgs.isEmpty()) {
            System.out.println("No logical volume groups found.");
        } else {
            System.out.println("Logical Volume Group count: " + lvgs.size());
            for (LogicalVolumeGroup lvg : lvgs) {
                System.out.println("Name: " + Formatter.nullToDefault(lvg.getName(), "Unknown"));
            }
        }
        System.out.println();
    }

    // Print USB devices in tree format, recursive function
    private void printUsbDeviceTree(List<UsbDevice> devices, String prefix) {
        for (int i = 0; i < devices.size(); i++) {
            UsbDevice device = devices.get(i);
            boolean isLast = (i == devices.size() - 1);

            String name = Formatter.nullToDefault(device.getName(), "Unknown Device");
            String vendor = Formatter.nullToDefault(device.getVendor(), "Unknown Vendor");
            String connector = isLast ? "└── " : "├── "; // Avoid Continous Connections By Changing For Last Item
            System.out.println(prefix + connector + name + " (" + vendor + ")");

            List<UsbDevice> connectedDevices = device.getConnectedDevices(); // Children Devices
            if (!connectedDevices.isEmpty()) {
                String newPrefix = prefix + (isLast ? "    " : "│   "); // Tabbed in prefix
                printUsbDeviceTree(connectedDevices, newPrefix); // Recursive Call for children
            }
        }
    }

    public void printTable() {
        Table t = new Table();
        t.addRow("METRIC", "VALUE");

        // Computer System
        t.addRow("COMPUTER SYSTEM", "");
        ComputerSystem cs = getComputerSystem();
        t.addRow("Manufacturer",
                cs != null ? Formatter.nullToDefault(cs.getManufacturer(), "Not Available") : "Error Fetching");
        t.addRow("Model", cs != null ? Formatter.nullToDefault(cs.getModel(), "Not Available") : "Error Fetching");
        t.addRow("Serial Number",
                cs != null ? Formatter.nullToDefault(cs.getSerialNumber(), "Not Available") : "Error Fetching");

        // Processor
        t.addRow("PROCESSOR", "");
        CentralProcessor cpu = getProcessor();
        t.addRow("CPU Name",
                cpu != null ? Formatter.nullToDefault(cpu.getProcessorIdentifier().getName(), "Not Available")
                        : "Error Fetching");
        t.addRow("Physical CPUs", cpu != null ? String.valueOf(cpu.getPhysicalProcessorCount()) : "Error Fetching");
        t.addRow("Logical CPUs", cpu != null ? String.valueOf(cpu.getLogicalProcessorCount()) : "Error Fetching");
        t.addRow("Max Frequency", cpu != null ? Formatter.formatHertz(cpu.getMaxFreq()) : "Error Fetching");

        // Memory
        t.addRow("MEMORY", "");
        GlobalMemory mem = getMemory();
        t.addRow("RAM Total", mem != null ? Formatter.formatBytes(mem.getTotal()) : "Error Fetching");
        t.addRow("RAM Available", mem != null ? Formatter.formatBytes(mem.getAvailable()) : "Error Fetching");

        // Disk
        t.addRow("DISK DRIVES", "");
        List<HWDiskStore> disks = getDiskStores();
        t.addRow("Disk count", String.valueOf(disks.size()));
        if (!disks.isEmpty()) {
            for (int i = 0; i < disks.size(); i++) {
                HWDiskStore d = disks.get(i);
                t.addRow("Disk " + (i + 1) + " Model", Formatter.nullToDefault(d.getModel(), "Unknown"));
                t.addRow("Disk " + (i + 1) + " Size", Formatter.formatBytes(d.getSize()));
            }
        }

        // File System
        t.addRow("FILE SYSTEM", "");
        FileSystem fs = getFileSystem();
        t.addRow("Mount points", fs != null ? String.valueOf(fs.getFileStores().size()) : "Error Fetching");

        // Network
        t.addRow("NETWORK INTERFACES", "");
        List<NetworkIF> nics = getNetworkIFs();
        t.addRow("NIC count", String.valueOf(nics.size()));
        if (!nics.isEmpty()) {
            for (int i = 0; i < nics.size(); i++) {
                NetworkIF nic = nics.get(i);
                t.addRow("NIC " + (i + 1) + " Name", Formatter.nullToDefault(nic.getName(), "Unknown"));
                t.addRow("NIC " + (i + 1) + " MAC", Formatter.nullToDefault(nic.getMacaddr(), "N/A"));
            }
        }

        // Power Sources
        t.addRow("POWER SOURCES", "");
        List<PowerSource> power = getPowerSources();
        t.addRow("Power source count", String.valueOf(power.size()));
        if (!power.isEmpty()) {
            for (int i = 0; i < power.size(); i++) {
                PowerSource p = power.get(i);
                t.addRow("Power " + (i + 1) + " Name", Formatter.nullToDefault(p.getName(), "Unknown"));
            }
        }

        // Sensors
        t.addRow("SENSORS", "");
        Sensors sensors = getSensors();
        t.addRow("CPU Temp", sensors != null ? sensors.getCpuTemperature() + " °C" : "Error Fetching");
        t.addRow("Fan Speeds", sensors != null ? String.valueOf(sensors.getFanSpeeds().length) : "Error Fetching");

        // USB Devices
        t.addRow("USB DEVICES", "");
        List<UsbDevice> usb = getUsbDevices(false);
        t.addRow("USB device count", String.valueOf(usb.size()));
        if (!usb.isEmpty()) {
            for (int i = 0; i < usb.size(); i++) {
                UsbDevice u = usb.get(i);
                t.addRow("USB " + (i + 1) + " Name", Formatter.nullToDefault(u.getName(), "Unknown"));
                t.addRow("USB " + (i + 1) + " Vendor", Formatter.nullToDefault(u.getVendor(), "N/A"));
            }
        }

        // Graphics Cards
        t.addRow("GRAPHICS CARDS", "");
        List<GraphicsCard> gpus = getGraphicsCards();
        t.addRow("GPU count", String.valueOf(gpus.size()));
        if (!gpus.isEmpty()) {
            for (int i = 0; i < gpus.size(); i++) {
                GraphicsCard g = gpus.get(i);
                t.addRow("GPU " + (i + 1) + " Name", Formatter.nullToDefault(g.getName(), "Unknown"));
                t.addRow("GPU " + (i + 1) + " VRAM", Formatter.formatBytes(g.getVRam()));
            }
        }

        // Logical Volume Groups
        t.addRow("LOGICAL VOLUME GROUPS", "");
        List<LogicalVolumeGroup> lvgs = getLogicalVolumeGroups();
        t.addRow("Logical Volume Groups count", String.valueOf(lvgs.size()));
        if (!lvgs.isEmpty()) {
            for (int i = 0; i < lvgs.size(); i++) {
                LogicalVolumeGroup lvg = lvgs.get(i);
                t.addRow("LVG " + (i + 1) + " Name",
                        Formatter.nullToDefault(lvg.getName(), "Unknown"));
            }
        }

        t.print();
        System.out.println();
    }

    // Test
    public static void main(String[] args) {
        SystemAndEntryPoints sys = new SystemAndEntryPoints();
        sys.printSystemInfo();
        System.out.println();
        sys.printTable();
    }
}
