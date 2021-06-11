package indi.huishi.sms.controller;

import indi.huishi.sms.entity.MailAddress;
import indi.huishi.sms.entity.MailInfo;
import indi.huishi.sms.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * @Author: Huishi
 * @Date: 2021/6/11 8:53
 */
@Controller
@Slf4j
public class MailController {

    @Resource
    MailService mailService;

    /**
     * 邮件模块自测
     * @return
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    @RequestMapping("/mail/send.action")
    public String doMailService(HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {

        Object attribute = request.getSession().getAttribute("statistics");
        if (attribute == null){
            System.out.println("null ");
            return "redirect:http://localhost:8080/user/fail/mail";
        }
        String statistics = attribute.toString();
        mailService.sendMail(new MailAddress("yourEmail","password",
            "xx", "xx@qq.com", "nick"),
            new MailInfo("成绩统计",statistics));
        return "redirect:http://localhost:8080/user/over/mail";
    }
}
