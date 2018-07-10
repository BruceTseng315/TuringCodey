package turing.turingcodey;

import turing.turingcodey.core.utils.CompileUtils;

import java.io.*;
import java.net.Socket;

public class WifiArduinoTest {
    public static void main(String[] args) {
        String ip = "172.17.3.122";
        int port = 8080;
        String filePath = "D:\\arduinoHex\\temp.ino.hex";
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("客户端启动成功");
            File file = new File(filePath);
           // InputStream in = null;
          //  in = new FileInputStream(file);
            //BufferedReader br = new BufferedReader(new InputStreamReader(in));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            String hex = CompileUtils.getStrFromFile(filePath);
            pw.print(hex);
            pw.flush();

            pw.close();
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
