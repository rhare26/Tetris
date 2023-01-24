package models;

import java.awt.*;

/**
 * StraightLine.java:
 * Creates a straight line tetronimo
 *
 * @author Professor Rossi, modified by Rachel Hare
 * @version 2.0 August 1, 2020
 *
 * @see java.awt.Point
 */
public class StraightLine extends Tetronimo
{
    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */

    final private int H_ORIENTATION = 0;
    final private int V_ORIENTATION = 1;
    final private int NUM_ORIENTATIONS = 2;

    public StraightLine()
    {
        //set 4 boxes stacked vertically
        super.r1.setLocation( 0, 0 );
        super.r2.setLocation( 0, Tetronimo.SIZE );
        super.r3.setLocation( 0, Tetronimo.SIZE * 2 );
        super.r4.setLocation( 0, Tetronimo.SIZE * 3 );

        super.setColor(Color.CYAN);

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
        //if rotating won't put it off screen
        if(rotateAvailable())
        {
            //increments current rotation
            super.rotate();

            //save current location in a Point
            Point curLoc = super.getLocation();
            super.setLocation(0, 0);

            //if vertical column
            if (super.curRotation % NUM_ORIENTATIONS == V_ORIENTATION)
            {
                //lay out block horizontally
                super.r1.setLocation(0, 0);
                super.r2.setLocation(Tetronimo.SIZE, 0);
                super.r3.setLocation(Tetronimo.SIZE * 2, 0);
                super.r4.setLocation(Tetronimo.SIZE * 3, 0);
            }

            //if horizontal row
            else
            {
                //stack blocks vertically
                super.r1.setLocation(0, 0);
                super.r2.setLocation(0, Tetronimo.SIZE);
                super.r3.setLocation(0, Tetronimo.SIZE * 2);
                super.r4.setLocation(0, Tetronimo.SIZE * 3);
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
        //if horizontal row
        if( this.curRotation % NUM_ORIENTATIONS == V_ORIENTATION )
        {
            return Tetronimo.SIZE;
        }

        else
        {
            return Tetronimo.SIZE * 4;
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
        if( this.curRotation % NUM_ORIENTATIONS == V_ORIENTATION )
        {
            return Tetronimo.SIZE * 4;
        }
        else
        {
            return Tetronimo.SIZE;
        }
    }
}
