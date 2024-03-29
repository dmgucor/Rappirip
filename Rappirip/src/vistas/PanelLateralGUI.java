package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controladores.ControladorContenido;
import eu.hansolo.custom.SteelCheckBox;
import extras.ColoresMain;
import extras.RoundedPanel;
import modelos.UsuarioModelo;

@SuppressWarnings("serial")
public class PanelLateralGUI extends JPanel{
	//Tipos de usuario
	static char ADMIN = 'A';
	static char CLIENTE = 'C';
	static char RESTAURANTE = 'R';


	UsuarioModelo usuario;

	JLabel modoIcon;
	JPanel panelInfo;
	JLabel  nombre;
	SteelCheckBox switchButton;
	RoundedPanel panelCarrito;
	JPanel acciones;
	JLabel pedir;
	JLabel verOrdenes;
	JPanel switchPanel;
	JPanel mainPanel;
	JPanel aux;
	JLabel editIcon;
	String rutaIconoEditar;
	JPanel logout;
	JPanel bottomPanel;
	JLabel logoutIcono;

	boolean darkMode;

	static String imgCarrito = "/cart.png";
	static String LIGHT_ROUTE = "/blanco.png";
	static String DARK_ROUTE = "/negro.png";
	static String LIGHT_EDIT = "/edit-light.png";
	static String DARK_EDIT = "/edit-dark.png";
	static String LOGOUT = "/logout.png";

	//Colores del tema
	Color colorFondo;
	Color colorLabel;
	Color colorFondoLabel;


	public PanelLateralGUI(boolean darkMode, UsuarioModelo usuario) {

		//Inicialización de atributos
		this.usuario = usuario;
		modoIcon = new JLabel();
		this.darkMode = darkMode;

		if(darkMode) {
			colorFondo = ColoresMain.DM_MAIN.getColor();
			colorLabel = ColoresMain.DM_MAIN_LABEL.getColor();
			rutaIconoEditar = LIGHT_EDIT;
			colorFondoLabel = ColoresMain.DM_LABEL_BACKGROUND.getColor();
		}
		else {
			colorFondo = ColoresMain.LM_MAIN.getColor();
			colorLabel = ColoresMain.LM_MAIN_LABEL.getColor();
			rutaIconoEditar = DARK_EDIT;
			colorFondoLabel = ColoresMain.LM_LABEL_BACKGROUND.getColor();
		}
		//Configuración del panel
		this.setLayout(new BorderLayout());

		switch(usuario.getTipoUsuario()) {
		case 'C':
			setmainPanel();
		}


		this.setVisible(true);

	}

	public void setmainPanel() {
		mainPanel = new JPanel();
		setInfoPanel();
		setCarrito();
		setAcciones();
		setToggleDarkMode();

		mainPanel.add(panelInfo);
		mainPanel.add(panelCarrito);
		mainPanel.add(acciones);

		mainPanel.setBackground(colorFondo);
		this.add(mainPanel, BorderLayout.CENTER);
	}

