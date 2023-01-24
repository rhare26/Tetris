package models;

import java.awt.*;
/**
 * DownSkew.java:
 * An abstract class to model a skewed tetronimo sloping upwards when vertical
 *
 * @author Professor Rossi, modified by Rachel Hare
 * @version 2.0 August 1, 2020
 *
 */
public class UpSkew extends Tetronimo
{
    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */
    final private int VERTICAL = 0;
    final private int HORIZONTAL = 1;

    final private int NUM_ORIENTATIONS = 2;

    public UpSkew()
    {
        //set in skew shape
        super.r1.setLocation(0, Tetronimo.SIZE * 2);
        super.r2.setLocation(0, Tetronimo.SIZE);
        super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
        super.r4.setLocation(Tetronimo.SIZE, 0);

        super.setColor(Color.red);

        //add to shapeGroup
        super.add(r1);
        super.add(r2);
        super.add(r3);
        super.add(r4);
    }

    /**
     * Rotates the tetronimo
     */
    @Override
    public void rotate()
    {
        if (rotateAvailable())
        {
            //increments current rotation
            super.rotate();

            //save current location in a Point
            Point curLoc = super.getLocation();
            super.setLocation(0, 0);

            //if regular vertical skew
            if (super.curRotation % NUM_ORIENTATIONS == VERTICAL)
            {
                //back to first position
                super.r1.setLocation(0, Tetronimo.SIZE * 2);
                super.r2.setLocation(0, Tetronimo.SIZE);
                super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r4.setLocation(Tetronimo.SIZE, 0);
            }

            //else horizontal skew
            else
            {
                //rotate to horizontal with hook on bottom right
                super.r1.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE);
                super.r2.setLocation(0, 0);
                super.r3.setLocation(Tetronimo.SIZE, 0);
                super.r4.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
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
        if (this.curRotation % NUM_ORIENTATIONS == VERTICAL || this.curRotation % NUM_ORIENTATIONS == VERTICAL)
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
        if (this.curRotation % NUM_ORIENTATIONS == VERTICAL || this.curRotation % NUM_ORIENTATIONS == VERTICAL)
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
