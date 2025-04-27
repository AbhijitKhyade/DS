import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Create an instance of the remote object
            ServerImplementation server = new ServerImplementation();

            // Create the RMI registry on port 1099
            Registry registry = LocateRegistry.createRegistry(5000);

            // Bind the remote object to the registry with a name
            registry.rebind("RemoteObject", server);

            System.out.println("Server is ready and waiting for client requests...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}