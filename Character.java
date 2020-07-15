import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Base Character class.
 * 
 * Contains all the functions shard by Clara and Ghost.
 * 
 * Can check for Trees to Front, Above, Below, ToTheLeft and ToTheRight.
 * 
 * Can get and set directions for Up, Down, Left and Right.
 * 
 * Can progress the standard animation frame.
 * 
 * Can progress the dead animation frame.
 * 
 * Can progress the scared animation frame.
 * 
 * Has the functionality to communicate between Clara and Ghost:
 *      To make Ghosts scared (Automatically counts for how long the 
 *                             Ghosts should be scared)
 *      To make Clara dead
 * 
 * Holds the intro sound.
 * 
 * Holds the ghost death sound.
 */
public class Character extends Actor
{
    /** Animation variables **/
    //Current frame that the animation is up to
    protected int _currentAnimationFrame = 0;  
    
    //Total frames in animation
    protected int _totalAnimationFrames = 3;
    
    /** Ghost Fear variables **/
    //NOTE: These variables are static to make it easy 
    //to communicate between Clara and the Ghosts. 
    //Normally, there would be a much better way to 
    //code this, but that involve public functions, 
    //and the students know nothing about that classes 
    //and scope yet.
    
    //Determines whether the Ghosts should be scared
    //or not
    private static boolean _scared = false;
    
    //The system time, in milliseconds, when the ghosts
    //started being scared
    private static long _systemTimeWhenScared = 0;
    
    //How long, in milliseconds, for the Ghosts to
    //be scared for
    private static long _systemTimeToStopScared = 5000;
    
    /** Clara variables **/
    //NOTE: It's static to make it easy to communicate
    //between Clara and the Ghosts. Normally, there
    //would be a much better way to code this,
    //but that involve public functions, and the
    //students know nothing about that classes and
    //scope yet.
    
    //Determines whether Clara is dead or not
    private static boolean _claraDead = false;
    
    /** Movement variables **/
    //The system, in milliseconds, since the 
    //Character's last move
    protected long _systemMoveTime = 0;
    
    //How far, in 1 unit cells, the Character
    //has moved up until this point
    protected int _currentDistanceMoved = 0;
    
    /** Sound variables **/
    //NOTE: It's static to make it easy to communicate
    //between Clara and the Ghosts. Normally, there
    //would be a much better way to code this,
    //but that involve public functions, and the
    //students know nothing about that classes and
    //scope yet.
    
    //Sound for game intro
    private static GreenfootSound _gameIntro = new GreenfootSound( "pacman_intro.wav" );
    
    //Sound for ghosts being eaten
    private GreenfootSound _ghostDie = new GreenfootSound( "pacman_eat_ghost.wav" );
    
    /**
     * Instantiates a new character
     * 
     * NOTE: It is important to shove static
     * variables in here, otherwise they don't
     * get reset. For example, _claraDead. It's
     * kind of important that she doesn't instantly 
     * die at the start of a new reset.
     */
    public Character()
    {
        _scared = false;
        _claraDead = false;
        _systemTimeWhenScared = 0;
        _systemTimeToStopScared = 5000;
        _gameIntro = new GreenfootSound( "pacman_intro.wav" );
        _systemMoveTime = 0;
        _currentDistanceMoved = 0;
    }

    /**
     * Base act function
     */
    public void act() 
    {
    }
    
    /**
     * Character checks if there is a tree in front of him <br>
     * 
     * @return true if there is a tree in front of Character, false otherwise
     */
    public boolean treeFront() 
    {
        //NOTE: The reason for checking not just directly in front, but also with slight offsets
        //is because otherwise the Character will literally slip between the little gaps between
        //the trees.
        return ( ( getObjectInFront( getRotation(), 10, Tree.class, getX(), getY() ) != null ) ||
                ( getObjectInFront( getRotation(), 10, Tree.class, getX() + 5, getY() ) != null ) ||
                ( getObjectInFront( getRotation(), 10, Tree.class, getX() - 5, getY() ) != null ) ||
                ( getObjectInFront( getRotation(), 10, Tree.class, getX(), getY() + 5 ) != null ) ||
                ( getObjectInFront( getRotation(), 10, Tree.class, getX(), getY() - 5 ) != null ) );
    }
    
