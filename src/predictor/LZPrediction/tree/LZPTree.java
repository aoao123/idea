package predictor.LZPrediction.tree;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class LZPTree extends LZTree {
    @Override
    protected void Fit() {
        for(TreeNode suffix = buffer;suffix!=null;suffix = suffix.Suffix()){
            HashMap<Point, Integer> node = nodes.get(suffix);
            HashMap<Point, Integer> prefix = nodes.get(suffix.Prefix());
            if (node == null) {
                nodes.put(suffix, new LinkedHashMap<>());
                prefix.put(point, 1);
                context = suffix;
                buffer = null;
            } else {
                // remove a entry first then add it back so LinkedHashMap order it in the last position
                int count = prefix.remove(point);
                prefix.put(point, count + 1);
            }
        }
    }

}
