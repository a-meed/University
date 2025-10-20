# import mysql.connector as mysql
#
# print("Connecting to MySQL database... ")
# cnx = mysql.connect(user='root', password='', host='127.0.0.1', database='python', charset='utf8')
# print("Connected to MySQL Database ")
# cursor = cnx.cursor()
# cursor.execute('INSERT INTO users (\'name\') VALUES (\'Ali\')')
# cnx.commit()
# cnx.close()

a = [1, 2, 4, 8, 10, 16, 32]

print(list(map(lambda x: 'Big' if x > 5 else 'Small', a)))

print(list(filter(lambda x: x % 2 == 0, a)))
