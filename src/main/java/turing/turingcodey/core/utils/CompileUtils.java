package turing.turingcodey.core.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class CompileUtils {
    private static final String inoFilePath = "D:/arduino";
    private static final  String fileName = inoFilePath+"/"+"temp.ino";
    private static final String hexFileName = "D:/arduinoHex/temp.ino.hex";

    public static JSONObject compile(String code){
        JSONObject result = new JSONObject();
        long start = System.currentTimeMillis();
        LogUtil.print("编译开始前清理ino文件和hex文件");
        sweepFile();
        writeIntoFile(code);
        String cmd = "arduino_debug "+"--verify "+fileName;
        try {
            Runtime.getRuntime().exec(cmd);
        }catch (Exception e){
            e.printStackTrace();
        }
        File file = null;
        int count = 0;
        while(count < 30){
            System.out.println("count:"+count);
            System.out.println("file:"+hexFileName);
            file = new File(hexFileName);
            if(file.exists()){
                break;
            }
            count++;
            try {
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(count >= 30)  {
            System.out.println("超时");
            result.put("success",false);
            result.put("msg","编译超时");
            return result;
        }
        String hex = getStrFromFile(hexFileName);
        System.out.println("count:"+count);
        System.out.println(">>>>>>>>>hex:"+hex);
        LogUtil.print("编译结束后清理ino文件和hex文件");
        sweepFile();
        long end = System.currentTimeMillis();
        result.put("success",true);
        result.put("time",end-start);
        result.put("hex",hex);
        return result;
    }

    public static void writeIntoFile(String code){
        File file = new File(inoFilePath);
        try{
            if(!file.exists()) {
                file.mkdir();
            }
        }catch (Exception e) {
            System.out.println("新建目录出错");
            e.printStackTrace();
        }
        file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
        try{
            file.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(code);
            fw.flush();
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String getStrFromFile(String fileName){
        FileReader fr = null;
        BufferedReader br = null;
        String str = "";
        try {
             fr = new FileReader(fileName);
             br = new BufferedReader(fr);
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            String hexLine = null;
            while((hexLine = br.readLine()) != null) {
            str = str + hexLine+"\n";
         }
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            fr.close();
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return str;
    }
    //删除ino文件和hex文件
    public static void sweepFile(){
       // LogUtil.print("编译开始前，清理in文件和hex文件。。。");
        File ino = new File(fileName);
        try{
            if(ino.exists()){
                System.out.println("path:"+ino.getPath()+"name:"+ino.getName());
                ino.delete();
                System.out.println("ino文件清理成功");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        File file = new File(hexFileName);
        try{
            if(file.exists()){
                System.out.println("path:"+file.getPath()+"name:"+file.getName());
                file.delete();
                System.out.println("hex文件清理成功!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
