package ru.job4j.forum.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.forum.model.*;
import ru.job4j.forum.repository.AuthorityRepository;
import ru.job4j.forum.repository.PostRepository;
import ru.job4j.forum.repository.TopicRepository;
import ru.job4j.forum.repository.UserRepository;

import java.util.Calendar;
import java.util.List;

@Service
public class ForumService {
    private final PasswordEncoder encoder;
    private final TopicRepository tRep;
    private final PostRepository pRep;
    private final UserRepository uRep;
    private final AuthorityRepository autRep;

    public ForumService(PasswordEncoder encoder, TopicRepository tRep,
                        PostRepository pRep, UserRepository uRep,
                        AuthorityRepository autRep) {
        this.encoder = encoder;
        this.tRep = tRep;
        this.pRep = pRep;
        this.uRep = uRep;
        this.autRep = autRep; /*
        Authority a1 = new Authority("ROLE_USER");
        Authority a2 = new Authority("ROLE_ADMIN");
        if (autRep.findByAuthority("ROLE_USER") == null) {
            autRep.save(a1);
            autRep.save(a2);
        } */

    }

    public void saveUser(User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(autRep.findByAuthority("ROLE_USER"));
        uRep.save(user);
    }

    @Transactional
    public void addTopic(Topic topic) {
        topic.setAuthor(
                uRep.findByUsername(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName()
                ));
        if (topic.getId() == 0) {
            topic.setStatus(Status.ACTIVE);
        }
        tRep.save(topic);
    }

    public List<Topic> getAllTopics() {
        return tRep.findAllTopics();
    }

    public Topic findById(int id) {
        return tRep.findById(id).orElse(null);
    }
    @Transactional
    public void addPost(int topicId, Post post) {
        post.setCreated(Calendar.getInstance());
        post.setCreator(uRep.findByUsername(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()
        ));
        Topic topicById = tRep.findById(topicId).get();
        topicById.addPosts(post);
        post.setTopic(topicById);
        pRep.save(post);
    }

    public Post postFindById(int id) {
        return pRep.findById(id).orElse(null);
    }
}
