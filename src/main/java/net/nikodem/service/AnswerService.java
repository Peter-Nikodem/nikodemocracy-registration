package net.nikodem.service;

import net.nikodem.model.entity.*;
import net.nikodem.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AnswerService {

    private AnswerRepository answerRepository;

    protected void saveAnswers(ElectionEntity election, List<String> possibleAnswers) {
        List<AnswerEntity> answerEntities = new ArrayList<>();
        for (int order = 0; order < possibleAnswers.size(); order++) {
            String answerText = possibleAnswers.get(order);
            answerEntities.add(new AnswerEntity(answerText, order, election));
        }
        answerRepository.save(answerEntities);
    }

    @Autowired
    public void setAnswerRepository(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }
}
