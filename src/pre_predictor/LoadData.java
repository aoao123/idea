package pre_predictor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 为predictor准备输入数据
 * Created by LW.Meng on 2016/7/19 0019.
 */
public class LoadData {
    private ArrayList<String> filenames;

    /**
     * 从trajData文件夹下的csv文件中抽取数据
     * @return double[]
     */
    public double[] getCSVData(){
        List<Double> datas = new ArrayList<Double>();
        File root=new File("TrajData");
        File[] files=root.listFiles();
        for(File file:files){
            filenames.add(file.getName());
            BufferedReader reader=null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                while ((tempString=reader.readLine())!=null){
                    String[] spl=tempString.split(",");
                    /*
                    for (String str:spl) {
                        datas.add(Double.parseDouble(str));
                    }
                    */
                    datas.add(Double.parseDouble(spl[1]));
                    datas.add(Double.parseDouble(spl[1]));

                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if (reader!=null){
                    try {
                        reader.close();
                    }catch (IOException e1){
                        e1.printStackTrace();
                    }
                }
            }
        }
        //将list<Double>转化为double[]
        int size=datas.size();
        Double[] temp=datas.toArray(new Double[size]);
        double[] result=new double[size];
        for (int i=0;i<size;i++){
            result[i]=temp[i];
        }
        return result;
    }

    public ArrayList<String> getFilenames(){
        return filenames;
    }
}
