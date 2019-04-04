package DTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by terrencewei on 2017/11/09.
 */
public class Project extends BaseDTO {
    private String               name;
    private Map<String, Release> mReleases = new HashMap<>();



    public Project(String pName) {
        name = pName;
    }



    public Project(String pName, Release pRelease) {
        addRelease(pRelease);
        name = pName;
    }



    public Release addRelease(Release pRelease) {
        mReleases.put(pRelease.getName(), pRelease);
        pRelease.addParentProject(this);
        return pRelease;
    }



    public Release getRelease(String pRelease) {
        Release release = mReleases.get(pRelease);
        return release != null ? release : new Release(pRelease);
    }



    public String getName() {
        return name;
    }



    public void setName(String pName) {
        name = pName;
    }



    public Map<String, Release> getReleases() {
        return mReleases;
    }



    public void setReleases(Map<String, Release> pReleases) {
        mReleases = pReleases;
    }

}
