package models;

import java.awt.*;

/**
 * DownSkew.java:
 * An abstract class to model a skewed tetronimo sloping downwards when vertical
 *
 * @author Professor Rossi, modified by Rachel Hare
 * @version 2.0 August 1, 2020
 *
 */
public class DownSkew extends Tetronimo
{

    final private int VERTICAL = 0; //position where height is greater than width
    final private int NUM_ORIENTATIONS = 2; //only two different positions possible
    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */
    public DownSkew()
    {
        //set original shape
        super.r1.setLocation( Tetronimo.SIZE, Tetronimo.SIZE * 2 ); //bottom right hook
        super.r2.setLocation( Tetronimo.SIZE , Tetronimo.SIZE ); //bottom right
        super.r3.setLocation( 0, Tetronimo.SIZE ); //middle of vertical
        super.r4.setLocation( 0, 0 ); //top of vertical

        setColor(Color.green);

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
        //if won't put past edge of game board
        if(rotateAvailable())
        {
            //increments current rotation
            super.rotate();

            //save current location in a Point
            Point curLoc = super.getLocation();
            super.setLocation(0, 0);

            //if vertical
            if (super.curRotation % NUM_ORIENTATIONS == VERTICAL)
            {
                super.r1.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);
                super.r2.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r3.setLocation(0, Tetronimo.SIZE);
                super.r4.setLocation(0, 0);
            }

            //else horizontal
            else
            {
                super.r1.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r2.setLocation(Tetronimo.SIZE, 0);
                super.r3.setLocation(Tetronimo.SIZE * 2, 0);
                super.r4.setLocation(0, Tetronimo.SIZE);
            }

            //set location to current Point
            super.setLocation(curLoc);
        }
    }

    /**
     * Gets the height of the tetronimo based on the orientation
     *
     * @return The height of the tetronimo
     */
    @Override
    public int getHeight()
    {
        //if vertical
        if( this.curRotation % NUM_ORIENTATIONS == VERTICAL)
        {
            return Tetronimo.SIZE * 3;
        }

        //else horizontal
        else
        {
            return Tetronimo.SIZE * 2;
        }
    }

    /**
     * Gets the width of the tetronimo based on the orientation
     *
     * @return The width of the tetronimo
     */
    @Override
    public int getWidth()
    {
        //if mostly vertical
        if( this.curRotation % NUM_ORIENTATIONS == VERTICAL)
        {
            return Tetronimo.SIZE * 2;
        }

        //else mostly horizontal
        else
        {
            return Tetronimo.SIZE * 3;
        }
    }
}
