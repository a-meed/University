from email.policy import default
from math import pi
import matplotlib.pyplot as plt
import numpy as np

x = np.linspace(0, 2 * np.pi, 10)
sin = np.sin(x)
cos = np.cos(x)
tan = np.tan(x)
cot = np.tan(x) * -1

while True:
    swt = input("Enter mode: ")
    match swt:
        case "sin":
            plt.plot(x, sin, "-.")
        case "cos":
            plt.plot(x, cos, "-.")
        case "tan":
            plt.plot(x, tan, "-.")
        case "cot":
            plt.plot(x, cot, "-.")
        case "par":
            px = np.linspace(-100, 2 * np.pi, 10)
            parabolic = (px**2 + 3) / (px - 1)
            plt.plot(px, parabolic, "-o")
        case "0":
            break
            exit()
        case _:
            print("----------Try again!----------")
    plt.show()