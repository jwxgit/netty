package msg;

import utils.DateUtil;
import utils.FileUtils;
import utils.ImageUtils;
import utils.Md5Utils;

import java.io.File;
import java.util.*;

/**
 * Created by ZHANGZHIYU on 2018/8/7.
 */

public class MessageFactory {
   public enum  MsgTypeEnum {
        CLIENT_AGREEMENT("CLIENT_AGREEMENT", "用户同意/不同意"),
        CLIENT_GET_CONSENT_FORM("CLIENT_GET_CONSENT_FORM", "获取知情同意书"),
        CLIENT_UPLOAD_IMG("CLIENT_UPLOAD_IMG", "客户端向服务端上传签核图片"),
        CLIENT_HEARTBEAT("CLIENT_HEARTBEAT", "心跳连接服务端-PAD"),

        SERVER_SIGNATURE_CANCEL("SERVER_SIGNATURE_CANCEL", "终止消息服务端-PAD"),
        SERVER_COMPLETE("SERVER_COMPLETE", "签核完成服务端-PAD"),
        SERVER_PUSH_IMAGE("SERVER_PUSH_IMAGE", "服务端向客户端发送图片"),
        SERVER_DO_SIGNATURE("SERVER_DO_SIGNATURE", "服务端向客户端发送签核指令"),
        SERVER_SIGNATURE_START("SERVER_SIGNATURE_START", "开始签核信息");

        MsgTypeEnum(String msgType, String desc) {
            this.msgType = msgType;
            this.desc = desc;
        }
        private String msgType;
        private String desc;

        public String getMsgType() {
            return msgType;
        }

        public String getDesc() {
            return desc;
        }
    }

    public static Map<String,String> IMAGE_CACHE = new HashMap<>();
   static {
       File[] files = new File[] {new File("assets/bacterin"),new File("assets/health")};
       for(File file : files) {
           if(file.exists() && file.isDirectory()) {
               for(File f : file.listFiles()) {
                   IMAGE_CACHE.put(FileUtils.getMd5ByFile(f), f.getAbsolutePath());
               }
           }
       }
   }

    private static  Message getModel(MsgTypeEnum msgType){
        Message message = new Message();
        message.setMsgId(UUID.randomUUID().toString());
        message.setMsgTimestamp(DateUtil.dateToString(new Date(),DateUtil.YEAR_TO_MINSECOND));
        message.setMsgType(msgType.getMsgType());
        return message;
    }

    public static class ClientMessages {
        /**
         * 获取签名消息测试消息
         *
         * @return
         */
        public static Message getClientSignMessage(String fileName) {
            if(fileName==null){
                fileName = "0101.jpg";
            }
            int imgType = 1;
            return getMutilClientMessage(fileName, MsgTypeEnum.CLIENT_UPLOAD_IMG.getMsgType(), imgType);
        }
        public static Message getClientSignMessage( ) {
            return getClientSignMessage("0101.jpg");
        }

        /**
         * 获取指纹消息测试消息
         *
         * @return
         */
        public static Message getClienteFignerPrintMessag() {
            String fileName = "finger.jpg";
            int imgType = 2;
            return getMutilClientMessage(fileName, MsgTypeEnum.CLIENT_UPLOAD_IMG.getMsgType(), imgType);
        }

        /**
         * 获取面部照片测试消息
         *
         * @return
         */
        public static Message getClientFacePictureMessage() {
            String fileName = "picture.jpg";
            int imgType = 3;
            return getMutilClientMessage(fileName, MsgTypeEnum.CLIENT_UPLOAD_IMG.getMsgType(), imgType);
        }

        /**
         * 获取桌面快照测试消息
         *
         * @return
         */
        public static Message getClientDestTopSnapShotMessage() {
            String fileName = "desktop.jpg";
            int imgType = 4;
            return getMutilClientMessage(fileName, MsgTypeEnum.CLIENT_UPLOAD_IMG.getMsgType(), imgType);
        }

        /**
         * 用户是否同意消息
         *
         * @return
         */
        public static Message getClientAgreementMessage(int isAgree) {
            Message message = new Message();
            message.setMsgId(UUID.randomUUID().toString());
            String dateStr = DateUtil.dateToString(new Date(), DateUtil.YEAR_TO_MINSECOND);
            message.setMsgTimestamp(dateStr);
            message.setMsgType("CLIENT_AGREEMENT");
            message.setMsgMd5(Md5Utils.convertMD5(dateStr));
            Aggrement aggrement = new Aggrement();
            aggrement.setAggree(isAgree);
            aggrement.setChilCode(UUID.randomUUID().toString());
            message.setData(aggrement);
            return message;
        }

