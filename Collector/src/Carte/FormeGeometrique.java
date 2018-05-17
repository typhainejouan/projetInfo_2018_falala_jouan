package Carte;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import coeur_collector.Connexion;

/**
 * Classe abstraite FormeGeometrique, classe mère des classe Decharge, Secteur et Trajet
 * Cette classe permet de faire le dessin de la carte
 * @author jouanfalala
 *
 */
public abstract class FormeGeometrique {
	
	//Attributs
	/**
	 * Attribut stockant les id_points, cet attribut est présent dans la BDD
	 */
	private ArrayList<Integer> list_id_points;
	/**
	 * Attribut stockant les coordonnées X,Y des points
	 */
	private ArrayList<ArrayList<Integer>> list_points;
	/**
	 * Attribut stockant les coordonnées X des points
	 */
	private ArrayList<Integer> liste_X;
	/**
	 * Attribut stockant les coordonnées Y des points
	 */
	private ArrayList<Integer> liste_Y;

	//Constructeur
	/**
	 * Constructeur de la classe FormeGeometrique
	 * 
	 */
	public FormeGeometrique() {
		this.list_id_points = new ArrayList<Integer>();
		this.list_points = new ArrayList<ArrayList<Integer>>();
	}


	//Accesseurs
	/**
	 * Accesseur de l'attribut list_id_points 
	 * @return
	 */
	public ArrayList<Integer> getList_id_points() {
		return this.list_id_points;
	}
	/**
	 * Accesseur de l'attribut list_points 
	 * @return
	 */
	public ArrayList<ArrayList<Integer>> getList_points() {
		return list_points;
	}
	/**
	 * Accesseur de l'attribut list_X 
	 * @return
	 */
	public ArrayList<Integer> getListe_X() {
		return this.liste_X;
	}
	/**
	 * Accesseur de l'attribut list_Y
	 * @return
	 */
	public ArrayList<Integer> getListe_Y() {
		return this.liste_Y;
	}


	//Mutateurs
	/**
	 * Mutateur de l'attribut list_id_points
	 * @param list_id_points
	 */
	public void setList_id_points(ArrayList<Integer> list_id_points) {
		this.list_id_points = list_id_points;
	}

	/**
	 * Mutateur de l'attribut list_points par recherche dans la BDD
	 * C'est une liste de listes de coordonnées de points
	 * @throws SQLException
	 */
	public void setList_points() throws SQLException {
		ArrayList<ArrayList<Integer>> list_pts = new ArrayList<ArrayList<Integer>>(); //Création de l'objet list_pts
		Statement statement = Connexion.getInstance().createStatement();
		for(int i : this.list_id_points) {											//On parcours la liste des id_points pour faire des requêtes SQL
			ResultSet rs = statement.executeQuery("select X, Y from Points"			//Requête SQL pour obtenir les coordonnées des points
					+ " WHERE id_point= " + i);
			while(rs.next()) {														//Remplissage de la liste list_pts
				ArrayList<Integer> ls = new ArrayList<Integer>();							
				ls.add(rs.getInt("X"));
				ls.add(rs.getInt("Y"));
				list_pts.add(ls);
			}
		}
		this.list_points = list_pts;													//Mutation de l'attribut list_points
	}
	
	/**
	 * Mutateurs de l'attribut liste_X par lecture de l'attribut list_points
	 */
	public void setListe_X(){
		ArrayList<Integer> list_X = new ArrayList<Integer>();
		for(ArrayList<Integer> l : this.list_points) {
			list_X.add(l.get(0));
		}
		this.liste_X = list_X;
	}
	/**
	 * Mutateurs de l'attribut liste_Y par lecture de l'attribut list_points
	 */
	public void setListe_Y(){
		ArrayList<Integer> list_Y = new ArrayList<Integer>();
		for(ArrayList<Integer> l : this.list_points) {
			list_Y.add(l.get(1));
		}
		this.liste_Y = list_Y;
	}
}
