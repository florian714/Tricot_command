public class Client {
    // Attributes
    private int id;
    private String name;
    private String mdp;

    /**
     * Constructs a new Client object.
     */
    public Client(int id, String name, String mdp) {
        this.id = id;
        this.name = name;
        this.mdp = mdp;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getMpd() {
        return this.mdp;
    }
}