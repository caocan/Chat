package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatClient extends Frame{

    Socket s;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;

    TextField tfTxt = new TextField();
    TextArea taContent = new TextArea();

    Thread trecv = new Thread(new RecvThread());

    public static void main(String[] args) {

        new ChatClient().launchFrame();
    }

    public void launchFrame(){
        setLocation(400, 300);  //设置聊天界面的起始位置
        this.setSize(300, 300); //设置聊天界面的大小
        add(tfTxt, BorderLayout.SOUTH); //添加显示框
        add(taContent, BorderLayout.NORTH); //添加输入框
        pack();  //这个方法就是依据你放置的组件设定窗口的大小 使之正好能容纳你放置的所有组件
        this.addWindowListener(new WindowAdapter() {    //添加窗口关闭事件
            @Override
            public void windowClosing(WindowEvent e) {
                disconnect();
                System.exit(0);
            }
        });
        tfTxt.addActionListener(new TFListener());  //为输入文本框绑定监听器
        setVisible(true);
        connect();

        trecv.start();
    }

    //定义客户端TCP的Socket，IP地址为127.0.0.1，端口号为8888
    //该客户端用于和服务器连接，如果连接成功，输出“已连接”的字样
    public void connect(){
        try {
            s = new Socket("127.0.0.1", 8888);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            System.out.println("已连接！");
            bConnected = true;
            //启动对客户端的接收线程
//            new Thread(new RecvThread()).start();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //关闭连接，资源清理
    public void disconnect(){
        try {
            dos.close();
            dis.close();    //先把读取通道关闭
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try{
            bConnected = false;
            trecv.join();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                dos.close();
                dis.close();    //先把读取通道关闭
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
    }

    /*输入文本框的监听器，用于将输入文本框中输入的内容复制到显示框上*/
    private class TFListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfTxt.getText().trim();
//            taContent.setText(str);
            tfTxt.setText("");  //输入框清空

            try {
//                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(str);
                dos.flush();
                //dos.close();  //写完一行就关了，所以只能输出一行
            }
            catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }

    //客户端的接收线程
    private class RecvThread implements Runnable {
        @Override
        public void run() {
            try {
                while(bConnected){
                    String str = dis.readUTF();
//                    System.out.println(str);
                    taContent.setText(taContent.getText() + str + '\n');
                }
            } catch (SocketException e){
                System.out.println("退出了，bye bye!");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
