package controllers;

import models.*;
import views.TetrisBoard;

import java.awt.*;
import java.util.ArrayList;

/**
 * TetrisController.java:
 * Class to hold all of the game logic for tetris
 *
 * @author Professor Rossi, modified by Rachel Hare
 * @version 2.0 August 1, 2020
 */
public class TetrisController
{
    private final TetrisBoard TETRIS_BOARD; //communicates with View
    private final int ONE_ROW_POINTS = 100; //points for clearing row
    private final int FOUR_ROW_BONUS = 400; //bonus on top of 400 points already scored with one row points (4 x 100)

    private boolean[][] blockAvailability;
    private int score;

    /**
     * Constructor for controller
     *
     * @param tetrisBoard A tetris board instance
     */
    public TetrisController(TetrisBoard tetrisBoard)
    {
        this.TETRIS_BOARD = tetrisBoard; //view
        blockAvailability = new boolean[tetrisBoard.HEIGHT][TETRIS_BOARD.WIDTH]; //tracks open spaces

        score = 0;

        //create array of to keep track of which blocks on board are available
        for(int row = 0; row < TetrisBoard.HEIGHT; row++)
        {
            for(int column = 0; column < TetrisBoard.WIDTH; column++)
            {
                //all blocks available to start
                blockAvailability[row][column] = true;
            }
        }
    }

    /**
     * Randomly chooses the next tetronimo and returns it
     *
     * @return The next tetronimo to be played
     */
    public Tetronimo getNextTetromino()
    {
        Tetronimo tetronimo;

        //generate number between 0 and 6, inclusive
        int shapeNum = (int)(Math.random() * 7);

        switch(shapeNum)
        {
            case 0: {
                tetronimo = new DownSkew();
            }
            break;
            case 1: {
                tetronimo = new JShape();
            }
            break;
            case 2: {
                tetronimo = new LShape();
            }
            break;
            case 3: {
                tetronimo = new Square();
            }
            break;
            case 4: {
                tetronimo = new StraightLine();
            }
            break;
            case 5: {
                tetronimo = new TShape();
            }
            break;
            case 6: {
                tetronimo = new UpSkew();
            }
            break;
            default:
                tetronimo = new TShape();
        }

        //put in center
        tetronimo.setLocation( TetrisBoard.LEFT_MARGIN + (5 * Tetronimo.SIZE), 0 );

        return tetronimo;
    }

    /**
     * Method to determine if the tetronimo has landed
     *
     * @param tetronimo The tetronimo to evaluate
     * @return True if the tetronimo has landed (on the bottom of the board or another tetronimo), false if it has not
     */
    public  boolean tetronimoLanded( Tetronimo tetronimo )
    {

        //loop through location of each square in tetronimo
        for(Point movingPieceSquare : tetronimo.getBlockIndexes())
        {
            //return whether another y move would put block past bottom of board
             if (movingPieceSquare.getY() * Tetronimo.SIZE  + Tetronimo.SIZE >= TetrisBoard.HEIGHT * Tetronimo.SIZE)
            {
                //make spaces unavailable
                inactivateBlocks(tetronimo);
                return true;
            }

            //if same x location, but plus 1 y has no availability
            else if(blockAvailability[(int)movingPieceSquare.getY() + 1][(int)movingPieceSquare.getX()] == false)
            {
                //make spaces unavailable
                inactivateBlocks(tetronimo);
                return true;
            }
        }
        return false;
    }

    /**
     * This method inactivates the spaces on the grid where the tetronimo lands
     *
     * @param tetronimo
     */
    private void inactivateBlocks(Tetronimo tetronimo)
    {
        //loop through tetronimo's current 4 blocks
        for(Point point : tetronimo.getBlockIndexes())
        {
            //get indexes (will return index, not coordinate)
            int row = (int) point.getY();
            int column = (int) point.getX();

            //inactivate block and set board color to match block color
            blockAvailability[row][column] = false;
            TETRIS_BOARD.setSquareColor(column, row, tetronimo.getColor());
        }

        //now check if row has been cleared
        checkClearRow();
    }

    /**
     * This method checks to see if a row is cleared after a piece lands
     */
    private void checkClearRow()
    {
        int rowsClearedCounter = 0; //number of rows cleared in a single landing

        //loop through rows
        for(int row = 0; row < TETRIS_BOARD.HEIGHT; row++)
        {
            //number of inactive squares in row
            //resets to zero on each iteration
            int rowSquaresCounter = 0;

            //loop throw columns to count inactive blocks in a row
            for(int column = 0; column < TetrisBoard.WIDTH; column++)
            {
                //if Y coordinate of inactive block is on this row
                if (blockAvailability[row][column] == false)
                {
                    //increment number of inactive squares in row
                    rowSquaresCounter++;
                }

                //if every square in row is inactive
                if (rowSquaresCounter == TETRIS_BOARD.WIDTH)
                {
                    //keep track of how many rows are being cleared in one move
                    rowsClearedCounter++;
                    clearRow(row);
                }
            }

            //if four or more rows are cleared in one go
            if (rowsClearedCounter >= 4)
            {
                //add additional 400 points to 400 already earned
                score += FOUR_ROW_BONUS;
            }
        }
    }

