package com.hexagonaldemo.ticketapi.adapters.meetup.rest;

import com.hexagonaldemo.ticketapi.common.commandhandler.VoidEmptyCommandHandler;
import com.hexagonaldemo.ticketapi.common.rest.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(name = "administration.enabled", havingValue = "true")
@RequestMapping("/api/v1/meetups")
public class MeetupAdminController extends BaseController {

    @Qualifier("meetupAdminCommandHandler")
    private final VoidEmptyCommandHandler meetupAdminCommandHandler;

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllMeetups() {
        meetupAdminCommandHandler.handle();
    }
}
