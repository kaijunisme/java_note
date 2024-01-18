package com.example.netty.sample.handler;

import com.example.netty.sample.enumeration.MessageType;
import com.example.netty.sample.event.LogEvent;
import com.example.netty.sample.factory.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import java.util.Optional;

public class MessageDecodeHandler extends ByteToMessageDecoder {

  @Override
  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf,
      List<Object> list) {
    // 建立ByteBuf 的副本
    ByteBuf frame = byteBuf.retainedDuplicate();

    // 讀取ByteBuf 的內容
    byte[] array = new byte[frame.readableBytes()];
    frame.readBytes(array);

    LogEvent.addLog(() -> "Read: " + new String(array));

    // 解析訊息類型
    Optional<MessageType> optionalMessageType = MessageType.createByByte(array);
    optionalMessageType.ifPresent(messageType -> {
      MessageFactory messageFactory = messageType.getMessageFactory();
      byte[] content = new byte[array.length - 2];
      System.arraycopy(array, 2, content, 0, content.length);
      list.add(messageFactory.convertToMessageReq(content));
    });

    // 跳過已讀取的位元組，以便從下一個訊息幀開始處理
    byteBuf.skipBytes(byteBuf.readableBytes());
  }
}
