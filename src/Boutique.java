import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;

public class Boutique {
    // -------------------------------------------------------
    // Attributes
    // -------------------------------------------------------
    private List<Command> commandes;
    private List<Produit> produits;
    private List<Client> clients;

    // Constructeur
    public Boutique() {
        // initialisation
        this.commandes = new ArrayList<>();
        this.produits = new ArrayList<>();
        this.clients = new ArrayList<>();

        // récuperer tous les produits en stocks
        this.loadProduitsFromCSV("Données/produits.csv");
        this.loadClientsFromCSV("Données/clients.csv");
        this.loadCommandsFromCSV("Données/commandes.csv");
        ;
    }

    public static void main(String[] args) {
        // Votre code ici
        Boutique boutique = new Boutique();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans la boutique !");
        System.out.println("Veuillez entrer du texte :");
        String texteUtilisateur = scanner.nextLine();
        if ("liste".equals(texteUtilisateur.trim())) {
            System.out.println("voila");
            boutique.displayProducts();
        } else if ("nouveau".equals(texteUtilisateur.trim())) {
            String name = scanner.nextLine();
            String color = scanner.nextLine();
            float price = Float.parseFloat(scanner.nextLine());
            boutique.userAddProduit(name, color, price);
        } else if ("commande".equals(texteUtilisateur.trim())) {
            String produitName = scanner.nextLine();
            String produitColor = scanner.nextLine();
            String clientName = scanner.nextLine();
            boutique.passCommand(produitName, produitColor, clientName);
        } else {
            System.out.println("Commande non reconnue");
        }
    }

    public void addCommand(Command command) {
        this.commandes.add(command);
    }

    /*
     * Passer une commande
     */

    public void passCommand(String produitName, String produitColor, String clientName) {
        String filePath = "Données/commandes.csv";
        String[] derniereLigne = this.lireDerniereLigne(filePath).split(";");
        int id_command = Integer.parseInt(derniereLigne[0]);
        System.out.println("id_commande " + id_command);

        int id_produit = 0;
        for (Produit produit : this.produits) {
            if (produitName.trim().equals(produit.getName().trim())
                    & produitColor.trim().equals(produit.getColor().trim())) {
                id_produit = produit.getId();
            }
        }

        int id_client = 0;

        for (Client client : this.clients) {
            if (clientName.trim().equals(client.getName().trim())) {
                id_client = client.getId();
                System.out.println("idclient " + id_client);
            }
        }

        String[] newRowData = { Integer.toString(id_command + 1), Integer.toString(id_produit),
                Integer.toString(id_client) };

        try (FileWriter fw = new FileWriter(filePath, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw)) {

            // Écrire la nouvelle ligne dans le fichier CSV
            pw.println("\n" + String.join(";", newRowData));

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addCommand(new Command(id_command + 1, id_client, id_produit));
    }

    /**
     * -------------------------------------------------------
     * afficher les produits dispos
     */
    public void displayProducts() {
        System.out.println(this.produits.size());
        for (Produit produit : this.produits) {
            System.out.println(produit.getName());
        }
    }

    public void displayCommands() {
        System.out.println(this.commandes.size());
        for (Command commande : this.commandes) {
            System.out.println(Integer.toString(commande.getId()));
        }
    }

    public void userAddProduit(String name, String color, float price) {

        String filePath = "Données/produits.csv";
        String[] derniereLigne = this.lireDerniereLigne(filePath).split(";");
        String id = derniereLigne[0];

        String[] newRowData = { Integer.toString(Integer.parseInt(id) + 1), name, color, Float.toString(price) };

        try (FileWriter fw = new FileWriter(filePath, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw)) {

            // Écrire la nouvelle ligne dans le fichier CSV
            pw.println("\n" + String.join(";", newRowData));

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.addProduit(new Produit(Integer.parseInt(id) + 1, name, color, price));
    }

    /**
     * --------------------------------------------------------
     * Ajouter un produit aux produits du magazin.
     *
     * @param produit le produit que tu veux ajouter
     * @throws none
     */
    public void addProduit(Produit produit) {
        if (this.produits != null) {
            this.produits.add(produit);
        } else {
            System.err.println("La liste des produits n'est pas initialisée.");
        }
    }

    public void addClient(Client client) {
        if (this.clients != null) {
            this.clients.add(client);
        } else {
            System.err.println("La liste des produits n'est pas initialisée.");
        }
    }

    /**
     * ------------------------------------------------------------
     * Loads books from a CSV file and adds them to the library.
     * 
     * @param filePath The path to the CSV file containing book data.
     * @throws IOException If there is an error reading the file, an
     *                     {@link IOException} will be thrown.
     */
    public void loadProduitsFromCSV(String filePath) {
        URL url = getClass().getClassLoader().getResource(filePath);

        // Vérifier si l'URL est null
        if (url == null) {
            System.err.println("Ressource non trouvée : " + filePath);
            return;
        }

        try (InputStream inputStream = url.openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            br.readLine(); // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                if (data.length == 4) {
                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String color = data[2].trim();
                    float price = Float.parseFloat(data[3].trim());
                    // Créer le produit
                    Produit produit = new Produit(id, name, color, price);
                    // Ajouter le produit
                    this.addProduit(produit);
                } else {
                    System.err.println("Format de ligne incorrect : " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format de nombre dans le fichier : " + e.getMessage());
        }
    }

    public void loadClientsFromCSV(String filePath) {
        URL url = getClass().getClassLoader().getResource(filePath);

        // Vérifier si l'URL est null
        if (url == null) {
            System.err.println("Ressource non trouvée : " + filePath);
            return;
        }

        try (InputStream inputStream = url.openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            br.readLine(); // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                if (data.length == 3) {
                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String mdp = data[2].trim();
                    // Créer le produit
                    Client client = new Client(id, name, mdp);
                    // Ajouter le produit
                    this.addClient(client);
                } else {
                    System.err.println("Format de ligne incorrect : " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format de nombre dans le fichier : " + e.getMessage());
        }
    }

    public void loadCommandsFromCSV(String filePath) {
        URL url = getClass().getClassLoader().getResource(filePath);

        // Vérifier si l'URL est null
        if (url == null) {
            System.err.println("Ressource non trouvée : " + filePath);
            return;
        }

        try (InputStream inputStream = url.openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            br.readLine(); // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                if (data.length == 3) {
                    int id = Integer.parseInt(data[0].trim());
                    int id_client = Integer.parseInt(data[1].trim());
                    int id_produit = Integer.parseInt(data[2].trim());
                    // Créer le produit
                    Command command = new Command(id, id_client, id_produit);
                    // Ajouter le produit
                    this.addCommand(command);
                } else {
                    System.err.println("Format de ligne incorrect : " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format de nombre dans le fichier : " + e.getMessage());
        }
    }

    /**
     * Lire la dernire ligne d'un fichier csv
     */

    public static String lireDerniereLigne(String filePath) {
        String derniereLigne = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            // Lire le fichier ligne par ligne
            while ((ligne = br.readLine()) != null) {
                derniereLigne = ligne;
                System.out.println(ligne);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        return derniereLigne;
    }
}