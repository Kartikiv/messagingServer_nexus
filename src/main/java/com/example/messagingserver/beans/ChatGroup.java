package com.example.messagingserver.beans;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class ChatGroup {
    @Id
    private String groupId;
    private String groupName;

    @ElementCollection
    private List<String> members;

    public ChatGroup() {}

    public ChatGroup(String groupId, String groupName, List<String> members) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.members = members;
    }

}

