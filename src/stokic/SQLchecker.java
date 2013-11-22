package stokic;

/**
 * Diese Klasse ist eine SQL-Befehl "checker"-/Überprüfungs-Klasse. Diese überprüft ob der eingegebene Befehl gültig ist, sprich ob der 
 * Befehl erlaubte "Texte"/"Befehle" hat oder nicht.
 * 
 * @author Stokic Stefan
 * @version 1.1
 */
public class SQLchecker {

	private static String[] notAllowed = {"INSERT", "DELETE", "CREATE", "SET", "GRANT", "UPDATE", "ALTER", "USE", "DROP"};
	private static String[] allowed = {"SELECT", "SHOW", "DESCRIBE", "DESC"};

	/**
	 * Diese Methode überprüft ob der eingegebene Befehlt gültig ist, sprich ob der Befehl erlaubte "Texte"/"Befehle" hat oder nicht.
	 * @param input der eingegebene Befehl
	 * @return ob der Befehl gültig ist oder nicht
	 */
	public boolean checkSqlString(String input) {

		String sql = input.toUpperCase();

		if(sql.indexOf(';') != sql.length() - 1) 
			return false;

		for (String s : notAllowed) {

			if (sql.contains(s)) 
				return false;
		}

		int i = 0;
		for (String s : allowed) {

			if (sql.startsWith(s)) 
				break;
			i++;
			if (i == allowed.length) 
				return false;
		}

		return true;
	}
}