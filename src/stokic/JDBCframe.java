package stokic;

import javax.swing.*;

/**
 * Diese Klasse erstellt das JDBC Panel.
 * 
 * @author Stokic Stefan
 * @version 1.0
 */
public class JDBCframe extends JFrame {

	private JDBCpanel panel;
	
	/**
	 * Konstruktor der die Connection Parameter bekommt.
	 * @param host der Hostname
	 * @param user der Username
	 * @param pass das Password
	 * @param db der Datenbankname
	 */
	public JDBCframe(String host, String user, String pass, String db) {
		
		super("JDBC");
		panel = new JDBCpanel(host, user, pass, db);
		this.add(panel);	
	}
}
