package post_predictor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 输出predictor生成的预测结果
 * Created by LW.Meng on 2016/7/19 0019.
 */
public class OutData {
    public void outCSV(double[] precision,String filename){
        final String NEW_LINE = "\r\n";  //换行符
        StringBuilder csvStr = new StringBuilder();
        try {
            File file=new File("precisions/"+filename);
            if (file.createNewFile()){
                //把double[]转换为List<String>
                List<String> result = new ArrayList<String>();
                for(int i=0;i<precision.length;i=i+2){
                    result.add(Double.toString(precision[i])+","+Double.toString(precision[i+1]));
                }

                //数据行
                for(String csvData : result){
                    csvStr.append(csvData).append(NEW_LINE);
                }

                //写文件
                Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                writer.write(csvStr.toString());
                writer.flush();
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
