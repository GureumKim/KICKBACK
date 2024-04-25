package org.example.businessserver.service;

import org.springframework.boot.CommandLineRunner;

public class TcpServerRunner implements CommandLineRunner {

    private final TcpServerInitializer tcpServerInitializer;

    public TcpServerRunner(TcpServerInitializer tcpServerInitializer) {
        this.tcpServerInitializer = tcpServerInitializer;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting TCP Server...");
        tcpServerInitializer.initializeTcpServer().subscribe(
                disposableServer -> {
                    System.out.println("TCP Server started on port: " + disposableServer.port());
                },
                error -> {
                    System.err.println("Failed to start TCP Server: " + error.getMessage());
                }
        );
    }
}