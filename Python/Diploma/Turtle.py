import turtle

T = turtle.Turtle()

turtle.bgcolor("white")
turtle.pencolor("black")
T.fillcolor("cyan")

for i in range(4):
    T.forward(200)
    T.left(90)
    T.up()
    T.goto(400, 0)
    T.down()
    T.circle(100)