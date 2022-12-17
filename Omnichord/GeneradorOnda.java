import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.Math;

abstract class GeneradorOnda {
    protected static final int SAMPLE_RATE = 16 * 1024;
    private static double c4 = 261.63;
    private static double ratio = 1.059464062;
    private static int c = 0;
    private static int d = 2;
    private static int e = 4;
    private static int f = 5;
    private static int g = 7;
    private static int a = 9;
    private static int b = 11;
    private static int tempo = 800;

    private static String[] notas = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
    private static String[] calidades = { "", "7", "Maj7", "m", "m7", "mMaj7" };
    private static String[][] arreglos = new String[calidades.length][notas.length];
    private static JButton[][] botones = new JButton[calidades.length][notas.length];

    private static ActionListener oyenteAccion = actionEvent -> {
        try {
            reproductor(actionEvent.getActionCommand());
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    };

    public static void _generarArreglos() {
        for (int i = 0; i < calidades.length; i++) {
            for (int j = 0; j < notas.length; j++) {
                arreglos[i][j] = notas[j] + calidades[i];
            }
            for (String element : arreglos[i]) {
                System.out.println(element);
            }
        }
    }

    private static SourceDataLine line;

    static {
        try {
            line = AudioSystem.getSourceDataLine(new AudioFormat(SAMPLE_RATE, 8, 1, true, true));
            line.open(new AudioFormat(SAMPLE_RATE, 8, 1, true, true), SAMPLE_RATE);
        } catch (LineUnavailableException lineUnavailableException) {
            lineUnavailableException.printStackTrace();
        }
        line.start();
    }

    public static JButton[][] botones() {

        _generarArreglos();

        for (int j = 0; j < arreglos.length; j++) {
            for (int i = 0; i < arreglos[0].length; i++) {
                JButton boton = new JButton(arreglos[j][i]);
                //boton.setBackground(new java.awt.Color(255,50,50));
                boton.addActionListener(oyenteAccion);
                botones[j][i] = boton;
            }
        }
        return botones;
    }

    public static JPanel panel() {
        JPanel p = new JPanel(new GridLayout(arreglos.length, arreglos[0].length));
        JButton[][] botones = botones();
        for (int j = 0; j < arreglos.length; j++) {
            for (int i = 0; i < arreglos[0].length; i++) {
                p.add(botones[j][i]);
            }
        }
        return p;
    }

    public static JFrame frame() {
        JFrame f = new JFrame("Omnichord");
        f.setVisible(true);
        f.setSize(800, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(panel());
        return f;
    }

    public static double[] freqs(String chord) {
        int[] notas;
        if (chord.indexOf('7') != -1) {
            if (chord.contains("Maj")) {
                notas = new int[] { 0, 0, 7, 11 };
            } else {
                notas = new int[] { 0, 0, 7, 10 };
            }
        } else {
            notas = new int[] { 0, 0, 7 };
        }

        double[] acordes = new double[notas.length];

        if (chord.indexOf('m') != -1) {
            notas[1] = 3;
        } else {
            notas[1] = 4;
        }

        if (chord.indexOf('D') != -1) {
            for (int i = 0; i < notas.length; i++)
                notas[i] += d;
        }

        if (chord.indexOf('E') != -1) {
            for (int i = 0; i < notas.length; i++)
                notas[i] += e;
        }

        if (chord.indexOf('F') != -1) {
            for (int i = 0; i < notas.length; i++)
                notas[i] += f;
        }

        if (chord.indexOf('G') != -1) {
            for (int i = 0; i < notas.length; i++)
                notas[i] += g;
        }

        if (chord.indexOf('A') != -1) {
            for (int i = 0; i < notas.length; i++)
                notas[i] += a;
        }

        if (chord.indexOf('B') != -1) {
            for (int i = 0; i < notas.length; i++)
                notas[i] += b;
        }

        if (chord.indexOf('#') != -1) {
            for (int i = 0; i < notas.length; i++)
                notas[i] += 1;
        }
        //////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < notas.length; i++) {
            notas[i] = notas[i] % 12;
        }
        /////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < acordes.length; i++) {
            acordes[i] = c4 * Math.pow(ratio, notas[i]);
        }
        return acordes;
    }

    public static byte[] acorde(double[] fs, int ms) {
        int samples = (ms * SAMPLE_RATE) / 1000;
        byte[] output = new byte[samples];
        double[] angles = new double[fs.length];
        for (int i = 0; i < output.length; i++) {
            double prom = 0;
            for (int j = 0; j < fs.length; j++) {
                angles[j] = 2.0 * Math.PI * i * fs[j] / SAMPLE_RATE;
            }
            for (int k = 0; k < angles.length; k++) {
                prom += Math.sin(angles[k]);
            }
            prom = prom / (float) (angles.length);
            output[i] = (byte) (prom * 127f);
        }
        return output;
    }

    public static void reproductor(String chord) throws LineUnavailableException {

        double[] acordes = freqs(chord);

        byte[] toneBuffer = acorde(acordes, tempo);
        line.write(toneBuffer, 0, toneBuffer.length);

        // line.drain();
        // line.close();
    }
}