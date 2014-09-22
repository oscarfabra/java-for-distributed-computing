/**
 * $Id: ATMFactoryImpl.java, v 1.0 5/12/13 17:33 oscarfabra Exp $
 * {@code ATMFactoryImpl} RMI-ready implementation of the ATMFactory interface.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 5/12/13
 */

package cscie55.hw5;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI-ready implementation of the ATMFactory interface.
 */
public class ATMFactoryImpl extends UnicastRemoteObject implements ATMFactory
{
    //-----------------------------------------------------------------
    // ATTRIBUTES
    //-----------------------------------------------------------------

    /**
     * ATM instance to be called.
     */
    private ATM atm = null;

    //-----------------------------------------------------------------
    // CONSTRUCTOR
    //-----------------------------------------------------------------

    /**
     * Creates a new ATM Factory.
     *
     * @throws RemoteException If ATMImpl couldn't be created.
     */
    public ATMFactoryImpl() throws RemoteException
    {
        if(this.atm == null)
        {
            this.atm = new ATMImpl();
            System.out.println("Created new ATMImpl.");
        }
    }

    //-----------------------------------------------------------------
    // PUBLIC METHODS
    //-----------------------------------------------------------------

    /**
     * Gets the ATM stub to be used by the Server process.
     *
     * @return ATM stub to be used by the Server
     * @throws java.rmi.RemoteException If the ATM stub couldn't be retrieved
     */
    @Override
    public ATM getATM() throws RemoteException
    {
        return this.atm;
    }
}
