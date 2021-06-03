package HomeWork2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends JFrame {

    private Socket socket;

    private JTextArea chatArea;

    private JTextField inputField;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private File file;


    public Client() {
        try {
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initGUI();
    }


    public void initGUI() {
        setBounds(600, 300, 500, 500);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Message area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        //down pannel
        JPanel panel = new JPanel(new BorderLayout());
        JButton sendButton = new JButton("Send");
        panel.add(sendButton, BorderLayout.EAST);
        inputField = new JTextField();
        //inputField.setBounds(100,100,150,30);
        panel.add(inputField, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                try {
                    outputStream.writeUTF(ChatConstants.STOP_WORD);
//                    closeConnection();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        setVisible(true);
    }

    private void openConnection() throws IOException {
        socket = new Socket(ChatConstants.HOST, ChatConstants.PORT);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //авторизация
                    while (true) {
                        String strFromServer = inputStream.readUTF();
                        if (strFromServer.startsWith(ChatConstants.AUTH_OK)) {
                            String[] parts = strFromServer.split("\\s+");
                            String nick = parts[1];
                            makeFile(nick);
                            break;
                        }
                        chatArea.append(strFromServer);
                        chatArea.append("\n");
                    }
                    //чтение
                    while (true) {
                        //Загрузка сообщений
                        String strFromServer = inputStream.readUTF();

                        if (strFromServer.equals(ChatConstants.STOP_WORD)) {
                            break;
                        } else if (strFromServer.startsWith(ChatConstants.CLIENTS_LIST)) {
                            chatArea.append("Сейчас онлайн " + strFromServer);
                        } else {
                            chatArea.append(strFromServer);
                        }
                        chatArea.append("\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void closeConnection() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void sendMessage() {
        if (!inputField.getText().trim().isEmpty()) {
            try {
                if(file != null) {
                    makeHistory(file);
                }
                outputStream.writeUTF(inputField.getText());
                inputField.setText("");
                inputField.grabFocus();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Send error occured");
            }
        }
    }

    public File makeFile(String nick) {
        System.out.println("Заходим в метод");
        file = new File("chatHistory" + nick + ".txt");
        return file;
    }


    public void makeHistory(File file) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        System.out.println("Пишем в файл");
        try (
                DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file, true))
        ) {
            dataOutputStream.writeUTF(inputField.getText());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//            }
//        }).start();


    }

    public void loadHistory(File file) {
        try (
                DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))
        ) {
            while (dataInputStream.available() != -1) {
                chatArea.append(String.valueOf(dataInputStream));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(Client::new);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
