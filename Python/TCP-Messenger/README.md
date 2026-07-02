# Python TCP-based messenger

## Introduction

In the world of computer networks, real-time communication between users is one of the most basic needs. TCP-based messengers are a simple but important example of client-server systems that implement the following concepts:

- Persistent Connection
- Reliable Transmission
- Multi-client Handling
- Concurrency

**Educational Importance of the Project**:

This project covers the following concepts in a practical way:
- Socket Programming in Python
- Client-Server Architecture
- Threading and Concurrency
- State Management in Networking
- Designing a Simple Communication Protocol

## Arch

**1. Server**

Tasks:
- Manage client connections
- Store the list of online users
- Receive and distribute messages (Broadcast)
- Manage user join/leave

**2. Client**

Tasks:
- Connect to the server
- Send messages
- Receive messages
- Display graphical UI (Tkinter)
- Display the list of online users

## Protocol design

### Normal message

```Ali: Hello```

### System message

```
[SYSTEM] Ali joined
[SYSTEM] Ali left
```

### Sending a list of users

```/users Ali,Reza,John``` 

**Protocol features:**

- Text-based
- Line-delimited
- Simple and extensible

## Server implementation

### Creating and preparing a socket

```python
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind((HOST, PORT))
server.listen()
```

- `AF_INET` ~> IPv4
- `SOCK_STREAM` ~> TCP
- `listen()` ~> Ready to receive connections

### User management

```python
clients = []
names = []
lock = threading.Lock()
```

- `clients` ~> list of sockets
- `names` ~> user names
- `lock` ~> prevent race conditions in threading

### Broadcast message

```python
def broadcast(message): 
```

- Send message to all users
- Manage disconnected clients
- Use sendall to ensure complete data transmission

### Manage each client's connection

```python
def handle(client): 
```

- Receive messages
- Parse messages (buffer handling)
- Detect disconnection
- Remove user from list

❗Note:

> Using buffer makes Manage incomplete TCP messages and Process multiple messages in a row correctly

### Manage user logins

```python
def receive():
```

Steps:

1. Accept connection
2. Request user name
3. Save in list
4. Start separate thread

## Implementing the client

### Interface  User (Tkinter)

- Text Area ~> Display messages
- Entry ~> Message input
- Listbox ~> List of users
- Buttons ~> Send and Disconnect

### Connecting to the server

```python
self.client.connect((HOST, PORT))
```

### Receiving messages (separate Thread)

```python
threading.Thread(target=self.receive, daemon=True).start() 
```

Preventing UI from blocking & Receiving messages simultaneously.

### Managing messages

1. User name message `NAME` 
2. User list `/users Ali,Reza` 
3. Normal message `Ali: Hello`

### Updating the UI safely

```python
self.window.after(0, self.safe_insert, msg)
```
Tkinter is not thread-safe so it must be updated from the main thread.

### Disconnect button

- Send optional system message
- Close socket
- Close window

## Online user list capability

**Server:**

- Server detects join/leave every time
- Broadcasts the following message: `/users user1,user2,user3`
 
**Client:**

- Receive message
- Split list
- Display in Listbox

## Concurrency

Advantages: Simple, Understandable, Suitable for educational project

Disadvantages: Limited scalability in large number of users

## Error management and stability

- Manage sudden disconnect
- Remove broken client from broadcast
- Prevent crash threads
- Use try/except in recv/send