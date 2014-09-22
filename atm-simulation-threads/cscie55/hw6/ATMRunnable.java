/**
 * $Id: ATMRunnable.java, v 1.0 13/12/13 12:16 oscarfabra Exp $
 * {@code ATMRunnable} Class responsible for executing client requests and
 * sending the response to the corresponding client.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 13/12/13
 */

package cscie55.hw6;

import cscie55.hw6.commands.ATMCommand;
import cscie55.hw6.exceptions.ATMException;

import java.io.PrintStream;

/**
 * Class responsible for executing client requests and sending the response to
 * the corresponding client.
 */
public class ATMRunnable implements Runnable
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * ATMCommand to execute.
     */
    private ATMCommand atmCommand;

    /**
     * ATMImplementation reference on which to execute the given command.
     */
    private static ATM atmImplementation = null;

    /**
     * Output object onto which it can write the result back to the client.
     */
    private PrintStream printStream;

    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Creates the ATMRunnable object given the ATMCommand that it is going to
     * execute, the ATMImplementation on which to work on, and the printStream
     * onto which it can write the response to the client.
     *
     * @param atmCommand ATMCommand to execute.
     * @param atmImplementation Reference to ATM object on which to work on.
     * @param printStream PrintStream onto which it can write a response.
     */
    public ATMRunnable(ATMCommand atmCommand, ATM atmImplementation,
                       PrintStream printStream)
    {
        this.atmCommand = atmCommand;

        // Guarantees that all ATMRunnable objects will access the same
        // atmImplementation object
        if(ATMRunnable.atmImplementation == null)
        {
            ATMRunnable.atmImplementation = atmImplementation;
        }

        this.printStream = printStream;
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Executes the corresponding ATMCommand or client request and sends the
     * result to the corresponding client.
     */
    @Override
    public void run()
    {
        String result = "";

        try
        {
            // Performs the command using polymorphism
            result = this.atmCommand.doCommand(ATMRunnable.atmImplementation);
        }
        catch(ATMException ae)
        {
            // Prints the message given to the exception on the account
            System.out.println(ae.toString());
        }

        System.out.println("Sending back: " + result);

        // Returns the result to the client
        printStream.println(result);
    }
}
