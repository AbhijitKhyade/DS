import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ServerImplementation extends UnicastRemoteObject implements RemoteInterface{

    public ServerImplementation() throws RemoteException{
        super();
    }

    public int add(int a, int b) throws RemoteException{
        return a+b;
    }
}