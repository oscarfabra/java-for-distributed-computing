/**
 * $Id: Client.java, v 1.0 21/11/13 23:06 oscarfabra Exp $
 * {@code Client} Is a class that represents a client that makes remote
 * transactions with an ATM server.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 21/11/13
 * @see cscie55.hw6.Server
 */

package cscie55.hw6;

import cscie55.hw6.exceptions.ATMException;

/**
 * Class that represents a client that makes remote transactions with an ATM
 * server.
 */
public class Client
{
    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    /**
     * Main class of the ATM Client.
     * @param args String array that contains host and port of server on
     *             args[0] and args[1] respectively.
     */
    public static void main(String [] args)
    {
        try
        {
            // parse command line arguments
            String host = args[0];
            int port = Integer.parseInt(args[1]);

            ATM atm = new ATMProxy(host, port);

            // get initial account balance
            System.out.println("Balance: " + atm.getBalance());

            // make $1000 deposit and get new balance
            System.out.println("Depositing: 1000");
            atm.deposit(1000);
            System.out.println("Balance: " + atm.getBalance());

            // make $250 withdrawal and get new balance
            System.out.println("Withdrawing: 250");
            atm.withdraw(250);
            System.out.println("Balance: " + atm.getBalance());

            // make $750 withdrawal and get new balance
            System.out.println("Withdrawing: 750");
            atm.withdraw(750);
            System.out.println("Balance: " + atm.getBalance());
        }
        catch (ATMException ae)
        {
            System.out.println("An exception occurred while communicating " +
                    "with the ATM.");
            ae.printStackTrace();
        }
    }

}
