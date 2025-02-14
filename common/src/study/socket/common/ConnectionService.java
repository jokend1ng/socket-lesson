package study.socket.common;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Objects;

public class ConnectionService implements AutoCloseable {


    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ConnectionService(Socket socket) throws IOException {
        this.socket = Objects.requireNonNull(socket, "socket is null");
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());

    }

    public void writeMessage(Message input) throws IOException {
        if (input.getText() != null) {
            input.setSendAt(LocalDate.now());
            output.writeObject(input);
            output.flush();
        }

    }

    public Message readMessage() throws IOException, ClassNotFoundException {
        return (Message) input.readObject();
    }


    @Override
    public void close() throws Exception {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("ошибка во время закрытия ресурсов");
        }
    }
}
