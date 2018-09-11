/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.media.*;

/**
 *
 * @author Alexander
 */
public class MusicPlayer extends Application {

    MediaPlayer mediaPlayer;
    ArrayList<String> tracks = new ArrayList<>();

    public void addTracks() {
        tracks.add("InsertFilePathHere.mp3");
    }

    public void setSound() {
        Media sound = new Media(new File(tracks.get(0)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
    }

    /*public Button createPlayPauseButton() {
        Button playPauseButton = new Button();
//figure out how to set button background
        playPauseButton.setStyle("-fx-background-image: url('PlayButton.png')");
        return playPauseButton;
    } */

   /* public void playPauseButtonAndPausePlayButton() {
        mediaPlayer.play();
        createPlayPauseButton().setOnAction(new EventHandler<ActionEvent>() {
             
            @Override
            public void handle(ActionEvent event) {
                System.out.println("PLAYING!");
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    System.out.println("PLAYING!");
                    mediaPlayer.pause();
                } 
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                    mediaPlayer.play();
                }
            }
        });
    } */

    @Override
    public void start(Stage primaryStage) {

        /* Button btn = new Button();
       btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        }); */
        
        addTracks();
        setSound();
        
        Button playPauseButton = new Button();
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

        Scene scene = new Scene(root, 300, 250);

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
