package base;

import java.util.Objects;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder>{
	private ArrayList<Note> notes;
	private String name;
	
	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Note> getNotes(){
		return notes;
	}

	public void sortNotes() {
		Collections.sort(notes);
	}
	
	public List<Note> searchNotes(String keywords) {
		ArrayList<Note> notesCopy = new ArrayList<Note>(notes);
		if (keywords.isEmpty())
			return notesCopy;
		
		ArrayList<String> keywordsSplit = new ArrayList<>(Arrays.asList(keywords.toLowerCase().split(" ")));
		//separate OR and AND
		ArrayList<String> keywordsAND = new ArrayList<String>(keywordsSplit);
		ArrayList<String> keywordsOR = new ArrayList<String>();
		if (!keywordsSplit.get(0).contains("or") && !keywordsSplit.get(keywordsSplit.size() - 1).contains("or")) {
			for (int i = 0; i < keywordsSplit.size(); i++) {
				if (keywordsSplit.get(i).contains("or")) {
					String before = keywordsSplit.get(i - 1);
					String after = keywordsSplit.get(i + 1);
					keywordsOR.add(before);
					keywordsOR.add(after);
					keywordsAND.remove(before);
					keywordsAND.remove("or");
					keywordsAND.remove(after);
				}
			}
		}
		//deal with OR
		for (int i = 0; i < keywordsOR.size(); i += 2) {
			ArrayList<Note> noteToRemove = new ArrayList<Note>();
			for (Note n : notesCopy) {
				String title = n.getTitle().toLowerCase();
				if (n instanceof ImageNote) {
					if (!(title.contains(keywordsOR.get(i)) || title.contains(keywordsOR.get(i + 1))))
						noteToRemove.add(n);
				} else if (n instanceof TextNote) {
					String content = ((TextNote) n).getContent().toLowerCase();
					if (!((title.contains(keywordsOR.get(i)) || title.contains(keywordsOR.get(i + 1))) || (content.contains(keywordsOR.get(i)) || content.contains(keywordsOR.get(i + 1)))))
						noteToRemove.add(n);
				}
			}
			if (!noteToRemove.isEmpty()) {
				for (Note n : noteToRemove)
					notesCopy.remove(n);
			}
		}
		//deal with AND
		for (int i = 0; i < keywordsAND.size(); i++) {
			ArrayList<Note> noteToRemove = new ArrayList<Note>();
			for (Note n : notesCopy) {
				String title = n.getTitle().toLowerCase();
				if (n instanceof ImageNote) {
					if (!title.contains(keywordsAND.get(i)))
						noteToRemove.add(n);
				} else if (n instanceof TextNote) {
					String content = ((TextNote) n).getContent().toLowerCase();
					if (!(title.contains(keywordsAND.get(i)) || content.contains(keywordsAND.get(i))))
						noteToRemove.add(n);
				}
			}
			if (!noteToRemove.isEmpty()) {
				for (Note n : noteToRemove)
					notesCopy.remove(n);
			}
		}
		return notesCopy;
	}
	
	@Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		
		for (Note n : notes) {
			if (n instanceof TextNote)
				nText++;
			else
				nImage++;
		}
		return name + ":" + nText + ":" + nImage;
	}

	@Override
	public int compareTo(Folder o) {
		return this.name.compareTo(o.name);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if(!(obj instanceof Folder))
			return false;
		Folder other = (Folder) obj;
		return Objects.equals(name, other.name);
	}
}