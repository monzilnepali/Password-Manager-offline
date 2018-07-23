package com.info.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.info.dao.UserDao;
import com.info.model.User;
import com.jfoenix.controls.JFXProgressBar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FxmlController implements Initializable {
    // login panale UI Element
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Hyperlink signup_link;
    @FXML
    private Label title_label;
    // signup panel UI element
    @FXML
    private TextField signup_emailAddress_textField;
    @FXML
    private PasswordField signup_password_textField;
    @FXML
    private PasswordField signup_repassword_textField;
    @FXML
    private CheckBox termCondition_label;
    @FXML
    private CheckBox defaultCheckBox;
    @FXML
    private Button login_btn;
    @FXML
    private static Pane loginPane;
    private Stage MainWindow = null;
    @FXML
    private Pane welcomeScreenPane;
    @FXML
    private Pane signUpPane;
    @FXML
    private Button startBtn;
    @FXML
    private Label status;
    @FXML
    private JFXProgressBar  progressbar;
    @FXML
    private Button signupBtn;

   

   
    public void checkLogin(ActionEvent event) throws InterruptedException {
      

    }// end login auth

    // alert box
    private void alertBox(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // checking the email pattern
    protected static boolean emailVerification(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();// match =true return

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("fxml controller called");
        welcomeScreenPane.setVisible(true);
        signUpPane.setVisible(false);
        progressbar.setProgress(0);
        startBtn.setOnAction(e -> {
            // creating directory and table
            System.out.println("startBtn called");
            status.setVisible(false);
            DatabaseCreationHandler dbHandler = new DatabaseCreationHandler();
            status.textProperty().bind(dbHandler.messageProperty());

            progressbar.progressProperty().bind(dbHandler.progressProperty());

            final ChangeListener changelistener = new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable,
                        String oldValue, String newValue) {

                    if (newValue.equals("Done")) {
                        welcomeScreenPane.setVisible(false);
                        signUpPane.setVisible(true);
                    }

                }

            };
            status.textProperty().addListener(changelistener);

            new Thread(dbHandler).start();

        });
        
        
        signupBtn.setOnAction(e->{
            
            System.out.println("signup btn clicked");
            if (termCondition_label.isSelected()) {// checking checkbox condition
                if (signup_emailAddress_textField.getText().equals("")
                        && signup_password_textField.getText().equals("")
                        && signup_repassword_textField.getText().equals("")) {
                    alertBox("Password Manager - Signup", "Please check your Inputs");

                } else {
                    // email verification
                    if (emailVerification(signup_emailAddress_textField.getText())) {
                        // email pattern matches
                        // checking passowor field and retype passoword field
                        if (signup_password_textField.getText()
                                .equals(signup_repassword_textField.getText())) {
                            // same
                          
                            User user = new User();
                            user.setEmail(signup_emailAddress_textField.getText());
                            user.setPassword(signup_password_textField.getText());

                            ExecutorService executor = Executors.newSingleThreadExecutor();
                            Callable<Boolean> c = () -> {
                                UserDao userdao = new UserDao();
                                System.out.println(Thread.currentThread().getName());
                                return userdao.registerUser(user);
                            };
                            Future<Boolean> future = executor.submit(c);
                            try {
                                Boolean result = future.get(); // waits for the
                                                               // thread to complete
                                if (result) {
                                    System.out.println("open logged window");
                                    
                                    Stage currentStage=(Stage)((Node)e.getSource()).getScene().getWindow();
                                    
                                    Parent root=FXMLLoader.load(getClass().getResource("/application/login_design.fxml"));
                                    Scene loginScene=new Scene(root);
                                    loginScene.getStylesheets().add(getClass().getResource("/application/login_design.css").toExternalForm());
                                    currentStage.setScene(loginScene);
                                    currentStage.setTitle("Login Screen");
                                    currentStage.setResizable(false);
                                    currentStage.show();
                                    
                                  
                                    
                                    
                                    
                                    
                                    
                                } else {
                                    System.out.println("show error about unable to signup");
                                }
                            } catch (ExecutionException | InterruptedException | IOException ex) {
                                ex.printStackTrace();
                            }
                            executor.shutdown();

                        } else {
                            alertBox("Password Manager - Signup", "Password doesnot match");
                        }
                    } else {
                        // not matches
                        alertBox("Password Manager - Signup",
                                "Please enter valid Email address");
                    }

                }
            } else {
                alertBox("Password Manager - Signup", "please accept term and condition");
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
        });

    }

}
