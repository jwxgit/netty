package msg;

import java.io.Serializable;

public class Message implements Serializable {
    /**
     *
     */
    private  String msgClientId;
    /*
     * 消息id
     */
    private String msgId;

    /*
     * 消息类型
     */
    private String msgType;

    /*
     * 消息md5
     */
    private String msgMd5;

    /*
     * 消息发送时间
     */
    private String msgTimestamp;

    /*
     * 消息体
     */
    private Object data;

    public String getMsgClientId() {
        return msgClientId;
    }

    public void setMsgClientId(String msgClientId) {
        this.msgClientId = msgClientId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgMd5() {
        return msgMd5;
    }

    public void setMsgMd5(String msgMd5) {
        this.msgMd5 = msgMd5;
    }

    public String getMsgTimestamp() {
        return msgTimestamp;
    }

    public void setMsgTimestamp(String msgTimestamp) {
        this.msgTimestamp = msgTimestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Message{" +
                "msgClientId='" + msgClientId + '\'' +
                ", msgId='" + msgId + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgMd5='" + msgMd5 + '\'' +
                ", msgTimestamp='" + msgTimestamp + '\'' +
                ", data=" + data +
                '}';
    }
}