        /**
         * 根据文件md5要求获取服务器对应文件的消息
         *
         * @return
         */
        public static Message getClientGetConsentFormMessage(String imgId) {
            Message message = new Message();
            message.setMsgId(UUID.randomUUID().toString());
            String dateStr = DateUtil.dateToString(new Date(), DateUtil.YEAR_TO_MINSECOND);
            message.setMsgTimestamp(dateStr);
            message.setMsgType("CLIENT_GET_CONSENT_FORM");
            message.setMsgMd5(Md5Utils.convertMD5(dateStr));
            ImgId  imgId1=new ImgId();
            imgId1.setImgId(imgId);
            message.setData(imgId1);
            return message;
        }
    }


    /**
     *    获取客户端签核及桌面图片测试工具类
     * @param fileName  文件名称
     * @param msgType
     * @param imgType      --1签字图像/2指纹图像/3拍照图像/4摄像头图片
     * @return
     */
    private    static  Message  getMutilClientMessage(String fileName ,String msgType, int imgType){
        Message message = new Message();
        message.setMsgId(UUID.randomUUID().toString());
        String dateStr =  DateUtil.dateToString(new Date(),DateUtil.YEAR_TO_MINSECOND);
        message.setMsgTimestamp(dateStr);
        message.setMsgType(msgType);

        SignImage  signImage = new SignImage();
        signImage.setImgName(fileName);
        signImage.setImgType(imgType);
        String  fileBase64 =  ImageUtils.fileToBase64(ImageUtils.getFile(fileName));
        signImage.setImgContent(fileBase64);
        signImage.setChilCode(UUID.randomUUID().toString());

        String msgMd5 =  Md5Utils.string2MD5(dateStr+fileBase64);
        message.setMsgMd5(msgMd5);
        message.setData(signImage);
        return  message;
    }


    public static class  ServerCommand{

        /**
         *       * 发送签核信息 2.3.1  ,
         * @param types  签核流程
         * @return
         */
        public  static Message   getServerCommandServerSignatureStartmessage( Integer []types  ){
            if(types==null ||types.length<=0){
                types = new Integer[]{1,2,3} ;
            }
            ChildInfo  childInfo = mockChildInfo(types);
            Message message = new Message();
            message.setMsgId(UUID.randomUUID().toString());
            String dateStr =  DateUtil.dateToString(new Date(),DateUtil.YEAR_TO_MINSECOND);
            message.setMsgTimestamp(dateStr);
            message.setMsgType("SERVER_SIGNATURE_START");
            message.setMsgMd5(Md5Utils.convertMD5(dateStr));; //这里没有把内容加进去校验
            message.setData(childInfo);
            return  message;
        }

        /**
         *  图片  2.4.4
         * @param
         * @return  md5
         */
        public  static Message   getServerCommandServerPushImagemessage(String fileName){
            Message message = new Message();
            message.setMsgId(UUID.randomUUID().toString());
            String dateStr =  DateUtil.dateToString(new Date(),DateUtil.YEAR_TO_MINSECOND);
            message.setMsgTimestamp(dateStr);
            message.setMsgType("SERVER_PUSH_IMAGE");
            message.setMsgMd5(Md5Utils.convertMD5(dateStr));; //这里没有把内容加进去校验
            ImageInfo  imageInfo = new ImageInfo();
            File file = ImageUtils.getFile(fileName);
            imageInfo.setImgMd5(FileUtils.getMd5ByFile(file));
            imageInfo.setImgName(fileName);
            imageInfo.setImgContent(ImageUtils.fileToBase64(file));
//            imageInfo.setChilCode();
//            imageInfo.setWorkbenchId();
            message.setData(imageInfo);
            return  message;
        }



        /**
         * 签字指令消息
         * @return
         */
        public  static Message   getServerCommandSignMessage(String  isCancel){
            return   getServerCommandMessage("010",isCancel);
        }

        /**
         * 指纹指令消息
         * @return
         */
        public  static Message   getServerCommandFignerPrintMessag(String  isCancel){
            return   getServerCommandMessage("020",isCancel);
        }

        /**
         * 签字指令消息
         * @return
         */
        public  static Message   getServerCommandFacePictureMessage(String  isCancel){
            return   getServerCommandMessage("030",isCancel);
        }


        /**
         * 签字指令消息
         * @return
         */
        public  static Message   getServerCommandPopuWindowFacePictureMessage(String  isCancel){
            return   getServerCommandMessage("031",isCancel);
        }

