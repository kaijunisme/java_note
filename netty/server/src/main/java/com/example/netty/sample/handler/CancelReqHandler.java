package com.example.netty.sample.handler;

import com.example.netty.sample.dto.CancelReq;
import com.example.netty.sample.event.LogEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CancelReqHandler extends SimpleChannelInboundHandler<CancelReq> {

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, CancelReq cancelReq) {
    LogEvent.addLog(cancelReq::toString);
  }
}
