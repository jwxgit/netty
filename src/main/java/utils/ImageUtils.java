package utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ImageUtils {

    private static  ImageUtils imageUtils;
    private  static Map<String ,File > imgs;
    private ImageUtils(){

    }
    private static void loadImageUtils(){
        System.out.println("资源路径:" + System.getProperty("user.dir"));
        String env = System.getProperty("env");
        String path = "";
        if(StringUtils.isNotEmpty(env) && env.equals("pro"))
            path = System.getProperty("user.dir") + "/classes";
        else
            path =  ImageUtils.class.getResource("/").getPath();
        File  file  =new File(path);
        File[]  files = file.listFiles();
        for (File  f:files){
            if(!f.isDirectory()){
                System.out.println("loadfile "+f.getName());
                imgs.put(f.getName(),f);
            }
        }
    }
    public  static  File getFile(String fileName){
        if(imageUtils==null){
            imgs= new HashMap<>();
            imageUtils=new ImageUtils();
            loadImageUtils();
        }
        return  imgs.get(fileName);
    }



    public static  String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes=new byte[(int)file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    public static File base64ToFile(String base64 ,String destPath, String fileName) {
        File file = null;
        //创建文件目录
        String filePath=destPath;
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file=new File(filePath+"/"+fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            return  file ;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  null;
    }



    public static void main(String[] args) {
        String  fileName ="0101.jpg";
        File  f  =ImageUtils.getFile(fileName);
        System.out.println(f.getName());
        String  base64Str =  ImageUtils.fileToBase64(f);
        System.out.println(base64Str);
        File  tempFile =   ImageUtils.base64ToFile(base64Str,"d:",fileName);
        System.out.println(tempFile.getName());


     /* 0101.jpg
        0201.jpg
        0401.jpg
        0402.jpg
        1401.jpg
        desktop.jpg
        finger.jpg
        picture.jpg
        sign.jpg*/


    }
}
