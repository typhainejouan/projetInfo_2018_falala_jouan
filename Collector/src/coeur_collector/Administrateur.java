package coeur_collector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Statistiques.*;

/**
 * Classe héritant de la classe Utilisateur, permet de créer un objet administrateur
 * @author jouanfalala
 *
 */
public class Administrateur extends Utilisateur{
	//Attributs
	private int id_admin;

	//Accesseur
	/**
	 * Accesseur de l'attribut id_admin
	 * @return
	 */
	public int getId_admin() {
		return id_admin;
	}

	//Mutateur
	/**
	 * Mutateur de l'attribut id_admin
	 * @param id_admin
	 */
	public void setId_admin(int id_admin) {
		this.id_admin = id_admin;
	}


	//Calcul stats
	/**
	 * Méthode qui calcule les statistiques
	 * @throws SQLException
	 */
	public static void calcul_stat() throws SQLException{

		//Ajout stat collecte
		System.out.println("\nAjout stat collecte");
		Stat_collecte.ajout_BDD_stat_collecte();

		//Ajout stat eboueur
		System.out.println("\nAjout stat eboueur");
		Stat_eboueur.modif_stat_ebou_BDD();;

		//Ajout stat equipe
		for(int i=1; i<=6; i++) {
			System.out.println("\nAjout stat equipe " + i);
			Stat_equipe.ajout_stat_equipe(i, "2018-05");
		}

		//Ajout stat secteur
		System.out.println("\nAjout stat secteur");
		String[] list_id_secteur = new String[6];
		int i = 0;
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("Select id_secteur FROM Secteur");
		while(rs.next()) {
			list_id_secteur[i] = (rs.getString("id_secteur"));
			i++;
		}
		for(String e : list_id_secteur) {
			Stat_secteur.ajout_stat_secteur(e);
		}
	}

	//Recherche
	/**
	 * Méthode qui recherche les attributs dans une table donnée
	 * @return List<ArrayList<Object>> ResList
	 * @throws SQLException
	 */
	public static List<ArrayList<Object>> recherche() throws SQLException{

		//Initialisation des conditions de la recherche
		System.out.println("Indiquez la table (avec ou sans jointure) dans laquelle "
				+ "vous voulez effectuer la recherche");
		System.out.print("Table : ");
		String table = Connexion.sc.next();
		System.out.println("Indiquez les conditions en SQL (s'il y en a) de votre recherche");
		System.out.println("Si vous ne souhaitez pas entrer de conditions, entrez \"\"");
		System.out.print("Conditions : ");
		String conditions = Connexion.sc.next();
		conditions += Connexion.sc.nextLine();
		conditions = " " + conditions;
		System.out.print("Commande SQL : ");
		System.out.println("SELECT * FROM " + table + conditions + "\n");

		//Récupération des résultats
		List<ArrayList<Object>> ResList = new ArrayList<ArrayList<Object>>();		// Liste de listes contenant les résultats de la recherche
		PreparedStatement find = Connexion.getInstance().prepareStatement(
				"SELECT * FROM " + table + conditions);
		ResultSet resultat = find.executeQuery();
		ResultSetMetaData resultMeta = resultat.getMetaData();
		int p = resultMeta.getColumnCount();


		//Affichage du nom des colonnes
		System.out.println("\t\t**********************************************"
				+ "***********************************************************"
				+ "**************************");
		for(int i = 1; i <= p; i++){
			System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
		}

		//Affichage des entrées
		System.out.println("\n\t------------------------------------------"
				+ "----------------------------------------------------"
				+ "----------------------------------------------------");
		while(resultat.next()){
			ArrayList<Object> list = new ArrayList<Object>();		// Création d'une liste pour chaque entrée de la BDD
			System.out.print("\t");
			for(int i = 1; i <= p; i++){ 
				if(resultat.getObject(i)==null) {				// Si la valeur de l'entrée est nulle
					System.out.print("\t" + "NULL" + "\t |");
					list.add(null);
				}
				else {											// Si la valeur de l'entrée n'est pas nulle
					System.out.print("\t" + resultat.getObject(i).toString() + "\t |");
					list.add(resultat.getObject(i));
				}
			}
			ResList.add(list);
			System.out.println("\n\t------------------------------------------"
					+ "----------------------------------------------------"
					+ "----------------------------------------------------");
		}
		resultat.close();
		find.close();		

		return ResList;
	}

