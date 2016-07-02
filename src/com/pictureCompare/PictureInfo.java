package com.pictureCompare;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class PictureInfo {
	public static void main(String[] args) {
		getPictureInfo();
	}

	public static void getPictureInfo() {
		try {
			Logger logger = Logger.getLogger(PictureInfo.class);
			Connection conn = DBUtil.getConn();
			Statement sta = conn.createStatement();
			//查询出所有记录
			String sql_select = PropertiesUtil.GetValueByKey("picInfo_select");
			ResultSet rs = sta.executeQuery(sql_select);
			List<String> lists = new ArrayList<>();
			// 获取哪一列的url
			String picInfo_urlRow = PropertiesUtil.GetValueByKey("picInfo_urlRow");
			while (rs.next()) {
				String urlString = rs.getString(picInfo_urlRow);
				lists.add(urlString);
			}
			if (lists != null) {
				for (String s : lists) {
					
					byte[] btPic = ReadAndWrite.getPictureFromNetByUrl(s);
					// md5
					String md5 = md5(btPic);
					// 图片大小
					long size = btPic.length;
					String sql_update = PropertiesUtil.GetValueByKey("picInfo_update");
				    sql_update = MessageFormat.format(sql_update, size+"",md5,picInfo_urlRow,s);
					sta.executeUpdate(sql_update);
					logger.info("图片信息：   size:"+size+"  md5:"+md5);
				}
			}
			DBUtil.close(rs, sta, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// md5
	protected static String md5(byte[] b) {
		return DigestUtils.md5Hex(b);
	}
}
