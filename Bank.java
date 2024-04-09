import java.util.ArrayList;
import java.util.Random;
public class Bank
{
    private String name;

    private ArrayList<User> user;

    private ArrayList<Account> accounts;

    public Bank(String name)
    {
        this.name = name;
        this.user = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID()
    {
        String uuid;
        Random rng = new Random();
        int length = 6;
        boolean nonUnique = false;

        do
        {
            uuid = "";
            for(int i = 0; i < length; i++)
            {
               uuid += ((Integer)rng.nextInt(10)).toString();
            }

            nonUnique = false;
            for (User u : this.user)
            {
                if(uuid.compareTo(u.getUUID()) == 0)
                {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid;
    }

    public String getNewAccountUUID()
    {
        String uuid;
        Random rng = new Random();
        int length = 10;
        boolean nonUnique = false;

        do
        {
            uuid = "";
            for(int i = 0; i < length; i++)
            {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            nonUnique = false;
            for (Account a : this.accounts)
            {
                if(uuid.compareTo(a.getUUID()) == 0)
                {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid;
    }

    public void addAccount(Account anAcct)
    {
        this.accounts.add(anAcct);
    }

    public User addUser(String firstName, String lastName, String pin)
    {
        User newUser = new User(firstName, lastName, pin, this);
        this.user.add(newUser);

        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin)
    {
        for (User u : this.user)
        {
            if (u.getUUID().compareTo(userID)==0 && u.validatePin(pin))
            {
                return u;
            }
        }

        return null;
    }

    public String getName()
    {
        return this.name;
    }

}