        /**
         * 服务端签核完成消息
         * @return 1完成 0未完成
         */
        public  static Message   getServerCompleteMessage(int  isComplete){
            Message message = new Message();
            message.setMsgId(UUID.randomUUID().toString());
            String dateStr =  DateUtil.dateToString(new Date(),DateUtil.YEAR_TO_MINSECOND);
            message.setMsgTimestamp(dateStr);
            message.setMsgType("SERVER_COMPLETE");
            message.setMsgMd5(Md5Utils.convertMD5(dateStr));; //这里没有把内容加进去校验
            Completion  completion = new Completion();
            completion.setComplete(isComplete);
            completion.setChilCode(UUID.randomUUID().toString());
            message.setData(completion);
            return   message;
        }


        /**
         * 取消回到主页面消息
         * @return
         */
        public  static Message   getServerServerSignatureCancelMessage(){
            Message message = new Message();
            message.setMsgId(UUID.randomUUID().toString());
            String dateStr =  DateUtil.dateToString(new Date(),DateUtil.YEAR_TO_MINSECOND);
            message.setMsgTimestamp(dateStr);
            message.setMsgType("SERVER_SIGNATURE_CANCEL");
            message.setMsgMd5(Md5Utils.convertMD5(dateStr));; //这里没有把内容加进去校验
            return  message;
        }


    }

    /**
     * 获取服务度
     * @return   ”010”, --010签字指令020指纹/030弹出拍照框 031拍照抓取图像
     */
   private  static  Message getServerCommandMessage(String operation,String  isCancel){
       Message message = new Message();
       message.setMsgId(UUID.randomUUID().toString());
       String dateStr =  DateUtil.dateToString(new Date(),DateUtil.YEAR_TO_MINSECOND);
       message.setMsgTimestamp(dateStr);
       message.setMsgType("SERVER_DO_SIGNATURE");
       ServerCommandOperation  serverCommand = new ServerCommandOperation();
       serverCommand.setChilCode(UUID.randomUUID().toString());
       serverCommand.setOperation(operation);
       serverCommand.setIsCancel(isCancel);
       message.setData(serverCommand);
       message.setMsgMd5(Md5Utils.convertMD5(dateStr));; //这里没有把内容加进去校验
       return  message;
   }

    private static ChildInfo mockChildInfo(Integer[] signTypes){
        ChildInfo childInfo = new ChildInfo();
        childInfo.setChilCode(UUID.randomUUID().toString());
        childInfo.setChilBirthday("2015-01-01");
        childInfo.setChilCardNo("1051213");
        childInfo.setChilName("张三");
        childInfo.setChilSex("男");
        childInfo.setVisitingNo("A001");
        childInfo.setWorkbenchName("1号接种台");
        childInfo.setSignTypes(signTypes);
        List<Inoculation>  inoculations = new ArrayList<>();
        Inoculation inoc1 = new Inoculation();
        inoc1.setInocBatchNo("20180101-1");
        inoc1.setInocBactCode("0101");
        inoc1.setInocBactName("卡介苗");
        inoc1.setInocCorpName("深圳康泰");
        inoc1.setInocValidDate("2020-08-09");
        inoc1.setInocDoctor("张医生");
        inoc1.setInocModel(FileUtils.getMd5ByFile(ImageUtils.getFile("0101.jpg")));
        inoculations.add(inoc1);
        Inoculation inoc2 = new Inoculation();
        inoc2.setInocBatchNo("20180101-1");
        inoc2.setInocBactCode("0201");
        inoc2.setInocBactName("乙肝");
        inoc2.setInocCorpName("深圳康泰");
        inoc2.setInocValidDate("2020-08-09");
        inoc2.setInocDoctor("张医生");
        inoc2.setInocModel(FileUtils.getMd5ByFile(ImageUtils.getFile("0201.jpg")));
        inoculations.add(inoc2);
        childInfo.setInoculations(inoculations);
        childInfo.setHealthModels(new String[] {FileUtils.getMd5ByFile(ImageUtils.getFile("HealthyQuestion.jpg"))});
       return childInfo;
    }


    public static void main(String[] args) {

  /*      System.out.println( MessageFactory.ServerCommand.getServerCommandSignMessage("0"));
        System.out.println( MessageFactory.ServerCommand.getServerCommandFacePictureMessage("0"));
        System.out.println( MessageFactory.ServerCommand.getServerCommandFignerPrintMessag("0"));
        System.out.println( MessageFactory.ServerCommand.getServerCommandSignMessage("1"));
        System.out.println( MessageFactory.ServerCommand.getServerCommandFacePictureMessage("1"));
        System.out.println( MessageFactory.ServerCommand.getServerCommandFignerPrintMessag("1"));*/

        System.out.println(MessageFactory.ServerCommand.getServerCommandServerSignatureStartmessage(new Integer[]{1,2,3}));

    }
}
