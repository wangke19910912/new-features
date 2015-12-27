package newFeture.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/*
 * 服务端在创建channel后，将selector(观察者)绑定到channel(被观察对象)，典型的观察者模式的应用，当
 * channel接受到事件发生时，channel通知selector，selector根据事件内容动作，反过来从selector中的key
 * 中可以获得封装后具体的channel，这个channel就是初始化新建的channel，从这个channel中就可以拿到相应
 * 的具体内容,所以说channel是和监听的端口绑定的，多个channel都可以注册到selector上，通过一个selector
 * 来进行转发，selector是进程隔离的。所以说init和listen并没有必然关系，可以初始化多个channel(端口
 * 不同) 绑定到同一个selector上进行统一处理，因为selector是单例的。
 */
public class NioService {

    // 通道管理器，需要跟通道绑定
    private Selector selector;

    // 测试使用
    ServerSocketChannel serverChannelInit;

    public void listen() throws IOException, InterruptedException {

        while (true) {
            // 在没有消息到来之前会一直阻塞,有事件发生时nKeys>0
            int nKeys = selector.select();

            // 服务端接受的消息类型acceptable，writable，readable
            if (nKeys > 0) {
                System.out.println("服务端接收到消息");
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    // 去除channel中已经使用过的key
                    keys.remove(key);

                    if (key.isAcceptable()) {
                        System.out.println("服务端接收到ACCEPT消息");
                        // 这里获得的channel和init注册的channel相同
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        // if (serverChannelInit == ssc)
                        // System.out.println("新建的channel和初始化channel的值相同");
                        // else
                        // System.out.println("新建的channel和初始化channel的值bu相同");
                        SocketChannel sc = ssc.accept();

                        if (sc == null) {
                            continue;
                        }
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                    } else if (key.isWritable()) {

                        System.out.println("服务端接收WRITABLE到消息");

                    } else if (key.isReadable()) {
                        System.out.println("服务端接收到READABLE消息");
                        read(key);
                    }

                }
            }
        }
    }
    public void read(SelectionKey key) throws IOException, InterruptedException {
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(50);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("服务端收到信息：" + msg);

        // 请求等待三秒，模仿数据请求操作
        // wait(3000);

        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(outBuffer);// 将消息回送给客户端
    }

    /*
     * 前期准备工作： 1.创建一个serverSocketChannel
     * 2.创建一个selector，并和这个channel相关联，select用于管理客户端上报事件
     * 3.注册事件，将感兴趣的时间注册到select上，这样当事件上传到服务器上时， selector.select
     * 会一直监听事件的到来，并一直阻塞，客户端在时间发起后可以 立即返回，时间送到消息队列中进行处理，所以nio是基于消息传输的一种通信方式
     */
    public void initServer(int port) throws IOException {
        // 获得一个serverSocket channel
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 设置通道为非阻塞
        serverChannel.configureBlocking(false);
        // 将该通道对应的ServerSocket绑定到port端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        // 获得一个通道管理器,单例模式，服务器仅仅维护一个selector，管理众多channel
        selector = Selector.open();
        // 将通道管理器和该通道相绑定，并为该通道注册OP_ACCEPT事件，注册该事件后
        // 当时间到达时，selector.select()函数会返回,如果事件没有到达selector.select()
        // 会一直阻塞，直到有新的线程去进行获取
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        serverChannelInit = serverChannel;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("服务端启动...");
        NioService server = new NioService();
        server.initServer(8000);
        server.listen();

    }
}
