import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ClientScreen extends javax.swing.JFrame {
  private Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  public String username;
  public static ArrayList<String> userList = new ArrayList<>();


  public ClientScreen() throws IOException {
    initComponents();
    setLocationRelativeTo(null);
    initClient();
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    userListScroll = new javax.swing.JScrollPane();
    userListPanel = new javax.swing.JPanel();
    chatInputScroll = new javax.swing.JScrollPane();
    chatInput = new javax.swing.JTextArea();
    submitBtn = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    nameLabel = new javax.swing.JLabel();
    chatScroll = new javax.swing.JScrollPane();
    chatPanel = new javax.swing.JPanel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setPreferredSize(new java.awt.Dimension(960, 720));
    setResizable(false);

    userListScroll.setBackground(new java.awt.Color(255, 255, 255));

    userListPanel.setBackground(new java.awt.Color(255, 255, 255));
    userListPanel.setLayout(new javax.swing.BoxLayout(userListPanel, javax.swing.BoxLayout.Y_AXIS));
    userListScroll.setViewportView(userListPanel);

    chatInput.setBackground(new java.awt.Color(255, 255, 255));
    chatInput.setColumns(20);
    chatInput.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
    chatInput.setRows(5);
    chatInputScroll.setViewportView(chatInput);

    submitBtn.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
    submitBtn.setText("Enviar");
    submitBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        onSubmitClick(evt);
      }
    });

    jLabel1.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
    jLabel1.setText("Nome:");

    nameLabel.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

    chatScroll.setBackground(new java.awt.Color(255, 255, 255));

    chatPanel.setBackground(new java.awt.Color(255, 255, 255));
    chatPanel.setToolTipText("");
    chatPanel.setFocusable(false);
    chatPanel.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
    chatPanel.setLayout(new javax.swing.BoxLayout(chatPanel, javax.swing.BoxLayout.Y_AXIS));
    chatScroll.setViewportView(chatPanel);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(14, 14, 14)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(chatInputScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
              .addComponent(submitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)))
          .addGroup(layout.createSequentialGroup()
            .addComponent(userListScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(chatScroll)))
        .addGap(14, 14, 14))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(14, 14, 14)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(userListScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
          .addComponent(chatScroll))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel1)
              .addComponent(nameLabel)))
          .addComponent(chatInputScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(15, 15, 15))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  public void connect(Socket socket, String username) {
    try {
      this.socket = socket;
      this.username = username;
      this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      
      this.listenForMessage();
      this.sendNewMessage(new Message("newUserConnected", username, ""));
    } catch (IOException e) {
      closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }
  
  public void sendNewMessage(Message message) {
    try {
      if (socket.isConnected()) {
        bufferedWriter.write(message.toString());
        bufferedWriter.newLine();
        bufferedWriter.flush();
        
        this.onReceiveNewMessage(message.toString());
      }

    } catch (IOException e) {
      closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }

  public void listenForMessage() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        String newMessage;

        while (socket.isConnected()) {
          try {
            newMessage = bufferedReader.readLine();
            onReceiveNewMessage(newMessage);

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
  
  public void onReceiveNewMessage(String receivedMessage) {
    List<String> splittedMessage = Arrays.asList(receivedMessage.split(" "));
    String type = splittedMessage.get(0);
    String author = splittedMessage.get(1);
    String body = String.join(" ", splittedMessage.subList(2, splittedMessage.size()));
    
    if (type.equals("privateMessage")) {
      body = body.replace("/to", "=>");
    }
    
    Message message = new Message(type, author, body);
    
    switch (type) {
      case "newUserConnected":
        addNewUserToList(message);
        break;
        
      case "userLeftChat":
        removeUserFromList(message);
        break;
        
      case "message":
        addMessageToChat(message.getAuthor(), message.getBody(), false);
        break;
        
      case "privateMessage":
        addMessageToChat(message.getAuthor(), message.getBody(), true);
        break;
        
      case "getUpdatedUserList":
        updateUserListScreen(message.getBody().split(","));
        break;
        
      default:
        throw new AssertionError();
    }
  }
  
  public void addNewUserToList(Message message) {
    addMessageToChat(message.getAuthor(), message.getBody(), false);
  }
  
  public void removeUserFromList(Message message) {
    addMessageToChat(message.getAuthor(), message.getBody(), false);
  }
  
  public void updateUserListScreen(String[] newUserList) {
    for (String user : newUserList) {
      this.userList.add(user);
    }
    
    userListPanel.removeAll();

    for (String user : this.userList) {
      JLabel userLabel = new JLabel(user);
      userLabel.setBorder(new CompoundBorder(userLabel.getBorder(), new EmptyBorder(5,0,5,0)));
      userLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
      
      this.userListPanel.add(userLabel);
    }
    
    this.userListPanel.validate();
    this.userListPanel.repaint();
  }
  
  public String getMessageType(String message) {
    boolean isPrivateMessage = message.split(" ")[0].equals("/to");
    
    return isPrivateMessage ? "privateMessage" : "message";
  }
  
  private void onSubmitClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSubmitClick
    String messageString = this.chatInput.getText();
    String type = getMessageType(messageString);
    
    Message message = new Message(type, this.username, messageString);
    
    this.sendNewMessage(message);
    this.chatInput.setText("");
  }//GEN-LAST:event_onSubmitClick

  private void initClient() throws IOException {
    String username = askForUsername();
    Socket socket = new Socket("localhost", 80);

    this.nameLabel.setText(username);
    this.connect(socket, username);
  }

  private String askForUsername() {
    String name = JOptionPane.showInputDialog("Digite seu nome");
    System.out.println(this.userList + " " + this.userList.size());

    if (this.userList.size() > 0) {
      for (String currName : this.userList) {
        System.out.println(currName + " " + name);
        if (name.equals(currName)) {
          System.out.println("Este nome já existe");
          JOptionPane.showMessageDialog(null, "Este nome já existe");
        }
      }
    }

    return name;
  }

  private void addMessageToChat(String author, String message, boolean isPM) {
    if (message.length() > 0) {
      JLabel messageLabel = new JLabel(author + ": " + message);
      messageLabel.setBorder(new CompoundBorder(messageLabel.getBorder(), new EmptyBorder(5,0,5,0)));
      messageLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
      
      if (isPM) {
        messageLabel.setOpaque(true);
        messageLabel.setBackground(Color.LIGHT_GRAY);
      }

      this.chatPanel.add(messageLabel);
      this.chatPanel.validate();
      this.chatPanel.repaint();
    }
  }

  public static void main(String args[]) throws IOException {
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(ClientScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(ClientScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(ClientScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(ClientScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          new ClientScreen().setVisible(true);
        } catch (IOException ex) {
          Logger.getLogger(ClientScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextArea chatInput;
  private javax.swing.JScrollPane chatInputScroll;
  private javax.swing.JPanel chatPanel;
  private javax.swing.JScrollPane chatScroll;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel nameLabel;
  private javax.swing.JButton submitBtn;
  private javax.swing.JPanel userListPanel;
  private javax.swing.JScrollPane userListScroll;
  // End of variables declaration//GEN-END:variables
}
