package com.mex.whytter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mex.whytter.domain.Message;
import com.mex.whytter.domain.User;
import com.mex.whytter.domain.Views;
import com.mex.whytter.dto.MessagePageDto;
import com.mex.whytter.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageService messageService;

    public static final int MESSAGES_PER_PAGE = 3;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto list(@PageableDefault(size = MESSAGES_PER_PAGE, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return messageService.findAll(pageable);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message, @AuthenticationPrincipal User user) {
        return messageService.create(message, user);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageDB,
            @RequestBody Message message,
            @AuthenticationPrincipal User user
    ) {
        return messageService.update(messageDB, message, user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageService.delete(message);
    }

}
