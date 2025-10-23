package com.example;

import java.util.List;

import com.example.helpers.Formatter;
import com.example.helpers.Table;

import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.software.os.OSFileStore;

public class DisksAndFilesystems {
    private final SystemInfo systemInfo;

    public DisksAndFilesystems() {
        this.systemInfo = new SystemInfo();
    }

    // Get all disk stores
    public List<HWDiskStore> getDisks() {
        try {
            List<HWDiskStore> disks = systemInfo.getHardware().getDiskStores();
            if (disks == null) {
                return java.util.Collections.emptyList();
            }
            return disks;
        } catch (Throwable t) {
            return java.util.Collections.emptyList();
        }
    }

    // Get all file stores
    public List<OSFileStore> getFileStores() {
        try {
            List<OSFileStore> stores = systemInfo.getOperatingSystem().getFileSystem().getFileStores();
            if (stores == null) {
                return java.util.Collections.emptyList();
            }
            return stores;
        } catch (Throwable t) {
            return java.util.Collections.emptyList();
        }
    }

    // Print all disk and filesystem info
    public void printInfo() {
        System.out.println("Disks & Filesystems");
        System.out.println("-------------------");

        List<HWDiskStore> disks = getDisks();
        if (disks.isEmpty()) {
            System.out.println("No disks found");
        } else {
            System.out.println("Disk count: " + disks.size());
            for (HWDiskStore disk : disks) {
                String name = Formatter.nullToDefault(disk.getName(), "Unknown");
                String model = Formatter.nullToDefault(disk.getModel(), "Unknown");
                String serial = Formatter.nullToDefault(disk.getSerial(), "Unknown");
                String size = Formatter.formatBytes(disk.getSize());
                long reads = disk.getReads();
                long writes = disk.getWrites();
                String readBytes = Formatter.formatBytes(disk.getReadBytes());
                String writeBytes = Formatter.formatBytes(disk.getWriteBytes());
                long transferTime = disk.getTransferTime();
                System.out.println("Name: " + name);
                System.out.println("Model: " + model);
                System.out.println("Serial: " + serial);
                System.out.println("Size: " + size);
                System.out.println("Reads: " + reads);
                System.out.println("Writes: " + writes);
                System.out.println("Read Bytes: " + readBytes);
                System.out.println("Write Bytes: " + writeBytes);
                System.out.println("Transfer Time: " + transferTime + " ms");
                List<HWPartition> parts = disk.getPartitions();
                if (parts.isEmpty()) {
                    System.out.println("Partitions: None");
                } else {
                    System.out.println("Partitions: " + parts.size());
                    for (HWPartition part : parts) {
                        String identification = Formatter.nullToDefault(part.getIdentification(), "Unknown");
                        String partName = Formatter.nullToDefault(part.getName(), "Unknown");
                        String type = Formatter.nullToDefault(part.getType(), "Unknown");
                        String uuid = Formatter.nullToDefault(part.getUuid(), "Unknown");
                        String mountPoint = Formatter.nullToDefault(part.getMountPoint(), "");
                        String partSize = Formatter.formatBytes(part.getSize());
                        long major = part.getMajor();
                        long minor = part.getMinor();
                        System.out.println("  Identification: " + identification);
                        System.out.println("  Name: " + partName);
                        System.out.println("  Type: " + type);
                        System.out.println("  UUID: " + uuid);
                        System.out.println("  Mount Point: " + mountPoint);
                        System.out.println("  Size: " + partSize);
                        System.out.println("  Major: " + major);
                        System.out.println("  Minor: " + minor);
                    }
                }
            }
        }
        System.out.println();

        List<OSFileStore> stores = getFileStores();
        System.out.println("File Stores");
        System.out.println("-----------");
        if (stores.isEmpty()) {
            System.out.println("No file stores found");
        } else {
            System.out.println("File store count: " + stores.size());
            for (OSFileStore store : stores) {
                System.out.println("Name: " + Formatter.nullToDefault(store.getName(), "Unknown"));
                System.out.println("Volume: " + Formatter.nullToDefault(store.getVolume(), "Unknown"));
                System.out.println("Label: " + Formatter.nullToDefault(store.getLabel(), "Unknown"));
                System.out.println("Description: " + Formatter.nullToDefault(store.getDescription(), "Unknown"));
                System.out.println("Type: " + Formatter.nullToDefault(store.getType(), "Unknown"));
                System.out.println("Mount: " + Formatter.nullToDefault(store.getMount(), "Unknown"));
                System.out.println("Total Space: " + Formatter.formatBytes(store.getTotalSpace()));
                System.out.println("Usable Space: " + Formatter.formatBytes(store.getUsableSpace()));
                System.out.println("Free Space: " + Formatter.formatBytes(store.getFreeSpace()));
            }
        }
        System.out.println();
    }

