package pl.wpulik.cassandra.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import pl.wpulik.cassandra.model.Message;
import pl.wpulik.cassandra.repository.MessageRepository;

@Service
public class MessageService {
	
	
	private MessageRepository messageRepository;

	@Autowired
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public Message save(Message message) throws Exception {
		message.setId(Uuids.timeBased());
		return messageRepository.save(message);
	}
	
	public Set<Message> findByMagicNumberAndDelete(int magic_number) throws Exception{
		Set<Message> messages = messageRepository.findBy_magic_number(magic_number);
		removeMessages(messages);
		return messages;
	}
	
	private void removeMessages(Set<Message> messages) {
		for(Message ms : messages) {
			messageRepository.delete(ms);
			System.out.println(ms.getEmail() + ": title \"" + ms.getTitle() + "\", email text \"" + ms.getContent() + "\"");
			}
	}
	
	public List<Message> findByEmail(String email) throws Exception{
		System.out.println(email);
		List<Message> list = messageRepository.findByEmail(email);
		System.out.println(list.toString());
		return list;
	}
	
	
	

}
