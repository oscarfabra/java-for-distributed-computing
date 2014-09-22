/**
 * $Id: Passenger.java,v 1.0 2013/10/15 15:13:33 oscarfabra Exp $
 * {@code Passenger} represents a passenger in the Elevator application.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @see <a href="Elevator.html">Elevator.java</a>
 * @see <a href="Floor.html">Floor.java</a>
 * @since October 15, 2013
 */

package cscie55.hw3;

import java.util.Calendar;

/**
 * This class represents a passenger located either inside the elevator or at
 * any floor inside the multi-story building of the Elevator application.
 */
public class Passenger implements Comparable
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Enumeration type to determine the passenger's status at any given
     * moment. </br>
     * status E {RESIDING, WAITING, ON_BOARD, DISPATCHED}
     * @see PassengerStatus
     */
    private PassengerStatus status;

    /**
     * Current floor where the passenger is standing. </br>
     * 0 <= currentFloor < Elevator.NUMBER_OF_FLOORS
     */
    private int currentFloor;

    /**
     * Destination of the passenger. </br>
     * 0 <= destinationFloor < Elevator.NUMBER_OF_FLOORS
     */
    private int destinationFloor;

    /**
     * Indicates the time on which the passenger arrives at the elevators
     * whether for UP or DOWN service, for comparison purposes. Passengers will
     * be loaded onto the elevators in the same order on which they arrive.
     */
    private Calendar queueArrivalTime;

    /**
     * Indicates the elevator that the passenger decides to use. This is done
     * to balance the load among the elevators.
     * 0 <= elevatorToUse < Building.NUMBER_OF_ELEVATORS
     */
    private int elevatorToUse;

    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Creates a Passenger residing on current floor, not queued for boarding
     * any elevator. </br>
     * @param currentFloor Floor where the passenger is standing,
     *                     0 <= currentFloor < Elevator.NUMBER_OF_FLOORS.
     */
    public Passenger(int currentFloor)
    {
        this.status = PassengerStatus.RESIDING;
        this.currentFloor = currentFloor;
        this.destinationFloor = -1;
        this.queueArrivalTime = null;
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Gets the passenger's status.
     * @return PassengerStatus The current passenger's status.
     */
    public PassengerStatus getStatus()
    {
        return this.status;
    }

    /**
     * Modifies the passenger's status.
     * @param status The status to establish. status E {RESIDING, WAITING,
     *               ON_BOARD, DISPATCHED}
     */
    public void setStatus(PassengerStatus status)
    {
        this.status = status;
    }

    /**
     * Returns the destination floor of the passenger object.
     * @return int The destination floor of the passenger.
     */
    public int getDestinationFloor()
    {
        return this.destinationFloor;
    }

    /**
     * Returns the elevator that the passenger is waiting to use.
     * @return Number of the elevator.
     * 0 <= elevatorToUse < Building.NUMBER_OF_ELEVATORS
     */
    public int getElevatorToUse()
    {
        return this.elevatorToUse;
    }

    /**
     * Used when the passenger decides to use the elevators (either for up or
     * down service), and receives a destination floor.
     * @param destinationFloor Floor where the passenger is heading.
     */
    public void queue(int destinationFloor, int elevatorToUse)
    {
        this.status = PassengerStatus.WAITING;
        this.destinationFloor = destinationFloor;
        this.queueArrivalTime = Calendar.getInstance();
        this.elevatorToUse = elevatorToUse;
    }

    /**
     * Updates the passenger's status as it has arrived at its destination.
     */
    public void arrive()
    {
        // He reached the destination floor
        this.currentFloor = this.destinationFloor;

        // No destination floor on mind for now
        this.destinationFloor = -1;

        // It is now residing on the previous destination floor
        this.status = PassengerStatus.RESIDING;
    }

    /**
     * Compares this passenger with the specified object to order them by their
     * respective queue arrival times. Returns a negative integer, zero, or a
     * positive integer as this object is less than, equal to, or greater
     * than the specified object.
     * @param object The passenger to compare to this passenger.
     * @return Whether this passenger arrived at the queue before, after or
     * at the time time than the other object.
     * @throws ClassCastException If the given object is not an instance of
     * Passenger.
     */
    @Override
    public int compareTo(Object object) throws ClassCastException
    {
        // Checks if the specified object is an instance of Passenger
        if(!(object instanceof Passenger))
        {
            throw new ClassCastException();
        }
        Passenger passenger = (Passenger)object;
        return this.queueArrivalTime.compareTo(passenger.queueArrivalTime);
    }

}
