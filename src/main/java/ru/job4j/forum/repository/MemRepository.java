package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.Topic;
import ru.job4j.forum.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemRepository {
    private User currentUser;
    private final List<User> users = new ArrayList<>();
    private final Map<Integer, Topic> topics = new HashMap<>();
    private static final AtomicInteger USER_ID = new AtomicInteger(2);
    private static final AtomicInteger TOPIC_ID = new AtomicInteger(2);
    private static final AtomicInteger POST_ID = new AtomicInteger(3);

    public MemRepository() {
        User user = User.of("root", "root");
        users.add(user);

        Post one = Post.of(1, "Лада", "Продам машину ладу 01.");
        Post two = Post.of(2, "Тойота", "Продам машину тойоту.");

        Topic topic = Topic.of("Продажа автомобилей");
        topic.setId(1);
        Map<Integer, Post> topicMap = topic.getPosts();
        topicMap.put(one.getId(), one);
        topicMap.put(two.getId(), two);
        topic.setAuthor(user);
        topics.put(topic.getId(), topic);

        one.setTopic(topic);
        two.setTopic(topic);

    }

    public void saveUser(User user) {
        user.setId(USER_ID.getAndIncrement());
        users.add(user);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    public User getCurrentUser() {
        return this.currentUser;
    }

    public User findUserByName(String name) {
        User res = null;
        for (User u : users) {
            if (u.getUsername().equals(name)) {
                res = u;
            }
        }
        return res;
    }

    public void addTopic(Topic topic) {
        topic.setAuthor(getCurrentUser());
        if (topic.getId() == 0) {
            topic.setId(TOPIC_ID.getAndIncrement());
        }
        topics.put(topic.getId(), topic);
    }

    public List<Topic> getAllTopics() {
        return new ArrayList<>(topics.values());
    }

    public Topic findById(int id) {
        return topics.get(id);
    }

    public void addPost(int topicId, Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.getAndIncrement());
        }
        post.setCreated(Calendar.getInstance());
        Topic topicById = findById(topicId);
        topicById.getPosts().put(post.getId(), post);
        post.setTopic(topicById);
    }

    public Post postFindById(int topicId, int id) {
        Topic topicById = findById(topicId);
        return topicById.getPosts().get(id);
    }
}
