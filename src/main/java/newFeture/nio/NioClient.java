package newFeture.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

/*
 * 客户端启动顺序,客户端首发送消息,然后在通道上注册OP_READ消息,这事,如果服务端没有响应,则客户端会阻塞在Select#select上,
 * 服务端启动后会将客户端链接的channel注册到Selector上,之后会注册OP_READ消息,等接受到客户端发送的数据读取后,会注册OP_WRITE消息,
 * 向客户端写入数据,之后再次注册OP_READ阻塞到Selector上.这就完成了一个简单的客户端与服务端一应一答的过程.
 * 
 */
public class NioClient {

    // 通道管理器
    private Selector selector;

    // 客户端channel
    private SocketChannel socketChannel;

    public void initClient(String ip, int port) throws IOException {
        // 获得一个Socket通道
        socketChannel = SocketChannel.open();
        // 设置通道为非阻塞
        socketChannel.configureBlocking(false);
        // 获得一个通道管理器
        this.selector = Selector.open();
        // 客户端连接服务器,其实方法执行并没有实现连接，需要在listen（）方法中调
        // 用channel.finishConnect();才能完成连接
        socketChannel.connect(new InetSocketAddress(ip, port));
        // 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void listen() throws IOException {
        // 轮询访问selector
        while (true) {
            // 在没有消息到来之前会一直阻塞,有事件发生时nKeys>0
            int nKeys = selector.select();
            if (nKeys > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    // 客户端有三种消息类型：connect,writable,readble
                    if (key.isConnectable()) {
                        System.out.println("客户端接收到CONNECT消息");
                        SocketChannel channel = (SocketChannel) key.channel();
                        if (socketChannel == channel)
                            System.out.println("新建的channel和初始化channel的值相同");
                        else
                            System.out.println("新建的channel和初始化channel的值BU相同");
                        // 如果正在连接，则完成连接
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();
                        }
                        // 设置成非阻塞
                        channel.configureBlocking(false);

                        // 在这里可以给服务端发送信息,异步请求操作,发送完之后直接返回,然后注册OP_READ消息
                        channel.write(ByteBuffer.wrap(new String("hello world ").getBytes()));
                        // 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
                        channel.register(this.selector, SelectionKey.OP_READ);
                        // 获得了可读的事件
                        System.out.println("客户端发送完毕");

                    } else if (key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.write(ByteBuffer.wrap(new String("hello world2 ").getBytes()));
                        channel.register(this.selector, SelectionKey.OP_READ);
                        // 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。


                    } else if (key.isReadable()) {
                        System.out.println("客户端接收到READABLE消息");
                        read(key);
                        //write again
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.write(ByteBuffer.wrap(new String("hello world2 ").getBytes()));
                        // 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
                        channel.register(this.selector, SelectionKey.OP_WRITE);
                    }

                }
            }
        }
    }

    public void read(SelectionKey key) throws IOException {
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(50);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("客户端收到信息：" + msg);
    }

    public static void main(String[] args) throws IOException {
        NioClient client = new NioClient();
        client.initClient("localhost", 8000);
        client.listen();
    }
}
