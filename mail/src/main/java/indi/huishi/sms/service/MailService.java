package indi.huishi.sms.service;

import indi.huishi.sms.entity.MailAddress;
import indi.huishi.sms.entity.MailInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * @Author: Huishi
 * @Date: 2021/6/11 8:24
 */

/**
 * 发邮件
 */
@Service
@Slf4j
public class MailService {
    /**
     * 发送邮件
     * @param mailAddress
     * @param mailInfo
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public void sendMail(MailAddress mailAddress, MailInfo mailInfo) throws UnsupportedEncodingException, MessagingException {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        System.out.println("打印" + mailAddress.toString());
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", mailAddress.getMyEmailSMTPHost());   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);

        // 3.1 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 3.2. From: 发件人
        message.setFrom(new InternetAddress(mailAddress.getMyEmailAccount(), "Huishi", "UTF-8"));

        // 3.3 To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mailAddress.getReceiveMailAccount(), "XX用户", "UTF-8"));

        // 3.4. Subject: 邮件主题
        message.setSubject(mailInfo.getMailTopic(), "UTF-8");

        // 3.5. Content: 邮件正文（可以使用html标签）
        message.setContent(mailInfo.getMailText(), "text/html;charset=UTF-8");
        // 3.6. 设置发件时间
        message.setSentDate(new Date());

        // 3.7. 保存设置
        message.saveChanges();
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        transport.connect(mailAddress.getMyEmailAccount(), mailAddress.getMyEmailPassword());

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }
}
