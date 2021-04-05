package extras;

import java.awt.Color;

public enum ColoresGeneral {
	//Light mode
	LM_LABEL("#00333D"), 
	LM_FONDO("#FFFFFF"),
	LM_TEXTFIELD("#FFFFFF"),
	LM_TEXTFIELD_BORDER("#a3ddcb"),
	LM_TEXTFIELD_SHADE("#5BC2A2"),
	LM_TEXTFIELD_CONTENT("#000000"),

	//Dark mode
	DM_LABEL("#B1B1B1"), 
	DM_FONDO("#2F2F2F"),
	DM_TEXTFIELD("#5C5C5C"),
	DM_TEXTFIELD_BORDER("#263154"),
	DM_TEXTFIELD_CONTENT("#c2c2c2"),
	DM_TEXTFIELD_SHADE("#263154");

	private String hex;

	private ColoresGeneral(String hex) {
		this.hex = hex;
	}

	public Color getColor() {
		return Color.decode(hex);
	}
}
