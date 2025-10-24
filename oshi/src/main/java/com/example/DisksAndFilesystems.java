package com.example;

import java.util.List;

import com.example.helpers.Formatter;

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
    public String getInfo() {
        StringBuilder info = new StringBuilder();

        try {
            info.append("Disks & Filesystems");
            info.append("-------------------");

            List<HWDiskStore> disks = getDisks();
            if (disks.isEmpty()) {
                info.append("No disks found");
            } else {
                info.append("Disk count: ").append(disks.size()).append("\n");
                for (HWDiskStore disk : disks) {
                    String name = Formatter.nullToDefault(disk.getName(), "Unknown");
                    String model = Formatter.nullToDefault(disk.getModel(), "Unknown");
                    String serial = Formatter.nullToDefault(disk.getSerial(), "Unknown");

                    long diskSize = disk.getSize() < 0 ? 0 : disk.getSize();
                    String size = Formatter.formatBytes(diskSize);

                    long readBytesVal = disk.getReadBytes() < 0 ? 0 : disk.getReadBytes();
                    long writeBytesVal = disk.getWriteBytes() < 0 ? 0 : disk.getWriteBytes();
                    String readBytes = Formatter.formatBytes(readBytesVal);
                    String writeBytes = Formatter.formatBytes(writeBytesVal);

                    long reads = disk.getReads();
                    long writes = disk.getWrites();
                    long transferTime = disk.getTransferTime();

                    info.append("Name: ").append(name).append("\n");
                    info.append("Model: ").append(model).append("\n");
                    info.append("Serial: ").append(serial).append("\n");
                    info.append("Size: ").append(size).append("\n");
                    info.append("Reads: ").append(reads).append("\n");
                    info.append("Writes: ").append(writes).append("\n");
                    info.append("Read Bytes: ").append(readBytes).append("\n");
                    info.append("Write Bytes: ").append(writeBytes).append("\n");
                    info.append("Transfer Time: ").append(transferTime).append("ms\n");

                    List<HWPartition> parts = disk.getPartitions();
                    if (parts.isEmpty()) {
                        info.append("Partitions: None\n");
                    } else {
                        info.append("Partitions: ").append(parts.size()).append("\n");
                        for (HWPartition part : parts) {
                            info.append("  Identification: ").append(Formatter.nullToDefault(part.getIdentification(), "Unknown")).append("\n");
                            info.append("  Name: ").append(Formatter.nullToDefault(part.getName(), "Unknown")).append("\n");
                            info.append("  Type: ").append(Formatter.nullToDefault(part.getType(), "Unknown")).append("\n");
                            info.append("  UUID: ").append(Formatter.nullToDefault(part.getUuid(), "Unknown")).append("\n");
                            info.append("  Mount Point: ").append(Formatter.nullToDefault(part.getMountPoint(), "")).append("\n");
                            info.append("  Size: ").append(Formatter.formatBytes(Math.max(0, part.getSize()))).append("\n");
                            info.append("  Major: ").append(part.getMajor()).append("\n");
                            info.append("  Minor: ").append(part.getMinor()).append("\n");
                        }
                    }
                }
            }
        
        info.append("\nFile Stores\n");
        System.out.println("-----------\n");
        List<OSFileStore> stores = getFileStores();
        if (stores.isEmpty()) {
            info.append("No file stores found\n");
        } else {
            info.append("File store count: ").append(stores.size()).append("\n");
            for (OSFileStore store : stores) {
                info.append("Name: ").append(Formatter.nullToDefault(store.getName(), "Unknown")).append("\n");
                info.append("Volume: ").append(Formatter.nullToDefault(store.getVolume(), "Unknown")).append("\n");
                info.append("Label: ").append(Formatter.nullToDefault(store.getLabel(), "Unknown")).append("\n");
                info.append("Description: ").append(Formatter.nullToDefault(store.getDescription(), "Unknown")).append("\n");
                info.append("Type: ").append(Formatter.nullToDefault(store.getType(), "Unknown")).append("\n");
                info.append("Mount: ").append(Formatter.nullToDefault(store.getMount(), "Unknown")).append("\n");

                long totalSpace = store.getTotalSpace() < 0 ? 0 : store.getTotalSpace();
                    long usableSpace = store.getUsableSpace() < 0 ? 0 : store.getUsableSpace();
                    long freeSpace = store.getFreeSpace() < 0 ? 0 : store.getFreeSpace();

                    info.append("Total Space: ").append(Formatter.formatBytes(totalSpace)).append("\n");
                    info.append("Usable Space: ").append(Formatter.formatBytes(usableSpace)).append("\n");
                    info.append("Free Space: ").append(Formatter.formatBytes(freeSpace)).append("\n");
            }
        }
        
    }catch (Exception e) {
            info.append("Error fetching disk/filesystem info: ").append(e.getMessage()).append("\n");
        }

        return info.toString();
    }

    // Test
    public static void main(String[] args) {
        DisksAndFilesystems util = new DisksAndFilesystems();
        System.out.println(util.getInfo());
    }
}
