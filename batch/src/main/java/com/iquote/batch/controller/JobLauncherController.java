package com.iquote.batch.controller;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JobLauncherController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @PostMapping("/joblauncher")
    public String handle() throws Exception {
        jobLauncher.run(job, new JobParameters());
        return "jobLauncher";
    }

    @GetMapping("/")
    public String test(Model model) {
        model.addAttribute("test", "fdafadf");
        model.addAttribute("today", new Date());
        return "index";
    }
}
