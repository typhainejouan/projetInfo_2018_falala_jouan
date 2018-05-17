package coeur_collector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Equipe, cette classe permet de créer une équipe
 * @author jouanfalala
 *
 */
public class Equipe {
	//Attributs
	/**
	 * Attribut correspondant à l'id_equipe dans la BDD
	 */
	private int id_equipe;
	/**
	 * Attribut correspondant aux horaires de travail de l'équipe
	 */
	private String horaires;
	/**
	 * Attribut correspondant à l'id_secteur dans la BDD
	 */
	private String id_secteur;
	/**
	 * Attribut correspondant au nombre d'éboueur dans l'équipe
	 */
	private int nb_eboueur;

	//Constructeurs
	/**
	 * Constructeur de la classe Equipe vide
	 */
	public Equipe(){
	}
	/**
	 * Constructeur de la classe Equipe
	 * @param id_equipe
	 * @param horaires
	 * @param id_secteur
	 * @param nombre_eboueur
	 */
	public Equipe(int id_equipe, String horaires, String id_secteur, int nombre_eboueur){
		this.id_equipe      = id_equipe;
		this.horaires       = horaires;
		this.id_secteur     = id_secteur;
		this.nb_eboueur = nombre_eboueur;
	}

	// Accesseurs
	/**
	 * Accesseur de l'attribut id_equipe
	 * @return
	 */
	public int getId_equipe() {
		return id_equipe;
	}
	/**
	 * Accesseur de l'attribut horaires
	 * @return
	 */
	public String getHoraires() {
		return horaires;
	}
	/**
	 * Accesseur de l'attribut id_secteur
	 * @return
	 */
	public String getId_secteur() {
		return id_secteur;
	}
	/**
	 * Accesseur de l'attribut nb_eboueur
	 * @return
	 */
	public int getNb_eboueur() {
		return nb_eboueur;
	}

	//Mutateurs
	/**
	 * Mutateur de l'attribut id_equipe
	 * @param id_equipe
	 */
	public void setId_equipe(int id_equipe) {
		this.id_equipe = id_equipe;
	}
	/**
	 * Mutateur de l'attribut horaires
	 * @param horaires
	 */
	public void setHoraires(String horaires) {
		this.horaires = horaires;
	}
	/**
	 * Mutateur de l'attribut id_secteur
	 * @param id_secteur
	 */
	public void setId_secteur(String id_secteur) {
		this.id_secteur = id_secteur;
	}
	/**
	 * Mutateur de l'attribut nb_eboueur
	 * @param nombre_eboueur
	 */
	public void setNb_eboueur(int nombre_eboueur) {
		this.nb_eboueur = nombre_eboueur;
	}


	//Ajout
	/**
	 * Méthode qui demande à l'admin de créer un objet Equipe
	 * puis l'ajoute dans la base de donnée
	 * @throws SQLException
	 */
	public static void ajout_BDD_equipe() throws SQLException{

		//Entrée manuelles des valeurs des variables
		Equipe equipe = new Equipe();        									//Création de l'objet Equipe

		System.out.println("Entrez l'identifiant secteur, les horaires "
				+ "de travail et le nombre d'éboueurs dans l'équipe");
		System.out.print("Identifiant du secteur : ");
		equipe.setId_secteur(Connexion.sc.next());
		System.out.print("Horaires (matin ou apres_midi) : ");
		equipe.setHoraires(Connexion.sc.next());
		System.out.print("Nombre d'éboueurs : ");
		equipe.setNb_eboueur(Connexion.sc.nextInt());


		Statement statement = Connexion.getInstance().createStatement();

		// Détermination de l'id de l'objet
		ArrayList<Integer> listId = new ArrayList<Integer>();						//Création de la liste des id
		ResultSet rs = statement.executeQuery("select id_equipe from Equipe");	//Récupère les id de la table
		while(rs.next()) {
			listId.add(rs.getInt("id_equipe"));
		}

		if(listId.size()==0) {													//Si il n'y a pas d'entrée dans la table,
			equipe.setId_equipe(1);												//On réalise la première entrée dans la table équipe (id = 1)
		}
		else {									//S'il existe déjà des entrées, on incrémente l'id pour l'objet que l'on veut ajouter
			equipe.setId_equipe(listId.get(listId.size()-1)+1); 	
		}

		// Ajout de l'éboueur à la BDD
		PreparedStatement ajout = Connexion.getInstance().prepareStatement(
				"INSERT INTO Equipe(id_equipe, id_secteur, nb_eboueur)"
						+ " VALUES(?, ?, ?)");

		ajout.setInt(1, equipe.getId_equipe());
		ajout.setString(2, equipe.getId_secteur());
		ajout.setInt(3, equipe.getNb_eboueur());
		ajout.executeUpdate();
		System.out.println("Ajout effectué :)");

		//Mise à jour de l'historique
		Connexion.log("Ajout", "Equipe");
	}	

