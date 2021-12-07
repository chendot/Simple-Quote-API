package com.iquote.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLauncherController {
    private static final Logger log = LoggerFactory.getLogger(JobLauncherController.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @GetMapping("/joblauncher")
    public String handle() throws Exception {
        log.info(job.getName().toString());
        jobLauncher.run(job, new JobParameters());
        log.info("The job had beed submitted!");
        return String.format("Job go!");
    }

    // @GetMapping("/")
    // public String test(@RequestParam String action, Model model) {
    //     model.addAttribute("test", "fdafadf");
    //     model.addAttribute("today", new Date());
    //     if (action.equals("save")) {
    //         log.info("aaaaaaa!");
    //     }
    //     return "index";
    // }
}
