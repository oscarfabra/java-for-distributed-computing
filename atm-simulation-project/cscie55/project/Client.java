/**
 * $Id: Client.java, v 1.0 26/12/13 10:58 oscarfabra Exp $
 * {@code Client} Class that remotely invokes the ATMServer's methods.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class that remotely invokes the ATMServer's methods.
 */
public class Client implements ATMListener
{

    //-------------------------------------------------------------------------
    // ATTRIBUTE
    //-------------------------------------------------------------------------

    /**
     * Variable used to uniquely identify a Client in any particular ATM.
     */
    private int listenerId;

    /**
     * Listener interface to read TransactionNotification objects from the ATM.
     */
    private static ATMListener myListener = null;

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new Client to listen for ATM TransactionNotification objects.
     * @param listenerId Integer to uniquely identify a client in the registry
     *                   of our distributed Java RMI application.
     */
    public Client(int listenerId)
    {
        this.listenerId = listenerId;
    }


    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Sets the listener's id for the ATM where it connected.
     */
    public void setListenerId(int listenerId) throws RemoteException
    {
        this.listenerId = listenerId;
    }

    /**
     * Receives a TransactionNotification object remotely from the ATM and
     * prints it in the standard output.
     */
    @Override
    public void getTransactionNotification(TransactionNotification transaction)
    {
        // Initializes myListener in case it is null
        if(myListener == null)
        {
            Registry reg = null;
            try
            {
                reg = LocateRegistry.getRegistry(1099);
            }
            catch(Exception ex)
            {
                System.out.println("Java RMI registry not found.");
            }

            try
            {
                String name = "listener_" + this.listenerId;
                myListener = (ATMListener)reg.lookup(name);
            }
            catch (Exception e)
            {
                System.out.println("ATMListener error: " + e.getMessage());
            }
        }

        try
        {
            myListener.printTransactionNotification(transaction);
        }
        catch(Exception e)
        {
            System.out.println("ATMListener error: " + e.getMessage());
        }
    }

    /**
     * Prints the TransactionNotification object on standard output.
     * @param transaction TransactionNotification object to print.
     */
    public void printTransactionNotification(TransactionNotification transaction)
    {
        System.out.println(transaction);
    }

    /**
     * Performs various tests on the ATMServer.
     *
     * @param atm Interface to the remote ATM implementation.
     */
    public static void testATM(ATM atm)
    {
        if (atm!=null)
        {
            printBalances(atm);
            System.out.println();

            performTestOne(atm);
            System.out.println();

            performTestTwo(atm);
            System.out.println();

            performTestThree(atm);
            System.out.println();

            performTestFour(atm);
            System.out.println();

            performTestFive(atm);
            System.out.println();

            performTestSix(atm);
            System.out.println();

            performTestSeven(atm);
            System.out.println();

            performTestEight(atm);
            System.out.println();

            performTestNine(atm);
            System.out.println();

            printBalances(atm);
            System.out.println();
        }
    }

