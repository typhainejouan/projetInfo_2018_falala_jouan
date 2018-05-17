package Main;

import java.sql.SQLException;

import coeur_collector.*;

/**
 * Classe Main pour faire tourner le programme 
 * @author jouanfalala
 *
 */
public class Main {
	
	/**
	 * Méthode d'affichage du menu
	 */
	public static void menu() {
		int choix;
		System.out.println("Que voulez vous faire ?");
		System.out.println("Pour faire une recherche, tapez 1");
		System.out.println("Pour modifier la BDD, tapez 2");
		System.out.println("Pour calculer les statistiques, tapez 3");
		System.out.println("Pour visualiser la carte, tapez 4");
		System.out.print("Choix = ");
		choix = Connexion.sc.nextInt();
		if(choix==1) {
			try {
				Administrateur.recherche();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(choix==2) {
			try {
				Administrateur.modif_BDD();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(choix==3) {
			try {
				Administrateur.calcul_stat();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(choix==4) {
			Utilisateur.visualiser_carte();
		}	
	}

	public static void main(String[] args) {
		
		
		int log = 0;
		
		//Authentification
		try {
			log = Utilisateur.login();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Si l'utilisateur est administrateur
		if(log==2) {
			menu();
		}

		//Si l'utilisateur est un éboueur
		if(log==1) {
			Utilisateur.visualiser_carte();
		}
	}
}