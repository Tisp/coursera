import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] fractions;
    private static final double CONFIDENCE_INTERVAL_95_PERCENTAGE = 1.96;

    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("Construtor value n must be greater than 0");
        }

        if (trials <= 0) {
            throw new IllegalArgumentException("Construtor value trials must be greater than 0");
        }

        fractions = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int openedSites = 0;

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    openedSites++;
                }
            }

            fractions[i] = 1.0 * openedSites / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() {
        return mean() - CONFIDENCE_INTERVAL_95_PERCENTAGE * stddev() / Math.sqrt(fractions.length);
    }

    public double confidenceHi() {
        return mean() + CONFIDENCE_INTERVAL_95_PERCENTAGE * stddev() / Math.sqrt(fractions.length);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = ["  + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
