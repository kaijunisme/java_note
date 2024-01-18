package com.example.netty.sample.factory;

import com.example.netty.sample.dto.ReplaceReq;
import com.example.netty.sample.util.MathUtil;

public class ReplaceReqMessageFactory implements MessageFactory<ReplaceReq> {

  @Override
  public ReplaceReq convertToMessageReq(byte[] content) {
    ReplaceReq replaceReq = new ReplaceReq();
    replaceReq.setOrderNo(new String(content, 0, 10));
    replaceReq.setPrice(MathUtil.convertToLong(new String(content, 10, 10)));
    replaceReq.setQuantity(MathUtil.convertToInt(new String(content, 20, 6)));
    return replaceReq;
  }
}
