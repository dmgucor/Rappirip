package extras;

import java.awt.Color;

public enum ColoresMain {
	//Light mode
	LM_LABEL("#00333D"), 
	LM_MAIN("#a3ddcb"),
	LM_MAIN_LABEL("#e5707e"),
	LM_TEXTFIELD("#FFFFFF"),
	LM_TEXTFIELD_BORDER("#a3ddcb"),
	LM_TEXTFIELD_SHADE("#5BC2A2"),
	LM_TEXTFIELD_CONTENT("#000000"),
	LM_HYPERLINK("#6B4C61"),
	LM_HYPERLINK_ENTERED("#3C2A36"),
	LM_LABEL_BACKGROUND("#82b0a2"),

	//Dark mode
	DM_LABEL("#C2C2C2"), 
	DM_MAIN("#182952"),
	DM_MAIN_LABEL("#E14594"),
	DM_TEXTFIELD("#5C5C5C"),
	DM_TEXTFIELD_BORDER("#263154"),
	DM_TEXTFIELD_CONTENT("#c2c2c2"),
	DM_TEXTFIELD_SHADE("#263154"),
	DM_HYPERLINK("#6183D1"),
	DM_HYPERLINK_ENTERED("#D0DAF1"),
	DM_LABEL_BACKGROUND("#132041"),;
	
	private String hex;

	private ColoresMain(String hex) {
		this.hex = hex;
	}

	public Color getColor() {
		return Color.decode(hex);
	}

}
