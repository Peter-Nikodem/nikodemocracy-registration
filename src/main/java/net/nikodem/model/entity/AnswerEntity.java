package net.nikodem.model.entity;

import javax.persistence.*;

/**
 * @author Peter Nikodem
 */
@Entity
public class AnswerEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String answerText;

    @Column
    private int answerOrder;

    @ManyToOne
    private ElectionEntity election;

    public AnswerEntity() {
    }

    public AnswerEntity(String answerText, int order, ElectionEntity election) {
        this.answerText = answerText;
        this.answerOrder = order;
        this.election = election;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(int answerOrder) {
        this.answerOrder = answerOrder;
    }

    public ElectionEntity getElection() {
        return election;
    }

    public void setElection(ElectionEntity election) {
        this.election = election;
    }
}
