package com.example.netty.sample.handler;

import com.example.netty.sample.dto.ReplaceReq;
import com.example.netty.sample.event.LogEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ReplaceReqHandler extends SimpleChannelInboundHandler<ReplaceReq> {

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, ReplaceReq replaceReq) {
    LogEvent.addLog(replaceReq::toString);
  }
}
