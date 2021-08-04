package mk.anyplex.com.hmvodapi.common.util;


import lombok.extern.slf4j.Slf4j;
import mk.anyplex.com.hmvodapi.common.bean.OcAccount;
import mk.anyplex.com.hmvodapi.common.constants.Sys;
import mk.anyplex.com.hmvodapi.common.exception.BusinessException;
import mk.anyplex.com.hmvodapi.common.tools.CheckOcAccount;
import mk.anyplex.com.hmvodapi.entry.mapper.EntryMapper;
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

    @Resource
    private EntryMapper entryMapper;

    /**
     * email Sender
     * username
     * */
    @Value("${spring.mail.username}")
    private  String sender;

    // private static int f ;

    /**
     * 发送邮箱通知
     * @param subject 主题
     * @param emailAdress 接受地址邮箱
     * @param text 内容->格式html
     *             -- 因为发送email 不属于主要业务，并且发送多个email 速度较慢，响应时间长, 所以异步处理
     * */
    public void sendEmail(String subject,String emailAdress,String text){
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                MimeMessage message = javaMailSender.createMimeMessage();
                String nick= "";
                try {
                    log.debug(emailAdress);
                    System.out.println(emailAdress);
                    nick=javax.mail.internet.MimeUtility.encodeText(Sys.SENDER);
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    helper.setFrom(new InternetAddress(nick+" <"+sender+">"));
                    helper.setTo(emailAdress);
                    helper.setSubject(subject);
                    helper.setText(text, true); // 设置成html 格式.
                } catch (Exception e) {
                    String msg = "Invalid mailbox or mailbox does not exist";
                    log.error(msg);
                    log.error(e.getMessage()+e);
                    throw new BusinessException(msg);
                }
                // try {
                //     Thread.sleep(f*1000);
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
                javaMailSender.send(message);
                // System.err.println("Send email success...");
            }
        });
        thread.start();
        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ;
    }

    /**
     * 发送邮箱通知 , Oc dept , 群发
     * @param subject 主题
     * @param text 内容->格式html
     * */
    public void sendEmailOcDept(String subject,String text){
      List<OcAccount> oclist  =  entryMapper.acList();
      Thread th =   new Thread(new Runnable() {
            @Override
            public void run() {
                if (oclist!=null && !oclist.isEmpty()){
                    for (OcAccount oc : oclist){

                        sendEmail(subject,oc.getAccount(),text);
                    }
                }
            }
        });
       th.start();
    }

}
