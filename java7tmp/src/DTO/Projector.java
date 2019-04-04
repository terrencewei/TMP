package DTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by terrencewei on 2017/11/09.
 */
public class Projector extends BaseDTO {
    private Map<String, Project> mProjects = new HashMap<>();



    public Projector() {
    }



    public Projector(Project pProject) {
        addProject(pProject);
    }



    public Project addProject(Project pProject) {
        mProjects.put(pProject.getName(), pProject);
        return pProject;
    }



    public Project getProject(String pProject) {
        Project pj = mProjects.get(pProject);
        return pj != null ? pj : new Project(pProject);
    }



    public Map<String, Project> getProjects() {
        return mProjects;
    }



    public void setProjects(Map<String, Project> pProjects) {
        mProjects = pProjects;
    }

}
