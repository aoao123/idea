package predictor.markov;


import java.util.ArrayList;

/**
 * Created by LW.Meng on 2016/7/27 0027.
 * 计算预测结果的准确率
 */
public class Campare {
    public static double[] result(double[] data,ArrayList<Records> prediction){

        //prediction.remove(0);

        ArrayList<Records> trace1 = getRealTrace(data);
        ArrayList<Records> trace2 = prediction;

        int j=0,k=0;

        for(int i=0;i<trace2.size();i++){
            if(trace1.get(i).getLat()==trace2.get(i).getLat()&&trace1.get(i).getLon()==trace2.get(i).getLon()){
                j++;
            }
            if(trace2.get(i).getLat()==0&&trace2.get(i).getLon()==0){
                k++;
            }
        }
        double precision1 = ((double)j)/trace2.size();
        double precision2 = ((double)j)/(trace2.size()-k);
        if(trace2.size()-k==0)
            precision2 = 0;
        return new double[]{precision1,precision2};
    }

    /**
     * 获取实际轨迹
     * @param data
     * @return
     */
    private static ArrayList<Records> getRealTrace(double[] data){
        ArrayList<Records> trace = new ArrayList<Records>();
        Records R1 = null;
        int id=1;
        for(int i=0;i<data.length;i=i+2){
            id++;
            R1 = new Records(id, data[i], data[i+1]);
            trace.add(R1);
        }
        trace.remove(0);
        return trace;
    }
}
