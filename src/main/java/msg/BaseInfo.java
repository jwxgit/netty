package msg;

public class BaseInfo {

    /*
     * 儿童编码
     */
    private String chilCode;

    /*
     * 登记台id
     */
    private String workbenchId;

    public String getChilCode() {
        return chilCode;
    }

    public void setChilCode(String chilCode) {
        this.chilCode = chilCode;
    }

    public String getWorkbenchId() {
        return workbenchId;
    }

    public void setWorkbenchId(String workbenchId) {
        this.workbenchId = workbenchId;
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "chilCode='" + chilCode + '\'' +
                ", workbenchId='" + workbenchId + '\'' +
                '}';
    }
}
