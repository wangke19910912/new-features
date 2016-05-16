package newFeture.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangke on 16/5/16.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        //ctx 代表了客户端和服务端的一个连接
        ByteBuf m = (ByteBuf) msg; // (1)
        try {
            byte[] bytes = new byte[m.readableBytes()];
            m.readBytes(bytes);
            System.out.println("客户端收到消息:"+ new String(bytes,"utf-8"));
            ctx.close();
        } finally {
            m.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
