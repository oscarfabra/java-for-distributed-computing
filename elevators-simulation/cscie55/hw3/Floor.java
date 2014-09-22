/**
 * $Id: Floor.java,v 1.0 2013/10/01 15:13:33 oscarfabra Exp $
 * {@code Floor} is a class that represents a floor for the Application.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @see <a href="Elevator.html">Elevator.java</a>
 * @since October 01, 2013
 */

package cscie55.hw3;

import cscie55.hw3.exceptions.ElevatorFullException;
import org.apache.commons.math3.distribution.PoissonDistribution;

import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;

/**
 * The Floor class represents a floor in a multi-story building for the
 * Application.
 */
public class Floor
{
    //-------------------------------------------------------------------------
    // CONSTANTS
    //-------------------------------------------------------------------------

    /**
     * Defines the average number of arrivals per second on the elevator.
     */
    public static final int AVERAGE_ARRIVALS_PER_SECOND = 4;

    /**
     * Defines the initial number of passengers residing on the floor.
     */
    public static final int INITIAL_PASSENGERS_RESIDING = 7;

    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * The number of this floor in the building.
     */
    private int floorNumber;

    /**
     * Poisson variable used to simulate the number of passengers residing who
     * decide to use the elevators at any second.
     */
    private PoissonDistribution poisson;

    /**
     * Set of passengers residing on this floor, just hanging out.
     */
    private List<Passenger> passengersResiding;

    /**
     * Number of passengers residing that decide to queue at the elevators
     * for UP service at any given second.
     */
    private int passengersDestinedUpAtThisSecond;

    /**
     * Number of passengers residing that decide to queue at the elevators
     * for DOWN service at any given second.
     */
    private int passengersDestinedDownAtThisSecond;

    /**
     * List of passengers who are waiting on the floor for UP service.
     */
    private Queue<Passenger> passengersDestinedUp;

