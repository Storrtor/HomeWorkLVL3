package HomeWork2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Обслуживает клиента (отвечает за связь между клиентом и сервером)
 */
public class ClientHandler {

    private MyServer server;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private String name;

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
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(authentication() == true) {
                            readMessages();
                            closeConnectionForAuthClients();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("Закрываем соединение");
                        closeConnectionForAllClients();
                    }
                }
            }).start();
        } catch (IOException ex) {
            System.out.println("Problem with client creating");
        }

    }

    private void readMessages() throws IOException {
        while (true) {
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
            } else {
                server.broadcastMessage("[" + name + "]: " + messageFromClient); //распространение сообщения по всем клиентам
            }
        }
    }

    /**
     * 1. Разобраться с кодом.
     * 2. Добавить отключение неавторизованных пользователей по таймауту
     * (120 сек. ждём после подключения клиента, и если он не авторизовался за это время, закрываем соединение).
     */
    // /auth login pass
    private boolean authentication() throws IOException {
        long start = System.currentTimeMillis();
        while (true && System.currentTimeMillis() - start < ChatConstants.TIME_OUT) {
            try{
                this.socket.setSoTimeout(ChatConstants.TIME_OUT);
                String message = inputStream.readUTF();
                if (message.startsWith(ChatConstants.AUTH_COMMAND)) {
                    String[] parts = message.split("\\s+");
                    String nick = server.getAuthService().getNickByLoginAndPass(parts[1], parts[2]);
                    if (nick != null) {
                        //проверим, что такого пока нет
                        if (!server.isNickBusy(nick)) {
                            sendMsg(ChatConstants.AUTH_OK + " " + nick);
                            name = nick;
                            server.subscribe(this);
                            server.broadcastMessage(name + " entered the chat");
                            socket.setSoTimeout(0);
                            return true;
                        } else {
                            sendMsg("Nick is already in use");
                        }
                    } else {
                        sendMsg("Incorrect login/pass");
                    }
                }
            } catch (SocketException | EOFException | SocketTimeoutException se) {
                System.out.println("Закрываем соединение");
                se.printStackTrace();
                closeConnectionForAllClients();
            }
        }
        return false;
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
    }


}
