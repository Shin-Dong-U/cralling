package naver;

import java.io.IOException;

public class WebtoonCrawller {
	/*
	 * Todo.
	 * 1. 실행기 UI 구현
	 * 2. 회차 선택 및 범위 조절 기능 
	 * 3. 중지 
	 */
	public static void main(String[] args) throws IOException {
		Webtoon crawlling = new Webtoon("고수", "C:\\Users\\goott3\\Desktop\\sample");
		long startTime = System.currentTimeMillis();
		crawlling.start(0, 0);
		long workTime = (System.currentTimeMillis() - startTime) / 1000;
		System.out.println(workTime);
	}
}
