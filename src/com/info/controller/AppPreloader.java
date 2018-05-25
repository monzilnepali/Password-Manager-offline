package com.info.controller;

import javafx.application.Preloader; 
import javafx.geometry.Insets; 
import javafx.geometry.Pos; 
import javafx.scene.Scene; 
import javafx.scene.control.Label; 
import javafx.scene.image.ImageView; 
import javafx.scene.layout.Background; 
import javafx.scene.layout.BackgroundFill; 
import javafx.scene.layout.CornerRadii; 
import javafx.scene.layout.VBox; 
import javafx.scene.paint.Color; 
import javafx.stage.Stage; 
import javafx.stage.StageStyle; 
 
public class AppPreloader extends Preloader { 
    private static Stage stage; 
    private static ImageView iconView; 
 
    private Scene createPreloaderScene() { 
        VBox preloaderPane = new VBox(); 
        preloaderPane.setAlignment(Pos.CENTER); 
        preloaderPane.setSpacing(30.0f); 
 
//        AppPreloader.iconView = new ImageView(new Image(this.getClass() 
//                .getResourceAsStream("/ui/icon/icon-256.png"))); 
       // preloaderPane.getChildren().add(AppPreloader.iconView); 
 
        Label loadingLabel = new Label(); 
        loadingLabel.getStylesheets().add("/ui/css/fonts.css"); 
        loadingLabel.setStyle("-fx-font-family: \"Roboto Light\";" 
                + "-fx-font-weight: bold; -fx-font-size: 32;" 
                + "-fx-text-fill: white;"); 
//        loadingLabel.setText(PreferencesManager.getInstance().localizeString( 
//                "splashscreen.loading") 
//                + " Tourney..."); 
        preloaderPane.getChildren().add(loadingLabel); 
 
        Scene preloaderScene = new Scene(preloaderPane, 600, 400); 
        preloaderPane.setBackground(new Background(new BackgroundFill(Color 
                .rgb(17, 119, 255), CornerRadii.EMPTY, Insets.EMPTY))); 
        preloaderScene.setFill(Color.rgb(17, 119, 255)); 
 
        return preloaderScene; 
    } 
 
    @Override 
    public void start(Stage stage) throws Exception { 
    	AppPreloader.stage = new Stage(); 
    	AppPreloader.stage.setTitle("Loading Tourney"); 
        Scene preloaderScene = createPreloaderScene(); 
        AppPreloader.stage.setScene(preloaderScene); 
        AppPreloader.stage.initStyle(StageStyle.UNDECORATED); 
        AppPreloader.stage.show(); 
    } 
 
    public static void hide() { 
        if (AppPreloader.stage != null) { 
        	AppPreloader.stage.close(); 
        } 
    } 
}