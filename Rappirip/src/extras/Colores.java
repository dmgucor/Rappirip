package extras;

import java.awt.Color;

public enum Colores {
	//Light mode
	LM_FONDO("#EEEEEE"), 
	LM_LABEL("#00333D"), 
	LM_MAIN("#a3ddcb"),
	LM_TEXTFIELD("#FFFFFF"),
	LM_TEXTFIELD_BORDER("#a3ddcb"),
	LM_TEXTFIELD_SHADE("#5BC2A2"),
	LM_TEXTFIELD_CONTENT("#000000"),
	LM_HYPERLINK("#6B4C61"),
	LM_HYPERLINK_ENTERED("#3C2A36"),

	//Dark mode
	DM_LABEL("#C2C2C2"), 
	DM_FONDO("#000000"), 
	DM_MAIN("#1F2947"),
	DM_TEXTFIELD("#5C5C5C"),
	DM_TEXTFIELD_BORDER("#263154"),
	DM_TEXTFIELD_CONTENT("#e5707e"),
	DM_HYPERLINK("#D6B11F"),
	DM_HYPERLINK_ENTERED("#E8CD5E"),
	DM_TEXTFIELD_SHADE("#263154");
	
	private String hex;

	private Colores(String hex) {
		this.hex = hex;
	}

	public Color getColor() {
		return Color.decode(hex);
	}

}
