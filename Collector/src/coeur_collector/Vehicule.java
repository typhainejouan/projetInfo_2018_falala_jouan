package coeur_collector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Vehicule, cette classe permet de créer un véhicule
 * @author jouanfalala
 *
 */
public class Vehicule {
	//Attributs
	/**
	 * Attribut correspondant à l'id_vehicule dans la BDD
	 */
	private int id_vehicule;
	/**
	 * Attribut correspondant à l'immatriculation dans la BDD
	 */
	private String immatriculation;
	/**
	 * Attribut correspondant au statut dans la BDD
	 */
	private String statut;
	/**
	 * Attribut stockant la date de mise en circulation du véhicule
	 * et correspondant à date_mise_circulation dans la BDD
	 */
	private String date_mise_circulation;
	/**
	 * Attribut stockant le nombre de kilomètre parcourus par le véhicule
	 * et correspondant à nb_km dans la BDD
	 */
	private double nb_km;


	//Constructeurs
	/**
	 * Constructeur de la classe Vehicule vide
	 */
	public Vehicule(){
	}
	/**
	 * Constructeur de la classe Equipe
	 * @param id_vehicule
	 * @param statut
	 * @param date_mise_circulation
	 * @param nb_km
	 */
	public Vehicule(int id_vehicule, String statut, String date_mise_circulation, double nb_km){
		this.id_vehicule           = id_vehicule;
		this.statut                = statut;
		this.date_mise_circulation = date_mise_circulation;
		this.nb_km                 = nb_km;
	}


	//Accesseurs
	/**
	 * Accesseur de l'attribut id_vehicule
	 * @return
	 */
	public int getId_vehicule() {
		return id_vehicule;
	}
	/**
	 * Accesseur de l'attribut immatriculation
	 * @return
	 */
	public String getImmatriculation() {
		return immatriculation;
	}
	/**
	 * Accesseur de l'attribut statut
	 * @return
	 */
	public String getStatut() {
		return statut;
	}
	/**
	 * Accesseur de l'attribut date_mise_circulation
	 * @return
	 */
	public String getDate_mise_circulation() {
		return date_mise_circulation;
	}
	/**
	 * Accesseur de l'attribut nb_km
	 * @return
	 */
	public double getNb_km() {
		return nb_km;
	}

