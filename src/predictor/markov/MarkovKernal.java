package predictor.markov;


import java.util.ArrayList;

/**
 * Created by LW.Meng on 2016/7/27 0027.
 * markov预测器
 */
public class MarkovKernal {

    public static ArrayList<Records> getTrace(double[] data, int order, int fallback){
        ArrayList<Records> trace = new ArrayList<Records>();
        Records R1 = null;

        //提取经纬度
        int id=1;
        for(int i=0;i<data.length;i=i+2){
            id++;
            R1 = new Records(id, data[i], data[i+1]);
            trace.add(R1);
        }

        //进行预测
        if (order == 0)
            trace = predict0Trace(trace);
        if (order == 1)
            trace = predict1Trace(trace,fallback);
        if (order == 2)
            trace = predict2Trace(trace,fallback);
        if (order == 3)
            trace = predict3Trace(trace,fallback);
        return trace;

    }


    //0阶预测单个点
    public static Records predictTrace0(ArrayList<Records> trace, int i) {//i>1
        ArrayList<Sequence> predictTrace = new ArrayList<Sequence>();
        int a = 0, max;

        double y1 = 0;//lon
        double z1 = 0;//lat

        predictTrace.add(new Sequence(trace.get(0).getLon(), trace.get(0).getLat(),
                0, 0, 0, 0, 0, 0));
        for (int j = 1; j < i; j++) {

            for (int k = 0, len = predictTrace.size(); k < len; k++) {
                if (predictTrace.get(k).getLon1() == trace.get(j).getLon() && predictTrace.get(k).getLat1() == trace.get(j).getLat()) {
                    Sequence s1 = new Sequence(trace.get(j).getLon(), trace.get(j).getLat(),
                            0, 0, 0, 0, 0, 0);
                    s1.setCishu(s1.getCishu() + predictTrace.get(k).getCishu());
                    predictTrace.add(s1);
                    a++;
                    predictTrace.remove(k);

                    break;
                }
            }
            if (a == 1) {
                a = 0;
            } else {
                Sequence s2 = new Sequence(trace.get(j).getLon(), trace.get(j).getLat(),
                        0, 0, 0, 0, 0, 0);
                predictTrace.add(s2);
            }

        }
        max = predictTrace.get(0).getCishu();
        for (int m = 1; m < predictTrace.size(); m++) {
            if (max < predictTrace.get(m).getCishu()) {
                max = predictTrace.get(m).getCishu();
            }
        }
        for (int n = predictTrace.size() - 1; n > -1; n--) {
            if (max == predictTrace.get(n).getCishu()) {


                y1 = predictTrace.get(n).getLon1();
                z1 = predictTrace.get(n).getLat1();
                break;
            }
        }


        return (new Records(i + 1, z1, y1));

    }

    //一阶预测单个点
    public static Records predictTrace1(ArrayList<Records> trace, int i, int fallback) {//i>1
        ArrayList<Sequence> predictTrace1 = new ArrayList<Sequence>();
        ArrayList<Sequence> predictTrace2 = new ArrayList<Sequence>();
        ArrayList<Sequence> predictTrace3 = new ArrayList<Sequence>();

        for (int j = 0; j < i; j++) {
            predictTrace1.add(new Sequence(trace.get(j).getLon(), trace.get(j).getLat(),
                    trace.get(j + 1).getLon(), trace.get(j + 1).getLat(),
                    0, 0, 0, 0));
        }

        double y1 = 0;//lon
        double z1 = 0;//lat
        int a = 0;
        for (int k = 0; k < predictTrace1.size(); k++) {

            if (predictTrace1.get(k).getLon1() == trace.get(i).getLon() && predictTrace1.get(k).getLat1() == trace.get(i).getLat()) {
                predictTrace2.add(predictTrace1.get(k));
                a++;
            }
        }
        if (a == 0) {
            if(fallback == 0)
                return new Records(-1,0,0);
            else
                return predictTrace0(trace, i);
        }

        int b = 0;
        predictTrace3.add(predictTrace2.get(0));
        for (int m = 1; m < predictTrace2.size(); m++) {
            for (int n = 0; n < predictTrace3.size(); n++) {
                if (predictTrace3.get(n).getLat2() == predictTrace2.get(m).getLat2() && predictTrace3.get(n).getLon2() == predictTrace2.get(m).getLon2()) {
                    Sequence s1 = predictTrace2.get(m);
                    s1.setCishu(s1.getCishu() + predictTrace3.get(n).getCishu());
                    predictTrace3.remove(n);
                    predictTrace3.add(s1);
                    b++;
                    break;
                }
            }
            if (b == 1) {
                b = 0;
            } else {
                predictTrace3.add(predictTrace2.get(m));
            }

        }
        int max = predictTrace3.get(0).getCishu();
        for (int q = 1; q < predictTrace3.size(); q++) {
            if (max < predictTrace3.get(q).getCishu()) {
                max = predictTrace3.get(q).getCishu();
            }
        }
        for (int p = predictTrace3.size() - 1; p > -1; p--) {
            if (max == predictTrace3.get(p).getCishu()) {


                y1 = predictTrace3.get(p).getLon2();
                z1 = predictTrace3.get(p).getLat2();
                break;
            }
        }

        return (new Records(i + 1, z1, y1));


    }

