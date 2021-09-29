package com.chendot.quote.controller;

import com.chendot.quote.service.RefreshQuote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuoteController {

	@Autowired
	private RefreshQuote refreshQuote;

    @GetMapping("/quote")
	public String getQuote(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		
		refreshQuote.update();

		// HttpEntity<String> e = restTemplate.getForEntity(url, responseType)

		model.addAttribute("name", name);
		model.addAttribute("type", refreshQuote.getQuote().getType());
		model.addAttribute("value", refreshQuote.getQuote().getValue().toString());
		model.addAttribute("count", refreshQuote.getCount());

		return "quote";
	}

}
