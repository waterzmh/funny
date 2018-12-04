package org.water;

/**
 * @author water
 * @since 2018/12/4 3:53 PM
 */
public interface EmailConstant {
    /**
     * 邮箱的发送者
     */
    String fromEmail = "";
    /**
     * 邮箱的发送姓名
     */
    String fromName = "";
    /**
     * 邮箱发送着的授权码
     */
    String fromEmailPassword = "";

    /**
     * 个人QQ邮箱用这个
     */
    String emailHost = "smtp.qq.com";

//    /**
//     * 企业QQ邮箱用下面这个
//     */
//    String emailHost = "smtp.exmail.qq.com";
}
