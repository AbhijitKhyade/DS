import socket
import time
from datetime import datetime

def start_time_daemon():
    host = '0.0.0.0'  # Listen on all interfaces
    port = 8080
    num_clients = 2  # Support multiple clients

    print("Berkeley Algorithm Time Daemon")
    print("-" * 30)

    server_socket = socket.socket()
    server_socket.bind((host, port))
    server_socket.listen(5)
    print(f"Time daemon started on port {port}. Waiting for {num_clients} client(s)...")

    client_sockets = []
    client_times = []
    time_differences = []

    # Step 1: Accept connections from all clients
    for i in range(num_clients):
        try:
            conn, addr = server_socket.accept()
            print(f"Client {i+1} connected from {addr}")
            client_sockets.append(conn)
        except socket.error as e:
            print(f"Error accepting connection: {e}")
            continue

    # Step 2: Get daemon's local time
    daemon_time = time.time()
    print(f"\nDaemon local time: {datetime.fromtimestamp(daemon_time)}")

    # Step 3: Request and collect time from all clients
    for i, conn in enumerate(client_sockets):
        try:
            # Request client's time
            conn.send("REQUEST_TIME".encode())
            client_time = float(conn.recv(1024).decode())
            client_times.append(client_time)
            
            # Calculate time difference (client - daemon)
            time_diff = client_time - daemon_time
            time_differences.append(time_diff)
            
            print(f"Client {i+1} time: {datetime.fromtimestamp(client_time)} (Diff: {time_diff:.6f} seconds)")
        except socket.error as e:
            print(f"Error receiving time: {e}")
            client_sockets.remove(conn)

    # Step 4: Include daemon's time in the calculation (with 0 difference)
    time_differences.append(0)  # Daemon's time difference with itself is 0
    
    # Step 5: Calculate the average offset
    average_offset = sum(time_differences) / len(time_differences)
    print(f"\nAverage time offset: {average_offset:.6f} seconds")
    
    # Step 6: Calculate and send adjustment for each client
    print("\nSending time adjustments to clients:")
    for i, conn in enumerate(client_sockets):
        try:
            # Calculate adjustment (negative of the difference from average)
            adjustment = -(time_differences[i] - average_offset)
            
            # Send adjustment to client
            conn.send(str(adjustment).encode())
            print(f"Client {i+1} adjustment: {adjustment:.6f} seconds")
        except socket.error as e:
            print(f"Error sending adjustment: {e}")

    # Step 7: Close all connections
    print("\nSynchronization complete. Closing connections.")
    for conn in client_sockets:
        conn.close()
    server_socket.close()

if __name__ == '__main__':
    start_time_daemon()