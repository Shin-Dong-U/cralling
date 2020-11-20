package luncher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import common.Utils;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
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
	
	private final String DEFAULT_FOLDER_NAME = "c:\\users\\" + System.getenv("USERNAME") + "\\desktop\\webtoon_download";
	
	private Popup popup;
	
	public static boolean cancel = false;
	
	Stage primaryStage;//setter 주입식으로 바꿀가....?
	
	Utils util = new Utils();
	
	Task<Void> task;
	
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
	
	//다운로드 시작
	public void startBtnAction(ActionEvent event) throws Exception{
		primaryStage = (Stage)start_btn.getScene().getWindow();
		
		String webtoonName = webtoon_name.getText().trim();
		String folderName = folder_name.getText().trim();
		
		if(webtoonName == null || "".equals(webtoonName)) return;//웹툰명을 적지 않았다면 실행 X
		if(folderName == null || "".equals(folderName)) folderName = this.DEFAULT_FOLDER_NAME;
		
		int start = util.safeParseInt(webtoon_start_no.getText());
		int end = util.safeParseInt(webtoon_end_no.getText());
		
		Webtoon crawlling = new Webtoon(webtoonName, folderName);
		
		popup = new Popup();//팝업
		HBox popbox = (HBox)FXMLLoader.load(getClass().getResource("popup.fxml"));
		
		Button stopBtn = (Button)popbox.lookup("#stop_btn");//팝업 중지 버튼
		stopBtn.setOnAction( e -> {
			try {
				stopBtnAction(e);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		});
		
		popup.getContent().add(popbox);
		popup.show(primaryStage);
		start_btn.setDisable(true);
		
		task = new Task<Void>() {
			@Override
			protected Void call() throws Exception{
				try {
					start_btn.setDisable(true);
					long startTime = System.currentTimeMillis();
					crawlling.start(start, end);
					long workTime = (System.currentTimeMillis() - startTime) / 1000;
					System.out.println(workTime);
					
				}catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void succeeded() { changeUI(); } 
			
			@Override
			protected void cancelled() {
				cancel = true; 
				changeUI(); 
				try {
					Thread.currentThread().sleep(5000);
					cancel = false;
				} catch (InterruptedException e) { e.printStackTrace();}
			}
			
			@Override
			protected void failed() { changeUI(); }
			
			private void changeUI() {
				popup.hide();
				start_btn.setDisable(false);
			}
		};
		
		Thread demonT = new Thread(task);
		demonT.start();
	}
	
	//팝업의 중지 버튼 실행중이던 쓰레드를 중지시킨다.
	public void stopBtnAction(ActionEvent event) throws Exception{
		task.cancel();
	}
}