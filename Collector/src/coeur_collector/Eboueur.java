package coeur_collector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Eboueur, cette classe hérite de la classe abstraite Utilisateur,
 * elle permet de créer un éboueur
 * @author jouanfalala
 *
 */

public class Eboueur extends Utilisateur{
	//Attributs
	/**
	 * Attribut correspondant à l'id_eboueur dans la BDD
	 */
	private int id_eboueur;
	/**
	 * Attribut correspondant à l'id_equipe dans la BDD
	 */
	private int id_equipe;
	/**
	 * Attribut correspondant au paramètre dispo dans la BDD
	 */
	private boolean dispo;


	//Constructeurs
	/**
	 * Constructeur de la classe Eboueur vide
	 */
	public Eboueur() {
	}
	/**
	 * Constructeur de la classe Eboueur
	 * @param id_eboueur
	 * @param id_equipe
	 * @param dispo
	 */
	public Eboueur(int id_eboueur, int id_equipe, boolean dispo) {
		super();
		this.id_eboueur = id_eboueur;
		this.id_equipe = id_equipe;
		this.dispo = dispo;
	}

	//Accesseurs
	/**
	 * Accesseur de l'attribut id_eboueur
	 * @return
	 */
	public int getId_eboueur() {
		return id_eboueur;
	}
	/**
	 * Accesseur de l'attribut id_equipe
	 * @return
	 */
	public int getId_equipe() {
		return id_equipe;
	}
	/**
	 * Accesseur de l'attribut dispo
	 * @return
	 */
	public boolean isDispo() {
		return dispo;
	}

	//Mutateurs
	/**
	 * Mutateur de l'attribut id_eboueur
	 * @param id_eboueur
	 */
	public void setId_eboueur(int id_eboueur) {
		this.id_eboueur = id_eboueur;
	}
	/**
	 * Mutateur de l'attribut id_equipe
	 * @param id_equipe
	 */
	public void setId_equipe(int id_equipe) {
		this.id_equipe = id_equipe;
	}
	/**
	 * Mutateur de l'attribut dispo
	 * @param dispo
	 */
	public void setDispo(boolean dispo) {
		this.dispo = dispo;
	}


	//Ajout
	/**
	 * Méthode qui demande à l'admin de créer un objet Eboueur
	 * puis l'ajoute dans la base de donnée
	 * @throws SQLException
	 */
	public static void ajout_BDD_eboueur() throws SQLException{
		Eboueur eboueur = new Eboueur();        										// création de l'objet Eboueur

		// Entrée manuelles des valeurs des variables
		System.out.println("\n\nEntrez le nom, le prénom, l'id_equipe"
				+ " et la date d'entrée de l'éboueur");


		//Test pour savoir si l'éboueur est déjà présent dans la BDD
		String Nom = "";
		String Prenom = "";
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("Select id_eboueur FROM Eboueur");
		boolean invalid = true;
		while(invalid) {
			System.out.print("Nom : ");
			Nom = Connexion.sc.next();
			System.out.print("Prénom : ");
			Prenom = Connexion.sc.next();
			rs = statement.executeQuery("Select id_eboueur FROM Eboueur "
				+ "WHERE login = " + "\"" + Prenom.substring(0,1) + Nom + "\"");
			invalid = rs.next();
			if(invalid) {
				System.out.println("L'éboueur que vous voulez"
						+ " ajouter est déjà présent dans la BDD");
			}
		}

		eboueur.setNom(Nom);
		eboueur.setPrenom(Prenom);
		System.out.print("Date d'entrée (YYYY-MM-DD): ");
		eboueur.setDate_entree(Connexion.sc.next());
		System.out.print("Id_equipe : ");
		eboueur.setId_equipe(Connexion.sc.nextInt());
		eboueur.setLogin();
		eboueur.setDispo(true);


		// Détermination de l'id de l'objet
		ArrayList<Integer> listId = new ArrayList<Integer>();							//Création de la liste des id
		rs = statement.executeQuery("select id_eboueur from Eboueur");				//Récupère les id de la table
		while(rs.next()) {
			listId.add(rs.getInt("id_eboueur"));
		}

		if(listId.size()==0) {														//Si il n'y a pas d'entrée dans la table,
			eboueur.setId_eboueur(1);												//On réalise la première entrée dans la table éboueur (id = 1)
		}
		else {																		//S'il existe déjà des entrées, on incrémente
			eboueur.setId_eboueur(listId.get(listId.size()-1)+1); 					//l'id pour l'objet que l'on veut ajouter
		}

		// Ajout de l'éboueur à la BDD
		PreparedStatement ajout = Connexion.getInstance().prepareStatement("INSERT INTO"
				+ " Eboueur(id_eboueur, Nom, Prenom, dispo, date_entree, login, mdp)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?)");

		ajout.setInt(1, eboueur.getId_eboueur());									//Mise à jour du PreparedStatement
		ajout.setString(2, eboueur.getNom());
		ajout.setString(3, eboueur.getPrenom());
		ajout.setBoolean(4, eboueur.isDispo());
		ajout.setString(5, eboueur.getDate_entree());  //modifier la date
		ajout.setString(6, eboueur.getLogin());
		ajout.setString(7, eboueur.getLogin());
		ajout.executeUpdate();
		System.out.println("Ajout effectué :)");

		//Mise à jour de l'historique
		Connexion.log("Ajout", "Eboueur");
	}	

