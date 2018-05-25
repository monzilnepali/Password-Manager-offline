package application;
	
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.info.controller.AppPreloader;
import com.info.controller.FirstPreloader;
import com.info.dao.UserDao;
import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	Stage mainWindow;
	Scene login_panel,signupPanel;
	static boolean panelFlag;//if flag is true then only show login window,otherwise show signupwindow and redirect in to login panel
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//checking user count in another thread
		ExecutorService  executor=Executors.newSingleThreadExecutor();
		Callable<Boolean> c=()->{
			panelFlag=UserDao.checkUserCount();
			//System.out.println(Thread.currentThread().getName());
			
			return panelFlag;
		};
		Future<Boolean> future =executor.submit(c);
	    try {
			panelFlag=future.get();
		} catch (ExecutionException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	   
		
			
		//	System.out.println(panelFlag);
		    mainWindow=primaryStage;
		    
		    
		    try {
		    Parent root;
//		    currently we allow only one user to regiser in application so that if there is already user in database
//		    then we don't allow other user to signup so that in that case we only so login window
	
		    if(panelFlag){
		    	//showing login window only,removing the link to signup window
			 root=FXMLLoader.load(getClass().getResource("login_design.fxml"));
			Scene login_panel = new Scene(root);
			login_panel.getStylesheets().add(getClass().getResource("login_design.css").toExternalForm());	
			mainWindow.setScene(login_panel);
			mainWindow.setTitle("Login");
			mainWindow.setResizable(false);
			

			mainWindow.show();
		    }else{
		    	 root=FXMLLoader.load(getClass().getResource("signup_design.fxml"));
		    	 Scene signup_panel=new Scene(root);
		    	 signup_panel.getStylesheets().add(getClass().getResource("signup_design.css").toExternalForm());
		    	 mainWindow.setScene(signup_panel);
		    	 mainWindow.setTitle("Signup");
		    	 mainWindow.setResizable(false);
		    	 mainWindow.show();
		    	
		    	
		    }
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public static void main(String[] args) throws Exception {
        System.setProperty("prism.forceGPU", "true"); //force gpu acceralation

		System.out.println(Thread.currentThread().getName());
		
	//	 LauncherImpl.launchApplication(Main.class,AppPreloader.class, args); 
		
	launch(args);
		
	}
}
