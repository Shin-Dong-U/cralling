package common;

public class Utils {
	
	//숫자가 아닌 문자가 들어오면 0리턴 
	public int safeParseInt(String integerStr) {
		int res;
		try {
			res = Integer.parseInt(integerStr);
		}catch(NumberFormatException e) {
			res = 0;
		}
		return res;
	}
}
