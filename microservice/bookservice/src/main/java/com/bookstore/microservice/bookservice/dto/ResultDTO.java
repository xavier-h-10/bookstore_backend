package com.bookstore.microservice.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {

  public static int SUCCESS_CODE = 200;
  int code;
  Object data;
  String message;

  public static ResultDTO success(Object data) {
    return ResultDTO.builder()
        .data(data)
        .code(SUCCESS_CODE)
        .message("success")
        .build();
  }

}
