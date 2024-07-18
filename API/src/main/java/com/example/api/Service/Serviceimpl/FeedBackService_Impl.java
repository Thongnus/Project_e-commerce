package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.FeedBack;
import com.example.api.Reponsitory.FeedBack_Repository;
import com.example.api.Service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FeedBackService_Impl implements FeedBackService {
    @Autowired
    FeedBack_Repository feedBackRepository;
    @Override
    @CacheEvict(value = "feedbacks",allEntries = true)
    public FeedBack addFeedBack(FeedBack feedBack) {
        return feedBackRepository.save(feedBack);
    }

    @Override
    @Caching(put = @CachePut(value = "feedback",key ="#feedBack.idFeedBack"),
    evict = @CacheEvict(value = "feedbacks",allEntries = true)
    )
    public FeedBack updateFeedBack(FeedBack feedBack) {
        return feedBackRepository.saveAndFlush(feedBack);
    }

    @Override
    @Cacheable("feedbacks")
    public List<FeedBack> findAllList() {

        System.out.println("get data");
    return feedBackRepository.findAll();
    }

    @Override

    @Caching(evict ={ @CacheEvict(value = "feedbacks"),@CacheEvict(value = "feedback",key = "#id")})
    public void deleteByIdFeedBack(int id) {
        feedBackRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "feedback",key = "#id")
    public FeedBack getFeedbackById(int id) {
        return feedBackRepository.getFeedBackByIdFeedBack(id);
    }

    @Override
    public ArrayList<FeedBack> search(String name) {
        return feedBackRepository.findByFirstNameContaining(name);
    }
}
