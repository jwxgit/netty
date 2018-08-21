package utils;


import java.security.MessageDigest;

/**
 * md5加密算法。把字符串加密成MD5
 * @author Administrator
 *
 */
public class Md5Utils {
	

	
	 /*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++){
        	byteArray[i] = (byte) charArray[i];  
        }
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++) {  
           int val = ((int) md5Bytes[i]) & 0xff;  
           if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString().toUpperCase();  
  
    }  
    
    
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }
    
    public final static String toMD5Str(String str)  { // MD5加密算法
        String s = str == null ? "" : str; // 如果为空则返回""
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' }; // 字典

        try {
          byte[] strTemp = s.getBytes("UTF-8"); // 获得二进制
          MessageDigest mdTemp = MessageDigest.getInstance("MD5"); // 加密器
          mdTemp.update(strTemp); // 执行加密
          byte[] md = mdTemp.digest(); // 加密结果
          int j = md.length;
          char[] value = new char[j * 2]; // 字符数组
          int k = 0;
          for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            value[k++] = hexDigits[byte0 >>> 4 & 0xf];
            value[k++] = hexDigits[byte0 & 0xf];
          }
          return new String(value);
        } catch (Exception e) {
          return null;
        }
      }
    
    
    
    public static void main(String[] args) {
//		String key = "jwxsecure";
//		String nowDate ="2016-04-21";
//		String username = "13418667517";
//		//jwxsecure2016-04-2013418667517
//		System.out.println(key + nowDate+username);
//    	String stringMd5  = string2MD5(key + nowDate+username);
    	String str = "123456";
//    	System.out.println(string2MD5(a));
//    	System.out.println(string2MD5(string2MD5(a)));
    	
    	  System.out.println("原始：" + str);  
          System.out.println("MD5后：" + string2MD5(str));  
          System.out.println("加密的：" + convertMD5(str));  
          System.out.println("解密的：" + convertMD5(convertMD5(str))); 
	}
  

}
