package cn.canghe.robot.groupsave.servicetest;

import cn.canghe.robot.groupsave.common.interceptor.OpenAILogger;
import cn.canghe.robot.groupsave.openai.ChatCompletion;
import cn.canghe.robot.groupsave.openai.ChatCompletionResponse;
import cn.canghe.robot.groupsave.openai.Message;
import cn.canghe.robot.groupsave.openai.OpenAiClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

@Slf4j
public class OpenAiClientTest {
    private OpenAiClient openAiClient;


    @Before
    public void before() {
        //可以为null
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        openAiClient = OpenAiClient.builder()
                .apiKey("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")
                .connectTimeout(50)
                .writeTimeout(50)
                .readTimeout(50)
                .interceptor(Arrays.asList(httpLoggingInterceptor))
                .proxy(proxy)
                .apiHost("https://api.openai.com/")
                .build();
    }

    @Test
    public void chat() {
        //聊天模型：gpt-3.5
        Message message = Message.builder().role(Message.Role.USER).content("请给我用\"江一舟\"做一首诗").build();
        ChatCompletion chatCompletion = ChatCompletion.builder().messages(Arrays.asList(message)).build();
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
        chatCompletionResponse.getChoices().forEach(e -> {
            System.out.println(e.getMessage());
        });
    }
}
