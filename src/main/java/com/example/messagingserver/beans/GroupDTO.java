package com.example.messagingserver.beans;

import java.util.List;

public class GroupDTO {
    private String groupId;
    private String groupName;
    private List<String> members;

    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public List<String> getMembers() { return members; }
    public void setMembers(List<String> members) { this.members = members; }
}
