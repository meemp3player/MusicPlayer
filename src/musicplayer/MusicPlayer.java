/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.media.*;
import javafx.util.Duration;

/**
 *
 * @author Alexander
 */
public class MusicPlayer extends Application {
    MediaPlayer mediaPlayer;
    
    //stores the URLs of the .mp3 song files (the order of this matters)
    ArrayList<String> tracks = new ArrayList<>();
    //stores the URLs of the .png song cover files (the order of this matters)
    ArrayList<String> songCovers = new ArrayList<>();
    
    //buttons
    ImageView playView;
    ImageView pauseView;
    ImageView restartView;
    ImageView nextTrackView;
    
    //song cover
    ImageView songCoverView;
    
    //converts .mp3s to sound
    Media sound;
    
    StackPane root;
    
    int songNumber = 0;
    
    //adds the tracks that are NOT added by the user via drag and drop
    public void addPresetTracks() {
        tracks.add("src/Rick Astley - Never Gonna Give You Up.mp3");
        tracks.add("src/Somebody once told me.mp3");
    }
    
    //sets the song to the one with the corresponding song number
    public void setSong() {
        switch (songNumber){
            case 0:
                sound = new Media(new File(tracks.get(0)).toURI().toString());
                break;
            case 1:
                sound = new Media(new File(tracks.get(1)).toURI().toString());
            /*this loops it back to the first song(Rick Astley) if the song number is greater than 1
            cuz clicking the nextTrack button too much can do that */
                break;
            default:
                sound = new Media(new File(tracks.get(0)).toURI().toString());
                break;
        }
        mediaPlayer = new MediaPlayer(sound);
    }
    
    //sets the song cover image to the one with the corresponding song number
    public void setSongCover() {
        addSongCovers();
        switch (songNumber) {
            case 0:
                try {
                    Image rickAstleyCover = new Image(new FileInputStream(songCovers.get(0)));
                    songCoverView = new ImageView(rickAstleyCover);
                    
                    songCoverView.setFitHeight(400);
                    songCoverView.setFitWidth(400);
                    //for some reason these statements make it crash lol
                    //songCoverView.setTranslateX(playView.getTranslateX());
                    //songCoverView.setTranslateY(playView.getTranslateY()-200);
                } catch (FileNotFoundException e){
                    System.out.println("Image not found!");
                }    break;
            case 1:
                try{
                    Image smashMouthCover = new Image(new FileInputStream(songCovers.get(1)));
                    songCoverView = new ImageView(smashMouthCover);
                    
                    songCoverView.setFitHeight(400);
                    songCoverView.setFitWidth(400);
                } catch(FileNotFoundException e) {
                    System.out.println("Image not found!");
                }
                break;
            /*this loops it back to the first song cover image(Rick Astley) if the song number is greater than 1
            cuz clicking the nextTrack button too much can do that */
            default:
                songNumber = 0;
                updateSongCover();
                break;
        }
    }
    
    public void addSongCovers() {
        songCovers.add("src/RickAstley.png");
        songCovers.add("src/SmashMouth.png");
    }
    
    public void goToNextTrack() {
        songNumber++;
    }

    public ImageView createPlayPauseButton() {
        try {
            Image pauseButtonIcon = new Image(new FileInputStream("src/PauseButton.png"));
            Image playButtonIcon = new Image(new FileInputStream("src/PlayButton.png"));
            pauseView = new ImageView(pauseButtonIcon);
            playView = new ImageView(playButtonIcon);
            pauseView.setFitHeight(70);
            pauseView.setFitWidth(70);
            playView.setFitHeight(50);
            playView.setFitWidth(50);
            pauseView.setTranslateY(250);
            playView.setTranslateY(250);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found!");
        }

        return pauseView;
    }
    
    public ImageView createRestartButton() {
        try {
            Image restartButtonIcon = new Image(new FileInputStream("src/restart.png"));
            restartView = new ImageView(restartButtonIcon);
            
            restartView.setFitHeight(50);
            restartView.setFitWidth(70);
            restartView.setTranslateX(playView.getTranslateX()-60);
            restartView.setTranslateY(250);
        } catch(FileNotFoundException e) {
            System.out.println("Image not found!");
        }
        return restartView;
    }

    public ImageView createNextTrackButton(){
        try {
            Image nextTrackButtonIcon = new Image(new FileInputStream("src/NextTrackButton.png"));
            nextTrackView = new ImageView(nextTrackButtonIcon);
            
            nextTrackView.setFitHeight(50);
            nextTrackView.setFitWidth(50);
            nextTrackView.setTranslateX(playView.getTranslateX()+60);
            nextTrackView.setTranslateY(250);
        } catch(FileNotFoundException e) {
            System.out.println("Image not found!");
        }
        return nextTrackView;
    }
    
    //replaces the current song cover image with the new one corresponding to the song number
    public void updateSongCover() {
        if(songNumber == 0) {
            root.getChildren().remove(songCoverView);
        }
        //checks what song cover image should be added after the old one is removed
        setSongCover();
    }
    
    public void updateSong() {
        setSong();
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        addPresetTracks();
        setSong();
        setSongCover();
        
        createPlayPauseButton();
        createRestartButton();
        createNextTrackButton();
        
        mediaPlayer.play();
        root = new StackPane();
	root.setOnDragOver((DragEvent event) -> {
		if (event.getGestureSource() != root
			&& event.getDragboard().hasFiles()) {
			/* allow for both copying and moving, whatever user chooses */
			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		}
		event.consume();
	});

        root.setOnDragDropped((DragEvent event) -> {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasFiles()) {
			System.out.println(db.getFiles().get(0).toString());
			success = true;
		}
		/* let the source know whether the string was successfully
		* transferred and used */
		event.setDropCompleted(success);
		
		event.consume();
	});

        Scene scene = new Scene(root, 450, 600);
        
        pauseView.setOnMouseClicked((MouseEvent event) -> {
            root.getChildren().remove(pauseView);
            root.getChildren().add(playView);
            mediaPlayer.pause();
        });
        
        playView.setOnMouseClicked((MouseEvent event) -> {
            root.getChildren().remove(playView);
            root.getChildren().add(pauseView);
            mediaPlayer.play();
        });
        
        restartView.setOnMouseClicked((MouseEvent event) -> {
            mediaPlayer.seek(Duration.ZERO);
        });
        
        nextTrackView.setOnMouseClicked((MouseEvent event) -> {
            goToNextTrack();
            mediaPlayer.stop();
            updateSong();
            updateSongCover();
            mediaPlayer.play();
            root.getChildren().add(songCoverView);
        });
        //I haven't waited for a song to end so idk if this will work
        mediaPlayer.setOnStopped(() -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
                goToNextTrack();
                mediaPlayer.stop();
                updateSong();
                updateSongCover();
                mediaPlayer.play();
                root.getChildren().add(songCoverView);
            }
        });
        
        primaryStage.setTitle("Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.getChildren().add(pauseView);
        root.getChildren().add(restartView);
        root.getChildren().add(nextTrackView);
        root.getChildren().add(songCoverView);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
