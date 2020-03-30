import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] grid;
    private int numberOfOpenSites;
    private final int gridSize;
    private final int top;
    private final int botton;
    private final WeightedQuickUnionUF weightedQuickUnionUF;

    public Percolation(int n) {
        this.gridSize = n;

        if (this.gridSize <= 0) {
            throw new IllegalArgumentException();
        }
        this.grid = new boolean[this.gridSize * this.gridSize + 2];
        this.top = 0;
        this.botton = this.gridSize * this.gridSize + 1;
        this.grid[top] = true;
        this.grid[botton] = true;
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(this.gridSize * this.gridSize + 2);
    }

    public void open(int row, int col) {
        this.assertInRange(row, col);
        int index = this.xyTo1D(row, col);
        this.grid[index] = true;
        this.numberOfOpenSites++;

        if (row == 1) {
            this.weightedQuickUnionUF.union(index, this.top);
        }

        if (row == this.gridSize) {
            this.weightedQuickUnionUF.union(index, this.botton);
        }

        if (row > 1 && this.isOpen(row - 1, col)) {
            this.weightedQuickUnionUF.union(index, this.xyTo1D(row - 1, col));
        }

        if (row < this.gridSize && this.isOpen(row + 1, col)) {
            this.weightedQuickUnionUF.union(index, this.xyTo1D(row + 1, col));
        }

        if (col > 1 && this.isOpen(row, col - 1)) {
            this.weightedQuickUnionUF.union(index, this.xyTo1D(row, col - 1));
        }

        if (col < this.gridSize && this.isOpen(row, col + 1)) {
            this.weightedQuickUnionUF.union(index, this.xyTo1D(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        this.assertInRange(row, col);
        return this.grid[this.xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        this.assertInRange(row, col);
        return this.weightedQuickUnionUF.find(top) == this.weightedQuickUnionUF.find(this.xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    public boolean percolates() {
        return this.weightedQuickUnionUF.find(this.top) == this.weightedQuickUnionUF.find(this.botton);
    }

    private int xyTo1D(int row, int col) {
        this.assertInRange(row, col);
        return (row - 1) * this.gridSize + col;
    }

    private void assertInRange(int row, int col) {
        if (row < 1 || row > this.gridSize) {
            throw new IllegalArgumentException("Row index is out of bounds");
        }

        if (col < 1 || col > this.gridSize) {
            throw new IllegalArgumentException("Column index is out of bounds");
        }
    }
}
