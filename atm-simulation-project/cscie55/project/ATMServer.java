/**
 * $Id: ATMServer.java, v 1.0 26/12/13 17:48 oscarfabra Exp $
 * {@code ATMServer} The Server class starts up and registers the ATMFactory.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * The ATMServer class starts up and registers the ATMFactory with the RMI
 * registry.
 */
public class ATMServer
{
    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    /**
     * Main method of the ATMServer.
     *
     * @param args Empty array.
     */
    public static void main(String [] args)
    {
        Registry reg = null;

        // Assigns the policy file to the VM
        System.setProperty("java.security.policy",
            "file:cscie55/project/server.policy");

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
            System.out.println("Java RMI registry already exists.");
        }

        try
        {
            Bank bank = (Bank)reg.lookup("bank");
            Security security = (Security)reg.lookup("security");

            // Instantiates the ATMFactoryImpl
            ATMFactoryImpl atmFactory = new ATMFactoryImpl(bank, security);

            // Bind this object instance to the name "atmfactory"
            Naming.rebind("//localhost/atmfactory", atmFactory);

            System.out.println("ATMFactory bound in registry.");
        }
        catch (Exception ex)
        {
            System.out.println("ATMFactory error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
