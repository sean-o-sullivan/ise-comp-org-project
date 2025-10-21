import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;

public class SensorsInfo {

    public static void printSensorsInfo() {
        try {
            SystemInfo systemInfo = new SystemInfo();
            HardwareAbstractionLayer hardware = systemInfo.getHardware();
            Sensors sensors = hardware.getSensors();

            System.out.println("=== Sensors Information ===");

            double cpuTemp = sensors.getCpuTemperature();
            double cpuVoltage = sensors.getCpuVoltage();
            double[] fanSpeeds = sensors.getFanSpeeds();

            System.out.printf("CPU Temperature: %.1f °C%n", cpuTemp);
            System.out.printf("CPU Voltage: %.2f V%n", cpuVoltage);

            if (fanSpeeds.length > 0) {
                for (int i = 0; i < fanSpeeds.length; i++) {
                    System.out.printf("Fan %d Speed: %.0f RPM%n", i + 1, fanSpeeds[i]);
                }
            } else {
                System.out.println("No fan data available.");
            }

        } catch (Exception e) {
            System.out.println("Error retrieving sensor data: " + e.getMessage());
        }
    }
}
