package base;

import java.util.Date;
import java.util.Objects;
import java.io.Serializable;

public class Note implements Comparable<Note>, Serializable{
	private Date date; 
	private String title;
	private static final long serialVersionUID = 1L;
	
	public Note(String title) {
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		return date.toString() + "\t" + title;	
	}
	
	@Override
	public int compareTo(Note o) {
		// TODO Auto-generated method stub
		if (this.date.before(o.date))
			return -1;
		if (this.date.after(o.date))
			return 1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if(!(obj instanceof Note))
			return false;
		Note other = (Note) obj;
		return Objects.equals(title, other.title);
	}
}