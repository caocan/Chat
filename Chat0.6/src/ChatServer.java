import java.io.*;
import java.net.*;

public class ChatServer {



    public static void main(String[] args)  {

        boolean started = false;
        ServerSocket ss = null;
        Socket s = null;
        DataInputStream dis = null;

        //定义一个ServerSocket，该ServerSocket用于接收端口号为8888的客户端
        try {
            ss = new ServerSocket(8888);
        }catch (BindException e){
            System.out.println("端口使用中");
            System.out.println("请关掉相关程序并重新运行服务器！");
            System.exit(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try{
            //服务器端启动
            started = true;
            while (started){
                boolean bConnected = false;

                s = ss.accept();
                System.out.println("一个客户端连了上来");

                //客户端连接上
                bConnected = true;

                dis = new DataInputStream(s.getInputStream());
                while(bConnected) {
                    String str = dis.readUTF(); //阻塞式的，当客户端关闭时，由于服务器还等着读数据，所以出错
                    System.out.println(str);
                }
//                dis.close();
            }
        }catch (EOFException e){    //捕捉到客户端关闭时的异常，就关闭服务器
            System.out.println("客户端关闭了！");
        }
        catch (IOException e) {     //其他错误直接打印
            e.printStackTrace();
        }finally {  //最后一定执行关闭资源
            try {
                if(dis != null) dis.close();
                if(s != null) s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
