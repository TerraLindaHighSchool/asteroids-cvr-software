import greenfoot.*;

/**
 * Space. Something for rockets to fly in.
 *
 * @author Michael Kölling
 * @version 1.1
 */
public class Space extends World
{
    private Counter scoreCounter;
    private int startAsteroids = 3;
    
    private Rocket rocket;

    /**
     * Create the space and all objects within it.
     */
    public Space()
    {
        super(600, 500, 1);
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fill();

        rocket = new Rocket();
        addObject(rocket, getWidth()/2 + 100, getHeight()/2);

        addAsteroids(startAsteroids);
        paintStars(300);

        scoreCounter = new Counter("Score: ");
        addObject(scoreCounter, 60, 480);

        Explosion.initializeImages();
        ProtonWave.initializeImages();
    }
    
    public void act()
    {
        String warpText = "";
        if (rocket.getIsWarpCharged())
        {
            warpText = "Warp Charged";
        } else {
            warpText = "";
        }
        
        showText(warpText, 180, 480);
    }

    /**
     * Add a given number of asteroids to our world. Asteroids are only added into
     * the left half of the world.
     */
    private void addAsteroids(int count)
    {
        for(int i = 0; i < count; i++)
        {
            int x = Greenfoot.getRandomNumber(getWidth()/2);
            int y = Greenfoot.getRandomNumber(getHeight()/2);
            addObject(new Asteroid(), x, y);
        }
    }

    private void paintStars(int count)
    {
        for (int i = 0; i < count; i++)
        {
          GreenfootImage background = getBackground();

          int x = Greenfoot.getRandomNumber(getWidth());
          int y = Greenfoot.getRandomNumber(getHeight());

          int starRed = Greenfoot.getRandomNumber(56) + 200;
          int starGreen = Greenfoot.getRandomNumber(56) + 200;
          int starBlue = Greenfoot.getRandomNumber(56) + 200;

          Color starColor = new greenfoot.Color(starRed, starGreen, starBlue);

          background.setColor(starColor);

          int starSize = Greenfoot.getRandomNumber(2) + 2;

          background.fillOval(x, y, starSize, starSize);
        }
    }

    /**
     * This method is called when the game is over to display the final score.
     */
    public void gameOver()
    {
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        int currentScore = scoreCounter.getValue();
        
        addObject(new ScoreBoard(currentScore),x ,y);
    }
    
    public void updateScore(int addToScore)
    {
        scoreCounter.add(addToScore);
    }
}
