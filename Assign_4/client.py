# -*- coding: utf-8 -*-
import socket
import time
import random
from datetime import datetime

def start_client():
    host = '127.0.0.1'  # Server IP (localhost for same machine)
    port = 8080

    print("Berkeley Algorithm Client")
    print("-" * 30)

    # Initialize client with a random clock offset (-5 to +5 seconds)
    offset = random.uniform(-5, 5)
    local_time = time.time() + offset
    print(f"Client initialized with offset: {offset:.2f} seconds")
    print(f"Local time before sync: {datetime.fromtimestamp(local_time)}")

    client_socket = socket.socket()
    try:
        client_socket.connect((host, port))
        print(f"Connected to time daemon at {host}:{port}")
    except socket.error as e:
        print(f"Failed to connect to server: {e}")
        return

    try:
        # Wait for daemon to request time
        request = client_socket.recv(1024).decode()
        if request == "REQUEST_TIME":
            # Send current local time to daemon
            current_time = time.time() + offset
            client_socket.send(str(current_time).encode())
            print(f"Sent local time: {datetime.fromtimestamp(current_time)}")
            
            # Receive time adjustment from daemon
            adjustment = float(client_socket.recv(1024).decode())
            print(f"\nReceived time adjustment: {adjustment:.6f} seconds")
            
            # Apply the adjustment to local time
            new_time = time.time() + offset + adjustment
            print(f"Local time after sync: {datetime.fromtimestamp(new_time)}")
            print(f"Clock adjustment applied: {adjustment:.6f} seconds")
    except socket.error as e:
        print(f"Error communicating with daemon: {e}")
    finally:
        client_socket.close()

if __name__ == '__main__':
    start_client()