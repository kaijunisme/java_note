package com.example.netty.heartbeat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.nio.charset.StandardCharsets;

public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

  private int readerIdleCounter = 1;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof ByteBuf) {
      ByteBuf frame = (ByteBuf) msg;
      System.out.println(frame.toString(StandardCharsets.UTF_8));
      readerIdleCounter = 1;
    }
    ctx.fireChannelRead(msg);
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
      IdleState idleState = idleStateEvent.state();

      int idleLimit = 10;
      switch (idleState) {
        case READER_IDLE:
          System.out.println("[IP: " + ctx.channel().remoteAddress() + "] 第 " + readerIdleCounter + " 次沒收到客戶端訊息。");

          if (readerIdleCounter >= idleLimit) {
            System.out.println("連續 " + idleLimit + " 次沒收到客戶端訊息，關閉客戶端連線。IP: " + ctx.channel().remoteAddress());
            ctx.close();
          } else {
            readerIdleCounter++;
            ctx.channel().writeAndFlush("pong");
          }
          break;
        case WRITER_IDLE:
          System.out.println("Writer idle.");
          break;
        case ALL_IDLE:
          System.out.println("All idle.");
          ctx.channel().writeAndFlush("pong");
          break;
      }
    }

    super.userEventTriggered(ctx, evt);
  }
}
