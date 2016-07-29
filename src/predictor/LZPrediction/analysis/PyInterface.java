package predictor.LZPrediction.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PyInterface {
    public static ArrayList<Double> getPrecision(double[] data){
        List<Location> input = new ArrayList<>();
        int length = data.length;
        for (int i = 0; i<length; i+=2){
            input.add(new Location(data[i],data[i+1]));
        }
        HashMap<String, Object> res = Utils.Execute(input);
        double[] result= new double[]{(double) res.get("OA1"),(double) res.get("OA2"),(double) res.get("OA3"),
                (double) res.get("SA1"), (double) res.get("POA1"), (double) res.get("POA2"),
                (double) res.get("POA3"), (double) res.get("PSA1")};

        ArrayList<Double> precisions=new ArrayList<Double>();
        precisions.add(result[0]);
        precisions.add(result[3]);
        precisions.add(result[1]);
        precisions.add(result[1]);
        precisions.add(result[2]);
        precisions.add(result[2]);
        precisions.add(result[4]);
        precisions.add(result[7]);
        precisions.add(result[5]);
        precisions.add(result[5]);
        precisions.add(result[6]);
        precisions.add(result[6]);

        return precisions;
    }
}

