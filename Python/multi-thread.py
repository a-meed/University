# This code demonstrates the use of threading to execute a task concurrently while measuring the total time taken for the task to complete.

import threading
import time

# Define a function that simulates a task by printing a message and sleeping for a short duration.
def tasking():
    for i in range(3):
        print(f"Tasking: {i}")
        time.sleep(1)

# Create a thread that will execute the tasking function.
task = threading.Thread(target=tasking)

# Record the start time before starting the thread.
start = time.time()

# Start the thread to execute the task concurrently.
task.start()

# Wait for the thread to complete its execution before proceeding.
task.join()

end = time.time()

print(f"Total time taken: {end - start:.2f}")