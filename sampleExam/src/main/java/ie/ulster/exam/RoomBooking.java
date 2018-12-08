package ie.ulster.exam;

public class RoomBooking {
    private String room;
    private int rm_capacity;
    private String rm_feature;
    private boolean alcohol;

    RoomBooking() {
    }

    RoomBooking(String rm, int cap, String fea, boolean al) {
        this.alcohol = al;
        this.rm_feature = fea;
        this.rm_capacity = cap;
        this.room = rm;
    }

    public String getRoom() {
        return room;
    }

    public int getCapacity() {
        return rm_capacity;
    }

    public String getFeature() {
        return rm_feature;
    }

    public boolean isAlcohol() {
        return alcohol;
    }

    public void SetRoom(String rm) {
        this.room = rm;
    }

    public void SetCapacity(int cap) {
        this.rm_capacity = cap;
    }

    public void SetFeature(String fea) {
        this.rm_feature = fea;
    }

    public void SetAlcohol(boolean al) {
        this.alcohol = al;
    }
}