	//Mutateurs
	/**
	 * Mutateur de l'attribut id_vehicule
	 * @param id_vehicule
	 */
	public void setId_vehicule(int id_vehicule) {
		this.id_vehicule = id_vehicule;
	}
	/**
	 * Mutateur de l'attribut immatriculation
	 * @param immatriculation
	 */
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}
	/**
	 * Mutateur de l'attribut statut
	 * @param statut
	 */
	public void setStatut(String statut) {
		this.statut = statut;
	}
	/**
	 * Mutateur de l'attribut date_mise_circulation
	 * @param date_mise_circulation
	 */
	public void setDate_mise_circulation(String date_mise_circulation) {
		this.date_mise_circulation = date_mise_circulation;
	}
	/**
	 * Mutateur de l'attribut nb_km
	 * @param nb_km
	 */
	public void setNb_km(double nb_km) {
		this.nb_km = nb_km;
	}

	//Ajout
	/**
	 * Méthode qui demande à l'admin de créer un objet Vehicule
	 * puis l'ajoute dans la base de donnée
	 * @throws SQLException
	 */
	public static void ajout_BDD_vehicule() throws SQLException{

		// Entrée manuelles des valeurs des variables
		Vehicule vehicule = new Vehicule();        		// création de l'objet Vehicule
		Statement statement = Connexion.getInstance().createStatement();
		
		System.out.println("Entrez l'immatriculation, le statut,"
				+ " la date de mise en circulation et le nombre de km du véhicule");

		System.out.print("Immatriculation : ");
		String immatriculation = Connexion.sc.next();
		
		//Test pour savoir si le véhicule est déjà présent dans la BDD
		ResultSet rs = statement.executeQuery("select id_vehicule "
				+ "from Vehicule WHERE immatriculation = " + immatriculation);
		boolean invalid = rs.next();
		while(invalid) {
			System.out.println("Le véhicule que vous voulez "
					+ "ajouter est déjà présent dans la BDD");
			System.out.print("Immatriculation : ");
			immatriculation = Connexion.sc.next();
			rs = statement.executeQuery("select id_vehicule"
					+ " from Vehicule WHERE immatriculation = " + immatriculation);
			invalid = rs.next();
		}
		
		vehicule.setImmatriculation(immatriculation);
		System.out.print("Statut (dispo, HS ou circule): ");
		vehicule.setStatut(Connexion.sc.next());
		System.out.print("Date de mise en circulation : ");
		vehicule.setDate_mise_circulation(Connexion.sc.next());
		System.out.print("Nombre de kilomètres parcourus : ");
		String nbkm = Connexion.sc.next();
		double nb = Double.parseDouble(nbkm);
		vehicule.setNb_km(nb);
		

		// Détermination de l'id de l'objet
		ArrayList<Integer> listId = new ArrayList<Integer>();							// Création de la liste des id
		rs = statement.executeQuery("select id_vehicule from Vehicule");	// Récupère les id de la table
		while(rs.next()) {
			listId.add(rs.getInt("id_vehicule"));
		}

		if(listId.size()==0) {														// Si il n'y a pas d'entrée dans la table,
			vehicule.setId_vehicule(1);												// On réalise la première entrée dans la table éboueur (id = 1)
		}
		else {																		// S'il existe déjà des entrées, on incrémente l'id pour l'objet que l'on veut ajouter
			vehicule.setId_vehicule(listId.get(listId.size()-1)+1); 	
		}

		// Ajout du véhicule à la BDD
		PreparedStatement ajout = Connexion.getInstance().prepareStatement("INSERT INTO Vehicule(id_vehicule, Immatriculation, Statut, Date_circulation, nb_km)"
				+ " VALUES(?, ?, ?, ?, ?)");

		ajout.setInt(1, vehicule.getId_vehicule());
		ajout.setString(2, vehicule.getImmatriculation());
		ajout.setString(3, vehicule.getStatut());
		ajout.setString(6, vehicule.getDate_mise_circulation());
		ajout.setDouble(7, vehicule.getNb_km());
		ajout.executeUpdate();
		System.out.println("Ajout effectué :)");

		//Mise à jour de l'historique
		Connexion.log("Ajout", "Vehicule");	  
	}	

	//Suppression
	/**
	 * Méthode qui demande à l'admin de rechercher un vehicule dans
	 * la table Vehicule de la BDD puis supprime cette entrée
	 * @throws SQLException
	 */
	public static void suppr_BDD_vehicule() throws SQLException{

		//Recherche du véhicule à modifier avec la méthode Administrateur.recherche()
		System.out.println("Recherchez le véhicule que vous voulez supprimer");
		List<ArrayList<Object>> recherche = Administrateur.recherche();					        //Résultat de la recherche

		int taille = recherche.size();

		while(taille != 0) {
			int id_vehicule_suppr = (int) recherche.get(taille).get(0);

			PreparedStatement suppr = Connexion.getInstance().prepareStatement(
				"DELETE FROM Vehicule WHERE id_vehicule=" + id_vehicule_suppr);
			suppr.execute();
			taille -= 1;
			System.out.println("La suppression a été effectuée :)");

			//Mise à jour de l'historique
			Connexion.log("Suppression", "Vehicule");
		}
	}

	//Modification
	/**
	 * Méthode qui demande à l'admin de rechercher un vehicule dans
	 * la table Vehicule de la BDD, puis d'entrer de nouveaux paramètres
	 * puis modifie cette entrée
	 * @throws SQLException
	 */
	public static void modif_BDD_vehicule() throws SQLException {
		Vehicule vehicule_modif = new Vehicule();											//Création de l'objet vehicule modif

		//Recherche de l'vehicule à modifier avec la méthode Administrateur.recherche()
		System.out.println("Recherchez le véhicule que vous voulez modifier");
		List<ArrayList<Object>> recherche = Administrateur.recherche();						//Résultat de la recherche

		int id_vehicule_modif = (int) recherche.get(0).get(0);

		PreparedStatement modif = Connexion.getInstance().prepareStatement(
				"UPDATE Vehicule SET Immatriculation = ?, Statut = ?, " +
				"Date_circulation = ?, nb_km = ? WHERE id_vehicule = " + id_vehicule_modif);	//Requête SQL incomplète pour modifier la BDD

		System.out.println("\n\n\t\tModification des paramètres");							//Instructions pour l'utilisateur
		System.out.println("Pour conserver les paramètres actuels de la BDD, entrez r "
				+ "pour une variable String, entrez 0 pour une variable Integer\n");

		//Modification ou conservation des valeurs de la BDD pour chaque variable
		System.out.print("Immatriculation : ");
		String immatriculation_modif = Connexion.sc.next();
		if(immatriculation_modif.equals("r")) {													//Test pour savoir si l'utilisateur
			vehicule_modif.setImmatriculation((String) recherche.get(0).get(1));				//veut modifier ou conserver la valeur
		}																					//de la BDD
		else {
			vehicule_modif.setImmatriculation(immatriculation_modif);
		}
		System.out.print("Statut : ");
		String statut_modif = Connexion.sc.next();
		if(statut_modif.equals("r")) {
			vehicule_modif.setStatut((String) recherche.get(0).get(2));
		}
		else {
			vehicule_modif.setStatut(statut_modif);
		}
		System.out.print("Date de mise en circulation (DD/mm/YYYY): ");
		String date_mise_circulation_modif = Connexion.sc.next();
		if(date_mise_circulation_modif.equals("r")) {
			vehicule_modif.setDate_mise_circulation((String) recherche.get(0).get(3));
		}
		else {
			vehicule_modif.setDate_mise_circulation(date_mise_circulation_modif);
		}
		System.out.print("Nombre de kilomètres : ");
		double nb_km_modif = Connexion.sc.nextDouble();
		if(nb_km_modif == 0) {
			vehicule_modif.setNb_km((double) recherche.get(0).get(4));			
		}
		else {
			vehicule_modif.setNb_km(nb_km_modif);
		}

		modif.setString(1, vehicule_modif.getImmatriculation());
		modif.setString(2, vehicule_modif.getStatut());										//Mise à jour du PreparedStatement
		modif.setString(3, vehicule_modif.getDate_mise_circulation());						//(requête SQL)
		modif.setDouble(4, vehicule_modif.getNb_km());
		System.out.println("\nModification effectuée :)");
		modif.executeUpdate();

		//Mise à jour de l'historique
		Connexion.log("Modification", "Vehicule");
	}
}