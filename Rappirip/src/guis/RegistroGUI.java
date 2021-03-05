package guis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
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
	JPasswordField passwordField;
	JTextField confirmarPassField;
	JTextField nombreField;
	Registro registroLogica;
	ArrayList<AvatarLabel> listaAvatares;
	boolean darkModeActive;

	static String DM_FONDO_COLOR = "#000000";
	static String DM_LABEL_COLOR_ = "#FFFFFF";
	static String LM_FONDO_COLOR = "#EEEEEE";
	static String LM_LABEL_COLOR = "#000000";

	int avatarActivo = 1;

	public RegistroGUI(JFrame loginFrame, Conexion conexion, boolean darkModeActive) {
		loginFrame.setVisible(false);

		//Inicializar atributos
		constraints = new GridBagConstraints();
		usernameField = new JTextField(20);
		passwordField = new JPasswordField(20);
		confirmarPassField = new JTextField(20);
		nombreField = new JTextField(20);
		registroLogica = new Registro(conexion);
		this.darkModeActive = darkModeActive;

		//Configuración de la ventana
		frame = new JFrame();
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
		String[] labels = {"Usuario: ", "Contraseña: ","Nombre: ", "Confirmar contraseña: " };
		//Panel que contiene la forma
		JPanel panelIzquierda = new JPanel(new SpringLayout());
		if (darkModeActive)
			panelIzquierda.setBackground(Color.decode(DM_FONDO_COLOR));
		else 
			panelIzquierda.setBackground(Color.decode(LM_FONDO_COLOR));

		//Document listener para detectar cambios en los campos
		DocumentListener myDocListener = new MyDocListener();

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
		passwordField.getDocument().addDocumentListener(myDocListener);
		panelIzquierda.add(passwordField);

		SpringUtilities.makeCompactGrid(panelIzquierda,
				2, 2, //rows, cols
				6, 6,        //initX, initY
				6, 15); 		//xPad, yPad

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(20, 20, 10, 10);

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
		confirmarPassField.getDocument().addDocumentListener(myDocListener);
		panelDerecha.add(confirmarPassField);

		SpringUtilities.makeCompactGrid(panelDerecha,
				2, 2, //rows, cols
				6, 6,        //initX, initY
				6, 15); 		//xPad, yPad

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(20, 10, 10, 20);

		frame.add(panelDerecha, constraints);

		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridx = 0;
		constraints.gridy = 1;
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

		//Título del panel
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
		constraints.gridy = 2;
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
		JButton aceptar = new JButton("Cancelar");
		JButton cancelar = new JButton("Registrarse");

		/*
		 * constraints = new GridBagConstraints(); constraints.gridx = 0;
		 * constraints.gridy = 0; constraints.insets = new Insets(0, 100, 40, 50);
		 * botonesPanel.add(aceptar, constraints); constraints.gridx = 1;
		 * constraints.insets = new Insets(0, 50, 40, 100); botonesPanel.add(cancelar,
		 * constraints);
		 * 
		 * constraints.gridx = 0; constraints.gridy = 3; constraints.gridwidth = 2;
		 * constraints.insets = new Insets(0, 0, 0, 0);
		 */

		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 50, 0, 0);
		botonesPanel.add(aceptar, constraints);

		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		Dimension d = separator.getPreferredSize();
		d.height = aceptar.getPreferredSize().height;
		separator.setPreferredSize(d);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 15, 0, 15);
		botonesPanel.add(separator, constraints);

		constraints.gridx = 2;
		constraints.insets = new Insets(0, 0, 0, 0);
		botonesPanel.add(cancelar, constraints);

		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.insets = new Insets(0, 0, 20, 0);

		frame.add(botonesPanel, constraints);
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
