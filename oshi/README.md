# OSHI Network Information

A JavaFX application that displays comprehensive system information using the OSHI library.

## Requirements

- **Java**: JDK 17 or higher (tested with JDK 17, 21, and 25)
- **Maven**: 3.6+ 

## How to Run

1. Navigate to the `oshi` directory:
   ```bash
   cd oshi
   ```

2. Compile and run the application:
   ```bash
   mvn clean javafx:run
   ```

## Building a JAR

To create an executable JAR file:

```bash
mvn clean package
```

This will create a fat JAR in the `target` directory that includes all dependencies.

## Project Structure

```
oshi/
├── src/
│   ├── main/
│   │   ├── java/com/example/     # Java source files
│   │   └── resources/com/example/ # FXML, CSS, and images
│   └── test/                      # Test files
├── pom.xml                        # Maven configuration
└── README.md                      # This file
```

## Compatibility Note

This project is compiled for **Java 17** but will run on any Java version 17 or higher (including Java 21, 25, etc.). Maven will automatically compile to the correct target version specified in `pom.xml`.

## Troubleshooting

### Issue: "release version 21 not supported"
**Solution**: Make sure your Maven is using a compatible JDK. Check with:
```bash
mvn -version
```

If you see Java 17 or higher, you're good to go!

### Issue: Application won't start
**Solution**: Make sure you're running the command from the `oshi` directory, not the root project directory.

## Features

- CPU Information
- Memory Information
- Disk and Filesystem Information
- Network Information
- Graphics Information
- Battery Information
- Operating System Information
- USB Device Information
- Sensor Information
