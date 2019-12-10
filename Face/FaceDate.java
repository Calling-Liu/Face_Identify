package Start;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

public class FaceDate {
    //用来存储查询出的所有base64码
    private Queue<String> base64 = new LinkedList<String>();
    //用来返回ID
    private Queue<String> id = new LinkedList<String>();
    //用来返回姓名
    private Queue<String> name_query = new LinkedList<String>();

    public boolean inse(String imageBase64) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        //加载数据库驱动程序
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException cne){
            cne.printStackTrace();
        }
        String dburl = "jdbc:mysql://127.0.0.1:3307/face_test?&useSSL=false&serverTimezone=UTC";
        try {

            conn = DriverManager.getConnection(dburl, "root", "123456");
            stmt = conn.createStatement();
            String sql = "insert into face values('3','Liu YuXuan','" + imageBase64 + "')";
            int rs = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stmt.close();
        conn.close();
        return true;
    }

    public Queue<String>[] query() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        //加载数据库驱动程序
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException cne){
            cne.printStackTrace();
        }
        String dburl = "jdbc:mysql://127.0.0.1:3307/face_test?&useSSL=false&serverTimezone=UTC";
        try {
            conn = DriverManager.getConnection(dburl, "root", "123456");
            stmt = conn.createStatement();
            String sql = "select* from face";
            ResultSet rs = stmt.executeQuery(sql);
            while ((rs.next())){
                String ID = String.valueOf(rs.getString("ID"));
                String Base64 = String.valueOf(rs.getString("Base64"));
                String Name = String.valueOf(rs.getString("Name"));
                base64.offer(Base64);
                id.offer(ID);
                name_query.offer(Name);
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Queue<String>[] result = new Queue[3];
        result[0] = id;
        result[1] = name_query;
        result[2] = base64;
        return result;
    }
}