    /**
     * Character checks if there is a ghost wall in front of him <br>
     * 
     * @return true if there is a ghost wall in front of Character, false otherwise
     */
    public boolean ghostWallFront() 
    {
        //NOTE: The reason for checking not just directly in front, but also with slight offsets
        //is for the same reason as the tree above, and just to make sure that the Character doesn't
        //slip between a little inbetween the Ghost Wall and a Tree.
         return ( ( getObjectInFront( getRotation(), 10, GhostWall.class, getX(), getY() ) != null ) ||
                ( getObjectInFront( getRotation(), 10, GhostWall.class, getX() + 5, getY() ) != null) ||
                ( getObjectInFront( getRotation(), 10, GhostWall.class, getX() - 5, getY() ) != null) ||
                ( getObjectInFront( getRotation(), 10, GhostWall.class, getX(), getY() + 5 ) != null) ||
                ( getObjectInFront( getRotation(), 10, GhostWall.class, getX(), getY() - 5 ) != null) );
    }

    /**
     * Character checks if there is a tree on above the character <br>
     * 
     * @return true if Character has a tree above them, false otherwise
     */
    public boolean treeAbove() 
    {
        //NOTE: The reason for checking not just directly there, but also with slight offsets
        //is because otherwise the Character will literally slip between the little gaps between
        //the trees.
        return ( ( getObjectInFront( 270, 19, Tree.class, getX(), getY() ) != null ) ||
                ( getObjectInFront( 270, 19, Tree.class, getX() + 5, getY() ) != null ) ||
                ( getObjectInFront( 270, 19, Tree.class, getX() - 5, getY() ) != null ) );
    }
    
     /**
     * Character checks if there is a tree below the character <br>
     * 
     * @return true if Character has a tree below the character, false otherwise
     */
    public boolean treeBelow() 
    {
        //NOTE: The reason for checking not just directly there, but also with slight offsets
        //is because otherwise the Character will literally slip between the little gaps between
        //the trees.
        return ( ( getObjectInFront( 90, 19, Tree.class, getX(), getY() ) != null ) ||
                ( getObjectInFront( 90, 19, Tree.class, getX() + 5, getY() ) != null ) ||
                ( getObjectInFront( 90, 19, Tree.class, getX() - 5, getY() ) != null ) );
    }
        
     /**
     * Character checks if there is a tree to the characters left <br>
     * 
     * @return true if Character has a tree to his left, false otherwise
     */
    public boolean treeToLeft() 
    {
        //NOTE: The reason for checking not just directly there, but also with slight offsets
        //is because otherwise the Character will literally slip between the little gaps between
        //the trees.
        return ( ( getObjectInFront( 180, 19, Tree.class, getX(), getY() ) != null ) ||
                ( getObjectInFront( 180, 19, Tree.class, getX(), getY() + 5 ) != null ) ||
                ( getObjectInFront( 180, 19, Tree.class, getX(), getY() - 5 ) != null ) );
    }
        
     /**
     * Character checks if there is a tree to the characters right <br>
     * 
     * @return true if Character has a tree to his right, false otherwise
     */
    public boolean treeToRight() 
    {
        //NOTE: The reason for checking not just directly there, but also with slight offsets
        //is because otherwise the Character will literally slip between the little gaps between
        //the trees.
        return ( ( getObjectInFront( 0, 19, Tree.class, getX(), getY() ) != null ) ||
                ( getObjectInFront( 0, 19, Tree.class, getX(), getY() + 5 ) != null ) ||
                ( getObjectInFront( 0, 19, Tree.class, getX(), getY() - 5 ) != null ) );
    }
    