    /**
     * This method clears a row once it's full
     *
     * @param rowToClear
     */
    private void clearRow(int rowToClear)
    {
        //update score
        score += ONE_ROW_POINTS;

        //loop through blocks in that rowToClear (moving across columns)
        for (int column = 0; column < TetrisBoard.WIDTH; column++)
        {
            //make block available again
            blockAvailability[rowToClear][column] = true;

            //change to white
            TETRIS_BOARD.setSquareColor(column,rowToClear, Color.WHITE);
        }

        //start at the row above the row to clear and work up to top
        //will not need to add to zero row (no row above it)
        for(int row = rowToClear; row > 0; row--)
        {
            //loop through columns
            for(int column = 0; column < TetrisBoard.WIDTH; column++)
            {
                //if block above is taken (availability = false)
                if(blockAvailability[row - 1][column] == false)
                {
                    //save color of block in rowToClear above before changing it to white
                    Color colorAbove = TETRIS_BOARD.getSquareColor(column, row - 1);

                    //change square above to available and white
                    TETRIS_BOARD.setSquareColor(column, row - 1, Color.WHITE);
                    blockAvailability[row - 1][column] = true;

                    //change current row to colorAbove and make unavailable
                    TETRIS_BOARD.setSquareColor(column, row, colorAbove);
                    blockAvailability[row][column] = false;
                }
            }
        }
    }

    /**
     * This game checks if the game is over (ie there is no more room the next
     * tetris piece
     *
     * @param tetronimo
     * @return whether or not the game is over
     */
    public boolean checkGameOver(Tetronimo tetronimo)
    {
        //if it has landed
        if(tetronimoLanded(tetronimo))
        {
            //if the y location minus the height puts it off the top of the board
            if(tetronimo.getYLocation() - tetronimo.getHeight() <= 0)
            {
                //no more room, game is over
                return true;
            }
        }
        //still room, game not over
        return false;
    }

    /**
     * This method checks that the tetronimo will not go offscreen or into
     * an inactive block if shifted right
     * @param tetronimo
     *
     * @return boolean for whether a right shift is available
     */
    public boolean shiftAvailable(Tetronimo tetronimo, int direction)
    {
        //make a copy of tetronimo's points
        ArrayList<Point> movePreview = new ArrayList<>();
        ArrayList<Point> currentBlock = tetronimo.getBlockIndexes();

        //loop through each block in current piece
        for(Point currentPoint : currentBlock)
        {
            //for 4 blocks in new array of blocks
            for (int i = 0; i < 4; i++)
            {
                //get preview of moving right
                if (direction == TETRIS_BOARD.RIGHT)
                {
                    //move to column to right
                    movePreview.add(new Point((int)currentPoint.getY(),(int)currentPoint.getX() + 1));
                }
                //get preview of moving left
                else if (direction == TETRIS_BOARD.LEFT)
                {
                    //move to column to left
                    movePreview.add(new Point((int)currentPoint.getY() ,(int)currentPoint.getX() - 1));
                }
            }
        }

        //if moving right
        //won't go past right side of board
        //and does not intersect an inactive block
        if(tetronimo.getXLocation() + tetronimo.getWidth() < (TetrisBoard.WIDTH * Tetronimo.SIZE) + TetrisBoard.LEFT_MARGIN &&
                direction == TetrisBoard.RIGHT &&
                !intersectInactiveBlock(movePreview))
        {
            //okay to move right
            return true;
        }

        //if moving left
        //won't go past left side
        //and does not intersect an inactive block
        else if (tetronimo.getXLocation() - Tetronimo.SIZE >= TetrisBoard.LEFT_MARGIN &&
            direction == TetrisBoard.LEFT &&
            !intersectInactiveBlock(movePreview))
        {
            //okay to move left
            return true;
        }

        //the move will put the piece of the board or into an inactive block
        return false;
    }

    /**
     * This function compares to ArrayLists of Points (blocks) to see if they itnersect
     * @param rightPreview
     * @return if they intersect
     */
    private boolean intersectInactiveBlock(ArrayList<Point> rightPreview)
    {
        //loop through points
        for(Point point : rightPreview) {
            //if inactive block at that location
            if(blockAvailability[(int)point.getX()][(int)point.getY()] == false)
            {
                //the point intersects another point
                return true;
            }
        }
        //the points do not overlap
        return false;
    }

    /**
     * This method returns the score
     * @return score
     */
    public int getScore()
    {
        return score;
    }

}
