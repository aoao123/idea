package control;


import pre_predictor.LoadData;
import predictor.LZPrediction.analysis.PyInterface;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * 程序入口，仅进行调用
 * Created by LW.Meng on 2016/7/16 0016.
 */
public class Run {
    public static void main(String args[]){
        LoadData ld=new LoadData();
        double[] d=ld.getCSVData();
        double[] dataList= PyInterface.getPrecision(d);
        ArrayList<String> filenames=ld.getFilenames();



    }


    private void outCSV(double[] data,String filename){


    }


}
