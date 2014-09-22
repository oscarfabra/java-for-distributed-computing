/**
 * $Id: Building.java,v 1.0 2013/10/31 15:13:33 oscarfabra Exp $
 * {@code Elevator} is an application that simulates an elevator system for a
 * multi-story building.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @see <a href="Elevator.html">Elevator.java</a>
 * @see <a href="Floor.html">Floor.java</a>
 * @since October 31, 2013
 */

package cscie55.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The building class simulates a multi-story building with multiple elevators.
 * </br>
 * <b>Assumption 1: </b> In all the application and classes a 'second' means
 * one loop or movement of all the elevators in the building (about 7 seconds
 * in reality).</br>
 * <b>Assumption 2:</b> All elevators have the same capacity. </br>
 * <b>Assumption 3:</b> All floors have the same average arrivals per second.
 * </br>
 * <b>Assumption 4:</b> Each elevator takes exactly ONE second to move UP or
 * DOWN, AND to load and unload passengers. </br>
 */
public class Building
{
    //-------------------------------------------------------------------------
    // CONSTANTS
    //-------------------------------------------------------------------------

    /**
     * Defines the number of elevators inside the building.
     */
    public static int NUMBER_OF_ELEVATORS = 3;

    /**
     * Defines the number of floors in the building.
     */
    public static int NUMBER_OF_FLOORS = 7;


    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * List of elevators inside the building.
     */
    private Map<Integer, Elevator> elevators;

