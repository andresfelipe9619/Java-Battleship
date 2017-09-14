import java.awt.GridLayout;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tablero extends JPanel implements Serializable {
	private JPanel tableroMapa;
	private JPanel tableroDisparo;
	private GridLayout cuadricula;
	private JButton[][] tablaMapa, tablaDisparo;
	private  ImageIcon agua = new ImageIcon(getClass().getResource("img/water.jpg"));
	private Barco barcos[] = new Barco[9];
	private int nporta = 1, nsub = 3, ndes = 3, nfra = 2, barDestruidos = 0,puntaje;
	private Boolean  posValida;
	
	public Tablero() {
		setLayout(null);
		cuadricula = new GridLayout(10, 10, 0, 0);

		tablaMapa = new JButton[10][10];
		tablaDisparo = new JButton[10][10];

		tableroMapa = new JPanel(cuadricula);
		tableroDisparo = new JPanel(cuadricula);

		for (int i = 0; i < tablaMapa.length; i++) {
			for (int j = 0; j < tablaMapa[i].length; j++) {
				tablaMapa[i][j] = new JButton();
				tablaMapa[i][j].setIcon(agua);
				tableroMapa.add(tablaMapa[i][j]);
			}
		}
		for (int i = 0; i < tablaDisparo.length; i++) {
			for (int j = 0; j < tablaDisparo[i].length; j++) {
				tablaDisparo[i][j] = new JButton();
				tablaDisparo[i][j].setIcon(agua);
				tableroDisparo.add(tablaDisparo[i][j]);
			}
		}

		acomodaLetras(10, 18, 10, 370);
		acomodaNumeros(26, 0, 25, 350);

		tableroMapa.setBounds(20, 30, 300, 300);
		tableroDisparo.setBounds(20, 380, 300, 300);
		add(tableroMapa);
		add(tableroDisparo);
	}

	public void acomodaLetras(int x, int y, int x1, int y1) {
		for (int i = 65; i < 75; i++) {
			JLabel abc = new JLabel(Character.toString((char) i));
			JLabel ab = new JLabel(Character.toString((char) i));
			abc.setBounds(x, y, 40, 40);
			ab.setBounds(x1, y1, 40, 40);
			add(abc);
			add(ab);
			y += 31;
			y1 += 31;
		}
	}

	public void acomodaNumeros(int x, int y, int x1, int y1) {
		for (int i = 48; i < 58; i++) {
			JLabel abc = new JLabel(Character.toString((char) i));
			JLabel ab = new JLabel(Character.toString((char) i));
			abc.setBounds(x, y, 40, 40);
			ab.setBounds(x1, y1, 40, 40);
			add(abc);
			add(ab);
			x += 31;
			x1 += 31;
		}
	}

	public void ubicaBarco(Barco b) {
		int x = b.getPosX();
		int y = b.getPosY();
		try {
			if (b.getDireccion().equals("vertical")) {//verifica la orientacion
				if (b.getNombre().equals("portaaviones")) {
					if (tablaMapa[x][y].getIcon() == agua && tablaMapa[x + 1][y].getIcon() == agua
							&& tablaMapa[x + 2][y].getIcon() == agua && tablaMapa[x + 3][y].getIcon() == agua) { 
						posValida = true;						//si las casillas en que se va ubicar estan vacias,entonces ubica el barco
						for (int i = 0; i < b.getCuerpo().length; i++) {
							tablaMapa[x][y].setIcon(b.getCuerpo()[i]);
							x++;
						}

					} else if (tablaMapa[x][y].getIcon() != agua || tablaMapa[x + 1][y].getIcon() != agua
							|| tablaMapa[x + 2][y].getIcon() != agua || tablaMapa[x + 3][y].getIcon() != agua) {//si hay almenos una casilla no vacia no podra ubicarse e barco
						posValida = false;

					}

				} else if (b.getNombre().equals("submarino")) {
					if (tablaMapa[x][y].getIcon() == agua && tablaMapa[x + 1][y].getIcon() == agua
							&& tablaMapa[x + 2][y].getIcon() == agua) {
						posValida = true;
						for (int i = 0; i < b.getCuerpo().length; i++) {
							tablaMapa[x][y].setIcon(b.getCuerpo()[i]);
							x++;
						}

					} else if (tablaMapa[x][y].getIcon() != agua || tablaMapa[x + 1][y].getIcon() != agua
							|| tablaMapa[x + 2][y].getIcon() != agua) {
						posValida = false;

					}

				} else if (b.getNombre().equals("destructor")) {
					if (tablaMapa[x][y].getIcon() == agua && tablaMapa[x + 1][y].getIcon() == agua) {
						posValida = true;
						for (int i = 0; i < b.getCuerpo().length; i++) {
							tablaMapa[x][y].setIcon(b.getCuerpo()[i]);
							x++;
						}
					} else if (tablaMapa[x][y].getIcon() != agua || tablaMapa[x + 1][y].getIcon() != agua) {
						posValida = false;

					}
				} else if (b.getNombre().equals("fragata")) {
					if (tablaMapa[x][y].getIcon() == agua) {
						posValida = true;
						for (int i = 0; i < b.getCuerpo().length; i++) {
							tablaMapa[x][y].setIcon(b.getCuerpo()[i]);

						}
					} else if (tablaMapa[x][y].getIcon() != agua) {
						posValida = false;

					}
				}
			} else if (b.getDireccion().equals("horizontal")) {
				if (b.getNombre().equals("portaaviones")) {
					if (tablaMapa[x][y].getIcon() == agua && tablaMapa[x][y + 1].getIcon() == agua
							&& tablaMapa[x][y + 2].getIcon() == agua && tablaMapa[x][y + 3].getIcon() == agua) {
						posValida = true;
						for (int i = 0; i < b.getCuerpo().length; i++) {
							tablaMapa[x][y].setIcon(b.getCuerpo()[i]);
							y++;
						}
					} else if (tablaMapa[x][y].getIcon() != agua || tablaMapa[x][y + 1].getIcon() != agua
							|| tablaMapa[x][y + 2].getIcon() != agua || tablaMapa[x][y + 3].getIcon() != agua) {
						posValida = false;

					}

				} else if (b.getNombre().equals("submarino")) {
					if (tablaMapa[x][y].getIcon() == agua && tablaMapa[x][y + 1].getIcon() == agua
							&& tablaMapa[x][y + 2].getIcon() == agua) {
						posValida = true;
						for (int i = 0; i < b.getCuerpo().length; i++) {
							tablaMapa[x][y].setIcon(b.getCuerpo()[i]);
							y++;
						}
					} else if (tablaMapa[x][y].getIcon() != agua || tablaMapa[x][y + 1].getIcon() != agua
							|| tablaMapa[x][y + 2].getIcon() != agua) {
						posValida = false;

					}

				} else if (b.getNombre().equals("destructor")) {
					if (tablaMapa[x][y].getIcon() == agua && tablaMapa[x][y + 1].getIcon() == agua) {
						posValida = true;
						for (int i = 0; i < b.getCuerpo().length; i++) {
							tablaMapa[x][y].setIcon(b.getCuerpo()[i]);
							y++;
						}
					} else if (tablaMapa[x][y].getIcon() != agua || tablaMapa[x][y + 1].getIcon() != agua) {
						posValida = false;

					}
				} else if (b.getNombre().equals("fragata")) {
					if (tablaMapa[x][y].getIcon() == agua) {
						posValida = true;
						for (int i = 0; i < b.getCuerpo().length; i++) {
							tablaMapa[x][y].setIcon(b.getCuerpo()[i]);

						}
					} else if (tablaMapa[x][y].getIcon() != agua) {
						posValida = false;

					}
				}
			}
		} catch (Exception e) {// manejador de excepcion
			posValida = false;
			JOptionPane.showMessageDialog(null, "Fuera del Rango");
		}
	}

	public JPanel getTableroMapa() {
		return tableroMapa;
	}

	public void setTableroMapa(JPanel tableroMapa) {
		this.tableroMapa = tableroMapa;
	}

	public JPanel getTableroDisparo() {
		return tableroDisparo;
	}

	public void setTableroDisparo(JPanel tableroDisparo) {
		this.tableroDisparo = tableroDisparo;
	}

	public JButton[][] getTablaMapa() {
		return tablaMapa;
	}

	public void setTablaMapa(JButton[][] tabla) {
		this.tablaMapa = tabla;
	}

	public JButton[][] getTablaDisparo() {
		return tablaDisparo;
	}

	public void setTablaDisparo(JButton[][] tablaEnemigo) {
		this.tablaDisparo = tablaEnemigo;
	}

	public Barco[] getBarcos() {
		return barcos;
	}

	public void setBarcos(Barco[] barcos) {
		this.barcos = barcos;
	}

	public ImageIcon getAgua() {
		return agua;
	}

	public void setAgua(ImageIcon agua) {
		this.agua = agua;
	}

	public Boolean getPosValida() {
		return posValida;
	}

	public void setPosValida(Boolean posValida) {
		this.posValida = posValida;
	}

	public int getNporta() {
		return nporta;
	}

	public void setNporta(int nporta) {
		this.nporta = nporta;
	}

	public int getNsub() {
		return nsub;
	}

	public void setNsub(int nsub) {
		this.nsub = nsub;
	}

	public int getNdes() {
		return ndes;
	}

	public void setNdes(int ndes) {
		this.ndes = ndes;
	}

	public int getNfra() {
		return nfra;
	}

	public void setNfra(int nfra) {
		this.nfra = nfra;
	}

	public int getBarDestruidos() {
		return barDestruidos;
	}

	public void setBarDestruidos(int barDestruidos) {
		this.barDestruidos = barDestruidos;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

}
