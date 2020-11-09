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
		
//		copy.copyStart("C:\\Users\\sdw\\Desktop\\movies\\영화\\Margin.Call.LIMITED.1080p.Bluray.x264-TWiZTED");
		copy.copyStart("https://r8---sn-ucgv5q-bh2l.googlevideo.com/videoplayback?expire=1604434536&ei=CGahX_ukC5fXqAGsmI64DA&ip=124.199.206.245&id=o-AGP7La-x_iwYn0QpqeQReBrIHXeDqGpNm7JJt7U-eQur&itag=247&aitags=133%2C134%2C135%2C136%2C137%2C160%2C242%2C243%2C244%2C247%2C248%2C271%2C278%2C313&source=youtube&requiressl=yes&mh=zF&mm=31%2C29&mn=sn-ucgv5q-bh2l%2Csn-n3cgv5qc5oq-bh2lr&ms=au%2Crdu&mv=m&mvi=8&pl=20&ctier=L&initcwndbps=991250&vprv=1&mime=video%2Fwebm&gir=yes&clen=28150201&dur=175.758&lmt=1604021174746032&mt=1604412882&fvip=8&keepalive=yes&c=WEB&txp=5432434&sparams=expire%2Cei%2Cip%2Cid%2Caitags%2Csource%2Crequiressl%2Cctier%2Cvprv%2Cmime%2Cgir%2Cclen%2Cdur%2Clmt&sig=AOq0QJ8wRgIhAPnUZEjtyaZYpHYztyZTSLsHv1nT05GCDURsE_zu_kaGAiEA_cXzGK4fz5FxguRFlJoIR4-Rs9X1dP_ccEgmazk88jg%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRAIgSD6xdvH2EqciMLOUOqe7oTDVvQh2H51ZBg2xj8aetPECIA1ftwclJHImQPVuutoWM1RUxtFrDFGNPsZTSuEt0sjk&alr=yes&cpn=uy8gR_4OzFlVona0&cver=2.20201031.02.00&rn=79&rbuf=54743");
	}
}
