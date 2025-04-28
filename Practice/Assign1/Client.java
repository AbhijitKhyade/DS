import java.rmi.registry.*;
import java.util.Scanner;

public class Client{
    public static void main(String[] args){
        try{
           // locate registry
           Registry registry = LocateRegistry.getRegistry("localhost",5000);

           // look up
           RemoteInterface remoteObject = (RemoteInterface) registry.lookup("Object");

           // Input
           Scanner sc = new Scanner(System.in);
            System.out.println("Enter Num1: ");
            int num1 = sc.nextInt();
            System.out.println("Enter Num2: ");
            int num2 = sc.nextInt();

            int result = remoteObject.add(num1, num2);

            System.out.println("Result: " + result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

