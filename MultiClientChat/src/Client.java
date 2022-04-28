
import java.io.*;
import java.net.Socket;

public class Client {

  private Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  public String username;

  public Client() {}
  
  public void connect(Socket socket, String username) {
    try {
      this.socket = socket;
      this.username = username;
      this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    } catch (IOException e) {
      closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }

//  public void sendMessage() {
//    System.out.println("tesate");
//    try {
//      bufferedWriter.write(username);
//      bufferedWriter.newLine();
//      bufferedWriter.flush();
//
//    Scanner scanner = new Scanner(System.in);
//
//      while (socket.isConnected()) {
//        String messageToSend = scanner.nextLine();
//        bufferedWriter.write(username + ": " + messageToSend);
//        bufferedWriter.newLine();
//        bufferedWriter.flush();
//      }
//    } catch (IOException e) {
//      closeEverything(socket, bufferedReader, bufferedWriter);
//    }
//  }

  public void sendNewMessage(String message) {
    try {
      if (socket.isConnected()) {
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
      }

    } catch (IOException e) {
      closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }

  public void listenForMessage() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        String msgFromGroupChat;

        while (socket.isConnected()) {
          try {
            msgFromGroupChat = bufferedReader.readLine();
            System.out.println(msgFromGroupChat);

          } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
          }
        }
      }
    }).start();
  }

  public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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