package pre_predictor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 为predictor准备输入数据
 * Created by LW.Meng on 2016/7/19 0019.
 */
public class LoadData {

    /**
     * 从csv文件中抽取数据
     * @return double[]
     */
    public double[] getCSVData(File file){
        List<Double> datas = new ArrayList<Double>();
        BufferedReader reader=null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString=reader.readLine())!=null){
                String[] spl=tempString.split(",");
                datas.add(Double.parseDouble(spl[1]));
                datas.add(Double.parseDouble(spl[2]));

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
        //将list<Double>转化为double[]
        int size=datas.size();
        Double[] temp=datas.toArray(new Double[size]);
        double[] result=new double[size];
        for (int i=0;i<size;i++){
            //保留两位小数
            BigDecimal bd = new BigDecimal(temp[i]);
            result[i]=bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return result;
    }

    /**
     * 获取文件名e
     * @param file 输入文件
     * @return 文件名
     */
    public String getFilename(File file){
        return file.getName();
    }

}
