/**
 * 
 */
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scr.message.response.ChatConversationReply;
import com.scr.message.response.UnReadMessageCount;
import com.scr.model.TChatConversion;

/**
 * @author vt1056
 *
 */
public interface TChatConversionRepository extends JpaRepository<TChatConversion, Long>{

	TChatConversion findByToUserIdAndFromUserId(Integer fromId, Integer toId);
	
	@Query(value="select con_reply.*, (select from_userid from t_chat_conversation where ID = con_reply.conversationid) as from_id, (select to_userid from t_chat_conversation where id = con_reply.conversationid) as to_id from t_chat_conversation_reply con_reply where conversationid in (select ID from t_chat_conversation where (from_userid = 74 and to_userid = 85) or (from_userid = 85 and to_userid = 74)) order by con_reply.timeStamp", nativeQuery=true)
	List<ChatConversationReply> findChatConversationReplies();

	@Query(value="select count(*) as Count from t_chat_conversation_reply con_reply inner join t_chat_conversation con  on con_reply.ConversationId = con.ID where con_reply.StatusId = 2 and con.to_userid =:userId group by ConversationId", nativeQuery=true)
	List<UnReadMessageCount> findUnReadMessageCountList(@Param("userId") Long userId);

}
