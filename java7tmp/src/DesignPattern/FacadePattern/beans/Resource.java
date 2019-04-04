package DesignPattern.FacadePattern.beans;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;

/**
 * Created by terrencewei on 2018/03/12.
 */
public class Resource {

    private ResourceState mResourceState;
    private List<Link>    mLinks;
    private Resource      mEmbeddedResource;



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }



    public Object getResourceState() {
        return mResourceState;
    }



    public void setResourceState(ResourceState pResourceState) {
        mResourceState = pResourceState;
    }



    public List<Link> getLinks() {
        return mLinks;
    }



    public void setLinks(List<Link> pLinks) {
        mLinks = pLinks;
    }



    public Resource getEmbeddedResource() {
        return mEmbeddedResource;
    }



    public void setEmbeddedResource(Resource pEmbeddedResource) {
        mEmbeddedResource = pEmbeddedResource;
    }
}
