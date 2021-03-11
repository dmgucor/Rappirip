package guis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import extras.Conexion;
import extras.LetterDocumentFilter;
import extras.LimitDocumentFilter;
import extras.SpringUtilities;
import modulos.Registro;

public class RegistroGUI {

	JFrame frame;
	GridBagConstraints constraints;
	JTextField usernameField;
	JPasswordField passwordField;
	JPasswordField confirmarPassField;
	JTextField nombreField;
	Registro registroLogica;
	ArrayList<AvatarLabel> listaAvatares;
	boolean darkModeActive;
	JLabel errorPassword;
	JLabel errorMatchingPass;
	JButton registrar;
	JFrame loginFrame;

	static String DM_FONDO_COLOR = "#000000";
	static String DM_LABEL_COLOR_ = "#FFFFFF";
	static String LM_FONDO_COLOR = "#EEEEEE";
	static String LM_LABEL_COLOR = "#000000";

	int avatarActivo = 1;

	public RegistroGUI(JFrame loginFrame, Conexion conexion, boolean darkModeActive) {
		loginFrame.setVisible(false);
		this.loginFrame = loginFrame;

		//Inicializar atributos
		constraints = new GridBagConstraints();
		usernameField = new JTextField(20);
		passwordField = new JPasswordField(20);
		confirmarPassField = new JPasswordField(20);
		nombreField = new JTextField(20);
		registroLogica = new Registro(conexion);
		this.darkModeActive = darkModeActive;

		//Configuraci�n de la ventana
		frame = new JFrame("Registrar usuario");
		frame.setResizable(false);
		frame.setLayout(new GridBagLayout());
		if (darkModeActive)
			frame.getContentPane().setBackground(Color.decode(DM_FONDO_COLOR));
		else 
			frame.getContentPane().setBackground(Color.decode(LM_FONDO_COLOR));

		//configurar cierre de la ventana
		WindowListener exitListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				loginFrame.setVisible(true);
			}
		};
		frame.addWindowListener(exitListener);

		setFormaRegistro();
		setAvatares();
		setBotones();

		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);

	}

	private void setFormaRegistro() {
		String[] labels = {"Usuario: ", "Contrase�a: ","Nombre: ", "Confirmar contrase�a: " };
		//Panel que contiene la forma
		JPanel panelIzquierda = new JPanel(new SpringLayout());
		if (darkModeActive)
			panelIzquierda.setBackground(Color.decode(DM_FONDO_COLOR));
		else 
			panelIzquierda.setBackground(Color.decode(LM_FONDO_COLOR));

		//Document listener para detectar cambios en los campos
		DocumentListener myDocListener = new MyDocListener();
		DocumentListener passListener = new PasswordListener();
		DocumentListener matchingPassListener = new MatchPasswordListener();

		//Agregar labels y configurar textFields
		JLabel username = new JLabel(labels[0], JLabel.TRAILING);
		if(darkModeActive)
			username.setForeground(Color.decode(DM_LABEL_COLOR_));
		else 
			username.setForeground(Color.decode(LM_LABEL_COLOR));
		panelIzquierda.add(username);
		((AbstractDocument)usernameField.getDocument()).setDocumentFilter(new LimitDocumentFilter(25));
		username.setLabelFor(usernameField);
		usernameField.getDocument().addDocumentListener(myDocListener);
		panelIzquierda.add(usernameField);

		JLabel password = new JLabel(labels[1], JLabel.TRAILING);
		if(darkModeActive)
			password.setForeground(Color.decode(DM_LABEL_COLOR_));
		panelIzquierda.add(password);
		((AbstractDocument)passwordField.getDocument()).setDocumentFilter(new LimitDocumentFilter(20));
		password.setLabelFor(passwordField);
		passwordField.getDocument().addDocumentListener(passListener);
		passwordField.getDocument().addDocumentListener(myDocListener);
		panelIzquierda.add(passwordField);

		SpringUtilities.makeCompactGrid(panelIzquierda,
				2, 2, //rows, cols
				6, 6,        //initX, initY
				6, 10); 		//xPad, yPad


		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(20, 20, 0, 10);

		frame.add(panelIzquierda, constraints);

		//Panel de la derecha
		JPanel panelDerecha = new JPanel(new SpringLayout());
		if(darkModeActive)
			panelDerecha.setBackground(Color.decode(DM_FONDO_COLOR));
		else
			panelDerecha.setBackground(Color.decode(LM_FONDO_COLOR));

		JLabel nombre = new JLabel(labels[2], JLabel.TRAILING);
		if(darkModeActive)
			nombre.setForeground(Color.decode(DM_LABEL_COLOR_));
		else
			nombre.setForeground(Color.decode(LM_LABEL_COLOR));

		panelDerecha.add(nombre);
		((AbstractDocument)nombreField.getDocument()).setDocumentFilter(new LimitDocumentFilter(35));
		((AbstractDocument)nombreField.getDocument()).setDocumentFilter(new LetterDocumentFilter());
		nombre.setLabelFor(nombreField);
		nombreField.getDocument().addDocumentListener(myDocListener);
		panelDerecha.add(nombreField);

		JLabel confirmarPass = new JLabel(labels[3], JLabel.TRAILING);
		if (darkModeActive)
			confirmarPass.setForeground(Color.decode(DM_LABEL_COLOR_));
		else
			confirmarPass.setForeground(Color.decode(LM_LABEL_COLOR));
		panelDerecha.add(confirmarPass);
		((AbstractDocument)confirmarPassField.getDocument()).setDocumentFilter(new LimitDocumentFilter(20));
		confirmarPass.setLabelFor(confirmarPassField);
		confirmarPassField.getDocument().addDocumentListener(matchingPassListener);
		confirmarPassField.getDocument().addDocumentListener(myDocListener);
		confirmarPassField.setEnabled(false);
		panelDerecha.add(confirmarPassField);

		SpringUtilities.makeCompactGrid(panelDerecha,
				2, 2, //rows, cols
				6, 6,        //initX, initY
				6, 10); 		//xPad, yPad

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(20, 10, 0, 20);

		frame.add(panelDerecha, constraints);

		//Labels para advertencias
		errorPassword = new JLabel(" ");
		errorPassword.setForeground(Color.RED);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(0, 195, 20, 0);
		frame.add(errorPassword, constraints);

		errorMatchingPass = new JLabel(" ");
		errorMatchingPass.setForeground(Color.RED);
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.insets = new Insets(0, 170, 20, 0);
		frame.add(errorMatchingPass, constraints);

		//Separador
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;

		JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
		Dimension d = separator.getPreferredSize();
		d.width = frame.getWidth() + 600;
		separator.setPreferredSize(d);
		frame.add(separator, constraints);

	}

	public void setAvatares() {
		ResultSet avatares = registroLogica.getAvatares();
		BufferedImage im;
		JPanel avatarPanel = new JPanel(new GridBagLayout());
		if(darkModeActive)
			avatarPanel.setBackground(Color.decode(DM_FONDO_COLOR));
		else
			avatarPanel.setBackground(Color.decode(LM_FONDO_COLOR));
		listaAvatares = new ArrayList<RegistroGUI.AvatarLabel>();

		//T�tulo del panel
		JLabel avatarTitulo = new JLabel("Selecciona un avatar");
		if(darkModeActive)
			avatarTitulo.setForeground(Color.decode(DM_LABEL_COLOR_));
		else
			avatarTitulo.setForeground(Color.decode(LM_LABEL_COLOR));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 5;
		constraints.insets = new Insets(0, 0, 20, 0);
		avatarPanel.add(avatarTitulo, constraints);

		try {
			int posx = 0;
			while(avatares.next()) {
				im = ImageIO.read(avatares.getBinaryStream("imagen"));

				AvatarLabel avatar = new AvatarLabel(avatares.getInt("avatar_id"));
				avatar.setIcon(new ImageIcon(im));

				if(avatar.getId() == 1)
					avatar.setBorder(BorderFactory.createLineBorder(Color.decode("#73d3b9"), 2));

				listaAvatares.add(avatar);

				avatar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						System.out.println(avatar.getId());

						if (avatarActivo != avatar.getId()) {
							for(AvatarLabel i : listaAvatares) {
								if(i.getId() != avatar.getId())
									i.setBorder(null);
								else
									i.setBorder(BorderFactory.createLineBorder(Color.decode("#73d3b9"), 2));
							}
						}
						avatarActivo = avatar.getId();
					}

					@Override
					public void mouseEntered(MouseEvent e) {

					}

					@Override
					public void mouseExited(MouseEvent e) {

					}
				});

				constraints.gridx = posx++;
				constraints.gridy = 1;
				constraints.gridwidth = 1;
				constraints.insets = new Insets(20, 20, 20, 20);
				avatarPanel.add(avatar, constraints);				

			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		frame.add(avatarPanel, constraints);

	}

	@SuppressWarnings("serial")
	private class AvatarLabel extends JLabel{
		int id;
		private AvatarLabel(int id) {
			this.id = id;
		}

		private int getId() {
			return id;
		}
	}

	private void setBotones() {
		JPanel botonesPanel  = new JPanel(new GridBagLayout());
		if(darkModeActive)
			botonesPanel.setBackground(Color.decode(DM_FONDO_COLOR));
		else
			botonesPanel.setBackground(Color.decode(LM_FONDO_COLOR));

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); 
				loginFrame.setVisible(true);
			}
		});

		registrar = new JButton("Registrarse");
		registrar.setEnabled(false);
		registrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				registrarUsuario();
			}
		});

		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 50, 0, 0);
		botonesPanel.add(cancelar, constraints);

		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		Dimension d = separator.getPreferredSize();
		d.height = cancelar.getPreferredSize().height;
		separator.setPreferredSize(d);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 15, 0, 15);
		botonesPanel.add(separator, constraints);

		constraints.gridx = 2;
		constraints.insets = new Insets(0, 0, 0, 0);
		botonesPanel.add(registrar, constraints);

		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.insets = new Insets(0, 0, 20, 0);

		frame.add(botonesPanel, constraints);
	}

	private void registrarUsuario() {
		String res = registroLogica.registrarUsuario(usernameField.getText().trim(), passwordField.getPassword(), nombreField.getText(), avatarActivo);
		if(res.equals("Oops! Este nombre de usuario ya existe"))
			new ErrorDialog(res, frame, darkModeActive);
		else {
			new RegisterDialog(res, frame, darkModeActive);
			loginFrame.setVisible(true);
		}
	}

	public class PasswordListener implements DocumentListener{

		public void checkPassword() {
			if(passwordField.getPassword().length < 6) {
				errorPassword.setText("M�nimo 6 caracteres");
				confirmarPassField.setEnabled(false);
			}
			else {
				errorPassword.setText(" ");
				confirmarPassField.setEnabled(true);
			}

		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			checkPassword();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			checkPassword();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			checkPassword();
		}

	}

	public class MatchPasswordListener implements DocumentListener{

		public void checkPasswords() {
			if(!Arrays.equals(passwordField.getPassword(), confirmarPassField.getPassword())) {
				errorMatchingPass.setText("Las contrase�as no coinciden");
			}
			else errorMatchingPass.setText(" ");
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			checkPasswords();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			checkPasswords();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			checkPasswords();
		}

	}

	public class MyDocListener implements DocumentListener
	{
		public void verificarCamposLlenos() {
			if(nombreField.getText().length() > 0 && usernameField.getText().length() > 0 && 
					passwordField.getPassword().length >= 6 && Arrays.equals(passwordField.getPassword(), confirmarPassField.getPassword())) {
				registrar.setEnabled(true);				
			}
			else
				registrar.setEnabled(false);
		}

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

	@SuppressWarnings("serial")
	public class RegisterDialog extends JDialog implements ActionListener{
		JButton boton;
		String inputMessage;
		JFrame registrarFrame;

		public RegisterDialog(String inputMessage, JFrame registrarFrame, boolean darkMode) {
			//Configuraci�n del di�logo
			super(registrarFrame, "Registro exitoso", true);
			Point ubicacion = registrarFrame.getLocation();
			setLocation(ubicacion.x + 240, ubicacion.y + 100);
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					dispose();
					registrarFrame.dispose();
				}
			});

			//DarkMode
			String fondoColor = "";
			String labelColor = "";
			if(darkMode) {
				fondoColor = DM_FONDO_COLOR;
				labelColor = DM_LABEL_COLOR_;
			}
			else {
				fondoColor = LM_FONDO_COLOR;
				labelColor = LM_LABEL_COLOR;
			}

			//Info del di�logo
			this.inputMessage = inputMessage;
			this.registrarFrame = registrarFrame;

			//Panel
			JPanel panel = new JPanel(new GridBagLayout());
			panel.setBackground(Color.decode(fondoColor));
			GridBagConstraints constraints = new GridBagConstraints();
			boton = new JButton("Aceptar");
			boton.addActionListener(this);
			JLabel mensaje = new JLabel(inputMessage);
			mensaje.setForeground(Color.decode(labelColor));

			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.insets = new Insets(15, 10, 15, 10);
			panel.add(mensaje, constraints);
			constraints.gridy = 1;
			constraints.insets = new Insets(0, 0, 10, 0);
			panel.add(boton, constraints);

			this.setBackground(Color.decode(fondoColor));
			getContentPane().add(panel);
			pack();
			this.setVisible(true);

		}
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			registrarFrame.dispose();
		}
	}

	@SuppressWarnings("serial")
	public class ErrorDialog extends JDialog implements ActionListener{
		JButton boton;
		String inputMessage;
		JFrame registrarFrame;

		public ErrorDialog(String inputMessage, JFrame registrarFrame, boolean darkMode) {
			//Configuraci�n del di�logo
			super(registrarFrame, "Error", true);
			Point ubicacion = registrarFrame.getLocation();
			setLocation(ubicacion.x + 240, ubicacion.y + 100);
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			//DarkMode
			String fondoColor = "";
			String labelColor = "";
			if(darkMode) {
				fondoColor = DM_FONDO_COLOR;
				labelColor = DM_LABEL_COLOR_;
			}
			else {
				fondoColor = LM_FONDO_COLOR;
				labelColor = LM_LABEL_COLOR;
			}

			//Info del di�logo
			this.inputMessage = inputMessage;
			this.registrarFrame = registrarFrame;

			//Panel
			JPanel panel = new JPanel(new GridBagLayout());
			panel.setBackground(Color.decode(fondoColor));
			GridBagConstraints constraints = new GridBagConstraints();
			boton = new JButton("Aceptar");
			boton.addActionListener(this);
			JLabel mensaje = new JLabel(inputMessage);
			mensaje.setForeground(Color.decode(labelColor));

			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.insets = new Insets(15, 10, 15, 10);
			panel.add(mensaje, constraints);
			constraints.gridy = 1;
			constraints.insets = new Insets(0, 0, 10, 0);
			panel.add(boton, constraints);

			this.setBackground(Color.decode(fondoColor));
			getContentPane().add(panel);
			pack();
			this.setVisible(true);

		}
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}


}