	//Modification
	/**
	 * Méthode qui demande à l'admin de rechercher une éboueur dans
	 * la table Eboueur de la BDD, puis d'entrer de nouveaux paramètres,
	 * puis modifie cette entrée
	 * @throws SQLException
	 */
	public static void modif_BDD_eboueur() throws SQLException{
		Eboueur eboueur_modif = new Eboueur();											//Création de l'objet eboueur modif

		//Recherche de l'éboueur à modifier avec la méthode Administrateur.recherche()
		System.out.println("Recherchez l'éboueur que vous voulez modifier");
		List<ArrayList<Object>> recherche = Administrateur.recherche();					//Résultat de la recherche

		int id_eboueur_modif = (int) recherche.get(0).get(0);

		PreparedStatement modif = Connexion.getInstance().prepareStatement("UPDATE Eboueur SET "
				+ "Nom = ?, Prenom = ?, id_equipe = ?, dispo = ?, date_entree = ?,"
				+ " login = ?, mdp = ? WHERE id_eboueur = " + id_eboueur_modif);			//Requête SQL incomplète pour modifier la BDD

		System.out.println("\n\n\t\tModification des paramètres");						//Instructions pour l'utilisateur
		System.out.println("Pour conserver les paramètres actuels de la BDD, entrez r "
				+ "pour une variable String, entrez 0 pour une variable Integer\n");

		//Modification ou conservation des valeurs de la BDD pour chaque variable
		System.out.print("Nom : ");
		String nom_modif;
		nom_modif = Connexion.sc.next();
		if(nom_modif.equals("r")) {															//Test pour savoir si l'utilisateur
			eboueur_modif.setNom((String) recherche.get(0).get(1));						//veut modifier ou conserver la valeur
		}																				//de la BDD
		else {
			eboueur_modif.setNom(nom_modif);
		}
		System.out.print("Prénom : ");
		String prenom_modif = Connexion.sc.next();
		if(prenom_modif.equals("r")) {
			eboueur_modif.setPrenom((String) recherche.get(0).get(2));
		}
		else {
			eboueur_modif.setPrenom(prenom_modif);
		}
		System.out.print("Id équipe : ");
		int id_equipe_modif = Connexion.sc.nextInt();
		if(id_equipe_modif == 0) {
			eboueur_modif.setId_equipe((int) recherche.get(0).get(3));			
		}
		else {
			eboueur_modif.setId_equipe(id_equipe_modif);
		}
		System.out.print("Dispo : ");													//Pas de test ici
		boolean dispo_modif = Connexion.sc.nextBoolean();
		eboueur_modif.setDispo(dispo_modif);
		System.out.print("Date d'entrée : ");
		String date_entree_modif = Connexion.sc.next();
		if(date_entree_modif.equals("r")) {
			eboueur_modif.setDate_entree((String) recherche.get(0).get(5));			
		}
		else {
			eboueur_modif.setDate_entree(date_entree_modif);
		}
		eboueur_modif.setLogin();														//On met à jour le login

		System.out.print("Mot de passe : ");
		String mdp_modif = Connexion.sc.next();
		if(mdp_modif.equals("r")) {
			eboueur_modif.setMdp((String) recherche.get(0).get(7));			
		}
		else {
			eboueur_modif.setMdp(mdp_modif);
		}

		modif.setString(1, eboueur_modif.getNom());										//Mise à jour du PreparedStatement
		modif.setString(2, eboueur_modif.getPrenom());									//(requête SQL)
		modif.setInt(3, eboueur_modif.getId_equipe());
		modif.setBoolean(4, eboueur_modif.isDispo());
		modif.setString(5, eboueur_modif.getDate_entree());  //modifier la date
		modif.setString(6, eboueur_modif.getLogin());
		modif.setString(7, eboueur_modif.getLogin());
		System.out.println("\nModification effectuée :)");
		modif.executeUpdate();

		//Mise à jour de l'historique
		Connexion.log("Modification", "Eboueur");
	}

	//Suppression
	/**
	 * Méthode qui demande à l'admin de rechercher un éboueur dans
	 * la table Eboueur de la BDD puis supprime cette entrée
	 * @throws SQLException
	 */
	public static void suppr_BDD_eboueur() throws SQLException{

		//Recherche de l'éboueur à supprimer avec la méthode Administrateur.recherche()
		System.out.println("Recherchez le/les éboueur(s) que vous voulez supprimer");
		List<ArrayList<Object>> recherche = Administrateur.recherche();					//Résultat de la recherche

		int taille = recherche.size();
		System.out.println("Nombre d'entrées à supprimer : " + taille);

		while(taille != 0) {
			int id_eboueur_suppr = (int) recherche.get(taille).get(0);

			PreparedStatement suppr = Connexion.getInstance().prepareStatement("DELETE FROM Eboueur WHERE id_eboueur=" + id_eboueur_suppr);
			suppr.execute();
			taille -= 1;
			System.out.println("La suppression a été effectuée :)");

			//Mise à jour de l'historique
			Connexion.log("Suppression", "Eboueur");
		}
	}
}