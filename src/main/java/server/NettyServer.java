package server;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import msg.Message;
import msg.MessageFactory;
import msgcoder.MessageByteDecoder;
import msgcoder.MessageByteEncoder;
import msgcoder.MessageDecoder;
import msgcoder.MessageEncoder;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NettyServer {
    private int port;
    public  SocketChannel socketChannel;
    public  NettyServer(int port) throws InterruptedException {
        this.port = port;
        bind();
    }

    private void bind() throws InterruptedException {
        EventLoopGroup boss=new NioEventLoopGroup();
        EventLoopGroup worker=new NioEventLoopGroup();
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(boss,worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        //通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        //保持长连接状态
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline p = socketChannel.pipeline();
                //字符串类解析
                //这里只能添加字符串的编码和解码器，
                //网上有很多例子是这样写的：
                //这种写法只能所有客户端都用netty写，否则其他框架实现的客户端无法发送消息到服务端,因为他是转换的netty自己的Object
                //p.addLast(new ObjectEncoder());
                //p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                /*  p.addLast(new StringEncoder(Charset.forName("UTF-8")));
                p.addLast(new StringDecoder(Charset.forName("UTF-8")));*/
                p.addLast(new MessageByteEncoder());
                p.addLast(new MessageByteDecoder());
                p.addLast(new NettyServerHandler());
                String key =  socketChannel.remoteAddress().toString();
                NettyChannelContainer.add(key,socketChannel);
                System.out.println("PAD_CHANNEL:"+socketChannel.remoteAddress() +"连接上");
            }
        });
        ChannelFuture f= bootstrap.bind(port).sync();
        if(f.isSuccess()){
            System.out.println("server start---------------");
         /*   sendCommands();*/
        }
    }

    /*private void sendCommands() {
        while (true) {
            try {
                ChannelHandlerContext ctx = NettyChannelContainer.getSocketChannel();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String  s = null;
                if(ctx==null){
                    System.out.println("无客户端连接,请输入任意字符继续");
                    br.readLine();
                    continue;
                }
                System.out.println("----------------------------------------------------");
                System.out.println("请输入想发送的测试信息");
                System.out.println("开始签核 【1】-启动签核信息|【2】-发送知情同意书图片");
                System.out.println("签名指令 【10】开始签名|【11】-取消签名");
                System.out.println("指纹指令 【20】开始录入指纹|【21】-取消录入指纹");
                System.out.println("拍照指令 【31】开始拍照|【32】-拍照|【33】-取消拍照");
                System.out.println("拍照指令 【40】签核完成|【41】-取消签核");
                System.out.println("拍照指令 【50】强制退出");
                System.out.println("  【B】退出");
                System.out.println("----------------------------------------------------");
                s = null;
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                Message message;
                switch (s){
                    case "1":
                        System.out.println("开始签核 呼出签核页面");
                        message = MessageFactory.ServerCommand.getServerCommandServerSignatureStartmessage(new Integer[]{1,2,3});
                        break;
                    //发送图片
                    case "2":
                        System.out.println("向客户端发送知情同意书");
                        message =  MessageFactory.ServerCommand.getServerCommandServerPushImagemessage("0101.jpg");
                        break;
                    case "10":
                        System.out.println("向客户端发送开始签名指令，客户端应弹出签字框");
                        message= MessageFactory.ServerCommand.getServerCommandSignMessage("0");
                        break;
                    case "11":
                        System.out.println("向客户端发送取消始签名指令，客户端应关闭签字框");
                        message= MessageFactory.ServerCommand.getServerCommandSignMessage("1");
                        break;
                    case "20":
                        System.out.println("向客户端发送录入指纹指令，客户端应弹出指纹录入框");
                        message= MessageFactory.ServerCommand.getServerCommandFignerPrintMessag("0");
                        break;
                    case "21":
                        System.out.println("向客户端发送取消录入指纹指令，客户端应关闭录入指纹框");
                        message= MessageFactory.ServerCommand.ge    tServerCommandFignerPrintMessag("1");
                        break;
                    case "31":
                        System.out.println("向客户端发送拍照指令，客户端应弹出指拍照框");
                        message= MessageFactory.ServerCommand.getServerCommandFacePictureMessage("0");
                        break;
                    case "32":
                        System.out.println("向客户端发送取消始拍照指令，客户端应抓取摄像头快照，并传送至服务端");
                        message= MessageFactory.ServerCommand.getServerCommandFacePictureMessage("1");
                        break;
                    case "33":
                        System.out.println("向客户端发送取消始拍照指令，客户端应关闭摄拍照框");
                        message= MessageFactory.ServerCommand.getServerCommandFacePictureMessage("2");
                        break;
                    case "40":
                        System.out.println("向客户端发送签核完成指令，签核完成，弹出感谢页面并回到主页面");
                        message= MessageFactory.ServerCommand.getServerCompleteMessage(1);
                        break;
                    case "41":
                        System.out.println("向客户端发送取消始拍照指令，签核取消 ,弹出提示页面并回到主页面");
                        message= MessageFactory.ServerCommand.getServerCompleteMessage(0);
                        break;
                    case "50":
                        System.out.println("向客户端发送取消始拍照指令,强制退出");
                        message= MessageFactory.ServerCommand.getServerServerSignatureCancelMessage();
                        break;
                    default:
                        System.out.println("指令错误【"+s+"】");
                        continue;

                }
                String msg = JSON.toJSON(message).toString();
                System.out.println(msg);
                ctx.channel().writeAndFlush(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    public static void main(String []args) throws InterruptedException {
        if(args.length == 0){
            new NettyServer(10000);
        }else{
            new NettyServer(Integer.parseInt(args[0]));
        }


    }


}
