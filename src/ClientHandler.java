
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientHandler implements Runnable {

  public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

  private Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  private String clientUsername;

  public ClientHandler(Socket socket) {
    try {
      this.socket = socket;
      this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

      clientHandlers.add(this);
      broadcastMessage(new Message("getUpdatedUserList", "SERVER", "").toString());
    } catch (IOException e) {
      closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }

  @Override
  public void run() {
    String messageFromClient;
    while (socket.isConnected()) {
      try {
        messageFromClient = bufferedReader.readLine();
        broadcastMessage(messageFromClient);

      } catch (IOException e) {
        closeEverything(socket, bufferedReader, bufferedWriter);
        break;
      }
    }
  }

  public String getUserList() {
    ArrayList<String> userList = new ArrayList<>();

    for (ClientHandler clientHandler : clientHandlers) {
      userList.add(clientHandler.clientUsername);
    }
    
    return String.join(",", userList);
  }

  public void broadcastMessage(String messageToSend) {
    List<String> splittedMessage = Arrays.asList(messageToSend.split(" "));
    String type = splittedMessage.get(0);
    String author = splittedMessage.get(1);
    String body = String.join(" ", splittedMessage.subList(2, splittedMessage.size()));

    String clientToReceiveMessage = "";
    
    if (type.equals("privateMessage")) {
      clientToReceiveMessage = splittedMessage.get(3);
    }

    if (type.equals("newUserConnected")) {
      this.clientUsername = author;
      broadcastMessage(new Message("getUpdatedUserList", "SERVER", "").toString());
    }
    
    if (type.equals("privateMessage")) {
      for (ClientHandler clientHandler : clientHandlers) {
        try {
          if (clientHandler.clientUsername.equals(clientToReceiveMessage)) {
            clientHandler.bufferedWriter.write(new Message(type, author, body).toString());
            clientHandler.bufferedWriter.newLine();
            clientHandler.bufferedWriter.flush();
          }
        } catch (IOException e) {
          closeEverything(socket, bufferedReader, bufferedWriter);
        }
      }
    } else if (type.equals("getUpdatedUserList")) {
      for (ClientHandler clientHandler : clientHandlers) {
        try {
          clientHandler.bufferedWriter.write(new Message(type, author, getUserList()).toString());
          clientHandler.bufferedWriter.newLine();
          clientHandler.bufferedWriter.flush();
        } catch (IOException e) {
          closeEverything(socket, bufferedReader, bufferedWriter);
        }
      }
    } else {
      for (ClientHandler clientHandler : clientHandlers) {
        try {
          if (!clientHandler.clientUsername.equals(clientUsername)) {
            clientHandler.bufferedWriter.write(messageToSend);
            clientHandler.bufferedWriter.newLine();
            clientHandler.bufferedWriter.flush();
          }
        } catch (IOException e) {
          closeEverything(socket, bufferedReader, bufferedWriter);
        }
      }
    }
  }

  public void removeClientHandler() {
    clientHandlers.remove(this);
    broadcastMessage(new Message("userLeftChat", clientUsername, "SAIU DO CHAT!").toString());
    broadcastMessage(new Message("getUpdatedUserList", clientUsername, "").toString());
  }

  public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
    removeClientHandler();

    try {
      if (bufferedReader != null) {
        bufferedReader.close();
      }
      if (bufferedWriter != null) {
        bufferedWriter.close();
      }
      if (socket != null) {
        socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
