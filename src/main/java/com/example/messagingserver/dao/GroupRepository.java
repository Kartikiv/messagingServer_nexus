package com.example.messagingserver.dao;

import com.example.messagingserver.beans.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<ChatGroup, String> {}
