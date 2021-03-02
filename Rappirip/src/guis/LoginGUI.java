package guis;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
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
import javax.swing.text.AbstractDocument;

import extras.LimitDocumentFilter;
import extras.SpringUtilities;

public class LoginGUI {
	JFrame frame;
	String[] labels = {"Usuario: ", "Contrase�a: "};
	GridBagConstraints constraints = new GridBagConstraints();

	public LoginGUI() {
		//Configuraci�n de la ventana
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

	//Bot�n para iniciar sesi�n
	private void setBotonIniciar(JFrame frame) {
		JButton iniciarSesion = new JButton("Iniciar sesi�n");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(20, 50, 50, 50);

		frame.add(iniciarSesion, constraints);
	}

	//Forma para iniciar sesi�n
	private void setFormaInicio(JFrame frame) {
		//Panel que contiene la forma
		JPanel panel = new JPanel(new SpringLayout());

		//Agregar labels y configurar textFields
		JLabel username = new JLabel(labels[0], JLabel.TRAILING);
		panel.add(username);
		JTextField usernameField = new JTextField(20);
		((AbstractDocument)usernameField.getDocument()).setDocumentFilter(new LimitDocumentFilter(25));
		username.setLabelFor(usernameField);
		panel.add(usernameField);

		JLabel password = new JLabel(labels[1], JLabel.TRAILING);
		panel.add(password);
		JPasswordField passwordField = new JPasswordField(20);
		((AbstractDocument)passwordField.getDocument()).setDocumentFilter(new LimitDocumentFilter(20));
		password.setLabelFor(passwordField);
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
				// the user clicks on the label
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

}
