package Models;

import java.util.ArrayList;
import java.util.List;

public class Board extends BaseClass{
    private String name, url;
    private List<User> members;
    private List<BoardList> lists;
    private BoardPrivacy privacy;

    public String getName() {
        return name;
    }

    public Board(){
        this.members = new ArrayList<>();
        this.lists = new ArrayList<>();
    }

    public String getId(){
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url.length() == 0 ? "https://www.tello.com/" + this.id : url;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<BoardList> getLists() {
        return lists;
    }

    public void setLists(List<BoardList> lists) {
        this.lists = lists;
    }

    public BoardPrivacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(BoardPrivacy privacy) {
        this.privacy = privacy;
    }
}
