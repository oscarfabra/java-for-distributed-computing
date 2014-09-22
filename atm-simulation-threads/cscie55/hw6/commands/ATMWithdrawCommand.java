/**
 * $Id: ATMWithdrawCommand.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMWithdrawCommand} Tells the server to withdraw a given amount from
 * a Client's account.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw6.commands.ATMCommand
 */

package cscie55.hw6.commands;

import cscie55.hw6.ATM;
import cscie55.hw6.exceptions.ATMException;

/**
 * Class that represents the ATM's withdraw command.
 */
public class ATMWithdrawCommand extends ATMCommand
{

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMWithdrawCommand object calling the constructor of its
     * superclass.
     * @param argument The argument to write on the super class.
     */
    public ATMWithdrawCommand(String argument)
    {
        super(argument);
    }

    //-------------------------------------------------------------------------
    // PROTECTED METHODS
    //-------------------------------------------------------------------------

    /**
     * Performs the action of the command.
     *
     * @param atmImplementation ATMImplementation on which to perform the action.
     * @return String to send to the other process.
     * @throws ATMException If there aren't enough funds to do the transaction
     */
    @Override
    public String doCommand(ATM atmImplementation) throws ATMException
    {
        String result = "Success.";
        atmImplementation.withdraw(Float.parseFloat(this.getArgument()));
        return result;
    }

}
