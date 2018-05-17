package Carte;

import javax.swing.JFrame;

/**
 * Classe permettant de créer la fenêtre de la classe puis de dessiner la carte
 * @author jouanfalala
 *
 */
public class Carte extends JFrame {

	//Constructeurs
	/**
	 * Constructeur de la carte complète
	 */
	public Carte() {
		this.all();
	}
	/**
	 * Constructeur carte des trajets d'une équipe pour aujourd'hui
	 */
	public Carte(int id_equipe) {
		this.equipe(id_equipe);
	}
	/**
	 * Constructeur carte des trajets d'une équipe pour une date donnée
	 */
	public Carte(int id_equipe, String date) {
		this.equipe_date(id_equipe, date);
	}
	
	
	//Méthodes d'affichage
	/**
	 * Méthode appelée pour dessiner la carte complète 
	 */
	private void all() {
		
		Dessin dessin = new Dessin();							//Création d'un objet Dessin
		
		this.setContentPane(dessin);
		this.setTitle("Carte des secteurs de collecte");			//Définition titre de la carte
		this.setSize(701,723);									//Taille de la fenêtre
		this.setLocationRelativeTo(null);						//Emplacement de la fenêtre dans l'écran
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//Fermeture en cliquant sur la croix
		this.setVisible(true);
	}
	
	/**
	 * Méthode appelée pour dessiner la carte des trajets d'une équipe à la date d'aujourd'hui
	 * @param id_equipe
	 */
	private void equipe(int id_equipe) {
		
		Dessin_equipe dessin_equipe = new Dessin_equipe(id_equipe);			//Création d'un objet Dessin_equipe
		
		this.setContentPane(dessin_equipe);
		this.setTitle("Carte des collectes à effectuer "
				+ "aujourd'hui pour l'équipe " + id_equipe);
		this.setSize(701,723);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Méthode appelée pour dessiner la carte des trajets d'une équipe à une date quelconque
	 * @param id_equipe
	 * @param date
	 */
	private void equipe_date(int id_equipe, String date) {
		
		Dessin_equipe dessin_equipe = new Dessin_equipe(id_equipe, date);		//Création d'un objet Dessin_equipe
		
		this.setContentPane(dessin_equipe);
		this.setTitle("Carte des collectes à effectuer le "
		+ date + " pour l'équipe " + id_equipe);
		this.setSize(701,723);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);		
	}
}