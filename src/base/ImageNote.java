package base;

import java.io.File;

public class ImageNote extends Note{
	public File image;
	private static final long serialVersionUID = 1L;
	
	public ImageNote(String title) {
		super(title);
	}
}