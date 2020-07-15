import greenfoot.*;  // (World, Clara, GreenfootImage, and Greenfoot)
/**
 * Ghost Class
 * 
 * Available functions (see Assignment document for explanations on what each function does):
 * treeFront, treeAbove, treeBelow, treeToLeft, treeToRight,
 * getDirection, setDirection,
 * move,
 * isScared,
 * animate, animateDead, animateScared,
 * getClara, getGhostHealer,
 * isAboveMe, isBelowMe, isToMyLeft, isToMyRight,
 * makeClaraDead,
 * playGhostEatenSound,
 * isPacmanIntroStillPlaying,
 * wrapAroundWorld
 */
public class Ghost extends Character
{
    //Add and initialise Ghost variables here
    int startgame = 0;
    int moving = 0;
    int animation = 0; 
    int level = 1;
    int pacman = 0;
    int direction = 4;
    int intersection = 2; 
    int endgame = 0;
    int animatescared = 0;
    int animatedead = 0;
    int dead = 0;

    /**
     * Act method, runs on every frame
     */
    public void act()
    {
        //Make the Ghost do things here

        //Tells the ghosts to stay in the world
        wrapAroundWorld();    

        //Tells the ghosts not to move when pacman intro sound is playing
        if (isPacmanIntroStillPlaying())
        {
            startgame = 0;
        }

        //Tells the ghosts to only move when a key is pressed
        if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("up") || 
        Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right") && endgame == 0)
        {
            startgame = 1;            
        }

        if (startgame == 1 && !treeFront() && endgame == 0)
        {
            moving++;
        }

        if (moving > 0 && startgame == 1 && endgame == 0)
        {
            move(2);
            animation++;
            if (isScared() == true)
            {
                animatescared++;
            }
            if (dead == 1)
            {
                animatedead++;
            }
        }

        if (intersects(getGhostHealer()))
        {
            direction = 0;
        }

        //Animates the ghosts
        if (animation > 0 && isScared() == false && dead == 0)
        {
            animateGhosts();
        }

        //Tells the ghosts what to do when they run into a tree in normal mode
        if (treeFront() && startgame == 1 && endgame == 0 && isScared() == false)
        {
            moving = 0;
            //Tells the ghosts to move towards Clara
            if (isAboveMe(getClara()) == true && !treeAbove())
            {
                direction = 0;
            }

            else if (isBelowMe(getClara()) == true && !treeBelow())
            {
                direction = 1;
            }

            else if (isToMyLeft(getClara()) == true && !treeToLeft())
            {
                direction = 2;
            }

            else if (isToMyRight(getClara()) == true && !treeToRight())
            {
                direction = 3;
            } 
            else
            {
                direction = Greenfoot.getRandomNumber(4);
            }
        }

        //Tells the ghosts what to do when they run into a tree in scared mode
        if (treeFront() && startgame == 1 && endgame == 0 && isScared() == true)
        {
            moving = 0;
            //Tells the ghosts to move away from Clara
            if (isBelowMe(getClara()) == true && !treeAbove())
            {
                direction = 0;
            }

            else if (isAboveMe(getClara()) == true && !treeBelow())
            {
                direction = 1;
            }

            else if (isToMyRight(getClara()) == true && !treeToLeft())
            {
                direction = 2;
            }

            else if (isToMyLeft(getClara()) == true && !treeToRight())
            {
                direction = 3;
            } 
            else
            {
                direction = Greenfoot.getRandomNumber(4);
            }
        }

        //Tells the ghosts what to do when they run into a tree in dead mode
        if (treeFront() && startgame == 1 && endgame == 0 && dead == 1)
        {
            moving = 0;
            //Tells the ghosts to move towards the ghost healer
            if (isAboveMe(getGhostHealer()) == true && !treeAbove())
            {
                direction = 0;
            }

            else if (isBelowMe(getGhostHealer()) == true && !treeBelow())
            {
                direction = 1;
            }

            else if (isToMyLeft(getGhostHealer()) == true && !treeToLeft())
            {
                direction = 2;
            }

            else if (isToMyRight(getGhostHealer()) == true && !treeToRight())
            {
                direction = 3;
            } 
            else
            {
                direction = Greenfoot.getRandomNumber(4);
            }
        }

        //Tells the ghosts what to do at an intersection in normal mode
        if (!treeAbove() && getDirection() != "down" && isAboveMe(getClara()) == true && 
        isScared() == false)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 0;              
            }
        }        

        if (!treeBelow() && getDirection() != "up" && isBelowMe(getClara()) == true && 
        isScared() == false)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 1;
            }                       
        }

        if (!treeToLeft() && getDirection() != "right" && isToMyLeft(getClara()) == true && 
        isScared() == false)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 2;
            }
        }

        if (!treeToRight() && getDirection() != "left" && isToMyRight(getClara()) == true && 
        isScared() == false)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 3;
            }
        }       

        //Tells the ghosts what to do at an intersection in scared mode
        if (!treeAbove() && getDirection() != "down" && isBelowMe(getClara()) == true && 
        isScared() == true)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 0;              
            }
        }        

        if (!treeBelow() && getDirection() != "up" && isAboveMe(getClara()) == true && 
        isScared() == true)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 1;
            }                       
        }

        if (!treeToLeft() && getDirection() != "right" && isToMyRight(getClara()) == true && 
        isScared() == true)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 2;
            }
        }

        if (!treeToRight() && getDirection() != "left" && isToMyLeft(getClara()) == true && 
        isScared() == true)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 3;
            }
        }

        //Tells the ghosts what to do at an intersection in dead mode
        if (!treeAbove() && getDirection() != "down" && isAboveMe(getGhostHealer()) == true && 
        dead == 1)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 0;              
            }
        }        

        if (!treeBelow() && getDirection() != "up" && isBelowMe(getGhostHealer()) == true && 
        dead == 1)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 1;
            }                       
        }

        if (!treeToLeft() && getDirection() != "right" && isToMyLeft(getGhostHealer()) == true && 
        dead == 1)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 2;
            }
        }

        if (!treeToRight() && getDirection() != "left" && isToMyRight(getGhostHealer()) == true && 
        dead == 1)
        {
            moving = 0;
            intersection = Greenfoot.getRandomNumber(2);
            if (intersection == 1)
            {
                direction = 3;
            }
        }

        //Tells the ghosts how to change direction
        //Up
        if (!treeAbove() && direction == 0 && startgame == 1)
        {
            setDirection("up");
        }

        //Down
        if (!treeBelow() && direction == 1 && startgame == 1)
        {
            setDirection("down");
        }

        //Left
        if (!treeToLeft() && direction == 2 && startgame == 1)
        {
            setDirection("left"); 
        }

        //Right
        if (!treeToRight() && direction == 3 && startgame == 1)
        {
            setDirection("right"); 
        }

        //Tells the ghosts to kill Clara if they intersect with her
        if (intersects(getClara()) == true && isScared() == false && dead == 0)
        {
            makeClaraDead();            
            moving = 0;
            endgame = 1;
        }

        if (endgame == 1)
        {
            startgame = 1;
        }

        //Tells the ghosts what to do when they are scared of Clara
        if (isScared() == true && dead != 1)
        {
            if (animatescared > 0)
            {
                scaredAnimation();
            } 
        }

        //Kills the ghosts if they intersect with Clara in scared mode
        if (intersects(getClara()) == true && isScared() == true)
        {
            playGhostEatenSound();
            dead = 1;
        }

        //Animates the ghosts while they are dead
        if (animatedead > 0 && dead == 1)
        {
            deadAnimation();
        }

        //Resurrects the ghosts when they visit the ghost healer
        if (intersects(getGhostHealer()) == true && dead == 1)
        {
            dead = 0;
        }
    }
    //Give the Ghost functions here

    public void deadAnimation()
    {
        animateDead();
        animatedead--;
        animatedead--;
        animatedead--;   
    }

    public void scaredAnimation()
    {
        animateScared();
        animatescared--;
        animatescared--;
        animatescared--;
    }

    public void animateGhosts()
    {
        animate();
        animation--;
        animation--;
        animation--;
    }
}
