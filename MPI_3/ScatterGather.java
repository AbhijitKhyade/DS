import mpi.MPI;
import java.util.Scanner;
import mpi.*;

public class ScatterGather {
    public static void main(String[] args) throws Exception {

        // 0. Initialize MPI
        MPI.Init(args);

        //  1. Rank & Size
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        
        // 2. Problem Setup
        int unitsize = 3;
        int root = 0;
        int send_buffer[] = null;
        //  1 process is expected to handle n elements
        send_buffer = new int [unitsize * size];
        int recieve_buffer[] = new int [unitsize];
        int new_recieve_buffer[] = new int [size];

        //  3. Data Initialization at Root Process
        if(rank == root) {
            int total_elements = unitsize * size;
            System.out.println("Enter " + total_elements + " elements");
            for(int i = 0; i < total_elements; i++) {
                System.out.println("Element " + i + "\t = " + i);
                send_buffer[i] = i;
            }
        }

        //  4. Scatter Operation
        MPI.COMM_WORLD.Scatter(
            send_buffer,  // data to scatter
            0,            // starting index in send_buffer
            unitsize,     // number of elements sent to each process
            MPI.INT,      // datatype
            recieve_buffer,  // where data is received
            0,            // starting index in recieve_buffer
            unitsize,     // number of elements received
            MPI.INT,      // datatype
            root          // root process
        );

        //  5. Process Data
        // ------------ Sum ------------- //
        //  Calculate sum at non root processes
        //  Store result in first index of array
        for(int i = 1; i < unitsize; i++) {
            recieve_buffer[0] += recieve_buffer[i];
        }
        System.out.println(
            "Intermediate sum at process " + rank + " is " + recieve_buffer[0]
        );


        //  6. Gather Operation
        MPI.COMM_WORLD.Gather(
            recieve_buffer,  // data to send
            0,               // starting index
            1,               // send 1 element (the sum)
            MPI.INT,         // data type
            new_recieve_buffer, // buffer at root
            0,               // start storing from index 0
            1,               // one element per process
            MPI.INT,         // data type
            root             // root process
        );


        // 7. Print Output
        // ------------- Sum ------------- //
        //  Aggregate output from all non root processes
        if(rank == root) {
            int total_sum = 0;
            for(int i = 0; i < size; i++) {
                total_sum += new_recieve_buffer[i];
            }
            System.out.println("Final sum : " + total_sum);
        }

        // 8. Finalize MPI
        MPI.Finalize();
    }
}