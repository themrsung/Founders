package pegasus.founders.company;

/**
 * The type of company.
 */
public enum CompanyType {
    /**
     * A private company owned and run by a single player.
     */
    PRIVATE,

    /**
     * A public company owned by shareholders and run by a representative.
     */
    CORPORATION,

    /**
     * A company owned by the server. Server companies have no minimum balance.
     */
    SERVER,
}
