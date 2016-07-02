package com.pictureCompare;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadAndWrite {

	/** 
	 * 根据地址获得数据的字节流 
	 * @param strUrl 网络连接地址 
	 * @return 
	 */  
	protected static byte[] getPictureFromNetByUrl(String strUrl){  
		try {  
			URL url = new URL(strUrl);  
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
			conn.setRequestMethod("GET");  
			conn.setConnectTimeout(5 * 1000);  
			InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
			
			byte[] btImg = readInputStream(inStream);//得到图片的二进制数据 
			
			
			return btImg;  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return null;  
	}  
    /** 
     * 从输入流中获取数据 
     * @param inStream 输入流 
     * @return 
     * @throws Exception 
     */  
	protected static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    }
	
	 /** 
     * 将图片写入到磁盘 
     * @param img 图片数据流 
     * @param fileName 文件保存时的名称 
     */  
	protected static void writePictureToDisk(byte[] pic, String fileName,String savePath){  
        try {  
        	File sf=new File(savePath);  
            if(!sf.exists()){  
                sf.mkdirs();  
            } 
            FileOutputStream fops = new FileOutputStream(sf.getPath()+"\\"+fileName);  
            fops.write(pic);  
            fops.flush();  
            fops.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

}
