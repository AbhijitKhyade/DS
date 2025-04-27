import java.util.*;

public class RingAlgo {
    public static void main(String[] args) {

        // 1. Get the Process Info (N & PIDs)
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes in the ring: ");
        int n = sc.nextInt();
        int processes[] = new int[n];

        System.out.println("Enter process IDs:");
        for (int i = 0; i < n; i++) {
            processes[i] = sc.nextInt();
        }

        // 2. Get the Coordinator ID and Initiator Process ID
        System.out.print("Enter the coordinator ID (who fails): ");
        int coordinator = sc.nextInt();

        System.out.print("Enter the ID of process detecting failure: ");
        int initiator = sc.nextInt();


        // 3. Find the index of the initiator process
        int startIndex = -1;
        for (int i = 0; i < n; i++) {
            if (processes[i] == initiator) {
                startIndex = i;
                break;
            }
        }

        if (startIndex == -1) {
            System.out.println("Initiator process not found!");
            sc.close();
            return;
        }

        System.out.println("\n--- Ring Election Started ---\n");

        ArrayList<Integer> electionList = new ArrayList<>();

        int index = startIndex;
        do {
            if (processes[index] != coordinator) { // Dead process does not participate
                System.out.println("Process " + processes[index] + " passes election message.");
                electionList.add(processes[index]);
            }
            index = (index + 1) % n;
        } while (index != startIndex);

        int newCoordinator = Collections.max(electionList);

        System.out.println("\nProcesses participated: " + electionList);
        System.out.println("New Coordinator elected: Process " + newCoordinator);

        sc.close();
    }
}
