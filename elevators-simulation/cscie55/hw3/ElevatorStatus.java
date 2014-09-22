/**
 * $Id: ElevatorStatus.java, v 1.0 17/10/13 21:41 oscarfabra Exp $
 * {@code ElevatorStatus} is an enumeration to distinguish the status of an
 * Elevator in the Elevator application.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 17/10/13
 */

package cscie55.hw3;

/**
 * Enumeration that distinguishes the status of each elevator in the Elevator
 * application.
 */
public enum ElevatorStatus
{
    /**
     * The elevator has at least one passenger but it is not full.
     */
    AVAILABLE,

    /**
     * The elevator has no passenger.
     */
    EMPTY,

    /**
     * The elevator has all its capacity covered.
     */
    FULL
}
