package test;

import java.io.IOException;
import java.util.Arrays;

import study.FileCopy;

public class TestIncludeMain {
	public static void main(String[] args) throws IOException {
		FileCopy copy = new FileCopy();
//		try {
//			copy.fileCopy("C:\\Users\\goott3\\eclipse-workspace\\crawlling\\images","lion.jpg");
//			copy.fileCopy("C:\\Users\\goott3\\eclipse-workspace\\crawlling\\files", "chap05_src.zip");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String[] test = copy.splitFileAddrAndFileName("C:\\Users\\goott3\\eclipse-workspace\\crawlling\\images\\lion.jpg");
//		System.out.println(Arrays.toString(test));
		
		copy.copyStart("C:\\Users\\sdw\\Desktop\\movies\\영화\\Margin.Call.LIMITED.1080p.Bluray.x264-TWiZTED");
	}
}
