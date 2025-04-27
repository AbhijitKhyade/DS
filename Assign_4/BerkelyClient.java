import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class BerkelyClient {
    public static int SERVER_PORT = 5000;
    public static String SERVER_ADDRESS = "localhost";

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // receive server time
        long serverTime = dis.readLong();
        System.out.println("Server Time received " + new Date(serverTime));

        // find local time

        long localTime = System.currentTimeMillis() + (long) (Math.random() * 10000 - 5000);
        System.out.println("Local time is " + new Date(localTime));
        dos.writeLong(localTime);

        long adjTime = dis.readLong();

        System.out.println("Adjustment from server is " + adjTime);
        localTime += adjTime;
        System.out.println("New time is " + new Date(localTime));

        socket.close();
    }
}