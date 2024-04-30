package updWork;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    private ActiveUsers userList = null;
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private InetAddress address = null;
    private int port = -1;

    public UDPServer(int serverPort) {
        try {
            socket = new DatagramSocket(serverPort);
        } catch(SocketException e) {
            System.out.println("Error: " + e);
        }
        userList = new ActiveUsers();
    }

    public void start() {
        try {
            byte[] receiveData = new byte[1024];
            while(true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received: " + message);

                User newUser = new User(receivePacket.getAddress(), receivePacket.getPort());
                if (!userList.contains(newUser)) {
                    userList.add(newUser);
                }

                sendUserData();
            }
        } catch(IOException e) {
            System.out.println("Error: " + e);
        } finally {
            if(socket != null) {
                socket.close();
            }
        }
    }

    public void work(int bufferSize) {
        try {
            System.out.println("Server start...");
            while (true) {
                getUserData(bufferSize);
                log(address, port);
                sendUserData();
            }
        } catch(IOException e) {
            System.out.println("Error: " + e);
        } finally {
            System.out.println("Server end...");
            socket.close();
        }
    }

    private void getUserData(int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        address = packet.getAddress();
        port = packet.getPort();
    }

    private void sendUserData() throws IOException {
        byte[] buffer;
        for (int i = 0; i < userList.size(); i++) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(userList.get(i));
            out.flush();
            buffer = bout.toByteArray();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        }
        buffer = "end".getBytes();
        packet = new DatagramPacket(buffer, 0, address, port);
        socket.send(packet);
    }

    private void log(InetAddress address, int port) {
        System.out.println("Request from: " + address.getHostAddress() + " port: " + port);
    }

    public static void main(String[] args) {
        (new UDPServer(1501)).work(256);
    }
}
