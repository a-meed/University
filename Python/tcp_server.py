# Import socket module to create a TCP server
import socket

# Create a TCP socket
srv_soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Bind the socket to a specific address and port ~> bind(('ip', port))
srv_soc.bind(("127.0.0.1", 10000))

# Listen for incoming connections ~> listen(backlog)
srv_soc.listen(1)

# Accept a connection from a client ~> accept() returns a new socket object and the address of the client
us, addr = srv_soc.accept()

# Print the address of the connected client
while True:
    # Receive data from the client ~> recv(buffer_size) returns the data sent by the client
    data = us.recv(1024).decode()

    # Check if the client has sent an exit command or if the connection is closed
    if not data or data.lower() == "exit":
        print("Connection Closed")
        break

    # Print the received data from the client
    print(f"Client: {data}")

    # Send a response back to the client ~> send(data) sends data to the client
    msg = str(input("You: "))

    # Send the message to the client
    us.send(msg.encode())

# Close the client socket and the server socket
us.close()

# Close the server socket to free up the port and resources
srv_soc.close()