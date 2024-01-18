package com.example.netty.sample.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplaceReq extends MessageReq {

  private String orderNo;
  private long price;
  private int quantity;

}
