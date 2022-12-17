import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;


public class Sintetizador extends GeneradorOnda{
    public static void main(String[] args) throws LineUnavailableException {
/*
        String[] arreglo0={"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
        String[] arreglo1={"C7","C#7","D7","D#7","E7","F7","F#7","G7","G#7","A7","A#7","B7"};
        String[] arreglo2={"Cm","C#m","Dm","D#m","Em","Fm","F#m","Gm","G#m","Am","A#m","Bm"};
        String[] arreglo3={"Cm7","C#m7","Dm7","D#m7","Em7","Fm7","F#m7","Gm7","G#m7","Am7","A#m7","Bm7"};

        JPanel p = new JPanel(new GridLayout(4, arreglo0.length));
        for(int i=0;i<arreglo0.length;i++){
            p.add(new JButton(arreglo0[i]));
        }
        for(int i=0;i<arreglo0.length;i++){
            p.add(new JButton(arreglo1[i]));
        }
        for(int i=0;i<arreglo0.length;i++){
            p.add(new JButton(arreglo2[i]));
        }
        for(int i=0;i<arreglo0.length;i++){
            p.add(new JButton(arreglo3[i]));
        }

        JFrame f= new JFrame("Frame prueba");
        f.setVisible(true);
        f.setSize(800,400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);



    }
}
*/
        String chord="G#7";
        int tempo = 2000;
        double[] acordes=freqs(chord);

        final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
        SourceDataLine line = AudioSystem.getSourceDataLine(af);
        line.open(af, SAMPLE_RATE);
        line.start();

        byte[] toneBuffer = acorde(acordes, tempo);
        line.write(toneBuffer, 0, toneBuffer.length);

        line.drain();
        line.close();
    }
}