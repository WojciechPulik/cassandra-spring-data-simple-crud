package pl.wpulik.cassandra.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

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

import pl.wpulik.cassandra.model.Message;
import pl.wpulik.cassandra.service.MessageService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class MessageController {

	private MessageService messageService;
	
	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@PostMapping("/message")
	public ResponseEntity<Message> createMessage(@RequestBody @Valid Message message){
		try {
			Message _message = messageService.save(message);
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
			messages = messageService.findByMagicNumberAndDelete(magic_number);
			return new ResponseEntity<>(messages, HttpStatus.FOUND);
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/messages/{emailValue}")
	public ResponseEntity<List<Message>> getByEmail(@PathVariable(value="emailValue") String email){
		List<Message> messages = new ArrayList<>();
		try {
			messages = messageService.findByEmail(email);
			return new ResponseEntity<>(messages, HttpStatus.FOUND);
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
