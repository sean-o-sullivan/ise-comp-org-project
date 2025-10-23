package com.example.helpers;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Formatter {

    /*
     * This class assumes String values either contain the correct data,
     * "Not Available", or "Error Fetching" to indicate problems obtaining the data.
     */

    // Standard date-time formatter consistency
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    // Return default value if string is null or empty
    public static String nullToDefault(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }

    // Convert epoch seconds string to date string
    public static String formatEpochSeconds(String epochSecondsString) {
        if ("Not Available".equals(epochSecondsString) || "Error Fetching".equals(epochSecondsString)) {
            return epochSecondsString;
        }
        try {
            long epochSeconds = Long.parseLong(epochSecondsString);
            return formatEpochSeconds(epochSeconds);
        } catch (NumberFormatException e) {
            return "Error Formatting";
        }
    }

    // Convert epoch seconds to date string
    public static String formatEpochSeconds(long epochSeconds) {
        if (epochSeconds <= 0) {
            return "Unknown";
        }
        try {
            return dateTimeFormatter.format(Instant.ofEpochSecond(epochSeconds));
        } catch (Exception e) {
            return "Unknown";
        }
    }

    // Convert epoch milliseconds to date string (for login times, etc.)
    public static String formatEpochMilliseconds(long epochMilliseconds) {
        if (epochMilliseconds <= 0) {
            return "Unknown";
        }
        try {
            return dateTimeFormatter.format(Instant.ofEpochMilli(epochMilliseconds));
        } catch (Exception e) {
            return "Unknown";
        }
    }

    // Overloaded method that takes in string and parses to long
    public static String formatDuration(String secondsString) {
        if ("Not Available".equals(secondsString) || "Error Fetching".equals(secondsString)) {
            return secondsString;
        }
        try {
            long seconds = Long.parseLong(secondsString);
            return formatDuration(seconds);
        } catch (NumberFormatException e) {
            return "Error Formatting";
        }
    }

    // Format seconds into a duration
    public static String formatDuration(long seconds) {
        if (seconds < 0) {
            return "Unknown";
        }

        Duration d = Duration.ofSeconds(seconds);
        long days = d.toDays();
        long hours = d.minusDays(days).toHours();
        long minutes = d.minusDays(days).minusHours(hours).toMinutes();
        long secs = d.minusDays(days).minusHours(hours).minusMinutes(minutes).getSeconds();

        String result = "";
        if (days > 0) {
            result += days + "d ";
        }
        result += String.format("%02d:%02d:%02d", hours, minutes, secs);
        return result.trim();
    }

    // Format bytes
    public static String formatBytes(long bytes) {
        if (bytes < 0) {
            return "Not Available";
        }
        int exp = (int) (Math.log(bytes) / Math.log(1024)); // log-b-(x) = log-k-(x) / log-k-(b) :: exp =
                                                            // log-1024-(bytes)
        String[] units = { "B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB" };
        return String.format("%.2f %s", bytes / Math.pow(1024, exp), units[exp]);
    }

    // Format Hertz, same logic as bytes
    public static String formatHertz(long hz) {
        if (hz < 0)
            return "Not Available";
        if (hz < 1000)
            return hz + " Hz";
        int exp = (int) (Math.log(hz) / Math.log(1000));
        String[] units = { "Hz", "kHz", "MHz", "GHz", "THz" };
        return String.format("%.2f %s", hz / Math.pow(1000, exp), units[exp]);
    }
}