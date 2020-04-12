/**
 * 
 */
package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.mapper.ChatMapper;
import com.scr.message.response.ChatConversationReply;
import com.scr.message.response.ChatUser;
import com.scr.message.response.UnReadMessageCount;
import com.scr.model.MLocation;
import com.scr.model.TChatConversationReply;
import com.scr.model.TChatConversion;
import com.scr.model.TUsers;
import com.scr.repository.MLocationRepository;
import com.scr.repository.TChatConversationReplyRepository;
import com.scr.repository.TChatConversionRepository;
import com.scr.repository.TUserRepository;

/**
 * @author vt1056
 *
 */
@Service
public class ChatService {

	@Autowired
	private TUserRepository tUserRepository;

	@Autowired
	private MLocationRepository mLocationRepository;

	@Autowired
	private TChatConversionRepository tChatConversionRepository;
	
	@Autowired
	private TChatConversationReplyRepository tChatConversationReplyRepository;
	
	@Autowired
	private ChatMapper mapper;

	public ChatUser findLoginUserDetails(Long id) {
		ChatUser chatUser = new ChatUser();
		Optional<TUsers> users = tUserRepository.findById(id);
		if (users.isPresent()) {
			TUsers loginUser = users.get();
			Optional<MLocation> mOptional = mLocationRepository.findById(Long.valueOf(loginUser.getLocationId()));
			if (mOptional.isPresent()) {
				chatUser = mapper.prepareLoginUserDetails(loginUser, mOptional.get());
			}
		}
		return chatUser;
	}

	public List<ChatUser> findExceptLoginUser(Long userId) {
		List<ChatUser> chatUsers = new ArrayList<ChatUser>();
		List<TUsers> usList = tUserRepository.findAll();
		List<MLocation> mLocations = mLocationRepository.findAll();
		chatUsers = mapper.prepareExceptLoginUserDetails(userId, usList, mLocations);
		return chatUsers;
	}

	public TChatConversion findByFromIdAndToId(Integer fromUserId, Integer toUserId) {		
		return tChatConversionRepository.findByToUserIdAndFromUserId(toUserId, fromUserId);
	}

	public TChatConversationReply findByConversationId(Integer conversationId) {		
		return tChatConversationReplyRepository.findByConversationId(conversationId);
	}

	public void saveTChatConversationReply(TChatConversationReply tChatConversationReply) {
		tChatConversationReplyRepository.save(tChatConversationReply);		
	}

	public void saveTChatConversation(TChatConversion tChatConversion) {		
		tChatConversionRepository.save(tChatConversion);
	}

	public List<TChatConversationReply> findByConversationIdAndStatusId(Integer conversationId, Integer statusId) {		
		return tChatConversationReplyRepository.findByConversationIdAndStatusId(conversationId, statusId);
	}

	public List<ChatConversationReply> findChatConversationReplies(Integer fromId, Integer toId) {		
		List<ChatConversationReply> chatConversationReplies = new ArrayList<ChatConversationReply>();
		List<ChatConversationReply> list =	tChatConversionRepository.findChatConversationReplies();
		System.out.println("list: "+list.size());
		return chatConversationReplies;
	}

	public List<UnReadMessageCount> findUnReadMessageCountList(Long userId) {		
		return tChatConversionRepository.findUnReadMessageCountList(userId);
	}

}
