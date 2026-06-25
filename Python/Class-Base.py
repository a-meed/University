from os import system as sys


class Print:
    def __init__(self, color):
        self.color = color
        sys("color " + self.color)
        print("Hello World!")

    @staticmethod
    def adder(a, b):
        return a + b

    def __del__(self):
        print("Bye")


color = input("Enter color: ")
obj = Print(color)