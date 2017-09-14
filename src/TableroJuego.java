
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;

public class TableroJuego extends JFrame implements Serializable {

	// Atributos
	private JButton CambiarJugador, randomPlayer1, randomPlayer2, jugar;
	private  ImageIcon  equis, explosion;
	private JMenu menu, ayuda;
	private JMenuBar menuBar;
	private JMenuItem Guardar, Cargar, Salir, Ayuda;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Container contenedor;
	private JLabel  Lnombre[], NumeroBarco[], JLnumerobarcos[], timer,puntaje1,puntaje2;
	private JButton ubicar[];
	private JRadioButton Rhorizontal[], Rvertical[];
	private String pos;
	private boolean validarJugador, empizaJuego ;
	private static final String letras[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
	private static final String numeros[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private JComboBox comboLetras[], comboNumeros[];
	private String filas, columnas;
	private Cronometro Cronometro;
	private Tablero jugador1, jugador2;
	private  static String aguaSonido="C:/Users/AndresFelipe/Desktop/agua.wav";
	private  static String explosionSonido="C:/Users/AndresFelipe/Desktop/explosion.wav";
	private Sonido acierto,fallo,inicio;

	public static void main(String args[]) throws Exception {
	
		TableroJuego juego = new TableroJuego();
		juego.setVisible(true);

	}

	public TableroJuego() {
		// Frame
		setTitle("Batalla Naval");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		ImageIcon icono = new ImageIcon(getClass().getResource("/img/IconW.jpg"));
		this.setIconImage(icono.getImage());
		Cronometro = new Cronometro();
	
		// Container
		contenedor = getContentPane();
		contenedor.setLayout(null);
	
		// Listener
		ManejadorBotones handler = new ManejadorBotones();

		// Menu
		menu = new JMenu("Archivo");
		ayuda = new JMenu("Ayuda");
		menuBar = new JMenuBar();
		Guardar = new JMenuItem("Guardar Partida");
		Cargar = new JMenuItem("Cargar Partida");
		Salir = new JMenuItem("Salir");
		Ayuda = new JMenuItem("instrucciones");

		
		// Buttons
		CambiarJugador = new JButton("cambio de  jugador");
		CambiarJugador.setBounds(610, 5, 200, 20);
		CambiarJugador.setEnabled(false);
		contenedor.add(CambiarJugador);

		randomPlayer1 = new JButton("Random Player1");
		randomPlayer1.setBounds(40, 520, 150, 20);
		contenedor.add(randomPlayer1);

		randomPlayer2 = new JButton("Random Player2");
		randomPlayer2.setBounds(40, 560, 150, 20);
		randomPlayer2.setEnabled(false);
		contenedor.add(randomPlayer2);

		jugar = new JButton("jugar");
		jugar.setBounds(645, 360, 100, 20);
		contenedor.add(jugar);

		// J MENU
		menuBar.add(menu);
		menuBar.add(ayuda);
		menu.add(Cargar);
		menu.add(Guardar);
		menu.add(Salir);
		ayuda.add(Ayuda);
		setJMenuBar(menuBar);
		CambiarJugador.addActionListener(handler);

		// ComboBox
		int y1 = 80;
		comboLetras = new JComboBox[4];
		comboNumeros = new JComboBox[4];

		for (int i = 0; i < comboLetras.length; i++) {
			comboLetras[i] = new JComboBox(letras);
			comboLetras[i].setBounds(10, y1, 60, 20);
			contenedor.add(comboLetras[i]);
			y1 += 130;
		}
		int x1 = 80;
		for (int i = 0; i < comboNumeros.length; i++) {
			comboNumeros[i] = new JComboBox(numeros);
			comboNumeros[i].setBounds(80, x1, 60, 20);
			contenedor.add(comboNumeros[i]);
			x1 += 130;
		}

		for (int i = 0; i < comboLetras.length; i++) {
			comboLetras[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					JComboBox cb = (JComboBox) evt.getSource();
					filas = (String) cb.getSelectedItem();
				}
			});
		}
		for (int i = 0; i < comboNumeros.length; i++) {
			comboNumeros[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					JComboBox cb = (JComboBox) evt.getSource();
					columnas = (String) cb.getSelectedItem();
				}
			});
		}

		// Buttons
		ubicar = new JButton[4];
		int y = 80;
		for (int i = 0; i < ubicar.length; i++) {
			ubicar[i] = new JButton("Ubicar");
			ubicar[i].setBounds(160, y, 80, 20);
			ubicar[i].addActionListener(handler);
			contenedor.add(ubicar[i]);
			y += 130;
		}

		// Labels
		int j1 = 60;
		JLnumerobarcos = new JLabel[4];
		for (int i = 0; i < JLnumerobarcos.length; i++) {
			JLnumerobarcos[i] = new JLabel("Barcos: ");
			JLnumerobarcos[i].setBounds(20, j1, 80, 20);
			contenedor.add(JLnumerobarcos[i]);
			j1 += 130;
		}

		int r2 = 60;
		NumeroBarco = new JLabel[4];
		for (int i = 0; i < NumeroBarco.length; i++) {
			NumeroBarco[i] = new JLabel("");
			NumeroBarco[i].setBounds(70, r2, 20, 20);
			contenedor.add(NumeroBarco[i]);
			r2 += 130;
		}
		NumeroBarco[0].setText("1");
		NumeroBarco[1].setText("3");
		NumeroBarco[2].setText("3");
		NumeroBarco[3].setText("2");

		Lnombre = new JLabel[4];
		Lnombre[0] = new JLabel("PortaAviones");
		Lnombre[0].setBounds(120, 5, 80, 20);
		contenedor.add(Lnombre[0]);
		Lnombre[1] = new JLabel("Submarino");
		Lnombre[1].setBounds(120, 130, 80, 20);
		contenedor.add(Lnombre[1]);
		Lnombre[2] = new JLabel("Destructor");
		Lnombre[2].setBounds(120, 265, 80, 20);
		contenedor.add(Lnombre[2]);
		Lnombre[3] = new JLabel("Fragata");
		Lnombre[3].setBounds(120, 395, 80, 20);
		contenedor.add(Lnombre[3]);

		
		timer = Cronometro.getTiempo();
		timer.setBounds(0, 600, 250, 40);
		contenedor.add(timer);
		
		
		puntaje1=new JLabel("Puntaje J1:  0");
		puntaje1.setBounds(350,5, 150, 20);
		contenedor.add(puntaje1);
		puntaje2=new JLabel("Puntaje J2:  0");
		puntaje2.setBounds(900,5, 150, 20);
		contenedor.add(puntaje2);
		
		
		// Radio Button Horizontal
		int Y3 = 20;
		Rhorizontal = new JRadioButton[4];
		for (int i = 0; i < Rhorizontal.length; i++) {
			Rhorizontal[i] = new JRadioButton("Horizontal");
			Rhorizontal[i].setBounds(5, Y3, 100, 20);
			contenedor.add(Rhorizontal[i]);
			Y3 += 130;
		}

		// Radio Button Vertical
		int X3 = 40;
		Rvertical = new JRadioButton[4];
		for (int i = 0; i < Rvertical.length; i++) {
			Rvertical[i] = new JRadioButton("vertical");
			Rvertical[i].setBounds(5, X3, 100, 20);
			contenedor.add(Rvertical[i]);
			X3 += 130;
		}

		// Button Groups
		ButtonGroup g1 = new ButtonGroup();
		g1.add(Rhorizontal[0]);
		g1.add(Rvertical[0]);
		ButtonGroup g2 = new ButtonGroup();
		g2.add(Rhorizontal[1]);
		g2.add(Rvertical[1]);
		ButtonGroup g3 = new ButtonGroup();
		g3.add(Rhorizontal[2]);
		g3.add(Rvertical[2]);
		ButtonGroup g4 = new ButtonGroup();
		g4.add(Rhorizontal[3]);
		g4.add(Rvertical[3]);

		for (int i = 0; i < Rhorizontal.length; i++) {
			Rhorizontal[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pos = "horizontal";
				}
			});
		}
		for (int i = 0; i < Rvertical.length; i++) {
			Rvertical[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pos = "vertical";
				}
			});
		}

		// Icons

		equis = resize(new ImageIcon(getClass().getResource("img/x.jpg")));
		explosion = resize(new ImageIcon(getClass().getResource("img/explosion.jpg")));



		// Boards

		jugador1 = new Tablero();
		jugador1.setBounds(280, 50, 350, 680);
		contenedor.add(jugador1);

		jugador2 = new Tablero();
		jugador2.setBounds(800, 50, 350, 680);
		contenedor.add(jugador2);

		// Events
		for (int i = 0; i < jugador1.getTablaDisparo().length; i++) {
			for (int j = 0; j < jugador1.getTablaDisparo()[i].length; j++) {
				jugador1.getTablaDisparo()[i][j].addActionListener(handler);
				jugador2.getTablaDisparo()[i][j].addActionListener(handler);
			}
		}
		Guardar.addActionListener(handler);
		Cargar.addActionListener(handler);
		Salir.addActionListener(handler);
		Ayuda.addActionListener(handler);
		randomPlayer1.addActionListener(handler);
		randomPlayer2.addActionListener(handler);
		jugar.addActionListener(handler);
		jugar.setEnabled(false);

		// bloqueo casillas
		for (int i = 1; i < 4; i++) {
			ubicar[i].setEnabled(false);
			Rhorizontal[i].setEnabled(false);
			Rvertical[i].setEnabled(false);
			comboLetras[i].setEnabled(false);
			comboNumeros[i].setEnabled(false);
		}

		ImagenBarcos imgB = new ImagenBarcos();

		contenedor.add(imgB);

		setVisible(true);
		setSize(1200, 820);
		setResizable(true);
		setLocationRelativeTo(null);

	}

	// Metodo Ajustar imagenes
	public ImageIcon resize(ImageIcon ic) {

		Image img = ic.getImage();
		Image icono = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		ImageIcon fianlIcon = new ImageIcon(icono);
		return fianlIcon;
	}

	public class ImagenBarcos extends JPanel {// Clase que pinta los barcos a ubicar

		public ImagenBarcos() {
			this.setSize(1500, 600);
		}

		public void paintComponent(Graphics g) {//
			ImageIcon img1 = new ImageIcon(new ImageIcon(getClass().getResource("/img/porta.jpg")).getImage());
			ImageIcon img2 = new ImageIcon(new ImageIcon(getClass().getResource("/img/sub.jpg")).getImage());
			ImageIcon img3 = new ImageIcon(new ImageIcon(getClass().getResource("/img/des.jpg")).getImage());
			ImageIcon img4 = new ImageIcon(new ImageIcon(getClass().getResource("/img/fraH.jpg")).getImage());
			g.drawImage(img1.getImage(), 100, 20, 140, 50, null);
			g.drawImage(img2.getImage(), 100, 150, 120, 50, null);
			g.drawImage(img3.getImage(), 100, 280, 100, 50, null);
			g.drawImage(img4.getImage(), 100, 410, 70, 50, null);
			
			setOpaque(false);
			super.paintComponent(g);
			
		}// fin del metodo
	}// fin de la clase

	private class ManejadorBotones implements ActionListener {// calse Manejador
																// de eventos

		public void actionPerformed(ActionEvent e) {// escucha del Boton Ubicar
			for (int k = 0; k < ubicar.length; k++) {
				try {
					if (e.getSource() == ubicar[k]) {
						int co = Integer.parseInt(columnas);
						int fi = 0;
						for (int i = 0; i < letras.length; i++) {
							if (filas.equals(letras[i])) {
								fi = i;
							}
						}
						if (k == 0) {// primer Boton
							if (validarJugador == true) {//validar jugador es verdadero cuando se cambia de jugador
								jugador2.getBarcos()[0] = new Barco("portaaviones", fi, co, pos);
								jugador2.getBarcos()[0].tipoBarco();
								jugador2.ubicaBarco(jugador2.getBarcos()[0]);//ubica el barco
								if (jugador2.getPosValida() == true) {// si la posicion que ubica el barco es valida entonces resta uno a la cantidad de barcos
									if (jugador2.getNporta() >= 1) {
										jugador2.setNporta(jugador2.getNporta()-1);
										NumeroBarco[k].setText(Integer.toString(jugador2.getNporta()));
									}
									if (jugador2.getNporta() == 0) {//caundo la cantidad de barcos es 0 ,pasa a escoger el otro tipo de barco
										NumeroBarco[k].setText(Integer.toString(jugador2.getNporta()));
										ubicar[k].setEnabled(false);
										Rhorizontal[k].setEnabled(false);
										Rvertical[k].setEnabled(false);
										comboLetras[k].setEnabled(false);
										comboNumeros[k].setEnabled(false);
									}
								} else if (jugador2.getPosValida() == false) {
									JOptionPane.showMessageDialog(null, "hay Otro Barco");
								}
							} else {
								jugador1.getBarcos()[0] = new Barco("portaaviones", fi, co, pos);
								jugador1.getBarcos()[0].tipoBarco();
								jugador1.ubicaBarco(jugador1.getBarcos()[0]);
								if (jugador1.getPosValida() == true) {
									if (jugador1.getNporta() >= 1) {
										jugador2.setNporta(jugador1.getNporta()-1);
										NumeroBarco[k].setText(Integer.toString(jugador1.getNporta()));
									}
									if (jugador1.getNporta() == 0) {
										NumeroBarco[k].setText(Integer.toString(jugador1.getNporta()));
										ubicar[k].setEnabled(false);
										Rhorizontal[k].setEnabled(false);
										Rvertical[k].setEnabled(false);
										comboLetras[k].setEnabled(false);
										comboNumeros[k].setEnabled(false);
									}
								} else if (jugador1.getPosValida() == false) {
									JOptionPane.showMessageDialog(null, "hay Otro Barco");
								}
							}
							if (ubicar[k].isEnabled() == false) {
								ubicar[k + 1].setEnabled(true);
								Rhorizontal[k + 1].setEnabled(true);
								Rvertical[k + 1].setEnabled(true);
								comboLetras[k + 1].setEnabled(true);
								comboNumeros[k + 1].setEnabled(true);
							}
							randomPlayer1.setEnabled(false);
						}
						if (k == 1) {// Segundo Boton
							if (validarJugador == true) {

								if (jugador2.getNsub() == 3) {

									jugador2.getBarcos()[1] = new Barco("submarino", fi, co, pos);
									jugador2.getBarcos()[1].tipoBarco();
									jugador2.ubicaBarco(jugador2.getBarcos()[1]);

								} else if (jugador2.getNsub() == 2) {

									jugador2.getBarcos()[2] = new Barco("submarino", fi, co, pos);
									jugador2.getBarcos()[2].tipoBarco();
									jugador1.ubicaBarco(jugador2.getBarcos()[2]);

								} else if (jugador2.getNsub() == 1) {

									jugador2.getBarcos()[3] = new Barco("submarino", fi, co, pos);
									jugador2.getBarcos()[3].tipoBarco();
									jugador2.ubicaBarco(jugador2.getBarcos()[3]);

								}

								if (jugador2.getPosValida() == true) {
									if (jugador2.getNsub() >= 1) {
										jugador2.setNsub(jugador2.getNsub()-1);

										NumeroBarco[1].setText(Integer.toString(jugador2.getNsub()));
									}
									if (jugador2.getNsub() == 0) {
										NumeroBarco[1].setText(Integer.toString(jugador2.getNsub()));
										ubicar[k].setEnabled(false);
										Rhorizontal[k].setEnabled(false);
										Rvertical[k].setEnabled(false);
										comboLetras[k].setEnabled(false);
										comboNumeros[k].setEnabled(false);
									}
								} else if (jugador2.getPosValida() == false) {

									JOptionPane.showMessageDialog(null, "hay Otro Barco");
								}
							} else if (validarJugador == false) {
								if (jugador1.getNsub() == 3) {

									jugador1.getBarcos()[1] = new Barco("submarino", fi, co, pos);
									jugador1.getBarcos()[1].tipoBarco();
									jugador1.ubicaBarco(jugador1.getBarcos()[1]);
								} else if (jugador1.getNsub() == 2) {

									jugador1.getBarcos()[2] = new Barco("submarino", fi, co, pos);
									jugador1.getBarcos()[2].tipoBarco();
									jugador1.ubicaBarco(jugador1.getBarcos()[2]);
								} else if (jugador1.getNsub()== 1) {

									jugador1.getBarcos()[3] = new Barco("submarino", fi, co, pos);
									jugador1.getBarcos()[3].tipoBarco();
									jugador1.ubicaBarco(jugador1.getBarcos()[3]);
								}

								if (jugador1.getPosValida() == true) {
									if (jugador1.getNsub() >= 1) {
										jugador1.setNsub(jugador1.getNsub()-1);
										NumeroBarco[k].setText(Integer.toString(jugador1.getNsub()));
									}
									if (jugador2.getNsub() == 0) {
										NumeroBarco[k].setText(Integer.toString(jugador1.getNsub()));
										ubicar[k].setEnabled(false);
										Rhorizontal[k].setEnabled(false);
										Rvertical[k].setEnabled(false);
										comboLetras[k].setEnabled(false);
										comboNumeros[k].setEnabled(false);
									}
								} else if (jugador1.getPosValida() == false) {
									JOptionPane.showMessageDialog(null, "hay Otro Barco");
								}
							}
							if (ubicar[k].isEnabled() == false) {
								ubicar[k + 1].setEnabled(true);
								Rhorizontal[k + 1].setEnabled(true);
								Rvertical[k + 1].setEnabled(true);
								comboLetras[k + 1].setEnabled(true);
								comboNumeros[k + 1].setEnabled(true);
							}
						}
						if (k == 2) {// Tercer Boton
							if (validarJugador == true) {
								if (jugador2.getNdes() == 3) {
									jugador2.getBarcos()[4] = new Barco("destructor", fi, co, pos);
									jugador2.getBarcos()[4].tipoBarco();
									jugador2.ubicaBarco(jugador2.getBarcos()[4]);
								} else if (jugador2.getNdes() == 2) {
									jugador2.getBarcos()[5] = new Barco("destructor", fi, co, pos);
									jugador2.getBarcos()[5].tipoBarco();
									jugador2.ubicaBarco(jugador2.getBarcos()[5]);
								} else if (jugador2.getNdes() == 1) {
									jugador2.getBarcos()[6] = new Barco("destructor", fi, co, pos);
									jugador2.getBarcos()[6].tipoBarco();
									jugador2.ubicaBarco(jugador2.getBarcos()[6]);
								}
								if (jugador2.getPosValida() == true) {
									if (jugador2.getNdes() >= 1) {
										jugador2.setNdes(jugador2.getNdes()-1);
										NumeroBarco[k].setText(Integer.toString(jugador2.getNdes()));
									}
									if (jugador2.getNdes() == 0) {
										NumeroBarco[k].setText(Integer.toString(jugador2.getNdes()));
										ubicar[k].setEnabled(false);
										Rhorizontal[k].setEnabled(false);
										Rvertical[k].setEnabled(false);
										comboLetras[k].setEnabled(false);
										comboNumeros[k].setEnabled(false);
									}
								} else if (jugador2.getPosValida() == false) {
									JOptionPane.showMessageDialog(null, "hay Otro Barco");
								}
							} else if (validarJugador == false) {
								if (jugador1.getNdes() == 3) {
									jugador1.getBarcos()[4] = new Barco("destructor", fi, co, pos);
									jugador1.getBarcos()[4].tipoBarco();
									jugador1.ubicaBarco(jugador1.getBarcos()[4]);
								} else if (jugador1.getNdes() == 2) {
									jugador1.getBarcos()[5] = new Barco("destructor", fi, co, pos);
									jugador1.getBarcos()[5].tipoBarco();
									jugador1.ubicaBarco(jugador1.getBarcos()[5]);
								} else if (jugador1.getNdes() == 1) {
									jugador1.getBarcos()[6] = new Barco("destructor", fi, co, pos);
									jugador1.getBarcos()[6].tipoBarco();
									jugador1.ubicaBarco(jugador1.getBarcos()[6]);
								}
								if (jugador1.getPosValida() == true) {
									if (jugador1.getNdes() >= 1) {
										jugador1.setNdes(jugador1.getNdes()-1);
										NumeroBarco[k].setText(Integer.toString(jugador1.getNdes()));
									}
									if (jugador1.getNdes() == 0) {
										NumeroBarco[k].setText(Integer.toString(jugador1.getNdes()));
										ubicar[k].setEnabled(false);
										Rhorizontal[k].setEnabled(false);
										Rvertical[k].setEnabled(false);
										comboLetras[k].setEnabled(false);
										comboNumeros[k].setEnabled(false);
									}
								} else if (jugador1.getPosValida() == false) {
									JOptionPane.showMessageDialog(null, "hay Otro Barco");
								}
							}
							if (ubicar[k].isEnabled() == false) {
								ubicar[k + 1].setEnabled(true);
								Rhorizontal[k + 1].setEnabled(true);
								Rvertical[k + 1].setEnabled(true);
								comboLetras[k + 1].setEnabled(true);
								comboNumeros[k + 1].setEnabled(true);
							}
						}
						if (k == 3) {// Ultimo Boton
							if (validarJugador == true) {
								if (jugador2.getNfra() == 2) {
									jugador2.getBarcos()[7] = new Barco("fragata", fi, co, pos);
									jugador2.getBarcos()[7].tipoBarco();
									jugador2.ubicaBarco(jugador2.getBarcos()[7]);
								} else if (jugador2.getNfra() == 1) {
									jugador2.getBarcos()[8] = new Barco("fragata", fi, co, pos);
									jugador2.getBarcos()[8].tipoBarco();
									jugador2.ubicaBarco(jugador2.getBarcos()[8]);
								}
								if (jugador2.getPosValida() == true) {
									if (jugador2.getNfra() >= 1) {
										jugador2.setNfra(jugador2.getNfra()-1);
										NumeroBarco[k].setText(Integer.toString(jugador2.getNfra()));
									}
									if (jugador2.getNfra()-1 == 0) {
										NumeroBarco[k].setText(Integer.toString(jugador2.getNfra()));
										ubicar[k].setEnabled(false);
										Rhorizontal[k].setEnabled(false);
										Rvertical[k].setEnabled(false);
										comboLetras[k].setEnabled(false);
										comboNumeros[k].setEnabled(false);
									}
								} else if (jugador2.getPosValida() == false) {
									JOptionPane.showMessageDialog(null, "hay Otro Barco");
								}
								if (jugador1.getNfra() == 0 && jugador2.getNfra() == 0) {
									jugar.setEnabled(true);
								}
							} else if (validarJugador == false) {
								if (jugador1.getNfra() == 2) {
									jugador1.getBarcos()[7] = new Barco("fragata", fi, co, pos);
									jugador1.getBarcos()[7].tipoBarco();
									jugador1.ubicaBarco(jugador1.getBarcos()[7]);
								} else if (jugador1.getNfra() == 1) {
									jugador1.getBarcos()[8] = new Barco("fragata", fi, co, pos);
									jugador1.getBarcos()[8].tipoBarco();
									jugador1.ubicaBarco(jugador1.getBarcos()[8]);
								}
								if (jugador1.getPosValida() == true) {
									if (jugador2.getNfra() >= 1) {
										jugador2.setNfra(jugador1.getNfra()-1);
										NumeroBarco[k].setText(Integer.toString(jugador1.getNfra()));
									}
									if (jugador1.getNfra() == 0) {
										NumeroBarco[k].setText(Integer.toString(jugador1.getNfra()));
										ubicar[k].setEnabled(false);
										Rhorizontal[k].setEnabled(false);
										Rvertical[k].setEnabled(false);
										comboLetras[k].setEnabled(false);
										comboNumeros[k].setEnabled(false);
										CambiarJugador.setEnabled(true);
									}
								} else if (jugador1.getPosValida() == false) {
									JOptionPane.showMessageDialog(null, "hay Otro Barco");
								}
							}
						}
					}
				} catch (Exception h) {
					// h.printStackTrace();
					jugador1.setPosValida(false);
					jugador2.setPosValida(false);
					JOptionPane.showMessageDialog(null, "Ingrese los valores de fila y columna correctamente");
				}
			} // fin de la escucha de los Botones Ubicar
			if (e.getSource() == CambiarJugador) {
			
				if (jugador1.getNporta() == 00 && jugador1.getNfra() == 0 && jugador1.getNsub() == 0
						&& jugador1.getNdes() == 0) {
					
					validarJugador = true;
					JOptionPane.showMessageDialog(null, "Jugador 2");
					NumeroBarco[0].setText(Integer.toString(jugador2.getNporta()));
					NumeroBarco[1].setText(Integer.toString(jugador2.getNsub()));
					NumeroBarco[2].setText(Integer.toString(jugador2.getNdes()));
					NumeroBarco[3].setText(Integer.toString(jugador2.getNfra()));
					ubicar[0].setEnabled(true);
					ubicar[1].setEnabled(false);
					ubicar[2].setEnabled(false);
					ubicar[3].setEnabled(false);
					Rhorizontal[0].setEnabled(true);
					Rvertical[0].setEnabled(true);
					comboLetras[0].setEnabled(true);
					comboNumeros[0].setEnabled(true);

					jugador1.setVisible(false);
					CambiarJugador.setVisible(false);
					randomPlayer2.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Por Favor ingresa todos los Barcos");
				}
			}
			if (e.getSource() == randomPlayer1) {
				ubicar[0].setEnabled(false);
				Rhorizontal[0].setEnabled(false);
				Rvertical[0].setEnabled(false);
				comboLetras[0].setEnabled(false);
				comboNumeros[0].setEnabled(false);
				
				int dRan = 0;
				for (int i = 0; i < jugador1.getBarcos().length; i++) {
					dRan = (int) (Math.random() * 2);
					String nombreRan = "";
					String direccionRan = (dRan == 0) ? "vertical" : "horizontal";
					if (i == 0) {
						nombreRan = "portaaviones";
						jugador1.setNporta(jugador1.getNporta() - 1);
					} else if (i == 1) {
						nombreRan = "submarino";
						jugador1.setNsub(jugador1.getNsub() - 1);
					} else if (i == 2) {
						nombreRan = "submarino";
						jugador1.setNsub(jugador1.getNsub() - 1);
					} else if (i == 3) {
						nombreRan = "submarino";
						jugador1.setNsub(jugador1.getNsub() - 1);
					} else if (i == 4) {
						nombreRan = "destructor";
						jugador1.setNdes(jugador1.getNdes() - 1);
					} else if (i == 5) {
						nombreRan = "destructor";
						jugador1.setNdes(jugador1.getNdes() - 1);
					} else if (i == 6) {
						nombreRan = "destructor";
						jugador1.setNdes(jugador1.getNdes() - 1);
					} else if (i == 7) {
						nombreRan = "fragata";
						jugador1.setNfra(jugador1.getNfra() - 1);
					} else if (i == 8) {
						nombreRan = "fragata";
						jugador1.setNfra(jugador1.getNfra() - 1);
					}

					jugador1.getBarcos()[i] = new Barco(nombreRan, (int) (Math.random() * 9), (int) (Math.random() * 9),
							direccionRan);// se le asigna un random a la posX y posY
					jugador1.getBarcos()[i].tipoBarco();
					jugador1.ubicaBarco(jugador1.getBarcos()[i]);// si la posicion es invalida,entra en un ciclo hasta que sea verdadea
					while (jugador1.getPosValida() == false) {
						jugador1.getBarcos()[i] = new Barco(nombreRan, (int) (Math.random() * 9),
								(int) (Math.random() * 9), direccionRan);
						jugador1.getBarcos()[i].tipoBarco();
						jugador1.ubicaBarco(jugador1.getBarcos()[i]);

					}

					NumeroBarco[0].setText(Integer.toString(jugador1.getNporta()));
					NumeroBarco[1].setText(Integer.toString(jugador1.getNsub()));
					NumeroBarco[2].setText(Integer.toString(jugador1.getNdes()));
					NumeroBarco[3].setText(Integer.toString(jugador1.getNfra()));

				}

				CambiarJugador.setEnabled(true);
				randomPlayer1.setVisible(false);

			} // fin del boton Ramdon

			if (e.getSource() == randomPlayer2) {// Boton Random2
				int dRan1 = 0;
				ubicar[0].setEnabled(false);
				Rhorizontal[0].setEnabled(false);
				Rvertical[0].setEnabled(false);
				comboLetras[0].setEnabled(false);
				comboNumeros[0].setEnabled(false);
				for (int i = 0; i < jugador2.getBarcos().length; i++) {
					dRan1 = (int) (Math.random() * 2);
					String nombreRan1 = "";
					String direccionRan1 = (dRan1 == 0) ? "vertical" : "horizontal";

					if (i == 0) {
						nombreRan1 = "portaaviones";
						jugador2.setNporta(jugador2.getNporta() - 1);
					} else if (i == 1) {
						nombreRan1 = "submarino";
						jugador2.setNsub(jugador2.getNsub() - 1);
					} else if (i == 2) {
						nombreRan1 = "submarino";
						jugador2.setNsub(jugador2.getNsub() - 1);
					} else if (i == 3) {
						nombreRan1 = "submarino";
						jugador2.setNsub(jugador2.getNsub() - 1);
					} else if (i == 4) {
						nombreRan1 = "destructor";
						jugador2.setNdes(jugador2.getNdes() - 1);
					} else if (i == 5) {
						nombreRan1 = "destructor";
						jugador2.setNdes(jugador2.getNdes() - 1);
					} else if (i == 6) {
						nombreRan1 = "destructor";
						jugador2.setNdes(jugador2.getNdes() - 1);
					} else if (i == 7) {
						nombreRan1 = "fragata";
						jugador2.setNfra(jugador2.getNfra() - 1);
					} else if (i == 8) {
						nombreRan1 = "fragata";
						jugador2.setNfra(jugador2.getNfra() - 1);
					}
					jugador2.getBarcos()[i] = new Barco(nombreRan1, (int) (Math.random() * 9),
							(int) (Math.random() * 9), direccionRan1);
					jugador2.getBarcos()[i].tipoBarco();
					jugador2.ubicaBarco(jugador2.getBarcos()[i]);
					while (jugador2.getPosValida() == false) {
						jugador2.getBarcos()[i] = new Barco(nombreRan1, (int) (Math.random() * 9),
								(int) (Math.random() * 9), direccionRan1);
						jugador2.getBarcos()[i].tipoBarco();
						jugador2.ubicaBarco(jugador2.getBarcos()[i]);
					}
					NumeroBarco[0].setText(Integer.toString(jugador2.getNporta()));
					NumeroBarco[1].setText(Integer.toString(jugador2.getNsub()));
					NumeroBarco[2].setText(Integer.toString(jugador2.getNdes()));
					NumeroBarco[3].setText(Integer.toString(jugador2.getNfra()));
					jugar.setEnabled(true);
					randomPlayer2.setVisible(false);
				}
				
			} // fin del Boton Random
			if (e.getSource() == jugar) {// Boton Jugar
				empizaJuego = true;
				JOptionPane.showMessageDialog(null, "BUENA SUERTE ");
				jugador1.setVisible(true);
				jugador2.setVisible(false);
				jugar.setVisible(false);
				Cronometro.iniciarCronometro();
				
			}

			if (e.getSource() == Cargar) {

				abrirArchivo();
				if (input != null) {
					leerRegistro();
				}			
			}

			if (e.getSource() == Guardar) {
				guardarArchivo();
				if (output != null) {
					registro();
				}			
			}
			if (e.getSource() == Salir) {
				System.exit(0);
			}
			if (e.getSource() == Ayuda) {
				JOptionPane.showMessageDialog(null,
						"Al comenzar, cada jugador posiciona sus barcos en el primer tablero,"
								+ " de forma secreta, invisible al oponente\n"
								+ "Cada quien ocupa, seg�n sus preferencias, una misma cantidad de casillas,"
								+ " horizontal y/o verticalmente\n"
								+ "las que representan sus naves. Ambos participantes deben ubicar igual el n�mero de naves, por lo que es habitual,\n"
								+ " antes de comenzar, estipular de com�n acuerdo la cantidad y el tama�o de las naves que se posicionar�n en el tablero.\n"
								+ " As�, por ejemplo, cinco casillas consecutivas conforman un porta aviones"
								+ "tres, un buque, y una casilla aislada,\n una lancha, y los participantes podr�an convenir, tambi�n a modo de ejemplo, en colocar"
								+ ", cada uno, dos portaaviones,\n tres buques y cinco lanchas");
			}
			// -----------------------------------Escuchas de Tableros
			// Jugadores------------------//
			for (int i = 0; i < jugador1.getTablaMapa().length; i++) {
				for (int j = 0; j < jugador1.getTablaMapa()[i].length; j++) {
					if (e.getSource() == jugador2.getTablaDisparo()[i][j]&& empizaJuego==true) {
						
						if (jugador1.getTablaMapa()[i][j].getIcon() == jugador1.getAgua()) {//al disparar en una casilla vacia pintara una honda de agua 
							jugador1.getTablaMapa()[i][j].setIcon(equis);
							jugador2.getTablaDisparo()[i][j].setIcon(equis);
							fallo=new Sonido(aguaSonido);
							fallo.start();
							jugador1.setVisible(true);
							jugador2.setVisible(false);
							Cronometro.pararCronometro();
							Cronometro.iniciarCronometro();
						}

						for (int k = 0; k < jugador1.getBarcos().length; k++) {//al disparar en una casilla con un barco pintara una honda explosiva 
							for (int n = 0; n < jugador1.getBarcos()[k].getCuerpo().length; n++) {
								if (jugador1.getTablaMapa()[i][j].getIcon() == jugador1.getBarcos()[k].getCuerpo()[n]) {
									jugador1.getTablaMapa()[i][j].setIcon(explosion);
									jugador2.getTablaDisparo()[i][j].setIcon(explosion);
									jugador1.getBarcos()[k].setHundido(jugador1.getBarcos()[k].getHundido() - 1);
									acierto=new Sonido(explosionSonido);
									acierto.start();
									
									if (jugador1.getBarcos()[k].getHundido() == 0) {
										JOptionPane.showMessageDialog(null,
										jugador1.getBarcos()[k].getNombre() + " fue hundido");
										if(jugador1.getBarcos()[k].getNombre().equals("portaaviones")){
											jugador2.setPuntaje(jugador2.getPuntaje()+40);
										}else if(jugador1.getBarcos()[k].getNombre().equals("submarino")){
											jugador2.setPuntaje(jugador2.getPuntaje()+30);
										}else if(jugador1.getBarcos()[k].getNombre().equals("destructor")){
											jugador2.setPuntaje(jugador2.getPuntaje()+20);
										}else if(jugador1.getBarcos()[k].getNombre().equals("fragata")){
											jugador2.setPuntaje(jugador2.getPuntaje()+10);
										}
										puntaje2.setText("Puntaje J2:  "+jugador2.getPuntaje());
										jugador1.setBarDestruidos(jugador1.getBarDestruidos() + 1);
										
									}
									if (jugador1.getBarDestruidos() == 9) {
																			
										JOptionPane.showMessageDialog(null, "Jugador 2 Gano!!!");
										int choice = JOptionPane.showOptionDialog(null, "Quieres Jugar de ",
												"Jugar de Nuevo ", JOptionPane.YES_NO_OPTION,
												JOptionPane.QUESTION_MESSAGE, null, null, null);
										// interpret the user's choice
										if (choice == JOptionPane.YES_OPTION) {
											setVisible(false);
											TableroJuego t1 = new TableroJuego();
											t1.setVisible(true);
										} else {
											System.exit(0);
										}
									}
								}
							}
						}
					}
				}
			}
			for (int i = 0; i < jugador2.getTablaMapa().length; i++) {
				for (int j = 0; j < jugador2.getTablaMapa()[i].length; j++) {

					if (e.getSource() == jugador1.getTablaDisparo()[i][j] && empizaJuego==true) {
						
						if (jugador2.getTablaMapa()[i][j].getIcon() == jugador2.getAgua()) {
							jugador2.getTablaMapa()[i][j].setIcon(equis);
							jugador1.getTablaDisparo()[i][j].setIcon(equis);
							fallo=new Sonido(aguaSonido);
							fallo.start();
							jugador1.setVisible(false);
							jugador2.setVisible(true);
							Cronometro.pararCronometro();
							Cronometro.iniciarCronometro();
						}
						for (int k = 0; k < jugador2.getBarcos().length; k++) {
							for (int n = 0; n < jugador2.getBarcos()[k].getCuerpo().length; n++) {
								if (jugador2.getTablaMapa()[i][j].getIcon() == jugador2.getBarcos()[k].getCuerpo()[n]) {
									jugador2.getTablaMapa()[i][j].setIcon(explosion);
									jugador1.getTablaDisparo()[i][j].setIcon(explosion);
									acierto=new Sonido(explosionSonido);
									acierto.start();
									jugador2.getBarcos()[k].setHundido(jugador2.getBarcos()[k].getHundido() - 1);
								
									if (jugador2.getBarcos()[k].getHundido() == 0) {
										JOptionPane.showMessageDialog(null,
												jugador2.getBarcos()[k].getNombre() + " fue hundido");
										if(jugador2.getBarcos()[k].getNombre().equals("portaaviones")){
											jugador1.setPuntaje(jugador1.getPuntaje()+40);
										}else if(jugador2.getBarcos()[k].getNombre().equals("submarino")){
											jugador1.setPuntaje(jugador1.getPuntaje()+30);
										}else if(jugador2.getBarcos()[k].getNombre().equals("destructor")){
											jugador1.setPuntaje(jugador1.getPuntaje()+20);
										}else if(jugador2.getBarcos()[k].getNombre().equals("fragata")){
											jugador1.setPuntaje(jugador1.getPuntaje()+10);
										}
										puntaje1.setText("Puntaje J1:  "+jugador1.getPuntaje());
										insertarPuntaje(Integer.toString(jugador1.getPuntaje()));
										jugador2.setBarDestruidos(jugador2.getBarDestruidos() + 1);
									}
									if (jugador2.getBarDestruidos() == 9) {
										JOptionPane.showMessageDialog(null, "Jugador1 Gano!!!");
										int choice = JOptionPane.showOptionDialog(null, "quieres Jugar de Nuevo ",
												"Jugar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
												null, null);
										if (choice == JOptionPane.YES_OPTION) {
											System.exit(0);
											TableroJuego t1 = new TableroJuego();
											t1.setVisible(true);
										} else {
											System.exit(0);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}//fin manejador de eventos

	
    public void insertarPuntaje(String points) {
        
    	BaseDatos db =new BaseDatos();

       try {
           String Query = "UPDATE usuarios SET puntaje ="+points+" where nombre = 'andres'";;
           Connection conexion = (Connection) db.ConexionMYSQL();
//           PreparedStatement statement = (PreparedStatement) conexion.prepareStatement(Query);
//           statement.setString(1, points);
//           statement.executeUpdate();
           Statement statement=conexion.createStatement();
           statement.executeUpdate(Query);
           //JOptionPane.showMessageDialog(null, "Datos almacenados de forma exitosa");
          
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos");
           ex.printStackTrace();
       }
   }
	public Tablero getJugador1() {
		return jugador1;
	}

	public void setJugador1(Tablero jugador1) {
		this.jugador1 = jugador1;
	}

	public Tablero getJugador2() {
		return jugador2;
	}

	public void setJugador2(Tablero jugador2) {
		this.jugador2 = jugador2;
	}
	
	public void abrirArchivo() {

		JFileChooser selectorArchivo = new JFileChooser();
		selectorArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int resultado = selectorArchivo.showOpenDialog(this);

		if (resultado == JFileChooser.CANCEL_OPTION)
			return;
		File nombreArchivo = selectorArchivo.getSelectedFile();

		if (nombreArchivo == null || nombreArchivo.getName().equals(""))
			JOptionPane.showMessageDialog(this, "Nombre de archivo incorrecto", "Nombre de archivo incorrecto",
					JOptionPane.ERROR_MESSAGE);
		else {
			try {
				input = new ObjectInputStream(new FileInputStream(nombreArchivo));
			}

			catch (IOException excepcionES) {
				JOptionPane.showMessageDialog(this, "Error al abrir el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void leerRegistro() {
		JPanel[]tableros =new JPanel[4];
		
		try {
			tableros = (JPanel[]) input.readObject();
			jugador1.setTableroMapa(tableros[0]);
			jugador1.setTableroDisparo(tableros[1]);
			jugador2.setTableroMapa(tableros[2]);
			jugador2.setTableroDisparo(tableros[3]);
			input.close();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void guardarArchivo() {

		JFileChooser selectorArchivo = new JFileChooser();
		selectorArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int resultado = selectorArchivo.showSaveDialog(this);

		if (resultado == JFileChooser.CANCEL_OPTION)
			return;

		File nombreArchivo = selectorArchivo.getSelectedFile();

		if (nombreArchivo == null || nombreArchivo.getName().equals(""))
			JOptionPane.showMessageDialog(this, "Nombre de archivo inválido", "Nombre de archivo invalido",
					JOptionPane.ERROR_MESSAGE);

		else {

			try {
				output = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
			} catch (IOException excepcionES) {
				JOptionPane.showMessageDialog(this, "Error al abrir el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void registro() {
		JPanel[] tableros =new JPanel[4];
		tableros[0]=jugador1.getTableroMapa();
		tableros[1]=jugador1.getTableroDisparo();
		tableros[2]=jugador2.getTableroMapa();
		tableros[3]=jugador2.getTableroDisparo();
	

		try {
			output.writeObject(tableros);
			output.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}// fin de la clase Tablero
