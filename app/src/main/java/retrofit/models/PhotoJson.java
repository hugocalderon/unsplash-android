package retrofit.models;

/**
 * Created by PERSONAL on 22/10/2018.
 */

public class PhotoJson {
    private String id;
    private String description;
    private String alt_description;
    private UrlJson urls;
    private String likes;
    private UserJson user;


    public PhotoJson() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UrlJson getUrls() {
        return urls;
    }

    public void setUrls(UrlJson urls) {
        this.urls = urls;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public UserJson getUser() {
        return user;
    }

    public void setUser(UserJson user) {
        this.user = user;
    }

    public String getAlt_description() {
        return alt_description;
    }

    public void setAlt_description(String alt_description) {
        this.alt_description = alt_description;
    }
}
