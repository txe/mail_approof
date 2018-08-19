package com.evo;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendMail {

    private Message _msg;
    private Multipart _multipart;

    public SendMail(Properties props, final String username, final String password) {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        _msg = new MimeMessage(session);
        _multipart = new MimeMultipart();
    }

    public void setHeader(String mailFrom, String[] mailTo, String subject) throws MessagingException {
        _msg.setFrom(new InternetAddress(mailFrom));
        _msg.setSubject(subject);

        InternetAddress[] sendTo = new InternetAddress[mailTo.length];
        for (int i = 0; i < mailTo.length; i++) {
            sendTo[i] = new InternetAddress(mailTo[i]);
        }
        _msg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO,
                sendTo);
    }

    public void addContentAsText(String content) throws MessagingException {
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText(content);
        _multipart.addBodyPart(textBodyPart);
    }

    public void addContentAsHtml(String content, String mimeType) throws MessagingException {
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setContent(content, mimeType);
        _multipart.addBodyPart(textBodyPart);
    }

    public void addAttachement(String filePath, String fileName) throws MessagingException {
        MimeBodyPart attachmentBodyPart= new MimeBodyPart();
        DataSource source = new FileDataSource(filePath);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(fileName);
        _multipart.addBodyPart(attachmentBodyPart);
    }

    public void send() throws MessagingException {
        _msg.setContent(_multipart);
        Transport.send(_msg);
    }

}
