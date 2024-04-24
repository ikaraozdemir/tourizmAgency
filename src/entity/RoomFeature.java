package entity;

import java.util.HashMap;
import java.util.Map;

public class RoomFeature {

    private int roomFeatureId;
    private int roomFeatureRoomId;
    private Map<String, Object> roomFeature;

    public RoomFeature() {
        roomFeature = new HashMap<>();

    }

    public void addRoomFeature(String key, Object value) {
        roomFeature.put(key, value);
    }

    public Object getRoomFeature(String key) {
        return roomFeature.get(key);
    }

    public boolean containsRoomFeature(String key) {
        return roomFeature.containsKey(key);
    }

    public Map<String, Object> getRoomFeature() {
        return roomFeature;
    }

    public int getRoomFeatureId() {
        return roomFeatureId;
    }

    public void setRoomFeatureId(int roomFeatureId) {
        this.roomFeatureId = roomFeatureId;
    }

    public int getRoomFeatureRoomId() {
        return roomFeatureRoomId;
    }

    public void setRoomFeatureRoomId(int roomFeatureRoomId) {
        this.roomFeatureRoomId = roomFeatureRoomId;
    }


    @Override
    public String toString() {
        return "RoomFeature{" +
                "roomFeatureId=" + roomFeatureId +
                ", roomFeatureRoomId=" + roomFeatureRoomId +
                ", roomFeature=" + roomFeature +
                '}';
    }
}
