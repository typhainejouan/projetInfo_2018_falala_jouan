package Statistiques;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import coeur_collector.Connexion;

/**
 * 
 * @author jouanfalala
 *
 */
public class Stat_collecte {
	//Attributs
	/**
	 * Identifiant de la statistique de la collecte
	 */
	private int id_stat_collecte;
	/**
	 * Poids total ramassé en un an
	 */
	private double Poids_total_annuel;
	/**
	 * Poids moyen d'une collecte
	 */
	private double Poids_moyen_collecte;
	/**
	 * Poids moyen d'une collecte de déchets non recyclables
	 */
	private double Poids_moyen_collecte_NR;
	/**
	 * Poids moyen d'une collecte de déchets recyclables
	 */
	private double Poids_moyen_collecte_Rec;
	/**
	 * Temps moyen d'une collecte
	 */
	private double Temps_moyen_collecte;
	
	//Constructeurs
	/**
	 * Constructeur de base
	 */
	public Stat_collecte(){
	}
	
	/**
	 * Constructeur complet de la statistique de la collecte
	 * @param Poids_total_année
	 * @param Poids_moyen_collecte
	 * @param Poids_moyen_collecte_NR
	 * @param Poids_moyen_collecte_Rec
	 * @param Temps_moyen_collecte
	 * @param Longueur_moyenne
	 */
	public Stat_collecte(double Poids_total_année, double Poids_moyen_collecte, double Poids_moyen_collecte_NR, double Poids_moyen_collecte_Rec, double Temps_moyen_collecte,double Longueur_moyenne){
		this.Poids_total_annuel         =   Poids_total_année;
		this.Poids_moyen_collecte       =   Poids_moyen_collecte;
		this.Poids_moyen_collecte_NR    =   Poids_moyen_collecte_NR;
		this.Poids_moyen_collecte_Rec   =   Poids_moyen_collecte_Rec;
		this.Temps_moyen_collecte       =   Temps_moyen_collecte;
	}
	
	//Getters
	/**
	 * Renvoie l'identifiant de la statistique de la collecte
	 * @return id_stat_collecte
	 */
	public int getId_stat_collecte() {
		return id_stat_collecte;
	}
	/**
	 * Renvoie le poids total ramassé en un an 
	 * @return Poids_total_annuel
	 */
	public double getPoids_total_annuel() {
		return Poids_total_annuel;
	}
	/**
	 * Renvoie le Poids moyen ramassé pendant une collecte
	 * @return Poids_moyen_collecte
	 */
	public double getPoids_moyen_collecte() {
		return Poids_moyen_collecte;
	}
	/**
	 * Renvoie le Poids moyen de déchets non recyclables ramassés en une collecte
	 * @return Poids_moyen_collecte_NR
	 */
	public double getPoids_moyen_collecte_NR() {
		return Poids_moyen_collecte_NR;
	}
	/**
	 * Renvoie le Poids moyen de déchets recyclables ramassés en une collecte
	 * @return Poids_moyen_collecte_Rec
	 */
	public double getPoids_moyen_collecte_Rec() {
		return Poids_moyen_collecte_Rec;
	}
	/**
	 * Renvoie la durée moyenne d'une collecte
	 * @return Temps_moyen_collecte
	 */
	public double getTemps_moyen_collecte() {
		return Temps_moyen_collecte;
	}

	
	//Setter Id_stat_collecte
	/**
	 * Mutateur de l'identifiant de la statistique de collecte
	 * @param id_stat_collecte
	 */
	public void setId_stat_collecte(int id_stat_collecte) {
		this.id_stat_collecte = id_stat_collecte;
	}
	
