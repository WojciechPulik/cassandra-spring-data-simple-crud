package pl.wpulik.cassandra.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.wpulik.cassandra.model.Message;

public interface MessageRepository extends CassandraRepository<Message, UUID>{
	
	
	@Query("SELECT * FROM message WHERE magic_number=:magic_number")
	Set<Message> findBy_magic_number(@Param("magic_number")int magic_number);
	
	List<Message> findByEmail(String email);
}
