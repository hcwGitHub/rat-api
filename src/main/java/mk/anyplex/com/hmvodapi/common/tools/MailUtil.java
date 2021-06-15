package mk.anyplex.com.hmvodapi.common.tools;


import lombok.extern.slf4j.Slf4j;

import mk.anyplex.com.hmvodapi.common.constants.Sys;
import mk.anyplex.com.hmvodapi.common.exception.ConfCenterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 发送邮箱
 * @author hcw
 * @date 2019-8-9
 * */
@Component
@Slf4j
public class MailUtil {

    /**
     * springBoot的javaMailSender
     * */
    @Resource
    private JavaMailSender javaMailSender;

    /**
     * email Sender
     * */
    @Value("${spring.mail.username}")
    private  String sender;

    /**
     * 发送邮箱验证码
     * @param subject 主题
     * @param emailAdress 接受地址邮箱
     * @param text 内容->格式html
     * */
    public  void  sendEmail(String subject,String emailAdress,String text){
        MimeMessage message = javaMailSender.createMimeMessage();
        String nick= "";
        try {
            nick=javax.mail.internet.MimeUtility.encodeText(Sys.SENDER);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(nick+" <"+sender+">"));
            helper.setTo(emailAdress);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (Exception e) {
            log.info("email-->"+emailAdress+"不存在或無效");
            throw new ConfCenterException("1007");
        }
        javaMailSender.send(message);
    }

    /**
     * 发送邮箱验证码带图片
     * @param subject 主题
     * @param emailAdress 接受地址邮箱
     * @param text 内容->格式html
     * @param rscPath 图片位置
     * @param rscId
     * */
    public  void  sendEmail(String subject,String emailAdress,String text,String rscPath,String rscId){
        MimeMessage message = javaMailSender.createMimeMessage();
        String nick= "";
        try {
            nick=javax.mail.internet.MimeUtility.encodeText(Sys.SENDER);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(nick+" <"+sender+">"));
            helper.setTo(emailAdress);
            helper.setSubject(subject);
            helper.setText(text, true);
            FileSystemResource res=new FileSystemResource(new File(rscPath));
            helper.addInline(rscId,res);
        } catch (Exception e) {
            log.info("email-->"+emailAdress+"不存在或無效");
            throw new ConfCenterException("1007");
        }

        javaMailSender.send(message);
    }

}
