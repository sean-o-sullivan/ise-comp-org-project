package com.example;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.helpers.Formatter;
import com.example.helpers.Table;

import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSProcess;
import oshi.software.os.OSSession;
import oshi.software.os.OperatingSystem;

public class OSInfo {

    private final SystemInfo systemInfo; // Main OSHI entry point for system information
    private final OperatingSystem os; // Handle to OS-specific data

    public OSInfo() {
        this.systemInfo = new SystemInfo(); // Initialize OSHI system info
        this.os = systemInfo.getOperatingSystem(); // Get OS interface for queries
    }

    // Get OS family
    public String getFamily() {
        try {
            String v = os.getFamily();
            if (v == null || v.isBlank()) {
                return "Not Available";
            }
            return v;
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get OS manufacturer
    public String getManufacturer() {
        try {
            String v = os.getManufacturer();
            if (v == null || v.isBlank()) {
                return "Not Available";
            }
            return v;
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get OS version
    public String getVersion() {
        try {
            String version = os.getVersionInfo().getVersion();
            if (version == null || version.isBlank()) {
                return "Not Available";
            }
            return version;
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get OS build number
    public String getBuildNumber() {
        try {
            String build = os.getVersionInfo().getBuildNumber();
            if (build == null || build.isBlank()) {
                return "Not Available";
            }
            return build;
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get OS code name
    public String getCodeName() {
        try {
            String codeName = os.getVersionInfo().getCodeName();
            if (codeName == null || codeName.isBlank()) {
                return "Not Available";
            }
            return codeName;
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get number of processes currently running
    public String getProcessCount() {
        try {
            return String.valueOf(os.getProcessCount());
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get total number of threads across all processes
    public String getThreadCount() {
        try {
            return String.valueOf(os.getThreadCount());
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // 32-bit or 64-bit
    public String getBitness() {
        try {
            int b = os.getBitness();
            if (b == 0) {
                return "Not Available";
            }
            return String.valueOf(b);
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get when the system was last booted (as seconds since epoch)
    public String getSystemBootTimeEpochSeconds() {
        try {
            long v = os.getSystemBootTime();
            if (v <= 0) {
                return "Not Available";
            }
            return String.valueOf(v);
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get how long the system has been running (in seconds)
    public String getSystemUptimeSeconds() {
        try {
            long v = os.getSystemUptime();
            if (v < 0) {
                return "Not Available";
            }
            return String.valueOf(v);
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get list of processes sorted by CPU usage (most intensive first)
    public List<OSProcess> getProcessesSafe(int limit) {
        try {
            List<OSProcess> list = os.getProcesses(null, OperatingSystem.ProcessSorting.CPU_DESC, limit);
            if (list == null) {
                return Collections.emptyList();
            }
            return list;
        } catch (Throwable t) {
            return Collections.emptyList();
        }
    }

    // Get single process by its Process ID
    public OSProcess getProcessSafe(int pid) {
        try {
            OSProcess p = os.getProcess(pid);
            if (p == null) {
                return null;
            }
            return p;
        } catch (Throwable t) {
            return null;
        }
    }

    // Get list of user sessions
    public List<OSSession> getSessionsSafe() {
        try {
            List<OSSession> list = os.getSessions();
            if (list == null) {
                return Collections.emptyList();
            }
            return list;
        } catch (Throwable t) {
            return Collections.emptyList();
        }
    }

    // Get file system for accessing disk/volume info
    public FileSystem getFileSystemSafe() {
        try {
            FileSystem fs = os.getFileSystem();
            if (fs == null) {
                return null;
            }
            return fs;
        } catch (Throwable t) {
            return null;
        }
    }

    // Get number of disks/volumes on FS
    public int getFileSystemStoreCount() {
        FileSystem fs = getFileSystemSafe();
        if (fs == null) {
            return -1;
        }
        try {
            return fs.getFileStores().size();
        } catch (Throwable t) {
            return -1;
        }
    }

    // String with error handling
    private String getFileSystemStoreCountString() {
        int count = getFileSystemStoreCount();
        if (count < 0) {
            return "Error Fetching";
        }
        return String.valueOf(count);
    }

    // Print all OS info
    public void printOsInfo() {
        System.out.println("Operating System");
        System.out.println("----------------");
        System.out.println("Family: " + getFamily() + ".");
        System.out.println("Manufacturer: " + getManufacturer() + ".");

        System.out.println("Version: " + getVersion() + ".");
        System.out.println("Build number: " + getBuildNumber() + ".");
        System.out.println("Code name: " + getCodeName() + ".");
        System.out.println();

        System.out.println("Architecture & Time");
        System.out.println("-------------------");
        System.out.println("Bitness: " + getBitness() + ".");
        String bootHuman = Formatter.formatEpochSeconds(getSystemBootTimeEpochSeconds());
        System.out.println("System boot time: " + bootHuman + ".");

        String uptimeHuman = Formatter.formatDuration(getSystemUptimeSeconds());
        String uptimeRaw = getSystemUptimeSeconds();
        System.out.println("System uptime: " + uptimeHuman + " (" + uptimeRaw + " seconds).");
        System.out.println();

        System.out.println("Workload");
        System.out.println("--------");
        System.out.println("Process count: " + getProcessCount() + ".");
        System.out.println("Thread count: " + getThreadCount() + ".");
        System.out.println();

        List<OSSession> sessions = getSessionsSafe();
        System.out.println("Logged-in Sessions");
        System.out.println("------------------");
        if (sessions.isEmpty()) {
            System.out.println("Sessions: None detected.");
        } else {
            System.out.println("Session count: " + sessions.size() + ".");
            for (OSSession s : sessions) {
                String login = Formatter.formatEpochMilliseconds(s.getLoginTime());
                System.out.println("User: " + Formatter.nullToDefault(s.getUserName(), "Unknown") +
                        " @ " + Formatter.nullToDefault(s.getHost(), "local") +
                        " (Login: " + login + ").");
            }
        }
        System.out.println();

        System.out.println("File System");
        System.out.println("-----------");
        String storeCount = getFileSystemStoreCountString();
        System.out.println("Mounted file stores: " + storeCount + ".");
        System.out.println();

        System.out.println("Top 3 Processes (by CPU usage)");
        System.out.println("==============================");
        List<oshi.software.os.OSProcess> topProcesses = getProcessesSafe(3);
        if (topProcesses.isEmpty()) {
            System.out.println("No processes found.");
        } else {
            for (int i = 0; i < topProcesses.size(); i++) {
                System.out.println("Process #" + (i + 1));
                System.out.println("-----------");
                com.example.OSProcess process = new com.example.OSProcess(topProcesses.get(i));
                process.printProcessInfo();
                if (i < topProcesses.size() - 1) {
                    System.out.println();
                }
            }
        }
    }

    // Print OS info in table
    public void printTable() {
        Table t = new Table();
        t.addRow("Metric", "Value");

        t.addRow("OS family", getFamily());
        t.addRow("Manufacturer", getManufacturer());

        t.addRow("Version", getVersion());
        t.addRow("Build number", getBuildNumber());
        t.addRow("Code name", getCodeName());

        t.addRow("Bitness", getBitness());
        t.addRow("Boot time", Formatter.formatEpochSeconds(getSystemBootTimeEpochSeconds()));
        t.addRow("Uptime (human)", Formatter.formatDuration(getSystemUptimeSeconds()));
        t.addRow("Uptime (seconds)", getSystemUptimeSeconds());

        t.addRow("Process count", getProcessCount());
        t.addRow("Thread count", getThreadCount());

        List<OSSession> sessions = getSessionsSafe();
        t.addRow("Session count", String.valueOf(sessions.size()));
        String usersSummary = sessions.stream()
                .map(OSSession::getUserName)
                .filter(s -> s != null && !s.isBlank())
                .distinct()
                .collect(Collectors.joining(", "));
        if (usersSummary.isBlank()) {
            t.addRow("Users (distinct)", "—");
        } else {
            t.addRow("Users (distinct)", usersSummary);
        }

        String storeCount = getFileSystemStoreCountString();
        t.addRow("Mounted file stores", storeCount);

        t.print();

        System.out.println();
        System.out.println("Top 3 Processes (by CPU usage)");
        System.out.println("==============================");

        List<oshi.software.os.OSProcess> topProcesses = getProcessesSafe(3);
        if (topProcesses.isEmpty()) {
            System.out.println("No processes found.");
        } else {
            for (int i = 0; i < topProcesses.size(); i++) {
                System.out.println();
                System.out.println("Process #" + (i + 1) + ":");
                com.example.OSProcess process = new com.example.OSProcess(topProcesses.get(i));
                process.printProcessTable();
            }
        }
    }

    // Test
    public static void main(String[] args) {
        OSInfo util = new OSInfo();

        util.printOsInfo();

        System.out.println();

        util.printTable();
    }
}
