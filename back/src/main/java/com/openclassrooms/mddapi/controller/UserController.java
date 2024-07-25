package com.openclassrooms.mddapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.service.IUserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<?> getUserSubscriptions(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserSubscriptions(id));
    }

    @PutMapping("/{id}/subscribe/{topicId}")
    public ResponseEntity<?> subscribe(@PathVariable("id") Long id, @PathVariable("topicId") Long topicId) {
        userService.subscribe(id, topicId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/subscribe/{topicId}")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") Long id, @PathVariable("topicId") Long topicId) {
        userService.unsubscribe(id, topicId);
        return ResponseEntity.ok().build();
    }
}