    /**
     * Finds an object in the specified direction.
     * 
     * @param direction
     *            the direction in which to look for the object
     * @param steps
     *            number of cells to look ahead (1 means the next field, etc.)
     * @param clazz
     *            the (actor) class to look for
     * @param X
     *          x position to check from
     * @param Y
     *          y position to check from
     * @return the object that was found or null if none was found
     */
    protected Object getObjectInFront( int direction, int steps, Class<?> clazz, int X, int Y ) 
    {
        //Grabbing X and Y Pos to check
        int x = X;
        int y = Y;

        // Change x und y depending on the direction
        //NOTE: This function originally didn't use the direction you sent it,
        //it actually just grabbed the "getRotation()" Actor function.
        //Please don't change it back, it was a horrible logic error.
        switch ( direction ) {
        case 0:
            x = modulo( ( x + steps ), getWorld().getWidth() );
            break;

        case 90:
            y = modulo( ( y + steps ), getWorld().getHeight() );
            break;

        case 180:
            x = modulo( ( x - steps ), getWorld().getWidth() );
            break;

        case 270:
            y = modulo( ( y - steps ), getWorld().getHeight() );
            break;

        default: // Not a valid direction
            return null;
        }
        
        //Grabbing objects at that position that are the class
        //specified
        List<?> objects = getWorld().getObjectsAt( x, y, clazz );

        //If it's not empty, then there's something there
        if ( objects != null && objects.size() > 0 ) 
        {
            return objects.get( 0 );
        } 
        else 
        {
            return null;
        }
    }

    
    /**
     * A special modulo operation that never returns a negative number. This is
     * necessary to always stay inside the grid of the world.
     * <p>
     * The Java modulo operation would return -1 for something like -1%10, but
     * we would need 9.
     * <p>
     * Note: Depending on the programming language, the modulo operation for
     * negative numbers is defined differently.
     * 
     * @param a
     *            the first operand
     * @param b
     *            the second operand
     * @return the result of the modulo operation, always positive
     */
    private int modulo( int a, int b ) 
    {
        return ( a % b + b ) % b;
    }
    
    /**
     * Shows a popup with a warning message
     */
    public void showWarning( String englishMessage ) 
    {
        String message = "<html>" + englishMessage + "</html>";

        Object[] options = { "OK", "Exit Program" };
        int choice = JOptionPane.showOptionDialog(
                                null, 
                                message, 
                                "Warning",
                                JOptionPane.DEFAULT_OPTION, 
                                JOptionPane.WARNING_MESSAGE, 
                                null,
                                options, 
                                options[0] );

        if ( choice == 1 ) 
        {
            // Emergency stop. Greenfoot should restart after this.
            System.exit( 0 );
        } 
        else 
        {
            // Stop. This will still finish the act()-method.
            Greenfoot.stop();
            // Repaint, otherwise the world might keep displaying a dialog in
            // some cases
            getWorld().repaint();
        }
    }
    
    /**
     * Returns the instance of the MyClara class
     */
    protected MyClara getClara()
    {
        Level level = ( Level )getWorld();
        
        return level.retrievePlayer();
    }
    
    /**
     * Returns the instance of the GhostHealer class
     */
    protected GhostHealer getGhostHealer()
    {
        Level level = ( Level )getWorld();
        
        return level.retrieveGhostHealer();
    }
    
    /**
     * Returns the instance of the GhostWall class
     */
    protected GhostWall getGhostWall()
    {
        Level level = ( Level )getWorld();
        
        return level.retrieveGhostWall();
    }
    
    /**
     * Checks if the specified actor is above this one.
     * 
     * returns true if the actor is indeed above the one calling this.
     */
    protected boolean isAboveMe( Actor actor )
    {
        //EXPLANATION:
        //Because the top left hand corner of the screen is x=0 and y=0,
        //then if one Actor's Y is < another Actor's Y, then the first
        //Actor is above the second.
        
        return ( actor.getY() < getY() );
    }
    
    /**
     * Checks if the specified actor is below this one.
     * 
     * returns true if the actor is indeed below the one calling this.
     */
    protected boolean isBelowMe( Actor actor )
    {
        //EXPLANATION:
        //Because the top left hand corner of the screen is x=0 and y=0,
        //then if one Actor's Y is > another Actor's Y, then the first
        //Actor is below the second.
        
        return ( actor.getY() > getY() );
    }
    
    /**
     * Checks if the specified actor is to the left this one.
     * 
     * returns true if the actor is indeed to the left of the one calling this.
     */
    protected boolean isToMyLeft( Actor actor )
    {
        //EXPLANATION:
        //Because the top left hand corner of the screen is x=0 and y=0,
        //then if one Actor's X is < another Actor's X, then the first
        //Actor is to the left of the second.
        
        return ( actor.getX() < getX() );
    }
    
