package GoldilocksProd.com.Server.controller;

import GoldilocksProd.com.Server.projects.Visitor;
import GoldilocksProd.com.Server.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
public class VisitorController {

    @Autowired
    private VisitorRepository visitorRepository;

    @GetMapping("/increment-count")
    public int incrementCount() {
        Visitor visitor = visitorRepository.findById(1L).orElse(new Visitor());
        visitor.setCount(visitor.getCount() + 1);
        visitorRepository.save(visitor);
        return visitor.getCount();
    }

    @GetMapping("/get-count")
    public int getCount() {
        Visitor visitor = visitorRepository.findById(1L).orElse(new Visitor());
        return visitor.getCount();
    }
}

