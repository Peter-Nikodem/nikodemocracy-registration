package net.nikodem.service;

import net.nikodem.model.entity.AnswerEntity;
import net.nikodem.model.entity.ElectionEntity;
import net.nikodem.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
