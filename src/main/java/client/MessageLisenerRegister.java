package client;

import msg.MessageFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageLisenerRegister {
    private static Map<MessageFactory.MsgTypeEnum,MessageLisener>  REGISTER = new  HashMap();
    private  MessageLisenerRegister(){
    }
    /**
     * 注册消息监听
     * @param msgtype
     * @param lisener
     * @return
     */
    public static boolean registMessageLisener(MessageFactory.MsgTypeEnum msgtype,MessageLisener lisener){
        try {
            REGISTER.put(msgtype,lisener);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(MessageLisenerRegister.class.getName()).log(Level.WARNING,"注册监听失败"+msgtype.getDesc());
        }
        return false;
    }
    /**
     * 获取消息监听器
     * @param msgtype
     * @return
     */
    public static MessageLisener getMessageLisener(MessageFactory.MsgTypeEnum msgtype){
          return   REGISTER.get(msgtype);
    }
}
