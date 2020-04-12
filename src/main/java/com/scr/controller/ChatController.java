/**
 * 
 */
package com.scr.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ChatUserDetailsWhileLoginResponse;
import com.scr.message.response.UnReadMessageCount;
import com.scr.model.TChatConversationReply;
import com.scr.model.TChatConversion;
import com.scr.services.ChatService;
import com.scr.util.Helper;
import com.scr.message.response.ChatConversationBetweenUsersResponse;
import com.scr.message.response.ChatConversationReply;
import com.scr.message.response.ChatUser;

/**
 * @author vt1056
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api/chat")
public class ChatController {
	static Logger logger = LogManager.getLogger(ChatController.class);
	
	@Autowired
	private ChatService service;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/GetUserDetailsWhileLogin", method = RequestMethod.GET, headers = "Accept=application/json")
	public ChatUserDetailsWhileLoginResponse GetUserDetailsWhileLogin(@RequestParam("UserId") Long UserId) throws JSONException {
		ChatUserDetailsWhileLoginResponse response = new ChatUserDetailsWhileLoginResponse();	
		
		ChatUser loginUser = service.findLoginUserDetails(UserId);		
		response.setLoginUser(loginUser);		
		List<ChatUser> ExceptLoginUser = service.findExceptLoginUser(UserId);
		List<UnReadMessageCount> unReadMessageCountsList = service.findUnReadMessageCountList(UserId);
		response.setExceptLoginUser(ExceptLoginUser);
		List<ChatUser> UnreadCount = new ArrayList<>();
		response.setUnreadCount(UnreadCount);
		
		return  response;
	}
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/GetConversationBetweenUsers", method = RequestMethod.GET, headers = "Accept=application/json")
	public ChatConversationBetweenUsersResponse GetConversationBetweenUsers(@RequestParam("To_Id") Integer ToId,@RequestParam("From_Id") Integer FromId,
			@RequestParam("Image") String Image,@RequestParam("UserName") String UserName,@RequestParam("Message") String Message) throws JSONException {
		ChatConversationBetweenUsersResponse response = new ChatConversationBetweenUsersResponse();	
		
		TChatConversion conversion = service.findByFromIdAndToId(FromId, ToId);
		TChatConversationReply tChatConversationReply = null;
		if(conversion != null) {
			 if (Message == null || Message == "")
             {
				 tChatConversationReply = service.findByConversationId(conversion.getID());
				 tChatConversationReply.setStatusId(1);
				 service.saveTChatConversationReply(tChatConversationReply);
             }else {
            	 tChatConversationReply = new TChatConversationReply();
            	 tChatConversationReply.setConversationId(conversion.getID());
            	 tChatConversationReply.setMessage(Message);
            	 tChatConversationReply.setStatusId(2);
            	 tChatConversationReply.setTimeStamp(Helper.getCurrentTimestamp());
            	 service.saveTChatConversationReply(tChatConversationReply);
             }
		}else {
			if (Message == null || Message == "")
            {
				
            }else {
            	TChatConversion tChatConversion = new TChatConversion();
            	tChatConversion.setFromUserId(FromId);
            	tChatConversion.setToUserId(ToId);
            	service.saveTChatConversation(tChatConversion);
            }
        }
		
		List<ChatConversationReply> chatConversationReplies = service.findChatConversationReplies(FromId, ToId);
		System.out.println(chatConversationReplies.size());
		response.setChatConversationReplies(chatConversationReplies);
		response.setFromId(FromId);
		response.setToId(ToId);
		response.setImage(Image);
		response.setUserName(UserName);
		/*
		 * ChatUser chatUser = new ChatUser(); chatUser.setID(127);
		 * chatUser.setUserName("Phanendra"); chatUser.setOnlineStatusId(1);
		 * chatUser.setUserLocationName("Vijayawada"); chatUser.setUserLocationId(25);
		 * response.setLoginUser(chatUser);
		 * 
		 * List<ChatUser> ExceptLoginUser = new ArrayList<ChatUser>();
		 * response.setExceptLoginUser(ExceptLoginUser); List<ChatUser> UnreadCount =
		 * new ArrayList<>(); response.setUnreadCount(UnreadCount);
		 */
		
		return  response;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/GetUserOnlineStatus", method = RequestMethod.GET, headers = "Accept=application/json")
	public Integer GetUserOnlineStatus(@RequestParam("UserId") Long UserId) throws JSONException {
		ChatUser loginUser = service.findLoginUserDetails(UserId);
		return  loginUser.getOnlineStatusId();
	}
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/GetUnreadMessageCountBetweenTwoUsers", method = RequestMethod.GET, headers = "Accept=application/json")
	public Integer GetUnreadMessageCountBetweenTwoUsers(@RequestParam("To_Id") Integer ToId,@RequestParam("From_Id") Integer FromId) throws JSONException {
		TChatConversion conversion = service.findByFromIdAndToId(FromId, ToId);
		List<TChatConversationReply> chatConversationReply = service.findByConversationIdAndStatusId(conversion.getID(), 2);
		return  chatConversationReply.size();
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/UpdateMessageStatusToRead", method = RequestMethod.GET, headers = "Accept=application/json")
	public Integer UpdateMessageStatusToRead(@RequestParam("To_Id") Integer ToId,
			@RequestParam("From_Id") Integer FromId) throws JSONException {
		TChatConversion conversion = service.findByFromIdAndToId(FromId, ToId);
		TChatConversationReply tChatConversationReply = service.findByConversationId(conversion.getID());
		tChatConversationReply.setStatusId(1);
		service.saveTChatConversationReply(tChatConversationReply);
		return 0;
	}
}
