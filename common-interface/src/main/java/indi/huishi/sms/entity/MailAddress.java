package indi.huishi.sms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: Huishi
 * @Date: 2021/6/11 21:06
 */

/**
 * 邮件发送、接收者信息
 */
@Data
@ToString
@AllArgsConstructor
public class MailAddress {

    private String myEmailAccount;

    private String myEmailPassword;

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 网易126邮箱的 SMTP 服务器地址为: smtp.126.com
    private String myEmailSMTPHost;

    // 收件人邮箱（替换为自己知道的有效邮箱）
    private String receiveMailAccount;

    // 发件人昵称
    private String myEmailNickname;

}
