package xyz.zegelink.studybuddy.firebaseChatRoom;

/**
 * Created by Chongxian on 11/6/16.
 */

public class Classes {
    private int id;
    private String ClassTaking;
    private String School;

    public Classes() {};

    public Classes(String ct, String sc){
        this.ClassTaking = ct;
        this.School = sc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassTaking() {
        return ClassTaking;
    }

    public void setClassTaking(String classTaking) {
        ClassTaking = classTaking;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }
}
