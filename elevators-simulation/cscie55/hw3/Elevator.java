/**
 * $Id: Elevator.java,v 3.0 2013/09/23 15:13:33 oscarfabra Exp $
 * {@code Elevator} is an application that simulates an elevator system for a
 * multi-story building.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 3.0
 * @since September 23, 2013
 */


package cscie55.hw3;

import cscie55.hw3.exceptions.ElevatorFullException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The Elevator class simulates an elevator for a multi-story building.
 */
public class Elevator
{
    //-------------------------------------------------------------------------
    // CONSTANTS
    //-------------------------------------------------------------------------

    /**
     * Defines the number of people that any Elevator can carry.
     */
    public static int CAPACITY = 7;

    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * String attribute that defines the elevator's availability. </br>
     * status E {EMPTY, AVAILABLE, FULL}
     * @see ElevatorStatus
     */
    private ElevatorStatus status;

    /**
     * Number of the elevator in the building. This variable is used to
     * identify which passengers are waiting for which elevator, so the load
     * may be balanced among all elevators.
     */
    private int elevatorNumber;

    /**
     * Current direction on which the elevator is moving. </br>
     * currentDirection E {"UP", "DOWN"}
     */
    private ElevatorDirection currentDirection;

    /**
     * Current floor on which the elevator is standing. </br>
     * 0 <= floor < NUMBER_OF_FLOORS
     */
    private int currentFloor;

