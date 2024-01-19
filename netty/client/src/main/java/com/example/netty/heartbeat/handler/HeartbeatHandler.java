package com.example.netty.heartbeat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.nio.charset.StandardCharsets;

public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

  private int writerIdleCounter = 1;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof ByteBuf) {
      ByteBuf frame = (ByteBuf) msg;
      System.out.println(frame.toString(StandardCharsets.UTF_8));
    }
    ctx.fireChannelRead(msg);
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
      IdleState idleState = idleStateEvent.state();

      int idleLimit = 5;
      switch (idleState) {
        case READER_IDLE:
          System.out.println("Reader idle.");
          break;
        case WRITER_IDLE:
          System.out.println("[IP: "  + ctx.channel().remoteAddress() + "] 第 " + writerIdleCounter + " 次沒向伺服器發送訊息。");

          if (writerIdleCounter >= idleLimit) {
            System.out.println("連續 " + writerIdleCounter + " 次沒向伺服器發送訊息。");
            writerIdleCounter = 1;
            ctx.channel().writeAndFlush("ping");
          } else {
            writerIdleCounter++;
          }
          break;
        case ALL_IDLE:
          System.out.println("All idle.");
          ctx.channel().writeAndFlush("ping");
          break;
      }
    }

    super.userEventTriggered(ctx, evt);
  }
}
