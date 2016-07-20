package post_predictor;

import java.io.*;
import java.util.ArrayList;

/**
 * 输出predictor生成的预测结果
 * Created by LW.Meng on 2016/7/19 0019.
 */
public class OutData {
    public void outCSV(double[] data,ArrayList<String> filenames){
        for(String filename:filenames){
            FileOutputStream out=null;
            OutputStreamWriter osw=null;
            BufferedWriter bw=null;
            try {
                File file=new File(filename);
                if (file.createNewFile()){
                    out = new FileOutputStream(file);
                    osw = new OutputStreamWriter(out);
                    bw =new BufferedWriter(osw);
                }
                /*
                if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    bw.append(data).append("\r");
                }
                 */
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if (bw != null) {
                    try {
                        bw.close();
                        bw = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (osw != null) {
                    try {
                        osw.close();
                        osw = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                        out = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
