package com.example.messagingserver.controller;

import com.example.messagingserver.beans.ChatGroup;
import com.example.messagingserver.beans.GroupDTO;
import com.example.messagingserver.dao.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupRepository groupRepo;

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody GroupDTO dto) {
        if (groupRepo.existsById(dto.getGroupId())) {
            return ResponseEntity.badRequest().body("Group already exists");
        }

        ChatGroup group = new ChatGroup(dto.getGroupId(), dto.getGroupName(), dto.getMembers());
        groupRepo.save(group);
        return ResponseEntity.ok("Group created successfully");
    }

    @GetMapping("/user/{username}")
    public List<ChatGroup> getGroupsForUser(@PathVariable String username) {
        return groupRepo.findAll().stream()
                .filter(g -> g.getMembers().contains(username))
                .collect(Collectors.toList());
    }
}
