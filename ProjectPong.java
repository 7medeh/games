/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectpong;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 *
 * @author Walid Hamade
 */
public class ProjectPong extends Application {

    //variables
    public static final int width = 800;
    public static final int height = 600;
    public static final int PLAYER_HEIGHT = 100;
    public static final int PLAYER_WIDTH = 15;
    public static final int BALL_RADIUS = 15;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private double player1YPosition = height/ 2;
    private double player2YPosition = height/ 2;
    private double player1XPosition = 0;
    private double player2XPosition = width - PLAYER_WIDTH;
    private double ballXPosition = width / 2;
    private double ballYPosition = width / 2;

    private int player1Score = 0;
    private int player2Score = 0;

    private boolean gameStarted;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Begin by setting the window title
        primaryStage.setTitle("King Pong [REMASTERED]");
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        t1.setCycleCount(Timeline.INDEFINITE);

        //Controls
        canvas.setOnMouseMoved(e -> player1YPosition = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);
        primaryStage.setScene(new Scene(new StackPane(canvas)));
        primaryStage.show();
        t1.play();

    }

    private void run(GraphicsContext gc) {
        //Setting BG Color
        gc.setFill(Color.FLORALWHITE);
        gc.fillRect(0, 0, width, height);

        //Setting TEXT Color
        gc.setFill(Color.PINK);
        gc.setFont(Font.font(25));

        if (gameStarted) {
            ballXPosition += ballXSpeed;
            ballYPosition += ballYSpeed;

            //Computer opponent
            if (ballXPosition < width - (width / 4)) {
                player2YPosition = ballYPosition - PLAYER_HEIGHT / 2;
            } else {
                player2YPosition
                        = (ballYPosition > player2YPosition + PLAYER_HEIGHT / 2)
                                ? player2YPosition += 2 : player2YPosition - 2;
            }

            // Drawing the ball
            gc.fillOval(ballXPosition, ballYPosition, BALL_RADIUS, BALL_RADIUS);

        } else {
            //Display start menu
            gc.setStroke(Color.BLACK);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click to Begin", width / 2, height / 2);

            //reset ball start position
            ballXPosition = width / 2;
            ballYPosition = height / 2;

            //reset speed & direction
            ballXSpeed = (new Random().nextInt(2) == 0) ? 1 : -1;
            ballYSpeed = (new Random().nextInt(2) == 0) ? 1 : -1;
        }

        // making the ball stay in the canvas
        if (ballYPosition > height || ballYPosition < 0) {
            ballYSpeed *= -1;
        }

        // if computer gets a point        
        if (ballXPosition < player1XPosition - PLAYER_WIDTH) {
            player2Score++;
            gameStarted = false;
        }

        // if you get a point
        if (ballXPosition > player2XPosition + PLAYER_WIDTH) {
            player1Score++;
            gameStarted = false;
        }

        // ball increases in speed
        if ((ballXPosition + BALL_RADIUS > player2XPosition) && (ballYPosition
                >= player2YPosition) && (ballYPosition <= player2YPosition
                + PLAYER_HEIGHT) || ((ballXPosition < player1XPosition + PLAYER_WIDTH)
                    && ballYPosition >= player1YPosition && ballYPosition
                    <= player1YPosition + PLAYER_HEIGHT)){
            
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;

    
        }
        
        // draw score
        gc.fillText(player1Score + "\t\t\t\t\t\t\t\t" + player2Score, width/2, 100);
        
        // draw players
        
        gc.fillRect(player1XPosition, player1YPosition, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(player2XPosition, player2YPosition, PLAYER_WIDTH, PLAYER_HEIGHT);
  
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
