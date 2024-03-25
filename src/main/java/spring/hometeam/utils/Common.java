package spring.hometeam.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
@Component
public class Common {

    public String generateVerificationCode() {
        return RandomStringUtils.randomNumeric(6);
    }




}