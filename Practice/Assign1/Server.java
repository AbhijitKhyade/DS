import java.rmi.registry.*;

public class Server{
    public static void main(String[] args){
        try{
            // server instance
            ServerImplementation server = new ServerImplementation();

            // create registry
            Registry registry = LocateRegistry.createRegistry(5000);

            // bind remote object
            registry.rebind("Object", server);

            System.out.println("Server is ready and waiting for client requests...");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}