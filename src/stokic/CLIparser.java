package stokic;

import org.apache.commons.cli.*;

/**
* Kommandozeilen Argumente werden im GNU Style geparst.
*
* @author Stokic Stefan
* @version 1.1
*/
public class CLIparser {

	private String[] args;
	private Options options;
        
	public CLIparser(String[] args){
                
		this.args = args;
		this.options = new Options();
	        
		Option h = OptionBuilder.withArgName("Hostname")
								.hasArg()
								.withDescription("Der Server Hostname")
								.create("h");
		Option u = OptionBuilder.withArgName("Benutzer")
								.hasArg()
								.withDescription("Der Benutzername")
								.create("u");
		Option p = OptionBuilder.withArgName("Passwort")
								.hasArg()
								.withDescription("Passwort")
								.create("p");
		Option d = OptionBuilder.withArgName("Datenbank")
								.hasArg()
								.withDescription("Datenbankname")
								.create("d");
	     
	        
		this.options.addOption(h);
		this.options.addOption(u);
		this.options.addOption(p);
		this.options.addOption(d);
        
	}
        
	/**
	* Methode die die Argumente parst und wenn sie korrekt sind, wird die GUI ausgefuehrt/angezeigt
	*/
	public void parse(){
                
		GnuParser parser = new GnuParser();
                
		try{
                        
			CommandLine line = parser.parse(this.options, this.args);
                        
			if(line.hasOption("h") && line.hasOption("u") && line.hasOption("p") && line.hasOption("d")){
                                
				String hostVal = line.getOptionValue("h");
				String userVal = line.getOptionValue("u");
				String passVal = line.getOptionValue("p");
				String dbVal = line.getOptionValue("d");
				
				JDBCgui gui = new JDBCgui(hostVal, userVal, passVal, dbVal);
				gui.startFrame();
				
			}else{
                                
				this.help();
			}
                
		}catch(ParseException e){
                        
			this.help();
		}
	}
        
	/**
	* Methode die eine Hilfestellung zeigt
	*/
	public void help(){
        	 
		HelpFormatter hf = new HelpFormatter();
		hf.printHelp("JDBC", this.options);
	}
}