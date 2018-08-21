package msg;

/*
 * 客户端推送图片
 */
public class SignImage extends BaseInfo {

    /*
     * 1签字图像/2指纹图像/3拍照图像/4摄像头图片/5pad桌面图像
     */
    private int imgType;

    /*
     * 文件名称
     */
    private String imgName;

    /*
     * base64（图像）
     */
    private String imgContent;


    public int getImgType() {
        return imgType;
    }

    public void setImgType(int imgType) {
        this.imgType = imgType;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgContent() {
        return imgContent;
    }

    public void setImgContent(String imgContent) {
        this.imgContent = imgContent;
    }

    @Override
    public String toString() {
        return "SignImage{" +
                "imgType=" + imgType +
                ", imgName='" + imgName + '\'' +
                ", imgContent='" + imgContent + '\'' +
                '}';
    }
}
