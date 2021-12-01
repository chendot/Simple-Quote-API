package com.iquote.restapi.controller;

import com.iquote.restapi.pojo.HelloMessage;
import com.iquote.restapi.pojo.Publish;

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
