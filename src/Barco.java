import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Barco implements Serializable {
	private String nombre, direccion;
	private  int posX, posY;
	private ImageIcon[] cuerpo;
	private int hundido;

	public Barco(String nombre, int posX, int posY, String direccion) {
		this.nombre = nombre;
		this.posX = posX;
		this.posY = posY;
		this.direccion = direccion;

	}

	public ImageIcon[] getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(ImageIcon[] cuerpo) {
		this.cuerpo = cuerpo;
	}

	public ImageIcon resize1(ImageIcon ic) {

		Image img = ic.getImage();
		Image icono = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		ImageIcon fianlIcon = new ImageIcon(icono);

		return fianlIcon;
	}

	public void tipoBarco() {//selecciona que tipo de barco es , asignandole un cuerpo
		ImageIcon[] submarinoV = { resize1(new ImageIcon(getClass().getResource("img/sub1V.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/sub2V.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/sub3V.jpg"))) };
		ImageIcon[] submarinoH = { resize1(new ImageIcon(getClass().getResource("img/sub1H.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/sub2H.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/sub3H.jpg"))) };
		ImageIcon[] fragataH = { resize1(new ImageIcon(getClass().getResource("img/fraH.jpg")) )};
		ImageIcon[] fragataV = { resize1(new ImageIcon(getClass().getResource("img/fraV.jpg"))) };
		ImageIcon[] destructorH = { resize1(new ImageIcon(getClass().getResource("img/des1H.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/des2H.jpg"))) };
		ImageIcon[] destructorV = { resize1(new ImageIcon(getClass().getResource("img/des1V.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/des2V.jpg"))) };
		ImageIcon[] portaavionesH = { resize1(new ImageIcon(getClass().getResource("img/porta1H.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/porta2H.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/porta3H.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/porta4H.jpg"))) };
		ImageIcon[] portaavionesV = { resize1(new ImageIcon(getClass().getResource("img/porta1V.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/porta2V.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/porta3V.jpg"))),
				resize1(new ImageIcon(getClass().getResource("img/porta4V.jpg"))) };

		if (nombre.equals("submarino") && direccion.equals("vertical")) {
			cuerpo = submarinoV;
			hundido = submarinoV.length;
		} else if (nombre.equals("submarino") && direccion.equals("horizontal")) {
			cuerpo = submarinoH;
			hundido = submarinoH.length;
		} else if (nombre.equals("portaaviones") && direccion.equals("horizontal")) {
			cuerpo = portaavionesH;
			hundido = portaavionesH.length;
		} else if (nombre.equals("portaaviones") && direccion.equals("vertical")) {
			cuerpo = portaavionesV;
			hundido = portaavionesV.length;
		} else if (nombre.equals("destructor") && direccion.equals("horizontal")) {
			cuerpo = destructorH;
			hundido = destructorH.length;
		} else if (nombre.equals("destructor") && direccion.equals("vertical")) {
			cuerpo = destructorV;
			hundido = destructorV.length;
		}
		else if (nombre.equals("fragata") && direccion.equals("horizontal")) {
			cuerpo = fragataH;
			hundido = fragataH.length;
		} else if (nombre.equals("fragata") && direccion.equals("vertical")) {
			cuerpo = fragataV;
			hundido = fragataV.length;
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getHundido() {
		return hundido;
	}

	public void setHundido(int hundido) {
		this.hundido = hundido;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}
