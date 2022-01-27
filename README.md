# hugosac-sockets
GUI Chat app written in Java. Multiple clients can connect and chat individually with each other.

## Start Server
First make sure you have Java installed. To run the server cd into /src/server and run
```
javac *.java
java ChatServer
```

## Login as client
To login as a client open a new terminal for each client, cd into /src/client and run
```
javac *.java
java MainWindow
```
Enter a username in the field and start to chat.
![Sign in window](/images/sign-in.png)

## Start Chatting
In the left sidebar you can select who to chat with from all joined clients.
![Chat window](/images/chat.png)
