package Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;


class base64{
    public static String GETImageStr(String imgPath){
        String imgFile=imgPath;//待处理文件文件名
        InputStream in = null;
        byte[] data=null;
        String encode=null;//转换后的base64码
        final Base64.Decoder encoder = Base64.getDecoder();
        try {
            // 读取图片字节数组
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = Base64.getEncoder().encodeToString(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return encode;
    }
}


public class ConfirmPicture extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH=500;
    private static final int FRAME_HEIGHT=700;
    private static final int BUTTON_WIDTH=160;
    private static final int BUTTON_HEIGHT=40;
    private static final int LABEL_WIDTH=640;
    private static final int LABEL_HEIGHT=480;
    private JButton Confirm, resetting;
    private JLabel picture;
    private String phouoName;
    private Sign start;
    public ConfirmPicture(String photoname, Sign my_start){
        start = my_start;
        phouoName=photoname;
        setBackground(Color.GRAY);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//将框体置为全屏
        Rectangle bounds = new Rectangle(screenSize);
        this.setBounds(bounds);
        this.setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        ImageIcon cc=new ImageIcon(phouoName);
        picture=new JLabel();
        picture.setIcon(cc);
        picture.setBounds(480,40,LABEL_WIDTH,LABEL_HEIGHT);
        add(picture);
        Confirm=new JButton("确认");
        Confirm.setBounds(480,700,BUTTON_WIDTH,BUTTON_HEIGHT);
        add(Confirm);
        resetting=new JButton("重置");
        resetting.setBounds(960,700,BUTTON_WIDTH,BUTTON_HEIGHT);
        add(resetting);
        resetting.addActionListener(this);
        Confirm.addActionListener(this);
        setVisible(true);
    }
    public String getPhouoName(){
        return phouoName;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
            String base1;
            String result;
            String base64=this.getPhouoName();
            JButton clickButton = (JButton)e.getSource();
        if(clickButton == Confirm){
            base1=new base64().GETImageStr("tmp.jpg");
            boolean result_check = false;
            IdentifyTest test = new IdentifyTest();
            /*FaceDate in = new FaceDate();
            try {
                in.inse(base1);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }*/
            try {
                result_check = test.Identify(base1);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //将图片转码base64，识别图片中人是否一致
            if(result_check){
               JOptionPane.showMessageDialog(this,"Verification successful.Similarity degree is: checked!,","message",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this,"匹配失败","message",JOptionPane.ERROR_MESSAGE);
          }
            dispose();
            start.setVisible(true);
        }else{
            //重新打开拍照框
            dispose();
            SelectPictureInterface return_select = new SelectPictureInterface(start);
        }
    }

}



