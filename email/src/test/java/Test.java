import org.water.EmailService;

import java.security.GeneralSecurityException;

/**
 * @author water
 * @since 2018/12/4 3:29 PM
 */
public class Test {

    @org.junit.Test
    public void sendEmail() throws GeneralSecurityException {
        EmailService emailService = new EmailService();
        emailService.sendEmail("903936820@qq.com");
    }
}
