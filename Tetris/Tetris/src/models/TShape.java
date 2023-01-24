package models;

import java.awt.*;
/**
 * DownSkew.java:
 * An abstract class to model the t-shaped tetronimo
 *
 * @author Professor Rossi, modified by Rachel Hare
 * @version 2.0 August 1, 2020
 *
 */
public class TShape extends Tetronimo
{
    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */
    final private int HOR_POINT_UP = 0; //L shape
    final private int VER_POINT_RIGHT = 1;
    final private int HOR_POINT_DOWN = 2;
    final private int VER_POINT_LEFT = 3;

    final private int NUM_ORIENTATIONS = 4;

    /**
     * Constructor for T shape Tetronimo
     */
    public TShape()
    {
        //set shape
        super.r1.setLocation( Tetronimo.SIZE, 0 );
        super.r2.setLocation( 0, Tetronimo.SIZE);
        super.r3.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );
        super.r4.setLocation( Tetronimo.SIZE * 2, Tetronimo.SIZE);

        super.setColor(new Color(175,80,175));

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
        //if rotating won't put it off the board
        if(rotateAvailable())
        {
            //increments current rotation
            super.rotate();

            //save current location in a Point
            Point curLoc = super.getLocation();
            super.setLocation(0, 0);

            //if regular T shape
            if (super.curRotation % NUM_ORIENTATIONS == HOR_POINT_UP)
            {
                super.r1.setLocation(Tetronimo.SIZE, 0);
                super.r2.setLocation(0, Tetronimo.SIZE);
                super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r4.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE);
            }

            //if vertical with point on right
            else if (super.curRotation % NUM_ORIENTATIONS == VER_POINT_RIGHT)
            {
                super.r1.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r2.setLocation(0, 0);
                super.r3.setLocation(0, Tetronimo.SIZE);
                super.r4.setLocation(0, Tetronimo.SIZE * 2);
            }

            //if horizontal with point on bottom
            else if (super.curRotation % NUM_ORIENTATIONS == HOR_POINT_DOWN)
            {
                super.r1.setLocation(0, 0);
                super.r2.setLocation(Tetronimo.SIZE, 0);
                super.r3.setLocation(Tetronimo.SIZE * 2, 0);
                super.r4.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
            }

            //else vertical with point on left
            else
            {
                super.r1.setLocation(Tetronimo.SIZE, 0);
                super.r2.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);
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
        //if mostly vertical
        if( this.curRotation % NUM_ORIENTATIONS == VER_POINT_LEFT || this.curRotation % NUM_ORIENTATIONS == VER_POINT_RIGHT)
        {
            return Tetronimo.SIZE * 3;
        }

        //else mostly horizontal
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
        if( this.curRotation % NUM_ORIENTATIONS == VER_POINT_LEFT || this.curRotation % NUM_ORIENTATIONS == VER_POINT_RIGHT)
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
