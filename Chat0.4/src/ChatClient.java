import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
