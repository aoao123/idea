package predictor.markov;

/**
 * Created by LW.Meng on 2016/7/27 0027.
 * 用于预测的辅助数据结构
 */
public class Sequence {
    private double lon1,lat1,lon2,lat2,lon3,lat3,lon4,lat4;
    private int cishu;

    public Sequence(double lon1, double lat1,double lon2, double lat2
            ,double lon3,double lat3
            ,double lon4, double lat4
    ) {

        this.lon1=lon1;
        this.lat1=lat1;

        this.lon2=lon2;
        this.lat2=lat2;

        this.lon3=lon3;
        this.lat3=lat3;

        this.lon4=lon4;
        this.lat4=lat4;
        this.cishu=1;
    }


    public double getLat1() {
        return lat1;
    }

    public void setLat1(double lat1) {
        this.lat1 = lat1;
    }

    public double getLon1() {
        return lon1;
    }

    public void setLon1(double lon1) {
        this.lon1 = lon1;
    }



    public double getLat2() {
        return lat2;
    }

    public void setLat2(double lat2) {
        this.lat2 = lat2;
    }

    public double getLon2() {
        return lon2;
    }

    public void setLon2(double lon2) {
        this.lon2 = lon2;
    }



    public double getLat3() {
        return lat3;
    }

    public void setLat3(double lat3) {
        this.lat3 = lat3;
    }

    public double getLon3() {
        return lon3;
    }

    public void setLon3(double lon3) {
        this.lon3 = lon3;
    }

    public int getCishu() {
        return cishu;
    }

    public void setCishu(int cishu) {
        this.cishu = cishu;
    }



    public double getLon4() {
        return lon4;
    }

    public void setLon4(double lon4) {
        this.lon4 = lon4;
    }

    public double getLat4() {
        return lat4;
    }

    public void setLat4(double lat4) {
        this.lat4 = lat4;
    }

    public void addCishu(){
        this.cishu++;
    }
}
