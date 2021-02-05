package pl.wpulik.cassandra.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import pl.wpulik.cassandra.model.Message;
import pl.wpulik.cassandra.repository.MessageRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class MessageController {
	
	@Autowired
	MessageRepository messageRepository;
	
	@PostMapping("/message")
	public ResponseEntity<Message> createMessage(@RequestBody Message message){
		try {
			message.setId(Uuids.timeBased());
			Message _message = messageRepository.save(message);
			return new ResponseEntity<>(_message, HttpStatus.CREATED);
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/send")
	public ResponseEntity<Set<Message>> sendMessage(@RequestParam int magic_number){
		Set<Message> messages = new HashSet<>();
		try {
			messages = messageRepository.findBy_magic_number(magic_number);
			removeMessages(messages);
			return new ResponseEntity<>(messages, HttpStatus.FOUND);
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private void removeMessages(Set<Message> messages) {
		for(Message ms : messages) {
			try {
				messageRepository.delete(ms);
				System.out.println(ms.getEmail() + ": title \"" + ms.getTitle() + "\", email text \"" + ms.getContent() + "\"");
			}catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	@GetMapping("/messages/{emailValue}")
	public ResponseEntity<List<Message>> getByEmail(@PathVariable(value="emailValue") String email){
		List<Message> messages = new ArrayList<>();
		try {
			messages = messageRepository.findByEmail(email);
			return new ResponseEntity<>(messages, HttpStatus.FOUND);
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
