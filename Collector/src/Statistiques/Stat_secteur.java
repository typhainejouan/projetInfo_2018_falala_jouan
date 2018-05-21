package Statistiques;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import coeur_collector.Connexion;

/**
 * Classe qui calcule les statistiques du secteur
 * @author jouanfalala
 *
 */
public class Stat_secteur {
	/**
	 * Clé primaire qui identifie le secteur
	 */
	private String id_secteur;
	/**
	 * Population du secteur
	 */
	private int Population;
	/**
	 * Surface du secteur
	 */
	private double Surface;
	/**
	 * Densité de population du secteur
	 */
	private double Densite;
	/**
	 * Poids des déchets non recyclables ramassés en un an dans le secteur
	 */
	private double Poids_NR_secteur_annuel;
	/**
	 * Poids des déchets recyclables ramassés en un an dans le secteur
	 */
	private double Poids_Rec_secteur_annuel;
	/**
	 * Poids des déchets ramassés en un an dans le secteur
	 */
	private double Poids_total_secteur_annuel;
	/**
	 * Poids moyen des déchets ramassés en un an dans le secteur
	 */
	private double Poids_moyen_hab_annuel;
	/**
	 * Temps moyen de la collecte dans un secteur
	 */
	private double Temps_moyen_secteur;
	
	//Constructeur
	/**
	 * Constructeur de base
	 */
	public Stat_secteur(){
	}
	
