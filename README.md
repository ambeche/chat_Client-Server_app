## This application allows communication between the android client, ChatClientCAT and the server, ChatServerCAT.
  - TCP Sockets are used to establish connection between the client and the server
  - The client and the server must run in the same network for connection to be established
  - ChatServerCAT runs on Intellij IDE on a PC

## [App's Demo Video](https://photos.app.goo.gl/yaJr15Zni4XN1ZN9A)

 ## 1. ChatClientCAT - Android Client
   ### 1.1 Implemented Features
   
   **-Login Activity:** Creates and verifies a username and login a user into the chat room
    
   **-Messaging Activity:** Allows the user to send and receive messages
    
   **-A heterogeneous ViewHolder RecyclerView** is used to render messages to the user, incoming messages are
      aligned to the right, while sent messages are aligned left.
      
   **-An option Menu with user commands:**  
   1. Users --> shows a list of all online users
   2. Top Chatters --> a list of 4 top chatters based on number of messages
   3. Chat History --> Conversation History
   4. Exit --> logout the user from chat room
   
### 1.2 OO Design Pattens Used
  **Singleton:** ServerConnector is an object, creating one instance of connection to server, and providing a global 
     access point to both Activities and custom classes.

 **Observer Pattern:** "SendAndReceiveMessageActivity: Observer" registers as an observer to "ServerConnector: Observable"
     When a new message arrives, ServerConnector calls the update() method of observer which then updates the recycler view adapter.

 **MVC**
   
   M - Model : contains the business logic of the applicaton, custom classes ChatAdapter, ChatMessage and ServerConnector
   
   V - View  : login and chatting user interfaces, presents model to user
   
   C - Control : interprets user inputs - Activities

## **2. ChatServerCAT**
   The server listens for incoming connections and can connent multiple clients, both Telnet and Android. New messages from connected
   clients are pushed to all connected clients. Messages are sent as Json strings using Kotlin Json serialization.
   The server respons to all commands from the clients.
   
   Observer and Singleton patterns where used in the server implementation; ChatHistory and Users Custom classes are singleton and 
   ChatConnector is an observer to ChatHistory which is Observable.
 
## 3. Installations and Configurations
  ### 3.1  [Android Client Installer Link](https://users.metropolia.fi/~tamanjic/ChatClient.apk)
   Open link on your android device to install application. Before installation make sure your device accepts apps from **Unkown Sources: go to Settings -> Security** then check **Unkown Sources**
  ### 3.2 [Download and Install Intellij IDEA on Your PC](https://www.jetbrains.com/idea/download/#section=windows)
  ### 3.2 Download chat_Client-Server_app  Project as .zip
  **Extract** the downloaded project and open the server folder, ChatServerCAT with Intellij IDEA. Then build and run the server project on Intellij IDEA. ***Note: Server App runs on port 3000***
  ### 3.3 Make sure that your that your server is running on your PC, and your android device is connected to the same LAN as your PC.
  Then open the client on your android device, login and start chatting. Open multiple clients and Telnet clients on your PC, then connect them to your server and chat.
  ### If the installer link above does not work, open the ChatClientCAT folder [Android Studio](https://developer.android.com/studio/?gclid=CjwKCAiAg9rxBRADEiwAxKDTuhCA7u13DRTLlB7YshYc7BZrdnGumbdFia_m33cqwwreesEjzhiG1hoCKygQAvD_BwE) - chat_Client-Server_app -> ChatClientCAT
  Then go to Project => src=>main=>Java=>com.example.chatclientcat=>ServerConnector.kt and change the IP address to that of your PC  socket = Socket("Your IP here", 3000). 
  Then save the file, build and run the project on android simulators and connect to the server.
