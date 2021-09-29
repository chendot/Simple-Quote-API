package com.chendot.quote.controller;

import com.chendot.quote.pojo.HelloMessage;
import com.chendot.quote.pojo.Publish;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class PublishController {
    
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Publish publishing(HelloMessage message) throws Exception {
        // Thread.sleep(1000);
        return new Publish("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
