package predictor.LZPrediction.tree;

import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;

import java.util.*;

public class LZTree {
    // use LinkedHashMap to maintain insertion order
    protected HashMap<TreeNode,LinkedHashMap<Point,Integer>> nodes = new HashMap<>();

    protected TreeNode buffer = null;
    protected TreeNode context = null;
    protected Point point;

    public LZTree() {
        nodes.put(null, new LinkedHashMap<>());
    }

    public @Nullable Point Predict(Fallback fallback){
        TreeNode context = buffer==null?this.context:buffer;
        if (point==null){
            throw new RuntimeException("Refuse to predict location 1");
        }
        LinkedHashMap<Point,Integer> node = nodes.get(context);
        if(node.size()==0){
            switch (fallback){
                case NoFallback:
                    return null;
                case FallbackToRoot:
                    node = nodes.get(null);
                    break;
                case FallbackGradually:
                    while (true){
                        context = context.Suffix();
                        node = nodes.get(context);
                        if (node!=null && node.size()!=0) break;
                    }
                    break;
                default:
                    throw new RuntimeException("wrong fallback code");
            }
        }
        // reverse the order to obtain the last inserted point when meeting a tie
        return Lists.reverse(new ArrayList<>(node.entrySet())).stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
    }

    public void Read(Point point){
        this.point = point;
        if (buffer==null){
            buffer = new TreeNode(point);
        } else {
            buffer = buffer.Append(point);
        }
        Fit();
    }

    protected void Fit() {
        HashMap<Point,Integer> node = nodes.get(buffer);
        HashMap<Point,Integer> prefix = nodes.get(buffer.Prefix());
        if (node==null){
            nodes.put(buffer, new LinkedHashMap<>());
            context = buffer;
            buffer = null;
            prefix.put(point, 1);
        }else {
            // remove a entry first then add it back so LinkedHashMap order it in the last position
            int count = prefix.remove(point);
            prefix.put(point, count + 1);
        }
    }

    public enum Fallback {
        NoFallback, FallbackToRoot, FallbackGradually
    }
}
