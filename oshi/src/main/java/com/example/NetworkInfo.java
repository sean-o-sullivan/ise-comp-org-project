
package com.example;

import java.util.List;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;


public class NetworkInfo {
    public String getNetworkDetails() {
        StringBuilder info = new StringBuilder();
        try {
            SystemInfo si = new SystemInfo();
            List<NetworkIF> networkIFs = si.getHardware().getNetworkIFs();

            info.append("==============================\n");
            info.append("Network Interfaces\n");
            info.append("==============================\n\n");

            if (networkIFs == null || networkIFs.isEmpty()) {
                info.append("No network interfaces found.\n");
            } else {
                for (NetworkIF net : networkIFs) {
                info.append("Interface Name: ").append(net.getName()).append("\n");
                info.append("Interface Display Name: ").append(net.getDisplayName()).append("\n");
                info.append("Interface Alias (RFC 2863): ").append(net.getIfAlias()).append("\n");
                info.append("Interface Index: ").append(net.getIndex()).append("\n");
                info.append("MAC Address: ").append(net.getMacaddr()).append("\n");
                info.append("IPv4 Addresses: ").append(net.getIPv4addr()).append("\n");
                info.append("IPv4 Subnet Masks: ").append(net.getSubnetMasks()).append("\n");
                info.append("IPv6 Addresses: ").append(net.getIPv6addr()).append("\n");
                info.append("IPv6 Prefix Lengths: ").append(net.getPrefixLengths()).append("\n");
                info.append("Interface Operational Status (RFC 2863): ").append(net.getIfOperStatus()).append("\n");
                info.append("Interface Type (NDIS): ").append(net.getIfType()).append("\n");
                info.append("NDIS Physical Medium Type: ").append(net.getNdisPhysicalMediumType()).append("\n");
                info.append("Maximum Transmission Unit (MTU): ").append(net.getMTU()).append("\n");
                info.append("Interface Speed (bps): ").append(net.getSpeed()).append("\n");
                info.append("Bytes Received: ").append(net.getBytesRecv()).append("\n");
                info.append("Bytes Sent: ").append(net.getBytesSent()).append("\n");
                info.append("Packets Received: ").append(net.getPacketsRecv()).append("\n");
                info.append("Packets Sent: ").append(net.getPacketsSent()).append("\n");
                info.append("Inbound Packet Drops: ").append(net.getInDrops()).append("\n");
                info.append("Inbound Errors: ").append(net.getInDrops()).append("\n");
                info.append("Outbound Errors: ").append(net.getOutErrors()).append("\n");
                info.append("Packet Collisions: ").append(net.getCollisions()).append("\n");
                info.append("Timestamp (ms since boot): ").append(net.getTimeStamp()).append("\n");
                info.append("Connector Present: ").append(net.isConnectorPresent()).append("\n");
                info.append("Known VM MAC Address: ").append(net.isKnownVmMacAddr()).append("\n");
                info.append("Java NetworkInterface Object: ").append(net.queryNetworkInterface()).append("\n");
                info.append("Attributes Updated: ").append(net.updateAttributes()).append("\n");
                info.append("======================\n\n");
        }
    }
} catch (Exception e) {
            info.append("Error fetching network information: ").append(e.getMessage()).append("\n");
        }

        return info.toString();
    }

    // Test method
    public static void main(String[] args) {
        NetworkInfo ni = new NetworkInfo();
        System.out.println(ni.getNetworkDetails());
    }
}
