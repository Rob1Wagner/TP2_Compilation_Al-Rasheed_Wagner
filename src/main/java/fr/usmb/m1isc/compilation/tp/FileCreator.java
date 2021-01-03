package fr.usmb.m1isc.compilation.tp;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class FileCreator {
	 public static void writeFile(String text) {
	    	try {

	    		 	File file = new File("C:\\Users\\Wagro\\eclipse-workspace\\I703_TP2_Lambada-master2\\test.asm");

	    		   // créer le fichier s'il n'existe pas
	    		    if (!file.exists()) {
	    			    file.createNewFile();
	    		    }

	    		    FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
	    		    BufferedWriter bw = new BufferedWriter(fw);
	    		    bw.write(text);
	    		    bw.close();

	    		    System.out.println("Modification terminée!");

	    		   } catch (IOException e) {
	    			   e.printStackTrace();
	    		}
	    }
}
