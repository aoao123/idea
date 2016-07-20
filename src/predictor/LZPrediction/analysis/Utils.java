package predictor.LZPrediction.analysis;

import predictor.LZPrediction.tree.LZPTree;
import predictor.LZPrediction.tree.LZTree;
import predictor.LZPrediction.tree.Point;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    /**
     * Conduce set experiments
     * @param locations Trajectory in lon and lat
     * @return
     *   return type: key-value pair
     *   key   means
     *   OA    Overall precision
     *   SA    Strict precision
     *   S     Predicted trajectory
     *   P*    LZP version
     *   *1    No fallback used
     *   *2    Fallback to root used
     *   *3    Fallback gradually used
     */
    public static HashMap<String,Object> Execute(List<Location> locations){
        HashMap<String,Object> res = new HashMap<>();
        BiMap<Location,Point> discretizator = GenerateDiscretizator(locations);
        BiMap<Point, Location> inverse = discretizator.inverse();
        List<Point> points = locations.stream().map(discretizator::get).collect(Collectors.toList());
        List<Point> LZPredicted1 = Predict(points, LZTree.class, LZTree.Fallback.NoFallback);
        List<Point> LZPredicted2 = Predict(points, LZTree.class, LZTree.Fallback.FallbackToRoot);
        List<Point> LZPredicted3 = Predict(points, LZTree.class, LZTree.Fallback.FallbackGradually);
        res.put("OA1", OverallAccuracy(points, LZPredicted1));
        res.put("SA1", StrictAccuracy(points, LZPredicted1));
        res.put("OA2", OverallAccuracy(points, LZPredicted2));
        res.put("OA3", OverallAccuracy(points, LZPredicted3));
        res.put("S1", LZPredicted1.stream().map(inverse::get).collect(Collectors.toList()));
        res.put("S2", LZPredicted2.stream().map(inverse::get).collect(Collectors.toList()));
        res.put("S3", LZPredicted3.stream().map(inverse::get).collect(Collectors.toList()));


        List<Point> LZPPredicted1 = Predict(points, LZPTree.class, LZTree.Fallback.NoFallback);
        List<Point> LZPPredicted2 = Predict(points, LZPTree.class, LZTree.Fallback.FallbackToRoot);
        List<Point> LZPPredicted3 = Predict(points, LZPTree.class, LZTree.Fallback.FallbackGradually);
        res.put("POA1", OverallAccuracy(points, LZPPredicted1));
        res.put("PSA1", StrictAccuracy(points, LZPPredicted1));
        res.put("POA2", OverallAccuracy(points, LZPPredicted2));
        res.put("POA3", OverallAccuracy(points, LZPPredicted3));
        res.put("PS1", LZPPredicted1.stream().map(inverse::get).collect(Collectors.toList()));
        res.put("PS2", LZPPredicted2.stream().map(inverse::get).collect(Collectors.toList()));
        res.put("PS3", LZPPredicted3.stream().map(inverse::get).collect(Collectors.toList()));
        return res;
    }

    /**
     * Run single LZ prediction
     * @param points Discretizated trajectory
     * @param treeType Class of LZ predictor
     * @param fallback Fallback Type
     * @return Predicted Trajectory, start from location 2 and end in location (length+1)
     */
    public static List<Point> Predict(List<Point> points, Class<? extends LZTree> treeType, LZTree.Fallback fallback){
        LZTree tree;
        try {
            tree = treeType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("tree instantiation exception", e);
        }
        List<Point> predicted = new ArrayList<>();
        for(Point i:points){
            tree.Read(i);
            Point p = tree.Predict(fallback);
            predicted.add(p);
        }
        return predicted;
    }

    public static double OverallAccuracy(List<Point> points, List<Point> LZPredicted) {
        double matches = 0;
        for (int i=1; i<points.size(); i++){
            if (points.get(i).equals(LZPredicted.get(i-1)))
                matches++;
        }
        return matches/(LZPredicted.size()-1);
    }

    public static double StrictAccuracy(List<Point> points, List<Point> LZPredicted) {
        double matches = 0;
        double counts = 0;
        for (int i=1; i<points.size(); i++) {
            if (LZPredicted.get(i - 1) != null) {
                counts++;
                if (points.get(i).equals(LZPredicted.get(i - 1)))
                    matches++;
            }
        }
        return matches/counts;
    }


    // generate a bidirectional translation between Location and Point
    public static HashBiMap<Location,Point> GenerateDiscretizator(List<Location> locations){
        HashBiMap<Location,Point> discretizator = HashBiMap.<Location,Point>create();
        int i = 0;
        for(Location l:locations){
            if (discretizator.containsKey(l)) continue;
            i++;
            String code = String.format("%0"+Point.ByteLength+"d",i);
            Point p = new Point(code);
            discretizator.put(l, p);
        }
        return discretizator;
    }
}
