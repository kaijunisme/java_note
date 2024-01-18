package com.example.netty.sample.factory;

import com.example.netty.sample.dto.MessageReq;

public interface MessageFactory<T extends MessageReq> {

  T convertToMessageReq(byte[] content);

}
