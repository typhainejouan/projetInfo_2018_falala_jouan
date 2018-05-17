package coeur_collector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import coeur_collector.Administrateur;
import coeur_collector.Connexion;

/**
 * Classe Collecte, cette classe permet de créer une collecte
 * @author jouanfalala
 *
 */
public class Collecte {
	
	//Attributs
	/**
	 * Attribut correspondant à l'id_collecte dans la BDD
	 */
	private int id_collecte;
	/**
	 * Attribut correspondant à l'id_equipe dans la BDD
	 */
	private int id_equipe;
	/**
	 * Attribut correspondant à l'id_vehicule dans la BDD
	 */
	private int id_vehicule;
	/**
	 * Attribut correspondant à l'id_secteur dans la BDD
	 */
	private String id_secteur;
	/**
	 * Attribut correspondant à l'id_trajet dans la BDD
	 */
	private int id_trajet;
	/**
	 * Attribut correspondant à date_debut dans la BDD
	 */
	private String date_debut;
	/**
	 * Attribut correspondant à date_fin dans la BDD
	 */
	private String date_fin;
	/**
	 * Attribut correspondant à duree dans la BDD
	 */
	private double duree;
	/**
	 * Attribut correspondant à poids_dechet dans la BDD
	 */
	private double poids_dechet;
	/**
	 * Attribut correspondant à type_dechet dans la BDD
	 */
	private String type_dechet;

	//Constructeurs
	/**
	 * Constructeur de la classe Collecte
	 */
	public Collecte() {
	}

	//Accesseurs
	/**
	 * Accesseur de l'attribut id_collecte
	 * @return
	 */
	public int getId_collecte() {
		return id_collecte;
	}
	/**
	 * Accesseur de l'attribut id_equipe
	 * @return
	 */
	public int getId_equipe() {
		return id_equipe;
	}
	/**
	 * Accesseur de l'attribut id_vehicule
	 * @return
	 */
	public int getId_vehicule() {
		return id_vehicule;
	}
	/**
	 * Accesseur de l'attribut id_secteur
	 * @return
	 */
	public String getId_secteur() {
		return id_secteur;
	}
	/**
	 * Accesseur de l'attribut id_trajet
	 * @return
	 */
	public int getId_trajet() {
		return id_trajet;
	}
	/**
	 * Accesseur de l'attribut date_debut
	 * @return
	 */
	public String getDate_debut() {
		return date_debut;
	}
	/**
	 * Accesseur de l'attribut date_fin
	 * @return
	 */
	public String getDate_fin() {
		return date_fin;
	}
	/**
	 * Accesseur de l'attribut duree
	 * @return
	 */
	public double getDuree() {
		return duree;
	}
	/**
	 * Accesseur de l'attribut poids
	 * @return
	 */
	public double getPoids() {
		return poids_dechet;
	}
	/**
	 * Accesseur de l'attribut type_dechet
	 * @return
	 */
	public String getType_dechet() {
		return type_dechet;
	}


