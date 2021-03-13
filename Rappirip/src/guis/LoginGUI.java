package guis;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import eu.hansolo.custom.SteelCheckBox;
import extras.Colores;
import extras.Conexion;
import extras.LimitDocumentFilter;
import extras.SpringUtilities;
import modulos.Login;

public class LoginGUI {
	static String ERROR_LOGIN = "Usuario o clave incorrecto";

	JFrame frame;
	String[] labels = {"Usuario: ", "Contraseña: "};
	GridBagConstraints constraints;
	JTextField usernameField;
	JPasswordField passwordField;
	JButton iniciarSesion;
	Login loginLogica; 
	Conexion conexion;
	JLabel modoIcon;
	JLabel username;
	JLabel password;
	JPanel panelFormaInicio;
	SteelCheckBox switchButton;
	JLabel errorLabel;
	JLabel registrarse;

	Color colorFondo;
	Color colorLabel;
	Color colorTextFields;
	Color colorBordeTextfield;
	Color colorSombraTextfield;
	Color colorHiperlink;
	Color colorHiperlinkActivo;
	Color colorTextFieldContenido;

	boolean darkModeActive = false;

	static String LIGHT_ROUTE = "/rojo.png";
	static String DARK_ROUTE = "/negro.png";


	public LoginGUI(Conexion conexion) { 
		//Inicializar atributos
		constraints = new GridBagConstraints();
		usernameField = new JTextField(20);
		passwordField = new JPasswordField(20);	
		iniciarSesion = new JButton("Iniciar sesión");
		loginLogica = new Login(conexion);
		modoIcon = new JLabel();
		this.conexion = conexion;
		errorLabel = new JLabel();

		//colores del tema
		colorLabel = Colores.LM_LABEL.getColor();
		colorFondo = Colores.LM_MAIN.getColor();
		colorTextFields = Colores.LM_TEXTFIELD.getColor();
		colorBordeTextfield = Colores.LM_TEXTFIELD_BORDER.getColor();
		colorSombraTextfield = Colores.LM_TEXTFIELD_SHADE.getColor();
		colorHiperlink = Colores.LM_HYPERLINK.getColor();
		colorHiperlinkActivo = Colores.LM_HYPERLINK_ENTERED.getColor();
		colorTextFieldContenido = Colores.LM_TEXTFIELD_CONTENT.getColor();

		//Configuración de la ventana
		frame = new JFrame("Iniciar sesión");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.getContentPane().setBackground(colorFondo);

		setLogo(frame);
		setFormaInicio(frame);
		setBotonIniciar(frame);
		setRegistrar(frame);
		setDarkModeToggle();

		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	//Imagen con el logo
	private void setLogo(JFrame frame) {
		JLabel picLogo = new JLabel();
		picLogo.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/logo.png")).getImage().getScaledInstance(456-120, 293-90, Image.SCALE_SMOOTH)));

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 4;
		constraints.insets = new Insets(50, 50, 10, 50);

		frame.add(picLogo, constraints);
	}

	//Botón para iniciar sesión
	private void setBotonIniciar(JFrame frame) {
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(20, 50, 50, 50);
		iniciarSesion.setEnabled(false);

		//Funcionalidad del botón
		iniciarSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Boolean logueado = loginLogica.login(usernameField.getText().trim(), passwordField.getPassword());
				if(!logueado) {
					errorLabel.setText(ERROR_LOGIN);
				}
			}
		});

		frame.add(iniciarSesion, constraints);
	}

	//Forma para iniciar sesión
	private void setFormaInicio(JFrame frame) {
		//Panel que contiene la forma
		panelFormaInicio = new JPanel(new SpringLayout());
		panelFormaInicio.setBackground(colorFondo);

		//Document listener para detectar cambios en los campos
		DocumentListener myDocListener = new MyDocListener();

		//Agregar labels y configurar textFields
		username = new JLabel(labels[0], JLabel.TRAILING);
		username.setForeground(colorLabel);
		panelFormaInicio.add(username);
		((AbstractDocument)usernameField.getDocument()).setDocumentFilter(new LimitDocumentFilter(25));
		username.setLabelFor(usernameField);
		usernameField.setSelectionColor(colorTextFields);
		usernameField.setBorder(javax.swing.BorderFactory.createBevelBorder(1, colorBordeTextfield, colorSombraTextfield));
		usernameField.setPreferredSize(new Dimension(usernameField.getWidth(), 25));
		usernameField.setForeground(colorTextFieldContenido);
		usernameField.getDocument().addDocumentListener(myDocListener);
		panelFormaInicio.add(usernameField);

		password = new JLabel(labels[1], JLabel.TRAILING);
		password.setForeground(colorLabel);
		panelFormaInicio.add(password);
		((AbstractDocument)passwordField.getDocument()).setDocumentFilter(new LimitDocumentFilter(20));
		passwordField.setPreferredSize(new Dimension(usernameField.getWidth(), 25));
		passwordField.setBorder(javax.swing.BorderFactory.createBevelBorder(1, colorBordeTextfield, colorSombraTextfield));
		passwordField.setForeground(colorTextFieldContenido);
		password.setLabelFor(passwordField);

		passwordField.setSelectionColor(colorTextFields);

		passwordField.getDocument().addDocumentListener(myDocListener);
		panelFormaInicio.add(passwordField);

		SpringUtilities.makeCompactGrid(panelFormaInicio,
				labels.length, 2, //rows, cols
				6, 6,        //initX, initY
				6, 6); 		//xPad, yPad

		//Constraints para el panel con la forma
		constraints.gridy = 1;
		constraints.insets = new Insets(50, 20, 10, 20);

		frame.add(panelFormaInicio, constraints);

		//Configuración del label con errores
		errorLabel.setText(" ");
		errorLabel.setForeground(Color.decode("#e5707e"));
		constraints.gridy = 2;
		constraints.insets = new Insets(0, 0, 0, 0);
		frame.add(errorLabel, constraints);
	}

	private void setRegistrar(JFrame frame) {
		registrarse = new JLabel("Crea tu cuenta");
		registrarse.setForeground(colorHiperlink);
		registrarse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		registrarse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new RegistroGUI(frame, conexion, darkModeActive);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				registrarse.setForeground(colorHiperlinkActivo);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				registrarse.setForeground(colorHiperlink);
			}
		});

		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.insets = new Insets(50, 50, 20, 50);
		frame.add(registrarse, constraints);
	}

	private void setDarkModeToggle() {
		modoIcon.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(DARK_ROUTE)).getImage()));
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 10, 10, 10);
		frame.add(modoIcon, constraints);

		switchButton = new SteelCheckBox();
		switchButton.setText(" ");
		switchButton.setRised(false);
		switchButton.setColored(true);
		switchButton.setBackground(colorFondo);

		switchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				darkModeActive = !darkModeActive;
				String iconRoute = "";
				if(darkModeActive) {
					colorFondo = Colores.DM_MAIN.getColor();
					colorLabel = Colores.DM_LABEL.getColor();
					colorTextFields = Colores.DM_TEXTFIELD.getColor();
					colorBordeTextfield = Colores.DM_TEXTFIELD_BORDER.getColor();
					colorSombraTextfield = Colores.DM_TEXTFIELD_SHADE.getColor();
					colorHiperlink = Colores.DM_HYPERLINK.getColor();
					colorHiperlinkActivo = Colores.DM_HYPERLINK_ENTERED.getColor();
					colorTextFieldContenido = Colores.DM_TEXTFIELD_CONTENT.getColor();
					iconRoute = LIGHT_ROUTE;
				}
				else {
					colorFondo = Colores.LM_MAIN.getColor();
					colorLabel = Colores.LM_LABEL.getColor();
					colorTextFields = Colores.LM_TEXTFIELD.getColor();
					colorTextFields = Colores.LM_TEXTFIELD.getColor();
					colorBordeTextfield = Colores.LM_TEXTFIELD_BORDER.getColor();
					colorSombraTextfield = Colores.LM_TEXTFIELD_SHADE.getColor();
					colorHiperlink = Colores.LM_HYPERLINK.getColor();
					colorHiperlinkActivo = Colores.LM_HYPERLINK_ENTERED.getColor();
					colorTextFieldContenido = Colores.LM_TEXTFIELD_CONTENT.getColor();
					iconRoute = DARK_ROUTE;
				}

				toggleMode(iconRoute);
			}
		});

		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.insets = new Insets(0, 0, 0, 0);
		frame.add(switchButton, constraints);

	}

	private void toggleMode(String iconRoute) {
		frame.getContentPane().setBackground(colorFondo);
		username.setForeground(colorLabel);
		password.setForeground(colorLabel);
		panelFormaInicio.setBackground(colorFondo);
		switchButton.setBackground(colorFondo);
		passwordField.setBackground(colorTextFields);
		passwordField.setBorder(javax.swing.BorderFactory.createBevelBorder(1, colorBordeTextfield, colorSombraTextfield));
		usernameField.setBorder(javax.swing.BorderFactory.createBevelBorder(1, colorBordeTextfield, colorSombraTextfield));
		usernameField.setBackground(colorTextFields);
		registrarse.setForeground(colorHiperlink);
		passwordField.setForeground(colorTextFieldContenido);
		usernameField.setForeground(colorTextFieldContenido);
		modoIcon.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(iconRoute)).getImage()));
	}

	private void verificarCamposLlenos() {
		if(usernameField.getText().trim().isEmpty() || passwordField.getPassword().length == 0) {
			iniciarSesion.setEnabled(false);
			errorLabel.setText(" ");
		}
		else iniciarSesion.setEnabled(true);
	}

	private class MyDocListener implements DocumentListener
	{

		public void changedUpdate(DocumentEvent e)
		{
			verificarCamposLlenos();
		}

		public void insertUpdate(DocumentEvent e)
		{
			verificarCamposLlenos();
		}

		public void removeUpdate(DocumentEvent e)
		{
			verificarCamposLlenos();
		}

	}

}
