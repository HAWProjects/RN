/**
 * 
 */
package eu.rn.praktikum2.serverUTF8;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Robert
 *
 */
public class MyFileWriter {
	
	private BufferedWriter bf;
	
	public MyFileWriter(String fileName, String outputPath) throws IOException{
		File file = new File(outputPath+fileName);
		FileWriter fw = new FileWriter(file);
		bf = new BufferedWriter(fw);
		
	}
	
	
	public void writeLine(String value) throws IOException{
		bf.write(value);
		bf.newLine();
		
	}
}
