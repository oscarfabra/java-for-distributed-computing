package cscie55.project.exceptions;

/**
 * ATMException used for reporting issues related with invalid amounts.
 */
public class ATMInvalidAmountException extends ATMException
{
    public ATMInvalidAmountException(String msg)
    {
        super(msg);
    }
}
