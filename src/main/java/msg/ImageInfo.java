package msg;

/*
 * @description 2.4.1 是否同意知情同意书
 * @author wurenqing
 */
public class ImageInfo extends BaseInfo {

    private String imgMd5;

    private String imgName;

    private String imgContent;

    public String getImgMd5() {
        return imgMd5;
    }

    public void setImgMd5(String imgMd5) {
        this.imgMd5 = imgMd5;
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
        return "ImageInfo{" +
                "imgMd5='" + imgMd5 + '\'' +
                ", imgName='" + imgName + '\'' +
                ", imgContent='" + imgContent + '\'' +
                '}';
    }
}
