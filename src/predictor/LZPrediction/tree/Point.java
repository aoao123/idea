package predictor.LZPrediction.tree;

public class Point {
    public static final int ByteLength = 3;

    private final String sequence;

    public Point(String sequence) {
        if(sequence.length()!=ByteLength) throw new RuntimeException("Point code length error");
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return sequence.equals(point.sequence);

    }

    @Override
    public int hashCode() {
        return sequence.hashCode();
    }

    @Override
    public String toString() {
        return sequence;
    }
}
