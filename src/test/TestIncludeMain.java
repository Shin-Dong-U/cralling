package test;

import java.io.IOException;

import study.*;

public class TestIncludeMain {
	public static void main(String[] args) {
		FileCopy copy = new FileCopy();
		try {
			copy.copy("C:\\Users\\goott3\\eclipse-workspace\\crawlling\\images","lion.jpg");
			copy.copy("C:\\Users\\goott3\\eclipse-workspace\\crawlling\\files", "chap05_src.zip");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
