package socket;

import java.io.*;
import java.net.Socket;
import JSON.*;
/**
 ***************************************************************
 * 项目名称：JavaThread 程序名称：JabberClient
 * 作者：Administrator
 * @version
 ***************************************************************
 */
public class JabberClient {
    /**
     * 方法名：main
     *
     * @param @param args
     * @return void
     */
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            //客户端socket指定服务器的地址和端口号
            socket = new Socket("192.168.199.232", JabberServer.PORT);
            System.out.println("Socket=" + socket);
            //同服务器原理一样
            br = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())));
            JSONObject test = new JSONObject();
            test.put("error_code","2000");

            for (int i = 0; i < 10; i++) {
                pw.println(test.toString());
                pw.flush();
                String str = br.readLine();
                System.out.println(str);
            }
            pw.println("END");
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("close......");
                br.close();
                pw.close();
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}