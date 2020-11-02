package study;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
	private static int fileCnt = 0;
	private static int dirCnt = 0;
	
	/*
	 * todo.
	 * 폴더 안의 파일 카피 안 됨.
	 */
	public void copyStart(String originalFullFileName) throws IOException {
		File original = new File(originalFullFileName);
		
		if(original.exists()) {
			if(original.isDirectory()) {
				File[] files = original.listFiles();
				
				for(File f : files) {
					if(f.isDirectory()) {
						copy(f.getCanonicalPath());
						copyStart(f.getCanonicalPath());
						
					}else {
						copy(f.getCanonicalPath());
					}
				}
			}else {
				copy(original.getCanonicalPath());
			}
		}
		System.out.println(dirCnt + "개의 디렉터리 / " + fileCnt + "개의 파일 복사.");

	}
	
	/*복사대상, 카피대상 각각의 경로, 파일명을 입력받아 파일 복사 실행.
	 * Todo. 
	 * 파일경로, 파일명 오류에 대한 익셉션 처리 (귀찮아서..안할지도)
	 * 복사 중지-재개 기능.
	 * -> tmp 파일 일시 생성 후 복사 실시. 
	 * -> tmp 삭제   
	*/
	public void copy(String originalFullFileName) throws IOException{
		File original = new File(originalFullFileName);
		
		if(!original.exists()) return;//파일이 존재 하지 않으면 종료.

		if(original.isDirectory()) {
			
			//디렉토리명이 '\'로 끝나면 '\' 삭제 
			if(originalFullFileName.charAt(originalFullFileName.length() - 1) == '\\') {
				originalFullFileName = originalFullFileName.substring(0, originalFullFileName.length() -1);
			}
			
			String newFileName = originalFullFileName + "_copy";
			new File(newFileName).mkdir();//폴더명_copy에 폴더생성	
			dirCnt++;
			
			return;
		}else {
			String[] originalFileNameAndPath = splitFileAddrAndFileName(originalFullFileName);
			
			String newFileName = "copy_" + originalFileNameAndPath[1];//파일명 앞에 copy추가
			
			File newFile = new File(originalFileNameAndPath[0], newFileName);
			
			FileInputStream in = new FileInputStream(original);
			BufferedInputStream bis = new BufferedInputStream(in);
			
			FileOutputStream out = new FileOutputStream(newFile);
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
			
			fileCnt++;
		}
	}
	
	public String[] splitFileAddrAndFileName(String fullFileName) {
		int separatorIdx = fullFileName.lastIndexOf("\\");
		String addr = fullFileName.substring(0, separatorIdx);
		String fileName = fullFileName.substring(separatorIdx + 1);
		
		return new String[] {addr, fileName};
	}
}