    /**
     * Prints balances of the accounts that reside on the Bank implementation.
     *
     * @param atm Interface to the remote ATM implementation.
     */
    public static void printBalances(ATM atm)
    {
        try
        {
            System.out.println("Balance(0000001): " +
                    atm.getBalance(getAccountInfo(0000001, 1234)));
            System.out.println("Balance(0000002): " +
                    atm.getBalance(getAccountInfo(0000002, 2345)));
            System.out.println("Balance(0000003): " +
                    atm.getBalance(getAccountInfo(0000003, 3456)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Performs test one.
     *
     * @param atm Interface to the remote ATM implementation.
     */
    public static void performTestOne(ATM atm)
    {
        try
        {
            atm.getBalance(getAccountInfo(0000001, 5555));
            System.out.println("Test one performed successfully.");
        }
        catch (Exception e)
        {
            System.out.println("Test one failed as expected: "+e);
        }
    }

    /**
     * Performs test two.
     *
     * @param atm Interface to the remote ATM implementation.
     */
    public static void performTestTwo(ATM atm)
    {
        try
        {
            atm.withdraw(getAccountInfo(0000002, 2345), 500);
            System.out.println("Test two performed successfully.");
        }
        catch (Exception e)
        {
            System.out.println("Test two failed as expected: "+e);
        }
    }

    /**
     * Performs test three.
     *
     * @param atm Interface to the remote ATM implementation.
     */
    public static void performTestThree(ATM atm)
    {
        try
        {
            atm.withdraw(getAccountInfo(0000001, 1234), 50);
            System.out.println("Test three performed successfully.");
        }
        catch (Exception e)
        {
            System.out.println("Test three failed as expected: "+e);
        }
    }

    /**
     * Performs test four.
     *
     * @param atm Interface to the remote ATM implementation.
     */
    public static void performTestFour(ATM atm)
    {
        try
        {
            atm.deposit(getAccountInfo(0000001, 1234), 500);
            System.out.println("Test four performed successfully.");
        }
        catch (Exception e)
        {
            System.out.println("Test four unexpected error: "+e);
        }
    }

    /**
     * Performs test five.
     *
     * @param atm Interface to the remote ATM implementation.
     */
    public static void performTestFive(ATM atm)
    {
        try
        {
            atm.deposit(getAccountInfo(0000002, 2345), 100);
            System.out.println("Test five performed successfully.");
        }
        catch (Exception e)
        {
            System.out.println("Test five unexpected error: "+e);
        }
    }

    /**
     * Performs test six.
     *
     * @param atm Interface to the remote ATM implementation.
     */
    public static void performTestSix(ATM atm)
    {
        try
        {
            atm.withdraw(getAccountInfo(0000001, 1234), 100);
            System.out.println("Test six performed successfully.");
        }
        catch (Exception e)
        {
            System.out.println("Test six unexpected error: "+e);
        }
    }

    /**
     * Performs test seven.
     *
     * @param atm Interface to the remote ATM implementation.
     */
    public static void performTestSeven(ATM atm)
    {
        try
        {
            atm.withdraw(getAccountInfo(0000003, 3456), 300);
            System.out.println("Test seven performed successfully.");
        }
        catch (Exception e)
        {
            System.out.println("Test seven unexpected error: "+e);
        }
    }

    /**
     * Performs test eight.
     *
     * @param atm Interface to the remote ATM implementation.
     */
    public static void performTestEight(ATM atm)
    {
        try
        {
            atm.withdraw(getAccountInfo(0000001, 1234), 200);
            System.out.println("Test eight performed successfully.");
        }
        catch (Exception e)
        {
            System.out.println("Test eight failed as expected: "+e);
        }
    }

    /**
     * Performs test nine.
     *
     * @param atm Interface to the remote ATM implementation.
     */
    public static void performTestNine(ATM atm)
    {
        try
        {
            atm.transfer(getAccountInfo(0000001, 1234),
                    getAccountInfo(0000002, 2345), 100);
            System.out.println("Test nine performed successfully.");
        }
        catch (Exception e)
        {
            System.out.println("Test nine unexpected error: "+e);
        }
    }

    //-------------------------------------------------------------------------
    // PRIVATE HELPER METHOD
    //-------------------------------------------------------------------------

    /**
     * Returns an AccountInfo object given the appropriate commands.
     *
     * @param accountNumber Account's number.
     * @param pin Personal Identification Number.
     * @return AccountInfo object.
     */
    private static AccountInfo getAccountInfo(int accountNumber, int pin)
    {
        return new AccountInfo(accountNumber, pin);
    }

    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    public static void main(String [] args)
    {
        Registry reg = null;
        ATM atm = null;

        // Assigns the policy file to the VM
        System.setProperty("java.security.policy",
            "file:cscie55/project/client.policy");

        // Create and install a security manager
        if(System.getSecurityManager()==null)
        {
            System.setSecurityManager(new RMISecurityManager());
            System.out.println("Security manager installed.");
        }

        try
        {
            reg = LocateRegistry.getRegistry(1099);
            System.out.println("Java RMI registry located.");
        }
        catch(Exception ex)
        {
            System.out.println("Java RMI registry not found.");
        }

        try
        {
            ATMFactory factory = (ATMFactory)reg.lookup("atmfactory");
            atm = factory.getATM();

            // Create a new client to listen for TransactionNotification
            // objects, registers it, and sets its listenerId
            Client listener = new Client(0000001);
            listener.setListenerId(atm.registerListener(listener));

            System.out.println("Client registered with ATM as ATMListener.");
            System.out.println();

            // Calls the test method from the Client class
            Client.testATM(atm);
        }
        catch (NotBoundException nbe)
        {
            nbe.printStackTrace();
        }
        catch (UnknownHostException uhe)
        {
            uhe.printStackTrace();
        }
        catch (RemoteException re)
        {
            re.printStackTrace();
        }
    }

}
