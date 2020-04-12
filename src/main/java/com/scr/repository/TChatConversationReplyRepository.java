/**
 * 
 */
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.TChatConversationReply;

/**
 * @author vt1056
 *
 */
@Repository
public interface TChatConversationReplyRepository extends JpaRepository<TChatConversationReply, Long> {

	TChatConversationReply findByConversationId(Integer conversationId);

	List<TChatConversationReply> findByConversationIdAndStatusId(Integer conversationId, Integer statusId);

}
