package org.water;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 钉钉机器人群消息通知
 *
 * @author water
 * @since 2019/10/14 11:22
 */
public class DingDingRobotUtil {
    private static String TEST_TOKEN = "填写你申请的机器人token";
    private static String ROBOT_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=";

    /**
     * 发送文本形式的钉钉消息,更直观的介绍请访问文档 https://open-doc.dingtalk.com/microapp/serverapi2/qf2nxq
     *
     * @param accessToken 机器人token
     * @param content     文本内容
     * @param atMobiles   需要@的手机号
     * @param needAtAll   是否需要@全部人,否则@传入的手机号
     * @return 0:成功;-1:失败
     * @throws IOException
     */
    public static int sendRobotMsgByText(String accessToken, String content, List<String> atMobiles, boolean needAtAll) throws IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgtype", "text");
        Map<String, Object> textMap = new HashMap<>(4);
        textMap.put("content", content);
        jsonObject.put("text", textMap);
        Map<String, Object> atMap = new HashMap<>(4);
        atMap.put("atMobiles", atMobiles);
        atMap.put("isAtAll", needAtAll);
        jsonObject.put("at", atMap);

        return sendRobotMsg(accessToken, jsonObject.toJSONString());
    }

    /**
     * 发送链接形式的钉钉消息,更直观的介绍请访问文档 https://open-doc.dingtalk.com/microapp/serverapi2/qf2nxq
     *
     * @param accessToken 机器人token
     * @param title       列表对话框显示的标题
     * @param text        文本内容
     * @param picUrl      图片地址
     * @param messageUrl  跳转地址
     * @return 0:成功;-1:失败
     * @throws IOException
     */
    public static int sendRobotMsgByLink(String accessToken, String title, String text, String picUrl, String messageUrl) throws IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgtype", "link");
        Map<String, Object> linkMap = new HashMap<>(4);
        linkMap.put("title", title);
        linkMap.put("text", text);
        linkMap.put("picUrl", picUrl);
        linkMap.put("messageUrl", messageUrl);
        jsonObject.put("link", linkMap);

        return sendRobotMsg(accessToken, jsonObject.toJSONString());
    }

    /**
     * 发送文本形式的钉钉消息,更直观的介绍请访问文档 https://open-doc.dingtalk.com/microapp/serverapi2/qf2nxq
     *
     * @param accessToken 机器人token
     * @param title       列表对话框显示的标题
     * @param markdown    markdown形式的文本
     * @param mobiles     需要@的手机号
     * @param needAtAll   是否需要@全部人,否则@传入的手机号
     * @return 0:成功;-1:失败
     * @throws IOException
     */
    public static int sendRobotMsgByMarkdown(String accessToken, String title, String markdown, List<String> mobiles, boolean needAtAll) throws IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgtype", "markdown");
        Map<String, Object> markdownMap = new HashMap<>(4);
        markdownMap.put("title", title);
        markdownMap.put("text", markdown);
        jsonObject.put("markdown", markdownMap);
        Map<String, Object> atMap = new HashMap<>(4);
        atMap.put("atMobiles", mobiles);
        atMap.put("isAtAll", needAtAll);
        jsonObject.put("at", atMap);

        return sendRobotMsg(accessToken, jsonObject.toJSONString());
    }

    /**
     * 发送全文跳转形式的钉钉消息,更直观的介绍请访问文档 https://open-doc.dingtalk.com/microapp/serverapi2/qf2nxq
     *
     * @param accessToken    机器人token
     * @param title          列表对话框显示的标题
     * @param text           文本内容
     * @param singleTitle    单个按钮的显示文案
     * @param singleURL      点击singleTitle按钮触发的URL
     * @param btnOrientation 0-按钮竖直排列，1-按钮横向排列
     * @param hideAvatar     0-正常发消息者头像，1-隐藏发消息者头像
     * @return 0:成功;-1:失败
     * @throws IOException
     */
    public static int sendRobotMsgByActionCardAllJump(String accessToken, String title, String text, String singleTitle, String singleURL, int btnOrientation, int hideAvatar) throws IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgtype", "actionCard");
        Map<String, Object> actionCardMap = new HashMap<>(4);
        actionCardMap.put("title", title);
        actionCardMap.put("text", text);
        actionCardMap.put("singleTitle", singleTitle);
        actionCardMap.put("singleURL", singleURL);
        actionCardMap.put("btnOrientation", String.valueOf(btnOrientation));
        actionCardMap.put("hideAvatar", String.valueOf(hideAvatar));
        jsonObject.put("actionCard", actionCardMap);

        return sendRobotMsg(accessToken, jsonObject.toJSONString());
    }

    /**
     * 发送按钮跳转形式的钉钉消息,更直观的介绍请访问文档 https://open-doc.dingtalk.com/microapp/serverapi2/qf2nxq
     *
     * @param accessToken    机器人token
     * @param title          列表对话框显示的标题
     * @param text           文本内容
     * @param btns           按钮列表
     * @param btnOrientation 0-按钮竖直排列，1-按钮横向排列;注意:按钮超过大于2个只会横向排列
     * @param hideAvatar     0-正常发消息者头像，1-隐藏发消息者头像
     * @return 0:成功;-1:失败
     * @throws IOException
     */
    public static int sendRobotMsgByActionCardBtnJump(String accessToken, String title, String text, List<Btn> btns, int btnOrientation, int hideAvatar) throws IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgtype", "actionCard");
        Map<String, Object> actionCardMap = new HashMap<>(8);
        actionCardMap.put("title", title);
        actionCardMap.put("text", text);
        actionCardMap.put("btnOrientation", String.valueOf(btnOrientation));
        actionCardMap.put("hideAvatar", String.valueOf(hideAvatar));
        actionCardMap.put("btns", btns);
        jsonObject.put("actionCard", actionCardMap);

        return sendRobotMsg(accessToken, jsonObject.toJSONString());
    }

    /**
     * 发送卡片形式的钉钉消息,更直观的介绍请访问文档 https://open-doc.dingtalk.com/microapp/serverapi2/qf2nxq
     *
     * @param accessToken 机器人token
     * @param links       链接对象
     * @return 0:成功;-1:失败
     * @throws IOException
     */
    public static int sendRobotMsgByFeedCard(String accessToken, List<Link> links) throws IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgtype", "feedCard");
        Map<String, Object> feedCardMap = new HashMap<>();
        feedCardMap.put("links", links);
        jsonObject.put("feedCard", feedCardMap);

        return sendRobotMsg(accessToken, jsonObject.toJSONString());
    }

    /**
     * 按钮对象
     */
    public static class Btn {
        /**
         * 按钮标题
         */
        private String title;

        /**
         * 按钮跳转的url
         */
        private String actionURL;

        public Btn() {
        }

        public Btn(String title, String actionURL) {
            this.title = title;
            this.actionURL = actionURL;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getActionURL() {
            return actionURL;
        }

        public void setActionURL(String actionURL) {
            this.actionURL = actionURL;
        }
    }

    public static class Link {
        /**
         * 单条信息文本
         */
        private String title;

        /**
         * 点击单条信息到跳转链接
         */
        private String messageURL;

        /**
         * 单条信息后面图片的URL
         */
        private String picURL;

        public Link() {
        }

        public Link(String title, String messageURL, String picURL) {
            this.title = title;
            this.messageURL = messageURL;
            this.picURL = picURL;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessageURL() {
            return messageURL;
        }

        public void setMessageURL(String messageURL) {
            this.messageURL = messageURL;
        }

        public String getPicURL() {
            return picURL;
        }

        public void setPicURL(String picURL) {
            this.picURL = picURL;
        }
    }

    private static int sendRobotMsg(String accessToken, String bodyStr) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(ROBOT_TOKEN + accessToken);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity se = new StringEntity(bodyStr, "utf-8");
        httppost.setEntity(se);

        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            String result = EntityUtils.toString(response.getEntity(), "utf-8");
//            System.out.println(result);
            return 0;
        }

        return -1;
    }

    public static void main(String args[]) throws Exception {
        List<String> mobiles = new ArrayList<>();
        mobiles.add("群内用户的手机号1");
        mobiles.add("群内用户的手机号2");
        sendRobotMsgByText(TEST_TOKEN, "我不是马蹄", mobiles, true);
        sendRobotMsgByLink(TEST_TOKEN,
                "我不是马蹄",
                "这肯定不是马蹄发的",
                "图片地址",
                "链接地址");

        sendRobotMsgByActionCardAllJump(TEST_TOKEN,
                "震惊!mati竟然对一堆小龙虾做出这种事!",
                "### 小龙虾死亡现场\n" +
                        "### 小龙虾的悲惨遭遇\n" +
                        "*昨晚药脉通团队对小龙虾竟然做出了一系列这种事！*", "点击查看小龙虾死亡现场",
                "https://open-doc.dingtalk.com/microapp/serverapi2/qf2nxq", 0, 1);
        List<Btn> btns = new ArrayList<>();
        btns.add(new Btn("十四香？", "链接地址"));
        btns.add(new Btn("十二香？", "链接地址"));
        btns.add(new Btn("清蒸", "链接地址"));
        btns.add(new Btn("猛男生吃！", "链接地址"));

        sendRobotMsgByActionCardBtnJump(TEST_TOKEN, "隐藏头像无效?？-",
                "你喜欢什么味的小龙虾?", btns, 0, 0);
        sendRobotMsgByActionCardBtnJump(TEST_TOKEN, "隐藏头像无效？-",
                "你喜欢什么味的小龙虾v2?", btns, 1, 1);
        List<Link> links = new ArrayList<>();
        links.add(new Link("年度十大小龙虾悲惨遭遇！", "www.baidu.com", "图片地址"));
        links.add(new Link("蒜蓉小龙虾做法！", "www.baidu.com", "图片地址"));
        links.add(new Link("十三香小龙虾做法！", "www.baidu.com", "图片地址"));
        links.add(new Link("清蒸小龙虾做法！", "www.baidu.com", "图片地址"));
        sendRobotMsgByFeedCard(TEST_TOKEN, links);

    }
}
