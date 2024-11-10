package com.myph.springentrytest.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> {
    private String status;
    private String message;
    private T data;
}
