package com.info.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.info.dao.UserDao;
import com.info.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class loginController implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Hyperlink signup_link;
    @FXML
    private Label title_label;
    @FXML private Button login_btn;
    private CurrentUserSingleton singleton=CurrentUserSingleton.getInstance();
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("login controller initliazed");
        login_btn.setOnAction(event->{
            System.out.println("login button clicked");
            
            login_btn.setText("loading");

            if (emailField.getText().equals("") && passwordField.equals("")) {
                alertBox("Password Manager", "please check your input");

            } else {
                if (FxmlController.emailVerification(emailField.getText())) {
                    User user = new User();
                    user.setEmail(emailField.getText());
                    user.setPassword(passwordField.getText());
                    // user.setDefaultUser(defaultCheckBox.isSelected());

                    // Parent root = null;
                    // verified user
                    // User vuser=userdao.loginAuth(user);

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Callable<User> c = () -> {
                        UserDao userdao = new UserDao();
                        System.out.println(Thread.currentThread().getName());
                        return userdao.loginAuth(user);
                    };

                    Future<User> future = executor.submit(c);
                    User user1 = new User();

                    try {
                        user1 = future.get();
                    } catch (ExecutionException | InterruptedException e1) {
                        e1.printStackTrace();
                    }
                      Stage MainWindow;
                    if (user1 != null) {
                        
                        
                        singleton.setVuser(user1);
                        // writing file

                        MainWindow = (Stage) ((Node) event.getSource()).getScene()
                                .getWindow();
                        // vuser=user1;
                        // code start for communicating between two controller for
                        // exchanaging the information
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/application/PreLoader.fxml"));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                       
                        // creating scene for mainWindow
                        Scene mainWindowScene = new Scene(root);
                        // loading css file for scene
                     //   mainWindowScene.getStylesheets().add(getClass()
                     //           .getResource("/application/MainWindow.css").toExternalForm());
                        // access stage
                        // Stage
                        // window=(Stage)((Node)event.getSource()).getScene().getWindow();
                        MainWindow.setScene(mainWindowScene);
                        MainWindow.setTitle("Password Manager - " + user1.getEmail());
                        MainWindow.show();

                    } else {
                        alertBox("Password Manager",
                                "Email Address and Password doesnot match");

                    }

                } else {

                    alertBox("Password Manager", "invalid Email Address");

                }

            }
        });
        
    }
 // alert box
    private void alertBox(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
