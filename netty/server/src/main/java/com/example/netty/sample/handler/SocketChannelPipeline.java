package com.example.netty.sample.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

public class SocketChannelPipeline extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel socketChannel) {
    ChannelPipeline channelPipeline = socketChannel.pipeline();

    // 基於分隔符的幀解碼器，用於將以換行符（\n）為分隔符的字節流解碼為訊息幀。
    channelPipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

    // 訊息解碼器，用於將訊息幀解碼為應用程式可以理解的訊息物件。
    channelPipeline.addLast(new MessageDecodeHandler());

    // 訊息編碼器，用於將應用程式訊息物件編碼為字節流。
    channelPipeline.addLast(new MessageEncodeHandler());

    // 其他自定義處理器
    channelPipeline.addLast(new OrderReqHandler());
    channelPipeline.addLast(new ReplaceReqHandler());
    channelPipeline.addLast(new CancelReqHandler());
  }
}
