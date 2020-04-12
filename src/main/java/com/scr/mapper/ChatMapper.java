/**
 * 
 */
package com.scr.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.scr.message.response.ChatUser;
import com.scr.model.MLocation;
import com.scr.model.TUsers;
import com.scr.util.PredicateUtil;

/**
 * @author vt1056
 *
 */
@Component
public class ChatMapper {	
	
	
	public ChatUser prepareLoginUserDetails(TUsers loginUser, MLocation mLocation) {
		ChatUser chatUser = new ChatUser();
		chatUser.setID(loginUser.getID().intValue());
		chatUser.setOnlineStatusId(loginUser.getOnlineStatusId());
		chatUser.setUserLocationId(loginUser.getLocationId());
		chatUser.setUserLocationName(mLocation.getLocationName());
		chatUser.setUserName(loginUser.getUserName());
		return chatUser;
	}

	public List<ChatUser> prepareExceptLoginUserDetails(Long userId, List<TUsers> usList, List<MLocation> mLocations) {
		List<ChatUser> chList = new ArrayList<ChatUser>();
		if(usList != null && mLocations != null) {
			for(TUsers user : usList) {
				if(Long.valueOf(user.getID()) != userId) {
					MLocation mLocation = PredicateUtil.findMLoactionByUserId(mLocations, user.getLocationId());
					chList.add(prepareLoginUserDetails(user, mLocation));
				}
			}
		}
		return chList;
	}

}
