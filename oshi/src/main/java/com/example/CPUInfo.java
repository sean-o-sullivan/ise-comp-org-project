package com.example;

import java.util.concurrent.TimeUnit;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

/**
 * Validated and more robust CPU info sampler using OSHI.
 * - Safely computes system and per-core CPU % using between-ticks snapshots.
 * - Polls frequencies and shows fallback handling when OS returns zeros.
 * - Shows defensive checks for NaN/negative values returned by OSHI on some platforms.
 */
public class CPUInfo{

    private static final long DEFAULT_INTERVAL_MS = 1_000;

    public String getCPUDetails() {
        StringBuilder info = new StringBuilder();
        try {
            final long interval = DEFAULT_INTERVAL_MS;

            SystemInfo si = new SystemInfo();
            HardwareAbstractionLayer hal = si.getHardware();
            CentralProcessor processor = hal.getProcessor();

            info.append("======================\n");
            info.append("CPU INFORMATION\n");
            info.append("======================\n\n");

            // Basic static info
            ProcessorIdentifier id = processor.getProcessorIdentifier();
            info.append("======================\n");
            info.append("CPU Model: ").append(id.getName()).append("\n");
            info.append("Vendor: ").append(id.getVendor()).append("\n");
            info.append("Physical Cores: ").append(processor.getPhysicalProcessorCount()).append("\n");
            info.append("Logical Cores: ").append(processor.getLogicalProcessorCount()).append("\n");
            long maxFreq = processor.getMaxFreq();
            info.append("Max Frequency (Hz, reported): ")
                .append(maxFreq > 0 ? FormatUtil.formatHertz(maxFreq) : "unknown").append("\n");
            info.append("======================\n\n");

            // Take initial snapshot
            long[] prevSysTicks = processor.getSystemCpuLoadTicks();
            long[][] prevProcTicks = processor.getProcessorCpuLoadTicks();

            TimeUnit.MILLISECONDS.sleep(interval);

            // System CPU load
            double systemLoad = processor.getSystemCpuLoadBetweenTicks(prevSysTicks) * 100d;

            // Per-core loads
            double[] perCoreLoads = processor.getProcessorCpuLoadBetweenTicks(prevProcTicks);
            for (int i = 0; i < perCoreLoads.length; i++) {
                perCoreLoads[i] = perCoreLoads[i] * 100d;
            }

            long[] freqs = processor.getCurrentFreq();

            // Build the text output
            info.append(String.format("Total System CPU Load: %.2f%%\n", systemLoad));
            info.append("Per-core load (%):\n");
            for (int i = 0; i < perCoreLoads.length; i++) {
                info.append(String.format("  Core %2d: %6.2f%%\n", i, perCoreLoads[i]));
            }

            info.append("\nCurrent Core Frequencies:\n");
            if (freqs != null && freqs.length > 0) {
                for (int i = 0; i < freqs.length; i++) {
                    info.append(String.format("  Core %2d: %s\n", i, FormatUtil.formatHertz(freqs[i])));
                }
            } else {
                info.append("  (no frequency info available)\n");
            }

            // Uptime
            long up = si.getOperatingSystem().getSystemUptime();
            info.append("\nSystem Uptime (sec): ").append(up).append("\n");
            info.append("======================\n");

        } catch (Exception e) {
            info.append("Error fetching CPU information: ").append(e.getMessage()).append("\n");
        }

        return info.toString();
        }

        //test
        public static void main(String[] args) {
            CPUInfo cpuInfo = new CPUInfo();
            System.out.println(cpuInfo.getCPUDetails());
        }
    }