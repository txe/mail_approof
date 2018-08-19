package com.evo;

import java.util.Properties;
import javax.mail.*;
import org.stringtemplate.v4.*;

public class Main {

    public static void main(String[] args) {

        final String username = "xxx@gmail.com";
        final String password = "xxx";

        final String mailFrom = "xxx@gmail.com";
        final String[] mailTo = {"xxx@yandex.ru"};
        final String subject = "Don't worry, it's a test";

        final String content = mailContent();
        final String mimeType = "text/html; charset=utf-8";
        final String attachementPath = "f:\\keyboard.ahk";
        final String attachementName = "keyboard.ahk";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");

        try {
            SendMail mail = new SendMail(props, username, password);
            mail.setHeader(mailFrom, mailTo, subject);
            mail.addContentAsHtml(content, mimeType); // addContentAsText(content);
            mail.addAttachement(attachementPath, attachementName);
            mail.send();
        } catch (MessagingException e) {
            // some exception
            e = e;
        } catch (Exception e) {
            e = e;
        }
    }

    private static String mailContent() {
        // StringTemplate : https://github.com/antlr/stringtemplate4/blob/master/doc/index.md
        String template = "Test an $what$ sending<br><a href=#>aaa</a>";
        ST st = new ST(template, '$', '$');
        st.add("what", "email");
        return st.render();
    }
}
