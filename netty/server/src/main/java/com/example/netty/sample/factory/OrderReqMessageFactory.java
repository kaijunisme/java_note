package com.example.netty.sample.factory;

import com.example.netty.sample.dto.OrderReq;
import com.example.netty.sample.util.MathUtil;

public class OrderReqMessageFactory implements MessageFactory<OrderReq> {

  @Override
  public OrderReq convertToMessageReq(byte[] content) {
    OrderReq orderReq = new OrderReq();
    orderReq.setSymbol(new String(content, 0, 10));
    orderReq.setPrice(MathUtil.convertToLong(new String(content, 10, 10)));
    orderReq.setQuantity(MathUtil.convertToInt(new String(content, 20, 6)));
    return orderReq;
  }
}
