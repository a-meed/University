import array
from math import degrees
import turtle

arr2D = [[1, 2, 3, 4], [6, 7, 8, 9]]
print(arr2D[0][1])

# class Main:
#     L = []
#     """
#     len
#     [:-1]
#     [y:x]
#     """

#     j = int(input())
#     if j > 30:
#         i = j
#         for i in range(i, 40, 2):
#             L.append(i)


#     print(L[0:-1])
def Multiple():
    operand = 0
    while operand == 0:
        try:
            operand = int(input("enter multiplier: "))
            break
        except:
            print("is not correct! TRY AGAIN...")

    arr = [1, 2, 3]
    for i in range(len(arr)):
        arr[i] = arr[i] * operand
        print(
            "step: {"
            + str(i)
            + "} ---"
            + str(arr[:])
            + f" operation: {arr[i]} * {operand}"
        )