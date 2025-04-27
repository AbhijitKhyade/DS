import mpi.MPI;
import java.util.Scanner;
import mpi.*;

public class ScatterGather {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);
        //  Get rank of each process and size of communicator
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        
        int unitsize = 3;
        int root = 0;
        int send_buffer[] = null;
        //  1 process is expected to handle 4 elements
        send_buffer = new int [unitsize * size];
        int recieve_buffer[] = new int [unitsize];
        int new_recieve_buffer[] = new int [size];

        //  Set data for distribution
        if(rank == root) {
            int total_elements = unitsize * size;
            System.out.println("Enter " + total_elements + " elements");
            for(int i = 0; i < total_elements; i++) {
                System.out.println("Element " + i + "\t = " + i);
                send_buffer[i] = i;
            }
        }

        //  Scatter data to processes
        MPI.COMM_WORLD.Scatter(
            send_buffer,
            0,
            unitsize,
            MPI.INT,
            recieve_buffer,
            0,
            unitsize,
            MPI.INT,
            root
        );

        // ------------ Sum ------------- //
        //  Calculate sum at non root processes
        //  Store result in first index of array
        for(int i = 1; i < unitsize; i++) {
            recieve_buffer[0] += recieve_buffer[i];
        }
        System.out.println(
            "Intermediate sum at process " + rank + " is " + recieve_buffer[0]
        );

        // ------------ Multiply ------------- //
        // Calculate product at each process
        // Store result in first index of array
        // recieve_buffer[0] = 1; // Initialize to 1 for multiplication
        // for(int i = 0; i < unitsize; i++) {
        //     recieve_buffer[0] *= recieve_buffer[i];
        // }
        // System.out.println(
        //     "Intermediate product at process " + rank + " is " + recieve_buffer[0]
        // );

        //  Gather data from processes
        MPI.COMM_WORLD.Gather(
            recieve_buffer,
            0,
            1,
            MPI.INT,
            new_recieve_buffer,
            0,
            1,
            MPI.INT,
            root
        );

        // ------------- Sum ------------- //
        //  Aggregate output from all non root processes
        if(rank == root) {
            int total_sum = 0;
            for(int i = 0; i < size; i++) {
                total_sum += new_recieve_buffer[i];
            }
            System.out.println("Final sum : " + total_sum);
        }

        // Aggregate output from all processes
        // if(rank == root) {
        //     int final_product = 1; // Initialize to 1
        //     for(int i = 0; i < size; i++) {
        //         final_product *= new_recieve_buffer[i];
        //     }
        //     System.out.println("Final product : " + final_product);
        // }

        MPI.Finalize();
    }
}