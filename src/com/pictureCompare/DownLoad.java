package com.pictureCompare;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
public class DownLoad {
	public static void main(String[] args) {
		downloadPicture();
	}
	
	public static void downloadPicture() { 
		try {
			Logger logger = Logger.getLogger(DownLoad.class);
			//保存路径
			String save_path = PropertiesUtil.GetValueByKey("save_path");
			//数据库连接
			Connection conn = DBUtil.getConn();
			Statement sta = conn.createStatement();
			
			//查询sql
			String sql_select = PropertiesUtil.GetValueByKey("download_select");
			
			//结果集
			ResultSet rs = sta.executeQuery(sql_select);
			
			//map存储结果集
			Map<String,String> maps = new HashMap<String, String>();
			
			//要下载的列
			String download_urlRow = PropertiesUtil.GetValueByKey("download_urlRow");
			
			//图片下载存储名
			String download_picName = PropertiesUtil.GetValueByKey("download_picName");
			
			
			while(rs.next()){
				//获取哪一列的url
				String urlString = rs.getString(download_urlRow);
				String picName = rs.getString(download_picName);
				maps.put(picName, urlString);
			}
			if (maps!=null) {
				
				for (Map.Entry<String, String> entry:maps.entrySet()) {
					//文件名
					String picName = entry.getKey() + ".png";
					
					//图片url
					String url = entry.getValue();
					
					//图片字节流
					byte[] btPic = ReadAndWrite.getPictureFromNetByUrl(url);
					
					//更新数据库表内容sql
					String sql_update = "" ;
					if(null != btPic && btPic.length > 0){  
						//写入本地
					    ReadAndWrite.writePictureToDisk(btPic, picName,save_path);  
					    sql_update = PropertiesUtil.GetValueByKey("download_success_update");
					    sql_update = MessageFormat.format(sql_update, url);
					    
					    logger.info(url+"处图片下载成功，存储为"+save_path+picName);
					}else{  
					    sql_update = PropertiesUtil.GetValueByKey("download_fail_update");
					    sql_update = MessageFormat.format(sql_update, url);
					    logger.error("没有从该连接获得内容");
					}
					sta.executeUpdate(sql_update);
				}
			}
			
			DBUtil.close(rs, sta, conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    } 
}