    //二阶预测单个点
    public static Records predictTrace2(ArrayList<Records> trace, int i, int fallback) {//i>1
        ArrayList<Sequence> predictTrace1 = new ArrayList<Sequence>();
        ArrayList<Sequence> predictTrace2 = new ArrayList<Sequence>();
        ArrayList<Sequence> predictTrace3 = new ArrayList<Sequence>();

        for (int j = 1; j < i; j++) {
            predictTrace1.add(new Sequence(trace.get(j - 1).getLon(), trace.get(j - 1).getLat(),
                    trace.get(j).getLon(), trace.get(j).getLat(),
                    trace.get(j + 1).getLon(), trace.get(j + 1).getLat(), 0, 0));

        }


        double y1 = 0, y2 = 0;//lon
        double z1 = 0, z2 = 0;//lat

        int a = 0;
        for (int k = 0; k < predictTrace1.size(); k++) {
            z1 = predictTrace1.get(k).getLat1();
            z2 = predictTrace1.get(k).getLat2();

            y1 = predictTrace1.get(k).getLon1();
            y2 = predictTrace1.get(k).getLon2();


            if (y1 == trace.get(i - 1).getLon() && z1 == trace.get(i - 1).getLat() && y2 == trace.get(i).getLon() && z2 == trace.get(i).getLat()) {
                predictTrace2.add(predictTrace1.get(k));
                a++;
            }

        }
        if (a == 0) {
            if(fallback == 0)
                return new Records(-1,0,0);
            else if(fallback == 1)
                return predictTrace0(trace, i);
            else
                return predictTrace1(trace, i, 2);
        }

        int b = 0;
        predictTrace3.add(predictTrace2.get(0));
        for (int m = 1; m < predictTrace2.size(); m++) {
            for (int n = 0; n < predictTrace3.size(); n++) {
                if (predictTrace3.get(n).getLat3() == predictTrace2.get(m).getLat3() && predictTrace3.get(n).getLon3() == predictTrace2.get(m).getLon3()) {
                    Sequence s1 = predictTrace2.get(m);
                    s1.setCishu(s1.getCishu() + predictTrace3.get(n).getCishu());
                    predictTrace3.remove(n);
                    predictTrace3.add(s1);
                    b++;
                    break;
                }
            }
            if (b == 1) {
                b = 0;
            } else {
                predictTrace3.add(predictTrace2.get(m));
            }

        }
        int max = predictTrace3.get(0).getCishu();
        for (int q = 1; q < predictTrace3.size(); q++) {
            if (max < predictTrace3.get(q).getCishu()) {
                max = predictTrace3.get(q).getCishu();
            }
        }
        for (int p = predictTrace3.size() - 1; p > -1; p--) {
            if (max == predictTrace3.get(p).getCishu()) {


                y1 = predictTrace3.get(p).getLon3();
                z1 = predictTrace3.get(p).getLat3();
                break;
            }
        }


        return (new Records(i + 1, z1, y1));
    }