	//Setter Poids_total_annuel
	/**
	 * Mutateur du Poids total de déchets ramassé en une année
	 * @throws SQLException
	 */
	public void setPoids_total_annuel() throws SQLException{
		String date = Connexion.date_annee_ant();											//Récupération de la date de l'année précédente
		
		//Requête directe pour récupérer le Poids_total_annuel de déchets
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT SUM(Poids_dechet) as sum FROM Collecte "
				+ "WHERE date(date_debut) > date("+ date + ")");
		rs.next();
		double Poids = rs.getDouble("sum");
		this.Poids_total_annuel = Poids;
	}
	
	//Setter de Poids_moyen_collecte
	/**
	 * Mutateur du Poids moyen ramassé pendant une collecte
	 * @throws SQLException
	 */
	public void setPoids_moyen_collecte() throws SQLException{
		//Requête directe pour récupérer le Poids_moyen_collecte de déchets
		Statement statement = Connexion.getInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT AVG(Poids_dechet) as avg FROM Collecte");
		double Poids = rs.getDouble("avg");
		this.Poids_moyen_collecte = Poids;
	}
	
	//Setter Poids_moyen_collecte_Rec
	/**
	 * Mutateur du poids moyen d'une collecte en déchets recyclables
	 * @throws SQLException
	 */
	public void setPoids_moyen_collecte_Rec() throws SQLException{
		PreparedStatement ajout = Connexion.getInstance().prepareStatement("SELECT AVG(Poids_dechet) as avg FROM Collecte WHERE type_dechet = 'Rec'");
		ResultSet resultat = ajout.executeQuery();
		double Poids = resultat.getDouble("avg");
		this.Poids_moyen_collecte_Rec = Poids;
	}
	
	//Setter Poids_moyen_collecte_NR
	/**
	 * Mutateur du poids moyen d'une collecte en déchets non recyclables
	 * @throws SQLException
	 */
	public void setPoids_moyen_collecte_NR() throws SQLException{
		PreparedStatement ajout = Connexion.getInstance().prepareStatement("SELECT AVG(Poids_dechet) as avg FROM Collecte WHERE type_dechet = 'NR'");
		ResultSet resultat = ajout.executeQuery();
		double Poids = resultat.getDouble("avg");
		this.Poids_moyen_collecte_NR = Poids;
	}
	
	
	//Setter Temps_moyen
	/**
	 * Mutateur du temps moyen d'une collecte
	 * @throws SQLException
	 */
	public void setTemps_moyen() throws SQLException{
		PreparedStatement ajout = Connexion.getInstance().prepareStatement("SELECT AVG(duree) as duree FROM Collecte");
		ResultSet resultat = ajout.executeQuery();
		double duree_moy = resultat.getDouble("duree");
		this.Poids_moyen_collecte_NR = duree_moy;
	}
	
	//Méthode qui ajoute une ligne de statistique dans la BDD
	/**
	 * Ajoute une ligne de stat dans la Base de Données
	 * @throws SQLException
	 */
	public static void ajout_BDD_stat_collecte() throws SQLException{
		Stat_collecte stat_coll = new Stat_collecte();
		
		Statement statement = Connexion.getInstance().createStatement();
		
		// Détermination de l'id de l'objet
		ArrayList<Integer> listId = new ArrayList<Integer>();								        // Création de la liste des id
		ResultSet rs = statement.executeQuery("select id_stat_collecte from Stat_collecte");		// Récupère les id de la table
		while(rs.next()) {
			listId.add(rs.getInt("id_stat_collecte"));
			}
			if(listId.size()==0) {															    // Si il n'y a pas d'entrée dans la table,
				stat_coll.setId_stat_collecte(1);											    // On réalise la première entrée dans la table éboueur (id = 1)
			}
			else {																			    // S'il existe déjà des entrées, on incrémente l'id pour l'objet que l'on veut ajouter
				stat_coll.setId_stat_collecte(listId.get(listId.size()-1)+1); 	
			}
		
		PreparedStatement ajout = Connexion.getInstance().prepareStatement("INSERT INTO"
				+ " Stat_collecte(id_stat_collecte, Poids_total_annuel, Poids_moyen_collecte,"
				+ " Poids_moyen_collecte_NR, Poids_moyen_collecte_Rec, Temps_moyen_collecte)"
				+ " VALUES(?, ?, ?, ?, ?, ?)");
		
		ajout.setInt(1, stat_coll.getId_stat_collecte());
		ajout.setDouble(2, stat_coll.getPoids_total_annuel());
		ajout.setDouble(3, stat_coll.getPoids_moyen_collecte());
		ajout.setDouble(4,  stat_coll.getPoids_moyen_collecte_NR());
		ajout.setDouble(5,  stat_coll.getPoids_moyen_collecte_Rec());
		ajout.setDouble(6, stat_coll.getTemps_moyen_collecte());
		ajout.execute();
		System.out.println("Ajout effectué :)");
		
		//Mise à jour de l'historique
		Connexion.log("Ajout", "Stat_Collecte");
	}
}
