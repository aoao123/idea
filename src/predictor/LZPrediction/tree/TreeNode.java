package predictor.LZPrediction.tree;

import com.sun.istack.internal.Nullable;

public class TreeNode {
    private final String sequence;

    public TreeNode(Point point) {
        this.sequence = point.getSequence();
    }

    private TreeNode(String sequence) {
        this.sequence = sequence;
    }

    public @Nullable TreeNode Suffix(){
        if (sequence.length()== Point.ByteLength) return null;
        return new TreeNode(sequence.substring(Point.ByteLength));
    }

    public @Nullable TreeNode Prefix(){
        if (sequence.length()== Point.ByteLength) return null;
        return new TreeNode(sequence.substring(0,sequence.length()-Point.ByteLength));
    }

    public Point LastPoint(){
        return new Point(sequence.substring(sequence.length()-Point.ByteLength));
    }

    public TreeNode Append(Point point){
        return new TreeNode(sequence.concat(point.getSequence()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeNode treeNode = (TreeNode) o;

        return sequence.equals(treeNode.sequence);

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
