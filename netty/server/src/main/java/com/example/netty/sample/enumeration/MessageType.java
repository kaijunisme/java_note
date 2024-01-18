package com.example.netty.sample.enumeration;

import com.example.netty.sample.factory.CancelReqMessageFactory;
import com.example.netty.sample.factory.MessageFactory;
import com.example.netty.sample.factory.OrderReqMessageFactory;
import com.example.netty.sample.factory.ReplaceReqMessageFactory;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum MessageType {

  ORDER_REQ(new byte[] {'O', 'R'}, new OrderReqMessageFactory()),
  REPLACE_REQ(new byte[] {'R', 'R'}, new ReplaceReqMessageFactory()),
  CANCEL_REQ(new byte[] {'C', 'R'}, new CancelReqMessageFactory()),
  ;

  private final byte[] functionCode;
  private final MessageFactory messageFactory;

  MessageType(byte[] functionCode, MessageFactory messageFactory) {
    this.functionCode = functionCode;
    this.messageFactory = messageFactory;
  }

  public static Optional<MessageType> createByByte(byte[] frame) {
    if (frame.length > 10) {
      for (MessageType messageType: values()) {
        byte[] functionCode = messageType.getFunctionCode();
        if (functionCode[0] == frame[0] && functionCode[1] == frame[1]) {
          return Optional.of(messageType);
        }
      }
    }
    return Optional.empty();
  }
}
