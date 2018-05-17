package Carte;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import coeur_collector.Connexion;

/**
 * Classe Secteur héritant de la classe FormeGeometrique
 * cette classe permet de créer un secteur (polygone) sur la carte
 * @author jouanfalala
 *
 */
public class Secteur extends FormeGeometrique{
	
	//Attribut
	/**
	 * Attribut correspondant à l'id_secteur de la BDD
	 */
	private String id_secteur;

	//Constructeur
	/**
	 * Constructeur de la classe Secteur
	 */
	public Secteur(String id_secteur) {
		super();
		this.id_secteur = id_secteur;
		try {
			this.setList_id_points();	//Création de la liste des id_points (méthode ci dessous)
			this.setList_points();		//Création de la liste des points à partir de la liste list_id_points (méthode de la classe mère)
			this.setListe_X();			//Création de la liste des X et des Y à partir de liste list_points (méthode classe mère)
			this.setListe_Y();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	//Accesseur
	/**
	 * Accesseur de l'attribut id_secteur
	 * @return
	 */
	public String getId_secteur() {
		return id_secteur;
	}
	
	//Mutateurs
	/**
	 * Mutateur de l'attribut id_secteur
	 * @param id_secteur
	 */
	public void setId_secteur(String id_secteur) {
		this.id_secteur = id_secteur;
	}

	/**
	 * Mutateur de l'attribut List_id_points par recherche dans la BDD
	 * Condition de la requête : id_secteur
	 * @throws SQLException
	 */
	public void setList_id_points() throws SQLException{

		Statement statement = Connexion.getInstance().createStatement();
		ArrayList<Integer> listId = new ArrayList<Integer>();					//Création de la liste listId
		ResultSet rs = statement.executeQuery("select Liste_id_points"		//Requête SQL pour rechercher la Liste_id_points dans la BDD
				+ " from Secteur WHERE id_secteur = " + this.id_secteur);		//Condition de la requête : id_secteur
		ArrayList<String> list_id_str = new ArrayList<String>();
		list_id_str.add("");
		while(rs.next()) {
			list_id_str.set(0, rs.getString("Liste_id_points"));				//Le résultat de la requête est de type String
		}
		String[] part = list_id_str.get(0).split(", ");						//On split la chaîne de caractère
		for(int i=0; i<=part.length-1; i++) {								//Transformation des caractères en entier et
			listId.add(Integer.parseInt(part[i]));							//remplissage de la liste listId
		}
		this.setList_id_points(listId);										//Utilisation du mutateur de la classe mère
	}
}