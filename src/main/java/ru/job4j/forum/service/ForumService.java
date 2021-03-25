package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.Status;
import ru.job4j.forum.model.Topic;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.PostRepository;
import ru.job4j.forum.repository.TopicRepository;
import ru.job4j.forum.repository.UserRepository;

import java.util.Calendar;
import java.util.List;

@Service
public class ForumService {
    private User currentUser;
    private final TopicRepository tRep;
    private final PostRepository pRep;
    private final UserRepository uRep;

    public ForumService(TopicRepository tRep,
                        PostRepository pRep,
                        UserRepository uRep) {
        this.tRep = tRep;
        this.pRep = pRep;
        this.uRep = uRep;
    }

    public void saveUser(User user) {
        uRep.save(user);
    }

    public User findUserByName(String name) {
        return uRep.findUserByName(name);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    public User getCurrentUser() {
        return this.currentUser;
    }

    @Transactional
    public void addTopic(Topic topic) {
        topic.setAuthor(getCurrentUser());
        if (topic.getId() == 0) {
            topic.setStatus(Status.ACTIVE);
        }
        tRep.save(topic);
    }

    public List<Topic> getAllTopics() {
        return tRep.findAllTopics();
    }

    public Topic findById(int id) {
        return tRep.findById(id).get();
    }
    @Transactional
    public void addPost(int topicId, Post post) {
        post.setCreated(Calendar.getInstance());
        post.setCreator(getCurrentUser());
        Topic topicById = tRep.findById(topicId).get();
        topicById.addPosts(post);
        post.setTopic(topicById);
        pRep.save(post);
    }

    public Post postFindById(int id) {
        return pRep.findById(id).get();
    }
}
