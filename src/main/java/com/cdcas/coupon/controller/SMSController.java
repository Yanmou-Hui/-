package com.cdcas.coupon.controller;

import com.github.qcloudsms.*;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("captcha")
public class SMSController {

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/send")
    public void send(@RequestParam("phone") String phone, HttpSession session) throws InterruptedException {

        StringBuilder builder = new StringBuilder("");
//        循环+随机
        for (int i = 0; i < 4; i++) {
//            生成一个随机数 0-9
            builder.append((int) (Math.random() * 9) + 1);
        }

        // 将生成的验证码保存(Session,将来应该用Redis)
//        session.setAttribute("code", builder.toString());
//        System.out.println("服务器生成的code:" + builder.toString());

//        使用Redis存储数据
        redisTemplate.opsForValue().set(phone, builder.toString());
        // 设置数据存储时间 10s（短时间用于测试）
        redisTemplate.expire(phone, 10, TimeUnit.SECONDS);
        System.out.println("保存在Redis中的验证码：" + redisTemplate.opsForValue().get(phone));

        Thread.sleep(12000);
        System.out.println("10s过后的验证码：" + redisTemplate.opsForValue().get(phone));
//        具体操作：腾讯云提供的标准操作模板
        // 短信应用 SDK AppID
     /*   int appid = 1400009099; // SDK AppID 以1400开头
        // 短信应用 SDK AppKey
        String appkey = "9ff91d87c2cd7cd0ea762f141975d1df37481d48700d70ac37470aefc60f9bad";
        // 需要发送短信的手机号码
        String[] phoneNumbers = {phone};
        // 短信模板 ID，需要在短信应用中申请
        // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请签名
        int templateId = 830152;
        // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请
        String smsSign = "您正在申请手机注册，{1}为您的登录验证码，请于{2}分钟内填写。如非本人操作，请忽略本短信。";
        // 单发短信
        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.send(0, "86", phoneNumbers[0],
                    "【你的学习平台】您的验证码是: " + builder.toString(), "", "");
            System.out.print(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }

        // 指定模板ID单发短信
        try {
            String[] params = {"5678"};
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            SmsMultiSenderResult result = msender.sendWithParam("86", phoneNumbers,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.print(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }

        // 发送语音验证码
        try {
            SmsVoiceVerifyCodeSender vvcsender = new SmsVoiceVerifyCodeSender(appid, appkey);
            SmsVoiceVerifyCodeSenderResult result = vvcsender.send("86", phoneNumbers[0],
                    "5678", 2, "");
            System.out.print(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }

        // 发送语音通知
        try {
            SmsVoicePromptSender vpsender = new SmsVoicePromptSender(appid, appkey);
            SmsVoicePromptSenderResult result = vpsender.send("86", phoneNumbers[0],
                    2, 2, "5678", "");
            System.out.print(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }

        // 拉取短信回执以及回复
        try {
            // Note: 短信拉取功能需要联系腾讯云短信技术支持(QQ:3012203387)开通权限
            int maxNum = 10;  // 单次拉取最大量
            SmsStatusPuller spuller = new SmsStatusPuller(appid, appkey);

            // 拉取短信回执
            SmsStatusPullCallbackResult callbackResult = spuller.pullCallback(maxNum);
            System.out.println(callbackResult);

            // 拉取回复
            SmsStatusPullReplyResult replyResult = spuller.pullReply(maxNum);
            System.out.println(replyResult);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }

        // 拉取单个手机短信状态
        try {
            int beginTime = 1511125600;  // 开始时间(unix timestamp)
            int endTime = 1511841600;    // 结束时间(unix timestamp)
            int maxNum = 10;             // 单次拉取最大量
            SmsMobileStatusPuller mspuller = new SmsMobileStatusPuller(appid, appkey);

            // 拉取短信回执
            SmsStatusPullCallbackResult callbackResult = mspuller.pullCallback("86",
                    phoneNumbers[0], beginTime, endTime, maxNum);
            System.out.println(callbackResult);

            // 拉取回复
            SmsStatusPullReplyResult replyResult = mspuller.pullReply("86",
                    phoneNumbers[0], beginTime, endTime, maxNum);
            System.out.println(replyResult);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }*/
    }
}
