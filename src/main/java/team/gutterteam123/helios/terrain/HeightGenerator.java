package team.gutterteam123.helios.terrain;

import org.joml.Vector3d;

import java.util.Random;

public class HeightGenerator {

    private static final int SAMPLE_SIZE = 256;
    private static final int[] ORDERED = new int[SAMPLE_SIZE];

    private static final double F2 = 0.5 * (Math.sqrt(3) - 1.0);
    private static final double G2 = (3.0 - Math.sqrt(3)) / 6.0;

    private static final Vector3d[] grad3 = {
            new Vector3d(1, 1, 0), new Vector3d(-1, 1, 0), new Vector3d(1, -1, 0), new Vector3d(-1, -1, 0),
            new Vector3d(1, 0, 1), new Vector3d(-1, 0, 1), new Vector3d(1, 0, -1), new Vector3d(-1, 0, -1),
            new Vector3d(0, 1, 1), new Vector3d(0, -1, 1), new Vector3d(0, 1, -1), new Vector3d(0, -1, -1)
    };


    static {
        for (int i = 0; i < ORDERED.length; i++) {
            ORDERED[i] = i;
        }
    }

    private int[] permTable = new int[512];
    private int[] permMod12Table = new int[512];

    public HeightGenerator(Random random) {
        int[] rnd = ORDERED.clone();
        for(int i = 0; i < SAMPLE_SIZE * 2; i++) {
            int from = random.nextInt(SAMPLE_SIZE);
            int to = random.nextInt(SAMPLE_SIZE);

            int temp = rnd[from];
            rnd[from] = rnd[to];
            rnd[to] = temp;
        }

        for(int i = 0; i < 512; i++) {
            int perm = rnd[i % SAMPLE_SIZE];
            permTable[i]= perm;
            permMod12Table[i] = perm % 12;
        }
    }

    public double noise(double xin, double yin) {
        double s = (xin + yin) * F2;
        double i = Math.floor(xin + s);
        double j = Math.floor(yin + s);
        double t = (i + j) * G2;
        double x0 = xin - (i - t);
        double y0 = yin - (j - t);
        boolean swap = x0 > y0;
        int i1 = swap ? 1 : 0, j1 = swap ? 0 : 1;
        double x1 = x0 - i1 + G2;
        double y1 = y0 - j1 + G2;
        double x2 = x0 - 1.0 + 2.0 * G2;
        double y2 = y0 - 1.0 + 2.0 * G2;
        int ii = (int) i % SAMPLE_SIZE;
        int jj = (int) j % SAMPLE_SIZE;
        double n0 = computeNote(x0, y0, jj, ii);
        double n1 = computeNote(x1, y1, jj + j1, ii + i1);
        double n2 = computeNote(x2, y2, jj + 1, ii + 1);
        return 70.0 * (n0 + n1 + n2);
    }

    private double computeNote(double x, double y, int permIndex, int modIndex) {
        int g = permMod12Table[modIndex + permTable[permIndex]];
        double t = 0.5 - x * x - y * y;
        if (t < 0) {
            return 0;
        }
        t *= t;
        Vector3d vec = grad3[g];
        return t * t * (vec.x * x + vec.y * y);
    }

}