	/**
	 * Constructeur d'une statistique de secteur
	 * @param id_secteur
	 */
	public Stat_secteur(String id_secteur) {
		this.id_secteur = id_secteur;
		try {
			this.setPopulation(id_secteur);
			this.setSurface(id_secteur);
			this.setDensite(id_secteur);
			this.setPoids_annee_zone_NR(id_secteur);
			this.setPoids_annee_zone_Rec(id_secteur);
			this.setPoids_total_annee_zone(id_secteur);
			this.setPoids_moyen_hab_annuel(id_secteur);
			this.setTemps_moyen_zone(id_secteur);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructeur explicite
	 * @param id_secteur
	 * @param Population
	 * @param Surface
	 * @param Densite
	 * @param Poids_annee_zone_NR
	 * @param Poids_annee_zone_Rec
	 * @param Poids_total_annee_zone
	 * @param Poids_moyen_hab_annuel
	 * @param Temps_moyen_zone
	 */
	public Stat_secteur(String id_secteur, int Population, double Surface, double Densite, double Poids_annee_zone_NR, double Poids_annee_zone_Rec, double Poids_total_annee_zone, double Poids_moyen_hab_annuel, double Temps_moyen_zone){
		this.id_secteur                  = id_secteur;
		this.Population                  = Population;
		this.Surface                     = Surface;
		this.Densite                     = Densite;
		this.Poids_NR_secteur_annuel     = Poids_annee_zone_NR;
		this.Poids_Rec_secteur_annuel    = Poids_annee_zone_Rec;
		this.Poids_moyen_hab_annuel      = Poids_moyen_hab_annuel;
		this.Poids_total_secteur_annuel  = Poids_total_annee_zone;
		this.Poids_moyen_hab_annuel      = Poids_moyen_hab_annuel;
		this.Temps_moyen_secteur         = Temps_moyen_zone;
	}
	//Accesseur
	/**
	 * Donne l'id_secteur
	 * @return id_secteur
	 */
	public String getId_secteur() {
		return id_secteur;
	}
	/**
	 * Renvoie la population du secteur
	 * @return Population
	 */
	public int getPopulation() {
		return Population;
	}
	/**
	 * Renvoie la surface du secteur
	 * @return Surface
	 */
	public double getSurface() {
		return Surface;
	}
	/**
	 * Renvoie la densité du secteur
	 * @return Densite
	 */
	public double getDensite() {
		return Densite;
	}
	/**
	 * Renvoie le Poids_NR_secteur_annuel
	 * @return Poids_NR_secteur_annuel
	 */
	public double getPoids_NR_secteur_annuel() {
		return Poids_NR_secteur_annuel;
	}
	/**
	 * Renvoie Poids_Rec_secteur_annuel
	 * @return Poids_Rec_secteur_annuel
	 */
	public double getPoids_Rec_secteur_annuel() {
		return Poids_Rec_secteur_annuel;
	}
	/**
	 * Renvoie Poids_total_secteur_annuel
	 * @return Poids_total_secteur_annuel
	 */
	public double getPoids_total_secteur_annuel() {
		return Poids_total_secteur_annuel;
	}
	/**
	 * Renvoie Poids_moyen_hab_annuel
	 * @return Poids_moyen_hab_annuel
	 */
	public double getPoids_moyen_hab_annuel() {
		return Poids_moyen_hab_annuel;
	}
	/**
	 * Temps_moyen_secteur
	 * @return Temps_moyen_secteur
	 */
	public double getTemps_moyen_secteur() {
		return Temps_moyen_secteur;
	}
	

	//Mutateur
	/**
	 * Mutateur de l'id_secteur
	 * @param id_secteur
	 */
	public void setId_secteur(String id_secteur) {
		this.id_secteur = id_secteur;
	}
	/**
	 * Mutateur de la population
	 * @param id_secteur
	 * @throws SQLException
	 */
	public void setPopulation(String id_secteur) throws SQLException{
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT Population from Secteur WHERE id_secteur = " + "\"" + id_secteur + "\"");
		rs.next();
		int pop = rs.getInt("Population");
		this.Population = pop;
		
	}
	/**
	 * Mutateur de la surface
	 * @param id_secteur
	 * @throws SQLException
	 */
	public void setSurface(String id_secteur) throws SQLException {
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT Surface FROM Secteur WHERE id_secteur = " + "\"" + id_secteur + "\"");
		rs.next();
		double surf = rs.getDouble("Surface");
		this.Surface = surf;
	}
	/**
	 * Mutateur de la densite
	 * @param id_secteur
	 */
	public void setDensite(String id_secteur) {
		this.Densite = this.Population / this.Surface;
	}
	/**
	 * Mutateur du Poids_NR_secteur_annuel
	 * @param id_secteur
	 * @throws SQLException
	 */
	public void setPoids_annee_zone_NR(String id_secteur) throws SQLException{
		String annee = Connexion.date("année");
		int a = Integer.parseInt(annee) - 1;
		String annee_2 = Integer.toString(a);
		String date_moins = "\'" + annee_2 + "-" + Connexion.date("mois_jour") + "\'";
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT SUM(Poids_dechet) as sum FROM Collecte WHERE id_secteur = "
			+ "\""+ id_secteur + "\"" + " AND type_dechet = 'NR' AND date_debut > date("+ date_moins + ")");
		rs.next();
		double Poids = rs.getDouble("sum");
		this.Poids_NR_secteur_annuel = Poids;
	}
	/**
	 * Poids_NR_secteur_annuel du Poids_annee_zone_Rec
	 * @param id_secteur
	 * @throws SQLException
	 */
	public void setPoids_annee_zone_Rec(String id_secteur) throws SQLException{
		String annee = Connexion.date("année");
		int a = Integer.parseInt(annee) - 1;
		String annee_2 = Integer.toString(a);
		String date_moins = "\'" + annee_2 + "-" + Connexion.date("mois_jour") + "\'";
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT SUM(Poids_dechet) as sum FROM Collecte WHERE id_secteur = "
		+ "\""+ id_secteur + "\""+ " AND type_dechet = 'Rec' AND date_debut > date("+ date_moins + ")");
		rs.next();
		double Poids = rs.getDouble("sum");
		this.Poids_NR_secteur_annuel = Poids;
	}
	/**
	 * Mutateur de Poids_total_secteur_annue
	 * @param id_secteur
	 */
	public void setPoids_total_annee_zone(String id_secteur) {
		this.Poids_total_secteur_annuel = this.Poids_NR_secteur_annuel + this.Poids_Rec_secteur_annuel;
	}
	/**
	 * Mutateur du Poids_moyen_hab_annuel
	 * @param id_secteur
	 * @throws SQLException
	 */
	public void setPoids_moyen_hab_annuel(String id_secteur) throws SQLException {
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT Population FROM Secteur WHERE id_secteur = " + "\""+ id_secteur+ "\"");
		rs.next();
		int pop = rs.getInt("Population");
		this.Poids_moyen_hab_annuel = this.Poids_total_secteur_annuel/pop;
	}
	/**
	 * Mutateur de Temps_moyen_zone
	 * @param id_secteur
	 * @throws SQLException
	 */
	public void setTemps_moyen_zone(String id_secteur) throws SQLException{
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT AVG(duree) as moyenne FROM Collecte WHERE id_secteur = " + "\""+ id_secteur+ "\"");
		rs.next();
		double avg = rs.getDouble("moyenne");
		this.Temps_moyen_secteur = avg;
	}
	
	/**
	 * Ajoute une statistique sur le secteur 
	 * @param id_secteur
	 * @throws SQLException
	 */
	public static void ajout_stat_secteur(String id_secteur) throws SQLException{
		Stat_secteur stat = new Stat_secteur(id_secteur);
		
		PreparedStatement ajout = Connexion.getInstance().prepareStatement("UPDATE"
				+ " Stat_secteur SET Population = ?, Surface = ?, Densite = ?, Poids_NR_secteur_annuel = ?,"
				+ " Poids_Rec_secteur_annuel = ?, Poids_total_secteur_annuel = ?, Poids_moyen_hab_annuel = ?, Temps_moyen_secteur = ?"
				+ " WHERE id_secteur = " + "\""  +id_secteur + "\"");
		
		ajout.setInt(1, stat.getPopulation());
		ajout.setDouble(2, stat.getSurface());
		ajout.setDouble(3, stat.getDensite());
		ajout.setDouble(4, stat.getPoids_NR_secteur_annuel());
		ajout.setDouble(5, stat.getPoids_Rec_secteur_annuel());
		ajout.setDouble(6,  stat.getPoids_total_secteur_annuel());
		ajout.setDouble(7,  stat.getPoids_moyen_hab_annuel());
		ajout.setDouble(8,  stat.getTemps_moyen_secteur());
		ajout.execute();
		System.out.println("Ajout effectué :)");
		
		//Mise à jour de l'historique
		Connexion.log("Ajout", "Stat_Secteur");
	}	
}