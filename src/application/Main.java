package application;
	
import com.info.controller.CurrentUserSingleton;
import com.info.dao.UserDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private CurrentUserSingleton singleton=CurrentUserSingleton.getInstance();
	Stage mainWindow;
	Scene login_panel,signupPanel;
	static boolean panelFlag;//if flag is true then only show login window,otherwise show signupwindow and redirect in to login panel
	@Override
	public void start(Stage primaryStage) throws Exception {
	    singleton.setPrimaryStage(primaryStage);
	    //gettting user presence in database
	    //we only allow one user in one application
	    //if user present in database then login screen is shown other signup screen
	    
	    Boolean flag=UserDao.checkUserCount();
	    
	    if(flag) {
	    
	    //showinglogin screen
	    try {
	        Parent root=FXMLLoader.load(getClass().getResource("login_design.fxml"));
	        Scene loginScene=new Scene(root);
	        loginScene.getStylesheets().add(getClass().getResource("login_design.css").toExternalForm());
	        primaryStage.setScene(loginScene);
	        primaryStage.setTitle("Login Screen");
	        primaryStage.setResizable(false);
	        primaryStage.show();
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	    }
	    
	    }else {
	        //showing signup screen
	        
	        try {
	            Parent root=FXMLLoader.load(getClass().getResource("Signup_design.fxml"));
	            Scene signupScene=new Scene(root);
	            signupScene.getStylesheets().add(getClass().getResource("Signup_design.css").toExternalForm());
	            primaryStage.setScene(signupScene);
	            primaryStage.setTitle("Signup");
	            primaryStage.setResizable(false);
	            primaryStage.show();
	            
	        }catch(Exception e) {
	            e.printStackTrace();
	        }
	        
	    }
	}
		
		
	
	
	public static void main(String[] args) throws Exception {
        System.setProperty("prism.forceGPU", "true"); //force gpu acceralation
		
	launch(args);
		
	}
}
