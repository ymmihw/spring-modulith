package com.ymmihw.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class NotificationDTO {
    private Date date;
    private String format;
    private String productName;
}
