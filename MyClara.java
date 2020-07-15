import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.Iterator;
import java.util.List;

/**
 * MyClara
 * 
 * Available functions (see Assignment document for explanations on what each function does):
 * treeFront, ghostWallFront,
 * getDirection, setDirection,
 * move,
 * makeScared, isScared,
 * animate, animateDead, 
 * onLeaf, removeLeaf, 
 * onMushroom, removeMushroom,
 * allLeavesEaten, 
 * isClaraDead,
 * playClaraDieSound, isClaraDieSoundStillPlaying,
 * playLeafEatenSound,
 * playPacmanIntro, isPacmanIntroStillPlaying,
 * wrapAroundWorld,
 * getCurrentLevelNumber, advanceToLevel
 */
public class MyClara extends Clara
{
    // Please leave this first level here,
    // until after you've completed "Part 12 -
    // Making and Adding Levels"
    public final char[][] LEVEL_1 = {
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
            {'#','.','.','.','.','.','.','.','.','#','.','.','.','.','.','.','.','.','#'},
            {'#','$','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','$','#'},
            {'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
            {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
            {'#','.','#','#','.','#','.','#','#','#','#','#','.','#','.','#','#','.','#'},
            {'#','.','.','.','.','#','.','.','.','#','.','.','.','#','.','.','.','.','#'},
            {'#','#','#','#','.','#','#','#',' ','#',' ','#','#','#','.','#','#','#','#'},
            {' ',' ',' ','#','.','#',' ',' ',' ',' ',' ',' ',' ','#','.','#',' ',' ',' '},
            {'#','#','#','#','.','#',' ','#','#','|','#','#',' ','#','.','#','#','#','#'},
            {' ',' ',' ',' ','.',' ',' ','#','%','?','%','#',' ',' ','.',' ',' ',' ',' '},
            {'#','#','#','#','.','#',' ','#','#','#','#','#',' ','#','.','#','#','#','#'},
            {' ',' ',' ','#','.','#',' ',' ',' ',' ',' ',' ',' ','#','.','#',' ',' ',' '},
            {'#','#','#','#','.','#',' ','#','#','#','#','#',' ','#','.','#','#','#','#'},
            {'#','.','.','.','.','.','.','.','.','#','.','.','.','.','.','.','.','.','#'},
            {'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
            {'#','$','.','#','.','.','.','.','.','@','.','.','.','.','.','#','.','$','#'},
            {'#','#','.','#','.','#','.','#','#','#','#','#','.','#','.','#','.','#','#'},
            {'#','.','.','.','.','#','.','.','.','#','.','.','.','#','.','.','.','.','#'},
            {'#','.','#','#','#','#','#','#','.','#','.','#','#','#','#','#','#','.','#'},
            {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
        };  

    public final char[][] LEVEL_2 = {
            {'#','#','#','#','#','#','#','#','#',' ','#','#','#','#','#','#','#','#','#'},
            {'#','.','.','.','.','.','.','.','#',' ','#','.','.','.','.','.','.','.','#'},
            {'#','$','#','#','.','#','#','.','#',' ','#','.','#','#','.','#','#','$','#'},
            {'#','.','#','#','.','#','#','.','#',' ','#','.','#','#','.','#','#','.','#'},
            {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
            {'#','.','#','#','.','#','.','#','#','#','#','#','.','#','.','#','#','.','#'},
            {'#','.','.','.','.','#','.','.','.','#','.','.','.','#','.','.','.','.','#'},
            {'#','#','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','#','#'},
            {'#','.','.','.','.','#','.','.','.','@','.','.','.','#','.','.','.','.','#'},
            {'#','.','#','#','.','#',' ','#','#','#','#','#',' ','#','.','#','#','.','#'},
            {'#','$','#','#','.',' ',' ',' ',' ',' ',' ',' ',' ',' ','.','#','#','$','#'},
            {'#','.','#','#','.','#',' ','#','#','#','#','#',' ','#','.','#','#','.','#'},
            {'#','.','.','.','.','#','.','.','.','.','.','.','.','#','.','.','.','.','#'},
            {'#','#','#','#','.','#','.','#','#','#','#','#','.','#','.','#','#','#','#'},
            {'#','.','.','.','.','.','.','#','%','?','%','#','.','.','.','.','.','.','#'},
            {'#','.','#','#','.','#','#','#','#','|','#','#','#','#','.','#','#','.','#'},
            {'#','.','.','#','.',' ',' ',' ',' ',' ',' ',' ',' ',' ','.','#','.','.','#'},
            {'#','#','.','#','.','#','#','#','#',' ','#','#','#','#','.','#','.','#','#'},
            {'#','.','.','.','.','.','.','.','.',' ','.','.','.','.','.','.','.','.','#'},
            {'#','$','#','#','#','#','#','.','#',' ','#','.','#','#','#','#','#','$','#'},
            {'#','.','.','.','.','.','.','.','#',' ','#','.','.','.','.','.','.','.','#'},
            {'#','#','#','#','#','#','#','#','#',' ','#','#','#','#','#','#','#','#','#'}

        };

    // Movement constants
    public final String  UP = "up";    
    public final String  DOWN = "down";    
    public final String  LEFT = "left";    
    public final String  RIGHT = "right";        

    // Add and initialise Clara's variables here
    int startgame = 0;
    int animation = 0; 
    int level = 1;
    int pacman = 0;
    int dead = 0;
    int endgame = 0;

    /**
     * Act method
     * 
     * Runs of every frame
     */
    public void act()
    {
        //Make Clara do things here

        //Tells Clara to stay inside the world
        wrapAroundWorld();   

        //Tells Clara to only move when a key is pressed
        if (startgame > 0 && endgame == 0)
        {
            move(3);
            animation++;
        }

        //Tells Clara what to do when the 'up' key is pressed
        if (Greenfoot.isKeyDown(UP) && endgame == 0)
        {
            moveUp();
        }

        //Tells Clara what to do when the 'down' key is pressed
        if (Greenfoot.isKeyDown(DOWN) && endgame == 0)
        {
            moveDown();
        }

        //Tells Clara what to do when the 'left' key is pressed
        if (Greenfoot.isKeyDown(LEFT) && endgame == 0)
        {
            moveLeft();
        }

        //Tells Clara what to do when the 'right' key is pressed
        if (Greenfoot.isKeyDown(RIGHT) && endgame == 0)
        {
            moveRight();
        }

        //Animates Clara
        if (animation > 0)
        {
            animateClara();
        }

        //Tells Clara not to walk through trees or ghost walls
        if (treeFront() || ghostWallFront())
        {
            startgame = 0;
        }

        //Tells Clara how to eat leaves and play a sound
        if (onLeaf())
        {
            eatLeaf();
        }

        //Works out what level Clara is currently on 
        //and asks her to move to the next level when this level is complete
        //and plays pacman intro sound at the beginning of the new level
        level = getCurrentLevelNumber();
        if (allLeavesEaten())
        {
            levelUp();
        }

        //Plays pacman intro sound at the start of the level
        if (pacman == 0)
        {
            playPacmanIntro();
            pacman = 1;
        }

        //Tells Clara not to move when pacman intro sound is playing
        while (isPacmanIntroStillPlaying())
        {
            startgame = 0;
        }

        //Checks if Clara should be dead
        if (isClaraDead() == true && dead == 0)
        {
            claraDead();
        }

        //Tells Clara what to do when she eats a mushroom
        if (onMushroom())
        {
            eatMushroom();
        }
    }        

    //Give Clara functions here

    public void moveUp()
    {
        setDirection(UP);
        if (startgame == 0 && !treeFront() && !ghostWallFront())
        {
            startgame++;           
        }   
    }

    public void moveDown()
    {
        setDirection(DOWN);   
        if (startgame == 0 && !treeFront() && !ghostWallFront())
        {
            startgame++;           
        }  
    }

    public void moveLeft()
    {
        setDirection(LEFT);
        if (startgame == 0 && !treeFront() && !ghostWallFront())
        {
            startgame++;           
        } 
    }

    public void moveRight()
    {
        setDirection(RIGHT);  
        if (startgame == 0 && !treeFront() && !ghostWallFront())
        {
            startgame++;           
        } 
    }

    public void eatMushroom()
    {
        removeMushroom();
        makeScared();
    }

    public void eatLeaf()
    {
        removeLeaf();
        playLeafEatenSound();
    }

    public void animateClara()
    {
        animate();
        animation--;
        animation--;
        animation--;
    }

    public void levelUp()
    {
        if (level == 1)
        {
            advanceToLevel(LEVEL_2);
            pacman = 0;
        }
        else if (level == 2)
        {
            advanceToLevel(LEVEL_1);
            pacman = 0;
        }
    }

    public void claraDead()
    {
        animateDead();
        playClaraDieSound();
        dead = 1;
        startgame = 0;
        endgame = 1;
    }
}