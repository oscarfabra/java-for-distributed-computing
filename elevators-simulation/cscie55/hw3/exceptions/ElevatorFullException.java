/**
 * $Id: ElevatorFullException.java,v 1.0 2013/10/04 15:13:33 oscarfabra Exp $
 * {@code ElevatorFullException} is an exception to be thrown when the elevator
 * is full at the Elevator class.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @see <a href="Elevator.html">Elevator.java</a>
 * @see <a href="Floor.html">Floor.java</a>
 * @since October 04, 2013
 */

package cscie55.hw3.exceptions;

/**
 * This class represents an exception to be thrown when the Elevator is full
 * and one more passenger tries to be loaded onto.
 */
public class ElevatorFullException extends Exception
{
	//-------------------------------------------------------------------------
	// ATTRIBUTES
	//-------------------------------------------------------------------------

	/**
	 * Indicates the number of people actually loaded onto the elevator
	 * at any given elevator stop on a particular floor.
	 */
	private int actuallyLoaded;

	/**
	 * Indicate the number of people left behind at any given elevator stop
	 * on a particular floor.
	 */
	private int leftBehind;

	//-------------------------------------------------------------------------
	// CONSTRUCTOR
	//-------------------------------------------------------------------------

	/**
	 * Creates the exception calling the constructor of its super class.
	 */
	public ElevatorFullException()
	{
		super();
	}

	//-------------------------------------------------------------------------
	// PUBLIC METHODS
	//-------------------------------------------------------------------------

	/**
	 * Sets the number of people actually loaded, for diagnostic purposes.
	 * @param actuallyLoaded Number of people actually loaded onto the elevator
	 */
	public void setActuallyLoaded(int actuallyLoaded)
	{
		this.actuallyLoaded = actuallyLoaded;
	}

	/**
	 * Sets the number of people who were left behind, for diagnostic purposes.
	 * @param leftBehind Number of people left behind
	 */
	public void setLeftBehind(int leftBehind)
	{
		this.leftBehind = leftBehind;
	}

	/**
	 * Overrides the toString method of Exception to show the appropriate
     * message with diagnostic information.
	 * @return String The message to be shown when printing the
     * ElevatorFullException object.
	 */
	@Override
	public String toString()
	{
		String wordEnding = (this.actuallyLoaded == 1)?"":"s";

		String msg = "ElevatorFullException: Actually loaded " +
                this.actuallyLoaded + " passenger" + wordEnding + ", " +
                this.leftBehind + " left behind.";

		return msg;
	}
}