	//Suppression
	/**
	 * Méthode qui demande à l'admin de rechercher une équipe dans
	 * la table Equipe de la BDD puis supprime cette entrée
	 * @throws SQLException
	 */
	public static void suppr_BDD_equipe() throws SQLException{

		//Recherche de l'équipe à supprimer avec la méthode Administrateur.recherche()
		System.out.println("Recherchez l'équipe que vous voulez supprimer");
		List<ArrayList<Object>> recherche = Administrateur.recherche();					//Résultat de la recherche

		int taille = recherche.size();
		while(taille != 0) {
			int id_equipe_suppr = (int) recherche.get(taille).get(0);

			PreparedStatement suppr = Connexion.getInstance().prepareStatement("DELETE FROM Equipe WHERE id_equipe=" + id_equipe_suppr);
			suppr.execute();
			taille -= 1;
			System.out.println("La suppression a été effectuée :)");

			//Mise à jour de l'historique
			Connexion.log("Suppression", "Equipe");
		}
	}

	//Modification
	/**
	 * Méthode qui demande à l'admin de rechercher une équipe dans
	 * la table Equipe de la BDD, puis d'entrer de nouveaux paramètres
	 * puis modifie cette entrée
	 * @throws SQLException
	 */
	public static void modif_BDD_equipe() throws SQLException{
		Equipe equipe_modif = new Equipe();												//Création de l'objet equipe modif

		//Recherche de l'éboueur à modifier avec la méthode Administrateur.recherche()
		System.out.println("Recherchez l'équipe que vous voulez modifier");
		List<ArrayList<Object>> recherche = Administrateur.recherche();					//Résultat de la recherche

		int id_equipe_modif = (int) recherche.get(0).get(0);

		PreparedStatement modif = Connexion.getInstance().prepareStatement("UPDATE Equipe SET "
				+ "id_secteur = ?, horaires = ?, nb_eboueur = ? WHERE id_equipe = " + id_equipe_modif);	//Requête SQL incomplète pour modifier la BDD

		System.out.println("\n\n\t\tModification des paramètres");						//Instructions pour l'utilisateur
		System.out.println("Pour conserver les paramètres actuels de la BDD, entrez r "
				+ "pour une variable String, entrez 0 pour une variable Integer\n");

		//Modification ou conservation des valeurs de la BDD pour chaque variable
		System.out.print("Horaires (matin ou apres_midi) : ");
		String horaires_modif = Connexion.sc.next();
		if(horaires_modif.equals("r")) {													//Test pour savoir si l'utilisateur
			equipe_modif.setHoraires((String) recherche.get(0).get(1));					//veut modifier ou conserver la valeur
		}																				//de la BDD
		else {
			equipe_modif.setHoraires(horaires_modif);
		}
		System.out.print("Id_secteur : ");
		String id_secteur_modif = Connexion.sc.next();
		id_secteur_modif += Connexion.sc.nextLine();
		if(id_secteur_modif.equals("r")) {
			equipe_modif.setId_secteur((String) recherche.get(0).get(2));
		}
		else {
			equipe_modif.setId_secteur(id_secteur_modif);
		}
		System.out.print("Nombre d'éboueurs : ");
		int nb_eboueur_modif = Connexion.sc.nextInt();
		if(nb_eboueur_modif == 0) {
			equipe_modif.setId_equipe((int) recherche.get(0).get(3));			
		}
		else {
			equipe_modif.setId_equipe(nb_eboueur_modif);
		}

		//Mise à jour du PreparedStatement (requête SQL)
		modif.setString(2, equipe_modif.getHoraires());
		modif.setString(1, equipe_modif.getId_secteur());
		modif.setInt(3, equipe_modif.getNb_eboueur());
		System.out.println("\nModification effectuée :)");
		modif.executeUpdate();

		//Mise à jour de l'historique
		Connexion.log("Modification", "Equipe");
	}
}