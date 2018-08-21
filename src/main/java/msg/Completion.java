package msg;

/*
 * @description 是否完成签核
 * @author wurenqing
 */
public class Completion extends BaseInfo {
    private  String chilCode;
    private int complete;

    @Override
    public String getChilCode() {
        return chilCode;
    }

    @Override
    public void setChilCode(String chilCode) {
        this.chilCode = chilCode;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }


    @Override
    public String toString() {
        return "Completion{" +
                "chilCode='" + chilCode + '\'' +
                ", complete=" + complete +
                '}';
    }
}
