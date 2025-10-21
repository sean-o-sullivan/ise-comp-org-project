package com.example;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class CPUInfo {
    public static void main(String[] args) throws InterruptedException {
        SystemInfo si = new SystemInfo();
        CentralProcessor cpu = si.getHardware().getProcessor();

        // Basic CPU info
        System.out.printf("Processor Identifier: %s%n", cpu.getProcessorIdentifier());
        System.out.printf("Logical Processor Count: %d%n", cpu.getLogicalProcessorCount());
        System.out.printf("Physical Processor Count: %d%n", cpu.getPhysicalProcessorCount());
        System.out.printf("Physical Package Count: %d%n", cpu.getPhysicalPackageCount());

        // Frequencies
        System.out.printf("Max Frequency (Hz): %d%n", cpu.getMaxFreq());
        System.out.printf("Current Frequencies (Hz): %s%n", Arrays.toString(cpu.getCurrentFreq()));

        // CPU usage
        long[][] oldTicks = cpu.getProcessorCpuLoadTicks();
        Thread.sleep(1000); // small delay for measurement
        double[] cpuLoad = cpu.getProcessorCpuLoadBetweenTicks(oldTicks);
        System.out.printf("CPU Load per Logical Processor: %s%n", Arrays.toString(cpuLoad));

        System.out.printf("System CPU Load: %.2f%n", cpu.getSystemCpuLoadBetweenTicks(cpu.getSystemCpuLoadTicks()));

        // Context and interrupts
        System.out.printf("Context Switches: %d%n", cpu.getContextSwitches());
        System.out.printf("Interrupts: %d%n", cpu.getInterrupts());

        // Features and caches
        System.out.printf("CPU Feature Flags: %s%n", cpu.getFeatureFlags());
        System.out.printf("Processor Caches: %s%n", cpu.getProcessorCaches());

        // System load average
        System.out.printf("System Load Average (1,5,15 min): %s%n", Arrays.toString(cpu.getSystemLoadAverage(3)));

        // Logical and physical processors lists (optional, verbose)
        System.out.printf("Logical Processors: %s%n", cpu.getLogicalProcessors());
        System.out.printf("Physical Processors: %s%n", cpu.getPhysicalProcessors());
    }
}
