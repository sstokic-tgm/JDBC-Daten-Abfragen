package stokic;

import javax.swing.*;

/**
 * Diese Klasse erstellt das JDBC Frame.
 * 
 * @author Stokic Stefan
 * @version 1.0
 */
public class JDBCgui {

	private String host, user, pass, db;
	
	/**
	 * Konstruktor der die Connection Parameter bekommt.
	 * @param host der Hostname
	 * @param user der Username
	 * @param pass das Password
	 * @param db der Datenbankname
	 */
	public JDBCgui(String host, String user, String pass, String db) {
		
		this.host = host;
		this.user = user;
		this.pass = pass;
		this.db = db;
	}
	
	/**
	 * Diese Methode erzeugt und startet das JDBC Frame.
	 */
	public void startFrame() {
		
		JDBCframe frame = new JDBCframe(this.host, this.user, this.pass, this.db);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 300, 300);
		frame.setResizable(false); 
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
