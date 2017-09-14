import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//import com.mysql.jdbc.Connection;



public class RegistroUsuario extends JFrame {

	private JLabel Lnombre, Lcodigo, Lcorreo, Lpassword;
	private JTextField nombre, codigo, correo, password;
	private JButton ingresar, cancelar;

	public RegistroUsuario() {
		this.setTitle("Registro Usuario");
		Container contenedor = getContentPane();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		ImageIcon icono = new ImageIcon(getClass().getResource("/img/IconW.jpg"));
		this.setIconImage(icono.getImage());

		Lnombre = new JLabel("Name");
		Lnombre.setForeground(Color.BLUE);

		Lcodigo = new JLabel("Code");
		Lcodigo.setForeground(Color.BLUE);

		Lcorreo = new JLabel("E-Mail");
		Lcorreo.setForeground(Color.BLUE);
		Lpassword = new JLabel("Password");
		Lpassword.setForeground(Color.BLUE);

		nombre = new JTextField(10);
		codigo = new JTextField(10);
		correo = new JTextField(10);
		password = new JTextField(10);

		ingresar = new JButton("Create Account");
		cancelar = new JButton("Cancel");

		ingresar.setBounds(110, 320, 130, 20);
		cancelar.setBounds(270, 320, 100, 20);
		Lnombre.setBounds(90, 200, 70, 20);
		nombre.setBounds(160, 200, 150, 20);
		Lcodigo.setBounds(90, 230, 70, 20);
		codigo.setBounds(160, 230, 150, 20);
		Lcorreo.setBounds(90, 260, 70, 20);
		correo.setBounds(160, 260, 150, 20);
		Lpassword.setBounds(90, 290, 70, 20);
		password.setBounds(160, 290, 150, 20);

		ImagenFondo image = new ImagenFondo();

		contenedor.add(ingresar);
		contenedor.add(cancelar);
		contenedor.add(Lnombre);
		contenedor.add(nombre);
		contenedor.add(Lcodigo);
		contenedor.add(codigo);
		contenedor.add(Lcorreo);
		contenedor.add(correo);
		contenedor.add(Lpassword);
		contenedor.add(password);
		contenedor.add(image);
		
		ButtonListener handler=new ButtonListener();
		ingresar.addActionListener(handler);
		cancelar.addActionListener(handler);
		
		this.setLocationRelativeTo(null);
		this.setSize(450, 450);
		this.setVisible(true);
		this.setResizable(false);

	}
	
    public void insertarDatos(String nombre,String correo,String codigo, String password) {
        
    	BaseDatos db =new BaseDatos();

       try {
           String Query = "INSERT INTO usuarios VALUES("
                   + "\"" + nombre + "\", "
                   + "\"" + correo + "\", "
                   + "\"" + codigo + "\", "
                   + "\"" + password + "\")";
          
           Connection conexion = (Connection) db.ConexionMYSQL();
           Statement statement = conexion.createStatement(); 
           statement.executeUpdate(Query);
           conexion.close();
           JOptionPane.showMessageDialog(null, "Datos almacenados de forma exitosa");
           setVisible(false);
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos");
       }
   }
	

	public class ImagenFondo extends JPanel {

		public ImagenFondo() {
			this.setSize(450, 450);
		}

		public void paintComponent(Graphics g) {
			Dimension tam = getSize();
			ImageIcon fondo = new ImageIcon(new ImageIcon(getClass().getResource("/img/tiosam.jpg")).getImage());
			g.drawImage(fondo.getImage(), 0, 0, 450, 420, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == ingresar) {
				insertarDatos(nombre.getText(),correo.getText(),codigo.getText(),password.getText());
			}
			if (e.getSource() == cancelar) {
				setVisible(false);
			}
		}
	}
}
