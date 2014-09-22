/**
 * $Id: Client.java, v 1.0 5/12/13 10:58 oscarfabra Exp $
 * {@code Client} Class that remotely invokes the Server's methods.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 5/12/13
 */

package cscie55.hw5;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class that remotely invokes the Server's methods.
 */
public class Client
{
    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    public static void main(String [] args)
    {

        Registry reg = null;
        ATM atm = null;

        // Assigns the policy file to the VM
        System.setProperty("java.security.policy","file:cscie55/hw5/client.policy");

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
            System.out.println();
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

        if (atm != null)
        {
            try
            {
                // get initial account balance
                System.out.println("Initial Balances");
                System.out.println("Balance(1): " + atm.getBalance(1));
                System.out.println("Balance(2): " + atm.getBalance(2));
                System.out.println("Balance(3): " + atm.getBalance(3));
                System.out.println();

                // make $1000 deposit in account 1 and get new balance
                System.out.println("Depositing(1): 1000");
                atm.deposit(1, 1000);
                System.out.println("Balance(1): " + atm.getBalance(1));

                // make $100 withdrawal from account 2 and get new balance
                System.out.println("Withdrawing(2): 100 ");
                atm.withdraw(2, 100);
                System.out.println("Balance(2): " + atm.getBalance(2));

                // make $500 deposit in account 3 and get new balance
                System.out.println("Depositing(3): 500 ");
                atm.deposit(3, 500);
                System.out.println("Balance(3): " + atm.getBalance(3));

                // get final account balance
                System.out.println();
                System.out.println("Final Balances");
                System.out.println("Balance(1): " + atm.getBalance(1));
                System.out.println("Balance(2): " + atm.getBalance(2));
                System.out.println("Balance(3): " + atm.getBalance(3));
            }
            catch (RemoteException re)
            {
                System.out.println("An exception occurred while " +
                        "communicating with the ATM");
                re.printStackTrace();
            }
        }
    }
}
