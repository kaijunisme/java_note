package com.example.netty.sample.handler;

import com.example.netty.sample.dto.OrderReq;
import com.example.netty.sample.event.LogEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class OrderReqHandler extends SimpleChannelInboundHandler<OrderReq> {

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, OrderReq orderReq) {
    LogEvent.addLog(orderReq::toString);
  }
}
