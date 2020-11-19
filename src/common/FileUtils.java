package common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FileUtils {
	
	URL url;
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
	
	File f = null;
	FileWriter fw = null;

	//폴더 생성
	public boolean makeFolder(String folderName) {
		boolean isMake = false;
		File f = new File(folderName);

		if(!f.exists()) isMake = f.mkdirs();
		
		return isMake;
	}
	
	//html 파일 생성.
	public boolean makeHtmlFile(String fileName, String html) throws IOException {
		f = new File(fileName);
  		fw = new FileWriter(f);
  		fw.write(html);
  		fw.flush();
  		
  		return true;
	}
	
	public boolean downloadImage(String addr, String src, String imgFolderName) {
		try {
			url = new URL(src);
			HttpsURLConnection  con = (HttpsURLConnection)url.openConnection();
			
			con.setRequestProperty("Referer", addr); 
						
			bis = new BufferedInputStream(con.getInputStream());
			
			makeFolder(imgFolderName);
			
			String fileName = src.substring( src.lastIndexOf('/') + 1);
			
			File f = new File(imgFolderName + "\\" + fileName);
			 
			bos = new BufferedOutputStream(new FileOutputStream(f));
			
			int data = 0;
			while((data = bis.read()) != -1) {
				bos.write(data);
			}
				
			bos.flush();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(bis != null) bis.close();
				if(bos != null) bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
}
