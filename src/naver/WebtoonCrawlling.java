package naver;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebtoonCrawlling {
	/*
	 * 1.웹툰명을 입력받음
	 * 2.a href에서 titleId 캐치
	 * 3.최신화 no 획득
	 * 4.하단 페이징 클릭으로 가장 오래된 화 찾기 
	 * 5.크롤륑
	 * 
	 */
	URL url;
	public String searchStr = "https://comic.naver.com/search.nhn?keyword=";//keyword : 웹툰검색명
	public String listStr = "https://comic.naver.com/webtoon/list.nhn?titleId=";//titleId : key값
	public String detailStr = "https://comic.naver.com/webtoon/detail.nhn?";//titleId={}&no={}" 상세
	
	
	public String titleId = "";//웹툰 key값
	public String no = "";//웹툰의 회차정보 1회는 no1
	
	public static void main(String[] args) throws IOException {
		WebtoonCrawlling crawlling = new WebtoonCrawlling();
		crawlling.titleId = crawlling.getTitleId("호랑이형님");
		System.out.println(crawlling.titleId);
	}
	
	public void start(String webToonName) {
		
	}
	
	//웹툰의 제목을 입력받으면 그 웹툰의 titleId값을 반환.  
	public String getTitleId(String webToonName) throws IOException {
		Document doc = Jsoup.connect(searchStr + webToonName).get();
		
		String href = doc.select(".resultList > li > h5 > a").attr("href");
		href = href.substring( href.indexOf("titleId=") + 8);
		return href;
	}
}
