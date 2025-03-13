package com.ooprog.javafxmedia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.File;

public class MediaPlayerController {

    @FXML
    private Button btnPlay;

    @FXML
    private Label lbDuration;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider slider;

    private Media media;

    private MediaPlayer mediaPlayer;

    private boolean isPlayed = false;
    @FXML
    void btnPlay(MouseEvent event) {
    if(!isPlayed){
        btnPlay.setText("Pause");
        mediaPlayer.play();
        isPlayed = true;
    }else{
        btnPlay.setText("Play");
        mediaPlayer.pause();
        isPlayed = false;
    }

    }

    @FXML
    void btnStop(MouseEvent event) {
        btnPlay.setText("Play");
        mediaPlayer.stop();
        isPlayed = false;
    }

    @FXML
    void selectMedia(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Media");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null){
            String url = selectedFile.toURI().toString();

            media = new Media (url);
            mediaPlayer = new MediaPlayer(media);

            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.currentTimeProperty().addListener(((observableValue, oldValue, newValue) -> {
                int totalSeconds = (int) newValue.toSeconds();
                int minutes = totalSeconds / 60;
                int seconds = totalSeconds % 60;

                slider.setValue(totalSeconds);
                lbDuration.setText(String.format("Duration: %02d:%02d / %02d:%02d",
                        minutes, seconds, (int)media.getDuration().toMinutes(), (int)media.getDuration().toSeconds() % 60));
            }));

            mediaPlayer.setOnReady(() -> {
                Duration totalDuration = media.getDuration();
                int totalSeconds = (int) totalDuration.toSeconds();
                int minutes = totalSeconds / 60;
                int seconds = totalSeconds % 60;

                slider.setValue(totalSeconds);
                lbDuration.setText(String.format("Duration: 00:00 / %02d:%02d", minutes, seconds));
            });

            Scene scene = mediaView.getScene();
            mediaView.fitWidthProperty().bind(scene.widthProperty());
            mediaView.fitHeightProperty().bind(scene.heightProperty());

           mediaPlayer.setAutoPlay(true);
        }
    }
    @FXML
    void sliderPressed(MouseEvent event) {
    mediaPlayer.seek(Duration.seconds(slider.getValue()));
    }

}

