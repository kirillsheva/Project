package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AllChats;
import com.example.demo.model.Message;
import com.example.demo.requesBodies.ChatRequst;
import com.example.demo.service.ChatService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class ChatController {


	

	@Autowired
	ChatService service;
	
	
	@RequestMapping("/allmessages")
     public AllChats getAllChats() {
    	 
    	 return service.GetAllMessages();
     }
	
	
	@RequestMapping("/sendmessage")
	public Message sendMessage(@RequestBody ChatRequst request) {
		
		return service.addMessage(request.from, request.msg);
	}
	
	
	
	@MessageMapping("/allMessages")
	public void processMessage(@Payload ChatMessage chatMessage) {
		var chatId = chatRoomService
				.getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
		chatMessage.setChatId(chatId.get());

		ChatMessage saved = chatMessageService.save(chatMessage);

		messagingTemplate.convertAndSendToUser(
				chatMessage.getRecipientId(),"/queue/messages",
				new ChatNotification(
						saved.getId(),
						saved.getSenderId(),
						saved.getSenderName()));
	}
	
}
