package ru.ezhov.test.net;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PingTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1000);
        for (int i = 1; i < 65555; i++) {
            service.execute(new PingIpWithPort("192.168.0.12", i));
        }
        service.shutdown();
    }

    private static class PingIpWithPort implements Runnable {

        private String host;
        private int port;

        public PingIpWithPort(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override
        public void run() {
            Socket socket = null;
            try {
                socket = new Socket(host, port);
                if (socket.isConnected()) {
                    System.out.println("JR порт: " + port);
                }
            } catch (Exception e) {
//                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
