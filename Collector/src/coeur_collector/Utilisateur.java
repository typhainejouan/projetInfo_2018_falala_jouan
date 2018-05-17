package coeur_collector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Carte.Carte;

/**
 * Classe abstraite mère des classes Administrateur et Eboueur
 * @author jouanfalala
 *
 */
public abstract class Utilisateur {
	
	//Attributs
	/**
	 * Attribut correspondant au nom dans la BDD
	 */
	private String Nom;
	/**
	 * Attribut correspondant au nom dans la BDD
	 */
	private String Prenom;
	/**
	 * Attribut correspondant à la date d'entrée dans la BDD
	 */
	private String date_entree;
	/**
	 * Attribut correspondant au login dans la BDD
	 */
	private String login;
	/**
	 * Attribut correspondant au mdp dans la BDD
	 */
	private String mdp;


	//Accesseurs
	/**
	 * Accesseur de l'attribut nom
	 * @return
	 */
	public String getNom() {
		return Nom;
	}
	/**
	 * Accesseur de l'attribut id_admin
	 * @return
	 */
	public String getPrenom() {
		return Prenom;
	}
	/**
	 * Accesseur de l'attribut date_entree
	 * @return
	 */
	public String getDate_entree() {
		return date_entree;
	}
	/**
	 * Accesseur de l'attribut login
	 * @return
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * Accesseur de l'attribut mdp
	 * @return
	 */
	public String getMdp() {
		return mdp;
	}

	//Mutateurs

	/**
	 * Mutateur de l'attribut nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.Nom = nom;
	}
	/**
	 * Mutateur de l'attribut prenom
	 * @param prenom
	 */
	public void setPrenom(String prenom) {
		this.Prenom = prenom;
	}
	/**
	 * Mutateur de l'attribut date_entree
	 * @param date_entree
	 */
	public void setDate_entree(String date_entree) {
		this.date_entree = date_entree;
	}
	/**
	 * Mutateur de l'attribut login
	 */
	public void setLogin() {										//Fabrication du login par concaténation
		this.login = this.Prenom.substring(0,1) + this.Nom;		//de l'initiale du Prenom et du Nom en entier
	}
	/**
	 * Mutateur de l'attribut mdp
	 * @param mdp
	 */
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	//Méthodes

	/**
	 * Méthode pour l'authentification de l'utilisateur
	 * @throws SQLException
	 */
	public static int login() throws SQLException{
		Statement statement = Connexion.getInstance().createStatement();

		
		System.out.println("Authentification");
		System.out.println("Entrez 1 pour vous connecter comme éboueur"
				+ " ou 2 pour vous connecter comme administrateur.");
		System.out.print("Choix : ");
		int chance = Connexion.sc.nextInt();
		while(!((chance == 1) | (chance == 2))) {
			System.out.println("Entrez 1 pour vous connecter comme éboueur"
					+ " ou 2 pour vous connecter comme administrateur.");
			System.out.print("Choix : ");
			chance = Connexion.sc.nextInt();
		}

		String login_co = "";
		String mdp_co;
		ResultSet rs1 = statement.executeQuery("Select mdp FROM Eboueur WHERE"
				+ " login = " + "\"" + login_co + "\"");
		ResultSet rs2 = statement.executeQuery("Select mdp FROM Administrateur"
				+ " WHERE login = " + "\"" + login_co + "\"");
		boolean authentification = false;
		if(chance == 1) {
			while(!authentification) {
				System.out.print("login : ");
				login_co = Connexion.sc.next();
				System.out.print("Mot de passe : ");
				mdp_co = Connexion.sc.next();
				rs1 = statement.executeQuery("Select mdp FROM Eboueur WHERE"
						+ " login = " + "\"" + login_co + "\"");
				authentification = mdp_co.equals(rs1.getString("mdp"));
				if(!authentification) {
					System.out.println("Mauvais login ou mauvais mot de passe");
				}
			}
		}
		else if(chance == 2){
			while(!authentification) {
				System.out.print("login : ");
				login_co = Connexion.sc.next();
				System.out.print("Mot de passe : ");
				mdp_co = Connexion.sc.next();
				rs2 = statement.executeQuery("Select mdp FROM Administrateur"
						+ " WHERE login = " + "\"" + login_co + "\"");
				authentification = mdp_co.equals(rs2.getString("mdp"));
				if(!authentification) {
					System.out.println("Mauvais login ou mauvais mot de passe");
				}
			}
		}
		System.out.println("\n\nAuthentification effectuée ;)");
		return chance;
	}


	/**
	 * Méthode pour visualiser les différentes cartes
	 */
	public static void visualiser_carte(){
		
		//Instructions pour l'utilisateur pour choisir le type de carte il veut voir 
		System.out.println("Voulez pouvez visualiser la carte complète des trajets"
				+ " ou bien les trajets d'une équipe pour une date donnée.");
		System.out.println("Entrez \"All\" pour voir la carte complète et \"équipe\""
				+ " pour visualiser les trajet d'une équipe.");
		System.out.print("Choix : ");
		String toast = Connexion.sc.next();
		
		while(!"All".equals(toast) && !"équipe".equals(toast)) {					//Si la chaîne de caractères n'est pas conforme
			System.out.println("Veuillez entrer \"All\" ou bien équipe.");
			System.out.print("Choix : ");
			toast = Connexion.sc.next();
		}
		if("All".equals(toast)){													//Carte complète
			Carte carte = new Carte();
		}
		else if(toast.equals("équipe")) {										//Carte montrant les trajets d'une équipe
			System.out.print("Entrez l'identifiant de l'équipe, id_equipe = ");
			int id_equipe = Connexion.sc.nextInt();
			System.out.print("Entrez la date sous format YYYY-MM-DD, "
					+ "(entrez \"\" pour aujourd'hui) = ");	
			String date = Connexion.sc.next();
			if(date.length()>2) {
				Carte carte = new Carte(id_equipe,date);							//Affichage de la carte des trajets d'une équipe
			}																	//à une date donnée
			else {
				Carte carte = new Carte(id_equipe);								//Affichage de la carte des trajets d'une équipe
			}																	//à la date d'aujourd'hui
		}
	}
}