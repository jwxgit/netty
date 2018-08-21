package msgcoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import javax.xml.bind.DatatypeConverter;
import java.util.List;

public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    private byte[]  body;     //消息体
    private byte[]  header;   //消息头
    private int     length=0;  //消息体的长度
    private int     readedlength=0;  //已经读取的消息长度
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        //获取头的长度,
        if(byteBuf.readableBytes()<1024){
            System.out.println("可读字符串小于1024");
        }
        if(header==null){
            header =  new byte[4];
            //如果消息头不够长， 直接丢弃，重置ByteBuf
            if(byteBuf.readableBytes()<4){
                byteBuf.resetReaderIndex();
                return;
            }
            //读取消息头， 把消息头转换成int数字，确认body的长度
            byteBuf.readBytes(header,0,4);
            length = Integer.parseInt(DatatypeConverter.printHexBinary(header), 16);
            //构造消息长度
            body=new byte[length];
            int readableLength=  byteBuf.readableBytes();
            //本次读取长度
            byte[]  bytes =new  byte[readableLength];
            byteBuf.readBytes(bytes);
            System.arraycopy(bytes, 0, body, readedlength, bytes.length);
            readedlength+=bytes.length;

        }else {
            int readableLength=  byteBuf.readableBytes();
            //本次读取长度
            byte[]  bytes =new  byte[readableLength];
            byteBuf.readBytes(bytes);
            System.arraycopy(bytes, 0, body, readedlength, bytes.length);
            readedlength+=bytes.length;
        }
        if(readedlength==length){
            out.add(Unpooled.copiedBuffer(body));
            readedlength=0;
            length=0;
            header=null;
            body=null;
        }

        if(readedlength>length){
            throw  new  RuntimeException("读取数据包逻辑错误，读取字符串长度大于消息头包含的字符长度");
        }
        //如果消息header的长度与读取的消息长度一致， 则上传消息

    }
}
