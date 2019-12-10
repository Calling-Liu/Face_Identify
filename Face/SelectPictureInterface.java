package Start;
/**
 * @author 17130130226 Liu YiLei
 * @paramn For Select Picture
 */
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SelectPictureInterface extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH = 880;
    private static final int FRAME_HEIGHT = 700;
    private static final int FRAME_X_ORIGIN = 400;
    private static final int FRAME_Y_ORIGIN = 200;
    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 30;
    private  JButton in, cancel;
    private  Sign start;
    private  boolean flag = true;
    private  JPanel title;
    VideoCapture camera;
    private  VideoCap videoCap;
    private  static  Mat my_mat;
    private static File tmp = new File(new File("tmp.jpg").getAbsolutePath());

    static class Mat2Image {
        Mat mat = new Mat();
        BufferedImage img;
        byte[] dat;

        public Mat2Image() {
        }
        public Mat2Image(Mat mat) {
            getSpace(mat);
        }
        public void getSpace(Mat mat) {
            this.mat = mat;
            int w = mat.cols(), h = mat.rows();
            if (dat == null || dat.length != w * h * 3)
                dat = new byte[w * h * 3];
            if (img == null || img.getWidth() != w || img.getHeight() != h
                    || img.getType() != BufferedImage.TYPE_3BYTE_BGR)
                img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        }

        BufferedImage getImage(Mat mat){

            getSpace(mat);
            mat.get(0, 0, dat);

            img.getRaster().setDataElements(0, 0,
                    mat.cols(), mat.rows(), dat);
            return img;
        }
        static{
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
    }
    static  class VideoCap {
        static{
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }

        VideoCapture cap;
        Mat2Image mat2Img = new Mat2Image();

        VideoCap(){
            cap = new VideoCapture();
            cap.open(0);
        }
        BufferedImage getOneFrame() {
            cap.read(mat2Img.mat);
            my_mat = mat2Img.mat;
            return mat2Img.getImage(mat2Img.mat);
        }
    }
    //for threads
    class MyThread extends Thread{
        @Override
        public void run() {
            while(flag){
                repaint();
                try { Thread.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public SelectPictureInterface(Sign my_start) {
        //setSize(FRAME_WIDTH, FRAME_HEIGHT);
        //setResizable(false);
        setTitle("开始识别");
        //setLocation(FRAME_X_ORIGIN, FRAME_Y_ORIGIN);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//将框体置为全屏
        Rectangle bounds = new Rectangle(screenSize);
        this.setBounds(bounds);
        this.setUndecorated(true);
        setBackground(Color.GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);    //不使用布局管理器
        start = my_start;

        //add label
        title = new JPanel();
        title.setBounds(380,100,880,500);

        //select  button
        in = new JButton("选取照片");
        in.setBounds(550, 700, BUTTON_WIDTH + 100, BUTTON_HEIGHT + 10);
        add(in);

        //cancel button
        cancel = new JButton("取消");
        cancel.setBounds(870, 700, BUTTON_WIDTH + 100, BUTTON_HEIGHT + 10);
        add(cancel);

        videoCap = new VideoCap();
        //add action listener
        in.addActionListener(this);      //add the frame as an action
        cancel.addActionListener(this);
        add(title);

        new MyThread().start();
        setVisible(true);
    }
    public void paint(Graphics g){
        super.paint(g);
        g = title.getGraphics();
        g.drawImage(videoCap.getOneFrame(), 110, 0, 600,480,this);
    }
    public void actionPerformed(ActionEvent event) {
        JButton clickButton = (JButton) event.getSource();   //返回产生事件的对象
        if (clickButton == cancel) {
            dispose();
            start.setVisible(true);
        } else {
            MatOfInt rate = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 100);
            flag = false;
            Imgcodecs.imwrite(String.valueOf(tmp), my_mat, rate);
            dispose();
            ConfirmPicture confirm = new ConfirmPicture("tmp.jpg",start);
        }
    }
}