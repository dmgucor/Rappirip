package vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import extras.ColoresGeneral;
import extras.ColoresMain;
import extras.LetterDocumentFilter;
import extras.LimitDocumentFilter;
import extras.SpringUtilities;
import modelos.ClienteDTO;
import modelos.UsuarioModelo;

public class EditarPerfilGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	UsuarioModelo usuario;

	//Componentes GUI
	JPanel perfilPanel;
	JPanel panelForma;
	JTextField nombreField;
	JTextField direccionField;
	JTextField celularField;
	GridBagConstraints constraints;

	//colores del tema
	Color fondoColor;
	Color labelColor;
	Color textfieldColor;
	Color textfieldBorderColor;
	Color textfieldShadowColor;
	Color textfieldContentColor;
	Color labelTituloColor;
	
	//Fuentes
	String fuenteTitulo = "MANOLETE";
	String fuenteLabel = "Comfortaa";

	public EditarPerfilGUI(boolean darkMode, UsuarioModelo usuario) {
		//Inicializar atributos
		this.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		this.usuario = usuario;
		
		if(darkMode) {
			fondoColor = ColoresGeneral.DM_FONDO.getColor();
			labelColor = ColoresGeneral.DM_LABEL.getColor();
			textfieldColor = ColoresGeneral.DM_TEXTFIELD.getColor();
			textfieldBorderColor = ColoresGeneral.DM_TEXTFIELD_BORDER.getColor();
			textfieldShadowColor = ColoresGeneral.DM_TEXTFIELD_SHADE.getColor();
			textfieldContentColor = ColoresGeneral.DM_TEXTFIELD_CONTENT.getColor();
			labelTituloColor = ColoresMain.DM_MAIN_LABEL.getColor();
		}
		else {
			fondoColor = ColoresGeneral.LM_FONDO.getColor();
			labelColor = ColoresGeneral.LM_LABEL.getColor();
			textfieldColor = ColoresGeneral.LM_TEXTFIELD.getColor();
			textfieldBorderColor = ColoresGeneral.LM_TEXTFIELD_BORDER.getColor();
			textfieldShadowColor = ColoresGeneral.LM_TEXTFIELD_SHADE.getColor();
			textfieldContentColor = ColoresGeneral.LM_TEXTFIELD_CONTENT.getColor();
			labelTituloColor = ColoresMain.LM_MAIN_LABEL.getColor();
		}
		
		this.setBackground(fondoColor);
		this.setVisible(true);
		setFormaCliente();

	}

	public void setFormaCliente() {
		String[] labels = {"Nombre: ", "Direcci�n: ", "Celular: ", "Icono" , "Mis tarjetas" };
		Font estiloFuenteLbl = new Font(fuenteLabel, Font.PLAIN, 22);

		//Datos personales
		JLabel perfil = new JLabel("Mi perfil");
		perfil.setForeground(labelTituloColor);
		perfil.setFont(new Font(fuenteTitulo, Font.PLAIN, 30));
		perfilPanel = new JPanel(new GridBagLayout());
		perfilPanel.setBackground(fondoColor);
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 2;
		perfilPanel.add(perfil, cons);
		cons.gridy++;

		JLabel icono = new JLabel();
		icono.setBackground(fondoColor);
		icono.setIcon(new ImageIcon(new javax.swing.ImageIcon(usuario.getAvatar()).getImage().getScaledInstance(128,128 , Image.SCALE_SMOOTH)));
		cons.weightx = 1;
		perfilPanel.add(icono, cons);
		cons.gridx++;

		panelForma = new JPanel(new SpringLayout());
		panelForma.setBackground(fondoColor);

		//Document listener para detectar cambios en los campos
		DocumentListener docListener = new DocChangeListener();
		
		//agregar labels y campos
		JLabel nombre = new JLabel(labels[0], JLabel.TRAILING);
		nombre.setForeground(labelColor);
		nombre.setFont(estiloFuenteLbl);
		panelForma.add(nombre);
		nombreField = new JTextField(20);
		nombre.setLabelFor(nombreField);
		((AbstractDocument)nombreField.getDocument()).setDocumentFilter(new LimitDocumentFilter(35));
		((AbstractDocument)nombreField.getDocument()).setDocumentFilter(new LetterDocumentFilter());		
		nombreField.getDocument().addDocumentListener(docListener);
		nombreField.setBorder(javax.swing.BorderFactory.createBevelBorder(1, textfieldBorderColor, textfieldShadowColor));
		nombreField.setPreferredSize(new Dimension(nombreField.getWidth(), 25));
		nombreField.setForeground(textfieldContentColor);
		nombreField.setBackground(textfieldColor);
		panelForma.add(nombreField);
		
		JLabel direccion = new JLabel(labels[1], JLabel.TRAILING);
		direccion.setForeground(labelColor);
		direccion.setFont(estiloFuenteLbl);
		panelForma.add(direccion);
		direccionField = new JTextField(20);
		direccion.setLabelFor(direccionField);
		direccionField.getDocument().addDocumentListener(docListener);
		direccionField.setBorder(javax.swing.BorderFactory.createBevelBorder(1, textfieldBorderColor, textfieldShadowColor));
		direccionField.setPreferredSize(new Dimension(direccionField.getWidth(), 25));
		direccionField.setForeground(textfieldContentColor);
		direccionField.setBackground(textfieldColor);
		panelForma.add(direccionField);
		
		JLabel celular = new JLabel(labels[2], JLabel.TRAILING);
		celular.setFont(estiloFuenteLbl);
		celular.setForeground(labelColor);
		panelForma.add(celular);
		celularField = new JTextField(20);
		celular.setLabelFor(celularField);
		celularField.getDocument().addDocumentListener(docListener);
		celularField.setBorder(javax.swing.BorderFactory.createBevelBorder(1, textfieldBorderColor, textfieldShadowColor));
		celularField.setPreferredSize(new Dimension(celularField.getWidth(), 25));
		celularField.setForeground(textfieldContentColor);
		celularField.setBackground(textfieldColor);
		panelForma.add(celularField);
		
		SpringUtilities.makeCompactGrid(panelForma,
				3, 2, //rows, cols
				6, 6,        //initX, initY
				6, 10); 		//xPad, yPad
		
		perfilPanel.add(panelForma, cons);

		this.add(perfilPanel, constraints);


	}
	
	public class DocChangeListener implements DocumentListener{
		public void verificarCambios() {
			if(!nombreField.getText().trim().equals(usuario.getNombre()) || direccionField.getText().trim().equals(((ClienteDTO) usuario).getDireccion())
					|| Integer.parseInt(celularField.getText().trim()) != ((ClienteDTO) usuario).getTelefono()) {
				//enable button
				
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			verificarCambios();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			verificarCambios();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			verificarCambios();
		}
	}
}
