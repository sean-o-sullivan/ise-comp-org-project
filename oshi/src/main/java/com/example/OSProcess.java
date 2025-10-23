package com.example;

import com.example.helpers.Formatter;
import com.example.helpers.Table;

public class OSProcess {

    private final oshi.software.os.OSProcess oshiProcess;

    // Internal constructor for when we already have the OSHI process
    public OSProcess(oshi.software.os.OSProcess oshiProcess) {
        this.oshiProcess = oshiProcess;
    }

    // Check if the process was found and is valid
    public boolean isValid() {
        return oshiProcess != null;
    }

    // Get process ID
    public int getProcessID() {
        try {
            return oshiProcess.getProcessID();
        } catch (Throwable t) {
            return -1;
        }
    }

    // Get parent process ID
    public int getParentProcessID() {
        try {
            return oshiProcess.getParentProcessID();
        } catch (Throwable t) {
            return -1;
        }
    }

    // Get process name
    public String getName() {
        if (oshiProcess == null) {
            return "Error Fetching";
        }
        try {
            String name = oshiProcess.getName();
            if (name == null || name.isBlank()) {
                return "Not Available";
            }
            return name;
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get process executable path
    public String getPath() {
        if (oshiProcess == null) {
            return "Error Fetching";
        }
        try {
            String path = oshiProcess.getPath();
            if (path == null || path.isBlank()) {
                return "Not Available";
            }
            return path;
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get command used to start the process
    public String getCommandLine() {
        if (oshiProcess == null) {
            return "Error Fetching";
        }
        try {
            String cmdLine = oshiProcess.getCommandLine();
            if (cmdLine == null || cmdLine.isBlank()) {
                return "Not Available";
            }
            return cmdLine;
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get kernel mode CPU time (Time spent executing system calls)
    public long getKernelTime() {
        try {
            return oshiProcess.getKernelTime();
        } catch (Throwable t) {
            return -1;
        }
    }

    // Get user time (Time spent executing actual code)
    public long getUserTime() {
        try {
            return oshiProcess.getUserTime();
        } catch (Throwable t) {
            return -1;
        }
    }

    // Get user that owns the process
    public String getUser() {
        if (oshiProcess == null) {
            return "Error Fetching";
        }
        try {
            String user = oshiProcess.getUser();
            if (user == null || user.isBlank()) {
                return "Not Available";
            }
            return user;
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // get user group that owns the process
    public String getGroup() {
        if (oshiProcess == null) {
            return "Error Fetching";
        }
        try {
            String group = oshiProcess.getGroup();
            if (group == null || group.isBlank()) {
                return "Not Available";
            }
            return group;
        } catch (Throwable t) {
            return "Error Fetching";
        }
    }

    // Get formatted start time
    public String getStartTimeFormatted() {
        long startTime;
        try {
            startTime = oshiProcess.getStartTime();
        } catch (Throwable t) {
            startTime = -1;
        }
        if (startTime <= 0) {
            return "Not Available";
        }
        return Formatter.formatEpochMilliseconds(startTime);
    }

    // Get formatted uptime
    public String getUpTimeFormatted() {
        long uptime;
        try {
            uptime = oshiProcess.getUpTime();
        } catch (Throwable t) {
            uptime = -1;
        }
        if (uptime < 0) {
            return "Not Available";
        }
        return Formatter.formatDuration(uptime);
    }

    // This is the actual physical memory used, not virtual memory
    public String getResidentSetSizeFormatted() {
        long rss;
        try {
            rss = oshiProcess.getResidentSetSize();
        } catch (Throwable t) {
            rss = -1;
        }
        if (rss < 0) {
            return "Not Available";
        }
        if (rss < 1024) {
            return rss + " B";
        }
        int exp = (int) (Math.log(rss) / Math.log(1024));
        String[] units = { "B", "KB", "MB", "GB", "TB" };
        return String.format("%.2f %s", rss / Math.pow(1024, exp), units[exp]);
    }

    // Get virtual size
    public String getVirtualSizeFormatted() {
        long vs;
        try {
            vs = oshiProcess.getVirtualSize();
        } catch (Throwable t) {
            vs = -1;
        }
        if (vs < 0) {
            return "Not Available";
        }
        if (vs < 1024) {
            return vs + " B";
        }
        int exp = (int) (Math.log(vs) / Math.log(1024));
        String[] units = { "B", "KB", "MB", "GB", "TB" };
        return String.format("%.2f %s", vs / Math.pow(1024, exp), units[exp]);
    }

    // Get CPU percentage
    public String getProcessCpuLoadCumulativeFormatted() {
        double cpuLoad;
        try {
            cpuLoad = oshiProcess.getProcessCpuLoadCumulative();
        } catch (Throwable t) {
            cpuLoad = -1.0;
        }
        if (cpuLoad < 0) {
            return "Not Available";
        }
        return String.format("%.2f%%", cpuLoad * 100);
    }

    // Print all process info as text
    public void printProcessInfo() {
        if (!isValid()) {
            System.out.println("    Process Information");
            System.out.println("    ------------------");
            System.out.println("    Error: Invalid or not found process.");
            return;
        }

        System.out.println("    Process Information");
        System.out.println("    ------------------");
        System.out.println("    Process ID: " + getProcessID() + ".");
        System.out.println("    Parent PID: " + getParentProcessID() + ".");
        System.out.println("    Name: " + getName() + ".");
        System.out.println("    Path: " + getPath() + ".");
        System.out.println();

        System.out.println("    Command & User");
        System.out.println("    --------------");
        // System.out.println(" Command line: " + getCommandLine() + "."); Commented out
        // as can be very long
        System.out.println("    User: " + getUser() + ".");
        System.out.println("    Group: " + getGroup() + ".");
        System.out.println();

        System.out.println("    Memory Usage");
        System.out.println("    ------------");
        System.out.println("    Resident set size: " + getResidentSetSizeFormatted() + ".");
        System.out.println("    Virtual size: " + getVirtualSizeFormatted() + ".");
        System.out.println();

        System.out.println("    CPU & Time");
        System.out.println("    ----------");
        System.out.println("    CPU load: " + getProcessCpuLoadCumulativeFormatted() + ".");
        System.out.println("    Kernel time: " + getKernelTime() + " ms.");
        System.out.println("    User time: " + getUserTime() + " ms.");
        System.out.println();

        System.out.println("    Timing");
        System.out.println("    ------");
        System.out.println("    Start time: " + getStartTimeFormatted() + ".");
        System.out.println("    Uptime: " + getUpTimeFormatted() + ".");
    }

    // Print process info as table
    public void printProcessTable() {
        if (!isValid()) {
            System.out.println("Error: Invalid or not found process.");
            return;
        }

        Table t = new Table();
        t.addRow("Metric", "Value");

        t.addRow("Process ID", String.valueOf(getProcessID()));
        t.addRow("Parent PID", String.valueOf(getParentProcessID()));
        t.addRow("Name", getName());
        t.addRow("Path", getPath());

        t.addRow("User", getUser());
        t.addRow("Group", getGroup());

        t.addRow("RSS (formatted)", getResidentSetSizeFormatted());
        t.addRow("Virtual size (formatted)", getVirtualSizeFormatted());
        t.addRow("CPU load", getProcessCpuLoadCumulativeFormatted());
        t.addRow("Kernel time (ms)", String.valueOf(getKernelTime()));
        t.addRow("User time (ms)", String.valueOf(getUserTime()));

        t.addRow("Start time", getStartTimeFormatted());
        t.addRow("Uptime", getUpTimeFormatted());

        t.print();
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "OSProcess[Invalid Process]";
        }
        return String.format("OSProcess[PID=%d, Name=%s, User=%s]",
                getProcessID(), getName(), getUser());
    }
}