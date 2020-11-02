package study;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * 파일 및 디렉터리 복사기능 제공.
 * -> 복사 파일 : 파일명 앞에 copy_ 고정
 * -> 복사 폴더 : 폴더명 뒤에 _copy 고정
 * 
 * todo.
 * 1. 이동할 경로 입력 받기
 * 2. 폴더 카피시 원본 네이밍 그대로 유지하여 새로운 경로에 복사. 
 * 3. 만약 같은 경로에 카피한다면 _copy 문구 생성
 * 4. 예외처리 
 */
public class FileCopy {
	private static int fileCnt = 0;
	private static int dirCnt = 0;
	
	public void copyStart(String originalFullFileName) throws IOException {
		File original = new File(originalFullFileName);
		
		if(original.exists()) {
			if(original.isDirectory()) {//디렉토리면 
				File[] files = original.listFiles();//하위목록 받아오기
				
				for(File f : files) {
					if(f.isDirectory()) {
						copy(f.getCanonicalPath());//단순 폴더 생성. 
						copyStart(f.getCanonicalPath());//재귀 함수로 하위목록 받아오기
					}else {
						copy(f.getCanonicalPath());//파일은 단순 카피.
					}
				}
			}else {
				copy(original.getCanonicalPath());//파일은 단순 카피.
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
	private void copy(String originalFullFileName) throws IOException{
		File original = new File(originalFullFileName);
		
		if(!original.exists()) return;//파일이 존재 하지 않으면 종료.

		if(original.isDirectory()) {//디렉토리는 단순 폴더만 생성. 
			
			//디렉토리명이 '\'로 끝나면 '\' 삭제 
			if(originalFullFileName.charAt(originalFullFileName.length() - 1) == '\\') {
				originalFullFileName = originalFullFileName.substring(0, originalFullFileName.length() -1);
			}
			
			String newFileName = originalFullFileName + "_copy";
			new File(newFileName).mkdir();//폴더명_copy에 폴더생성	
			dirCnt++;//복사 된 디렉토리 수 카운팅 
			
			return;
		}else {
			
			String[] originalFileNameAndPath = splitFileAddrAndFileName(originalFullFileName); 
			
			File tmpDir = new File(originalFileNameAndPath[0] + "_copy");//파일을 복사할 경로에 폴더가 존재 하지 않는다면 생성.
			if(!tmpDir.exists()) tmpDir.mkdir();
			
			String newFileName = "copy_" + originalFileNameAndPath[1];//파일명 앞에 copy추가
			
			File newFile = new File(originalFileNameAndPath[0] + "_copy", newFileName);
			
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
	
	/**
	 * 파일의 절대 경로를 -> 경로, 파일명 배열로 변환.
	 * @param fullFileName 파일경로
	 * @return [0] = 경로, [1] = 파일명  반환.
	 */
	public String[] splitFileAddrAndFileName(String fullFileName) {
		int separatorIdx = fullFileName.lastIndexOf("\\");
		String addr = fullFileName.substring(0, separatorIdx);
		String fileName = fullFileName.substring(separatorIdx + 1);
		
		return new String[] {addr, fileName};
	}
}
