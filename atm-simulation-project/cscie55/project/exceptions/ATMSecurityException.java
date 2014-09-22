package cscie55.project.exceptions;

/**
 * ATMException (RemoteException) used for reporting issues related with
 * authentication of accounts and authorization of transactions.
 */
public class ATMSecurityException extends ATMException
{
    public ATMSecurityException(String msg)
    {
        super(msg);
    }
}
