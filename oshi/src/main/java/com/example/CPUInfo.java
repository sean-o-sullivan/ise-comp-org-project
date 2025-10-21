package com.example;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.lang.management.ManagementFactory;

/**
 * Validated and more robust CPU info sampler using OSHI.
 * - Safely computes system and per-core CPU % using between-ticks snapshots.
 * - Polls frequencies and shows fallback handling when OS returns zeros.
 * - Shows defensive checks for NaN/negative values returned by OSHI on some platforms.
 */
public class CPUInfo{

    private static final long DEFAULT_INTERVAL_MS = 1_000;

    public static void main(String[] args) throws InterruptedException {
        final long interval = DEFAULT_INTERVAL_MS;

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();

        // Basic static info
        ProcessorIdentifier id = processor.getProcessorIdentifier();
        System.out.println("======================");
        System.out.println("CPU Model: " + id.getName());
        System.out.println("Vendor: " + id.getVendor());
        System.out.println("Physical Cores: " + processor.getPhysicalProcessorCount());
        System.out.println("Logical Cores: " + processor.getLogicalProcessorCount());
        long maxFreq = processor.getMaxFreq();
        System.out.println("Max Frequency (Hz, reported): " + (maxFreq > 0 ? maxFreq : "unknown"));
        System.out.println("======================\n");

        // --- initial snapshots (must be taken before the interval)
        long[] prevSysTicks = processor.getSystemCpuLoadTicks();
        long[][] prevProcTicks = processor.getProcessorCpuLoadTicks();

        // You can run this in a loop; here we show a single validated sample after "interval" ms
        TimeUnit.MILLISECONDS.sleep(interval);

        // --- system CPU load between snapshots -> returns 0..1 (or sometimes NaN)
        double systemLoad = processor.getSystemCpuLoadBetweenTicks(prevSysTicks);
        systemLoad = systemLoad * 100d; // convert to percent

        // --- per-core loads between snapshots -> each element 0..1
        double[] perCoreLoads = processor.getProcessorCpuLoadBetweenTicks(prevProcTicks);
        // sanitize and convert to percent
        for (int i = 0; i < perCoreLoads.length; i++) {
            perCoreLoads[i] = perCoreLoads[i] * 100d;
        }

        // --- current frequencies (may return zeros on some OSes/drivers)
        long[] freqs = processor.getCurrentFreq();

        // --- print results (using OSHI FormatUtil for human readable frequencies)
        System.out.printf("Total System CPU Load: %.2f%%\n", systemLoad);
        System.out.println("Per-core load (%):");
        for (int i = 0; i < perCoreLoads.length; i++) {
            System.out.printf("  Core %2d: %6.2f%%\n", i, perCoreLoads[i]);
        }

        System.out.println("Current Core Frequencies:");
        // Use FormatUtil for human friendly output
        if (freqs != null && freqs.length > 0) {
            System.out.println(Arrays.toString(freqs));
System.out.println("Human-friendly:");
for (int i = 0; i < freqs.length; i++) {
    System.out.printf("  Core %2d: %s\n", i, oshi.util.FormatUtil.formatHertz(freqs[i]));
}
        } else {
            System.out.println("  (no frequency info available on this platform)");
        }

        // Example uptime fetch via OSHI Util if available
        try {
            long up = si.getOperatingSystem().getSystemUptime();   // ✅ OSHI 6.x

            System.out.println("System Uptime (sec): " + up);
        } catch (Throwable t) {
            System.out.println("System Uptime: (not available in this OSHI version)");
        }

    System.out.println("======================");
}

private static String humanReadableHz(long hz) {
        if (hz <= 0) return "unknown";
        double mhz = hz / 1_000_000.0;
        if (mhz >= 1000.0) {
            return String.format("%.2f GHz", mhz / 1000.0);
        } else {
            return String.format("%.2f MHz", mhz);
        }
    }
}
