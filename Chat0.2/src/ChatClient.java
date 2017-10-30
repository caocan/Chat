import java.awt.*;

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
        pack(); //这个方法就是依据你放置的组件设定窗口的大小 使之正好能容纳你放置的所有组件
        setVisible(true);
    }
}
