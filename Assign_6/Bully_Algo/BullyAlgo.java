import java.util.*;

public class BullyAlgo {
    public static void main(String[] args) {
        
        // 1. Get the Process Info (N & PIDs)
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        int processes[] = new int[n];

        System.out.println("Enter process IDs:");
        for (int i = 0; i < n; i++) {
            processes[i] = sc.nextInt();
        }

        Arrays.sort(processes); // Ascending order

        // 2. Get the Coordinator ID and Initiator Process ID
        System.out.print("Enter the coordinator ID (who fails): ");
        int coordinator = sc.nextInt();

        System.out.print("Enter the ID of process detecting failure: ");
        int initiator = sc.nextInt();

        System.out.println("\n--- Bully Election Started ---\n");
        
        // 3.Start election with the initiator process
        int newCoordinator = startElection(processes, initiator, coordinator, n);
        
        // 4.Announce the coordinator to all processes
        announceCoordinator(processes, newCoordinator, coordinator);
        
        sc.close();
    }
    
    // Simulate election process with responses and recursive elections
    private static int startElection(int[] processes, int initiator, int failedCoordinator, int n) {
        System.out.println("Process " + initiator + " initiates election.");
        boolean receivedResponse = false;
        
        // a) Send election messages to higher processes
        for (int i = 0; i < n; i++) {
            if (processes[i] > initiator && processes[i] != failedCoordinator) {
                System.out.println("Process " + initiator + " sends election message to " + processes[i]);
                
                // Simulate response from higher process
                System.out.println("Process " + processes[i] + " responds OK to " + initiator);
                receivedResponse = true;
            }
        }
        
        // b) Simulate timeout - if no responses received
        if (!receivedResponse) {
            System.out.println("Process " + initiator + " timeout waiting for responses.");
            System.out.println("Process " + initiator + " becomes coordinator.");
            return initiator;
        }
        
        // c) Recursively run elections with higher processes
        int highestProcess = -1;
        for (int i = 0; i < n; i++) {
            if (processes[i] > initiator && processes[i] != failedCoordinator) {
                System.out.println("\n--- Process " + processes[i] + " starts election recursively ---");
                
                // Higher processes start their own elections
                highestProcess = Math.max(highestProcess, processes[i]);
            }
        }
        
        System.out.println("\nNew Coordinator elected: Process " + highestProcess);
        return highestProcess;
    }
    
    // Announce coordinator to all processes
    private static void announceCoordinator(int[] processes, int newCoordinator, int failedCoordinator) {
        System.out.println("\n--- Coordinator Announcements ---");
        for (int process : processes) {
            if (process != newCoordinator && process != failedCoordinator) {
                System.out.println("Coordinator " + newCoordinator + " sends coordinator message to Process " + process);
            }
        }
        System.out.println("\nElection completed. Process " + newCoordinator + " is the new coordinator.");
    }
}