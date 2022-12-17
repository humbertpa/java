import java.lang.Math;

abstract class GeneradorOnda {
    protected static final int SAMPLE_RATE = 16 * 1024;
    private static String[][] frecuencias = new String[][]{{"C5", "523.25"}, {"C#5", "554.37"}, {"D5", "587.33"}, {"D#5", "622.25"}, {"E5", "659.25"}, {"F5", "698.46"}, {"F#5", "739.99"}, {"G5", "783.99"}, {"G#5", "830.61"}, {"A5", "880"}, {"A#5", "932.33"}, {"B5", "987.77"}, {"C6", "1046.5"}, {"C#6", "1108.73"}, {"D6", "1174.66"}, {"D#6", "1244.51"}, {"E6", "1318.51"}, {"F6", "1396.91"}, {"F#6", "1479.98"}, {"G6", "1567.98"}, {"G#6", "1661.22"}, {"A6", "1760"}, {"A#6", "1864.66"}, {"B6", "1975.53"}};
    private static int c=0;
    private static int d=3;
    private static int e=5;
    private static int f=6;
    private static int g=8;
    private static int a=10;
    private static int b=12;

    public static double[] freqs(String chord){
        int[] notas;
        if(chord.indexOf('7')!=-1){
            notas = new int[]{0, 0, 7, 10};
        }else{
            notas=new int[]{0,0,7};
        }

        double[] acordes=new double[notas.length];

        if(chord.indexOf('m')!=-1){
            notas[1]=3;
        }else {
            notas[1] = 4;
        }

        if (chord.indexOf('D') != -1) {
            for(int i=0;i<notas.length;i++)
                notas[i] += d;
        }

        if (chord.indexOf('E') != -1) {
            for(int i=0;i<notas.length;i++)
                notas[i] += e;
        }

        if (chord.indexOf('F') != -1) {
            for(int i=0;i<notas.length;i++)
                notas[i] += f;
        }

        if (chord.indexOf('G') != -1) {
            for(int i=0;i<notas.length;i++)
                notas[i] += g;
        }

        if (chord.indexOf('A') != -1) {
            for(int i=0;i<notas.length;i++)
                notas[i] += a;
        }

        if (chord.indexOf('B') != -1) {
            for(int i=0;i<notas.length;i++)
                notas[i] += b;
        }

        if (chord.indexOf('#') != -1) {
            for(int i=0;i<notas.length;i++)
                notas[i] += 1;
        }

        for(int i=0;i<acordes.length;i++){
            acordes[i]=Double.parseDouble(frecuencias[notas[i]][1]);
        }

        return acordes;
    }

    public static byte[] acorde(double[] fs, int ms) {
        int samples = (ms * SAMPLE_RATE) / 1000;
        byte[] output = new byte[samples];
        double[] angles=new double[fs.length];
        for (int i = 0; i < output.length; i++) {
            double prom=0;
            for(int j=0;j< fs.length;j++) {
                angles[j] = 2.0 * Math.PI * i * fs[j] / SAMPLE_RATE;
            }
            for(int k=0;k<angles.length;k++){
                prom+=Math.sin(angles[k]);
            }
            prom=prom/(float)(angles.length);
            output[i] = (byte)(prom*127f);
        }
        return output;
    }

    public static void toString(String[][] array) {

        for (int i = 0; i < array[0].length; i++) {
            if (array[0][i] == null) break;
            System.out.format("%7s ", array[0][i]);
        }
        System.out.println();
        for (int i = 0; i < array[0].length; i++) {
            if (array[1][i] == null) break;
            System.out.format("%7s ", array[1][i]);
        }
        System.out.println();
        if (array.length > 2) {
            for (int i = 0; i < array[0].length; i++) {
                if (array[1][i] == null) break;
                System.out.format("%7s ", array[2][i]);
            }
        }
    }
}