	//Modif BDD
	/**
	 * Méthode permettant l'appel d'autres méthodes pour modifier la BDD 
	 * @throws SQLException
	 */
	public static void modif_BDD() throws SQLException {
		int choix;
		String table;
		System.out.println("Que voulez vous faire ?");
		System.out.println("Pour ajouter un objet, tapez 1");
		System.out.println("Pour modifier un objet, tapez 2");
		System.out.println("Pour supprimer un objet, tapez 3");
		System.out.print("Choix : ");
		choix = Connexion.sc.nextInt();
		System.out.println("Quelle table voulez altérer (Eboueur, Vehicule, Equipe, Administrateur ou bien Collecte ?");
		table = Connexion.sc.next();
		if(choix==1) {
			if(table.equals("Eboueur")) {
				Eboueur.ajout_BDD_eboueur();
			}
			if(table.equals("Vehicule")) {
				Vehicule.ajout_BDD_vehicule();
			}
			if(table.equals("Equipe")) {
				Equipe.ajout_BDD_equipe();
			}
			if(table.equals("Collecte")) {
				Collecte.ajout_BDD_collecte();
			}
			if(table.equals("Administrateur")) {
				Administrateur.ajout_BDD_admin();
			}
		}
		if(choix==2) {
			if(table.equals("Eboueur")) {
				Eboueur.modif_BDD_eboueur();
			}
			if(table.equals("Vehicule")) {
				Vehicule.modif_BDD_vehicule();
			}
			if(table.equals("Equipe")) {
				Equipe.modif_BDD_equipe();
			}
			if(table.equals("Collecte")) {
				Collecte.modif_BDD_collecte();
			}
			if(table.equals("Administrateur")) {
				Administrateur.modif_BDD_admin();
			}
		}
		if(choix==3) {
			if(table.equals("Eboueur")) {
				Eboueur.suppr_BDD_eboueur();
			}
			if(table.equals("Vehicule")) {
				Vehicule.suppr_BDD_vehicule();
			}
			if(table.equals("Equipe")) {
				Equipe.suppr_BDD_equipe();
			}
			if(table.equals("Collecte")) {
				Collecte.suppr_BDD_collecte();
			}
		}		
	}

	//Modification admin
	/**
	 * Méthode qui demande à l'admin de rechercher un admin dans
	 * la table Administrateur de la BDD, puis d'entrer de nouveaux paramètres,
	 * puis modifie cette entrée
	 * @throws SQLException
	 */
	public static void modif_BDD_admin() throws SQLException{
		Administrateur admin_modif = new Administrateur();										//Création de l'objet eboueur modif

		//Recherche de l'éboueur à modifier avec la méthode Administrateur.recherche()
		System.out.println("Recherchez l'admin que vous voulez modifier");
		List<ArrayList<Object>> recherche = Administrateur.recherche();					//Résultat de la recherche

		int id_admin = (int) recherche.get(0).get(0);

		PreparedStatement modif = Connexion.getInstance().prepareStatement("UPDATE Administrateur SET "
				+ "login = ?, Nom = ?, Prenom = ?, mdp = ? WHERE id_admin = " + id_admin);//Requête SQL incomplète pour modifier la BDD

		System.out.println("\n\n\t\tModification des paramètres");						//Instructions pour l'utilisateur
		System.out.println("Pour conserver les paramètres actuels de la BDD, entrez r "
				+ "pour une variable String, entrez 0 pour une variable Integer\n");

		//Modification ou conservation des valeurs de la BDD pour chaque variable
		System.out.print("Nom : ");
		String nom_modif;
		nom_modif = Connexion.sc.next();
		if(nom_modif.equals("r")) {													//Test pour savoir si l'utilisateur
			admin_modif.setNom((String) recherche.get(0).get(2));						//veut modifier ou conserver la valeur
		}																			//de la BDD
		else {
			admin_modif.setNom(nom_modif);
		}
		System.out.print("Prénom : ");
		String prenom_modif = Connexion.sc.next();
		if(prenom_modif.equals("r")) {
			admin_modif.setPrenom((String) recherche.get(0).get(3));
		}
		System.out.print("Mot de passe : ");
		String mdp_modif = Connexion.sc.next();
		if(mdp_modif.equals("r")) {
			admin_modif.setMdp((String) recherche.get(0).get(4));			
		}
		else {
			admin_modif.setMdp(mdp_modif);
		}
		admin_modif.setLogin();

		modif.setString(2, admin_modif.getNom());						//Mise à jour du PreparedStatement
		modif.setString(3, admin_modif.getPrenom());
		modif.setString(1, admin_modif.getLogin());
		modif.setString(4, admin_modif.getMdp());
		System.out.println("\nModification effectuée :)");
		modif.executeUpdate();

		//Mise à jour de l'historique
		Connexion.log("Modification", "Administrateur");
	}

