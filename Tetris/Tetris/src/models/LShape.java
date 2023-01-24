package models;

import java.awt.*;
/**
 * DownSkew.java:
 * An abstract class to model the an L shape tetronimo
 *
 * @author Professor Rossi, modified by Rachel Hare
 * @version 2.0 August 1, 2020
 *
 */
public class LShape extends Tetronimo
{
    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */
    final private int VERT_BOTTOM_RIGHT_HOOK = 0; //L shape
    final private int HOR_BOT_LEFT_HOOK = 1;
    final private int VERT_TOP_LEFT_HOOK = 2;
    final private int HOR_TOP_RIGHT_HOOK = 3;

    final private int NUM_ORIENTATIONS = 4;

    public LShape()
    {
        //set in L shape
        super.r1.setLocation( Tetronimo.SIZE, Tetronimo.SIZE * 2 ); //bottom right hook
        super.r2.setLocation( 0, Tetronimo.SIZE * 2); //bottom right
        super.r3.setLocation( 0, Tetronimo.SIZE ); //middle of vertical
        super.r4.setLocation( 0, 0 ); //top of vertical

        super.setColor(Color.orange);

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
        if(rotateAvailable())
        {
            //increments current rotation
            super.rotate();

            //save current location in a Point
            Point curLoc = super.getLocation();
            super.setLocation(0, 0);

            //if regular L shape
            if (super.curRotation % NUM_ORIENTATIONS == VERT_BOTTOM_RIGHT_HOOK)
            {
                super.r1.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2); //bottom right hook
                super.r2.setLocation(0, Tetronimo.SIZE * 2);
                super.r3.setLocation(0, Tetronimo.SIZE);
                super.r4.setLocation(0, 0);
            }

            //if horizontal with hook on bottom left
            else if (super.curRotation % NUM_ORIENTATIONS == HOR_BOT_LEFT_HOOK)
            {
                super.r1.setLocation(0, 0);
                super.r2.setLocation(Tetronimo.SIZE, 0);
                super.r3.setLocation(Tetronimo.SIZE * 2, 0);
                super.r4.setLocation(0, Tetronimo.SIZE); //hook on bottom left
            }

            //if vertical with hook on top left
            else if (super.curRotation % NUM_ORIENTATIONS == VERT_TOP_LEFT_HOOK)
            {
                super.r1.setLocation(0, 0); //top left hook
                super.r2.setLocation(Tetronimo.SIZE, 0);
                super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r4.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);
            }

            //else horizontal with top right hook
            else
            {
                super.r1.setLocation(0, Tetronimo.SIZE);
                super.r2.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r3.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE);
                super.r4.setLocation(Tetronimo.SIZE * 2, 0); //hook on top right
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
        //if hmostly vertical
        if( this.curRotation % NUM_ORIENTATIONS == VERT_BOTTOM_RIGHT_HOOK || this.curRotation % NUM_ORIENTATIONS == VERT_TOP_LEFT_HOOK)
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
        if( this.curRotation % NUM_ORIENTATIONS == VERT_BOTTOM_RIGHT_HOOK || this.curRotation % NUM_ORIENTATIONS == VERT_TOP_LEFT_HOOK)
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
