package msg;

import java.util.Arrays;
import java.util.List;

/*
 * @description 儿童接种信息
 * @author wurenqing
 */
public class ChildInfo extends BaseInfo {

    /*
     * 工作台名称
     */
    private String workbenchName;

    /*
     * 儿童免疫卡号
     */
    private String chilCardNo;

    /*
     * 儿童性别
     */
    private String chilSex;

    /*
     * 儿童出生日期
     */
    private String chilBirthday;

    /*
     * 儿童姓名
     */
    private String chilName;

    /*
     * 就诊号
     */
    private String visitingNo;

    /*
     * 健康询问模板
     */
    private String[] healthModels;

    /*
     * --类型  1签名/2指纹/3拍照
     */
    private Integer[] signTypes;
    /*
     * 疫苗集合
     */
    private List<Inoculation> inoculations;

    public String getWorkbenchName() {
        return workbenchName;
    }

    public void setWorkbenchName(String workbenchName) {
        this.workbenchName = workbenchName;
    }

    public String getChilCardNo() {
        return chilCardNo;
    }

    public void setChilCardNo(String chilCardNo) {
        this.chilCardNo = chilCardNo;
    }

    public String getChilSex() {
        return chilSex;
    }

    public void setChilSex(String chilSex) {
        this.chilSex = chilSex;
    }

    public String getChilBirthday() {
        return chilBirthday;
    }

    public void setChilBirthday(String chilBirthday) {
        this.chilBirthday = chilBirthday;
    }

    public String getChilName() {
        return chilName;
    }

    public void setChilName(String chilName) {
        this.chilName = chilName;
    }

    public String getVisitingNo() {
        return visitingNo;
    }

    public void setVisitingNo(String visitingNo) {
        this.visitingNo = visitingNo;
    }

    public List<Inoculation> getInoculations() {
        return inoculations;
    }

    public void setInoculations(List<Inoculation> inoculations) {
        this.inoculations = inoculations;
    }

    public String[] getHealthModels() {
        return healthModels;
    }

    public void setHealthModels(String[] healthModels) {
        this.healthModels = healthModels;
    }

    public Integer[] getSignTypes() {
        return signTypes;
    }

    public void setSignTypes(Integer[] signTypes) {
        this.signTypes = signTypes;
    }

    @Override
    public String toString() {
        return "ChildInfo{" +
                "workbenchName='" + workbenchName + '\'' +
                ", chilCardNo='" + chilCardNo + '\'' +
                ", chilSex='" + chilSex + '\'' +
                ", chilBirthday='" + chilBirthday + '\'' +
                ", chilName='" + chilName + '\'' +
                ", visitingNo='" + visitingNo + '\'' +
                ", healthModels=" + Arrays.toString(healthModels) +
                ", signTypes=" + Arrays.toString(signTypes) +
                ", inoculations=" + inoculations +
                '}';
    }
}
