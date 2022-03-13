package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class NoteBook implements Serializable{
	private ArrayList<Folder> folders;
	private static final long serialVersionUID = 1L;
	
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	
	public NoteBook(String file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fis);
			NoteBook n = (NoteBook)in.readObject();
			folders = n.getFolders();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean createTextNote(String folderName, String title) {
		TextNote note = new TextNote(title);
		return insertNote(folderName, note);
	}
	
	public boolean createTextNote(String folderName, String title, String content) {
		TextNote note = new TextNote(title, content);
		return insertNote(folderName, note);
	}
	
	public boolean createImageNote(String folderName, String title) {
		ImageNote note = new ImageNote(title);
		return insertNote(folderName, note);
	}
	
	public boolean insertNote(String folderName, Note note) {
		Folder f = null;
		
		for (Folder f1 : folders) {
			if (f1.getName().equals(folderName))
				f = f1;
		}
		
		if (f == null) {
			f = new Folder(folderName);
			folders.add(f);
		}
		
		for (Note n : f.getNotes()) {
			if (n.equals(note)){
				System.out.println("Creating note " + note.getTitle() + " under folder " + folderName + " failed");
				return false;
			}
		}
		f.addNote(note);
		return true;
	}
	
	public ArrayList<Folder> getFolders(){
		return folders;
	}
	
	public void sortFolders() {
		for (Folder f : folders) {
			f.sortNotes();
		}
		Collections.sort(folders);
	}
	
	public List<Note> searchNotes(String keyword){
		ArrayList<Note> result = new ArrayList<Note>();
		for(Folder o: folders){
			result.addAll(o.searchNotes(keyword.toLowerCase()));
		}
		return result;
	}
	
	public boolean save(String file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}