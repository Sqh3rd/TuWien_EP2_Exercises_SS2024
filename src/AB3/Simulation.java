package AB3;

import java.awt.*;
import java.util.Random;

import AB3.Collections.List.Standard.UnsortedList;
import codedraw.CodeDraw;

/**
 * Simulates a cluster of bodies.
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
    public static final int NUMBER_OF_BODIES = 50;
    public static final double OVERALL_SYSTEM_MASS = 30 * SUN_MASS; // kilograms

    // all quantities are based on units of kilogram respectively second and meter.

    public static void main(String[] args)
    {

        // simulation
        CodeDraw cd = new CodeDraw();
        BodyQueue bodies = new BodyQueue();
        BodyAccelerationTreeMap accelerationOfBody = new BodyAccelerationTreeMap();

        Random random = new Random(2024);

        for (int i = 0; i < NUMBER_OF_BODIES; i++)
        {
            Vector3 massCenter = new Vector3(
                0.2 * random.nextGaussian() * AU,
                0.2 * random.nextGaussian() * AU,
                0.2 * random.nextGaussian() * AU);
            Vector3 currentMovement = new Vector3(
                random.nextGaussian() * 5e3,
                random.nextGaussian() * 5e3,
                random.nextGaussian() * 5e3);
            bodies.add(new Body(
                Math.abs(random.nextGaussian()) * OVERALL_SYSTEM_MASS / NUMBER_OF_BODIES,
                massCenter,
                currentMovement));
        }

        double seconds = 0;

        // simulation loop
        while (true)
        {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            UnsortedList<Body> bodyList = UnsortedList.<Body>builder()
                .withInitialArray(new Body[NUMBER_OF_BODIES])
                .build();
            while (bodies.size() > 0) {
                bodyList.push(bodies.poll());
            }
            // merge bodies that have collided
            for (int i = 0; i < bodyList.count(); i++)
            {
                for (int j = i + 1; j < bodyList.count(); j++)
                {
                    if (bodyList.get(j).distanceTo(bodyList.get(i)) >=
                        bodyList.get(j).getRadius() + bodyList.get(i).getRadius())
                        continue;

                    bodyList.replace(bodyList.get(i).merge(bodyList.get(j)), i);
                    bodyList.remove(j);

                    i = -1;
                    break;
                }
            }

            for (int i = 0; i < bodyList.count(); i++)
            {
                bodies.add(bodyList.get(i));
            }

            BodyQueue bodiesCopy = new BodyQueue(bodies);

            // for each body (with index i): compute its total acceleration.
            for (int i = 0; i < bodies.size(); i++)
            {
                Body currentBody = bodies.poll();
                accelerationOfBody.put(currentBody, Vector3.ZERO);
                for (int j = 0; j < bodiesCopy.size(); j++)
                {
                    Body otherBody = bodiesCopy.poll();
                    if (otherBody != currentBody)
                    {
                        Vector3 toAdd = currentBody.acceleration(otherBody);
                        accelerationOfBody.put(currentBody, accelerationOfBody.get(currentBody).plus(toAdd));
                    }
                    bodiesCopy.add(otherBody);
                }
                bodies.add(currentBody);
            }
            // now accelerationOfBody[i] holds the total acceleration vector for bodies[i].

            // for each body (with index i): accelerate it for one second.
            for (int i = 0; i < bodies.size(); i++)
            {
                Body body = bodies.poll();
                body.accelerate(accelerationOfBody.get(body));
                bodies.add(body);
            }

            // show all movements in the canvas only every hour (to speed up the simulation)
            if (seconds % (3600) == 0)
            {
                System.out.println("Drawing Frame");
                // clear old positions (exclude the following line if you want to draw orbits).
                cd.clear(Color.BLACK);

                // draw new positions
                for (int i = 0; i < bodies.size(); i++)
                {
                    Body body = bodies.poll();
                    body.draw(cd);
                    bodies.add(body);
                }

                // show new positions
                cd.show();
            }
        }
    }
}
