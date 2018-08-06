package entity;

/**
 * Created by DL on 2016/3/17.
 */
public class ExamPlace {
    private int examPlaceId;
    private String examPlaceLocation;
    private int permitNumber;
    private String examPlaceName;

    public int getExamPlaceId() {
        return examPlaceId;
    }

    public void setExamPlaceId(int examPlaceId) {
        this.examPlaceId = examPlaceId;
    }

    public String getExamPlaceLocation() {
        return examPlaceLocation;
    }

    public void setExamPlaceLocation(String examPlaceLocation) {
        this.examPlaceLocation = examPlaceLocation;
    }

    public int getPermitNumber() {
        return permitNumber;
    }

    public void setPermitNumber(int permitNumber) {
        this.permitNumber = permitNumber;
    }

    public String getExamPlaceName() {
        return examPlaceName;
    }

    public void setExamPlaceName(String examPlaceName) {
        this.examPlaceName = examPlaceName;
    }
}
