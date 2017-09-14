import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.*;

public class Sonido extends Thread {
	private InputStream in;
	private AudioStream audio;

public Sonido(String sound){
	try{
		
		in =new FileInputStream(sound);
	  audio=new AudioStream(in);
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
}

public void run() {

    AudioPlayer.player.start(audio);

}

public void DetenerSonido() {
    if (audio != null) {
        AudioPlayer.player.stop(audio);
    }
}
public static void main(String args[]){

	
}
}


