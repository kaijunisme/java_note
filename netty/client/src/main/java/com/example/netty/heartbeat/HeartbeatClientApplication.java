package com.example.netty.heartbeat;

import com.example.netty.heartbeat.handler.SocketChannelPipeline;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HeartbeatClientApplication {

  private static final String HOST = "127.0.0.1";
  private static final int PORT = 9001;

  public static void main(String[] args) {
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      Bootstrap bootstrap = new Bootstrap()
          .group(workerGroup)
          .channel(NioSocketChannel.class)
          .handler(new SocketChannelPipeline())
          .option(ChannelOption.TCP_NODELAY, true);

      ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
      future.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      workerGroup.shutdownGracefully();
    }
  }
}
