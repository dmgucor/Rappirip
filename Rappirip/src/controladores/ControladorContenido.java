package controladores;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import modelos.ModeloContenido;
import vistas.MainGUI;
import vistas.PanelLateralGUI;

public class ControladorContenido implements MouseListener {

	private MainGUI vista;
	private ModeloContenido modelo;
	
	public ControladorContenido(MainGUI vista, ModeloContenido modelo) {
		this.vista = vista;
		 this.modelo = modelo;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		PanelLateralGUI lateral = vista.getPanelLateral(); 
		if(e.getSource() == lateral.getLabelOrdenes()) {
			System.out.println("ver ordenes");
		}
		else if(e.getSource() == lateral.getLabelPedir()) {
			System.out.println("panel restaurantes");
		}
		else if(e.getSource() == lateral.getLabelLogout()) {
			vista.getFrame().dispose();
			modelo.getLogin(lateral.getdarkMode());
		}
		else if(e.getSource() == lateral.getLabelEditar()) {
			vista.setContenido(modelo.getEditarPerfilPanel(lateral.getdarkMode(), vista.getUsuario()));
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == vista.getPanelLateral().getLabelOrdenes()) {
			vista.getPanelLateral().getLabelOrdenes().setBackground(vista.getPanelLateral().getColorFondoLabel());
		}
		else if(e.getSource() == vista.getPanelLateral().getLabelPedir()) {
			vista.getPanelLateral().getLabelPedir().setBackground(vista.getPanelLateral().getColorFondoLabel());
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == vista.getPanelLateral().getLabelOrdenes()) {
			vista.getPanelLateral().getLabelOrdenes().setBackground(vista.getPanelLateral().getColorFondo());
		}
		else if(e.getSource() == vista.getPanelLateral().getLabelPedir()) {
			vista.getPanelLateral().getLabelPedir().setBackground(vista.getPanelLateral().getColorFondo());
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
