package fr.usmb.m1isc.compilation.tp;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws Exception  {
		LexicalAnalyzer yy;
		if (args.length > 0)
		        yy = new LexicalAnalyzer(new FileReader(args[0])) ;
		else
		        yy = new LexicalAnalyzer(new InputStreamReader(System.in)) ;
		@SuppressWarnings("deprecation")
		parser p = new parser (yy);
		//System.out.println(p.parse().value);
		Arbre arb = (Arbre)(p.parse().value);
		System.out.println(arb.toString());
		// Segment Data
				Set<String> data;
				data = arb.getLet();
				String dataWrite = "DATA SEGMENT \n";
				for (String i : data) {
					dataWrite += "\t" + i + " DD\n";
				}
				dataWrite += "DATA ENDS \n";

				// Generer
				String code = "CODE SEGMENT\n";
				code += arb.generer();
				code += "CODE ENDS";

				try (FileWriter fw = new FileWriter("prog.asm", false);
					 BufferedWriter bw = new BufferedWriter(fw);
					 PrintWriter out = new PrintWriter(bw)) {
					out.print(dataWrite);
					out.print(code);
				} catch (IOException e) {
					System.out.println(e);
				}
			}

		}
