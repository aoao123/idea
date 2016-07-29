package predictor.markov;

/**
 * Created by LW.Meng on 2016/7/27 0027.
 * 预测点的数据类
 */
public class Records {

    private double lon,lat;//经纬度
    private int id;

    //构造函数
    public Records(int id,double lat,double lon) {

        this.lon = lon;
        this.lat = lat;
        this.id=id;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
