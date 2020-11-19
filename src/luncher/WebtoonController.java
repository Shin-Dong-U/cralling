package luncher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import common.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import naver.Webtoon;

public class WebtoonController implements Initializable{
	
	@FXML private TextField webtoon_name;
	@FXML private TextField folder_name;
	@FXML private TextField webtoon_start_no;
	@FXML private TextField webtoon_end_no;
	
	@FXML private Button find_directory_btn;
	@FXML private Button start_btn;
	
	@FXML private Button stop_btn;//popup.fxml
	
	private final String DEFAULT_FOLDER_NAME = "c:\\users\\" + System.getenv("USERNAME") + "\\desktop\\webtoon_download";
	
	Stage primaryStage;//setter 주입식으로 바꿀가....?
	
	Utils util = new Utils();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		folder_name.setText(this.DEFAULT_FOLDER_NAME);
		
		//다운로드 디렉토리 설정
		find_directory_btn.setOnAction(event -> {
			try {
				selectFolderAction(event);
			} catch (Exception e) { e.printStackTrace(); }
		});
		//다운로드 실행.
		start_btn.setOnAction( event -> {
			try {
				startBtnAction(event);
			} catch (Exception e) { e.printStackTrace(); }
		});
	}
	
	//다운로드할 폴더명 설정
	public void selectFolderAction(ActionEvent event) throws Exception{
		primaryStage = (Stage)find_directory_btn.getScene().getWindow();
		
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(new File(this.DEFAULT_FOLDER_NAME));
    	File selectedFolder = dc.showDialog(primaryStage);
    	
    	String folderName = "";
    	if(selectedFolder != null) {
    		folderName = selectedFolder.getPath();
    		folder_name.setText(folderName);
    	}else {
    		folderName = this.DEFAULT_FOLDER_NAME;
    		folder_name.setText(folderName);
    	}
    }
	
	/* Todo.
	 * 1. 다운로드를 알리는 표시 
	 * 2. 실행 중지 버튼 생성
	 * 3. 다운로드중은 다른 조작 안되게 설정. 
	 * 4. 데몬쓰레드로 구현
	 */
	public void startBtnAction(ActionEvent event) throws Exception{
		primaryStage = (Stage)start_btn.getScene().getWindow();
		
		String webtoonName = webtoon_name.getText().trim();
		String folderName = folder_name.getText().trim();
		
		if(webtoonName == null || "".equals(webtoonName)) return;//웹툰명을 적지 않았다면 실행 X
		if(folderName == null || "".equals(folderName)) folderName = this.DEFAULT_FOLDER_NAME;
		
		int start = util.safeParseInt(webtoon_start_no.getText());
		int end = util.safeParseInt(webtoon_end_no.getText());
		
		Webtoon crawlling = new Webtoon(webtoonName, folderName);
		
		
		Popup popup = new Popup();
		popup.getContent().add(FXMLLoader.load(getClass().getResource("popup.fxml")));
		popup.show(primaryStage);
		
		Thread demonT = new Thread() {
			@Override
			public void run() {
				try {
					long startTime = System.currentTimeMillis();
					crawlling.start(start, end);
					long workTime = (System.currentTimeMillis() - startTime) / 1000;
					System.out.println(workTime);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		demonT.setDaemon(true);
		demonT.start();
		//종료가 되면 신호를 받고 popup을 종료시키자!!!.
	
	}
}