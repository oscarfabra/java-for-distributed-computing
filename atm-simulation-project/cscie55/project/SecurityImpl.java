/**
 * $Id: SecurityImpl.java, v 1.0 26/12/13 10:58 oscarfabra Exp $
 * {@code SecurityImpl} Implements the methods of the Security interface.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

import cscie55.project.commands.Commands;
import cscie55.project.exceptions.ATMSecurityException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;

/**
 * Implements the methods of the Security interface.
 *
 * @see cscie55.project.Account
 */
public class SecurityImpl extends UnicastRemoteObject implements Security
{

    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Collection of values to authenticate AccountInfo objects.
     */
    private static List<ATMAuthentication> authenticationValues = null;

    /**
     * Collection of values to authorize specific operations on individual
     * accounts.
     */
    private static List<ATMAuthorization> authorizationValues = null;

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new Security object and establishes the corresponding
     * authentication and authorization values for each account.
     *
     * @throws RemoteException If there's an unforeseen exception.
     */
    public SecurityImpl() throws RemoteException
    {
        super();

        // Initializes the authentication values
        if(authenticationValues == null)
        {
            authenticationValues = new Vector<ATMAuthentication>();

            // Adds 3 ATMAuthentication objects with their respective values
            authenticationValues.add(new ATMAuthentication(0000001, 1234));
            authenticationValues.add(new ATMAuthentication(0000002, 2345));
            authenticationValues.add(new ATMAuthentication(0000003, 3456));
        }

        // Initializes the authorization values
        if(authorizationValues == null)
        {
            authorizationValues = new Vector<ATMAuthorization>();

            // Adds 3 ATMAuthorization objects with their respective permitted
            // commands.
            authorizationValues.add(new ATMAuthorization(0000001,
                    Commands.DEPOSIT, Commands.WITHDRAW, Commands.INQUIRE));
            authorizationValues.add(new ATMAuthorization(0000002,
                    Commands.DEPOSIT, Commands.INQUIRE));
            authorizationValues.add(new ATMAuthorization(0000003,
                    Commands.WITHDRAW, Commands.INQUIRE));

            // To authorize a transaction between any two accounts we just
            // grant the first account the withdraw command and the second
            // account the deposit command.
        }
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Determines if a given AccountInfo object corresponds with an available
     * Bank account.
     *
     * @param accountInfo AccountInfo object to authenticate.
     * @return Whether it is authentic or not.
     * @throws ATMSecurityException If a security exception occurs.
     */
    @Override
    public boolean authenticateAccount(AccountInfo accountInfo)
            throws RemoteException
    {
        boolean authentic = false;
        ATMAuthentication authentication = null;
        int numberOfAuthentications = authenticationValues.size();

        for (int i=0; i < numberOfAuthentications; i++)
        {
            authentication = authenticationValues.get(i);
            if(authentication.corresponds(accountInfo))
            {
                authentic = true;
                break;
            }
        }
        return authentic;
    }

    /**
     * Determines if it should authorize a given transaction.
     *
     * @param transaction TransactionNotification to examine.
     * @return Whether the transaction is permitted or not.
     * @throws ATMSecurityException If a security exception occurs.
     */
    @Override
    public boolean authorizeTransaction(TransactionNotification transaction)
        throws RemoteException
    {
        boolean authorize = false;
        ATMAuthorization authorization1 = null,
                authorization2 = null;
        Commands command = transaction.getCommand();
        List<AccountInfo> accountsInfo = transaction.getAccountsInfo();
        AccountInfo accountInfo1 = null,
                accountInfo2 = null;

        if(command.equals(Commands.TRANSFER))
        {
            accountInfo1 = accountsInfo.get(0);
            accountInfo2 = accountsInfo.get(1);
            authorization1 = this.
                    searchAuthorization(accountInfo1.getAccountNumber());
            authorization2 = this.
                    searchAuthorization(accountInfo2.getAccountNumber());

            // Verifies that the first account has permission to be withdrawn
            // and that the second account has permission to receive a deposit.
            if(authorization1.isPermitted(Commands.WITHDRAW) &&
                    authorization2.isPermitted(Commands.DEPOSIT))
            {
                authorize = true;
            }
        }
        else
        {
            accountInfo1 = accountsInfo.get(0);
            authorization1 = this.
                    searchAuthorization(accountInfo1.getAccountNumber());

            // Verifies if the given transaction's command is permitted on its
            // corresponding account.
            if(authorization1.isPermitted(command))
            {
                authorize = true;
            }
        }

        return authorize;
    }

    /**
     * Searches and returns an ATMAuthorization object by its corresponding
     * account number.
     *
     * @param accountNumber Account number of the authorization.
     * @return ATMAuthorization object.
     */
    @Override
    public ATMAuthorization searchAuthorization(int accountNumber)
            throws RemoteException
    {
        ATMAuthorization authorization = null;

        for(ATMAuthorization atmAuthorization: authorizationValues)
        {
            if (atmAuthorization.getAccountNumber() == accountNumber)
            {
                authorization = atmAuthorization;
                break;
            }
        }
        return authorization;
    }
}