    //三阶预测单个点
    public static Records predictTrace3(ArrayList<Records> trace, int i, int fallback) {//i>1
        ArrayList<Sequence> predictTrace1 = new ArrayList<Sequence>();
        ArrayList<Sequence> predictTrace2 = new ArrayList<Sequence>();
        ArrayList<Sequence> predictTrace3 = new ArrayList<Sequence>();

        for (int j = 2; j < i; j++) {
            predictTrace1.add(new Sequence(trace.get(j - 2).getLon(), trace.get(j - 2).getLat(),
                    trace.get(j - 1).getLon(), trace.get(j - 1).getLat(),
                    trace.get(j).getLon(), trace.get(j).getLat(),
                    trace.get(j + 1).getLon(), trace.get(j + 1).getLat()));

        }

        double y1 = 0, y2 = 0, y3 = 0;//lon
        double z1 = 0, z2 = 0, z3 = 0;//lat

        int a = 0;
        for (int k = 0; k < predictTrace1.size(); k++) {
            z1 = predictTrace1.get(k).getLat1();
            z2 = predictTrace1.get(k).getLat2();
            z3 = predictTrace1.get(k).getLat3();

            y1 = predictTrace1.get(k).getLon1();
            y2 = predictTrace1.get(k).getLon2();
            y3 = predictTrace1.get(k).getLon3();


            if (y1 == trace.get(i - 2).getLon() && z1 == trace.get(i - 2).getLat() && y2 == trace.get(i - 1).getLon() && z2 == trace.get(i - 1).getLat() && y3 == trace.get(i).getLon() && z3 == trace.get(i).getLat()) {
                predictTrace2.add(predictTrace1.get(k));
                a++;
            }

        }
        if (a == 0) {
            if(fallback == 0)
                return new Records(-1,0,0);
            else if(fallback == 1)
                return predictTrace0(trace, i);
            else
                return predictTrace2(trace, i, 2);
        }
        int b = 0;
        predictTrace3.add(predictTrace2.get(0));
        for (int m = 1; m < predictTrace2.size(); m++) {
            for (int n = 0; n < predictTrace3.size(); n++) {
                if (predictTrace3.get(n).getLat4() == predictTrace2.get(m).getLat4() && predictTrace3.get(n).getLon4() == predictTrace2.get(m).getLon4()) {
                    Sequence s1 = predictTrace2.get(m);
                    s1.setCishu(s1.getCishu() + predictTrace3.get(n).getCishu());
                    predictTrace3.remove(n);
                    predictTrace3.add(s1);
                    b++;
                    break;
                }
            }
            if (b == 1) {
                b = 0;
            } else {
                predictTrace3.add(predictTrace2.get(m));
            }

        }
        int max = predictTrace3.get(0).getCishu();
        for (int q = 1; q < predictTrace3.size(); q++) {
            if (max < predictTrace3.get(q).getCishu()) {
                max = predictTrace3.get(q).getCishu();
            }
        }
        for (int p = predictTrace3.size() - 1; p > -1; p--) {
            if (max == predictTrace3.get(p).getCishu()) {


                y1 = predictTrace3.get(p).getLon4();
                z1 = predictTrace3.get(p).getLat4();
                break;
            }
        }
        return (new Records(i + 1, z1, y1));
    }

    //三阶预测算法
    public static ArrayList<Records> predict3Trace(ArrayList<Records> trace, int fallback) {
        int size = trace.size();
        ArrayList<Records> trace1 = new ArrayList<Records>();

        for (int i = 1; i < size; i++) {
            trace1.add(predictTrace3(trace, i, fallback));
        }


        return trace1;
    }


    //二阶预测算法
    public static ArrayList<Records> predict2Trace(ArrayList<Records> trace, int fallback) {
        int size = trace.size();
        ArrayList<Records> trace1 = new ArrayList<Records>();


        for (int i = 1; i < size; i++) {

            trace1.add(predictTrace2(trace, i, fallback));

        }


        return trace1;
    }

    //一阶预测算法
    public static ArrayList<Records> predict1Trace(ArrayList<Records> trace, int fallback) {
        int size = trace.size();
        ArrayList<Records> trace1 = new ArrayList<Records>();

        for (int i = 1; i < size; i++) {

            trace1.add(predictTrace1(trace, i, fallback));

        }

        return trace1;
    }

    //零阶预测算法
    public static ArrayList<Records> predict0Trace(ArrayList<Records> trace) {
        int size = trace.size();
        ArrayList<Records> trace1 = new ArrayList<Records>();

        for (int i = 1; i < size; i++) {

            trace1.add(predictTrace0(trace, i));

        }

        return trace1;
    }
}
