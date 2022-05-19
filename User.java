import java.util.ArrayList;
import java.security.MessageDigest;

public class User{

    /**
     * First name of user
     */
    private String firstName;

    /**
     * Last name of user
     */
    private String lastName;

    /**
     * ID number of user
     */
    private String uuid;

    /**
     * MD5 Hash of the user's pin number
     */
    private byte pinHash[];

    /**
     * List of accounts for this user
     */
    private ArrayList<Account>accounts;

    /**
	 * Create new user
	 * @param firstName: the user's first name
	 * @param lastName:  the user's last name
	 * @param pin:       the user's account pin number (as a String)
	 * @param theBank:	 the bank that the User is a customer of
	 */
    public User(String firstName, String lastName, String pin, Bank theBank){

        // set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        // store the pin's MD5 hash, rather than original value for safety/security reasons
        // try catch to handle NoSuchAlgorithmException or use throw in method signature
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (Exception e){
            System.err.println("Error, caught exception: " + e.getMessage());
            System.exit(1);
        }

        // get new, unique universal ID for the user
        this.uuid = theBank.getNewUserUUID();

        // empty list of accounts
        this.accounts = new ArrayList<Account>();

        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
    }

    /**
     * Get user ID
     * @return:             the uuid
     */
    public String getUUID(){
        return this.uuid;
    }

    /**
	 * Add an account for the user.
	 * @param anAcct:       the account to add
	 */
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
	
	/**
	 * Get the number of accounts the user has.
	 * @return:             the number of accounts
	 */
	public int numAccounts() {
		return this.accounts.size();
	}
	
	/**
	 * Get the balance of a particular account.
	 * @param acctIdx:      the index of the account to use
	 * @return:             the balance of the account
	 */
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}

    /**
	 * Get the UUID of a particular account.
	 * @param acctIdx:      the index of the account to use
	 * @return:             the UUID of the account
	 */
    public String getAcctUUID(int acctIdx){
        return this.accounts.get(acctIdx).getUUid();
    }

    /**
	 * Print transaction history for a particular account.
	 * @param acctIdx:      the index of the account to use
	 */
    public void printAcctTransHistory(int acctIdx){
        this.accounts.get(acctIdx).printTransHistory();
    }

    /**
	 * Add a transaction to a particular account.
	 * @param acctIdx:	    the index of the account
	 * @param amount:	    the amount of the transaction
	 * @param memo:     	the memo of the transaction
	 */
	public void addAcctTransaction(int acctIdx, double amount, String memo){
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}
	
	/**
	 * Check whether a given pin matches the true User pin
	 * @param aPin:     	the pin to check
	 * @return:     		whether the pin is valid or not
	 */
	public boolean validatePin(String aPin){
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (Exception e) {
			System.err.println("Error, caught exeption : " + e.getMessage());
			System.exit(1);
		}
		
		return false;
	}
	
	/**
	 * Print summaries for the accounts of this user.
	 */
	public void printAccountsSummary(){
		
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for (int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("%d) %s\n", a+1, 
					this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}
}