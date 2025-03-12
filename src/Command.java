public class Command {

    // Attributes
    private int id;
    private Client client;
    private Produit produit;

    /**
     * Constructs a new Command object.
     */
    public Command(int id, Client client, Produit produit) {
        this.id = id;
        this.client = client;
        this.produit = produit;
    }

}