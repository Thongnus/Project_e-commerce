package com.example.api.RestController;

import com.example.api.Entity.FeedBack;
import com.example.api.Entity.User;
import com.example.api.Service.FeedBackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api-feedback")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FeedBackController {
    @Autowired
    private FeedBackService feedBackService;
    @GetMapping("")

    public ResponseEntity<List<FeedBack>> findAll(){
        return ResponseEntity.ok(feedBackService.findAllList());
    }
    @PostMapping("/add_feedback")
    public FeedBack addFeedBack(@RequestBody FeedBack feedBack){
        return feedBackService.addFeedBack(feedBack);
    }
    @PutMapping("/update_feedback/{id}")
        public ResponseEntity<FeedBack> updateFeedBack(@PathVariable int id, @RequestBody FeedBack feedBack){
            FeedBack exitFeedBack=feedBackService.getFeedbackById(id);
            if(exitFeedBack!=null){
                exitFeedBack.setEmail(feedBack.getEmail());
                exitFeedBack.setNote(feedBack.getNote());
                exitFeedBack.setPhone(feedBack.getPhone());
                exitFeedBack.setLastName(feedBack.getLastName());
                exitFeedBack.setFirstName(feedBack.getFirstName());
                exitFeedBack.setSubjectName(feedBack.getSubjectName());
                feedBackService.updateFeedBack(exitFeedBack);
                return ResponseEntity.ok(exitFeedBack);
            }
            else return ResponseEntity.notFound().build();
        }
    @DeleteMapping("/delete/{id}")

    public ResponseEntity<Void> deleteFeedBack(@PathVariable int id){
        feedBackService.deleteByIdFeedBack(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("keyword") String key){
        ArrayList<FeedBack> feedback = feedBackService.search(key);
        return ResponseEntity.ok(feedback);
    }
}
