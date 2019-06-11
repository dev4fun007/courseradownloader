package bytes.sync.model;

import java.util.List;

public class Week {

    public String weekName;
    public String weekUrl;
    public List<Module> moduleList;

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public String getWeekUrl() {
        return weekUrl;
    }

    public void setWeekUrl(String weekUrl) {
        this.weekUrl = weekUrl;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }
}
