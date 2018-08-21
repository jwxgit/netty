package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import msg.Message;
import msg.MessageFactory;
import msgcoder.MessageByteEncoder;
import msgcoder.MessageDecoder;
import msgcoder.MessageEncoder;

public class NettyClient {
        private int port;
        private String host;
        public SocketChannel socketChannel;
        private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);
        public NettyClient(int port, String host) {
            this.port = port;
            this.host = host;
            start();
        }
        private void start(){
            ChannelFuture future = null;
            try {
                EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
                Bootstrap bootstrap=new Bootstrap();
                bootstrap.channel(NioSocketChannel.class);
                bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
                bootstrap.group(eventLoopGroup);
                bootstrap.remoteAddress(host,port);
                bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(new IdleStateHandler(20,10,0));
                        p.addLast(new MessageDecoder());
                        p.addLast(new MessageByteEncoder());
                        p.addLast(new NettyClientHandler());
                    }
                });
                future =bootstrap.connect(host,port).sync();
                if (future.isSuccess()) {
                    socketChannel = (SocketChannel)future.channel();
                    System.out.println("connect server  成功---------");
                }else{
                    System.out.println("连接失败！");
                    System.out.println("准备重连！");
                    start();
                }
            } catch (Exception e) {

            }finally{
//    		if(null != future){
//    			if(null != future.channel() && future.channel().isOpen()){
//    				future.channel().close();
//    			}
//    		}
//    		System.out.println("准备重连！");
//    		start();
            }
        }


    public static void main(String[] args) throws InterruptedException {

           NettyClient bootstrap = new NettyClient(10000, "192.168.199.69");
           MessageLisenerRegister.registMessageLisener(MessageFactory.MsgTypeEnum.SERVER_SIGNATURE_START, new MessageLisener() {
               @Override
               public void onMessage(Message message) {
                   System.out.println("收到服务端消息");
               }
           });

    }



}






