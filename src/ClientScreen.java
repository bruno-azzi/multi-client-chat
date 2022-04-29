
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ClientScreen extends javax.swing.JFrame {

  private Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  public String username;
  public ArrayList<String> userList = new ArrayList<>();
  JDialog nameDialog = new JDialog();

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
    submitBtn = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    nameLabel = new javax.swing.JLabel();
    chatScroll = new javax.swing.JScrollPane();
    chatPanel = new javax.swing.JPanel();
    chatInput = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setResizable(false);

    userListScroll.setBackground(new java.awt.Color(255, 255, 255));

    userListPanel.setBackground(new java.awt.Color(255, 255, 255));
    userListPanel.setLayout(new javax.swing.BoxLayout(userListPanel, javax.swing.BoxLayout.Y_AXIS));
    userListScroll.setViewportView(userListPanel);

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
    chatScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    chatScroll.setAutoscrolls(true);
    chatScroll.setMaximumSize(new java.awt.Dimension(10000, 10000));
    chatScroll.setPreferredSize(new java.awt.Dimension(0, 0));
    chatScroll.setViewportView(chatPanel);

    chatPanel.setBackground(new java.awt.Color(255, 255, 255));
    chatPanel.setToolTipText("");
    chatPanel.setAutoscrolls(true);
    chatPanel.setFocusable(false);
    chatPanel.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
    chatPanel.setLayout(new javax.swing.BoxLayout(chatPanel, javax.swing.BoxLayout.Y_AXIS));
    chatScroll.setViewportView(chatPanel);

    chatInput.setBackground(new java.awt.Color(255, 255, 255));
    chatInput.setToolTipText("Mensagem...");
    chatInput.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        onPressEnter(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(14, 14, 14)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(chatInput, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
              .addComponent(submitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)))
          .addGroup(layout.createSequentialGroup()
            .addComponent(userListScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(chatScroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        .addGap(14, 14, 14))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(14, 14, 14)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(userListScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
          .addComponent(chatScroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addGroup(layout.createSequentialGroup()
            .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(11, 11, 11)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel1)
              .addComponent(nameLabel)))
          .addComponent(chatInput))
        .addGap(15, 15, 15))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  public void connect(Socket socket) {
    try {
      this.socket = socket;
      this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
    this.userList.removeAll(userList);
    this.userListPanel.removeAll();

    for (String user : newUserList) {
      if (!user.equals("null")) {
        this.userList.add(user);
      }
    }

    for (String user : this.userList) {
      JLabel userLabel = new JLabel(user);
      userLabel.setBorder(new CompoundBorder(userLabel.getBorder(), new EmptyBorder(5, 0, 5, 0)));
      userLabel.setFont(new Font("Consolas", Font.PLAIN, 12));

      this.userListPanel.add(userLabel);
    }

    this.userListScroll.validate();
    this.userListScroll.repaint();
    this.userListPanel.validate();
    this.userListPanel.repaint();
  }

  public String getMessageType(String message) {
    boolean isPrivateMessage = message.split(" ")[0].equals("/to");

    return isPrivateMessage ? "privateMessage" : "message";
  }

  private void onSubmitClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSubmitClick
    sendMessageFromInput();
  }//GEN-LAST:event_onSubmitClick

  private void onPressEnter(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_onPressEnter
    if (evt.getKeyCode() == KeyEvent.VK_ENTER){
      sendMessageFromInput();
    }
  }//GEN-LAST:event_onPressEnter

  public void sendMessageFromInput() {
    String messageString = this.chatInput.getText();
    String type = getMessageType(messageString);

    Message message = new Message(type, this.username, messageString);

    this.sendNewMessage(message);
    this.chatInput.setText("");
    this.chatInput.requestFocus();
  }
  
  private void initClient() throws IOException {
    Socket socket = new Socket("localhost", 80);

    this.connect(socket);

    askForUsername();
  }

  private void askForUsername() {
    Boolean invalidName = false;
    String name = JOptionPane.showInputDialog("Digite seu nome");

    if (this.userList.size() > 0) {
      for (String currName : this.userList) {
        if (name.equals(currName)) {
          JOptionPane.showMessageDialog(null, "Este nome já existe");
          invalidName = true;
          askForUsername();
          break;
        }
      }
    }
    
    if (!invalidName) {
      this.username = name;
      this.nameLabel.setText(name);
      
      this.listenForMessage();
      this.sendNewMessage(new Message("newUserConnected", username, " Entrou no chat!"));
    }
  }

  private void addMessageToChat(String author, String message, boolean isPM) {
    if (message.length() > 0) {
      JLabel messageLabel = new JLabel(author + ": " + message);
      messageLabel.setBorder(new CompoundBorder(messageLabel.getBorder(), new EmptyBorder(5, 0, 5, 0)));
      messageLabel.setFont(new Font("Consolas", Font.PLAIN, 12));

      if (isPM) {
        messageLabel.setOpaque(true);
        messageLabel.setBackground(Color.LIGHT_GRAY);
      }

      this.chatPanel.add(messageLabel);
      this.chatScroll.validate();
      this.chatScroll.repaint();
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
  private javax.swing.JTextField chatInput;
  private javax.swing.JPanel chatPanel;
  private javax.swing.JScrollPane chatScroll;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel nameLabel;
  private javax.swing.JButton submitBtn;
  private javax.swing.JPanel userListPanel;
  private javax.swing.JScrollPane userListScroll;
  // End of variables declaration//GEN-END:variables
}
