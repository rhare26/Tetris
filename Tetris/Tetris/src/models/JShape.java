package models;

import java.awt.*;
/**
 * LShape.java:
 * An abstract class to model an L tetronimo
 *
 * @author Professor Rossi, modified by Rachel Hare
 * @version 2.0 August 1, 2020
 *
 */
public class JShape extends Tetronimo
{

    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */
    final private int VERT_BOTTOM_LEFT_HOOK = 0; //J shape vertical, hook on left
    final private int HOR_TOP_LEFT_HOOK = 1; //J rotated clockwise (horizontal, hook is on left)
    final private int VERT_TOP_RIGHT_HOOK = 2; //J shape rotated clockwise 2x (vertical, hook on right)
    final private int HOR_BOT_RIGHT_HOOK = 3; //J shape rotated clockwise 3x ( horizontal, hook down on right)

    final private int NUM_ORIENTATIONS = 4;

    public JShape()
    {
        //set in J shape, orientation 1
        super.r1.setLocation( 0, Tetronimo.SIZE * 2 ); //bottom left hook
        super.r2.setLocation( Tetronimo.SIZE, Tetronimo.SIZE * 2); //bottom right
        super.r3.setLocation( Tetronimo.SIZE, Tetronimo.SIZE ); //middle of vertical
        super.r4.setLocation( Tetronimo.SIZE, 0 ); //top of vertical

        setColor(Color.blue);

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
        //if rotating won't put it off of board
        if (rotateAvailable())
        {
            //increments current rotation
            super.rotate();

            //save current location in a Point
            Point curLoc = super.getLocation();
            super.setLocation(0, 0);

            //if regular j shape
            if (super.curRotation % NUM_ORIENTATIONS == VERT_BOTTOM_LEFT_HOOK)
            {
                //back to first position
                super.r1.setLocation(0, Tetronimo.SIZE * 2); //bottom left hook
                super.r2.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2); //bottom right
                super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE); //middle of vertical
                super.r4.setLocation(Tetronimo.SIZE, 0); //top of vertical
            }

            //if horizontal with hook on top left
            else if (super.curRotation % NUM_ORIENTATIONS == HOR_TOP_LEFT_HOOK)
            {
                //rotate to horizontal with hook on top left
                super.r1.setLocation(0, 0); //top left hook
                super.r2.setLocation(0, Tetronimo.SIZE); //same x, lower y level
                super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE); //over 1 x, , lower y level
                super.r4.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE); //over 2 x, , lower y level
            }

            //if vertical with hook on top right
            else if (super.curRotation % NUM_ORIENTATIONS == VERT_TOP_RIGHT_HOOK)
            {
                //rotate to vertical with hook on top right
                super.r1.setLocation(0, 0); //top of vertical
                super.r2.setLocation(0, Tetronimo.SIZE); //middle of vertical
                super.r3.setLocation(0, Tetronimo.SIZE * 2); //bottom of vertical
                super.r4.setLocation(Tetronimo.SIZE, 0); //right hook
            } else
            {
                //rotate to horizontal with hook on bottom right
                super.r1.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE); //bottom right hook
                super.r2.setLocation(0, 0); //leftmost
                super.r3.setLocation(Tetronimo.SIZE, 0); //middle of horizontal
                super.r4.setLocation(Tetronimo.SIZE * 2, 0); //rightmost of horizontal
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
        if( this.curRotation % NUM_ORIENTATIONS == VERT_BOTTOM_LEFT_HOOK || this.curRotation % NUM_ORIENTATIONS == VERT_TOP_RIGHT_HOOK)
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
        //if vertical
        if( this.curRotation % NUM_ORIENTATIONS == VERT_BOTTOM_LEFT_HOOK || this.curRotation % NUM_ORIENTATIONS == VERT_TOP_RIGHT_HOOK)
        {
            return Tetronimo.SIZE * 2;
        }

        //else horizontal
        else
        {
            return Tetronimo.SIZE * 3;
        }
    }
}
