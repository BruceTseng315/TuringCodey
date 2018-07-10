package turing.turingcodey.core.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class ArduinoUtils {
    private static final String inoFilePath = "D:/arduino/temp";
    private static final  String fileInoName = inoFilePath+"/"+"temp.ino";
    private static final String compilePath = "D:/arduino/build";
    private static final String fileHexName = compilePath+"/temp.ino.hex";
    public final static int timeLimit = 20;

    public static void sweepBeforeCompile(){
        LogUtil.print("下载开始前，清理hex文件和ino文件。。。");
        FileUtlis.createPath(compilePath);

        File hex = new File(fileHexName);
        try{
            if(hex.exists()){
                System.out.println("path:"+hex.getPath()+"name:"+hex.getName());
                hex.delete();
                System.out.println("hex文件清理成功");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        File ino = new File(fileInoName);
        try{
            if(ino.exists()){
                System.out.println("path:"+ino.getPath()+"name:"+ino.getName());
                ino.delete();
                LogUtil.print("ino文件清理成功");
            }
        }catch(Exception e){
            LogUtil.print("编译前清理文件出现异常，信息："+e.getMessage());
        }
    }


    public static JSONObject compileAndUpload(String code)throws Exception{
        JSONObject result = new JSONObject();

        long start = System.currentTimeMillis();
        LogUtil.print("编译前清理ino文件和hex文件...");
        sweepBeforeCompile();
        LogUtil.print("将待编译代码写入ino文件");
        writeIntoFile(code);

        String cmd = "arduino"+" --upload "+fileInoName;
        LogUtil.print("开始编译...");
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(cmd);
            LineNumberReader br = new LineNumberReader(new InputStreamReader(
                    proc.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line).append("\n");
            }
            LogUtil.print("编译信息："+sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (proc != null){
                proc.destroy();
            }
        }

        Runtime.getRuntime().exec(cmd);
        long end = System.currentTimeMillis();
        LogUtil.print("上传成功，共耗时："+(end-start) +" ms");
        result.put("success",true);
        result.put("time",end-start);

        return result;
    }
    public static JSONObject compileUseArduinoBuilder(String code){
        JSONObject result = new JSONObject();

        long start = System.currentTimeMillis();
//        LogUtil.print("编译前清理文件。。。");
        FileUtlis.deleteFile(fileInoName);
        FileUtlis.deleteFile(fileHexName);
        FileUtlis.createFile(fileInoName);
        FileUtlis.writeIntoFile(code,fileInoName);
//        LogUtil.print("文件清理完成");
//        LogUtil.print("编译前清理ino文件和hex文件...");
//        sweepBeforeCompile();
//        LogUtil.print("将待编译代码写入ino文件");
//        writeIntoFile(code);
        int success = 0;
        String cmd ="arduino-builder -hardware \"C:\\Program Files (x86)\\Arduino\\hardware\" " +
                "-tools \"C:\\Program Files (x86)\\Arduino\\tools-builder\" " +
                "-tools \"C:\\Program Files (x86)\\Arduino\\hardware\\tools\" " +
                "-libraries \"C:\\Program Files (x86)\\Arduino\\lib\" " +
                "-fqbn arduino:avr:nano:cpu=atmega328 " +
                "-build-path  " + "\""+compilePath+"\"" +
                " "+"\""+fileInoName+"\"";
        LogUtil.print("cmd:"+cmd);
        try{
            Process p = Runtime.getRuntime().exec(cmd);
            LineNumberReader br = new LineNumberReader(new InputStreamReader(
                    p.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line).append("\n");
            }
            LogUtil.print("编译信息："+sb.toString());
            success = p.waitFor();
            if(success==0){
                LogUtil.print("编译成功");

            }else{
                success = -1;
                LogUtil.print("编译失败");
            }
        }catch(Exception e){
            success = -2;
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        LogUtil.print("本次编译成功，耗时："+(end-start)+" ms");

        if(success==0){
            String hex = CompileUtils.getStrFromFile(fileHexName);
            result.put("success",true);
            result.put("hex",hex);
        }else{
            result.put("success",false);
        }
        return result;
    }
//    public static JSONObject compileAndDownloadUseArduinoBuilder(String code)throws Exception{
//        JSONObject result = new JSONObject();
//
//        long start = System.currentTimeMillis();
//        LogUtil.print("编译前清理ino文件和hex文件...");
//        sweepBeforeCompile();
//        LogUtil.print("将待编译代码写入ino文件");
//        writeIntoFile(code);
////        String cmd = "arduino_debug "+"--verify "+fileInoName;
////        LogUtil.print("开始编译...");
////        Runtime.getRuntime().exec(cmd);
//
//        boolean success = compileUseArduinoBuilder(code);
//        if(success == false) {
//            LogUtil.print("编译失败");
//            return null;
//        }
//        File file = null;
//        //int count = 0;
//        //检测hex文件是否已存在，若20s内检测不到hex文件，结束并返回编译超时；若20s内检测到hex文件，则编译成功，开始进行上传
//
//           // System.out.println("count:"+count);
//            System.out.println("file:"+fileHexName);
//            file = new File(fileHexName);
//            if(!file.exists()){
//                LogUtil.print("未生成hex文件");
//                return null;
//            }
//
//        LogUtil.print("编译成功生成hex文件，开始上传...");
//        String cmdDownload = "/opt/arduino-1.8.2/hardware/tools/avr/bin/avrdude -C\"/opt/arduino-1.8.2/hardware/tools/avr/etc/avrdude.conf\" -v -patmega328p -carduino -P/dev/ttyUSB0 -b57600 -D -Uflash:w:"+fileHexName+":i";
//       LogUtil.print("upload cmd:"+cmdDownload);
//        try {
//            Process p = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",cmdDownload});
//            LineNumberReader br = new LineNumberReader(new InputStreamReader(
//                    p.getInputStream()));
//            StringBuffer sb = new StringBuffer();
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//                sb.append(line).append("\n");
//            }
//            LogUtil.print("编译信息："+sb.toString());
//            p.waitFor();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        long end = System.currentTimeMillis();
//        LogUtil.print("上传成功，共耗时："+(end-start) +" ms");
//        result.put("success",true);
//        result.put("time",end-start);
//
//        return result;
//    }

    //将待编译代码写到文件.ino中
    public static void writeIntoFile(String code){
        File file = new File(inoFilePath);
        try{
            if(!file.exists()) {
                file.mkdir();
            }
        }catch (Exception e) {
            System.out.println("新建目录出错 ");
            e.printStackTrace();
        }
        file = new File(fileInoName);
        if(file.exists()){
            file.delete();
        }
        try{
            file.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(fileInoName);
            fw.write(code);
            fw.flush();
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
