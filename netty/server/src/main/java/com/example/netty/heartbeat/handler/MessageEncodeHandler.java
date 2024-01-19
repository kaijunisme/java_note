package com.example.netty.heartbeat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.StandardCharsets;

public class MessageEncodeHandler extends MessageToByteEncoder {

  @Override
  protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) {
    byteBuf.writeBytes((o.toString() + "\r\n").getBytes(StandardCharsets.UTF_8));
  }
}
