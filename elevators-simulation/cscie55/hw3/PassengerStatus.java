/**
 * $Id: PassengerStatus.java, v 1.0 15/10/13 21:44 oscarfabra Exp $
 * {@code PassengerStatus} is an enumeration to distinguish the status of each
 * passenger in the Elevator application.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @see <a href="Elevator.html">Elevator.java</a>
 * @see <a href="Floor.html">Floor.java</a>
 * @see <a href="Passenger.html">Passenger.java</a>
 * @since October 15, 2013
 */

package cscie55.hw3;

/**
 * Enumeration that indicates the status of each passenger in the Elevator
 * application.
 */
public enum PassengerStatus
{
    /**
     * The passenger is residing on a particular floor, with no intentions
     * of going up or down.
     */
    RESIDING,

    /**
     * The passenger is waiting for the elevator to board it.
     */
    WAITING,

    /**
     * The passenger is inside the Elevator heading toward his or her
     * particular destination.
     */
    ON_BOARD
}
