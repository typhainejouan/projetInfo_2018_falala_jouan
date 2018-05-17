package Carte;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Classe permettant le dessin de la carte complète
 * @author jouanfalala
 *
 */
public class Dessin extends JPanel{

	/**
	 * Méthode pour convertir un objet de type ArrayList<Integer> en objet de type int[] 
	 * @param integers
	 * @return
	 */
	public static int[] convertIntegers(ArrayList<Integer> integers){
		int[] ret = new int[integers.size()];
		for (int i=0; i < ret.length; i++){
			ret[i] = integers.get(i).intValue();
		}
		return ret;
	}

	/**
	 * Méthode pour dessiner les secteurs
	 * @param g2d
	 * @param id_secteur
	 */
	public static void dessin_secteur(Graphics2D g2d, String id_secteur) {
		Secteur secteur = new Secteur(id_secteur);				//Création d'un objet de type Secteur
		int[] px = convertIntegers(secteur.getListe_X());			//Conversion des ArrayList getListe_X et getListe_Y en int[]
		int[] py = convertIntegers(secteur.getListe_Y());
		int t = px.length;										//Longueur de la liste des X (et donc des Y)
		Polygon pol = new Polygon(px, py, t);					//Création d'un objet Polygon à t points
		g2d.fillPolygon(pol);									//Dessin du polygone
	}
	
	/**
	 * Méthodes pour dessiner les trajets
	 * @param g2d
	 * @param id_trajet
	 */
	public static void dessin_trajet(Graphics2D g2d,int id_trajet) {
		Trajet trajet = new Trajet(id_trajet);					//Création d'un objet de type Trajet
		int[] tx = convertIntegers(trajet.getListe_X());			//Conversion des ArrayList getListe_X et getListe_Y en int[]
		int[] ty = convertIntegers(trajet.getListe_Y());
		for(int i=0; i < tx.length-1; i++) {						//
			BasicStroke bs = new BasicStroke(1);					//Configuration de la taille du pinceau pour les lignes
			g2d.setStroke(bs);
			g2d.drawLine(tx[i], ty[i], tx[i+1], ty[i+1]);		//Dessin des tx.length-1 lignes du trajet
			
			BasicStroke bs1 = new BasicStroke(5);				//Configuration de la taille du pinceau pour les points
			g2d.setStroke(bs1);
			g2d.drawLine(tx[i+1], ty[i+1], tx[i+1], ty[i+1]);		//Dessin des tx.length-1 points du trajet
		}
	}

	/**
	 * Méthode pour dessiner les décharges
	 * @param g2d
	 * @param id_decharge
	 */
	public static void dessin_decharge(Graphics2D g2d, int id_decharge) {
		Decharge decharge = new Decharge(id_decharge);			//Création de l'objet décharge
		g2d.drawLine(decharge.getListe_X().get(0), 				//Dessin du point où est situé la décharge
				decharge.getListe_Y().get(0),
				decharge.getListe_X().get(0), 
				decharge.getListe_Y().get(0));
	}

	
	@Override
	/**
	 * Méthode permettant d'effectuer le dessin
	 */
	public void paintComponent(Graphics g) {  
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		int w = this.getWidth();				//Largeur de la fenêtre
		int h = this.getHeight();			//Hauteur de la fenêtre
		int size = 50;						//Taille de l'entreprise sur la carte

		//Dessin du secteur Bleu
		Color color1 = new Color(0, 0, 255, 100);
		g2d.setPaint(color1);				//Configuration de la couleur du secteur Bleu
		dessin_secteur(g2d, "\"Bleu\"");

		g2d.setPaint(Color.blue);			//Dessin des trajets du secteur Bleu
		for(int i=1; i<=3; i++) {			//(id_trajet entre 1 et 3)
			dessin_trajet(g2d, i);
		}

		//Dessin du secteur Cyan
		Color color2 = new Color(0, 255, 255, 100);
		g2d.setPaint(color2);
		dessin_secteur(g2d, "\"Cyan\"");

		Color color_tB = new Color(0, 150, 255);
		g2d.setPaint(color_tB);			
		for(int i=4; i<=6; i++) {
			dessin_trajet(g2d, i);
		}

		//Dessin du secteur Vert
		Color color3 = new Color(0, 255, 0, 100);
		g2d.setPaint(color3);
		dessin_secteur(g2d, "\"Vert\"");

		Color color_tC = new Color(0, 122, 37);
		g2d.setPaint(color_tC);
		for(int i=7; i<=9; i++) {
			dessin_trajet(g2d, i);
		}

		//Dessin du secteur Jaune
		Color color4 = new Color(255, 255, 0, 100);
		g2d.setPaint(color4);
		dessin_secteur(g2d, "\"Jaune\"");

		Color color_tD = new Color(204, 194, 0);
		g2d.setPaint(color_tD);
		for(int i=10; i<=12; i++) {
			dessin_trajet(g2d, i);
		}

		//Dessin du secteur Orange
		Color color5 = new Color(255, 200, 0, 100);
		g2d.setPaint(color5);
		dessin_secteur(g2d, "\"Orange\"");

		Color color_tE = new Color(255, 194, 0);
		g2d.setPaint(color_tE);
		for(int i=13; i<=15; i++) {
			dessin_trajet(g2d, i);
		}

		//Dessin du secteur Rouge
		Color color6 = new Color(255, 0, 0, 100);
		g2d.setPaint(color6);
		dessin_secteur(g2d, "\"Rouge\"");

		g2d.setPaint(Color.red);
		for(int i=16; i<=18; i++) {
			dessin_trajet(g2d, i);
		}

		//Dessin des décharges
		g2d.setPaint(Color.black);
		BasicStroke bs1 = new BasicStroke(5);
		g2d.setStroke(bs1);
		for(int i=1; i<=6; i++) {
			dessin_decharge(g2d, i);
		}

		//Dessin de l'entreprise
		g2d.setPaint(Color.black); 								//Couleur de l’interieur
		g2d.fillRect(w/2 - size/2, h/2 - size/2, size, size);		//Dessin de l’interieur
		g2d.setPaint(Color.white); 								//Couleur du contour
		g2d.drawRect(w/2 - size/2, h/2 - size/2, size, size);		//Dessin du contour
	}
}