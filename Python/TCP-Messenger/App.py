import socket
import threading
import tkinter as tk
from tkinter import simpledialog
from tkinter.scrolledtext import ScrolledText

HOST = "127.0.0.1"
PORT = 5000


class ChatClient:
    def __init__(self):
        self.window = tk.Tk()
        self.window.title("Chat")

        self.name = simpledialog.askstring("Name", "Enter your name:")

        self.window.title("User: " + self.name)

        self.text_area = ScrolledText(self.window, height=15)
        self.text_area.pack(padx=10, pady=5)

        self.user_list = tk.Listbox(self.window, width=25)
        self.user_list.pack(side=tk.RIGHT, fill=tk.Y, padx=5)

        bottom = tk.Frame(self.window)
        bottom.pack(fill=tk.X)

        self.entry = tk.Entry(bottom, width=40)
        self.entry.pack(side=tk.LEFT, padx=5)

        tk.Button(bottom, text="Send", command=self.send_message).pack(side=tk.LEFT)

        tk.Button(bottom, text="Disconnect", command=self.disconnect).pack(side=tk.LEFT)

        self.client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.client.connect((HOST, PORT))

        self.buffer = ""

        threading.Thread(target=self.receive, daemon=True).start()

        self.window.protocol("WM_DELETE_WINDOW", self.disconnect)
        self.window.mainloop()

    def safe_insert(self, msg):
        self.text_area.insert(tk.END, msg + "\n")
        self.text_area.see(tk.END)

    def update_users(self, users):
        self.user_list.delete(0, tk.END)
        for u in users:
            self.user_list.insert(tk.END, u)

    def receive(self):
        while True:
            try:
                data = self.client.recv(1024).decode()
                if not data:
                    break

                self.buffer += data

                while "\n" in self.buffer:
                    msg, self.buffer = self.buffer.split("\n", 1)

                    if msg == "NAME":
                        self.client.sendall((self.name + "\n").encode())

                    elif msg.startswith("/users "):
                        users = msg.replace("/users ", "").strip()
                        user_list = users.split(",") if users else []
                        self.window.after(0, self.update_users, user_list)

                    else:
                        self.window.after(0, self.safe_insert, msg)

            except:
                break

    def send_message(self):
        msg = self.entry.get().strip()
        if msg:
            self.client.sendall(f"{self.name}: {msg}\n".encode())
            self.entry.delete(0, tk.END)

    def disconnect(self):
        try:
            self.client.sendall(f"[SYSTEM] {self.name} disconnected\n".encode())
            self.client.close()
        except:
            pass

        self.window.destroy()


ChatClient()