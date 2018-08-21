package msgcoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.commons.lang3.StringUtils;
import utils.FileUtils;
import utils.FormatUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;

public class MessageByteEncoder extends MessageToByteEncoder<CharSequence> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CharSequence msg, ByteBuf byteBuf) throws Exception {
        if(StringUtils.isEmpty(msg))
            return;
        // 报文长度
        byte[] bytes = msg.toString().getBytes("UTF-8");
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

}
