package com.ymmihw.notification.internal;

import lombok.Data;

import java.util.Date;
@Data
public class Notification {
    private Date date;
    private NotificationType format;
    private String productName;
}
