package naver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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
	public String folderName = "";
	public String searchStr = "https://comic.naver.com/search.nhn?keyword=";//keyword : 웹툰검색명
	public String listStr = "https://comic.naver.com/webtoon/list.nhn?titleId=";//titleId : key값
	public String detailStr = "https://comic.naver.com/webtoon/detail.nhn?";//titleId={}&no={}" 상세
	
	public String title = "";
	public String titleId = "";//웹툰 key값
	public String lastNo = "";//웹툰의 회차정보 1회는 1
	public String no = "";
	
	
	public static void main(String[] args) throws IOException {
		WebtoonCrawlling crawlling = new WebtoonCrawlling();
		
		crawlling.title = "호랑이형님";
		crawlling.folderName = "c:\\dev\\sample\\" + crawlling.title + "\\";
		crawlling.titleId = crawlling.getTitleId(crawlling.title);
		crawlling.getLastNo(crawlling.listStr + crawlling.titleId);
		
		crawlling.webtoonDownload("1");
	}
	
	public void start(String webToonName) {
		
	}
	
	//웹툰의 제목을 입력받으면 그 웹툰의 titleId값을 반환.  
	public String getTitleId(String webToonName) throws IOException {
		Document doc = Jsoup.connect(searchStr + webToonName).get();
		
		String href = doc.select(".resultList > li > h5 > a").attr("href");
		String tmp = "titleId=";
		href = href.substring( href.indexOf("titleId=") + tmp.length()); 
		return href;
	}
	
	//해당 웹툰의 마지막 화 회차 리턴.
	public String getLastNo(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		String href = doc.select(".viewList > tbody > tr:nth-child(2) > td > a").attr("href");
		String tmp = "no=";
		String lastNo = href.substring( href.indexOf("no=") + tmp.length(), href.lastIndexOf("&"));
		return lastNo;
	}
	
	public boolean webtoonDownload(String no) throws IOException {
		String url = this.detailStr + "titleId=" + this.titleId + "&no=" + no;
		Document doc = Jsoup.connect(url).get();
		Elements elements = doc.select(".wt_viewer img");
		int size = elements.size();
		
		for(int i = 0; i < size; i++) {
			// 1. 폴더 생성 후 이미지 파일 다운로드,
			// 2. src string 변경.(상대경로로)
			// 3. html 생성. 
			String src = elements.eq(i).attr("src");
			
		}
		
		return false;
	}
	
	public boolean downloadImage(String src) {
		try {
			url = new URL(src);
			InputStream is = url.openStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			
			String folderName = this.folderName + "\\images\\" + no;   
			String fileName = src.substring( src.lastIndexOf('/') + 1);
			
//			File f = new File();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	//폴더 생성
	public void makeFolder(String folderName) {
		File f = new File(folderName);
		
		if(!f.exists()) f.mkdirs();
	}
}
