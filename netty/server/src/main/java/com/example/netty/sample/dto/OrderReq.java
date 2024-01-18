package com.example.netty.sample.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderReq extends MessageReq {

  private String symbol;
  private long price;
  private int quantity;

}
