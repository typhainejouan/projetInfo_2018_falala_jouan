package Carte;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JPanel;

import coeur_collector.Connexion;

/**
 * Classe permettant le dessin d'une carte montrant les trajets d'une équipe
 * pour aujourd'hui ou pour une date donnée
 * @author jouanfalala
 *
 */
public class Dessin_equipe extends JPanel{
	
	//Attributs
	/**
	 * Attribut correspondant à l'id_equipe de la BDD
	 */
	private int id_equipe;
	/**
	 * Attribut correspondant à la date de la collecte dans la BDD
	 */
	private String date;

	/**
	 * Constructeur de la carte à une date donnée
	 * @param id_equipe
	 * @param date
	 */
	public Dessin_equipe(int id_equipe, String date) {
		this.id_equipe = id_equipe;
		this.date = date;
	}

	//Constructeur
	/**
	 * Constructeur de la carte à la date d'aujourd'hui
	 * @param id_equipe
	 */
	public Dessin_equipe(int id_equipe) {
		this.id_equipe = id_equipe;
		this.date = Connexion.date("partiel");		//Récupération de la date d'aujourd'hui
	}

	//Accesseurs
	/**
	 * Accesseur de l'attribut id_equipe
	 * @return
	 */
	public int getId_equipe() {
		return id_equipe;
	}
	/**
	 * Accesseur de l'attribut date
	 * @return
	 */
	public String getDate() {
		return date;
	}

	//Méthodes
	
	/**
	 * Méthode qui va permettre d'obtenir la liste des id_trajet d'une équipe à une date donnée
	 * en faisant une requête SQL dans la table Collecte
	 * @param id_equipe
	 * @param date
	 * @return list_id_trajet
	 * @throws SQLException
	 */
	public static ArrayList<Integer> liste_id_trajet(int id_equipe, String date) throws SQLException{
		ArrayList<Integer> list_id_trajet = new ArrayList<Integer>();			//Création de l'objet list_id_trajet de type ArrayList<Integer>
		Statement statement = Connexion.getInstance().createStatement();		//Création d'un objet statement		
		ResultSet rs = statement.executeQuery("select id_trajet from"			//Requête SQL pour obtenir la liste des id_trajet
				+ " Collecte WHERE id_equipe = " 							//pour un id_equipe et une date donnée
				+ id_equipe + " AND date_debut = date(\"" + date + "\")");	

		while(rs.next()) {													
			list_id_trajet.add(rs.getInt("id_trajet"));						//Remplissage de la liste list_id_trajet
		}
		return list_id_trajet;
	}

	/**
	 * Méthode permettant d'effectuer le dessin
	 */
	@Override
	public void paintComponent(Graphics g) {  
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		int w = this.getWidth();			//Largeur de la fenêtre
		int h = this.getHeight();		//Hauteur de la fenêtre
		int size = 50;					//Taille de l'entreprise sur la carte

		//Dessin du secteur Bleu
		Color color1 = new Color(0, 0, 255, 100);
		g2d.setPaint(color1);
		Dessin.dessin_secteur(g2d, "\"Bleu\"");


		//Dessin du secteur Cyan
		Color color2 = new Color(0, 255, 255, 100);
		g2d.setPaint(color2);
		Dessin.dessin_secteur(g2d, "\"Cyan\"");


		//Dessin du secteur Vert
		Color color3 = new Color(0, 255, 0, 100);
		g2d.setPaint(color3);
		Dessin.dessin_secteur(g2d, "\"Vert\"");


		//Dessin du secteur Jaune
		Color color4 = new Color(255, 255, 0, 100);
		g2d.setPaint(color4);
		Dessin.dessin_secteur(g2d, "\"Jaune\"");

		//Dessin du secteur Orange
		Color color5 = new Color(255, 200, 0, 100);
		g2d.setPaint(color5);
		Dessin.dessin_secteur(g2d, "\"Orange\"");


		//Dessin du secteur Rouge
		Color color6 = new Color(255, 0, 0, 100);
		g2d.setPaint(color6);
		Dessin.dessin_secteur(g2d, "\"Rouge\"");

		//Dessin des trajets de l'équipe pour la date donnée
		g2d.setPaint(Color.red);		
		ArrayList<Integer> list_id_trajet;
		try {
			list_id_trajet = liste_id_trajet(this.getId_equipe(), this.getDate());
			for(int id_trajet : list_id_trajet) {
				Dessin.dessin_trajet(g2d, id_trajet);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Dessin des décharges
		g2d.setPaint(Color.black);
		BasicStroke bs1 = new BasicStroke(5);
		g2d.setStroke(bs1);
		for(int i=1; i<=6; i++) {
			Dessin.dessin_decharge(g2d, i);
		}

		//Dessin de l'entreprise
		g2d.setPaint(Color.black); // couleur de l’interieur
		g2d.fillRect(w/2 - size/2, h/2 - size/2, size, size); // dessin de l’interieur
		g2d.setPaint(Color.white); // couleur du contour
		g2d.drawRect(w/2 - size/2, h/2 - size/2, size, size);
	}
}