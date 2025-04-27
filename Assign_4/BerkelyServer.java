import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BerkelyServer {

    public static int PORT = 5000;
    public static int MAX_CLIENTS = 2;

    static class ClientConnection {

        Socket socket;
        long clientTime;
        DataInputStream dis;
        DataOutputStream dos;

        ClientConnection(Socket socket) throws IOException {
            this.socket = socket;
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        List<ClientConnection> clients = new ArrayList();
        System.out.println("Server has started waiting for clients");

        for (int i = 0; i < MAX_CLIENTS; i++) {
            Socket socket = serverSocket.accept();
            ClientConnection clientConnection = new ClientConnection(socket);
            clients.add(clientConnection);
            System.out.println("Client " + (i + 1) + " Connected ");
        }

        long serverTime = System.currentTimeMillis();
        System.out.println("Server Time is " + new Date(serverTime));

        // send server time to all clients
        for (ClientConnection clientConnection : clients) {
            clientConnection.dos.writeLong(serverTime);
        }

        // receive client times
        for (ClientConnection clientConnection : clients) {
            clientConnection.clientTime = clientConnection.dis.readLong();
            System.out.println("Client time received is " + new Date(clientConnection.clientTime));

        }

        // calculate avg
        long avgTime = serverTime;
        for (ClientConnection clientConnection : clients) {
            avgTime += clientConnection.clientTime;
        }

        avgTime /= (MAX_CLIENTS + 1);
        long adjTime = avgTime - serverTime;

        System.out.println("Avg Time is " + new Date(avgTime) + " and server ajecement is " + adjTime);
        // send adjacement to all clients
        for (ClientConnection clientConnection : clients) {
            long clientAdj = avgTime - clientConnection.clientTime;
            System.out.println("Adjacement sent is " + clientAdj);
            clientConnection.dos.writeLong(clientAdj);
        }

        serverSocket.close();
    }
}
