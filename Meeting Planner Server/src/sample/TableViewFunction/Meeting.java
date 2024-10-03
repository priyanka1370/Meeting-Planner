package sample.TableViewFunction;

import com.mysql.cj.conf.IntegerProperty;
import com.mysql.cj.conf.StringProperty;

import java.sql.Date;

public class Meeting {
    int MeetingID;
    String Name;
    String Start;
    String End;
    String Place;
    String Date;

    public Meeting(int meetingID, String name, String start, String end, String place, String date) {
        MeetingID = meetingID;
        Name = name;
        Start = start;
        End = end;
        Place = place;
        Date = date;
    }

    public int getMeetingID() {
        return MeetingID;
    }

    public void setMeetingID(int meetingID) {
        MeetingID = meetingID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}



