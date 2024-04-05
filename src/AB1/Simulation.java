package AB1;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

import codedraw.CodeDraw;

/*
    ADDITIONAL QUESTIONS:
        1) What is Data Encapsulation?
            Data Encapsulation describes the concept of "encapsulating" data and functionality that logically belong
            together by for example having them in the same class (e.g. moving the Vector Math Functions into the
            Vector3 Class)

        2) What is Data Hiding?
            Data Hiding means, quite literally, that data is hidden. This is achieved by making fields private
            (e.g. the instance variables in Vector3 or Body)

        3) What is to the left of a method call?
            On the left is either
                - nothing (when the method is static and directly imported)
                - a class (when the method is static)
                - an object (when the method is not static)

            Object methods can only be called from an object and not from a static context!
 */

/**
 * Simulates the formation of a massive solar system.
 */
public class Simulation
{

    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance between earth and sun.
    public static final double AU = 150e9; // meters

    // some further constants needed in the simulation
    public static final double SUN_MASS = 1.989e30; // kilograms
    public static final double SUN_RADIUS = 696340e3; // meters

    // set some system parameters
    public static final double SECTION_SIZE = 2 * AU; // the size of the square region in space
    public static final int NUMBER_OF_BODIES = 50;
    public static final double OVERALL_SYSTEM_MASS = 30 * SUN_MASS; // kilograms

    // all quantities are based on units of kilogram respectively second and meter.

    /**
     * The main simulation method using instances of other classes.
     *
     * @param args not used.
     */
    public static void main(String[] args)
    {

        //TODO: change implementation of this method according to 'Aufgabenblatt1.md'.

        // simulation
        CodeDraw cd = new CodeDraw();
        Body[] bodies = new Body[NUMBER_OF_BODIES];
        Vector3[] accelerationOfBody = new Vector3[bodies.length];

        Random random = new Random(2024);

        for (int i = 0; i < bodies.length; i++)
        {
            Vector3 massCenter = new Vector3(
                0.2 * random.nextGaussian() * AU,
                0.2 * random.nextGaussian() * AU,
                0.2 * random.nextGaussian() * AU);
            Vector3 currentMovement = new Vector3(
                random.nextGaussian() * 5e3,
                random.nextGaussian() * 5e3,
                random.nextGaussian() * 5e3);
            bodies[i] = new Body(
                Math.abs(random.nextGaussian()) * OVERALL_SYSTEM_MASS / bodies.length,
                massCenter,
                currentMovement);
        }

        double seconds = 0;

        long frameEnd = Instant.now().toEpochMilli();

        // simulation loop
        while (true)
        {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            // merge bodies that have collided
//            for (int i = 0; i < bodies.length; i++)
//            {
//                for (int j = i + 1; j < bodies.length; j++)
//                {
//                    if (bodies[j].distanceTo(bodies[i]) < bodies[j].getRadius() + bodies[i].getRadius())
//                    {
//                        // collision of bodies i and j
//                        bodies[i] = bodies[i].merge(bodies[j]);
//
//                        // generate a duplicate of the array with body j removed.
//                        Body[] bodiesOneRemoved = new Body[bodies.length - 1];
//                        for (int k = 0; k < bodiesOneRemoved.length; k++)
//                        {
//                            bodiesOneRemoved[k] = bodies[k < j ? k : k + 1];
//                        }
//                        bodies = bodiesOneRemoved;
//
//                        // since the body index i changed size there might be new collisions
//                        // at all positions of bodies, so start all over again
//                        i = -1;
//                        j = bodies.length;
//                    }
//                }
//            }

            // for each body (with index i): compute its total acceleration.
            for (int i = 0; i < bodies.length; i++)
            {
                accelerationOfBody[i] = Vector3.ZERO;
                for (int j = 0; j < bodies.length; j++)
                {
                    if (i != j)
                    {
                        Vector3 toAdd = bodies[i].acceleration(bodies[j]);
                        accelerationOfBody[i] = accelerationOfBody[i].plus(toAdd);
                    }
                }
            }
            // now accelerationOfBody[i] holds the total acceleration vector for bodies[i].

            // for each body (with index i): accelerate it for one second.
            for (int i = 0; i < bodies.length; i++)
            {
                bodies[i].accelerate(accelerationOfBody[i]);
            }

            // show all movements in the canvas only every hour (to speed up the simulation)
            if (seconds % (3600) == 0)
            {
                // clear old positions (exclude the following line if you want to draw orbits).
                cd.clear(Color.BLACK);

                // draw new positions
                for (Body body : bodies)
                {
                    body.draw(cd);
                }

                // show new positions
                cd.show();

                long frameEnd2 = Instant.now().toEpochMilli();
                System.out.println(STR."\{ frameEnd2 - frameEnd }ms");
                frameEnd = frameEnd2;
            }

        }
    }
}