    /**
     * Checks if the specified actor is to the right this one.
     * 
     * returns true if the actor is indeed to the right of the one calling this.
     */
    protected boolean isToMyRight( Actor actor )
    {
        //EXPLANATION:
        //Because the top left hand corner of the screen is x=0 and y=0,
        //then if one Actor's X is > another Actor's X, then the first
        //Actor is to the right of the second.
        
        return ( actor.getX() > getX() );
    }
    
    /**
     * Sets the direction of the character that calls it.
     * 
     * Does not change the character's direction if there
     * is a tree in the way.
     * 
     * Valid inputs are "up", "down", "left" and "right".
     * For the sake of simplicity, capitalisations will be
     * ignored and the string will automatically be
     */
    protected void setDirection( String direction )
    {
        //Automatically set to lowercase for ease of use
        direction = direction.toLowerCase();
        
        if( getDirection() != direction)
        {
            //Not using the string in a  switch case because then 
            //I'd have to change the version Java being used. Which 
            //I don't want to do because it could screw someone up.
            if( direction.compareTo( "up" ) == 0 && !treeAbove() )
            {
                moveIntoCellAndResetDistanceMoved();
                setRotation( 270 );
            }
            else if( direction.compareTo( "down" ) == 0 && !treeBelow() )
            {
                moveIntoCellAndResetDistanceMoved();
                setRotation( 90 );
            }
            else if( direction.compareTo( "right" ) == 0 && !treeToRight() )
            {
                moveIntoCellAndResetDistanceMoved();
                setRotation( 0 );
            }
            else if( direction.compareTo("left") == 0 && !treeToLeft() )
            {
                moveIntoCellAndResetDistanceMoved();
                setRotation( 180 );
            }
        }
    }
    
    /**
     * Gets the direction of the character that calls it
     * 
     * Returns one of the following:
     * "up"
     * "down"
     * "left"
     * "right"
     */
    protected String getDirection()
    {
        switch( getRotation() )
        {
            case 270:
                return "up";
            case 90:
                return "down";
            case 0:
                return "right";
            case 180:
                return "left";
        }
        
        return null;
    }
    
    /**
     * Advances to the next frame of the animation
     * 
     * Requires total animation frames to be passed to it,
     * this is because we're reusing the function for Clara,
     * and simply changing the _totalAnimationFrames variable
     * doesn't work
     */
    protected void advanceAnimationFrame( int totalAnimationFrames )
    {
        _currentAnimationFrame++;
        
        if( _currentAnimationFrame >= totalAnimationFrames )
        {
            _currentAnimationFrame = 0;
        }
    }
    
    /**
     * Advances the animation for the character calling it.
     * 
     * NOTE: That this function also rotates the image so character is
     * facing the right way, i.e. for the Ghosts. Clara will need to override this.
     */
    protected void animate()
    {
        //Advance to the next frame logic
        advanceAnimationFrame( _totalAnimationFrames );
        
        //Sets the ghost image
        setImage( "ghostAnim" + Integer.toString(_currentAnimationFrame + 1) + ".png" );
        
        //Rotates the ghost so they stay face up
        getImage().rotate( getRotation() * -1 );
    }
    
    /**
     * Advances the dead animation for the character calling it.
     * 
     * NOTE: That this function also rotates the image so character is
     * facing the right way, i.e. for the Ghosts. Clara will need to override this.
     */
    protected void animateDead()
    {
        //Advance to the next frame logic
        advanceAnimationFrame( _totalAnimationFrames );
        
        //Sets the ghost image
        setImage( "ghostDeadAnim" + Integer.toString(_currentAnimationFrame + 1) + ".png" );
        
        //Rotates the ghost so they stay face up
        getImage().rotate( getRotation() );
    }
    
    /**
     * Advances the scared animation for the character calling it.
     * 
     * NOTE: That this function also rotates the image so character is
     * facing the right way, i.e. for the Ghosts. Clara will need to override this.
     */
    protected void animateScared()
    {
        //Advance to the next frame logic
        advanceAnimationFrame( _totalAnimationFrames );
        
        //Sets the ghost image
        setImage( "ghostScaredAnim" + Integer.toString(_currentAnimationFrame + 1) + ".png" );
        
        //Rotates the ghost so they stay face up
        getImage().rotate( getRotation() );
    }
    
