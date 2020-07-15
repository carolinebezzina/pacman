import greenfoot.*;

/**
 * Base Clara class
 * 
 * Contains all functionality unique to Clara and separate from the Ghosts.
 * 
 * Can check for, and remove, leaves.
 * 
 * Can check for, and remove, mushrooms.
 * 
 * Can check that all the leaves are eaten.
 * 
 * Can play Clara's death sound.
 * 
 * Can play Clara's eating leaf sound.
 * 
 * Can progress Clara's standard animation frame.
 * 
 * Can set Clara to her death frame.
 * 
 * Can advance to the next level.
 * 
 * Can check what the current level is.
 */
public class Clara extends Character 
{
    /** Animation variable **/
    //Total animation frames for Clara's standard animation
    protected int _totalAnimationFrames = 2;
    
    /** Sound variables **/
    //Sound of eating a leaf
    private GreenfootSound _eatLeaf = new GreenfootSound( "pacman_eat_pellet.wav" );
    
    //Sound of clara dying
    private GreenfootSound _claraDie = new GreenfootSound( "pacman_death.wav" );

    /**
     * Clara picks up a leaf <br>
     */
    public void removeLeaf() 
    {
        Leaf leaf = ( Leaf ) getOneObjectAtOffset( 0, 0, Leaf.class );
        
        if ( leaf != null ) 
        {
            getWorld().removeObject( leaf );
        } 
        else 
        {
            showWarning( "There is no leaf that Clara could remove here!" );
        }
    }

    /**
     * Clara checks if he stands on a leaf <br>
     * 
     * @return true if Clara stands on a leaf, false otherwise
     */
    public boolean onLeaf() 
    {
        return getOneObjectAtOffset( 0, 0, Leaf.class ) != null;
    }
    
    /**
     * Clara picks up a mushroom <br>
     */
    public void removeMushroom() 
    {
        Mushroom mushroom = ( Mushroom ) getOneObjectAtOffset( 0, 0, Mushroom.class );
        
        if ( mushroom != null ) 
        {
            getWorld().removeObject( mushroom );
        } 
        else 
        {
            showWarning( "There is no mushroom that Clara could remove here!" );
        }
    }

    /**
     * Clara checks if he stands on a mushroom <br>
     * 
     * @return true if Clara stands on a mushroom, false otherwise
     */
    public boolean onMushroom() 
    {
        return getOneObjectAtOffset( 0, 0, Mushroom.class ) != null;
    }
    
    /**
     * Checks if all the leaves are eaten, returns true if so.
     */
    protected boolean allLeavesEaten()
    {
        return ( getWorld().getObjects( Leaf.class ).size() == 0 );
    }
    
    /**
     * Plays Clara's death sound
     */
    protected void playClaraDieSound()
    {
        _claraDie.play();
    }
    
    /**
     * Checks if Clara death sound is still playing
     * 
     * Returns true if so, else false
     */
    protected boolean isClaraDieSoundStillPlaying()
    {
        return _claraDie.isPlaying();
    }
    
    /**
     * Plays the leaf eaten sound
     */
    protected void playLeafEatenSound()
    {
        _eatLeaf.play();
    }
    
    /**
     * Stops the simulation cycle (the act()-method is finished first) <br>
     */
    protected void stop() {
        Greenfoot.stop();
    }
    
    /**
     * Override animate for Clara's animation
     */
    protected void animate()
    {
        advanceAnimationFrame( _totalAnimationFrames );
        
        setImage( "claraAnim" + Integer.toString( _currentAnimationFrame + 1 ) + ".png" );
    }
    
    /**
     * Override animate dead for Clara
     */
    protected void animateDead()
    {
        setImage("clara_grey.png");
    }
    
    /**
     * Override animate scared to remove from Clara
     */
    protected void animateScared()
    {
    }
    
    /**
     * Advances the stage to the specified game level
     * 
     * gameLevel        sets the game that level, null sets it to level 1
     */
    protected void advanceToLevel( char[][] gameLevel )
    {
        //Gets the Level variable
        Level level = ( Level )getWorld();
        
        //If the given level is null, return to the first level of the game
        if( gameLevel == null )
        {
            level = new Level( level.level1, 1 );
        }
        //Continue to specified level
        else
        {
            level = new Level( gameLevel, level.getLevelNumber() + 1 );
        }
        
        //Set game to new world
        Greenfoot.setWorld( ( World ) level );
    }
    
    /**
     * Returns the number of the current level
     */
    protected int getCurrentLevelNumber()
    {
        Level level = ( Level )getWorld();
        
        return level.getLevelNumber();
    }
}
