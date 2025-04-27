import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Locate the RMI registry on the specified port
            Registry registry = LocateRegistry.getRegistry("localhost", 5000);

            // Look up the remote object by its name
            RemoteInterface remoteObject = (RemoteInterface) registry.lookup("RemoteObject");

            // Call the remote method and print the result
            System.out.println("Enter two numbers to add:");
            int num1, num2;
            Scanner scanner = new Scanner(System.in);
            System.out.print("Number 1: ");
            num1 = scanner.nextInt();
            System.out.print("Number 2: ");
            num2 = scanner.nextInt();
            int result = remoteObject.add(num1, num2);
            // int result = remoteObject.add(5, 20);
            System.out.println("Result of addition: " + result);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}