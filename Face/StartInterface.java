package Start;
/**
 * @author 17130130226 Liu YiLei
 * @paramn For Start
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Sign extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH = 880;
    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_X_ORIGIN = 400;
    private static final int FRAME_Y_ORIGIN = 200;
    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 30;
    JButton in, test;
    Sign my;
    public Sign() {
        setTitle("面部识别");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//将框体置为全屏
        Rectangle bounds = new Rectangle(screenSize);
        this.setBounds(bounds);
        this.setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);    //不使用布局管理器
        my = this;

        //in sign in button
        in = new JButton("开始识别");
        in.setBounds(700, 660, BUTTON_WIDTH + 100, BUTTON_HEIGHT + 10);
        add(in);
        //test button
        test = new JButton("test");
        test.setBounds(880, 760, BUTTON_WIDTH + 100, BUTTON_HEIGHT + 10);
        add(test);

        //add label
        JLabel title = new JLabel();
        title.setBounds(480,100,800,200);
        title.setText("面部识别");
        title.setFont(new Font(null, JLabel.CENTER,150));
        add(title);


        //add action listener
        in.addActionListener(this);      //add the frame as an action
        test.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        JButton clickButton = (JButton) event.getSource();   //返回产生事件的对象
        if (clickButton == in) {
            setVisible(false);
            EventQueue.invokeLater(new Runnable(){
                public void run() {
                    try {
                        SelectPictureInterface frame = new SelectPictureInterface(my);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            dispose();
        }
    }
}
public class StartInterface {
    public static void main(String[] args){
        Sign my_start = new Sign();
    }
}