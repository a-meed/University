# Importing necessary libraries
import socket

# Create a TCP socket
cl_soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Connect to the server at the specified address and port ~> connect(('ip', port))
cl_soc.connect(('127.0.0.1', 10000))

# Enter a loop to send messages to the server and receive responses until the user decides to exit
while True:
    # Get user input to send to the server
    msg = str(input("You: "))

    # Send the message to the server ~> send(data) sends data to the server
    cl_soc.send(msg.encode())

    # Check if the user wants to exit the chat
    if msg.lower() == "exit":
        break

    # Receive a response from the server ~> recv(buffer_size) returns the data sent by the server
    data = cl_soc.recv(1024).decode()

    # Print the response received from the server
    print(f"Server: {data}")

# Close the client socket to free up resources and end the connection
cl_soc.close()