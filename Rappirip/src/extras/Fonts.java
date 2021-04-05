package extras;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Fonts {
	public Fonts() {
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/Fonts/MANOLETE.ttf").getFile())));
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/Fonts/VT323-Regular.ttf").getFile())));
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/Fonts/Comfortaa.ttf").getFile())));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
	}
}
