package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { //channel失效，从Map中移除
        String key =  ctx.channel().remoteAddress().toString();
        NettyChannelContainer.setSocketChannel(null);
        System.out.println("移除连接"+key);
    }

    @Override
    public  void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("出现异常！");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("接收到消息：" + msg);

       /* Message message = JSON.parseObject(msg + "", Message.class);
        switch ( message.getMsgType()){
            case  "CLIENT_UPLOAD_IMG":
                Object  data = message.getData();
                SignImage signImage =   JSON.parseObject(data + "", SignImage.class);
                String fileBase64 =signImage.getImgContent();
                File f = ImageUtils.base64ToFile(fileBase64,"d:",UUID.randomUUID().toString()+".jpg") ;
                break;
            default:
        }*/
        System.out.println(msg.toString());
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bf = (ByteBuf) msg;
        byte[] byteArray = new byte[bf.capacity()];
        bf.readBytes(byteArray);
        String result = new String(byteArray);
        System.out.println(result);
      /*  Message message = JSON.parseObject(result + "", Message.class);
        Object  data = message.getData();
        System.out.println(data);*/
    /*    SignImage signImage =   JSON.parseObject(data + "", SignImage.class);
        String fileBase64 =signImage.getImgContent();
        File f = ImageUtils.base64ToFile(fileBase64,"d:",UUID.randomUUID().toString()+".jpg") ;*/
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接成功"+ctx.channel().remoteAddress());
        NettyChannelContainer.setSocketChannel(ctx);
       /* Message message = MessageFactory.ServerCommand.getServerCommandServerSignatureStartmessage(new Integer[]{1,2,3});
        System.out.println(message.toString());
        String msg = JSON.toJSON(message).toString();
        NettyChannelContainer.getSocketChannel().channel().writeAndFlush(msg);*/
    }
}