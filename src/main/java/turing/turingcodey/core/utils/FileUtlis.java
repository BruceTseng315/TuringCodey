package turing.turingcodey.core.utils;

import java.io.File;
import java.io.FileWriter;

public class FileUtlis {
    public final static String path = "D:/arduino/download";
    public final static String fileName = path+"/temp.hex";
    public static void writeIntoFile(String hex){
        File file = new File(path);
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
            fw.write(hex);
            fw.flush();
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
public static void download()throws  Exception{
        LogUtil.print("开始下载文件。。。");

        String cmd = "avrdude -C\"C:\\Program Files (x86)\\Arduino\\hardware\\tools\\avr/etc/avrdude.conf\" -v -patmega328p -carduino -PCOM4 -b57600 -D -Uflash:w:D:/arduino/download/temp.hex:i";

        Runtime.getRuntime().exec(cmd);

         //
    //
    //
    //sweepFile();
}
    //删除ino文件和hex文件
    public static void sweepFile(){
        // LogUtil.print("下载开始前，清理hex文件。。。");
        File hex = new File(fileName);
        try{
            if(hex.exists()){
                System.out.println("path:"+hex.getPath()+"name:"+hex.getName());
                hex.delete();
                System.out.println("ihex文件清理成功");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    //新建文件
    public static void createFile(String fileName){
        File file = new File(fileName);
        try {
            if (file.exists()) {
                return;
            } else {
                file.createNewFile();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //新建文件夹
    public static void createPath(String path){
        File file = new File(path);
        try {
            if (file.exists()) {
                return;
            } else {
                file.mkdir();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //删除文件
    public static void deleteFile(String fileName){
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                return;
            } else {
                file.delete();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void writeIntoFile(String code,String fileName){

        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(code);
            fw.flush();
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
