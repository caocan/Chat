import java.io.IOException;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        //定义一个ServerSocket，该ServerSocket用于接收端口号为8888的客户端
        try {
            ServerSocket ss = new ServerSocket(8888);
            while (true){
                Socket s = ss.accept();
                System.out.println("一个客户端连了上来");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
