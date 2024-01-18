package com.example.netty.echo;

import static java.lang.Thread.sleep;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class EchoClientApplication {

  private static final String HOST = "127.0.0.1";
  private static final int PORT = 9001;

  public static void main(String[] args) {
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      Bootstrap bootstrap = new Bootstrap()
          .group(workerGroup)
          .channel(NioSocketChannel.class)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              ChannelPipeline channelPipeline = socketChannel.pipeline();
              channelPipeline.addLast(new EchoChannelHandler());
            }
          });

      ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
      future.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      workerGroup.shutdownGracefully();
    }
  }
}

class EchoChannelHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("[" + LocalDateTime.now() + "] Channel Active: {}" + ctx);
    ByteBuf message = Unpooled.copiedBuffer("Hello, Netty.", StandardCharsets.UTF_8);
    ctx.writeAndFlush(message);
  }

  @Override
  public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
    ByteBuf message = (ByteBuf) msg;
    System.out.println("[" + LocalDateTime.now() + "] Message received: " + message.toString(StandardCharsets.UTF_8));
    channelHandlerContext.writeAndFlush(msg);
    sleep(1000);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}