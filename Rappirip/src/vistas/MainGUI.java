package vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controladores.ControladorContenido;
import modelos.ModeloContenido;
import modelos.UsuarioModelo;

public class MainGUI {

	JFrame frame;
	PanelLateralGUI lateral;
	JPanel contenido;
	GridBagConstraints constraints;
	UsuarioModelo usuario;
	boolean darkMode;
	int gridx = 0;
	int gridy = 0;

	public MainGUI(boolean darkMode, UsuarioModelo usuario) {
		//Inicialización de atributos
		constraints = new GridBagConstraints();
		this.darkMode = darkMode;
		this.usuario = usuario;

		//configuración de la ventana
		frame = new JFrame("Rappirip");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new GridBagLayout());
		frame.setPreferredSize(new Dimension(1300, 800));
		frame.pack();

		//Paneles
		lateral = new PanelLateralGUI(darkMode, usuario);
		lateral.setPreferredSize(new Dimension((int) (frame.getContentPane().getWidth()*0.2), frame.getContentPane().getHeight()));
		lateral.setMinimumSize(new Dimension((int) (frame.getContentPane().getWidth()*0.2), frame.getContentPane().getHeight()));
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		frame.add(lateral, constraints);


		contenido = new JPanel(); 
		if(darkMode) {
			contenido.setBackground(Color.BLACK);
		}
		else {
			contenido.setBackground(Color.WHITE);
		}

		contenido.setPreferredSize(new Dimension((int) (frame.getContentPane().getWidth()*0.8), frame.getContentPane().getHeight()));
		contenido.setMinimumSize(new Dimension((int) (frame.getContentPane().getWidth()*0.8), frame.getContentPane().getHeight()));

		contenido.add(new JLabel("Contenido"));
		gridx++;
		constraints.gridx = gridx; 
		frame.add(contenido, constraints);


		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		//Controlador contenido
		ModeloContenido m = new ModeloContenido();
		ControladorContenido c = new ControladorContenido(this, m);
		lateral.setControlador(c);
	}

	public void setContenido(JPanel panel) {
		panel.setPreferredSize(new Dimension((int) (frame.getContentPane().getWidth()*0.8), frame.getContentPane().getHeight()));
		panel.setMinimumSize(new Dimension((int) (frame.getContentPane().getWidth()*0.8), frame.getContentPane().getHeight()));
		frame.getContentPane().remove(contenido);
		frame.getContentPane().add(panel, constraints);
		frame.validate();
	}

	public boolean getDarkMode() {
		return darkMode;
	}

	public UsuarioModelo getUsuario() {
		return usuario;
	}

	public PanelLateralGUI getPanelLateral() {
		return lateral;
	}

	public JFrame getFrame() {
		return frame;
	}

}
