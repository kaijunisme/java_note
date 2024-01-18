package com.example.netty.sample.factory;

import com.example.netty.sample.dto.CancelReq;
import java.nio.charset.StandardCharsets;

public class CancelReqMessageFactory implements MessageFactory<CancelReq> {

  /**
   *
   * @param content
   * @return
   */
  @Override
  public CancelReq convertToMessageReq(byte[] content) {
    CancelReq cancelReq = new CancelReq();
    cancelReq.setOrderNo(new String(content, StandardCharsets.UTF_8));
    return cancelReq;
  }
}
