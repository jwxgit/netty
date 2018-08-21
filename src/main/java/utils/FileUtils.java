package utils;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class FileUtils {

    private static int DEFAULT_BUFFER_SIZE = 1024 * 200;

//    public static int byteArrayToInt(byte[] b) {
//        return b[3] & 0xFF |
//                (b[2] & 0xFF) << 8 |
//                (b[1] & 0xFF) << 16 |
//                (b[0] & 0xFF) << 24;
//    }
//
//    public static byte[] intToByteArray(int a) {
//        return new byte[]{
//                (byte) ((a >> 24) & 0xFF),
//                (byte) ((a >> 16) & 0xFF),
//                (byte) ((a >> 8) & 0xFF),
//                (byte) (a & 0xFF)
//        };
//    }

    public static int byteArrayToInt(byte[] b) {
        return (b[0] & 0xFF) << 24|
                (b[1] & 0xFF) << 16|
                (b[2] & 0xFF) << 8|
                b[3] & 0xFF;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) (a & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 24) & 0xFF)
        };
    }

    /**
     * 文件转化成base64字符串
     * @param file 文件的位置
     * @return 返回Base64编码过的字节数组字符串
     */
    public static String coverFileToString(File file) {// 将文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取文件字节数组
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * base64字符串转为文件
     * @param fileStr base64编码的code
     * @param filePath 文件生成的位置
     * @return
     */
    public static boolean generateFile(String fileStr ,String filePath) { // 对字节数组字符串进行Base64解码并生成文件
        if (StringUtils.isEmpty(fileStr)) // 文件数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(fileStr);
//            for (int i = 0; i < b.length; ++i) {
//                if (b[i] < 0) {// 调整异常数据
//                    b[i] += 256;
//                }
//            }
            OutputStream out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public static byte[] intToByteArray1(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    public static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

        public static void main(String args[]){
//        File file = new File("/APP/image/2.jpg");
//        FileUtils.generateFile(FileUtils.coverFileToString(file),"/APP/image/" + UUID.randomUUID() + ".jpg");
            return;
    }

}
