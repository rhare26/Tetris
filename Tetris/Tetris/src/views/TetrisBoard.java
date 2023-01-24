package views;

import controllers.TetrisController;
import models.Tetronimo;
import wheelsunh.users.*;
import wheelsunh.users.Frame;
import wheelsunh.users.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * TetrisBoard.java:
 * Class to model the tetris board
 *
 * @author Professor Rossi, modified by Rachel Hare
 * @version 2.0 August 1, 2020
 *
 * @see java.awt.Color
 * @see java.awt.event.KeyListener
 * @see java.awt.event.KeyEvent
 */
public class TetrisBoard implements KeyListener
{
    /**
     * Constant to represent the width of the board
     */
    public static final int WIDTH = 10;

    /**
     * Constant to represent the height of the board
     */
    public static final int HEIGHT = 24;

    /**
     * Constant to represent the left margin
     */
    public static final int LEFT_MARGIN = 250;

    /**
     * Constant to represent movement to the left
     */
    public static final int LEFT = 0;

    /**
     * Constant to represent movement to the right
     */
    public static final int RIGHT = 1;

    /**
     * Constant for default speed (in milliseconds)
     */
    public static final int BASE_TIME_INTERVAL = 300;

    /**
     * Constant for faster speed (in milliseconds)
     */
    public static final int FAST_TIME_INTERVAL = 50;

    //how many milliseconds between each piece movement
    private int timeInterval;

    //controller
    private final TetrisController CONTROLLER;

    private Tetronimo tetronimo; //current tetronimo that is falling
    private Tetronimo nextTetronimo; //next tetronimo
    private Rectangle[][] playingField; //game grid
    private final String GAME_OVER_MESSAGE = "Game over!"; //game over message
    private TextBox gameMessageLabel; //Textbox for game over string
    private TextBox scoreBoard; //Textbook to track score

    /**
     * Constructor to initialize the board
     *
     * @param frame The wheelsunh frame (so we can add this class as a key listener for the frame)
     */
    public TetrisBoard(Frame frame)
    {
        //add listener for keyboard & controller
        frame.addKeyListener( this );
        this.CONTROLLER = new TetrisController( this );

        //set up scoreboard
        scoreBoard = new TextBox(550, 100);
        scoreBoard.setText("Score: " + CONTROLLER.getScore());
        scoreBoard.setSize(90, 70);
        scoreBoard.setFillColor(Color.lightGray);

        //set up game over message
        gameMessageLabel = new TextBox(550, 200);
        gameMessageLabel.setText("");
        gameMessageLabel.setSize(90, 70);
        gameMessageLabel.setFillColor(Color.lightGray);

        //set speed to slower default
        timeInterval = BASE_TIME_INTERVAL;

        //lay out grid
        this.buildBoard();

        //start game
        this.run();
    }

    /**
     * Builds the playing field for tetris
     */
    private void buildBoard()
    {
        //initialize grid
        this.playingField = new Rectangle[ WIDTH ][ HEIGHT ];

        //set up pieces in grid
        for ( int i = 0; i < TetrisBoard.WIDTH; i++ )
        {
            for ( int j = 0; j < TetrisBoard.HEIGHT; j++ )
            {
                //set up blocks
                this.playingField[ i ][ j ] = new Rectangle();
                this.playingField[ i ][ j ].setLocation( i * Tetronimo.SIZE + LEFT_MARGIN, j * Tetronimo.SIZE );
                this.playingField[ i ][ j ].setSize( Tetronimo.SIZE, Tetronimo.SIZE );
                this.playingField[ i ][ j ].setColor( Color.WHITE );
                this.playingField[ i ][ j ].setFrameColor( Color.BLACK );
            }
        }
    }

    /**
     * This method sets a square on the grid to a specified color
     *
     * @param column the column or x index
     * @param row the row or y index
     * @param color the color to change to
     */
    public void setSquareColor(int column, int row, Color color)
    {
        this.playingField[column][row].setColor(color);
        this.playingField[column][row].setFrameColor(Color.BLACK);
    }

    /**
     * This returns what color a square currently is
     * @param column the column or x index
     * @param row the row or y index
     * @return color of square
     */
    public Color getSquareColor(int column, int row)
    {
        return this.playingField[column][row].getFillColor();
    }
    /**
     * Starts gameplay and is responsible for keeping the game going (INCOMPLETE)
     */
    public void run()
    {
        //asks controller to generate a tetronimo to start
        this.tetronimo = this.CONTROLLER.getNextTetromino();

        //check if game over after each piece lands
        while(!CONTROLLER.checkGameOver(tetronimo))
        {
            //asks controller to generate next tetronimo to preview on side
            this.nextTetronimo = this.CONTROLLER.getNextTetromino();
            this.nextTetronimo.setLocation(LEFT_MARGIN + (WIDTH * Tetronimo.SIZE) + 40, 50);

            //while landed bool is false, keep falling
            while (!this.CONTROLLER.tetronimoLanded(this.tetronimo))
            {
                //keep x location, add one block to y location
                this.tetronimo.setLocation(this.tetronimo.getXLocation(), this.tetronimo.getYLocation() + Tetronimo.SIZE);

                //wait before next move
                Utilities.sleep(timeInterval);
            }

            //once landed, check for score update
            scoreBoard.setText("Score: " + CONTROLLER.getScore());

            //hild old tetronimo offscreen
            this.tetronimo.setLocation(-500,-500);

            //move the nest tetronimo into the current spot
            tetronimo = nextTetronimo;
            this.tetronimo.setLocation(LEFT_MARGIN + (WIDTH * Tetronimo.SIZE)/2, 0);
        }

        //exit while loop when game is over
        //display the message below the score
        gameMessageLabel.setText(GAME_OVER_MESSAGE);
    }

    /**
     * This method is not used in this program
     *
     * @param e The key event
     */
    @Override
    public void keyTyped( KeyEvent e )
    {
        //not in use
    }

    /**
     * Handles the key events by the user (INCOMPLETE)
     *
     * @param e The key event
     */
    @Override
    public void keyPressed( KeyEvent e )
    {
        int key = e.getKeyCode();

        //if no active tetronimo
        if(this.tetronimo == null)
        {
            return;
        }

        switch(key)
        {
            //up arrow
            case 38:
                this.tetronimo.rotate();

                break;

            //left arrow
            case 37:
                //if moving left one block won't move piece off left side board
                if(CONTROLLER.shiftAvailable(tetronimo, LEFT))
                {
                    //move left
                    tetronimo.shiftLeft();
                }
                break;

            //right arrow
            case 39:
                //if moving right one block won't move piece off right side of board
                if(CONTROLLER.shiftAvailable(tetronimo, RIGHT))
                {
                    //move right
                    this.tetronimo.shiftRight();
                }
                break;

            //down button
            case 40:
                timeInterval = FAST_TIME_INTERVAL;
                break;


        }

    }

    /**
     * If user releases down key.
     *
     * NOTE: Speed is only increased when down key is pressed. Will not be affected by releasing other keys
     *
     * @param e The key event
     */
    @Override
    public void keyReleased( KeyEvent e )
    {
        //return speed to normal
        timeInterval = BASE_TIME_INTERVAL;
    }
}