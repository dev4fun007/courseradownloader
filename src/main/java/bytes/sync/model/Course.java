package bytes.sync.model;

import java.util.List;

public class Course {

    public String courseName;
    public String courseUrl;
    public List<Week> weekList;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public List<Week> getWeekList() {
        return weekList;
    }

    public void setWeekList(List<Week> weekList) {
        this.weekList = weekList;
    }
}