    /**
     * List of passengers who are waiting on the floor for DOWN service.
     */
    private Queue<Passenger> passengersDestinedDown;


    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates the Floor object with passengers destined to random floors.
     * @param floorNumber The number of the floor in the building.
     */
    public Floor(int floorNumber)
    {
        // Sets the number of this floor
        this.floorNumber = floorNumber;

        // Instantiates and populates passengers residing on this floor
        this.generatePassengersResiding();

        // Instantiates the poisson variable with mean equal to
        // AVERAGE_ARRIVALS_PER_SECOND.
        this.poisson = new PoissonDistribution(AVERAGE_ARRIVALS_PER_SECOND);

        // Instantiates the passengersDestinedUp Collection
        this.passengersDestinedUp = new PriorityQueue<Passenger>();

        // Instantiates the passengersDestinedDown Collection
        this.passengersDestinedDown = new PriorityQueue<Passenger>();

        // Generates random arrivals at the elevators based on the floor's
        // Poisson variable, taking passengers from the PassengersResiding
        // Vector, and sets a destination to each of them, either for up
        // or down service.
        this.generateArrivalsAtThisSecond();
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Defines the passengers residing that decide to leave at this second
     * following these steps:</br>
     *
     * <b>1.</b> Set the number of passengers that decide to leave at this
     * second taking a sample from the floor's poisson variable. </br>
     *
     * <b>2.</b> Calculate the probability that passengers are heading up or
     * down based on the current floor number, and assuming a Uniform
     * Distribution among the floors.</br>
     *
     * P[U = n] = (N - 1 - n) / (N - 1)</br>
     * where,</br>
     * U : The passengers are heading upwards.</br>
     * n : Current floor number, 0 <= n < N.</br>
     * N : Number of floors in the building.</br>
     * Particularly, P[U = 0] = 1.0, and P[U = N-1] = 0.0.</br>
     *
     * AND,</br>
     *
     * P[D = n] = (n) / (N - 1) OR SIMPLY (1 - P[U = n]) </br>
     * where,</br>
     * D : The passengers are heading downwards.</br>
     * n : Current floor number, 0 <= n < N.</br>
     * N : Number of floors in the building.</br>
     * Particularly, P[D = 0] = 0.0, and P[D = N-1] = 1.0.</br>
     */
    public void generateArrivalsAtThisSecond()
    {
        // First, set the number of passengers that decide to leave at this
        // second.
        int numberOfPassengers = this.poisson.sample();

        // Guarantees that this number isn't bigger than the number of
        // passengers residing on the floor.
        numberOfPassengers =
                (numberOfPassengers > this.passengersResiding.size())?
                        this.passengersResiding.size():numberOfPassengers;

        // Second, calculate the probability that passengers are heading up
        // or down assuming a Uniform Distribution among the floors.
        double probabilityForUpService =
                (Building.NUMBER_OF_FLOORS - 1 - this.floorNumber) /
                        (double)(Building.NUMBER_OF_FLOORS - 1);

        this.passengersDestinedUpAtThisSecond =
                (int)(probabilityForUpService * numberOfPassengers);

        this.passengersDestinedDownAtThisSecond =
                numberOfPassengers - this.passengersDestinedUpAtThisSecond;

        // Determines the destination for each of the passengers waiting for
        // UP service and puts them in the passengersDestinedUp Queue
        this.queuePassengersDestinedUp();

        // Determines the destination for each of the passengers waiting for
        // DOWN service and puts them in the passengersDestinedDown Queue
        this.queuePassengersDestinedDown();

        // Prints the values that were created
        System.out.println("//--- GENERATING ARRIVALS, FLOOR " +
                (this.floorNumber + 1) + " ---//");
        System.out.println("Residing passengers that leave: " +
                numberOfPassengers);
        System.out.println("Number of passengers destined up: " +
                this.passengersDestinedUpAtThisSecond);
        System.out.println("Number of passengers destined down: " +
                this.passengersDestinedDownAtThisSecond +
                System.getProperty("line.separator") +
                System.getProperty("line.separator"));
    }

    /**
     * Unload the elevator's passengers who are destined for this floor, then
     * loads its waiting passengers who are heading towards the same direction
     * onto the elevator. Writes messages accordingly.
     * @param elevator The Elevator object to unload and load passengers to
     */
    public void unloadPassengers(Elevator elevator)
    {
        // Adds an "s" at the end of the word "passenger" in case there is more
        // than 1 passenger
        String ending = (elevator.countPassengersOnboard() == 1)?"":"s";

        // Says how many passengers are there on board
        System.out.println("Currently " + elevator.countPassengersOnboard() +
                " passenger" + ending + " on board.");

        // Gets the number of people inside the elevator that are going to be
        // unloaded.
        int passengersUnloading = elevator.countPassengersDestined();

        // Unloads passengers destined for this floor in case there are.
        if (passengersUnloading > 0)
        {
            // Adds an "s" at the end of the string if there is more than 1
            // passenger
            String wordEnding = (passengersUnloading == 1)?"":"s";

            // Prints a message saying how many passengers it is unloading
            System.out.println("Unloading " + passengersUnloading + " passenger" +
                    wordEnding+"...");

            // Takes all passengers on board and adds them to the floor's
            // passengersResiding ArrayList.

            List<Passenger> passengersDestined =
                    elevator.unloadPassengersDestined();

            for (Passenger passenger : passengersDestined)
            {
                // Updates the passenger's status
                passenger.arrive();

                this.passengersResiding.add(passenger);
            }
        }

        // Gets the number of passengers that need to be loaded, including the
        // ones that the elevator won't be able to carry.
        int passengersToLoad = elevator.countPassengersToLoad();

        if(passengersToLoad > 0)
        {
            // Adds an "s" at the end of the string if there is more than 1
            // passenger
            String wordEnding = (passengersToLoad == 1)?"":"s";

            // Prints a message saying how many passengers it is loading
            System.out.println("Loading " + passengersToLoad + " passenger" +
                    wordEnding+"...");

            // Copies the number of passengers that need to be carried, in
            // order to leave the number of passengers that will be left behind.
            int tryingToLoad = passengersToLoad;

            // Boards each of the passengers waiting on current floor
            for (int i = 0; i < tryingToLoad; i++)
            {
                try
                {
                    // Boards a passenger onto the elevator
                    elevator.boardPassenger(this.floorNumber);

                    // One less passenger to load. This number is kept for
                    // diagnostic purposes (to show the appropriate
                    // ElevatorFullException message in case it is thrown).
                    passengersToLoad--;
                }
                catch (ElevatorFullException efe)
                {
                    // Sets the number of people actually loaded, for diagnostic
                    // purposes
                    efe.setActuallyLoaded(tryingToLoad - passengersToLoad);

                    // Sets the number of people who were left behind, for
                    // diagnostic purposes
                    efe.setLeftBehind(passengersToLoad);

                    // Prints the exception's message
                    System.out.println(efe);

                    break;
                }
            }
        }

    }

    /**
     * Returns the number of passengers waiting on this floor for UP service.
     * @return int Number of passengers on this floor waiting for UP service.
     */
    public int countPassengersDestinedUp()
    {
        return this.passengersDestinedUp.size();
    }

    /**
     * Returns the number of passengers waiting on this floor for DOWN service.
     * @return int Number of passengers on this floor waiting for DOWN service.
     */
    public int countPassengersDestinedDown()
    {
        return this.passengersDestinedDown.size();
    }

    /**
     * Determines how many passengers that are queued for up service are going
     * to use the given elevator.
     * @param elevatorNumber The number of the elevator to use,
     *                       0 <= elevatorNumber < Building.NUMBER_OF_ELEVATORS
     * @return int Number of passengers waiting for up service that are going
     * to use the specified elevator.
     */
    public int passengersWaitingForElevatorUp(int elevatorNumber)
    {
        int count = 0;

        for (Passenger passenger : this.passengersDestinedUp)
        {
            count += (passenger.getElevatorToUse() == elevatorNumber) ? 1 : 0;
        }

        return count;
    }

    /**
     * Determines how many passengers that are queued for down service are
     * going to use the given elevator.
     * @param elevatorNumber The number of the elevator to use,
     *                       0 <= elevatorNumber < Building.NUMBER_OF_ELEVATORS
     * @return int Number of passengers waiting for down service that are going
     * to use the specified elevator.
     */
    public int passengersWaitingForElevatorDown(int elevatorNumber)
    {
        int count = 0;

        for (Passenger passenger : this.passengersDestinedDown)
        {
            count += (passenger.getElevatorToUse() == elevatorNumber) ? 1 : 0;
        }

        return count;
    }

    /**
     * Retrieves and removes the right passenger from the
     * passengersDestinedUp queue.
     * @return The passenger that was removed from the list.
     */
    public Passenger removePassengerDestinedUp(int elevatorNumber)
    {
        Passenger passenger = null;
        Queue<Passenger> passengersDestinedUp = new PriorityQueue<Passenger>();

        // Checks that the queue is not empty
        if(!this.passengersDestinedUp.isEmpty())
        {
            // Auxiliary array used to iterate over the passengersDestinedUp
            // queue and to remove the passenger that is going to be loaded
            // onto the elevator number elevatorNumber
            Passenger [] passengers =
                    new Passenger[this.passengersDestinedUp.size()];
            int i = 0;
            for (Object object : this.passengersDestinedUp.toArray())
            {
                passengers[i] = (Passenger)object;
                i++;
            }

            boolean passengerIdentified = false;

            // Iterates over the array copying into the new PriorityQueue all
            // passengers that are going to be left on the queue
            for (i = 0; i < this.passengersDestinedUp.size(); i++)
            {
                // Guarantees that only one passenger wil be removed from the
                // original queue.
                if ((passengers[i].getElevatorToUse() == elevatorNumber) &&
                        !passengerIdentified)
                {
                    passengerIdentified = true;
                    passenger = passengers[i];
                }
                else
                {
                    passengersDestinedUp.add(passengers[i]);
                }
            }
        }

        // Assigns the new queue's reference to the attribute queue
        this.passengersDestinedUp = passengersDestinedUp;

        return passenger;
    }

    /**
     * Retrieves and removes the right passenger from the
     * passengersDestinedDown queue.
     * @return The passenger that was removed from the list.
     */
    public Passenger removePassengerDestinedDown(int elevatorNumber)
    {
        Passenger passenger = null;
        Queue<Passenger> passengersDestinedDown = new PriorityQueue<Passenger>();

        // Checks that the queue is not empty
        if(!this.passengersDestinedDown.isEmpty())
        {
            // Auxiliary array used to iterate over the passengersDestinedDown
            // queue and to remove the passenger that is going to be loaded
            // onto the elevator number elevatorNumber
            Passenger [] passengers =
                    new Passenger[this.passengersDestinedUp.size()];
            int i = 0;
            for (Object object : this.passengersDestinedUp.toArray())
            {
                passengers[i] = (Passenger)object;
                i++;
            }

            boolean passengerIdentified = false;

            // Iterates over the array copying into the new PriorityQueue all
            // passengers that are going to be left on the queue
            for (i = 0; i < this.passengersDestinedDown.size(); i++)
            {
                // Guarantees that only one passenger wil be removed from the
                // original queue.
                if ((passengers[i].getElevatorToUse() == elevatorNumber) &&
                        !passengerIdentified)
                {
                    passengerIdentified = true;
                    passenger = passengers[i];
                }
                else
                {
                    passengersDestinedDown.add(passengers[i]);
                }
            }
        }

        // Assigns the new queue's reference to the attribute queue
        this.passengersDestinedDown = passengersDestinedDown;

        return passenger;
    }


    /**
     * Returns a String summarizing the pertinent values of the floor.
     * @return String The message to be written on the standard output.
     */
    public String toString()
    {
        // Gets the size of each collection
        int passengersResiding = this.passengersResiding.size();
        int passengersDestinedUp = this.passengersDestinedUp.size();
        int passengersDestinedDown = this.passengersDestinedDown.size();

        // Adds an "s" at the end of the word "passenger" in case there is more
        // than 1 passenger destined up or down or residing.
        String wordEndingResiding = (passengersDestinedDown == 1)?
                " residing (no destination): ":
                "s residing (no destination): ";
        String wordEndingUp = (passengersDestinedUp == 1)?
                " waiting for UP service: ":"s waiting for UP service: ";
        String wordEndingDown = (passengersDestinedDown == 1)?
                " waiting for DOWN service: ":"s waiting for DOWN service: ";

        // Stores an iterator for each of the collections to show in standard
        // output (passengersDestinedUp, passengersDestinedDown, and
        // PassengersResiding)
        Iterator<Passenger> iteratorResiding =
                this.passengersResiding.iterator();
        Iterator<Passenger> iteratorUp =
                this.passengersDestinedUp.iterator();
        Iterator<Passenger> iteratorDown =
                this.passengersDestinedDown.iterator();

        String msg = "";

        // Shows the number of passengers residing.
        msg += this.listPassengers(passengersResiding, wordEndingResiding,
                iteratorResiding);

        // Iterates over the passengersDestinedUp queue storing a message with
        // each of the passengers' destinations.
        msg += this.listPassengers(passengersDestinedUp, wordEndingUp,
                iteratorUp);

        // Iterates over the passengersDestinedDown queue showing each of the
        // passengers' destinations.
        msg += this.listPassengers(passengersDestinedDown, wordEndingDown,
                iteratorDown);

        // Returns the message
        return msg;
    }

    //-------------------------------------------------------------------------
    // PRIVATE HELPER METHODS
    //-------------------------------------------------------------------------

    /**
     * Instantiates and populates the passengers residing collection for this
     * floor with exactly INITIAL_PASSENGERS_RESIDING number of passengers.
     */
    private void generatePassengersResiding()
    {
        // Instantiates the passengersResiding Vector
        this.passengersResiding =
                new Vector<Passenger>(INITIAL_PASSENGERS_RESIDING);

        // Populates the passengersResiding Vector with passengers that have
        // no destination on mind yet.
        for (int i = 0; i < INITIAL_PASSENGERS_RESIDING; i++)
        {
            this.passengersResiding.add(new Passenger(this.floorNumber));
        }
    }

    /**
     * Selects passengers residing to go for UP service, sets a random
     * destination floor for each of them, and puts them in the
     * passengersDestinedUp Queue. </br>
     * <b>Pre:</b> this.passengersDestinedUpAtThisSecond has been defined.</br>
     * <b>Post:</b> Passengers from the passengersResiding Vector have been
     * queued for UP service, currentFloor < destinationfloor <= N-1.</br>
     */
    private void queuePassengersDestinedUp()
    {
        //Populates the passengersDestinedUp Queue
        for(int i = 0; i < this.passengersDestinedUpAtThisSecond; i++)
        {
            int destinationFloor = this.floorNumber +
                    (int)(Math.random()*
                            (Building.NUMBER_OF_FLOORS - this.floorNumber));

            // Guarantees that the destination floor is above the current floor.
            // (At this point there's always going to be at least one floor above
            // because otherwise numberOfPassengersDestinedUp would have been 0).
            destinationFloor = (destinationFloor == this.floorNumber)?
                    destinationFloor + 1 : destinationFloor;

            // Takes the first passenger in the passengersResiding Vector
            Passenger passenger = this.passengersResiding.get(0);
            this.passengersResiding.remove(0);

            // Selects a random elevator to be used by the passenger
            int elevatorToUse =
                    (int)(Math.random()* Building.NUMBER_OF_ELEVATORS);

            // Changes the passenger status and sets its destination floor
            passenger.queue(destinationFloor, elevatorToUse);

            // Adds the passenger to the passengersDestinedUp queue
            this.passengersDestinedUp.add(passenger);
        }
    }

    /**
     * Selects passengers residing to go for DOWN service, sets a random
     * destination floor for each of them, and puts them in the
     * passengersDestinedDown Queue. </br>
     * <b>Pre:</b> this.passengersDestinedDownAtThisSecond has been defined.
     * </br>
     * <b>Post:</b> Passengers from the passengersResiding Vector have been
     * queued for DOWN service, 0 <= destinationFloor < currentFloor.</br>
     */
    private void queuePassengersDestinedDown()
    {
        // Populates the passengersDestinedDown Queue
        for (int i = 0; i < this.passengersDestinedDownAtThisSecond; i++)
        {
            int destinationFloor = (int)(Math.random()* this.floorNumber);

            // Guarantees that the destination floor is below the current floor.
            // (At this point there's always going to be at least one floor below
            // because otherwise numberOfPassengerDestinedDown would have been 0).
            destinationFloor = (destinationFloor == this.floorNumber)?
                    destinationFloor - 1 : destinationFloor;

            // Takes the first passenger in the passengersResiding vector
            Passenger passenger = this.passengersResiding.get(0);
            this.passengersResiding.remove(0);

            // Selects a random elevator to be used by the passenger
            int elevatorToUse =
                    (int)(Math.random()* Building.NUMBER_OF_ELEVATORS);

            // Changes the passenger status and sets its destination floor
            passenger.queue(destinationFloor, elevatorToUse);


            // Adds the passenger to the passengersDestinedDown queue
            this.passengersDestinedDown.add(passenger);
        }
    }

    /**
     * Utility method to show the list of passengers waiting for UP service
     * or list of passengers waiting for DOWN service or list of passengers
     * residing on this floor.
     * @param numberOfPassengers Number of passengers to iterate over.
     * @param wordEnding String to show a nice message.
     * @param iterator An iterator of any of the collections to examine.
     * @return String A message string to show in the standard output.
     */
    private String listPassengers(int numberOfPassengers, String wordEnding,
                                  Iterator<Passenger> iterator)
    {
        String msg = "";

        msg += "Passenger" + wordEnding;
        msg += numberOfPassengers + System.getProperty("line.separator");

        // Prints the destination floor of each one if they're queued for
        // up or down service (or if they're not residing).
        if(!wordEnding.contains("residing"))
        {
            int i = 0;
            while(iterator.hasNext())
            {
                Passenger passenger = iterator.next();
                msg += "    Destination of passenger " + (i+1) + ": " +
                        (passenger.getDestinationFloor() + 1) +
                        System.getProperty("line.separator");
                i++;
            }
        }

        return msg;
    }
}