import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//import m.mysql.jdbc.Connection;



public class Ingreso extends JFrame {

	private JButton Ingresar, Registrar, Salir;
	private JTextField user;
	private JPasswordField password;
	private JLabel Luser, Lpass;
	private Sonido inicio;
	private  static String inicioSonido="C:/Users/AndresFelipe/Desktop/Halo.wav";
	public Ingreso() {
		//Frame
		this.setTitle("BattleShip");
		Container contenedor = getContentPane();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		 ImageIcon icono = new ImageIcon(getClass().getResource("/img/IconW.jpg"));
		 this.setIconImage(icono.getImage());
		 
		 
		 //Sound
		 inicio=new Sonido(inicioSonido);
		inicio.start();
		
		//Buttons
		Ingresar = new JButton("LogIn");
		Registrar = new JButton("Create Account");
		Salir = new JButton("Exit");
		Ingresar.setBounds(130, 260, 80, 20);
		Registrar.setBounds(220, 260, 130, 20);
		Salir.setBounds(200, 300, 80, 20);

		
		//Labels
		Luser = new JLabel("USER");
		Lpass = new JLabel("PASSWORD");
		user = new JTextField(10);
		password = new JPasswordField(10);
		Luser.setBounds(170, 170, 70, 20);
		user.setBounds(220, 170, 100, 20);
		Lpass.setBounds(150, 200, 100, 20);
		password.setBounds(220, 200, 100, 20);

	
		Imagen image = new Imagen();

		contenedor.add(Luser);
		contenedor.add(user);
		contenedor.add(Lpass);
		contenedor.add(password);
		contenedor.add(Ingresar);
		contenedor.add(Registrar);
		contenedor.add(Salir);
		contenedor.add(image);
		
		ButtonListener handler = new ButtonListener();
		Ingresar.addActionListener(handler);
		Salir.addActionListener(handler);
		Registrar.addActionListener(handler);

		this.setLocationRelativeTo(null);
		this.setSize(450, 450);
		this.setVisible(true);
		this.setResizable(false);
	}

	public class Imagen extends JPanel {//clase que pinta el fondo

		public Imagen() {
			this.setSize(450, 450);
		}

		public void paintComponent(Graphics g) {
			Dimension tam = getSize();
			ImageIcon fondo = new ImageIcon(new ImageIcon(getClass().getResource("/img/Battleship.jpg")).getImage());
			g.drawImage(fondo.getImage(), 0, 0, tam.width, tam.height, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}

	public static void main(String args[]) {
		
		Ingreso t2 = new Ingreso();
	}

	BaseDatos DB = new BaseDatos();
	Connection conexion = (Connection) DB.ConexionMYSQL();

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == Salir) {
				System.exit(0);
			}

			if (e.getSource() == Ingresar) {

				try {
					if (user.getText().length() > 0 && password.getText().length() > 0) {//si ingresa almenos un valor

						if (DB.AutenticaUsuario(user.getText(), password.getText())) {//si el usuario y contrse�a son validos ,inicia el juego

							setVisible(false);
							TableroJuego juego = new TableroJuego();
							juego.setVisible(true);
							conexion.close();

						} else {
							JOptionPane.showMessageDialog(null, "Usuario y/o Contrase�a Incorrectos");
							user.setText("");
							password.setText("");
						}

					} else {
						JOptionPane.showMessageDialog(null, "Usuario y/o Contrase�a Incorrectos");
					}

				} catch (Exception exep) {
					exep.printStackTrace();

				}
			}

			if (e.getSource() == Registrar) {//Se abre una ventana de registro
				
				RegistroUsuario registro=new RegistroUsuario();
				registro.setVisible(true);

			}
		}

	}

}
