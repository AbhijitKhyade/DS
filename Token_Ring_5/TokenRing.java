import java.util.Scanner;

public class TokenRing {
    public static void main(String[] args) throws Throwable {   

        // 1. Get the Process Info (N & PIDs) 
        System.out.println("Enter the number of processes: ");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n : ");
        int n = sc.nextInt();
        int token = 0;
        int sender, receiver;
        String message;

        System.out.println("Initializing ring");
        for(int i = 0; i < n; i++) {
            System.out.print(" " + i);                        
        }
        System.out.print("\nEnter sender : ");
        sender = sc.nextInt();
        System.out.print("Enter receiver : ");
        receiver = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter message : ");
        message = sc.nextLine();

        System.out.println("\n--- Token Circulation and Mutual Exclusion Simulation Started ---");

        for(int i = token; i != sender; i = (i+1)%n) {
            System.out.print(" " + i + " ->");
        }
        System.out.println(" " + sender);

        // MUTUAL EXCLUSION PART (NEW)
        System.out.println("\nProcess " + sender + " now has the token.");
        System.out.print("Does Process " + sender + " want to enter Critical Section? (yes=1 / no=0): ");
        int choice = sc.nextInt();
        if(choice == 1) {
            System.out.println("Process " + sender + " entering Critical Section...");
            System.out.println("Process " + sender + " exiting Critical Section...");
        }
        System.out.println("Process " + sender + " sending message: " + message);

        for(int i = (sender + 1) % n; i != receiver; i = (i+1) % n){
            System.out.println("Message '" + message + "' forwarded by " + i);

            // MUTUAL EXCLUSION DURING FORWARDING (Optional per problem)
            System.out.print("Does Process " + i + " want to enter Critical Section? (yes=1 / no=0): ");
            choice = sc.nextInt();
            if(choice == 1) {
                System.out.println("Process " + i + " entering Critical Section...");
                System.out.println("Process " + i + " exiting Critical Section...");
            }
        }

        System.out.println("\nReceiver " + receiver + " received message: " + message);

        // MUTUAL EXCLUSION at RECEIVER
        System.out.print("Does Receiver " + receiver + " want to enter Critical Section? (yes=1 / no=0): ");
        choice = sc.nextInt();
        if(choice == 1) {
            System.out.println("Receiver " + receiver + " entering Critical Section...");
            System.out.println("Receiver " + receiver + " exiting Critical Section...");
        }

        System.out.println("\n--- Token Circulation and Mutual Exclusion Simulation Completed ---");
        token = sender;

        sc.close();
    }
}
