package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.Topic;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.MemRepository;

import java.util.List;

@Service
public class ForumService {
    MemRepository memRepository;

    public ForumService(MemRepository memRepository) {
        this.memRepository = memRepository;

    }

    public void saveUser(User user) {
        memRepository.saveUser(user);
    }

    public User findUserByName(String name) {
        return memRepository.findUserByName(name);
    }

    public void setCurrentUser(User user) {
        memRepository.setCurrentUser(user);
    }
    public User getCurrentUser() {
        return memRepository.getCurrentUser();
    }

    public void addTopic(Topic topic) {
        memRepository.addTopic(topic);
    }

    public List<Topic> getAllTopics() {
        return memRepository.getAllTopics();
    }

    public Topic findById(int id) {
        return memRepository.findById(id);
    }

    public void addPost(int topicId, Post post) {
        memRepository.addPost(topicId, post);
    }

    public Post postFindById(int topicId, int id) {
        return memRepository.postFindById(topicId, id);
    }
}
