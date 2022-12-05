package ru.relex.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