    // Print info in table format
    public void printTable() {
        Table t = new Table();
        t.addRow("METRIC", "VALUE");

        t.addRow("DISK STORES", "");
        List<HWDiskStore> disks = getDisks();
        t.addRow("Disk count", String.valueOf(disks.size()));
        for (int i = 0; i < disks.size(); i++) {
            HWDiskStore disk = disks.get(i);
            String name = Formatter.nullToDefault(disk.getName(), "Unknown");
            String model = Formatter.nullToDefault(disk.getModel(), "Unknown");
            String serial = Formatter.nullToDefault(disk.getSerial(), "Unknown");
            String size = Formatter.formatBytes(disk.getSize());
            String reads = String.valueOf(disk.getReads());
            String writes = String.valueOf(disk.getWrites());
            String readBytes = Formatter.formatBytes(disk.getReadBytes());
            String writeBytes = Formatter.formatBytes(disk.getWriteBytes());
            String transferTime = disk.getTransferTime() + " ms";
            t.addRow("Disk " + (i + 1) + " Name", name);
            t.addRow("Disk " + (i + 1) + " Model", model);
            t.addRow("Disk " + (i + 1) + " Serial", serial);
            t.addRow("Disk " + (i + 1) + " Size", size);
            t.addRow("Disk " + (i + 1) + " Reads", reads);
            t.addRow("Disk " + (i + 1) + " Writes", writes);
            t.addRow("Disk " + (i + 1) + " Read Bytes", readBytes);
            t.addRow("Disk " + (i + 1) + " Write Bytes", writeBytes);
            t.addRow("Disk " + (i + 1) + " Transfer Time", transferTime);
            List<HWPartition> parts = disk.getPartitions();
            t.addRow("Disk " + (i + 1) + " Partitions", String.valueOf(parts.size()));
            for (int j = 0; j < parts.size(); j++) {
                HWPartition part = parts.get(j);
                String identification = Formatter.nullToDefault(part.getIdentification(), "Unknown");
                String partName = Formatter.nullToDefault(part.getName(), "Unknown");
                String type = Formatter.nullToDefault(part.getType(), "Unknown");
                String uuid = Formatter.nullToDefault(part.getUuid(), "Unknown");
                String mountPoint = Formatter.nullToDefault(part.getMountPoint(), "");
                String partSize = Formatter.formatBytes(part.getSize());
                String major = String.valueOf(part.getMajor());
                String minor = String.valueOf(part.getMinor());
                t.addRow("Partition " + (j + 1) + " Identification", identification);
                t.addRow("Partition " + (j + 1) + " Name", partName);
                t.addRow("Partition " + (j + 1) + " Type", type);
                t.addRow("Partition " + (j + 1) + " UUID", uuid);
                t.addRow("Partition " + (j + 1) + " Mount Point", mountPoint);
                t.addRow("Partition " + (j + 1) + " Size", partSize);
                t.addRow("Partition " + (j + 1) + " Major", major);
                t.addRow("Partition " + (j + 1) + " Minor", minor);
            }
        }

        t.addRow("FILE STORES", "");
        List<OSFileStore> stores = getFileStores();
        t.addRow("File store count", String.valueOf(stores.size()));
        for (int i = 0; i < stores.size(); i++) {
            OSFileStore store = stores.get(i);
            String name = Formatter.nullToDefault(store.getName(), "Unknown");
            String volume = Formatter.nullToDefault(store.getVolume(), "Unknown");
            String label = Formatter.nullToDefault(store.getLabel(), "Unknown");
            String description = Formatter.nullToDefault(store.getDescription(), "Unknown");
            String type = Formatter.nullToDefault(store.getType(), "Unknown");
            String mount = Formatter.nullToDefault(store.getMount(), "Unknown");
            String totalSpace = Formatter.formatBytes(store.getTotalSpace());
            String usableSpace = Formatter.formatBytes(store.getUsableSpace());
            String freeSpace = Formatter.formatBytes(store.getFreeSpace());
            t.addRow("FileStore " + (i + 1) + " Name", name);
            t.addRow("FileStore " + (i + 1) + " Volume", volume);
            t.addRow("FileStore " + (i + 1) + " Label", label);
            t.addRow("FileStore " + (i + 1) + " Description", description);
            t.addRow("FileStore " + (i + 1) + " Type", type);
            t.addRow("FileStore " + (i + 1) + " Mount", mount);
            t.addRow("FileStore " + (i + 1) + " Total Space", totalSpace);
            t.addRow("FileStore " + (i + 1) + " Usable Space", usableSpace);
            t.addRow("FileStore " + (i + 1) + " Free Space", freeSpace);
            // Attribute view support not available in this OSHI version
        }

        t.print();
        System.out.println();
    }

    // Test
    public static void main(String[] args) {
        DisksAndFilesystems util = new DisksAndFilesystems();
        util.printInfo();
        System.out.println();
        util.printTable();
    }
}
