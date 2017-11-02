import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    boolean started = false;
    ServerSocket ss = null;

    //创建一个客户端数组
    List<Client> clients = new ArrayList<Client>();

    //启动主线程
    public static void main(String[] args)  {
        new ChatServer().start();
    }

    //服务器主线程的start处理程序，只负责不断接受新的客户端并new线程与之交互
    public void start(){
        //定义一个ServerSocket，该ServerSocket用于接收端口号为8888的客户端
        try {
            ss = new ServerSocket(8888);
            started = true;
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

            while (started){
                Socket s = ss.accept();
                Client c = new Client(s);
                System.out.println("一个客户端连了上来");
                new Thread(c).start();
                //向数组中添加一个客户端
                clients.add(c);
            }
        }
        catch (IOException e) {     //其他错误直接打印
            e.printStackTrace();
        }finally {  //最后一定执行关闭资源
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //一个客户端线程的实现
    class Client implements Runnable{

        private Socket s;
        private DataInputStream dis = null;
        private DataOutputStream dos = null;
        private boolean bConnected = false;

        public Client(Socket s){
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                bConnected = true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        public void send(String str){
            try {
                dos.writeUTF(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);

                    //将客户端发来的信息再转发给客户端
                    for(int i = 0; i < clients.size(); i++){
                        Client c = clients.get(i);
                        c.send(str);
                        System.out.println("发出了一句话!");
                    }
                    /*for(Iterator<Client> it = clients.iterator(); it.hasNext();){
                        Client c = it.next();
                        c.send(str);
                    }*/
                    /*
                    Iterator<Client> it = clients.iterator();
                    while(it.hasNext()){
                        Client c = it.next();
                    }
                    */
                }
            }catch (EOFException e){    //捕捉到客户端关闭时的异常，就关闭服务器
                System.out.println("客户端关闭了！");
            }
            catch (IOException e) {     //其他错误直接打印
                e.printStackTrace();
            }finally {  //最后一定执行关闭资源
                try {
                    if(dis != null) dis.close();
                    if(dos != null) dos.close();
                    if(s != null) {
                        s.close();
                        s = null;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
