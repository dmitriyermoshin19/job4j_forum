package ru.job4j.forum.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.forum.model.Topic;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic, Integer> {

    @Query("select distinct t from Topic t join fetch t.author")
    List<Topic> findAllTopics();
}
