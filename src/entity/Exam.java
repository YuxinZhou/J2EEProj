package entity;

import java.util.Date;

/**
 * Created by DL on 2016/3/17.
 */
public class Exam {
    private int examId;
    private Date time;
    private int examPlaceId;
    private String subject;
    private String category;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getExamPlaceId() {
        return examPlaceId;
    }

    public void setExamPlaceId(int examPlaceId) {
        this.examPlaceId = examPlaceId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
