package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyChannelContainer {
    private static ChannelHandlerContext socketChannel=null;
    private static Map<String , SocketChannel> map = new ConcurrentHashMap<>();

    public static void add(String clientId,SocketChannel channel){
        map.put(clientId, channel);
    }

    public static Channel get(String clientId){
        return map.get(clientId);
    }

    public static void remove(String channel){
           map.remove(channel);
    }

    public static  ChannelHandlerContext getSocketChannel() {
        return socketChannel;
    }

    public static void setSocketChannel(ChannelHandlerContext socketChannel) {
        NettyChannelContainer.socketChannel = socketChannel;
    }
}
