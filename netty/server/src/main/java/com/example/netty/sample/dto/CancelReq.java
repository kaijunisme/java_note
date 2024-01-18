package com.example.netty.sample.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CancelReq extends MessageReq {

  private String orderNo;

}
