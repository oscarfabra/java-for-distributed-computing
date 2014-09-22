/**
 * $Id: ATMImplementationTest.java, v 1.0 25/11/13 23:15 oscarfabra Exp $
 * {@code ATMImplementationTest} Is a Class to test the ATMImplementation Class.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 25/11/13
 * @see cscie55.hw4.ATMImplementation
 */

package cscie55.hw4.tests;

import cscie55.hw4.ATMImplementation;
import cscie55.hw4.exceptions.ATMException;
import junit.framework.TestCase;
import junit.textui.TestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test the ATMImplementation Class's methods. In this case,
 * ATMImplementation is just a wrapper class that contains one single Account.
 * @see cscie55.hw4.ATMImplementation
 */
public class ATMImplementationTest extends TestCase
{
    //-------------------------------------------------------------------------
    // ATTRIBUTE
    //-------------------------------------------------------------------------

    /**
     * ATMImplementation object that allows the server to connect to client
     * processes.
     */
    private ATMImplementation atmImplementation;

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMImplementationTest to test the ATMImplementation class.
     * @param name Name for the test.
     */
    public ATMImplementationTest(String name)
    {
        super(name);
    }

    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Method tha is executed before running any of the test methods.
     * @throws Exception If the setUp() couldn't be done successfully.
     */
    @Before
    public void setUp() throws Exception
    {
        this.atmImplementation = new ATMImplementation();
    }

    /**
     * Method that is executed after running any of the test methods.
     * @throws Exception If tearDown() couldn't be done successfully.
     */
    @After
    public void tearDown() throws Exception
    {
        this.atmImplementation = null;
    }

    /**
     * Tests the getBalance() method from the ATMImplementation class.
     * @throws Exception If the test couldn't be made successfully.
     */
    @Test
    public void testGetBalance() throws Exception
    {
        // Balance should be 0.0 because the account is created with this value
        // in this.setUp() method.
        try
        {
            String errorMessage = "getBalance() broken, expected 0.0, got "+
                    this.atmImplementation.getBalance();
            assertTrue(errorMessage, 0.0 == this.atmImplementation.getBalance());
        }
        catch (ATMException e)
        {
            System.out.println("Exception gotten when testing getBalance()");
        }
    }

    /**
     * Tests the deposit() method from the ATMImplementation class.
     * @throws Exception If the test couldn't be done successfully.
     */
    @Test
    public void testDeposit() throws Exception
    {
        try
        {
            this.testGetBalance();
        }
        catch(Exception e)
        {
            System.out.println("If getBalance() is broken deposit() can't be "+
                    "tested.");
        }
        finally
        {
            try
            {
                float amount = 750;
                this.atmImplementation.deposit(amount);
                String errorMessage = "deposit() broken, expected "+amount+
                        ", got " + this.atmImplementation.getBalance();
                assertTrue(errorMessage,
                        amount == this.atmImplementation.getBalance());
            }
            catch(ATMException e)
            {
                System.out.println("Exception gotten when testing deposit()");
            }
        }
    }

    /**
     * Tests the withdraw() method from the ATMImplementation class.
     * @throws Exception If the test couldn't be done successfully.
     */
    @Test
    public void testWithdraw() throws Exception
    {
        try
        {
            this.testGetBalance();
            this.testDeposit();
        }
        catch(Exception e)
        {
            System.out.println("If getBalance() or deposit() are broken " +
                    "withdraw() can't be tested.");
        }
        finally
        {
            try
            {
                // this.testDeposit() adds 750 to the account's balance, so
                // after withdrawing 'amount' getBalance() should return
                // 750 - amount, or, 250.
                float amount = 500;
                float answer = 750 - amount;
                this.atmImplementation.withdraw(amount);
                String errorMessage = "withdraw() broken, expected "+answer+
                        ", got " + this.atmImplementation.getBalance();
                assertTrue(errorMessage,
                        answer == this.atmImplementation.getBalance());
            }
            catch(ATMException e)
            {
                System.out.println("Exception gotten when testing withdraw()");
            }
        }
    }

    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    /**
     * Main method that runs each of the test methods in class
     * ATMImplementationTest.
     *
     * @param args Empty String array.
     */
    public static void main(String [] args)
    {
        TestRunner.run(ATMImplementationTest.class);
    }
}
