package ru.ezhov.test.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpApplication {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);

            while (true) {
                Socket socket = serverSocket.accept();

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("catch: " + line);
                    }

                } catch (Exception e) {
                    //
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
