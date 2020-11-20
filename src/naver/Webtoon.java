package naver;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import common.FileUtils;
import javafx.concurrent.Task;

public class Webtoon {
	
	private final String SEARCH_STR = "https://comic.naver.com/search.nhn?keyword=";//keyword : 웹툰검색명
	private final String LIST_STR = "https://comic.naver.com/webtoon/list.nhn?titleId=";//titleId : key값
	private final String DETAIL_STR = "https://comic.naver.com/webtoon/detail.nhn?";//titleId={}&no={}" 상세
	
	private String title = "";
	private String titleId = "";//웹툰 key값
	private String folderName = "";//저장할 폴더
	private String lastNo = "";//웹툰의 회차정보 1회는 1
	
	private Document doc;
	FileUtils fileUtil;
	
	/**
	 * 
	 * @param title 웹툰제목
	 * @param folderName 저장할 로컬 폴더 경로
	 */
	public Webtoon(String title, String folderName) {
		this.title = title;
		this.folderName = folderName + "\\" + title;
	}
	
	//웹툰 다운전 설정 
	private void prepareSetting() throws IOException {
		this.fileUtil = new FileUtils();
		this.titleId = this.getTitleId(this.title);//웹툰의 key 
		this.lastNo = this.getLastNo(this.LIST_STR + this.titleId);//해당 웹툰의 마지막 회차 
	}
	
	public void start(int start, int end) throws IOException{
		prepareSetting();
		int last = Integer.parseInt(this.lastNo);

		//예외처리
		if(start < 1) start = 1;
		else if(start > last) start = last;
		if(end < start || end > last) end = last;
		
		
		for(int i = start; i <= end; i++ ) {
			if(luncher.WebtoonController.cancel) return; //중도 취소 요청 시 종료 
			try {
				this.webtoonDownload(i + "");
			} catch (IOException e) {
				System.out.println(i + "회차 다운 중 에러발생");
				e.printStackTrace();
			}
		}
	}
	
	//웹툰의 제목을 입력받으면 그 웹툰의 titleId값을 반환.  
	private String getTitleId(String webToonName) throws IOException {
		this.doc = Jsoup.connect(SEARCH_STR + webToonName).get();
		
		String href = this.doc.select(".resultList > li > h5 > a").attr("href");
		String tmp = "titleId=";
		href = href.substring( href.indexOf("titleId=") + tmp.length()); 
		return href;
	}
	
	//해당 웹툰의 마지막 화 회차 리턴.
	private String getLastNo(String url) throws IOException {
		this.doc = Jsoup.connect(url).get();
		String href = this.doc.select(".viewList > tbody > tr:nth-child(2) > td > a").attr("href");
		String tmp = "no=";
		String lastNo = href.substring( href.indexOf("no=") + tmp.length(), href.lastIndexOf("&"));
		return lastNo;
	}
	
	//해당 회차 다운로드 및 html 문서 생성.
	//Todo. 페이징 버튼 만들기 
	private boolean webtoonDownload(String no) throws IOException {
		String addr = this.DETAIL_STR + "titleId=" + this.titleId + "&no=" + no;
		this.doc = Jsoup.connect(addr).get();
  		Elements elements = this.doc.select(".wt_viewer img");
  		int size = elements.size();
		
  		
  		StringBuffer sb = new StringBuffer();
  		
  		// 1. 폴더 생성 후 이미지 파일 다운로드,
		// 2. src string 변경.(상대경로로)
		// 3. html 파일 생성.
  		for(int i = 0; i < size; i++) {
			String src = elements.eq(i).attr("src");//img 태그의 src attribute 값 획득
			
			fileUtil.downloadImage(addr, src, makeImageFolderName(no));//로컬로 다운로드
						
			String newSrcPath = getNewRelativePath(src, no);//img 태그를 새로운 경로로 맵핑		
			sb.append("<img src='");
			sb.append(newSrcPath);
			sb.append("'/></br>\n");
		}
  		
		return makeHtmlFile(sb.toString(), no);
	}
	
	/**
	 * 다운로드한 이미지를 볼 수 있는 html 문서 string 작성
	 * 
	 * Todo. 페이징  스크립트 작성. 
	 * @param bufferString html정보
	 * @param no 회차정보.(파일명에 작성할)
	 * @return
	 * @throws IOException
	 */
	private boolean makeHtmlFile(String bufferString, String no) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>\n<header>\n");
		sb.append("<style>\n.wt_viewer{min-width:960px;padding:50px 0;text-align:center;font-size:0;line-height:0}.wt_viewer img{display:block;margin:0 auto}.wt_viewer ~ .pre_view{display:block;width:270px;height:51px;margin:30px auto 0;border:1px solid #b8b8b8;text-align:center}.wt_viewer ~ .pre_view span{display:inline-block;width:155px;height:22px;margin-top:15px;background-position:0 -730px}.wt_viewer ~ .pre_view.end span{width:172px;background-position:0 -760px}</style>");
		sb.append("</header>\n<body>\n");
		sb.append(makeLinkButton(no));
		sb.append("<div class='wt_viewer'>\n");
		sb.append(bufferString);
		sb.append("</div>\n");
		sb.append(makeLinkButton(no));
		sb.append("</body>\n</html>");
		
		String fileName = makeHtmlFileName(no);
		
		return fileUtil.makeHtmlFile(fileName, sb.toString());
	}
	
	//html 파일명 생성
	private String makeHtmlFileName(String no) {
		return this.folderName + "\\" + no + ".html";
	}
	
	//웹주소 절대경로를 local 상대경로 문자열로 변경 
	private String getNewRelativePath(String path, String no) {
		return "./images/" + no + "/" + path.substring(path.lastIndexOf('/') + 1);
	}
	
	//이미지 폴더명 리턴
	private String makeImageFolderName(String no) {
		return this.folderName + "\\images\\" + no;
	}
	
	//이전화 다음화 버튼 생성
	private String makeLinkButton(String no) {
		StringBuffer sb = new StringBuffer();
		int curr = Integer.parseInt(no);
		int last = Integer.parseInt(this.lastNo);
		
		sb.append("<div style='min-width:960px;float:right;'>");
		if(curr == 1) {
			sb.append(" <a href = './" + (curr + 1) + ".html'>다음화</a> ");
		}else if(curr >= last) {
			sb.append(" <a href = './" + (curr - 1) + ".html'>이전화</a> ");
		}else {
			sb.append(" <a href = './" + (curr - 1) + ".html'>이전화</a> ");
			sb.append(" <a href = './" + (curr + 1) + ".html'>다음화</a> ");
		}
		sb.append("</div>");
		
		return sb.toString();
	}
}