    /**
     * Map that contains the passengers destined for each floor. </br>
     * The first parameter indicates the destined floor number.
     * 0 <= floorNumber < Elevator.NUMBER_OF_FLOORS </br>
     * The second parameter contains the actual Passenger object.
     */
    private Map<Integer, Passenger> passengersOnboard;


    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Initializes each instance attribute to its corresponding value.
     */
    public Elevator(int elevatorNumber)
    {
        // At the beginning of the simulation the elevator is empty
        this.status = ElevatorStatus.EMPTY;

        // Indicates the elevator number
        this.elevatorNumber = elevatorNumber;

        // The elevator is standing on the first floor
        this.currentFloor = 0;

        // It is going to move UP
        this.currentDirection = ElevatorDirection.UP;

        // Instantiates the passengersOnboard HashMap
        this.passengersOnboard = new HashMap<Integer, Passenger>(CAPACITY);
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Moves the elevator one floor up or down after determining the right
     * direction to move.
     */
    public void move()
    {
        // Determines the right direction according to various rules
        this.determineDirection();

        // Moves UP or DOWN depending on current direction
        if(this.currentDirection.equals(ElevatorDirection.UP))
        {
            this.currentFloor++;
        }
        else
        {
            this.currentFloor--;
        }

        System.out.println("Current floor: " + (this.currentFloor+1) + "...");

        // Determines whether to stop on this floor or not checking if there is
        // a request on this floor in the same direction on which the elevator
        // is moving.
        if(this.stopOnThisFloor())
        {
            this.stop();
        }
    }

    /**
     * <b>Rule 1:</b> If it is moving upward and it is standing on the last
     * floor (n = N - 1), changes direction to DOWN. </br>
     * <b>Rule 2:</b> If it is moving downward and it is standing on the first
     * flor (n = 0), changes direction to UP. </br>
     * <b>Rule 3:<b/> If it is moving upward but there aren't any more requests
     * from upper floors and there aren't passengers inside, changes direction
     * to DOWN.
     */
    public void determineDirection()
    {
        if(this.currentDirection.equals(ElevatorDirection.UP) &&
                this.currentFloor == (Building.NUMBER_OF_FLOORS - 1) )
        {
            this.currentDirection = ElevatorDirection.DOWN;
        }
        else if(this.currentDirection.equals(ElevatorDirection.DOWN) &&
                this.currentFloor == 0)
        {
            this.currentDirection = ElevatorDirection.UP;
        }
        else if(this.currentDirection.equals(ElevatorDirection.UP) &&
                (this.countRequestsFromUpperFloors(this.currentFloor) == 0) &&
                (this.countPassengersOnboard() == 0))
        {
            this.currentDirection = ElevatorDirection.DOWN;
        }

    }

    /**
     * Gets the current direction on which the elevator is moving.
     * @return ElevatorDirection Current direction of the elevator.
     */
    public ElevatorDirection getCurrentDirection()
    {
        return this.currentDirection;
    }

    /**
     * This method returns a String summarizing the pertinent values in the
     * elevator. <br>
     * @return String The message to be written on the standard output.
     */
    public String toString()
    {
        // Adds an "s" at the end of the word "passenger" in case there is
        // more than 1 passenger
        String wordEnding = (this.countPassengersOnboard() == 1)?"":"s";

        String msg = "Standing on floor: " + (this.currentFloor+1) +
                System.getProperty("line.separator");
        msg += "Passenger" + wordEnding + " on board: " +
                this.countPassengersOnboard() +
                System.getProperty("line.separator");

        // Returns the message
        return msg;
    }

    /**
     * Determines whether to stop on this floor by checking if there is a
     * request on this floor in the same direction on which the elevator
     * is moving or if the elevator is carrying passengers destined to
     * current floor.
     * @return boolean Whether the elevator should stop on this floor.
     */
    public boolean stopOnThisFloor()
    {
        return ((this.countPassengersToLoad() > 0) ||
                (this.countPassengersDestined() > 0));
    }

    /**
     * Counts the number of passengers that should be loaded onto the elevator.
     * @return int Number of passengers to load onto the elevator.
     */
    public int countPassengersToLoad()
    {
        Floor currentFloor = Building.floors.get(this.currentFloor);

        int count = 0;

        if(this.currentDirection.equals(ElevatorDirection.UP))
        {
            count += currentFloor.
                    passengersWaitingForElevatorUp(this.elevatorNumber);
        }
        else
        {
            count += currentFloor.
                    passengersWaitingForElevatorDown(this.elevatorNumber);
        }

        return count;

    }


    /**
     * Stops the specified elevator, unloads passengers destined to current
     * floor and loads waiting passengers who are heading towards the same
     * direction.
     */
    public void stop()
    {
        // Indicates that it is going to stop on current floor
        System.out.println("Stopping on floor " +
                (this.currentFloor+1) + "...");

        // Gets the current floor object from the Building's floors array
        Floor currentFloorObject = Building.floors.get(this.currentFloor);

        // Unloads the elevator's passengers who are destined for the floor,
        // then loads its waiting passengers who are heading towards the same
        // direction onto the elevator.
        currentFloorObject.unloadPassengers(this);
    }

    /**
     * Boards one more passenger on current floor destined for the floor
     * specified in its parameter. </br>
     * If capacity is FULL throws and exception indicating it and doesn't boards
     * the passenger. </br>
     * @param floor The number of the floor to where the passenger is destined.
     * @throws ElevatorFullException Exception to be thrown if it tries to load
     * one more passenger into the Elevator but it is full.
     */
    public void boardPassenger(int floor) throws ElevatorFullException
    {
        // Checks if the elevator can handle one more passenger
        if(this.status.equals(ElevatorStatus.AVAILABLE) ||
                this.status.equals(ElevatorStatus.EMPTY))
        {
            Floor currentFloor = Building.floors.get(this.currentFloor);
            Passenger passenger = new Passenger(floor);

            // Guarantees that it is going to load a passenger heading
            // toward the same direction as the elevator and that it is
            // waiting for this elevator.
            if(this.currentDirection.equals(ElevatorDirection.UP))
            {
                passenger = currentFloor.
                        removePassengerDestinedUp(this.elevatorNumber);
            }
            else if(this.currentDirection.equals(ElevatorDirection.DOWN))
            {
                passenger = currentFloor.
                        removePassengerDestinedDown(this.elevatorNumber);
            }

            // Checks if a passenger was actually removed from the up or down
            // waiting queues, so it needs to be loaded onto the elevator.
            if(passenger.getDestinationFloor()!= -1)
            {
                // Updates the passenger's status
                passenger.setStatus(PassengerStatus.ON_BOARD);

                // Puts the passenger in the passengersOnboard HashMap
                this.passengersOnboard.put(this.passengersOnboard.size(),
                        passenger);

                // Updates the elevator's status according to whether it is FULL or
                // AVAILABLE
                this.updateElevatorStatus();
            }
        }
        else // If capacity is full throws an exception
        {
            throw new ElevatorFullException();
        }
    }

    /**
     * Gets the value of the currentFloor attribute
     * @return The number of the current floor
     */
    public int getCurrentFloor()
    {
        return this.currentFloor;
    }

    /**
     * Sets the current direction of the elevator to the value specified in
     * its parameter.
     * @param direction Direction to assign to the elevator.
     */
    public void setCurrentDirection(ElevatorDirection direction)
    {
        this.currentDirection = direction;
    }

    /**
     * Returns the size of the passengersOnboard HashMap.
     * @return int The number of passengers on board.
     */
    public int countPassengersOnboard()
    {
        return this.passengersOnboard.size();
    }

    /**
     * Counts the number of passengers on board destined to the specified
     * floor.
     * @return int Number of passengers destined to the specified floor.
     */
    public int countPassengersDestined()
    {
        int count = 0;

        // Iterates over the HashMap and counts how many passengers are
        // destined to the specified floor
        for(Passenger passenger : this.passengersOnboard.values())
        {
            count += (passenger.getDestinationFloor() == this.currentFloor)?
                    1:0;
        }
        return count;
    }

    /**
     * Gets a list with the passengers destined to the specified floor and
     * removes them from the passengersOnboard HashMap.
     * @return A List with the passengers destined to the specified floor.
     */
    public List<Passenger> unloadPassengersDestined()
    {
        List<Passenger> passengersDestined = new ArrayList<Passenger>();

        Map<Integer, Passenger> passengersOnBoard =
                new HashMap<Integer, Passenger>();

        int i = 0;

        // Adds passengers destined to the specified floor to the List that is
        // going to be returned and puts the other ones into the new HashMap.
        for (Passenger passenger : this.passengersOnboard.values())
        {
            if (passenger.getDestinationFloor() == this.currentFloor)
            {
                passengersDestined.add(passenger);
            }
            else
            {
                passengersOnBoard.put(i,passenger);
            }
            i++;
        }

        // Points the passengersOnboard attribute to the created HashMap
        // (this means that it just removed the passengers destined to the
        // given floor)
        this.passengersOnboard = passengersOnBoard;

        return passengersDestined;
    }


    //-------------------------------------------------------------------------
    // PRIVATE HELPER METHODS
    //-------------------------------------------------------------------------

    /**
     * Returns the number of people from upper floors who are heading upwards.
     * @param floor Current floor number, 0 <= floor < NUMBER_OF_FLOORS.
     * @return int Number of people from upper floors who are waiting for an
     * elevator for UP service.
     */
    private int countRequestsFromUpperFloors(int floor)
    {
        int count = 0;

        // Iterates over each floor above current floor and adds up all
        // passengers who are heading UP (because otherwise the elevator
        // won't stop).
        for (int i = floor+1; i < Building.NUMBER_OF_FLOORS; i++)
        {
            Floor currentFloor = Building.floors.get(i);
            count+= currentFloor.countPassengersDestinedUp() +
                    currentFloor.countPassengersDestinedDown();
        }

        return count;
    }

    /**
     * Sets the elevator status to EMPTY, FULL or AVAILABLE according to the
     * number of passengers on board.
     */
    private void updateElevatorStatus()
    {
        int passengersOnboard = this.countPassengersOnboard();

        if(passengersOnboard == 0)
        {
            this.status = ElevatorStatus.EMPTY;
        }
        else if(passengersOnboard == CAPACITY)
        {
            this.status = ElevatorStatus.FULL;
        }
        else
        {
            this.status = ElevatorStatus.AVAILABLE;
        }
    }
}