package predictor.markov;


import java.util.ArrayList;

/**
 * Created by LW.Meng on 2016/7/27 0027.
 * markov预测器接口
 */
public class MarkovInter {
    public static ArrayList<Double> getPrecision(double[] data){

        ArrayList<Double> precisions=new ArrayList<Double>();

        double[] markov00=Campare.result(data,MarkovKernal.getTrace(data,0,0));
        double[] markov10= Campare.result(data,MarkovKernal.getTrace(data,1,0));
        double[] markov11= Campare.result(data,MarkovKernal.getTrace(data,1,1));
        double[] markov20= Campare.result(data,MarkovKernal.getTrace(data,2,0));
        double[] markov21= Campare.result(data,MarkovKernal.getTrace(data,2,1));
        double[] markov22= Campare.result(data,MarkovKernal.getTrace(data,2,2));
        double[] markov30= Campare.result(data,MarkovKernal.getTrace(data,3,0));
        double[] markov31= Campare.result(data,MarkovKernal.getTrace(data,3,1));
        double[] markov32= Campare.result(data,MarkovKernal.getTrace(data,3,2));

        //合并准确率结果
        add(precisions,markov00);
        add(precisions,markov10);
        add(precisions,markov11);
        add(precisions,markov20);
        add(precisions,markov21);
        add(precisions,markov22);
        add(precisions,markov30);
        add(precisions,markov31);
        add(precisions,markov32);

        return precisions;

    }

    private static void add(ArrayList<Double> precisions,double[] data){
        precisions.add(data[0]);
        precisions.add(data[1]);
    }
}
