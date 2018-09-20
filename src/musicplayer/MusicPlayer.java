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
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Alexander
 */
public class MusicPlayer extends Application {
    MediaPlayer mediaPlayer;
    
    //stores the URLs of the .mp3 song files (the order of this matters)
    //ArrayList<String> tracks = new ArrayList<>();
    //stores the URLs of the .png song cover files (the order of this matters)
    //ArrayList<String> songCovers = new ArrayList<>();

    ArrayList<Song> songs = new ArrayList<>();
    
    //buttons
    ImageView playView;
    ImageView pauseView;
    ImageView restartView;
    ImageView nextTrackView;
    ImageView randomTrackView;
    
    //song cover
    ImageView songCoverView;
    
    //converts .mp3s to sound
    Media sound;
    
    StackPane root;
    
    Text songTitle = new Text();
    
    int songNumber = 0;
    
    //adds the tracks that are NOT added by the user via drag and drop
    public void addPresetTracks() {
        songs.add(new Song(
            "Rick Astley - Never Gonna Give You Up",
            "src/Rick Astley - Never Gonna Give You Up.mp3",
            "src/RickAstley.png"
        ));
        songs.add(new Song(
            "Smash Mouth - All Star",
		    "src/Somebody once told me.mp3",
		    "src/SmashMouth.png"
		));
        songs.add(new Song(
            "EarthBFlat",
            "src/EarthBFlat.mp3",
			"src/Default.png"
        ));
        songs.add(new Song(
            "Rockefeller Street",
            "srcNightcore - Rockefeller Street.mp3",
			"src/Default.png"
        ));
    }
    
    //sets the song to the one with the corresponding song number
    public void setSong() {
		sound = new Media(new File(songs.get(songNumber).path).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
    }
    
    //sets the song cover image to the one with the corresponding song number
    public void setSongCover() {
	    try {
		    Image songCover = new Image(new FileInputStream(songs.get(songNumber).coverImagePath));
		    songCoverView = new ImageView(songCover);
		    
		    songCoverView.setFitHeight(400);
		    songCoverView.setFitWidth(400);
		    //for some reason these statements make it crash lol
		    //songCoverView.setTranslateX(playView.getTranslateX());
		    //songCoverView.setTranslateY(playView.getTranslateY()-200);
	    } catch (FileNotFoundException e){
		    defaultSongCover();
	    }
    }
    
    public void setSongTitle() {
		songTitle = new Text();
		songTitle.setText(songs.get(songNumber).name);
		songTitle.setTranslateX(20);
		songTitle.setTranslateY(20);
		songTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
    }
    
    //sets cover to default image if there is no image for song or image fails to load
    public void defaultSongCover(){
        try {
            Image Default = new Image(new FileInputStream("src/DefaultCoverImage.png"));
            songCoverView = new ImageView(Default);
            
            songCoverView.setFitHeight(400);
            songCoverView.setFitWidth(400);
        } catch (FileNotFoundException e){
            System.out.println("Image not found!");
        }    
    }
    
    public void goToNextTrack() {
        if (++songNumber >= songs.size()) songNumber = 0;
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
    
    public ImageView createRandomTrackButton() {
        try {
            Image randomTrackButtonIcon = new Image(new FileInputStream("src/RandomTrackButton.png"));
            randomTrackView = new ImageView(randomTrackButtonIcon);
            
            randomTrackView.setFitHeight(50);
            randomTrackView.setFitWidth(80);
            randomTrackView.setTranslateX(playView.getTranslateX()-150);
            randomTrackView.setTranslateY(250);
        } catch(FileNotFoundException e) {
             System.out.println("Image not found!");
        }
        return randomTrackView;
    } 
    
    //replaces the current song cover image with the new one corresponding to the song number
    public void updateSongCover() {
            root.getChildren().remove(songCoverView);
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
        setSongTitle();
        
        createPlayPauseButton();
        createRestartButton();
        createNextTrackButton();
        createRandomTrackButton();
        
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
                String full_filename = db.getFiles().get(0).getName();
                String filename = full_filename.substring(0, full_filename.lastIndexOf("."));
                System.out.println(filename);
                //tracks.add(full_filename);
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
            
            root.getChildren().remove(songTitle);
            setSongTitle();

            
            updateSong();
            updateSongCover();
            mediaPlayer.play();
            root.getChildren().add(songCoverView);
            root.getChildren().add(songTitle);
        });
        randomTrackView.setOnMouseClicked((MouseEvent event) -> {
            int songNumberComparison = songNumber;
            while(songNumber == songNumberComparison){
                //returns a random number within the range 0 -> track arraylist length-1
                int random = ThreadLocalRandom.current().nextInt(0, songs.size()); 
                songNumber = (int)random;
            }
            mediaPlayer.stop();
            root.getChildren().remove(songTitle);
            setSongTitle();
            updateSong();
            updateSongCover();
            mediaPlayer.play();
            root.getChildren().add(songCoverView);
            root.getChildren().add(songTitle);
        }); 
        
        mediaPlayer.setOnEndOfMedia(() -> {
            //System.out.println("what");
            goToNextTrack();
            mediaPlayer.stop();
            root.getChildren().remove(songTitle);
            setSongTitle();
            root.getChildren().add(songTitle);
            updateSong();
            updateSongCover();
            mediaPlayer.play();
            root.getChildren().add(songCoverView);
        });
        
        primaryStage.setTitle("Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.getChildren().add(pauseView);
        root.getChildren().add(restartView);
        root.getChildren().add(nextTrackView);
        root.getChildren().add(songCoverView);
        root.getChildren().add(randomTrackView);
        root.getChildren().add(songTitle);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
