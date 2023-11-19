package assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MoveLog {
	
	private File myFile;
	
    public void createTextFile(String fileName) {
        try {
        	Date date = new Date();
        	Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
        	myFile = new File("MoveLog/" + fileName + "_" + formatter.format(date) + ".txt");
            if (myFile.createNewFile()) {
              FileWriter myWriter = new FileWriter(myFile, true);
              myWriter.write(" MUSKETEER        GUARD" + "\n");
              myWriter.close();
              System.out.println("File created: " + myFile.getName());
            } else {
              System.out.println("File " + fileName + " already exists.");
            }
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
	
    /**
     * adds text to the movelog text file in a specific order
     */
	public void addText(String move, Boolean turn) {
		 try {
		        FileWriter myWriter = new FileWriter(myFile, true);
		        if (turn) {
		        myWriter.write(move + "\n");
		        }
		        else {
		        myWriter.write("               " + move + "\n");
		        }
		        myWriter.close();
	      } catch (IOException e) {
		        System.out.println("An error occurred.");
		        e.printStackTrace();
		      }
	}
	
	public void addRandomized(String random) {
		 try {
		        FileWriter myWriter = new FileWriter(myFile, true);
		        myWriter.write("----------------------------" + "\n");
		        myWriter.write(random + "\n");
		        myWriter.write("----------------------------" + "\n");
		        myWriter.close();
	      } catch (IOException e) {
		        System.out.println("An error occurred.");
		        e.printStackTrace();
		      }
	}
	
    /**
     * removes last line of text from the movelog
     */
	public void removeText() {
		
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(myFile, "rw");
			long size = file.length() - 1;
			int c;
			do {                     
			  size = size - 1;
			  file.seek(size);
			  c = file.readByte();
			} while(c != 10 && size > 0);
			if (size != 0) { 
			file.setLength(size + 1);
			} else {
			file.setLength(size);
			}
		} catch (FileNotFoundException e) {
	        System.out.println("The file is not found");
			e.printStackTrace();

	    } catch (IOException e) {
	        System.out.println("An error occurred.");
			e.printStackTrace();
		}  
	
	}
}
	

