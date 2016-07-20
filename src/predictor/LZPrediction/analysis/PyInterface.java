package predictor.LZPrediction.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PyInterface {
    public static double[] getPrecision(double[] data){
        List<Location> input = new ArrayList<>();
        int length = data.length;
        for (int i = 0; i<length; i+=2){
            input.add(new Location(data[i],data[i+1]));
        }
        HashMap<String, Object> res = Utils.Execute(input);
        return new double[]{(double) res.get("OA1"),(double) res.get("OA2"),(double) res.get("OA3"),
                (double) res.get("SA1"), (double) res.get("POA1"), (double) res.get("POA2"),
                (double) res.get("POA3"), (double) res.get("PSA1")};
    }
}

