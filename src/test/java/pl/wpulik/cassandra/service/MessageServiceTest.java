package pl.wpulik.cassandra.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import pl.wpulik.cassandra.model.Message;

@SpringBootTest
public class MessageServiceTest {

	
	@Autowired
	MessageService messageService;
	
	
	@Test
	public void saveTest() throws Exception {
		Message message = new Message(Uuids.timeBased(), "test@test.com", "test title", "test content", 111222333);
		Message message2 = new Message(Uuids.timeBased(), "test2@test.com", "test title2", "test content2", 111222333);
		assertNotNull(messageService.save(message));
		assertNotNull(messageService.save(message2));
	}
	
	@Test void findByEmailTest() throws Exception {
		assertNotNull(messageService.findByEmail("test@test.com"));
		assertNotNull(messageService.findByEmail("test2@test.com"));
	}
	
	@Test void findByEmailListTest() throws Exception {
		assertNotNull(messageService.findByEmail("test@test.com"));
		
	}
	
	@Test
	public void findByMagicNumberAndDeleteTest() throws Exception {
		assertFalse(messageService.findByMagicNumberAndDelete(111222333).size()==2);
	}
	
	@Test
	public void findByMagicNumberAndDeleteMustBeEmptyTest() throws Exception {
		assertFalse(messageService.findByMagicNumberAndDelete(111222333).isEmpty());
	}
	
	@Test void findByEmailAfterRemoveTest() throws Exception {
		assertFalse(messageService.findByEmail("test@test.com").isEmpty());
	}
	
}




