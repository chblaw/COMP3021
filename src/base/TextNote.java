package base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

public class TextNote extends Note{
	String content;
	private static final long serialVersionUID = 1L;
	
	public TextNote(File f) {
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	
	public TextNote(String title) {
		super(title);
	}
	
	public TextNote(String title, String content){
		super(title);
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	private String getTextFromFile(String absolutePath){
		String result = "";
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(absolutePath);
			in = new ObjectInputStream(fis);
			result += (String)in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void exportTextToFile (String pathFolder) throws FileNotFoundException, IOException{
		String title = this.getTitle().replaceAll(" ", "_");
		FileWriter file = null;
		if (pathFolder.isBlank())
			pathFolder = ".";
		file = new FileWriter(pathFolder + File.separator + title + ".txt");
		try {
			BufferedWriter writer = new BufferedWriter(file);
			writer.append(this.getContent());
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}