	//Ajout admin
	/**
	 * Méthode qui ajoute un objet administrateur dans la BDD
	 * @throws SQLException
	 */
	public static void ajout_BDD_admin() throws SQLException{

		// Entrée manuelles des valeurs des variables
		Administrateur admin = new Administrateur();        		// création d'un objet Administrateur


		System.out.println("Entrez le nom, le prénom et "
				+ "le mot de passe de l'administrateur que vous voulez créer");

		//Test pour savoir si l'administrateur est déjà présent dans la BDD
		String Nom = "";
		String Prenom = "";
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("Select id_admin FROM Administrateur");
		boolean invalid = true;
		while(invalid) {
			System.out.print("Nom : ");
			Nom = Connexion.sc.next();
			System.out.print("Prénom : ");
			Prenom = Connexion.sc.next();
			rs = statement.executeQuery("Select id_admin FROM Administrateur WHERE login"
					+ " = \"" + Prenom.substring(0,1) + Nom + "\"");
			invalid = rs.next();
			if(invalid) {
				System.out.println("L'administrateur que vous voulez"
						+ " ajouter est déjà présent dans la BDD");
			}
		}

		admin.setNom(Connexion.sc.next());
		admin.setPrenom(Connexion.sc.next());
		System.out.print("Mot de passe : ");
		admin.setMdp(Connexion.sc.next());
		admin.setLogin();


		// Détermination de l'id de l'objet
		ArrayList<Integer> listId = new ArrayList<Integer>();						// Création de la liste des id des admins de la table
		rs = statement.executeQuery("select id_admin from Administrateur");		// Récupère les id de la table
		while(rs.next()) {
			listId.add(rs.getInt("id_admin"));
		}

		if(listId.size()==0) {													//Si il n'y a pas d'entrée dans la table,
			admin.setId_admin(1);												//On réalise la première entrée dans la table éboueur (id = 1)
		}
		else {																	//S'il existe déjà des entrées, on incrémente l'id
			admin.setId_admin(listId.get(listId.size()-1)+1);						//pour l'objet que l'on veut ajouter
		}

		// Ajout de l'admin à la BDD
		PreparedStatement ajout = Connexion.getInstance().prepareStatement(
				"INSERT INTO Administrateur(id_admin, login, Nom, Prenom, mdp)"
						+ " VALUES(?, ?, ?, ?, ?)");

		ajout.setInt(1, admin.getId_admin());									//Mise à jour du PreparedStatement
		ajout.setString(2, admin.getLogin());
		ajout.setString(3, admin.getNom());
		ajout.setString(4,  admin.getPrenom());
		ajout.setString(5,  admin.getMdp());
		ajout.executeUpdate();
		System.out.println("Ajout d'un admin effectué :)");

		//Mise à jour de l'historique
		Connexion.log("Ajout", "Administrateur");
	}
}