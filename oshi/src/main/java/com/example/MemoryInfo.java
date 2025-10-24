package com.example;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;
import java.util.List;

public class MemoryInfo {

    public String getMemoryDetails() {
        StringBuilder info = new StringBuilder();

        try {
            SystemInfo si = new SystemInfo();
            GlobalMemory memory = si.getHardware().getMemory();

            info.append("MEMORY INFORMATION\n");
            info.append("======================\n");

            // Total and available physical memory
            info.append(String.format("Total Physical Memory: %d bytes%n", memory.getTotal()));
            info.append(String.format("Available Physical Memory: %d bytes%n", memory.getAvailable()));

            // Virtual memory (swap) info
            VirtualMemory virtualMem = memory.getVirtualMemory();
            info.append(String.format("Total Swap Memory: %d bytes%n", virtualMem.getSwapTotal()));
            info.append(String.format("Used Swap Memory: %d bytes%n", virtualMem.getSwapUsed()));
            info.append(String.format("Pages Swapped In: %d%n", virtualMem.getSwapPagesIn()));
            info.append(String.format("Pages Swapped Out: %d%n", virtualMem.getSwapPagesOut()));

            // Detailed information about each physical DIMM module
            List<PhysicalMemory> dimms = memory.getPhysicalMemory();
            if (dimms != null && !dimms.isEmpty()) {
                info.append("\nPhysical Memory Modules (DIMMs):\n");
                for (int i = 0; i < dimms.size(); i++) {
                    PhysicalMemory dimm = dimms.get(i);
                    info.append(String.format("=== DIMM %d ===%n", i));
                    info.append(String.format("Capacity: %d bytes%n", dimm.getCapacity()));
                    info.append(String.format("Clock Speed: %d Hz%n", dimm.getClockSpeed()));
                    info.append(String.format("Manufacturer: %s%n", dimm.getManufacturer()));
                    info.append(String.format("Serial Number: %s%n", dimm.getSerialNumber()));
                    info.append(String.format("Bank Label: %s%n", dimm.getBankLabel()));
                    info.append(String.format("Memory Type: %s%n", dimm.getMemoryType()));
                }
            } else {
                info.append("No physical memory module information available.\n");
            }

            info.append("======================\n");

        } catch (Exception e) {
            info.append("Error fetching memory information: ").append(e.getMessage()).append("\n");
        }

        return info.toString();
    }

    // Optional main method for testing
    public static void main(String[] args) {
        MemoryInfo memoryInfo = new MemoryInfo();
        System.out.println(memoryInfo.getMemoryDetails());
    }
}
