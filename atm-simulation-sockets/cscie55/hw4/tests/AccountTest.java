/**
 * $Id: AccountTest.java, v 1.0 25/11/13 23:08 oscarfabra Exp $
 * {@code AccountTest} Is a class to test the methods from the Account Class.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 25/11/13
 * @see cscie55.hw4.Account
 */

package cscie55.hw4.tests;

import cscie55.hw4.Account;
import cscie55.hw4.exceptions.ATMException;
import junit.framework.TestCase;
import junit.textui.TestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test the Account Class from the ATM Distributed Application.
 * @see cscie55.hw4.Account
 */
public class AccountTest extends TestCase
{
    //-------------------------------------------------------------------------
    // ATTRIBUTE
    //-------------------------------------------------------------------------

    /**
     * Account object to test.
     */
    private Account account;

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new AccountTest calling its superclass.
     * @param name Name for the test.
     */
    public AccountTest(String name)
    {
        super(name);
    }

    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Method tha is executed before running any of the test methods.
     * @throws Exception If setUp didn't run successfully.
     */
    @Before
    public void setUp() throws Exception
    {
        this.account = new Account();
    }

    /**
     * Method to execute after running any of the test methods.
     * @throws Exception If tearDown didn't run successfully.
     */
    @After
    public void tearDown() throws Exception
    {
        this.account = null;
    }

    /**
     * Tests the getBalance() method from the Account class.
     * @throws Exception If test couldn't be done successfully.
     */
    @Test
    public void testGetBalance() throws Exception
    {
        // Balance should be 0.0 because the account is created with this value
        // in this.setUp() method.
        try
        {
            String errorMessage = "getBalance() broken, expected 0.0, got "+
                    this.account.getBalance();
            assertTrue(errorMessage, 0.0 == this.account.getBalance());
        }
        catch (ATMException e)
        {
            System.out.println("Exception gotten when testing getBalance()");
        }
    }

    /**
     * Tests the deposit() method from the Account class.
     * @throws Exception If test couldn't be done successfully.
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
                this.account.deposit(amount);
                String errorMessage = "deposit() broken, expected "+amount+
                        ", got " + this.account.getBalance();
                assertTrue(errorMessage,amount == this.account.getBalance());
            }
            catch(ATMException e)
            {
                System.out.println("Exception gotten when testing deposit()");
            }
        }
    }

    /**
     * Tests the withdraw() method from the Account class.
     * @throws Exception If the test couldn't be performed successfully.
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
                this.account.withdraw(amount);
                String errorMessage = "withdraw() broken, expected "+answer+
                        ", got " + this.account.getBalance();
                assertTrue(errorMessage,answer == this.account.getBalance());
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
     * Main method that runs each of the test methods in class AccountTest.
     *
     * @param args Empty String array.
     */
    public static void main(String [] args)
    {
        TestRunner.run(AccountTest.class);
    }
}
