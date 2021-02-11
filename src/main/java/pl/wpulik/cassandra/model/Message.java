package pl.wpulik.cassandra.model;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Message {
	
	@PrimaryKey
	private UUID id;
	
	@Email
	private String email;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	@Max(2147483647)
	@Min(-2147483648)
	private int magic_number;
	
	public Message() {}

	public Message(UUID id, String email, String title, String content, int magic_number) {
		this.id = id;
		this.email = email;
		this.title = title;
		this.content = content;
		this.magic_number = magic_number;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMagic_number() {
		return magic_number;
	}

	public void setMagic_number(int magic_number) {
		this.magic_number = magic_number;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", email=" + email + ", title=" + title + ", content=" + content
				+ ", magic_number=" + magic_number + "]";
	}
	
	
	
	
	

}
