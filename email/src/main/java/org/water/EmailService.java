package org.water;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author water
 * @since 2018/12/4 3:16 PM
 */
@Service
public class EmailService {

    private static org.springframework.core.io.Resource resource = new ClassPathResource("emailTemplate.html");

    /**
     * 发送邮件
     *
     * @param email 目标邮箱地址
     */
    public String sendEmail(String email) throws GeneralSecurityException {
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileString = readFile(file);
        // 收件人电子邮箱
        String to = email;

        // 发件人电子邮箱
        String from = EmailConstant.fromEmail;

        // 指定发送邮件的主机
        // QQ邮件服务器
        String host = EmailConstant.emailHost;

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        // 协议
        properties.setProperty("mail.transport.protocol", "smtp");
        // 设置端口,使用SSL,不能使用25,会与阿里云的冲突,如果不用阿里云的可以忽略
        properties.setProperty("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
        mailSSLSocketFactory.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
        properties.put("mail.smtp.auth", "true");
        // 设置超时时间
        properties.put("mail.smtp.timeout", 5000);
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、密码(授权码)
                return new PasswordAuthentication(EmailConstant.fromEmail, EmailConstant.fromEmailPassword);
            }
        });
        try {
            // 创建默认的 MimeMessage 对象。
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from, EmailConstant.fromName));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头字段
            message.setSubject("这是邮件的头");

            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(fileString, "text/html;charset=UTF-8");
            // 创建多重消息
            Multipart multipart = new MimeMultipart();

            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);

            // 发送完整消息
            message.setContent(multipart);
            // 发送消息
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException mex) {
            mex.printStackTrace();
        }
        return "send email success!";
    }

    private String readFile(File file) {
        StringBuilder buffer = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            Reader in = new BufferedReader(isr);
            int i;
            while ((i = in.read()) > -1) {
                buffer.append((char) i);
            }
            in.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
