package server;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import msg.Message;
import msg.MessageFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class NettyServerFrame  extends JFrame {

    NettyServerFrame(){
        init();
        this.setTitle("表格布局");
        this.setResizable(true);
        this.setSize(1280, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    void init(){
        this.setLayout(new GridLayout(4,3,10,5)); //默认为1行，n列；2行3列，水平间距10，垂直间距5
        ArrayList<ServerCommnad> serverCommnads =this.getServerCommands();
        for(int i=0;i<serverCommnads.size();i++){
            ServerCommnad commnad  =  serverCommnads.get(i);
            MessageButton  messageButton = new MessageButton(commnad.msgType,commnad.commandName);
            this.add(messageButton);
            messageButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    System.out.println(messageButton.label);
                    sendMessage(messageButton.msgType);
                }
            });
        }
    }

    private   ArrayList<ServerCommnad> getServerCommands(){
        ArrayList<ServerCommnad>  serverCommnadList = new ArrayList<ServerCommnad>();
        ServerCommnad commnad =new ServerCommnad("1","【1】-启动签核信息");
        serverCommnadList.add(commnad);
         commnad =new ServerCommnad("2","【2】-发送知情同意书图片");
        serverCommnadList.add(commnad);
        commnad =new ServerCommnad("10","【10】开始签名");
        serverCommnadList.add(commnad);
        commnad =new ServerCommnad("11","【11】-取消签名");
        serverCommnadList.add(commnad);
        commnad =new ServerCommnad("20","【20】开始录入指纹");
        serverCommnadList.add(commnad);
        commnad=new ServerCommnad("21","【21】-取消录入指纹");
        serverCommnadList.add(commnad);
        commnad =new ServerCommnad("31","【31】开始拍照");
        serverCommnadList.add(commnad);
        commnad =new ServerCommnad("32","【32】-拍照");
        serverCommnadList.add(commnad);
        commnad =new ServerCommnad("33","【33】-取消拍照");
        serverCommnadList.add(commnad);
        commnad =new ServerCommnad("40","【40】签核完成");
        serverCommnadList.add(commnad);
        commnad =new ServerCommnad("41","【41】-取消签核");
        serverCommnadList.add(commnad);
        commnad =new ServerCommnad("50","【50】强制退出");
        serverCommnadList.add(commnad);
        return  serverCommnadList;
    }


    /**
     * 发送消息
     * @param msgType
     */
    private void sendMessage(String msgType) {
                List<Message> messages = new ArrayList<>();
                Message message;
                switch (msgType){
                    case "1":
                        System.out.println("开始签核 呼出签核页面");
                        message = MessageFactory.ServerCommand.getServerCommandServerSignatureStartmessage(new Integer[]{1,2,3});
                        messages.add(message);
                        break;
                    //发送图片
                    case "2":
                        System.out.println("向客户端发送知情同意书");
                        message =  MessageFactory.ServerCommand.getServerCommandServerPushImagemessage("HealthyQuestion.jpg");
                        messages.add(message);
                        message =  MessageFactory.ServerCommand.getServerCommandServerPushImagemessage("0101.jpg");
                        messages.add(message);
                        message =  MessageFactory.ServerCommand.getServerCommandServerPushImagemessage("0201.jpg");
                        messages.add(message);
                        break;
                    case "10":
                        System.out.println("向客户端发送开始签名指令，客户端应弹出签字框");
                        message= MessageFactory.ServerCommand.getServerCommandSignMessage("0");
                        messages.add(message);
                        break;
                    case "11":
                        System.out.println("向客户端发送取消始签名指令，客户端应关闭签字框");
                        message= MessageFactory.ServerCommand.getServerCommandSignMessage("1");
                        messages.add(message);
                        break;
                    case "20":
                        System.out.println("向客户端发送录入指纹指令，客户端应弹出指纹录入框");
                        message= MessageFactory.ServerCommand.getServerCommandFignerPrintMessag("0");
                        messages.add(message);
                        break;
                    case "21":
                        System.out.println("向客户端发送取消录入指纹指令，客户端应关闭录入指纹框");
                        message= MessageFactory.ServerCommand.getServerCommandFignerPrintMessag("1");
                        messages.add(message);
                        break;
                    case "31":
                        System.out.println("向客户端发送拍照指令，客户端应弹出指拍照框");
                        message= MessageFactory.ServerCommand.getServerCommandFacePictureMessage("0");
                        messages.add(message);
                        break;
                    case "32":
                        System.out.println("向客户端发送取消始拍照指令，客户端应抓取摄像头快照，并传送至服务端");
                        message= MessageFactory.ServerCommand.getServerCommandFacePictureMessage("1");
                        messages.add(message);
                        break;
                    case "33":
                        System.out.println("向客户端发送取消始拍照指令，客户端应关闭摄拍照框");
                        message= MessageFactory.ServerCommand.getServerCommandFacePictureMessage("2");
                        messages.add(message);
                        break;
                    case "40":
                        System.out.println("向客户端发送签核完成指令，签核完成，弹出感谢页面并回到主页面");
                        message= MessageFactory.ServerCommand.getServerCompleteMessage(1);
                        messages.add(message);
                        break;
                    case "41":
                        System.out.println("向客户端发送取消始拍照指令，签核取消 ,弹出提示页面并回到主页面");
                        message= MessageFactory.ServerCommand.getServerCompleteMessage(0);
                        messages.add(message);
                        break;
                    case "50":
                        System.out.println("向客户端发送取消始拍照指令,强制退出");
                        message= MessageFactory.ServerCommand.getServerServerSignatureCancelMessage();
                        messages.add(message);
                        break;
                    default:
                        System.out.println("指令错误【"+msgType+"】");
                        return;

                }

                ChannelHandlerContext channel  =  NettyChannelContainer.getSocketChannel();
                if(channel==null){
                    System.out.println("没有客户端连接");
                  return;
                }
//        String msg = JSON.toJSON(messages.get(0)).toString();
//        channel.channel().writeAndFlush(msg);
                for(Message m : messages) {
                    String msg = JSON.toJSON(m).toString();
                    channel.channel().writeAndFlush(msg);
                    System.out.println("发送消息成功");
                    if(m.getMsgType().equals(MessageFactory.MsgTypeEnum.SERVER_PUSH_IMAGE.toString()))
                        System.out.println(msg);
                }
    }

    class MessageButton  extends   JButton{
        private  String msgType;
        private  String label;
        public MessageButton( String msgType,String text) {
            super(text);
            this.label = text;
            this.msgType = msgType;
        }
    }

    class ServerCommnad{
        private  String  msgType;
        private   String commandName;

        public ServerCommnad(String msgType, String commandName) {
            this.msgType = msgType;
            this.commandName = commandName;
        }

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public String getCommandName() {
            return commandName;
        }

        public void setCommandName(String commandName) {
            this.commandName = commandName;
        }
    }

    public static void main(String args[]) throws InterruptedException {
        new NettyServer(9999);
        new NettyServerFrame();
    }
}
