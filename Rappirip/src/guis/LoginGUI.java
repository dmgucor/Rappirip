package guis;

import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

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

	public LoginGUI(Conexion conexion) { 
		//Inicializar atributos
		constraints = new GridBagConstraints();
		usernameField = new JTextField(20);
		passwordField = new JPasswordField(20);	
		iniciarSesion = new JButton("Iniciar sesión");
		loginLogica = new Login(conexion);
		this.conexion = conexion;

		//Configuración de la ventana
		frame = new JFrame();
		frame.setBounds(500, 200, 200, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());

		setLogo(frame);
		setFormaInicio(frame);
		setBotonIniciar(frame);
		setRegistrar(frame);

		frame.setVisible(true);
		frame.pack();
	}

	//Imagen con el logo
	private void setLogo(JFrame frame) {
		JLabel picLogo = new JLabel();
		picLogo.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/logo.png")).getImage().getScaledInstance(357-150, 234-100, Image.SCALE_SMOOTH)));

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(50, 50, 10, 50);

		frame.add(picLogo, constraints);
	}

	//Botón para iniciar sesión
	private void setBotonIniciar(JFrame frame) {
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(20, 50, 50, 50);
		iniciarSesion.setEnabled(false);

		//Funcionalidad del botón
		iniciarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Boolean logueado = loginLogica.login(usernameField.getText().trim(), passwordField.getPassword());
				if(!logueado) {
					JOptionPane.showMessageDialog(frame, ERROR_LOGIN, "Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		frame.add(iniciarSesion, constraints);
	}

	//Forma para iniciar sesión
	private void setFormaInicio(JFrame frame) {
		//Panel que contiene la forma
		JPanel panel = new JPanel(new SpringLayout());

		//Document listener para detectar cambios en los campos
		DocumentListener myDocListener = new MyDocListener();

		//Agregar labels y configurar textFields
		JLabel username = new JLabel(labels[0], JLabel.TRAILING);
		panel.add(username);
		((AbstractDocument)usernameField.getDocument()).setDocumentFilter(new LimitDocumentFilter(25));
		username.setLabelFor(usernameField);
		usernameField.getDocument().addDocumentListener(myDocListener);
		panel.add(usernameField);

		JLabel password = new JLabel(labels[1], JLabel.TRAILING);
		panel.add(password);
		((AbstractDocument)passwordField.getDocument()).setDocumentFilter(new LimitDocumentFilter(20));
		password.setLabelFor(passwordField);
		passwordField.getDocument().addDocumentListener(myDocListener);
		panel.add(passwordField);

		SpringUtilities.makeCompactGrid(panel,
				labels.length, 2, //rows, cols
				6, 6,        //initX, initY
				6, 6); 		//xPad, yPad

		//Constraints para el panel con la forma
		constraints.gridy = 1;
		constraints.insets = new Insets(50, 10, 10, 10);

		frame.add(panel, constraints);
	}

	private void setRegistrar(JFrame frame) {
		JLabel registrarse = new JLabel("Crea tu cuenta");
		registrarse.setForeground(Color.decode("#5c704e"));
		registrarse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		registrarse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new RegistroGUI(frame, conexion);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				registrarse.setForeground(Color.decode("#1e251a"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				registrarse.setForeground(Color.decode("#5c704e"));
			}
		});

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(50, 50, 20, 50);
		frame.add(registrarse, constraints);
	}

	public void verificarCamposLlenos() {
		if(usernameField.getText().trim().isEmpty() || passwordField.getPassword().length == 0) {
			iniciarSesion.setEnabled(false);
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
