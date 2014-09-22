package cscie55.project.exceptions;

/**
 * ATMException used for reporting issues related with insufficient funds.
 */
public class ATMInsufficientFundsException extends ATMException
{
    public ATMInsufficientFundsException(String msg)
    {
        super(msg);
    }
}
