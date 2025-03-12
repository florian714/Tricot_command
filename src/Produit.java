public class Produit {

    // Attributes
    private int id;
    private String name;
    private float price;
    private String color;

    /**
     * Constructs a new Produit object.
     */
    public Produit(int id, String name, String color, float price) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.price = price;
    }

    public String getName() {
        return this.name + " " + this.color + " à " + this.price + " $ ";
    }
}