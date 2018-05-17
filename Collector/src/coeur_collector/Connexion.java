package coeur_collector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Classe Connexion qui permet de se connecter à la Base de Donnée principalement
 * Mais qui est aussi utilisée pour récupérer les dates
 * ce qui est utile dans les requetes SQL des autres classes
 * Elle permet aussi de sauvegarder chaque modification de la base de donnée dans un fichier texte
 * @author jouanfalala
 *
 */
public class Connexion {
	/**
	 * Chemin de l'emplacement de la base de données
	 */
	
	String filePath = Paths.get("Collector.db").toAbsolutePath().toString();
	private Path DB_Path = Paths.get(filePath);
	/**
	 * Attribut Connection qui va permettre au Driver de se connecter à la BDD
	 */
	private static Connection conn; 									//Objet Connection
	/**
	 * Objet Scanner qui permet d'interragir avec les utilisateurs
	 */
	public static final Scanner sc = new Scanner(System.in);			//Scanner qui va être utilisé dans les autres classes
	/**
	 * Chemin d'accès au fichier texte de sauvegarde des modifications de la BDD
	 */
	static String filePath_log = Paths.get("Historique.txt").toAbsolutePath().toString();
	private static Path log_path = Paths.get(filePath_log);

	
	/**
	 * Constructeur de la classe
	 * Elle n'a pas d'argument et essaye de se connecter à la base de donnée SQLite3
	 */
	private Connexion() {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	//Méthode d'instanciation de la connection à la BDD
	/**
	 * Créé une nouvelle connexion à la base de donnée 
	 * @return conn
	 */
	public static Connection getInstance() {
		if(conn == null) {																			//Si il n'y a pas de connexion existante, on la créé
			new Connexion();
		}
		return conn;
	}

	//Méthode de mise à jour de l'historique
	/**
	 * Met à jour le fichier texte d'historique en fonction de l'action et de la table visée
	 * @param action
	 * @param nom_table
	 */
	public static void log(String action, String nom_table) {
		String dat = date("");                                           
		String ligne = "\n" + dat + " - " + action + " d'une entrée dans la table " + nom_table;      //Date et action effectuée dans la table spécifiée
		Path logFile = Paths.get(filePath_log);
		if (!Files.exists(logFile)){                                                     		        //Si le fichier n'existe pas encore, il le créé
			try {
				Files.createFile(logFile);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath_log, true))){		        //Ecriture dans le fichier
			writer.write(ligne);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//Méthode qui récupère la date du jour
	/**
	 * En fonction de si on veut une date partielle ou plus ou moins précise
	 * On rentre une condition sous forme de String dans la méthode
	 * @param condition
	 * @return String date
	 */
	public static String date(String condition){
		Date actuelle = new Date();
		if(condition == "partiel"){
			DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");  
			String dat = dateFormat.format(actuelle);
			return dat;
		}
		else if(condition == "année"){
			DateFormat dateFormat = new SimpleDateFormat("YYYY");
			String dat = dateFormat.format(actuelle);
			return dat;
		}
		else if(condition == "mois_jour"){
			DateFormat dateFormat = new SimpleDateFormat("MM-dd");
			String dat = dateFormat.format(actuelle);
			return dat;
		}
		else if(condition == "mois"){
			DateFormat dateFormat = new SimpleDateFormat("MM");
			String dat = dateFormat.format(actuelle);
			return dat;
		}
		else{
			DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");  
			String dat = dateFormat.format(actuelle);
			return dat;
		}
	}
	
	//Renvoie la date du jour mais un an avant
	/**
	 * Renvoie la date avec le mois et le jour courant
	 * Mais avec une année en arrière
	 * @return date
	 */
	public static String date_annee_ant(){
		String annee = Connexion.date("année");                           //Récupération de la date actuelle
		int a = Integer.parseInt(annee) - 1;                              //Modification de l'année
		String annee_2 = Integer.toString(a);
		String date_moins = annee_2 + "-" + Connexion.date("mois_jour");  
		return date_moins;                                                //Renvoie le mois et le jour actuel mais un an avant
	}
	
	//Renvoie la date du début du mois
	/**
	 * Renvoie la date du début du mois courant
	 * @return date
	 */
	public static String date_deb_mois(){
		String mois = Connexion.date("mois");
		String annee = Connexion.date("annee");
		String date_deb = annee + "-" + mois + "-01";
		return date_deb;
	}
	
	//Renvoie la date du début de mois associé à l'entrée
	/**
	 * Renvoie la date du début du mois rentré en argument dans la fonction
	 * @param annee_mois
	 * @return date
	 */
	public static String date_deb_mois_parti(String annee_mois){
		String date = annee_mois + "-01";
		return date;
	}
	
	//Renvoie la date de fin de mois associé à l'entrée
	/**
	 * Renvoie la date de fin de mois rentré en argument dans la fonction
	 * @param annee_mois
	 * @return date
	 */
	public static String date_fin_mois_parti(String annee_mois){
		String date = annee_mois + "-31";
		return date;
	}
		
}