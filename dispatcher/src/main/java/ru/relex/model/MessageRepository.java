package ru.relex.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findByData(LocalDateTime dataTime);
}
