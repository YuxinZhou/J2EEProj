package entity;

/**
 * Created by DL on 2016/3/17.
 */
public class Score {
    private int scoreId;
    private int examId;
    private int studentId;
    private int expertId;
    private int firstGrade;
    private int finalGrade;
    private int retrialGrade;

    private String ticketNumber;
    private String studentName;
    private String examPlaceLocation;
    private String category;
    private String subject;
    private int examNumber;

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getExpertId() {
        return expertId;
    }

    public void setExpertId(int expertId) {
        this.expertId = expertId;
    }

    public int getFirstGrade() {
        return firstGrade;
    }

    public void setFirstGrade(int firstGrade) {
        this.firstGrade = firstGrade;
    }

    public int getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(int finalGrade) {
        this.finalGrade = finalGrade;
    }

    public int getRetrialGrade() {
        return retrialGrade;
    }

    public void setRetrialGrade(int retrialGrade) {
        this.retrialGrade = retrialGrade;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentNumber) {
        this.studentName = studentNumber;
    }

    public String getExamPlaceLocation() {
        return examPlaceLocation;
    }

    public void setExamPlaceLocation(String examPlaceLocation) {
        this.examPlaceLocation = examPlaceLocation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(int examNumber) {
        this.examNumber = examNumber;
    }
}
