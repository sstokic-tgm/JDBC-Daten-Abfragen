package stokic;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.sql.*;

/**
 * Diese Klasse ist eine Panel Klasse. Diese verwaltet die verschiedenen GUI-Komponenten und verwaltet verschiedene Handler. (In dem Fall nur ein 
 * Handler für die Buttons)
 * 
 * @author Stokic Stefan
 * @version 3.2
 */
public class JDBCpanel extends JPanel {

	private JTextField hostTf, userTf, pwTf, dbTf, sqlTf;
	private JLabel hostL, userL, pwL, dbL, statusL;
	private JButton connect, send;
	private JTabbedPane tabPane;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;

	private ConnectionLogic cl;

	/**
	 * Konstruktor der die Connection Parameter bekommt. Ebenfalls wird das Panel und der Handler im Konstruktor erzeugt.
	 * @param host der Hostname
	 * @param user der Username
	 * @param pass das Password
	 * @param db der Datenbankname
	 */
	public JDBCpanel(String host, String user, String pass, String db) {

		cl = new ConnectionLogic();

		setupPanel(host, user, pass, db);
		setupHandler();
	}

	/**
	 * Diese Methode bekommt die Connection Parameter und setzt sie in die einzelnen Textboxen. Diese Methode erzeugt das Panel mit den einzelnen
	 * GUI-Komponenten/-Elementen.
	 * 
	 * @param host der Hostname
	 * @param user der Username
	 * @param pass das Password
	 * @param db der Datenbankname
	 */
	public void setupPanel(String host, String user, String pass, String db) {

		// Tabpane
		tabPane = new JTabbedPane();

		// ---- Home ----
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);

		JPanel gridPan = new JPanel();
		GridLayout gridL = new GridLayout(10, 1);
		gridPan.setLayout(gridL);

		JPanel bordPanStatus = new JPanel();
		BorderLayout borderStatusL = new BorderLayout();
		bordPanStatus.setLayout(borderStatusL);

		hostTf = new JTextField(host);
		userTf = new JTextField(user);
		pwTf = new JPasswordField(pass);
		dbTf = new JTextField(db);
		hostL = new JLabel("Hostname:");
		userL = new JLabel("Username:");
		pwL = new JLabel("Password:");
		dbL = new JLabel("Database:");

		statusL = new JLabel("Not connected");
		statusL.setForeground(Color.RED);

		connect = new JButton("Connect");

		gridPan.add(hostL);
		gridPan.add(hostTf);
		gridPan.add(userL);
		gridPan.add(userTf);
		gridPan.add(pwL);
		gridPan.add(pwTf);
		gridPan.add(dbL);
		gridPan.add(dbTf);
		gridPan.add(connect);

		bordPanStatus.add(statusL, BorderLayout.LINE_END);
		gridPan.add(bordPanStatus);
		// --------

		// ---- Sql ----
		JPanel sqlPanel = new JPanel();
		BorderLayout sqlLayout = new BorderLayout();
		sqlPanel.setLayout(sqlLayout);

		sqlTf = new JTextField();
		send = new JButton("Send");
		table = new JTable();
		table.setEnabled(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane = new JScrollPane(table);

		sqlPanel.add(sqlTf, BorderLayout.PAGE_START);
		sqlPanel.add(scrollPane, BorderLayout.CENTER);
		sqlPanel.add(send, BorderLayout.PAGE_END);
		// --------

		// Tab
		tabPane.addTab("Home", gridPan);
		tabPane.addTab("Sql", sqlPanel);
		tabPane.setEnabledAt(1, false);
		this.add(tabPane);
	}

	/**
	 * Diese Methode erzeugt den ActionHandler. Dieser ActionHandler wird den Buttons zugewiesen.
	 */
	public void setupHandler() {

		// ActionHandler
		ActionHandler ah = new ActionHandler();
		// ActionHandler dem connect Button geben
		connect.addActionListener(ah);
		// ActionHandler dem send Button geben
		send.addActionListener(ah);
	}

	/**
	 * Diese Klasse ist eine innere Klasse. Diese Klasse ist eine ActionHandler Klasse weshalb sie den ActionListener implementiert.
	 * 
	 * @author Stokic Stefan
	 * @version 3.2
	 */
	public class ActionHandler implements ActionListener {

		private SQLchecker sqlchecker;

		/**
		 * Bei "Buttondruck" wird diese Methode ausgeführt. Je nach welcher Button gedrückt wurde, wird eine Funktionalität ausgeführt. 
		 * Sprich: Das verbinden mit der Datenbank, das trennen der Verbindung zur Datenbank und das absenden des Benutzer-eingegebenen Befehls 
		 * an die Datenbank.
		 */
		@Override
		public void actionPerformed(ActionEvent e){

			sqlchecker = new SQLchecker();

			// Beim drücken des Connect-Buttons wird eine Verbindung zur Datenbank hergestellt.
			if(e.getSource() == connect && cl.getStatus() == false) {

				cl.setConnection(hostTf.getText(), userTf.getText(), pwTf.getText(), dbTf.getText());

				if(cl.connect() == true) {

					statusL.setText("Connected");
					statusL.setForeground(new Color(0, 130, 2));
					connect.setText("Disconnect");
					hostTf.setEditable(false);
					userTf.setEditable(false);
					pwTf.setEditable(false);
					dbTf.setEditable(false);

					tabPane.setEnabledAt(1, true);

					cl.setStatus(true);
				}
				
			// Beim drücken des Disconnect-Buttons wird die Verbindung zur Datenbank getrennt.
			}else if(e.getSource() == connect && cl.getStatus() == true) {

				cl.disconnect();

				statusL.setText("Not connected");
				statusL.setForeground(Color.RED);
				connect.setText("Connect");
				hostTf.setEditable(true);
				userTf.setEditable(true);
				pwTf.setEditable(true);
				dbTf.setEditable(true);

				tabPane.setEnabledAt(1, false);

				cl.setStatus(false);

			// Beim drücken des Send-Buttons wird der vom Benutzer-eingegebener SQL-Befehl überprüft und falls der richtig ist, wird dieser an 
			// die Datenbank abgeschickt.
			}else if(e.getSource() == send && sqlchecker.checkSqlString(sqlTf.getText())) {

				try {

					ResultSet rs = cl.execute(sqlTf.getText());
					ResultSetMetaData rsmd = rs.getMetaData();

					int columnCount = rsmd.getColumnCount();
					String[] column = new String[columnCount];
					int rows = 0;

					while(rs.next()) {

						rows++;
					}

					for(int i = 1; i <= columnCount; i++) {

						column[i - 1] = rsmd.getColumnName(i);
					}

					Object[][] erg = new Object[rows][columnCount];
					rows = 0;
					rs.beforeFirst();

					while(rs.next()) {

						for(int i = 1; i <= columnCount; i++) {

							erg[rows][i - 1] = rs.getString(i);
						}
						rows++;
					}

					model = new DefaultTableModel(erg, column);
					table.setModel(model);

					rs.close();
					rs = null;
				}catch(SQLException sqle) {

					System.out.println("Error: " + sqle.getMessage());
				}

			}else {

				System.out.println("Error: Error in your SQL syntax. Only SELECT, SHOW and DESCRIBE is allowed.");
			}
		}
	}
}
