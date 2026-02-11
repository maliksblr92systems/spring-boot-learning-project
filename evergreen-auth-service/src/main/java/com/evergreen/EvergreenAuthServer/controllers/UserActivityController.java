package com.evergreen.EvergreenAuthServer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evergreen.EvergreenAuthServer.services.user_activity.IUserActivityService;
import com.evergreen.lib.dtos.UserActivityDto;

@RestController
@RequestMapping("/api/v1/auth/user-activity")
public class UserActivityController {

    @Autowired
    private IUserActivityService userActivityService;

    @GetMapping("")
    public ResponseEntity<List<UserActivityDto>> getUserActivityByUser() {
        return new ResponseEntity<List<UserActivityDto>>(userActivityService.get(), HttpStatus.OK);
    }

}
