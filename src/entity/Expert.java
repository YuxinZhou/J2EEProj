package entity;

/**
 * Created by DL on 2016/3/15.
 */
public class Expert {
    private int expertId;
    private String expertName;
    private String certificatedId;
    private String majorIn;
    private String phoneNumber;
    private String email;
    private String address;
    private String userName;
    private String password;
    private int examId;
    private int examGroup;
    private String subject;

    public int getExpertId() {
        return expertId;
    }

    public void setExpertId(int expertId) {
        this.expertId = expertId;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getCertificatedId() {
        return certificatedId;
    }

    public void setCertificatedId(String certificatedId) {
        this.certificatedId = certificatedId;
    }

    public String getMajorIn() {
        return majorIn;
    }

    public void setMajorIn(String majorIn) {
        this.majorIn = majorIn;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getExamGroup() {
        return examGroup;
    }

    public void setExamGroup(int examGroup) {
        this.examGroup = examGroup;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