	public void setInfoPanel() {
		panelInfo = new JPanel(new GridBagLayout());
		panelInfo.setBackground(colorFondo);
		GridBagConstraints cons = new GridBagConstraints();

		JLabel icono = new JLabel();
		icono.setIcon(new ImageIcon(new javax.swing.ImageIcon(usuario.getAvatar()).getImage().getScaledInstance(80,80 , Image.SCALE_SMOOTH)));
		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 2;
		cons.insets = new Insets(40, 40, 20, 0);
		panelInfo.add(icono, cons);

		nombre = new JLabel(usuario.getUsername());
		nombre.setFont(new Font("MANOLETE", Font.PLAIN, 30));
		nombre.setForeground(colorLabel);
		cons.weightx = 1;
		cons.gridy = 1;
		cons.insets = new Insets(0, 0, 100, 0);
		panelInfo.add(nombre, cons);

		editIcon = new JLabel();
		editIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		editIcon.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(rutaIconoEditar)).getImage().getScaledInstance(20,20 , Image.SCALE_SMOOTH)));
		cons.gridy = 1;
		cons.gridx = 1;
		cons.insets = new Insets(0, 20, 100, 0);
		panelInfo.add(editIcon, cons);


	}

	public void setCarrito() {
		panelCarrito = new RoundedPanel(new GridBagLayout(), colorLabel);
		panelCarrito.setPreferredSize(new Dimension(258-40, 60));
		panelCarrito.setBackground(colorLabel);
		panelCarrito.setBorder(null);
		GridBagConstraints cons = new GridBagConstraints();

		JLabel iconoCarrito= new JLabel();
		iconoCarrito.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(imgCarrito)).getImage()));
		cons.gridx = 0;
		cons.gridy = 0;
		cons.insets = new Insets(0, 30, 0, 40);
		panelCarrito.add(iconoCarrito, cons);

		JLabel items = new JLabel("0");
		items.setForeground(Color.WHITE);
		items.setFont(new Font("MANOLETE", Font.PLAIN, 20));
		cons.gridx = 1;
		panelCarrito.add(items, cons);


	}

	public void setAcciones() {
		acciones = new JPanel(new GridBagLayout());
		acciones.setBackground(colorFondo);
		GridBagConstraints cons = new GridBagConstraints();
		Dimension labelDimension = new Dimension(258, 40);

		pedir = new JLabel("Pide ahora", SwingConstants.CENTER);
		pedir.setForeground(colorLabel);
		pedir.setFont(new Font("Comfortaa", Font.PLAIN, 22));
		pedir.setMinimumSize(labelDimension);
		pedir.setPreferredSize(labelDimension);
		pedir.setMaximumSize(labelDimension);
		pedir.setBackground(colorFondo);
		pedir.setOpaque(true);
		pedir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cons.insets = new Insets(70, 0, 0, 0);
		acciones.add(pedir, cons);
		verOrdenes = new JLabel("Mis órdenes", SwingConstants.CENTER);
		verOrdenes.setForeground(colorLabel);
		verOrdenes.setMinimumSize(labelDimension);
		verOrdenes.setPreferredSize(labelDimension);
		verOrdenes.setMaximumSize(labelDimension);
		verOrdenes.setBackground(colorFondoLabel);
		verOrdenes.setBackground(colorFondo);
		verOrdenes.setOpaque(true);
		verOrdenes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		verOrdenes.setFont(new Font("Comfortaa", Font.PLAIN, 22));
		cons.gridy = 1;
		cons.insets = new Insets(15, 0, 0, 0);
		acciones.add(verOrdenes, cons);

	}




	public void setToggleDarkMode() {
		switchPanel = new JPanel(new GridBagLayout());
		switchPanel.setBackground(colorFondo);
		GridBagConstraints cons = new GridBagConstraints();


		modoIcon.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(DARK_ROUTE)).getImage()));
		cons.gridwidth = 1;
		cons.insets = new Insets(0, 10, 10, 10);
		switchPanel.add(modoIcon, cons);

		switchButton = new SteelCheckBox();
		switchButton.setText(" ");
		switchButton.setRised(false);
		switchButton.setColored(true);
		switchButton.setBackground(colorFondo);

		switchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				darkMode = !darkMode;
				String iconRoute = "";
				if(darkMode) {
					colorFondo = ColoresMain.DM_MAIN.getColor();
					colorLabel = ColoresMain.DM_MAIN_LABEL.getColor();
					iconRoute = LIGHT_ROUTE;
					rutaIconoEditar = LIGHT_EDIT;
					colorFondoLabel = ColoresMain.DM_LABEL_BACKGROUND.getColor();
				}
				else {
					colorFondo = ColoresMain.LM_MAIN.getColor();
					colorLabel = ColoresMain.LM_MAIN_LABEL.getColor();
					iconRoute = DARK_ROUTE;
					rutaIconoEditar = DARK_EDIT;
					colorFondoLabel = ColoresMain.LM_LABEL_BACKGROUND.getColor();
				}

				toggleMode(iconRoute);
			}
		});

		cons.insets = new Insets(0, 0, 0, 0);
		cons.gridx = 1;
		switchPanel.add(switchButton, cons);

		//Panel que contiene el switch y el logout
		bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setBackground(colorFondo);
		cons = new GridBagConstraints();

		aux = new JPanel(new FlowLayout(FlowLayout.LEFT));
		aux.setBackground(colorFondo);
		aux.add(switchPanel);
		bottomPanel.add(aux, cons);

		logout = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		logout.setBackground(colorFondo);
		logoutIcono = new JLabel();
		logoutIcono.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(LOGOUT)).getImage().getScaledInstance(25,25 , Image.SCALE_SMOOTH)));
		logout.add(logoutIcono);
		cons.gridx = 1;
		cons.insets = new Insets(0, 20, 10, 0);
		bottomPanel.add(logout, cons);

		this.add(bottomPanel, BorderLayout.PAGE_END);

	}

	private void toggleMode(String iconRoute) {

		this.setBackground(colorFondo);
		panelInfo.setBackground(colorFondo);
		switchButton.setBackground(colorFondo);
		nombre.setForeground(colorLabel);
		panelCarrito.setBorderColor(colorLabel);
		panelCarrito.setBackground(colorLabel);
		acciones.setBackground(colorFondo);
		pedir.setForeground(colorLabel);
		verOrdenes.setForeground(colorLabel);
		switchPanel.setBackground(colorFondo);
		mainPanel.setBackground(colorFondo);
		aux.setBackground(colorFondo);
		editIcon.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(rutaIconoEditar)).getImage().getScaledInstance(20,20 , Image.SCALE_SMOOTH)));
		bottomPanel.setBackground(colorFondo);
		logout.setBackground(colorFondo);
		modoIcon.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(iconRoute)).getImage().getScaledInstance(25,25 , Image.SCALE_SMOOTH)));
		pedir.setBackground(colorFondo);
		verOrdenes.setBackground(colorFondo);
	}

	public JLabel getLabelOrdenes() {
		return verOrdenes;
	}

	public JLabel getLabelPedir() {
		return pedir;
	}

	public Color getColorFondoLabel() {
		return colorFondoLabel;
	}

	public Color getColorFondo() {
		return colorFondo;
	}

	public JLabel getLabelLogout() {
		return logoutIcono;
	}

	public JLabel getLabelEditar() {
		return editIcon;
	}

	public void setControlador(ControladorContenido c) {
		logoutIcono.addMouseListener(c);
		verOrdenes.addMouseListener(c);
		pedir.addMouseListener(c);
		editIcon.addMouseListener(c);
	}

	public boolean getdarkMode() {
		return darkMode;
	}

}
