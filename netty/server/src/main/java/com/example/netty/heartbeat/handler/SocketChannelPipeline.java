package com.example.netty.heartbeat.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;

public class SocketChannelPipeline extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    ChannelPipeline channelPipeline = socketChannel.pipeline();
    channelPipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
    channelPipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
    channelPipeline.addLast(new MessageEncodeHandler());
    channelPipeline.addLast(new HeartbeatHandler());
  }
}
