package msg;

/*
 * 接种信息
 */
public class Inoculation {
    /*
     * 疫苗编码
     */
    private String inocBactCode;

    /*
     * 疫苗名称
     */
    private String inocBactName;

    /*
     * 接种医生
     */
    private String inocDoctor;

    /*
     * 疫苗批次号
     */
    private String inocBactchNo;

    /*
     * 疫苗生产产家
     */
    private String inocCorpName;

    /*
     * 知情同意书模板
     */
    private String inocModel;

    /*
     * 有效期
     */
    private String inocValidDate;

    public String getInocBactCode() {
        return inocBactCode;
    }

    public void setInocBactCode(String inocBactCode) {
        this.inocBactCode = inocBactCode;
    }

    public String getInocBactName() {
        return inocBactName;
    }

    public void setInocBactName(String inocBactName) {
        this.inocBactName = inocBactName;
    }

    public String getInocDoctor() {
        return inocDoctor;
    }

    public void setInocDoctor(String inocDoctor) {
        this.inocDoctor = inocDoctor;
    }

    public String getInocBactchNo() {
        return inocBactchNo;
    }

    public void setInocBactchNo(String inocBactchNo) {
        this.inocBactchNo = inocBactchNo;
    }

    public String getInocCorpName() {
        return inocCorpName;
    }

    public void setInocCorpName(String inocCorpName) {
        this.inocCorpName = inocCorpName;
    }

    public String getInocValidDate() {
        return inocValidDate;
    }

    public void setInocValidDate(String inocValidDate) {
        this.inocValidDate = inocValidDate;
    }

    public String getInocModel() {
        return inocModel;
    }

    public void setInocModel(String inocModel) {
        this.inocModel = inocModel;
    }

    @Override
    public String toString() {
        return "Inoculation{" +
                "inocBactCode='" + inocBactCode + '\'' +
                ", inocBactName='" + inocBactName + '\'' +
                ", inocDoctor='" + inocDoctor + '\'' +
                ", inocBactchNo='" + inocBactchNo + '\'' +
                ", inocCorpName='" + inocCorpName + '\'' +
                ", inocModel='" + inocModel + '\'' +
                ", inocValidDate='" + inocValidDate + '\'' +
                '}';
    }
}
