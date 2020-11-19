package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagingUtil {
	
	/**
	 * 
	 * @param total 리스트의 총 rows
	 * @param rows 뿌려줄 rows갯수
	 * @param showPage 보여지는 페이징 단위  
	 * @param currPage 현재 페이지
	 * @return
	 */
	public Map<String, Object> getPaging(int total, int rows, int showPage, int currPage){
		
		int totalPageCount = (total / rows) + 1;
		int startPage = ((currPage - 1) / showPage) * showPage + 1;
		
		int endPage = startPage + showPage - 1;
		
		Map<String, Object> pageMap = new HashMap<String, Object>();
		
		int startRowNum = (currPage - 1) * rows + 1;  
		int endRowNum = Math.min(startRowNum + rows -1, total);
		
		pageMap.put("total", total);
		pageMap.put("start", startRowNum);
		pageMap.put("end", endRowNum);
		pageMap.put("curr", currPage);
		pageMap.put("totalPageCount", totalPageCount);
		pageMap.put("rows", rows);
		
		List<Integer> pages = new ArrayList<>();
		for(int i = startPage; i <= endPage; i++) pages.add(i);
		System.out.println("@@@@@@");
		pageMap.put("pages", pages);
		
		return pageMap;
	}
}
