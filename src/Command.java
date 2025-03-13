public class Command {

    // Attributes
    private int id;
    private int id_client;
    private int id_produit;

    /**
     * Constructs a new Command object.
     */
    public Command(int id, int id_client, int id_produit) {
        this.id = id;
        this.id_client = id_client;
        this.id_produit = id_produit;
    }

    public int getId() {
        return id;
    }

    public int getIdClient() {
        return id_client;
    }

    public int getIdProduit() {
        return id_produit;
    }

}