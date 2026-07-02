import socket
import threading

HOST = "127.0.0.1"
PORT = 5000

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind((HOST, PORT))
server.listen()

clients = []
names = []
lock = threading.Lock()


def broadcast(message: str):
    data = (message + "\n").encode()
    dead = []

    with lock:
        for c in clients:
            try:
                c.sendall(data)
            except:
                dead.append(c)

        for d in dead:
            remove_client(d)


def send_user_list():
    with lock:
        user_list = ",".join(names)
    broadcast(f"/users {user_list}")


def remove_client(client):
    if client in clients:
        idx = clients.index(client)
        name = names[idx]

        clients.remove(client)
        names.remove(name)

        broadcast(f"[SYSTEM] {name} left")
        send_user_list()


def handle(client):
    buffer = ""

    while True:
        try:
            data = client.recv(1024).decode()
            if not data:
                break

            buffer += data

            while "\n" in buffer:
                msg, buffer = buffer.split("\n", 1)
                broadcast(msg)

        except:
            break

    remove_client(client)
    client.close()


def receive():
    print("Server started...")

    while True:
        client, _ = server.accept()

        client.sendall(b"NAME\n")
        name = client.recv(1024).decode().strip()

        with lock:
            clients.append(client)
            names.append(name)

        broadcast(f"[SYSTEM] {name} joined")
        send_user_list()

        threading.Thread(target=handle, args=(client,), daemon=True).start()


receive()