	//Mutateurs
	/**
	 * Mutateur de l'attribut id_collecte
	 * @param id_collecte
	 */
	public void setId_collecte(int id_collecte) {
		this.id_collecte = id_collecte;
	}
	/**
	 * Mutateur de l'attribut id_equipe
	 * @param id_equipe
	 */
	public void setId_equipe(int id_equipe) {
		this.id_equipe = id_equipe;
	}
	/**
	 * Mutateur de l'attribut id_vehicule
	 * @param id_vehicule
	 */
	public void setId_vehicule(int id_vehicule) {
		this.id_vehicule = id_vehicule;
	}
	/**
	 * Mutateur de l'attribut id_secteur
	 * @param id_secteur
	 */
	public void setId_secteur(String id_secteur) {
		this.id_secteur = id_secteur;
	}
	/**
	 * Mutateur de l'attribut id_trajet
	 * @param id_trajet
	 */
	public void setId_trajet(int id_trajet) {
		this.id_trajet = id_trajet;
	}
	/**
	 * Mutateur de l'attribut date_debut
	 * @param date_debut
	 */
	public void setDate_debut(String date_debut) {
		this.date_debut = date_debut;
	}
	/**
	 * Mutateur de l'attribut date_fin
	 * @param date_fin
	 */
	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}
	/**
	 * Mutateur de l'attribut duree
	 * @param date_debut
	 * @param date_fin
	 */
	public void setDuree(String date_debut, String date_fin) {				//Calcul de la durée
		this.duree = minute(date_fin) - minute(date_debut);
	}
	/**
	 * Mutateur de l'attribut poids
	 * @param poids
	 */
	public void setPoids(double poids) {
		this.poids_dechet = poids;
	}
	/**
	 * Mutateur de l'attribut type_dechet
	 * @param type_dechet
	 */
	public void setType_dechet(String type_dechet) {
		this.type_dechet = type_dechet;
	}

	//Méthodes 
	
	/**
	 * Méthodes pour renvoyer l'heure d'un date en minute
	 * @param date
	 * @return
	 */
	public static int minute(String date) {
		String heure_split = date.substring(11,13);
		String min_split = date.substring(14,16);
		int heure;
		if(heure_split.startsWith("0")) {
			heure = Integer.parseInt(heure_split.substring(1,2));
		}
		else {
			heure = Integer.parseInt(heure_split);
		}
		return Integer.parseInt(min_split)+heure*60;
	}

	//Ajout
	
	/**
	 * Méthode qui demande à l'admin de créer un objet Collecte
	 * puis l'ajoute dans la base de donnée
	 * @throws SQLException
	 */
	public static void ajout_BDD_collecte() throws SQLException{

		// Entrée manuelles des valeurs des variables
		Collecte collecte = new Collecte();        		// création de l'objet Collecte
		Statement statement = Connexion.getInstance().createStatement();

		System.out.println("Entrez l'identifiant de l'équipe, l'identifiant véhicule, l'identifiant secteur,");
		System.out.println("la date de début de collecte, l'identifiant du trajet, et le type de déchets.");

		System.out.println("Identifiant de l'équipe : ");
		int id_equipe_ajout = 0;
		int id_vehicule_ajout = 0;
		String id_secteur_ajout = "";
		String date_debut_ajout ="";
		
		ResultSet rs = statement.executeQuery("select statut from Vehicule");
		
		//initialisation des booléens
		boolean vehicule_ok = false;
		boolean secteur_ok = false;
		boolean date_ok = false;
		boolean horaire_ok = false;
		boolean equipe_ok = false;
		
		//Test de la disponibilité de l'équipe (si le nombre d'éboueur dispo >= 3)
		while(!equipe_ok) {
			System.out.println("Identifiant de l'équipe : ");
			id_equipe_ajout = Connexion.sc.nextInt();
			rs = statement.executeQuery("select COUNT(id_eboueur) AS ct from Eboueur WHERE id_equipe = " + id_equipe_ajout + "AND dispo = 0");
			rs.next();
			equipe_ok = (rs.getInt("ct")>=3);
			if(equipe_ok) {
				System.out.println("Equipe disponible, entrée validée");
			}
			else {
				System.out.println("Equipe indisponible, entrée invalidée");
			}
		}
		
		//Test de la disponibilité du véhicule 
		while(!vehicule_ok) {
			System.out.print("Identifiant du véhicule : ");
			id_vehicule_ajout = Connexion.sc.nextInt();
			rs = statement.executeQuery("select statut from Vehicule WHERE id_vehicule = " + id_vehicule_ajout);
			rs.next();
			vehicule_ok = (rs.getString("statut").equals("dispo")|rs.getString("statut").equals("circule"));
			if(vehicule_ok) {
				System.out.println("Véhicule disponible, entrée validée");
			}
			else {
				System.out.println("Véhicule indisponible, entrée invalidée");
			}
		}
		
		//Test secteur
		while(!secteur_ok) {
			System.out.print("Identifiant du secteur : ");
			id_secteur_ajout = Connexion.sc.next();
			id_secteur_ajout += Connexion.sc.nextLine();
			rs = statement.executeQuery("select id_secteur from Equipe WHERE id_equipe = " + id_equipe_ajout);
			rs.next();
			secteur_ok = (rs.getString("id_secteur").equals(id_secteur_ajout));
			if(secteur_ok) {
				System.out.println("L'équipe couvre ce secteur, entrée validée");
			}
			else {
				System.out.println("L'équipe ne couvre pas ce secteur, entrée invalidée");
			}	
		}
		
		//Test pour savoir si l'équipe est dispo pour la date donnée
		while(!horaire_ok | !date_ok) {
			System.out.print("Date de début de collecte (YYYY-MM-DD HH:mm): ");
			date_debut_ajout = Connexion.sc.next();
			date_debut_ajout += Connexion.sc.nextLine();
			int heure = Integer.parseInt(date_debut_ajout.substring(11,12));
			rs = statement.executeQuery("select horaires from Equipe WHERE id_equipe = " + id_equipe_ajout);
			rs.next();
			horaire_ok = ((rs.getString("horaires").equals("matin") && heure<11)|
					(rs.getString("horaires").equals("apres_midi") && heure>12));
			rs = statement.executeQuery("select id_equipe from Collecte WHERE date_debut = " + "\"" + date_debut_ajout + "\"");
			date_ok = !rs.next();
			
			if(secteur_ok | date_ok) {
				System.out.println("L'équipe travaille à cet horaire, entrée validée");
			}
			else {
				System.out.println("L'équipe ne travaille pas à cet horaire, entrée invalidée");
			}
		}
		
		//Récupération des id_trajet en fonction de l'id_secteur
		ArrayList<Integer> list_id_trajet = new ArrayList<Integer>();
		rs = statement.executeQuery("select id_trajet from Trajet WHERE id_secteur = " + "\"" + id_secteur_ajout +"\"");
		while(rs.next()) {
			list_id_trajet.add(rs.getInt("id_trajet"));
		}
		StringBuilder str = new StringBuilder();
        for (int i = 0; i < list_id_trajet.size(); i++) {
            str.append(list_id_trajet.get(i) + ",  ");
        }
        
        collecte.setId_equipe(id_equipe_ajout);
		collecte.setId_vehicule(id_vehicule_ajout);
		collecte.setId_secteur(id_secteur_ajout);
		collecte.setDate_debut(date_debut_ajout);
        
		System.out.print("Identifiant du trajet correspondant au secteur ("+ str.toString() +") : ");
		collecte.setId_trajet(Connexion.sc.nextInt());
		System.out.print("Type de déchet (NR ou Rec): ");
		collecte.setType_dechet(Connexion.sc.next());


		// Détermination de l'id de l'objet
		ArrayList<Integer> listId = new ArrayList<Integer>();				// Création de la liste des id
		rs = statement.executeQuery("select id_collecte from Collecte");	// Récupère les id de la table
		while(rs.next()) {
			listId.add(rs.getInt("id_collecte"));
		}

		if(listId.size()==0) {											// Si il n'y a pas d'entrée dans la table,
			collecte.setId_collecte(1);									// On réalise la première entrée dans la table éboueur (id = 1)
		}																// S'il existe déjà des entrées, on incrémente
		else {															//l'id pour l'objet que l'on veut ajouter
			collecte.setId_collecte(listId.get(listId.size()-1)+1); 	
		}

		// Ajout de la collecte à la BDD
		PreparedStatement ajout = Connexion.getInstance().prepareStatement("INSERT INTO Collecte("
				+ "id_collecte, id_equipe, id_vehicule,"
				+ " id_secteur, id_trajet, date_debut, type_dechet)"
				+ " VALUES(?, ?, ?, ?, ?, ? ,?)");

		ajout.setInt(1, collecte.getId_collecte());
		ajout.setInt(2, collecte.getId_equipe());
		ajout.setInt(3, collecte.getId_vehicule());
		ajout.setString(4,  collecte.getId_secteur());
		ajout.setInt(5,  collecte.getId_trajet());
		ajout.setString(6, collecte.getDate_debut());
		ajout.setString(7, collecte.getType_dechet());
		ajout.executeUpdate();
		System.out.println("Ajout d'une collecte effectué :)");

		//Mise à jour de l'historique
		Connexion.log("Ajout", "Collecte");
	}

	//Suppression
	/**
	 * Méthode qui demande à l'admin de rechercher une collecte dans
	 * la table Collecte de la BDD puis supprime cette entrée
	 * @throws SQLException
	 */
	public static void suppr_BDD_collecte() throws SQLException{

		//Recherche de la collecte à supprimer avec la méthode Administrateur.recherche()
		System.out.println("Recherchez la/les collecte(s) que vous voulez supprimer");
		List<ArrayList<Object>> recherche = Administrateur.recherche();					//Résultat de la recherche

		int taille = recherche.size();
		System.out.println("Nombre d'entrées à supprimer : " + taille);
		while(taille != 0) {
			int id_collecte_suppr = (int) recherche.get(taille-1).get(0);
			PreparedStatement suppr = Connexion.getInstance().prepareStatement(
					"DELETE FROM Collecte WHERE id_collecte=" + id_collecte_suppr);
			suppr.execute();
			taille -= 1;
			System.out.println("La suppression a été effectuée :)");

			//Mise à jour de l'historique
			Connexion.log("Suppression", "Collecte");
		}
	}

	//Modification
	/**
	 * Méthode qui demande à l'admin de rechercher une collecte dans
	 * la table Collecte de la BDD, puis d'entrer de nouveaux paramètres
	 * puis modifie cette entrée
	 * @throws SQLException
	 */
	public static void modif_BDD_collecte() throws SQLException {
		Collecte collecte_modif = new Collecte();											//Création de l'objet collecte modif

		//Recherche de la collecte à modifier avec la méthode Administrateur.recherche()
		System.out.println("Recherchez la collecte que vous voulez modifier");
		List<ArrayList<Object>> recherche = Administrateur.recherche();						//Résultat de la recherche

		int id_collecte_modif = (int) recherche.get(0).get(0);

		PreparedStatement modif = Connexion.getInstance().prepareStatement("UPDATE Collecte SET "
				+ "id_collecte = ?, id_equipe = ?, id_vehicule = ?, id_secteur = ?, id_trajet = ?,"
				+ " date_debut = ?, date_fin = ?, duree = ?, Poids_dechet = ?, type_dechet = ?"
				+ " WHERE id_collecte = " + id_collecte_modif);								//Requête SQL incomplète pour modifier la BDD

		System.out.println("\n\n\t\tModification des paramètres");							//Instructions pour l'utilisateur
		System.out.println("Pour conserver les paramètres actuels de la BDD, entrez \"\" "
				+ "pour une variable String, entrez 0 pour une variable Integer\n");

		//Modification ou conservation des valeurs de la BDD pour chaque variable
		System.out.print("Id_équipe : ");
		int id_equipe_modif = Connexion.sc.nextInt();
		if(id_equipe_modif == 0) {															//Test pour savoir si l'utilisateur
			collecte_modif.setId_equipe((int) recherche.get(0).get(1));						//veut modifier ou conserver la valeur
		}																					//de la BDD
		else {
			collecte_modif.setId_equipe(id_equipe_modif);
		}
		System.out.print("Id_vehicule : ");
		int id_vehicule_modif = Connexion.sc.nextInt();
		if(id_vehicule_modif == 0) {
			collecte_modif.setId_vehicule((int) recherche.get(0).get(2));
		}
		else {
			collecte_modif.setId_vehicule(id_vehicule_modif);
		}
		System.out.print("Id_secteur : ");
		String id_secteur_modif = Connexion.sc.next();
		id_secteur_modif = Connexion.sc.nextLine();
		if(id_secteur_modif.equals("r")) {
			collecte_modif.setId_secteur((String) recherche.get(0).get(3));			
		}
		else {
			collecte_modif.setId_secteur(id_secteur_modif);
		}
		System.out.print("Id_trajet : ");
		int id_trajet_modif = Connexion.sc.nextInt();
		if(id_trajet_modif == 0) {
			collecte_modif.setId_trajet((int) recherche.get(0).get(4));			
		}
		else {
			collecte_modif.setId_trajet(id_trajet_modif);
		}
		System.out.print("Date début (YYYY-MM-DD HH:mm): ");
		String date_debut_modif = Connexion.sc.next();
		date_debut_modif += Connexion.sc.nextLine();
		if(date_debut_modif.equals("r")) {
			collecte_modif.setDate_debut((String) recherche.get(0).get(5));			
		}
		else {
			collecte_modif.setDate_debut(date_debut_modif);
		}
		System.out.print("Date fin (YYYY-MM-DD HH:mm): ");
		String date_fin_modif = Connexion.sc.next();
		date_fin_modif += Connexion.sc.nextLine();
		if(date_fin_modif.equals("r")) {
			collecte_modif.setDate_fin((String) recherche.get(0).get(6));			
		}
		else {
			collecte_modif.setDate_fin(date_fin_modif);
		}
		collecte_modif.setDuree(collecte_modif.date_debut, collecte_modif.getDate_fin());		//On met à jour la durée

		System.out.print("Poids des déchets (en kilo): ");
		double poids_dechet_modif = Connexion.sc.nextDouble();
		if(poids_dechet_modif == 0) {
			collecte_modif.setPoids((double) recherche.get(0).get(8));			
		}
		else {
			collecte_modif.setPoids(poids_dechet_modif);
		}
		System.out.print("Type de déchet (NR ou Rec): ");
		String type_dechet_modif = Connexion.sc.next();
		if(type_dechet_modif.equals("r")) {
			collecte_modif.setType_dechet((String) recherche.get(0).get(9));			
		}
		else {
			collecte_modif.setType_dechet(type_dechet_modif);
		}

		modif.setInt(1, collecte_modif.getId_collecte());
		modif.setInt(2, collecte_modif.getId_equipe());									//Mise à jour du PreparedStatement
		modif.setInt(3, collecte_modif.getId_vehicule());									//(requête SQL)
		modif.setString(4, collecte_modif.getId_secteur());
		modif.setInt(5, collecte_modif.getId_trajet());
		modif.setString(6, collecte_modif.getDate_debut());
		modif.setString(7, collecte_modif.getDate_fin());
		modif.setDouble(8, collecte_modif.getDuree());
		modif.setDouble(9, collecte_modif.getPoids());
		modif.setString(10, collecte_modif.getType_dechet());
		System.out.println("\nModification effectuée :)");
		modif.executeUpdate();

		//Mise à jour de l'historique
		Connexion.log("Modification", "Collecte");
	}
}