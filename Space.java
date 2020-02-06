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

    /**
     * Create the space and all objects within it.
     */
    public Space()
    {
        super(600, 500, 1);
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fill();

        Rocket rocket = new Rocket();
        addObject(rocket, getWidth()/2 + 100, getHeight()/2);

        addAsteroids(startAsteroids);
        paintStars(300);

        scoreCounter = new Counter("Score: ");
        addObject(scoreCounter, 60, 480);

        Explosion.initializeImages();
        ProtonWave.initializeImages();
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
        // TODO: show the score board here. Currently missing.
    }

}
