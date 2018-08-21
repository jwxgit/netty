package client;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import msg.Message;
import msg.MessageFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NettyClientHandler extends SimpleChannelInboundHandler {

    public static ChannelHandlerContext context = null;
    //利用写空闲发送心跳检测消息
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //在此发送心跳消息
    }

    private void  handlerMessage(Message message){
       String mstType = message.getMsgType();
       for( MessageFactory.MsgTypeEnum e: MessageFactory.MsgTypeEnum.values()){
           if(e.getMsgType().equals(mstType)){
              MessageLisener messageLisener  = MessageLisenerRegister.getMessageLisener(e);
              if(messageLisener==null){
                  System.out.println("消息类型【"+mstType+"】"+"未注册监听器");
                  return;
              }
              messageLisener.onMessage(message);
           }
       }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object byteBuf) throws Exception {
        Message   message = getMessageFromBuf(byteBuf);
        if(message!=null){
            System.out.println("【channelRead0】-----收到消息：");
            System.out.println(message.toString());
            handlerMessage(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object byteBuf) throws Exception {
        Message   message = getMessageFromBuf(byteBuf);
        if(message!=null){
            System.out.println("【channelRead】收到消息"+message.toString());
            System.out.println(message.toString());
            handlerMessage(message);
        }
    }

    private Message getMessageFromBuf(Object byteBuf) {
        String message="";
        try {
            ByteBuf bf = (ByteBuf) byteBuf;
            byte[] byteArray = new byte[bf.capacity()];
            bf.readBytes(byteArray);
            message = new String(byteArray);
            return JSON.parseObject(message, Message.class);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(NettyClientHandler.class.getName()).log(Level.WARNING,"消息转换失败"+message);
        }
        return null;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      /*  super.channelActive(ctx);*/


    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        //重新连接服务器
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
