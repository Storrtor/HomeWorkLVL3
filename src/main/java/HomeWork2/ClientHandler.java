package HomeWork2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * Обслуживает клиента (отвечает за связь между клиентом и сервером)
 */
public class ClientHandler {

    private MyServer server;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    private String name;
    private boolean isAuth = false;

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        authentication();
                        readMessages();
                        closeConnectionForAuthClients();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("Закрываем соединение");
                        closeConnectionForAllClients();
                    }
                }
            });
        } catch (IOException ex) {
            System.out.println("Problem with client creating");
        }

    }

    private void readMessages() throws IOException {
        while (true) {
            try {
                String messageFromClient = inputStream.readUTF();
                System.out.println("from " + name + ": " + messageFromClient); //отправка только в консоль, никуда больше
                if (messageFromClient.equals(ChatConstants.STOP_WORD)) {
                    return;
                }
                if (messageFromClient.startsWith(ChatConstants.PRIVATE_MESSAGE)) {
                    server.broadcastMessageToOne(name, "[" + name + "]: " + messageFromClient);
                } else if (messageFromClient.startsWith(ChatConstants.SEND_TO_LIST)) {
                    String[] splittedStr = messageFromClient.split("\\s+");
                    List<String> nicknames = new ArrayList<>();
                    for (int i = 1; i < splittedStr.length - 1; i++) {
                        nicknames.add(splittedStr[i]);
                    }
                } else if (messageFromClient.startsWith(ChatConstants.CLIENTS_LIST)) {
                    server.broadcastClients();
                    //2. Смена ника
                } else if (messageFromClient.startsWith(ChatConstants.CHANGE_NICK)) {
                    String[] splittedStr = messageFromClient.split("\\s+");
                    if (!server.isNickBusy(splittedStr[1])) {
                        String oldName = name;
                        name = splittedStr[1];
                        server.broadcastMessage(oldName + " сменил ник на " + name);
                    } else {
                        sendMsg("Ник уже занят");
                    }
                } else {
                    server.broadcastMessage("[" + name + "]: " + messageFromClient); //распространение сообщения по всем клиентам
                }
            } catch (IndexOutOfBoundsException ex) {
                sendMsg("Неверная команда");
            }

        }
    }


    // /auth login pass
    public void authentication() throws IOException, SQLException {
        long start = System.currentTimeMillis();
        while (true && System.currentTimeMillis() - start < ChatConstants.TIME_OUT) {
            executorService.execute(()->{
                while (System.currentTimeMillis() - start < ChatConstants.TIME_OUT && !isAuth) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!isAuth) {
                    closeConnectionForAllClients();
                }
            });
            String message = inputStream.readUTF();
            if (message.startsWith(ChatConstants.AUTH_COMMAND)) {
                try {
                    String[] parts = message.split("\\s+");
                    String nick = server.getAuthService().getNickByLoginAndPass(parts[1], parts[2]);
                    if (nick != null) {
                        //проверим что пользователя с таким ником нет
                        if (!server.isNickBusy(nick)) {
                            sendMsg(ChatConstants.AUTH_OK + " " + nick);
                            name = nick;
                            server.subscribe(this);
                            server.broadcastMessage(name + " вошел в чат");
                            isAuth = true;
                            return;
                        } else {
                            sendMsg("Ник уже используется");
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException | SQLException ex) {
                    sendMsg("Неверные логин/пароль");
                }
            } else {
                sendMsg("Неверные логин/пароль");
            }
        }
    }


    public void sendMsg(String message) {
        try {
            outputStream.writeUTF(message); //отправка сообщения от сервера клиенту
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnectionForAuthClients() {
        server.unsubscribe(this);
        server.broadcastMessage(name + " left the chat");
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
        executorService.shutdown();
    }

    public void closeConnectionForAllClients() {
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
        executorService.shutdown();
    }


}
