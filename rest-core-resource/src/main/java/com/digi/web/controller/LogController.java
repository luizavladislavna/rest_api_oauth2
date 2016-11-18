package com.digi.web.controller;

import com.digi.model.resource.Log;
import com.digi.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LogController {

    @Autowired
    LogRepository logsRepo;

    // API - read
    @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.GET, value = "/logs")
    @ResponseBody
    public List<Log> showAll() {
        return logsRepo.findAll();
    }
}
