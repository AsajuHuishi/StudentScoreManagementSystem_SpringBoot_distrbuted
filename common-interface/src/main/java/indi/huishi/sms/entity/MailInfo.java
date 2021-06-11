package indi.huishi.sms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: Huishi
 * @Date: 2021/6/11 21:14
 */
@Data
@AllArgsConstructor
@ToString
public class MailInfo {
    // 主题
    private String mailTopic;
    // 正文
    private String mailText;
}
