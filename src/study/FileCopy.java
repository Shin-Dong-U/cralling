package study;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
	private static int sequence = 0;
	
	public void copy(String address, String imageName) throws IOException{
		File original = new File(address, imageName);
		
		String copyFileName = "copy_" + ++sequence + imageName;
		File copyFile = new File(address, copyFileName);
		
		FileInputStream in = new FileInputStream(original);
		BufferedInputStream bis = new BufferedInputStream(in);
		
		FileOutputStream out = new FileOutputStream(copyFile);
		BufferedOutputStream bos = new BufferedOutputStream(out);
		
		int tmp = 0;
		while( (tmp = bis.read()) != -1){
			bos.write(tmp);
		}
		bos.flush();
		
		bos.close();
		out.close();
		bis.close();
		in.close();
	}
}
