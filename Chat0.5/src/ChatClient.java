import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.*;

public class ChatClient extends Frame{

    TextField tfTxt = new TextField();
    TextArea taContent = new TextArea();

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
                System.exit(0);
            }
        });
        tfTxt.addActionListener(new TFListener());  //为输入文本框绑定监听器
        setVisible(true);
        connect();
    }

    //定义客户端TCP的Socket，IP地址为127.0.0.1，端口号为8888
    //该客户端用于和服务器连接，如果连接成功，输出“已连接”的字样
    public void connect(){
        try {
            Socket s = new Socket("127.0.0.1", 8888);
            System.out.println("已连接！");
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /*输入文本框的监听器，用于将输入文本框中输入的内容复制到显示框上*/
    private class TFListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = tfTxt.getText().trim();
            taContent.setText(s);
            tfTxt.setText("");  //输入框清空
        }
    }
}
