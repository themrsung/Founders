package pegasus.founders.company;

import org.bukkit.OfflinePlayer;
import org.joda.time.DateTime;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * The superinterface for every type of company.
 */
public interface Company {
    //
    // Information
    //

    /**
     * Returns the unique identifier of this company.
     * @return The unique identifier of this company
     */
    UUID getUniqueId();

    /**
     * Returns the name of this company.
     * @return The name of this company
     */
    String getName();

    /**
     * Sets the name of this company.
     * @param name The name of this company
     */
    void setName(String name);

    /**
     * Returns the symbol of this company.
     * @return The symbol of this company
     */
    String getSymbol();

    /**
     * Sets the symbol of this company.
     * @param symbol The symbol of this company
     */
    void setSymbol(String symbol);

    /**
     * Returns the type of this company.
     * @return The type of this company
     */
    CompanyType getType();

    /**
     * Returns the creation date of this company.
     * @return The creation date of this company
     */
    DateTime getCreationDate();

    //
    // Representation
    //

    /**
     * Returns the CEO of company. Private companies with a single owner always have a CEO,
     * while public corporations can have no CEO at a given time.
     * @return The CEO of this company if designated, {@code null} if not
     */
    OfflinePlayer getCEO();

    /**
     * Sets the CEO of this company. Private companies cannot set a CEO.
     * @param ceo The CEO of this company
     */
    void setCEO(OfflinePlayer ceo);

    //
    // Employment
    //

    /**
     * Returns the employment of this company. This is a shallow copy.
     * @return The employment of this company
     */
    Map<OfflinePlayer, Double> getEmployment();

    /**
     * Returns a stream of the employees of this company.
     * @return The employees of this company
     */
    Stream<OfflinePlayer> getEmployees();

    /**
     * Returns the hourly pay of the employee. If the player is not an employee, this will return zero.
     * @param employee The employee
     * @return The hourly pay
     */
    double getHourlyPay(OfflinePlayer employee);

    /**
     * Sets the hourly pay of the provided employee.
     * @param employee The employee
     * @param pay The hourly pay
     */
    void setHourlyPay(OfflinePlayer employee, double pay);

    /**
     * Fires the provided employee.
     * @param employee The employee to fire
     */
    void fire(OfflinePlayer employee);

    //
    // Assets
    //

    /**
     * Returns the balance of this company. This should not be used to check whether the company
     * is able to pay a certain amount of money.
     * @return The balance of this company
     */
    double getBalance();

    /**
     * Sets the balance of this company.
     * @param balance The balance of this company
     */
    void setBalance(double balance);
}
