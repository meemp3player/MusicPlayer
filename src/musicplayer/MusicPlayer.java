/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.*;

/**
 *
 * @author Alexander
 */
public class MusicPlayer extends Application {

    MediaPlayer mediaPlayer;
    ArrayList<String> tracks = new ArrayList<>();
    ImageView playView;
    ImageView pauseView;
    public void addTracks() {
        
        tracks.add("src\\Rick Astley - Never Gonna Give You Up.mp3");
    }

    public void setSound() {
        Media sound = new Media(new File(tracks.get(0)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
    }

    public ImageView createPlayPauseButton() {
        try {
            Image pauseButtonIcon = new Image(new FileInputStream("src\\PauseButton.png"));
            Image playButtonIcon = new Image(new FileInputStream("src\\PlayButton.png"));
            pauseView = new ImageView(pauseButtonIcon);
            playView = new ImageView(playButtonIcon);
            
            pauseView.setFitHeight(70);
            pauseView.setFitWidth(70);
            playView.setFitHeight(50);
            playView.setFitWidth(50);
        } catch (FileNotFoundException ex) {
            System.out.println("Image not found!");
        }

        return pauseView;
    }

    @Override
    public void start(Stage primaryStage) {
        
        addTracks();
        setSound();
        
        createPlayPauseButton();
        mediaPlayer.play();
         StackPane root = new StackPane();

        Scene scene = new Scene(root, 800, 650);
        pauseView.setOnMouseClicked((MouseEvent event) -> {
                root.getChildren().remove(pauseView);
                root.getChildren().add(playView);
                mediaPlayer.play();
        });
        playView.setOnMouseClicked((MouseEvent event) -> {
            root.getChildren().remove(playView);
            root.getChildren().add(pauseView);
            mediaPlayer.pause();
        });

        primaryStage.setTitle("Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.getChildren().add(playView);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
