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
import javafx.scene.media.*;

/**
 *
 * @author Alexander
 */
public class MusicPlayer extends Application {

    MediaPlayer mediaPlayer;
    ArrayList<String> tracks = new ArrayList<>();
    Button playPauseButton;
    public void addTracks() {
        
        tracks.add("src\\Rick Astley - Never Gonna Give You Up.mp3");
    }

    public void setSound() {
        Media sound = new Media(new File(tracks.get(0)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
    }

    public Button createPlayPauseButton() {
        Image playPauseButtonIcon;
        try {
            playPauseButtonIcon = new Image(new FileInputStream("src\\PauseButton.png"));
            ImageView playPauseButtonView = new ImageView(playPauseButtonIcon);

            playPauseButton = new Button("",playPauseButtonView);
            playPauseButtonView.setFitHeight(50);
            playPauseButtonView.setFitWidth(50);
            playPauseButton.setTranslateX(70.0);
        } catch (FileNotFoundException ex) {
            System.out.println("Image not found!");
        }

        return playPauseButton;
    }

    @Override
    public void start(Stage primaryStage) {
        
        addTracks();
        setSound();
        
        playPauseButton = createPlayPauseButton();
       /* playPauseButton.setLayoutX(0);
        playPauseButton.setLayoutY(0);*/
        mediaPlayer.play();
        playPauseButton.setOnAction(new EventHandler<ActionEvent>() {
             
            @Override
            public void handle(ActionEvent event) {
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                } 
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                    mediaPlayer.play();
                }
            }
        });
        StackPane root = new StackPane();
        //root.getChildren().add(btn);

        //Scene scene = new Scene(root, 300, 250);
        Scene scene = new Scene(root, 800, 650);

        primaryStage.setTitle("Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.getChildren().add(playPauseButton);
        //playPauseButtonAndPausePlayButton();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
