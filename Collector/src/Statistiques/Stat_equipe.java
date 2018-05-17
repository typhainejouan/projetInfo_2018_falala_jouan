package Statistiques;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import coeur_collector.Administrateur;
import coeur_collector.Connexion;

/**
 * Classe Stat_Equipe qui permet de générer différentes statistiques sur les équipes
 * Elle en génère trois qui sont mensuels: Le temps de travail, le poids de déchet ramassé et le nombre de km parcourus
 * Pour les générer on se connecte à la Base de Données pour y faire les recherches nécéssaires
 * 
 * @author jouanfalala
 *
 */

public class Stat_equipe {
	/**
	 * Représente la clé primaire de la table Equipe
	 */
	private int id_equipe;
	/**
	 * Statistique du temps de travail par mois de l'équipe
	 */
	private double Temps_travail_mensuel;
	/**
	 * Statistique du poids de déchet ramassé en un mois par l'équipe
	 */
	private double Poids_mensuel;
	/**
	 * Statistique du nombre de km parcourus en un mois par l'équipe
	 */
	private double km_parcourus_mensuel;
	
	
	//Constructeurs
	/**
	 * Constructeur de base de la classe Stat_equipe
	 * 
	 */
	public Stat_equipe(){
	}
	
	/**
	 * Constructeur particulier de la classe Stat_equipe
	 * 
	 * @param id_equipe
	 * @param mois
	 */
	public Stat_equipe(int id_equipe, String annee_mois){
		this.id_equipe = id_equipe;
		try {
			this.setTemps_travail_mensuel(id_equipe, annee_mois);        //Création de la statistique Temps_travail_mensuel grâce une recherche SQL
			this.setPoids_mensuel(id_equipe, annee_mois);				//Création de la statistique Poids_mensuel grâce à une recherche SQL
			this.setKm_parcourus_mensuel(id_equipe, annee_mois);			//Création de la statistique Km_parcourus_mensuel grâce à uen recherche SQL
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructeur explicite de la classe Stat_equipe
	 * @param id_equipe
	 * @param Temps_travail_mensuel
	 * @param Poids_mensuel
	 * @param km_parcourus_mensuel
	 */
	public Stat_equipe(int id_equipe, double Temps_travail_mensuel, double Poids_mensuel, double km_parcourus_mensuel){
		this.id_equipe               = id_equipe;
		this.Temps_travail_mensuel   = Temps_travail_mensuel;
		this.Poids_mensuel           = Poids_mensuel;
		this.km_parcourus_mensuel    = km_parcourus_mensuel;
	}
	
	//Getters
	/**
	 * Accesseur de l'identifiant de l'équipe
	 * @return id_equipe
	 */
	public int getId_equipe() {
		return id_equipe;
	}
	
	/**
	 * Accesseur de la statistique temps de travail mensuel
	 * @return Temps_travail_mensuel
	 */
	public double getTemps_travail_mensuel() {
		return Temps_travail_mensuel;
	}
	
	/**
	 * Accesseur de la statistique de poids ramassé par mois
	 * @returnPoids_mensuel
	 */
	public double getPoids_mensuel() {
		return Poids_mensuel;
	}
	
	/**
	 * Accesseur de la statistique sur le nombre de km parcourus par mois
	 * @return km_parcourus_mensuel
	 */
	public double getKm_parcourus_mensuel() {
		return km_parcourus_mensuel;
	}
	
	//Setters
	/**
	 * Mutateur de l'id_equipe à partir de l'id rentré
	 * @param id_equipe
	 */
	public void setId_equipe(int id_equipe){
		this.id_equipe = id_equipe;
	}
	
	/**
	 * Mutateur de la statistique de temps de travail mensuel de l'équipe à partir de l'id_equipe et le mois
	 * Il utilise un appel à la BDD pour faire une somme des temps travaillés par jours par l'équipe
	 * @param id_equipe
	 * @throws SQLException
	 */
	public void setTemps_travail_mensuel(int id_equipe, String annee_mois) throws SQLException {
		//Récupération des dates de début et de fin de mois
		String date_deb = Connexion.date_deb_mois_parti(annee_mois);
		String date_fin = Connexion.date_fin_mois_parti(annee_mois);
		
		//Récupération de la requête SQL pour le temps de travail mensuel de l'équipe
		Statement statement = Connexion.getInstance().createStatement();											//Création du statement de la BDD
		ResultSet rs = statement.executeQuery("SELECT SUM(duree) as sum FROM Collecte WHERE id_equipe = "			//Requête SQL
		+ id_equipe + " AND date_debut >= date('" + date_deb + "') AND date_debut <= date('" + date_fin + "')" ); 
		rs.next();
		double temps = rs.getDouble("sum");																		//Récupération de la statistique
		this.Temps_travail_mensuel = temps;
	}
	
	/**
	 * Mutateur de la statistique du Poids rammassé en un mois par une équipe
	 * Utilise l'id_equipe et le mois pour faire la recherche SQL associée
	 * @param id_equipe
	 * @throws SQLException
	 */
	public void setPoids_mensuel(int id_equipe, String annee_mois) throws SQLException {
		//Récupération des dates de début et de fin de mois
		String date_deb = Connexion.date_deb_mois_parti(annee_mois);
		String date_fin = Connexion.date_fin_mois_parti(annee_mois);
		
		//Récupération de la requête SQL pour le poids ramassé en un mois pour l'équipe
		Statement statement = Connexion.getInstance().createStatement();											//Création du statement de la BDD
		ResultSet rs = statement.executeQuery("SELECT SUM(duree) as sum FROM Collecte "							
				+ "WHERE id_equipe = " + id_equipe + " AND date_debut >= date(" + date_deb +
				") AND date_debut <= date(" + "\"" + date_fin + "\""+ ")");
		rs.next();
		double poids_mensuel = rs.getDouble("sum");																//Récupération de la statistique
		this.Poids_mensuel = poids_mensuel;
	}
	
	/**
	 * Mutateur de la statistique du nombre de km parcourus par mois par l'équipe
	 * Utilise l'id_equipe et le mois pour faire la recherche SQL associée
	 * @param id_equipe
	 * @throws SQLException
	 */
	public void setKm_parcourus_mensuel(int id_equipe, String mois) throws SQLException{
		//Récupération des dates de début et de fin de mois
		String date_deb = Connexion.date_deb_mois_parti(mois);
		String date_fin = Connexion.date_fin_mois_parti(mois);
		
		//Récupération de la requête SQL pour le nombre de km parcourus ce mois-ci par l'équipe
		Statement statement = Connexion.getInstance().createStatement();											//Création du statement de la BDD
		ResultSet rs = statement.executeQuery("SELECT SUM(distance) as sum FROM Trajet "
				+ "JOIN Collecte ON Trajet.id_trajet = Collecte.id_trajet "
				+ "WHERE Collecte.id_equipe = " + id_equipe
				+ " AND Collecte.date_debut >= date("+ "\"" + date_deb + "\"" + ")"
				+ " AND Collecte.date_fin <= date(" + "\"" + date_fin + "\"" + ")"); 
		rs.next();
		double Km_parcourus_mensuel = rs.getDouble("sum");														//Récupération de la statistique
		this.km_parcourus_mensuel = Km_parcourus_mensuel;
	}
	
	
	//Méthode qui ajoute la statistique mensuelle de l'équipe
	/**
	 * Ajout de la statistique mensuelle de l'équipe si elle n'existe pas encore
	 * @param id_equipe
	 * @param mois
	 * @throws SQLException
	 */
	public static void ajout_stat_equipe(int id_equipe, String annee_mois) throws SQLException{
		Stat_equipe stat = new Stat_equipe(id_equipe, annee_mois);
		
		//Recherche de la collecte à modifier avec la méthode Administrateur.recherche()
		System.out.println("Recherchez la statistique que vous voulez voir");
		List<ArrayList<Object>> recherche = Administrateur.recherche();	
							
		//Test d'existence de la statistique
		if(recherche.isEmpty()){																					//Si il n'y a pas de ligne de statistique correspondant, on la créé
			PreparedStatement ajout = Connexion.getInstance().prepareStatement("INSERT INTO"						//Requete préparée pour rentrer la statistique
					+ " Stat_equipe(id_equipe, Temps_travail_mensuel, Poids_mensuel,"
					+ " km_parcourus_mensuel, annee_mois)"
					+ " VALUES(?, ?, ?, ?, ?)");
			
			ajout.setInt(1, id_equipe);
			ajout.setDouble(2, stat.getTemps_travail_mensuel());
			ajout.setDouble(3, stat.getPoids_mensuel());
			ajout.setDouble(4,  stat.getKm_parcourus_mensuel());
			ajout.setString(5,  annee_mois);
			ajout.execute();
			System.out.println("La statistique a été ajoutée :)");
		}
		else{
			System.out.println("Votre statistique se trouve ci_dessus!");
		}
	}
}