    /**
     * Class attribute containing the List of floors inside the building.
     */
    public static List<Floor> floors;

    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Creates a new building generating its elevators and floors.
     */
    public Building()
    {
        // Creates the elevators' HashMap
        this.elevators = new HashMap<Integer, Elevator>(NUMBER_OF_ELEVATORS);

        // Navigates the Elevators' HashMap and assigns a new key/value pair to
        // each slot.
        this.generateElevators();

        // Creates the floors for the simulation
        floors = new ArrayList<Floor>(NUMBER_OF_FLOORS);

        // Populates the building's floors, including each of its resident
        // passengers, passengers waiting for up service, and passengers
        // waiting for down service.
        this.generateFloors();
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Moves all the elevators inside the building the specified number of
     * times printing all their values every time that all elevators have
     * moved.
     * @param times Number of times to move all the elevators.
     * @throws InterruptedException If the main process is interrupted when
     * it is sleeping.
     */
    public void moveElevators(int times) throws InterruptedException
    {
        for (int i = 0; i < times; i++)
        {
            for (Integer j : this.elevators.keySet())
            {
                // Gets the key/value pairs and moves the elevator
                System.out.println("//----- MOVING ELEVATOR " +
                        (j + 1)+", LOOP " + (i+1) + " ----//");
                Elevator elevator = this.elevators.get(j);
                elevator.move();

                // Updates the elevators HashMap with the moved elevator
                this.elevators.put(j, elevator);

                System.out.println();

                // Waits two seconds between each elevator movement, for
                // illustration purposes.
                Thread.sleep(1000);
            }

            // Prints the values of each floor and elevator in the building
            System.out.print(this);

            // Waits two seconds after showing all the building's values, for
            // illustration purposes.
            Thread.sleep(2000);

            // End line to identify each loop
            System.out.println("//------------------------------------------" +
                    "-----------------------" +
                    System.getProperty("line.separator"));

            // Generates arrivals or requests for any of the elevators on each
            // of the floors using their respective poisson variables.
            for (int k = 0; k < NUMBER_OF_FLOORS; k++)
            {
                floors.get(k).generateArrivalsAtThisSecond();
                // Waits one second after generating arrivals on each floor,
                // for illustration purposes.
                Thread.sleep(1000);
            }

            // End line to identify each loop
            System.out.println("//------------------------------------------" +
                    "-----------------------" +
                    System.getProperty("line.separator"));

            // Waits three seconds to move the elevators again
            Thread.sleep(3000);
        }
    }

    /**
     * Generates a string with the pertinent values of the building to show in
     * the standard output.
     * @return String The String with the pertinent values for the simulation.
     */
    public String toString()
    {
        String msg = "";

        String endLine = System.getProperty("line.separator") +
                System.getProperty("line.separator");

        // Iterates through the floors' ArrayList and shows their respective
        // values.
        for (int i=0; i < NUMBER_OF_FLOORS; i++)
        {
            // Adds the key/value pairs to the message
            msg += "//---------- FLOOR " + (i+1) + " VALUES ----------//" +
                    System.getProperty("line.separator");

            //  Calls the toString() method of each floor
            msg += floors.get(i).toString() + endLine;
        }

        // Iterates through the elevators' HashMap and shows their respective
        // values.
        Iterator iterator = this.elevators.entrySet().iterator();

        while(iterator.hasNext())
        {
            // Gets the key/value pairs
            Map.Entry pair = (Map.Entry)iterator.next();

            // Adds the key/value pairs to the message
            msg += "//-------- ELEVATOR " + ((Integer)pair.getKey() + 1) +
                    " VALUES --------//" +
                    System.getProperty("line.separator");

            // Calls the toString() method of each elevator
            msg += pair.getValue().toString() +
                    System.getProperty("line.separator");
        }

        return msg;
    }


    //-------------------------------------------------------------------------
    // PRIVATE HELPER METHODS
    //-------------------------------------------------------------------------

    /**
     * Generates the building's Elevator objects in order to start the
     * simulation. </br>
     * <b>Post:</b> Each elevator has been created with 0 passengers, equal
     * capacity, and all of them are standing on the first floor.
     */
    private void generateElevators()
    {
        for (int i=0; i < NUMBER_OF_ELEVATORS; i++)
        {
            this.elevators.put(i, new Elevator(i));
        }
    }

    /**
     * Generates the building's Floor objects in order to start the simulation.
     * </br>
     * <b>Post:</b> Each of the floors have been created, including their
     * waiting passengers and each of the passengers' destinations.
     */
    private void generateFloors()
    {
        for (int i=0; i < NUMBER_OF_FLOORS; i++)
        {
            // Creates a new floor object for slot i in the floors ArrayList
            floors.add(new Floor(i));
        }
    }

    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    /**
     * Main method to demonstrate the features of the Application.
     */
    public static void main(String [] args)
    {
        System.out.println();
        System.out.println("//----------------------------------------------" +
                "-------------------");
        System.out.println("// APPLICATION'S INITIAL VALUES");
        System.out.println("//----------------------------------------------" +
                "-------------------");
        System.out.println();

        // Shows the number of elevators.
        System.out.println("Number of Elevators: " +
                Building.NUMBER_OF_ELEVATORS);
        System.out.println();

        // Shows the elevators' capacity.
        System.out.println("Elevators' Capacity: " + Elevator.CAPACITY);
        System.out.println();

        // Shows the number of floors in the building.
        System.out.println("Number of Floors: " + Building.NUMBER_OF_FLOORS);
        System.out.println();

        // Shows the initial number of passengers residing on any floor.
        System.out.println("Initial Number of Residents on Any Floor: " +
                Floor.INITIAL_PASSENGERS_RESIDING);
        System.out.println();

        // Shows the average arrivals per second on any floor.
        System.out.println("Average Queue Arrivals Per Sec on Any Floor: " +
                Floor.AVERAGE_ARRIVALS_PER_SECOND);
        System.out.println();

        System.out.println();
        System.out.println("//----------------------------------------------" +
                "-------------------");
        System.out.println("// ELEVATORS AND FLOORS' INITIAL VALUES");
        System.out.println("//----------------------------------------------" +
                "-------------------");
        System.out.println();

        // Generates the building, including its elevators and floors.
        Building myBuilding = new Building();

        // Prints the building
        System.out.println(myBuilding);

        System.out.println();
        System.out.println("//-------------------------------------------------" +
                "----------------");
        System.out.println("// MOVING ELEVATORS FOR 30 SECONDS (30 MOVES EACH)");
        System.out.println("//-------------------------------------------------" +
                "----------------");
        System.out.println();

        try
        {
            // Moves all elevators 30 times each, printing all the pertinent
            // values each time all of them are moved.
            myBuilding.moveElevators(30);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
