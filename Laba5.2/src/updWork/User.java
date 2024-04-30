package updWork;

import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private InetAddress ipAddress;
    private int port;

    public User(InetAddress ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "IP: " + ipAddress + ", Port: " + port;
    }
}
