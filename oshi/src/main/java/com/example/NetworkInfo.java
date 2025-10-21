
package com.example;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;
import java.util.Arrays;

public class NetworkInfo {
    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        for (NetworkIF networkIF : si.getHardware().getNetworkIFs()) {
            System.out.println("======================");
            System.out.println("Interface Name: " + networkIF.getName());
            System.out.println("Interface Display Name: " + networkIF.getDisplayName());
            System.out.println("Interface Alias (RFC 2863): " + networkIF.getIfAlias());
            System.out.println("Interface Index: " + networkIF.getIndex());
            System.out.println("MAC Address: " + networkIF.getMacaddr());
            System.out.println("IPv4 Addresses: " + Arrays.toString(networkIF.getIPv4addr()));
            System.out.println("IPv4 Subnet Masks: " + Arrays.toString(networkIF.getSubnetMasks()));
            System.out.println("IPv6 Addresses: " + Arrays.toString(networkIF.getIPv6addr()));
            System.out.println("IPv6 Prefix Lengths: " + Arrays.toString(networkIF.getPrefixLengths()));
            System.out.println("Interface Operational Status (RFC 2863): " + networkIF.getIfOperStatus());
            System.out.println("Interface Type (NDIS): " + networkIF.getIfType());
            System.out.println("NDIS Physical Medium Type: " + networkIF.getNdisPhysicalMediumType());
            System.out.println("Maximum Transmission Unit (MTU): " + networkIF.getMTU());
            System.out.println("Interface Speed (bps): " + networkIF.getSpeed());
            System.out.println("Bytes Received: " + networkIF.getBytesRecv());
            System.out.println("Bytes Sent: " + networkIF.getBytesSent());
            System.out.println("Packets Received: " + networkIF.getPacketsRecv());
            System.out.println("Packets Sent: " + networkIF.getPacketsSent());
            System.out.println("Inbound Packet Drops: " + networkIF.getInDrops());
            System.out.println("Inbound Errors: " + networkIF.getInErrors());
            System.out.println("Outbound Errors: " + networkIF.getOutErrors());
            System.out.println("Packet Collisions: " + networkIF.getCollisions());
            System.out.println("Timestamp (ms since boot): " + networkIF.getTimeStamp());
            System.out.println("Connector Present: " + networkIF.isConnectorPresent());
            System.out.println("Known VM MAC Address: " + networkIF.isKnownVmMacAddr());
            System.out.println("Java NetworkInterface Object: " + networkIF.queryNetworkInterface());
            System.out.println("Attributes Updated: " + networkIF.updateAttributes());
            System.out.println("======================\n");
        }
    }
}