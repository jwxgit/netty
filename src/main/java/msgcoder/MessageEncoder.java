package msgcoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import utils.FormatUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;

public class MessageEncoder extends MessageToMessageEncoder<CharSequence> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CharSequence msg, List<Object> out) throws Exception {
        if(msg == null)
            return;
        ByteBuf tb = null;
        if(msg instanceof ByteBuf) {
            tb = (ByteBuf) msg;
        }else if(msg instanceof ByteBuffer) {
            tb = Unpooled.copiedBuffer((ByteBuffer)msg);
        }else {
            String ostr = msg.toString();
            tb = Unpooled.copiedBuffer(ostr, Charset.forName("UTF-8"));
        }
        byte[] pkg = new byte[4 + tb.readableBytes()];//数据包
        byte[] header = FormatUtils.intToBytes(tb.readableBytes());//报文包头
        byte[] body = new byte[tb.readableBytes()];//包体
        tb.readBytes(body);
        System.arraycopy(header, 0, pkg, 0, header.length);
        System.arraycopy(body, 0, pkg, header.length, body.length);
        out.add(Unpooled.copiedBuffer(pkg));
    }

    public static void main(String[] args) {
        System.out.println(FormatUtils.intToByteArray(244));
    }
}
