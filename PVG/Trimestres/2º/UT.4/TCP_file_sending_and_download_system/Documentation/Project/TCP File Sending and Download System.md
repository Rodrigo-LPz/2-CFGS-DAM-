# TCP File Sending and Download System



### Project description

This project consists of a client-server system developed in Java that allows file transfer via a TCP connection.

The main objective is for a client to be able to request a specific file from the server and download it automatically if it exists.



##### 👨🏻‍💻 The project serves as practice to reinforce concepts of:

* Java Programming
* Client-Server Communication
* Using TCP Sockets
* File and Directory Management



##### 🤖 System operation

The system is composed of two distinct parts:

* Server ("FileServer")
     ○ It listens on a TCP port.
     ○ It has a "files/" directory/folder where available files are stored.
     ○ Receive the name of the file requested by the client.
     ○ If the file exists, it sends it to the client.
     ○ If it does not exist, it returns an error message.
* Client ("FileClient")
     ○ It connects to the server using TCP (Host and port).
     ○ Request a file by specifying its name.
     ○ Receive the name of the file requested by the client.
     ○ It automatically creates a "downloads/" folder if it does not exist.
     ○ Save the downloaded file there.



##### 📁 PROJECT STRUCTURE

(BEFORE running the program)

At this point, only the original file exists on the server and there are no downloads yet.



TCP\_file\_sending\_and\_download\_system/

└── src/

&nbsp;   └── main/

&nbsp;       └── java/

&nbsp;           ├── Server/

&nbsp;           │   ├── FileServer.java

&nbsp;           │   └── files/

&nbsp;           │       └── Test.txt

&nbsp;           │

&nbsp;           └── Client/

&nbsp;               └── FileClient.java



Observations (initial state):

* The "files/" folder already exists on the server.
* The server has the file "Test.txt".
* The client has no downloads folder.
* No transfer has been performed.
* The "downloads/" folder does not exist yet.
* There are no files downloaded on the client.

##### 

(AFTER running the program)

After execution, a new folder and a new file appear on the client side.



TCP\_file\_sending\_and\_download\_system/

└── src/

&nbsp;   └── main/

&nbsp;       └── java/

&nbsp;           ├── Server/

&nbsp;           │   ├── FileServer.java

&nbsp;           │   └── files/

&nbsp;           │       └── Test.txt

&nbsp;           │

&nbsp;           └── Client/

&nbsp;               ├── FileClient.java

&nbsp;               └── downloads/

&nbsp;                   └── Test.txt



Observations (actual state):

* Both the "files/" folder and the "Test.txt" file still exist on the server.
* The client automatically creates the "downloads/" directory if it does not exist.
* It downloads and stores the "Test.txt" file received from the server in "downloads/", keeping the original server files intact.
* The Client/Server communication connection via TCP is concluded and closed.



##### 👀 Result

After successful execution:

* The original file remains on the server.
* The client receives an exact copy of the requested file in their "downloads" folder.
