package msg;

/*
 * @description 2.4.1 是否同意知情同意书
 * @author wurenqing
 */
public class Aggrement extends BaseInfo {
    private int aggree;

    public int getAggree() {
        return aggree;
    }

    public void setAggree(int aggree) {
        this.aggree = aggree;
    }

    @Override
    public String toString() {
        return "Aggrement{" +
                "aggree=" + aggree +
                '}';
    }
}
