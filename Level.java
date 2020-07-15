import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.awt.Color;
/**
 * Level Class
 * 
 * The Level Class sets up the level of Pac-Man.
 * It uses an array storing the setup of the level,
 * and reads it sets up the world on the arrays information.
 * This level array contains information of every object
 * to be created in world.
 * 
 * Holds GhostHealth instance, and can return it when asked.
 * 
 * Holds the Player instance, and can return it when asked.
 * 
 * Holds the GhostWall instance, and can return it when asked.
 * 
 * Although the world has each cell the size of one pixel,
 * it uses it's own cell size to position the level into cells
 * for placing all the objects perfectly alligned, to each other.
 */
public class Level extends World
{
    // the space between each world object when being placed
    private  static int CELL_SIZE = 20;
    private  static int LEVEL_WIDTH = 28;
    private  static int LEVEL_HEIGHT = 31;
    
    private static int LEVEL_NUMBER = 1;

    //Ghost healer for the level
    private GhostHealer ghostHealer;
    
    //Player for the level
    private MyClara player;
    
    //Ghost wall in level
    private GhostWall ghostWall;
    
    //First level
    public static final char[][] level1 = {
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
        
    /**
     * Creates and sets up the Pac-Man level.
     */
    public Level()
    {
        //Creates the world
        super( ( level1[0].length ) * CELL_SIZE, 
               ( level1.length ) * CELL_SIZE, 
               1, 
               false );
        
        //Sets important values
        LEVEL_WIDTH = level1[0].length;
        LEVEL_HEIGHT = level1.length;
        LEVEL_NUMBER = 1;
        
        //Creates a background image, colours it black
        //and then sets it as the background of the level.
        GreenfootImage background = new GreenfootImage( "field.png" );
        setBackground( background );
        
        //Sets the paint order
        setPaintOrder( Clara.class, Ghost.class, Actor.class );

        //Read the array and generate the level
        generateLevel( level1 );
    }
    
    
    /**
     * Creates and sets up the level for an array
     */
    public Level( char[][] inLevel, 
                  int levelNum )
    {
        //Creates the world
        super( ( inLevel[0].length ) * CELL_SIZE, 
               ( inLevel.length ) * CELL_SIZE, 
               1, 
               false );
        
        LEVEL_HEIGHT = inLevel.length;
        LEVEL_WIDTH = inLevel[0].length;
        LEVEL_NUMBER = levelNum;
        
        //Creates a background image, colours it black
        //and then sets it as the background of the level.
        GreenfootImage background = new GreenfootImage( "field.png" );
        setBackground( background );
        
        //Sets the paint order
        setPaintOrder( Clara.class, Ghost.class, Actor.class );

        //Read the array and generate the level
        generateLevel( inLevel );
        //createInfoBar();
    }
    
    /**
     * Reads through the level array and creates
     * objects to be placed in the world depending on
     * what integers it finds.
     * The objects it creates are also aligned by the levels
     * own cell size.
     */
    private void generateLevel( char[][] level )
    {
       for ( int y = 0; y < level.length ; y ++ )
        {
            for ( int x = 0; x < level[0].length; x ++ )
            {
                addObject( level[y][x], x, y );
            }
        }
        
    }

    
    /**
     * Adds an object specified by a number
     * at position (x,y)
     */
    private void addObject( char objectType, int startX, int startY )
    {  
        int x = startX * CELL_SIZE + CELL_SIZE/2;
        int y = startY * CELL_SIZE + CELL_SIZE/2;

        //Wall
        if ( objectType == '#' ) 
        {
            addObject( new Tree(), x, y );
        }

        //Food
        else if ( objectType == '.' ) 
        {
            addObject( new Leaf(), x, y );
        }

        //Mushroom
        else if ( objectType == '$' ) 
        {
            addObject( new Mushroom(), x, y );
        }

        //Player (PacMan)
        else if ( objectType == '@' ) 
        {
            //Appears half a CELL_SIZE to the right,
            //so it is between two cells
            player = new MyClara();
            addObject ( player, x, y );
        }

        //Ghost
        else if ( objectType == '%' ) 
        {
            //Appears half a CELL_SIZE to the right,
            //so it is between two cells
            addObject ( new Ghost(), x, y );
        }

        //PacMan Wall
        else if ( objectType == '|' ) 
        {
            addObject( new GhostWall(), x, y );
        }

        //Ghost Healer
        else if ( objectType == '?' ) 
        {
            ghostHealer = new GhostHealer();
            addObject( ghostHealer, x, y );
        }
    }
    
    /**
     * Returns the ghost healer instance
     */
    public GhostHealer retrieveGhostHealer()
    {
        return ghostHealer;
    }
    
    /**
     * Returns the player character instance
     */
    public MyClara retrievePlayer()
    {
        return player;
    }
    
    /**
     * Returns the ghost wall instance
     */
    public GhostWall retrieveGhostWall()
    {
        return ghostWall;
    }
    
    /**
     * Gets the current level number
     */
    public int getLevelNumber()
    {
        return LEVEL_NUMBER;
    }
}