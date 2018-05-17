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
 * Classe qui renvoie les statistiques sur les éboueurs
 * Les informations sont remises à jour quand l'administrateur le demande,
 * et on peut accéder au salaire et au temps de travail mensuel de l'éboueur choisi
 * @author jouanfalala
 *
 */
public class Stat_eboueur {
	/**
	 * Clé primaire indentifiant l'éboueur
	 */
	private int id_eboueur;
	/**
	 * Qualifie le temps de travail mensuel de l'éboueur
	 */
	private double Temps_travail_mensuel;
	/**
	 * Qualifie le salaire de l'éboueur
	 */
	private double Salaire;
	
	//Constructeur
	/**
	 * Constructeur de base de la Stat_eboueur
	 */
	public Stat_eboueur(){
	}
	
	/**
	 * Constructeur de la statistique de l'éboueur à l'aide de l'identifiant éboueur
	 * @param id_eboueur
	 */
	public Stat_eboueur(int id_eboueur){
		this.id_eboueur = id_eboueur;
		try {
			this.setTemps_travail_mensuel(id_eboueur);
			this.setSalaire();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructeur explicite de Stat_eboueur
	 * @param id_eboueur
	 * @param Temps_travail_mensuel
	 * @param Salaire
	 */
	public Stat_eboueur(int id_eboueur, double Temps_travail_mensuel, double Salaire){
		this.id_eboueur            = id_eboueur;
		this.Temps_travail_mensuel = Temps_travail_mensuel;
		this.Salaire               = Salaire;
	}
	
	//Getters
	/**
	 * Renvoie l'identifiant de l'éboueur
	 * @return id_eboueur
	 */
	public int getId_eboueur() {
		return id_eboueur;
	}
	
	/**
	 * Renvoie le temps de travail mensuel de l'éboueur
	 * @return Temps_travail_mensuel
	 */
	public double getTemps_travail_mensuel() {
		return Temps_travail_mensuel;
	}
	
	/**
	 * Renvoie le salaire de l'éboueur
	 * @return Salaire
	 */
	public double getSalaire() {
		return Salaire;
	}
	
	//Setters
	/**
	 * Modifie l'identifiant de l'éboueur
	 * @param id_eboueur
	 */
	public void setId_eboueur(int id_eboueur) {
		this.id_eboueur = id_eboueur;
	}
	/**
	 * Modifie le temps de travail mensuel de l'éboueur
	 * @param id_eboueur
	 * @throws SQLException
	 */
	public void setTemps_travail_mensuel(int id_eboueur) throws SQLException{
		String mois = Connexion.date("mois");
		String annee = Connexion.date("annee");
		String date_d = annee + "-" + mois + "O1";
		String date_f = annee + "-" + mois + "31";
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT id_equipe FROM Eboueur WHERE id_eboueur = " + id_eboueur);
		rs.next();
		int id_equipez = rs.getInt("id_equipe");
		rs = statement.executeQuery("SELECT SUM(duree) as sum from Collecte WHERE id_equipe = "				
		+ id_equipez + " AND date_debut >= date(" +"\"" + date_d +"\""+ ") AND date_debut <= date(" +"\"" + date_f +"\"" + ")");
		double temps = rs.getDouble("sum");
		this.Temps_travail_mensuel = temps;
	}
	/**
	 * Modifie le salaire de l'éboueur à l'aide du temps de travail mensuel
	 */
	public void setSalaire(){
		double Prime_penibilite = 300;									//Chaque éboueur a droit à une prime de pénibilité du travail à hauteur de 300€
		double Salaire_Base = this.Temps_travail_mensuel*0.5 ;			//Le salaire de base est de 30€ de l'heure 
		this.Salaire = Salaire_Base + Prime_penibilite;
	}
	
	/**
	 * Actualise la statistique de l'éboueur dans la BDD
	 * @throws SQLException
	 */
	//Méthode d'actualisation de la stat de l'éboueur à la Base de Données
	public static void modif_stat_ebou_BDD() throws SQLException{
		Stat_eboueur stat_ebou_modif = new Stat_eboueur();											//Création de l'objet stat_eboueur
		
		//Recherche de la stat eboueur à modifier avec la méthode Administrateur.recherche()
		System.out.println("Recherchez la stat eboueur que vous voulez modifier");
		List<ArrayList<Object>> recherche = Administrateur.recherche();
				
		int id_ebou_modif = (int) recherche.get(0).get(0);									        //Récupération de l'identifiant de l'éboueur
		
		PreparedStatement modif = Connexion.getInstance().prepareStatement("UPDATE Stat_eboueur SET"
				+ " id_eboueur = ? , Temps_travail_mensuel = ?, Salaire = ?"
				+ " WHERE id_eboueur = " + id_ebou_modif);
		
		
		//Modification des valeurs de la BDD pour chaque variable
		stat_ebou_modif.setId_eboueur(id_ebou_modif);
		stat_ebou_modif.setTemps_travail_mensuel(id_ebou_modif);
		stat_ebou_modif.setSalaire();
		
		modif.setInt(1, id_ebou_modif);
		modif.setDouble(2, stat_ebou_modif.getTemps_travail_mensuel());
		modif.setDouble(3, stat_ebou_modif.getSalaire());
		modif.execute();
		System.out.println("Modification de la statistique effectuée :)");
		
		//Mise à jour de l'historique
		Connexion.log("Modification", "Stat_Eboueur");
	}
}
