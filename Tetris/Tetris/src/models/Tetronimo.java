package models;

import controllers.TetrisController;
import views.TetrisBoard;
import wheelsunh.users.Rectangle;
import wheelsunh.users.ShapeGroup;

import java.awt.*;
import java.util.ArrayList;

/**
 * Tetronimo.java:
 * An abstract class to model the base capabilities of a tetronimo
 *
 * @author Professor Rossi, modified by Rachel Hare
 * @version 2.0 August 1, 2020
 *
 * @see java.awt.Color
 */
public abstract class Tetronimo extends ShapeGroup
{
    /**
     * Constant to represent the size of the tetronimo
     */
    public static final int SIZE= 20;

    //four blocks
    protected Rectangle r1;
    protected Rectangle r2;
    protected Rectangle r3;
    protected Rectangle r4;

    Color color;

    //default rotation value
    protected int curRotation = 0;

    /**
     * Generates the four rectangles for the tetronimo and puts them on the screen, they are at the default coordinates
     * to start
     */
    public Tetronimo()
    {
        super(); //shapegroup constructor

        this.r1 = new Rectangle();
        this.r1.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r1.setFrameColor( Color.BLACK );

        this.r2 = new Rectangle();
        this.r2.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r2.setFrameColor( Color.BLACK );

        this.r3 = new Rectangle();
        this.r3.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r3.setFrameColor( Color.BLACK );

        this.r4 = new Rectangle();
        this.r4.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r4.setFrameColor( Color.BLACK );
    }

    /**
     * Increments the rotation of the tetronimo, other classes need to override this to provide the full functionality
     */
    public void rotate()
    {
        this.curRotation++;
    }

    /**
     * Shifts the tetronimo left one column, controller will check if move is okay first
     */
    public void shiftLeft()
    {

        //move left by Tetronimo.SIZE
        super.setLocation( super.getXLocation() - Tetronimo.SIZE, super.getYLocation() );
    }

    /**
     * Shifts the tetronimo right one column, controller will check if move is okay first
     */
    public void shiftRight()
    {
        //move right by Tetronimo.SIZE
        super.setLocation( super.getXLocation() + Tetronimo.SIZE, super.getYLocation() );
    }

    /**
     * gives index of each individual block in tetronimo
     */
    public ArrayList<Point> getBlockIndexes()
    {
        ArrayList<Point> list = new ArrayList<>();

        //points are saved as indexes
        Point p1 = new Point((this.r1.getXLocation() - TetrisBoard.LEFT_MARGIN)/Tetronimo.SIZE, this.r1.getYLocation()/Tetronimo.SIZE);
        Point p2 = new Point((this.r2.getXLocation() - TetrisBoard.LEFT_MARGIN)/Tetronimo.SIZE, this.r2.getYLocation()/Tetronimo.SIZE);
        Point p3 = new Point((this.r3.getXLocation() - TetrisBoard.LEFT_MARGIN)/Tetronimo.SIZE, this.r3.getYLocation()/Tetronimo.SIZE);
        Point p4 = new Point((this.r4.getXLocation() - TetrisBoard.LEFT_MARGIN)/Tetronimo.SIZE, this.r4.getYLocation()/Tetronimo.SIZE);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        return list;
    }

    /**
     * This method checks if rotating will move piece off screen
     * @return if piece can rotate without going off screen
     */
    public boolean rotateAvailable()
    {
        //rotating would mean its height becomes its width
        int newWidth = getHeight();

        //if current location plus new width puts it past right edge
        if(this.getXLocation() + newWidth > //left most part of piece plus width
                TetrisBoard.LEFT_MARGIN + (TetrisBoard.WIDTH * SIZE)) //right edge of board
        {
            //not okay to rotate
            return false;
        }

        //okay to rotate
        return true;
    }

    /**
     * This method sets the piece to a specified color
     * @param color
     */
    public void setColor(Color color)
    {
        this.color = color;

        this.r1.setColor(color);
        this.r2.setColor(color);
        this.r3.setColor(color);
        this.r4.setColor(color);

        this.r1.setFrameColor( Color.BLACK );
        this.r2.setFrameColor( Color.BLACK );
        this.r3.setFrameColor( Color.BLACK );
        this.r4.setFrameColor( Color.BLACK );
    }

    /**
     * Getter method for color of piece
     * @return color
     */
    public Color getColor()
    {
        return color;
    }

}