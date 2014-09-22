/**
 * $Id: ATMProxyTest.java, v 1.0 25/11/13 23:22 oscarfabra Exp $
 * {@code ATMProxyTest} Is a class to test the methods from the ATMProxy class.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 25/11/13
 * @see cscie55.hw4.ATMProxy
 */

package cscie55.hw4.tests;

import cscie55.hw4.ATMProxy;
import cscie55.hw4.exceptions.ATMException;
import junit.framework.TestCase;
import junit.textui.TestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test the ATMProxy Class's methods.
 */
public class ATMProxyTest extends TestCase
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * ATMProxy object to test.
     */
    ATMProxy atmProxy = null;

    /**
     * Host location of server process, used to test communication between the
     * ATMProxy object and the server.
     */
    public static String host = "";

    /**
     * Port number where server process is located, used to test communication
     * between the ATMProxy object and the server.
     */
    public static int port = 0;

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMProxyTest by instantiating a server and the ATMProxy to
     * test.
     * <b>Pre:</b> Server must be running and listening to requests.
     *
     * @param name Name for the test.
     */
    public ATMProxyTest(String name)
    {
        super(name);
    }

    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Method that is executed before running any of the tester methods.
     * @throws Exception If setUp() couldn't be done successfully.
     */
    @Before
    public void setUp() throws Exception
    {
        System.out.println("-----------------------------------------");

        // Sets up the atmProxy for the upcoming tests
        this.atmProxy = new ATMProxy(host, port);
    }

    /**
     * Method that is executed after running any of the tester methods.
     * @throws Exception If tearDown() couldn't be done successfully.
     */
    @After
    public void tearDown() throws Exception
    {
        // Indicates to the server that a transaction just finished.
        this.atmProxy.done();

        // Closes the connection to the server
        this.atmProxy.close();
    }

    /**
     * Tests the getBalance() method of the ATMProxy class by connecting to a
     * local server and sending and receiving the appropriate messages.
     *
     * @throws Exception If the test couldn't be done successfully.
     */
    @Test
    public void testGetBalance() throws Exception
    {
        try
        {
            // Atempts to test getBalance() by getting two times the balance
            // and checking if they're the same
            float balance1 = this.atmProxy.getBalance();
            float balance2 = this.atmProxy.getBalance();
            String errorMessage = "getBalance() broken, balance1 not equal " +
                "to balance2";
            assertEquals(errorMessage, balance1, balance2);
        }
        catch (ATMException e)
        {
            System.out.println("Exception gotten when testing getBalance()");
        }
    }

    /**
     * Tests the deposit() method of the ATMProxy class by connecting to a
     * local server and sending and receiving the appropriate messages.
     *
     * @throws Exception If test couldn't be done successfully.
     */
    @Test
    public void testDeposit() throws Exception
    {
        try
        {
            // Atempts to test deposit() by checking if expected balance is
            // the same as the actual balance gotten after the deposit
            float balance = this.atmProxy.getBalance();
            float amount = 750;
            float answer = balance + amount;
            this.atmProxy.deposit(amount);
            float newBalance = this.atmProxy.getBalance();

            String errorMessage = "deposit() broken, expected " + answer +
                    ", got " + newBalance;

            assertEquals(errorMessage, answer, newBalance);
        }
        catch(ATMException e)
        {
            System.out.println("Exception gotten when testing deposit()");
        }
    }

    /**
     * Tests the withdraw() method of the ATMProxy class by connecting to a
     * local server and sending and receiving the appropriate messages.
     *
     * @throws Exception If the test couldn't be done successfully.
     */
    @Test
    public void testWithdraw() throws Exception
    {
        try
        {
            // Atempts to test deposit() by checking if expected balance is
            // the same as the actual balance gotten after the withdrawal
            float balance = this.atmProxy.getBalance();
            float amount = 500;
            float answer = (balance - amount >= 0)? balance - amount: balance;
            this.atmProxy.withdraw(amount);
            float newBalance = this.atmProxy.getBalance();


            String errorMessage = "withdraw() broken, expected " + answer +
                    ", got " + newBalance;
            assertEquals(errorMessage, answer, newBalance);
        }
        catch(ATMException e)
        {
            System.out.println("Exception gotten when testing withdraw()");
        }
    }

    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    /**
     * Main method that runs each of the test methods in class ATMProxyTest.
     *
     * @param args String array that contains host and port of server on
     *             args[0] and args[1] respectively.
     */
    public static void main(String [] args)
    {
        // parse command line arguments
        ATMProxyTest.host = args[0];
        ATMProxyTest.port = Integer.parseInt(args[1]);

        TestRunner.run(ATMProxyTest.class);
    }
}
