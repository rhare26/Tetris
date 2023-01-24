package models;

import java.awt.*;

/**
 * Square.java:
 * An abstract class to model the square tetronimo
 *
 * @author Professor Rossi, modified by Rachel Hare
 * @version 2.0 August 1, 2020
 *
 */
public class Square extends Tetronimo
{
    /**
     * Creates the tetronimo
     */

    public Square()
    {
        //set 4 boxes stacked vertically
        super.r1.setLocation( 0, 0 ); //top left
        super.r2.setLocation( Tetronimo.SIZE , 0 ); //top right
        super.r3.setLocation( 0, Tetronimo.SIZE ); //bottom right
        super.r4.setLocation( Tetronimo.SIZE, Tetronimo.SIZE ); //bottom left

        super.setColor(Color.yellow);

        //add to shapeGroup
        super.add( r1 );
        super.add( r2 );
        super.add( r3 );
        super.add( r4 );
    }

    /**
     * Rotates the tetronimo
     */
    @Override
    public void rotate()
    {
        //square shape does not rotate
    }

    /**
     * Gets the height of the tetronimo
     *
     * @return The height of the tetronimo
     */
    @Override
    public int getHeight()
    {
        //height is always two blocks
        return Tetronimo.SIZE*2;
    }

    /**
     * Gets the width of the tetronimo
     *
     * @return The width of the tetronimo
     */
    @Override
    public int getWidth()
    {
        //height is always two blocks
        return Tetronimo.SIZE*2;
    }
}