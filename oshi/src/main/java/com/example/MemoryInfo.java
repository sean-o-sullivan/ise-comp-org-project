package com.example;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;

import java.util.List;

public class MemoryInfo {

    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        GlobalMemory memory = si.getHardware().getMemory();

        // Total and available physical memory
        System.out.println(String.format("Total Physical Memory: %d bytes", memory.getTotal()));
        System.out.println(String.format("Available Physical Memory: %d bytes", memory.getAvailable()));

        // Virtual memory (swap) info
        VirtualMemory virtualMem = memory.getVirtualMemory();
        System.out.println(String.format("Total Swap Memory: %d bytes", virtualMem.getSwapTotal()));
        System.out.println(String.format("Used Swap Memory: %d bytes", virtualMem.getSwapUsed()));
        System.out.println(String.format("Pages Swapped In: %d", virtualMem.getSwapPagesIn()));
        System.out.println(String.format("Pages Swapped Out: %d", virtualMem.getSwapPagesOut()));

        // Detailed information about each physical DIMM module
        List<PhysicalMemory> dimms = memory.getPhysicalMemory();
        for (int i = 0; i < dimms.size(); i++) {
            PhysicalMemory dimm = dimms.get(i);
            System.out.println(String.format("=== DIMM %d ===", i));
            System.out.println(String.format("Capacity: %d bytes", dimm.getCapacity()));
            System.out.println(String.format("Clock Speed: %d Hz", dimm.getClockSpeed()));
            System.out.println(String.format("Manufacturer: %s", dimm.getManufacturer()));
            System.out.println(String.format("Serial Number: %s", dimm.getSerialNumber()));
            System.out.println(String.format("Bank Label: %s", dimm.getBankLabel()));
            System.out.println(String.format("Memory Type: %s", dimm.getMemoryType()));
        }
    }
}
