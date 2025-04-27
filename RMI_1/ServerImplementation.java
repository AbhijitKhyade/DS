import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ServerImplementation extends UnicastRemoteObject implements RemoteInterface {
    
    // Constructor to initialize the remote object
    public ServerImplementation() throws RemoteException {
        super();
    }

    // Implementation of the remote method
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}