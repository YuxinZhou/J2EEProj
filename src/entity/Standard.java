package entity;

/**
 * Created by DL on 2016/3/31.
 */
public class Standard {
    private int standardId;
    private String standardName;
    private String kind;
    private int gradeSection1;
    private int gradeSection2;
    private int gradeSection3;
    private boolean ifAbsoluteAnswer;

    public int getStandardId() {
        return standardId;
    }

    public void setStandardId(int standardId) {
        this.standardId = standardId;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getGradeSection1() {
        return gradeSection1;
    }

    public void setGradeSection1(int gradeSection1) {
        this.gradeSection1 = gradeSection1;
    }

    public int getGradeSection2() {
        return gradeSection2;
    }

    public void setGradeSection2(int gradeSection2) {
        this.gradeSection2 = gradeSection2;
    }

    public int getGradeSection3() {
        return gradeSection3;
    }

    public void setGradeSection3(int gradeSection3) {
        this.gradeSection3 = gradeSection3;
    }

    public boolean isIfAbsoluteAnswer() {
        return ifAbsoluteAnswer;
    }

    public void setIfAbsoluteAnswer(boolean ifAbsoluteAnswer) {
        this.ifAbsoluteAnswer = ifAbsoluteAnswer;
    }
}
