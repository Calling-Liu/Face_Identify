package Start;

import com.jdcloud.apigateway.signature.JdcloudSDKClient;
import com.jdcloud.sdk.utils.BinaryUtils;
import com.google.api.client.http.HttpResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static com.jdcloud.sdk.http.Protocol.HTTP;

public class IdentifyTest {
    //用来存储id
    Queue<String> information_id = new LinkedList<String>();
    //用来存储name
    Queue<String> information_name = new LinkedList<String>();
    //用来存储base64码
    Queue<String> information_base64 = new LinkedList<String>();
    private String res;
    //本代码仅供参考，请根据实际情况进行调整
        public boolean Identify(String imageBase64) throws SQLException {
            String accessKey = "26028389836E04030832AED5601B605B";
            String secretKey = "32AAAC9908546675359E7346B8D46449";
            String endPoint = "aiapi.jdcloud.com";
            String path = "/jdai/face_compare";
            String method = "POST";
            Map<String, String> headers = new HashMap<>();
            Map<String, Object> queryMap = new HashMap<>();
            String temp[] = new String[12];
            FaceDate test = new FaceDate();
            Queue<String>[] result = new Queue[3];
            result = test.query();
            information_id = result[0];
            information_name = result[1];
            information_base64 = result[2];
            for(String s:information_base64)
            {
                String body = "imageBase64_1="+imageBase64+"&imageBase64_2="+s;
                try {
                    HttpResponse response = JdcloudSDKClient.execute(accessKey, secretKey, HTTP,
                            endPoint, path, method, headers, queryMap, body);
                    res = new String(BinaryUtils.toByteArray(response.getContent()));
                    temp = res.split(":");
                    temp = temp[11].split("}");
                    System.out.println(temp[0]);
                    if(Double.parseDouble(temp[0]) >= 0.6){
                        return true;
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            return false;
        }
        /*public  static void main(String[] args) throws SQLException, ClassNotFoundException {
            IdentifyTest test = new IdentifyTest();
            FaceDate F = new FaceDate();
            F.inse("1234");
            //System.out.println(test.Identify(""));
        }*/
}
