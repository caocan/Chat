import java.io.*;
import java.net.*;

public class ChatServer {



    public static void main(String[] args) {

        boolean started = false;

        //定义一个ServerSocket，该ServerSocket用于接收端口号为8888的客户端
        try {
            ServerSocket ss = new ServerSocket(8888);

            //服务器端启动
            started = true;
            while (started){
                boolean bConnected = false;

                Socket s = ss.accept();
                System.out.println("一个客户端连了上来");

                //客户端连接上
                bConnected = true;

                DataInputStream dis = new DataInputStream(s.getInputStream());
                while(bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                }
                dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
