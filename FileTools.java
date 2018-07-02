import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

/**
 * 
 * @User：HuangXinyu
 * 
 *                  创建时间：2014-5-14 下午1:09:16
 * 
 *                  修改备注：
 * @version 1.0
 * 
 */
@SuppressWarnings("all")
public class FileTools {
    
    /**
     * 图片转String
     * 
     * @param fileUrl 文本路径
     * @param imgPath 图片路径
     */
    public static void ImgToString(String fileUrl,String imgPath) {

        try {
            StringBuffer sb = new StringBuffer();
            FileInputStream fis = new FileInputStream(imgPath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();

            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = fis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
            // 得到图片的字节数组
            byte[] result = bos.toByteArray();

            //System.out.println("++++" + byte2HexStr(result));
            // 字节数组转成十六进制
            String str = byte2HexStr(result);
            
            //将十六进制串保存到txt文件中
             
            PrintWriter pw = new PrintWriter(new FileWriter(fileUrl));
            pw.println(str);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 实现字节数组向十六进制的转换方法一
     * 
     * @param b
     * @return
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
    
    /**
     * 读取文本的Byte转换成图片
     * 
     * @param path
     *            文本路径
     * @param fileUrl
     *            图片保存路径
     * @throws Exception
     */
    public static void stringToImg(String path, String fileUrl)
            throws Exception {
        FileTools to = new FileTools();
        InputStream is = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String str = null;
        StringBuilder sb = new StringBuilder();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        to.saveToImgFile(sb.toString().toUpperCase(), fileUrl);
    }
    
    private void saveToImgFile(String src, String output) {
        if (src == null || src.length() == 0) {
            return;
        }
        try {
            FileOutputStream out = new FileOutputStream(new File(output));
            byte[] bytes = src.getBytes();
            for (int i = 0; i < bytes.length; i += 2) {
                out.write(charToInt(bytes[i]) * 16 + charToInt(bytes[i + 1]));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int charToInt(byte ch) {
        int val = 0;
        if (ch >= 0x30 && ch <= 0x39) {
            val = ch - 0x30;
        } else if (ch >= 0x41 && ch <= 0x46) {
            val = ch - 0x41 + 10;
        }
        return val;
    }
    
    
    public static void main(String[] args){
        String fileUrl="D:\\doc\\笔记\\imgString转换\\tuoxiabameinv.txt";
//        String imgPath="D:\\doc\\笔记\\tuoxiabameinv.jpg";
//        ImgToString(fileUrl,imgPath);
        String imgPath2="D:\\doc\\笔记\\imgString转换\\tuoxiabameinv_2.JPG";
        try
        {
            stringToImg(fileUrl,imgPath2);
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

}
