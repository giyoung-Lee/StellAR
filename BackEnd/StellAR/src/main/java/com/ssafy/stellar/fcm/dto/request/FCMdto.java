package com.ssafy.stellar.fcm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FCMdto {
    private String userId;
    private String title;
    private String body;
}
