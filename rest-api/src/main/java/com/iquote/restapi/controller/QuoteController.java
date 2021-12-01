package com.iquote.restapi.controller;

import com.iquote.restapi.service.RefreshQuote;

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
	public String getQuote(@RequestParam(name="name", required=false, defaultValue="Quote") String name, Model model) {
		
		refreshQuote.update();

		model.addAttribute("name", name);
		model.addAttribute("askPrice", String.valueOf(refreshQuote.getResponseEntity().getBody().getAskPrice()));
		model.addAttribute("bidPrice", String.valueOf(refreshQuote.getResponseEntity().getBody().getBidPrice()));
		model.addAttribute("askSize", String.valueOf(refreshQuote.getResponseEntity().getBody().getAskSize()));
		model.addAttribute("bidSize", String.valueOf(refreshQuote.getResponseEntity().getBody().getBidSize()));
		// model.addAttribute("count", refreshQuote.getCount());

		return "quote";
	}

}
