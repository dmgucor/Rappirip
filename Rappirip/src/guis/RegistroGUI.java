package guis;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import extras.Conexion;
import extras.LimitDocumentFilter;
import extras.SpringUtilities;
import modulos.Registro;

public class RegistroGUI {

	JFrame frame;
	GridBagConstraints constraints;
	JTextField usernameField;
	JTextField passwordField;
	JTextField nombreField;
	Registro registroLogica;

	public RegistroGUI(JFrame loginFrame, Conexion conexion) {
		loginFrame.setVisible(false);

		//Inicializar atributos
		constraints = new GridBagConstraints();
		usernameField = new JTextField(20);
		passwordField = new JTextField(20);
		nombreField = new JTextField(30);
		registroLogica = new Registro(conexion);

		//Configuración de la ventana
		frame = new JFrame();
		frame.setBounds(loginFrame.getX(), loginFrame.getY(), 400, 500);
		frame.setResizable(false);
		frame.setLayout(new GridBagLayout());

		//configurar cierre de la ventana
		WindowListener exitListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				loginFrame.setLocation(frame.getX() , frame.getY());
				loginFrame.setVisible(true);
			}
		};
		frame.addWindowListener(exitListener);

		setFormaRegistro();
		setAvatares();

		frame.setVisible(true);
		frame.pack();

	}

	private void setFormaRegistro() {
		String[] labels = {"Usuario", "Constraseña","Nombre" };
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

		JLabel nombre = new JLabel(labels[2], JLabel.TRAILING);
		panel.add(nombre);
		((AbstractDocument)nombreField.getDocument()).setDocumentFilter(new LimitDocumentFilter(35));
		nombre.setLabelFor(nombreField);
		nombreField.getDocument().addDocumentListener(myDocListener);
		panel.add(nombreField);

		SpringUtilities.makeCompactGrid(panel,
				labels.length, 2, //rows, cols
				6, 6,        //initX, initY
				6, 15); 		//xPad, yPad

		constraints.gridx = 0;
		constraints.gridy = 0;

		frame.add(panel, constraints);
	}

	public void setAvatares() {
		ResultSet avatares = registroLogica.getAvatares();
		BufferedImage im;
		JPanel avatarPanel = new JPanel();

		try {
			while(avatares.next()) {
				im = ImageIO.read(avatares.getBinaryStream("imagen"));

				AvatarLabel avatar = new AvatarLabel(avatares.getInt("avatar_id"));
				avatar.setIcon(new ImageIcon(im));
				
				avatar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						System.out.println(avatar.getId());
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						
					}
				});
				
				avatarPanel.add(avatar);				

			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		constraints.gridx = 0;
		constraints.gridy = 1;
		frame.add(avatarPanel, constraints);

	}
	
	private class AvatarLabel extends JLabel{
		int id;
		private AvatarLabel(int id) {
			this.id = id;
		}
		
		private int getId() {
			return id;
		}
	}

	public class MyDocListener implements DocumentListener
	{

		public void changedUpdate(DocumentEvent e)
		{

		}

		public void insertUpdate(DocumentEvent e)
		{

		}

		public void removeUpdate(DocumentEvent e)
		{

		}

	}























}
