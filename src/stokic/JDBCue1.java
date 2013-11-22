package stokic;

/**
 * Startklasse, die den CLIparser benutzt
 *
 * Argumente:
 * h	Der Hostname
 * u	Der Username
 * p	Das Password
 * d	Der Datenbankname
 *
 * @author Stokic Stefan
 * @version 1.0
 */
public class JDBCue1 {

	public static void main(String[] args){

		CLIparser cli = new CLIparser(args);
		cli.parse();
	}
}
