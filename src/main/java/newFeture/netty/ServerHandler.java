package newFeture.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangke on 16/5/15.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException { // (2)
        //在有消息可读时,回调这个函数
        ByteBuf buf = ((ByteBuf) msg); // (3)
        try {
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            System.out.println("服务端收到消息:"+ new String(bytes,"utf-8"));
            ctx.writeAndFlush(msg);

        } finally {
            buf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        //异常被捕捉到时,回调这个函数
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws UnsupportedEncodingException {
        //链接建立后,回调这个函数
        final ByteBuf msg= ctx.alloc().buffer("hello world".getBytes("utf-8").length); // (2)
        msg.writeBytes("hello world".getBytes("utf-8"));
        final ChannelFuture f = ctx.writeAndFlush(msg); // (3)
        //配置监听器,在成功之后进行回调,因为所有操作都为异步
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                assert f == future;
//                ctx.close();
//            }
//        });

    }
}
