package control;


import post_predictor.OutData;
import pre_predictor.LoadData;
import predictor.LZPrediction.analysis.PyInterface;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * 程序入口
 * Created by LW.Meng on 2016/7/16 0016.
 */
public class Run {
    public static void main(String args[]){
        LoadData ld=new LoadData();
        OutData od=new OutData();
        File root=new File("TrajData");
        File[] files=root.listFiles();
        for(File file:files){
            //从csv文件中读取数据
            double[]data=ld.getCSVData(file);

            //预测
            double[] precision= PyInterface.getPrecision(data);

            //预测结果输出为csv文件
            od.outCSV(precision,ld.getFilename(file));
        }



    }






}
