import greenfoot.*;

/**
 * A rocket that can be controlled by the arrowkeys: up, left, right.
 * The gun is fired by hitting the 'space' key. 'z' releases a proton wave.
 * 
 * @author Poul Henriksen
 * @author Michael Kölling
 * 
 * @version 1.1
 */
public class Rocket extends SmoothMover
{
    private static final int gunReloadTime = 5;         // The minimum delay between firing the gun.
    private static final int warpReloadTime = 60;

    private int reloadDelayCount;               // How long ago we fired the gun the last time.
    private int warpDelayCount;
    
    private GreenfootImage rocket = new GreenfootImage("rocket.png");    
    private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");

    /**
     * Initialise this rocket.
     */
    public Rocket()
    {
        reloadDelayCount = 5;
        warpDelayCount = 60;
        addToVelocity(new Vector(180, .1));
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void act()
    {
        checkKeys();
        reloadDelayCount++;
        warpDelayCount++;
        move();
        checkCollision();
    }
    
    /**
     * Check whether there are any key pressed and react to them.
     */
    private void checkKeys() 
    {
        if (Greenfoot.isKeyDown("space")) 
        {
            fire();
        }
        if (Greenfoot.isKeyDown("left"))
        {
            turn(-5);
        }
        if (Greenfoot.isKeyDown("right"))
        {
            turn(5);
        }
        if (Greenfoot.isKeyDown("shift") && warpDelayCount >= warpReloadTime)
        {
            Marker marker = new Marker();
            getWorld().addObject(marker, getX(), getY());
            
            int oldX = getX();
            int oldY = getY();
            int oldRotation = getRotation();
            int newX = Greenfoot.getRandomNumber(getWorld().getWidth());
            int newY = Greenfoot.getRandomNumber(getWorld().getHeight());
            int newRotation = Greenfoot.getRandomNumber(360);
            
            setLocation(newX, newY);
            setRotation(newRotation);
            
            if (!getObjectsInRange(120, Marker.class).isEmpty())
            {
                newX = Greenfoot.getRandomNumber(getWorld().getWidth());
                newY = Greenfoot.getRandomNumber(getWorld().getHeight());
                newRotation = Greenfoot.getRandomNumber(360);
                
                setLocation(newX, newY);
                setRotation(newRotation);
            }
            
            if (!getObjectsInRange(80, Asteroid.class).isEmpty())
            {
                setLocation(oldX, oldY);
                setRotation(oldRotation);
                
                Greenfoot.playSound("failteleport.wav");
            } else {
                Greenfoot.playSound("teleport.wav");
            }
            
            getWorld().removeObject(marker);
            warpDelayCount = 0;
        }
        ignite(Greenfoot.isKeyDown("up"));
    }
    
    /**
     * Fire a bullet if the gun is ready.
     */
    private void fire() 
    {
        if (reloadDelayCount >= gunReloadTime) 
        {
            Bullet bullet = new Bullet (getVelocity(), getRotation());
            getWorld().addObject (bullet, getX(), getY());
            bullet.move ();
            reloadDelayCount = 0;
        }
    }
    
    private void ignite(boolean conditional)
    {
        if (conditional)
        {
            setImage(rocketWithThrust);
            addToVelocity(new Vector(getRotation(), 0.3));
        } else {
            setImage(rocket);
        }
    }
    
    private void checkCollision()
    {
        if (getOneIntersectingObject(Asteroid.class) != null)
        {
            Space space = (Space) getWorld();
            space.addObject(new Explosion(), getX(), getY());
            space.removeObject(this);
            space.gameOver();
        }
    }
    
    public boolean getIsWarpCharged()
    {
        if (warpDelayCount >= warpReloadTime)
        {
            return true;
        } else {
            return false;
        }
    }
}