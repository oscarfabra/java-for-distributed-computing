/**
 * $Id: ATMCommand.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMCommand} Is an abstract class that contains the argument and
 * allows polymorphism with the ATM's commands.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 */

package cscie55.hw6.commands;

import cscie55.hw6.ATM;
import cscie55.hw6.exceptions.ATMException;

/**
 * Abstract class that contains the argument for any of the ATM's commands.
 */
public abstract class ATMCommand
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * The argument that represents the ATM's Command
     */
    private final String argument;

    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMCommand given its argument
     * @param argument String with the argument
     */
    protected ATMCommand(String argument)
    {
        this.argument = argument;
    }

    /**
     * Creates a new ATMCommand with no argument.
     */
    protected ATMCommand()
    {
        this.argument = null;
    }

    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Returns the String object containing the ATMCommand's argument
     * @return The ATMCommand's argument
     */
    protected String getArgument()
    {
        return this.argument;
    }

    /**
     * Performs the command.
     *
     * @param atmImplementation ATMImplementation on which to perform the action.
     * @return String with the result of performing the command.
     * @throws ATMException If an exception occurs.
     */
    public abstract String doCommand(ATM atmImplementation)
            throws ATMException;

}
