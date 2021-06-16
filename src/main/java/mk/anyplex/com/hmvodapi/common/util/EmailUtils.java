package mk.anyplex.com.hmvodapi.common.util;


import lombok.extern.slf4j.Slf4j;
import mk.anyplex.com.hmvodapi.common.bean.OcAccount;
import mk.anyplex.com.hmvodapi.common.constants.Sys;
import mk.anyplex.com.hmvodapi.common.exception.BusinessException;
import mk.anyplex.com.hmvodapi.common.tools.CheckOcAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.List;

/**
 *  email 工具, 发送 email
 *  created by andy 2020-06-07
 *  Component -- Spring IOC
 *  Slf4j -- logs
 * */

@Component
@Slf4j
public class EmailUtils implements Serializable {

    /**
     * javaMailSender for SpringBoot
     * */
    @Resource
    private JavaMailSender javaMailSender;

    /**
     * email Sender
     * username
     * */
    @Value("${spring.mail.username}")
    private  String sender;

    /**
     * 发送邮箱通知
     * @param subject 主题
     * @param emailAdress 接受地址邮箱
     * @param text 内容->格式html
     * */
    public void sendEmail(String subject,String emailAdress,String text){
        MimeMessage message = javaMailSender.createMimeMessage();
        String nick= "";
        try {
            nick=javax.mail.internet.MimeUtility.encodeText(Sys.SENDER);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(nick+" <"+sender+">"));
            helper.setTo(emailAdress);
            helper.setSubject(subject);
            helper.setText(text, true); // 设置成html 格式.
        } catch (Exception e) {
            String msg = "Invalid mailbox or mailbox does not exist";
            log.info(msg);
            log.info(e.getMessage()+e);
            throw new BusinessException(msg);
        }
        javaMailSender.send(message);
    }

    /**
     * 发送邮箱通知 , Oc dept , 群发
     * @param subject 主题
     * @param text 内容->格式html
     * */
    public void sendEmailOcDept(String subject,String text){
      List<OcAccount> oclist  =  CheckOcAccount.getAcList();
      if (oclist!=null && !oclist.isEmpty()){
          for (OcAccount oc : oclist){
              sendEmail(subject,oc.getAccount(),text);
          }
      }
    }

}
