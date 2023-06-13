package com.wpc;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangpeican
 * @date 2023/4/14 20:06
 */
public class HdfsSlowRpcDetector {

    private final int threshold; // 阈值，单位毫秒
    private final Map<String, Long> lastRpcTimeMap; // 存储每个RPC类型的最后一次调用时间

    public HdfsSlowRpcDetector(int threshold) {
        this.threshold = threshold;
        this.lastRpcTimeMap = new HashMap<>();
    }

    public void processLog(String logLine) {
        String[] fields = logLine.split("\\s+");
        if (fields.length < 5) {
            return;
        }
        String rpcType = fields[3];
        long timestamp = Long.parseLong(fields[0]);
        long lastRpcTime = lastRpcTimeMap.getOrDefault(rpcType, 0L);
        long rpcDuration = timestamp - lastRpcTime;
        if (rpcDuration > threshold) {
            System.out.printf("Slow RPC detected: %s took %d ms to complete%n", rpcType, rpcDuration);
        }
        lastRpcTimeMap.put(rpcType, timestamp);
    }

    public void processLogs(List<String> logData) {
        logData.stream()
                .forEach(this::processLog);
    }

    public Map<String, Long> detectSlowRpcs(List<String> logData) {
        return logData.stream()
                .map(logLine -> {
                    String[] fields = logLine.split("\\s+");
                    if (fields.length < 5) {
                        return null;
                    }
                    String rpcType = fields[3];
                    long timestamp = Long.parseLong(fields[0]);
                    long lastRpcTime = lastRpcTimeMap.getOrDefault(rpcType, 0L);
                    long rpcDuration = timestamp - lastRpcTime;
                    lastRpcTimeMap.put(rpcType, timestamp);
                    return new AbstractMap.SimpleEntry<>(rpcType, rpcDuration);
                })
                .filter(entry -> entry != null && entry.getValue() > threshold)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static void main(String[] args) {
        HdfsSlowRpcDetector detector = new HdfsSlowRpcDetector(5000);
        List<String> logData = Arrays.asList(
                "1234567890 INFO rpc.Server: Slow RPC call: getBlockLocations took 5500 ms",
                "1234568901 INFO rpc.Server: Slow RPC call: getFileInfo took 6000 ms",
                "1234569002 INFO rpc.Server: Slow RPC call: getBlockLocations took 4000 ms",
                "1234569012 INFO rpc.Server: Slow RPC call: rename took 7000 ms",
                "1234569012 INFO rpc.Server: Slow RPC call: rename took 7000 ms",
                "1234569012 INFO rpc.Server: Slow RPC call: rename took 7000 ms",
                "1234569012 INFO rpc.Server: Slow RPC call: rename took 7000 ms",
                "1234569012 INFO rpc.Server: Slow RPC call: rename took 7000 ms"
        );
        Map<String, Long> slowRpcs = detector.detectSlowRpcs(logData);
        if (!slowRpcs.isEmpty()) {
            System.out.println("Slow RPCs detected:");
            slowRpcs.forEach((rpcType, duration) ->
                    System.out.printf("%s took %d ms to complete%n", rpcType, duration));
        }
    }
}