    /**
     * Automatically wraps the characters around the world
     */
    protected void wrapAroundWorld()
    {
        //IMPORTANT NOTE: in Greenfoot, unlike literally
        //every other game making thing ever, getX() and
        //getY() DO NOT get the actual top left hand
        //corner X and Y positions, it actually automatically
        //grabs the center position.
        //AND knowing that the students are not to mess about
        //with the images, they should be 20 pixels by 20 pixels,
        //and so 5 pixels are in each four cardinal directions 
        //from the center.
        //Therefore, if we let literally any more than 4 pixels off
        //the side, they would actually be able to move outside the
        //level. Hence why we stop them at 4.
        
        //If character goes off the right edge of world
        //It ends up on the left edge
        if( getX() > getWorld().getWidth() - 4 )
        {
            setLocation( - 4, getY() );
        }
        
        //If character goes off left edge of the world
        //It ends up on the right edge
        if( getX() < - 4 )
        {
            setLocation( getWorld().getWidth() - 4, getY() );
        }
        
        //If character goes off the top of the world
        //It ends up on the bottom
        if( getY() < - 4 )
        {
            setLocation( getX(), getWorld().getHeight() - 4 );
        }
        
        //If character goes off the bottom of the world
        //It ends up on the top
        if( getY() > getWorld().getHeight() - 4 )
        {
            setLocation( getX(), - 4 );
        }
    }
    
    /**
     * Returns whether the entity is scared or not.
     */
    protected static boolean isScared()
    {
        //Checks if the Ghosts should still be scared and then stops them
        //from being scared if they should no longer be so.
        //NOTE: Normally, this kind of checking would actually be
        //in a very different place, but in order to cater for the students,
        //who aren't expected to know about object oriented programming,
        //it's shoved in this kinda awkward place that I can guarantee
        //will need to be called every act in order to check if they
        //are scared.
        if( System.currentTimeMillis() > ( _systemTimeWhenScared + _systemTimeToStopScared ) )
        {
            _scared = false;
        }
        
        return _scared;
    }
    
    /**
     * Makes the Ghosts scared
     */
    protected static void makeScared()
    {
        //Set them to scared
        _scared = true;
        
        //Save the time when they were set to be scared
        _systemTimeWhenScared = System.currentTimeMillis();
    }
    
    /**
     * Returns whether Clara is dead or not.
     */
    protected static boolean isClaraDead()
    {
        return _claraDead;
    }
    
    /**
     * Makes Clara dead
     */
    protected static void makeClaraDead()
    {
        _claraDead = true;
    }
    
    /**
     * Plays Ghosts' death sound
     */
    protected void playGhostEatenSound()
    {
        _ghostDie.play();
    }
    
    /**
     * Plays the pacman intro sound
     */
    protected static void playPacmanIntro()
    {
        _gameIntro.play();
    }
    
    /**
     * Checks if the pacman intro sound is still playing
     * 
     * Returns true if it is, else false
     */
    protected static boolean isPacmanIntroStillPlaying()
    {
        return _gameIntro.isPlaying();
    }
    
    /**
     * Moves the character at the specified speed
     */
    public void move( int move )
    {
        //If they aren't moving the distance of a
        //entire cell yet, then just keep moving
        if((_currentDistanceMoved + move) < 20)
        {
            _currentDistanceMoved += move;
            
            super.move(move);
        }
        //Otherwise, move correctly inside the new
        //cell and reset the distance moved
        else
        {
            moveIntoCellAndResetDistanceMoved();
        }
    }
    
    /**
     * Moves the character directly into the cell they
     * are moving into, and then resets the distance
     * moved
     */
    private void moveIntoCellAndResetDistanceMoved()
    {
        //Only move them into a new cell if they aren't already inside one
        if(_currentDistanceMoved > 0)
        {
            super.move(20 - _currentDistanceMoved);
                    
            _currentDistanceMoved = 0;
        }
    }
}
