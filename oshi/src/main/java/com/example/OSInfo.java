package com.example;

import java.util.Collections;
import java.util.List;

import com.example.helpers.Formatter;

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
    public String buildOsInfo() {
        StringBuilder info = new StringBuilder();

        info.append("==============================\n");
        info.append("OPERATING SYSTEM\n");
        info.append("==============================\n\n");

        try{
            info.append("Family: ").append(getFamily()).append(".\n");
            } catch (Throwable t) {
            }

        try { 
            info.append("Manufacturer: ").append(getManufacturer()).append(".\n"); 
            } catch(Throwable t) {
            }
        
        try {
             info.append("Version: ").append(getVersion()).append(".\n"); 
            } catch(Throwable t) {
            }

        try { 
            info.append("Build number: ").append(getBuildNumber()).append(".\n"); 
            } catch(Throwable t) {
            }

        try { 
            info.append("Code name: ").append(getCodeName()).append(".\n\n"); 
            } catch(Throwable t) {
            }

        
        
        info.append("Architecture & Time\n");
        info.append("-------------------\n");

        try {
            info.append("Bitness: ").append(getBitness()).append(".\n");
            } catch (Throwable e) {
            }

        try { info.append("System boot time: ").append(Formatter.formatEpochSeconds(getSystemBootTimeEpochSeconds())).append(".\n"); 
            } catch(Throwable t) {
            }
        
        try { 
            info.append("System uptime: ").append(Formatter.formatDuration(getSystemUptimeSeconds())).append(" (").append(getSystemUptimeSeconds()).append(" seconds).\n"); 
            } catch(Throwable t) {
            }
        info.append("\n");

        info.append("Workload\n");
        info.append("--------\n");

        try { 
            info.append("Process count: ").append(getProcessCount()).append(".\n"); 
            } catch(Throwable t) {
            }

        try { 
            info.append("Thread count: ").append(getThreadCount()).append(".\n"); 
            } catch(Throwable t) {
            }
        info.append("\n");

        List<OSSession> sessions = getSessionsSafe();
        info.append("Logged-in Sessions\n");
        info.append("------------------\n");
        if (sessions.isEmpty()) {
            info.append("Sessions: None detected.");
        } else {
            try { info.append("Session count: ").append(sessions.size()).append(".\n"); 
        } catch(Throwable t) {}

            for (OSSession s : sessions) {
                try {
                    String login = Formatter.formatEpochMilliseconds(s.getLoginTime());
                    info.append("User: ").append(Formatter.nullToDefault(s.getUserName(), "Unknown")).append(" @ ").append(Formatter.nullToDefault(s.getHost(), "local")).append(" (Login: ").append(login).append(").\n");
                } catch (Throwable t) {
                }
                          
            }
        }
        info.append("\n");



        info.append("File System\n");
        info.append("-----------\n");
        
        try {
            info.append("Mounted file stores: ").append(getFileSystemStoreCountString()).append(".\n"); 
            } catch(Throwable t) {
            }
        info.append("\n");

        info.append("Top 3 Processes (by CPU usage)\n");
        info.append("==============================\n");
        List<oshi.software.os.OSProcess> topProcesses = getProcessesSafe(3);
        if (topProcesses.isEmpty()) {
            info.append("No processes found.\n");
        } else {
            for (int i = 0; i < topProcesses.size(); i++) {
                try {
                    info.append("Process #").append(i + 1).append("\n");
                    info.append("-----------\n");
                    com.example.OSProcess process = new com.example.OSProcess(topProcesses.get(i));
                    info.append(process.getProcessInfo()).append("\n\n");
                } catch (Throwable t) {}
            }
        }
        return info.toString();
    }
    
    // Test
    public static void main(String[] args) {
        OSInfo util = new OSInfo();
        System.out.println(util.buildOsInfo());
    }
}
