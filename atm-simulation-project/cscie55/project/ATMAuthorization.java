/**
 * $Id: ATMAuthorization.java, v 1.0 26/12/13 10:58 oscarfabra Exp $
 * {@code ATMAuthorization} ATMAuthorization is a class whose instances
 * indicate the permitted Commands for a given account's number.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

import cscie55.project.commands.Commands;

import java.util.List;
import java.util.Vector;

/**
 * ATMAuthorization is a class whose instances indicate each of the permitted
 * Commands for a given account's number.
 */
public class ATMAuthorization
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Account's number.
     */
    private int accountNumber;

    /**
     * Collection with the permitted Commands for the given account's number.
     */
    private List<Commands> permittedCommands;

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMAuthorization object given the account's number and the
     * commands it will be permitted to execute.
     *
     * @param accountNumber Account's number.
     * @param commands Array of Commands containing the permitted commands.
     */
    public ATMAuthorization(int accountNumber, Object... commands)
    {
        this.accountNumber = accountNumber;

        // Initializes the permittedCommands collection and adds the given
        // commands into it.
        this.permittedCommands = new Vector<Commands>();
        for(Object command: commands)
        {
            this.permittedCommands.add((Commands)command);
        }
    }

    /**
     * Returns the account number for this ATMAuthorization object.
     * @return Account's number.
     */
    public int getAccountNumber()
    {
        return this.accountNumber;
    }

    /**
     * Determines if the given command is permitted to be executed by the
     * account represented by this ATMAuthorization object.
     *
     * @param command Command to verify if it is authorized.
     * @return Whether it corresponds or not.
     */
    public boolean isPermitted(Commands command)
    {
        boolean isPermitted = false;
        for(Commands permittedCommand: this.permittedCommands)
        {
            if(command.equals(permittedCommand))
            {
                isPermitted = true;
                break;
            }
        }
        return isPermitted;
    }

}
