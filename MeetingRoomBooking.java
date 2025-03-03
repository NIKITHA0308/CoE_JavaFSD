package neww;
import java.util.*;
enum Feature {
    PROJECTOR,
    VIDEO_CONFERENCING,
    WHITEBOARD,
    CONFERENCE_PHONE,
    AIR_CONDITIONING
}
class Room {
    String roomId;
    String roomName;
    int capacity;
    EnumSet<Feature> features;
    public Room(String roomId, String roomName, int capacity, EnumSet<Feature> features) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.features = features;
    }
}
class Scheduler {
    private final Map<String, Room> rooms = new HashMap<>();
    public void addRoom(Room room) {
        rooms.put(room.roomId, room);
        System.out.println("Room added: " + room.roomName + ", ID: " + room.roomId);
    }
    public void bookRoom(String roomId, EnumSet<Feature> requiredFeatures) {
        Room room = rooms.get(roomId);
        if (room != null && room.features.containsAll(requiredFeatures)) {
            System.out.println("Room " + room.roomId + " booked successfully.");
        } else {
            System.out.println("Room " + roomId + " does not meet the required features.");
        }
    }
    public void listAvailableRooms(EnumSet<Feature> requiredFeatures) {
        List<String> availableRooms = new ArrayList<>();
        for (Room room : rooms.values()) {
            if (room.features.containsAll(requiredFeatures)) {
                availableRooms.add(room.roomName);
            }
        }
        System.out.println("Available rooms with " + requiredFeatures + ": " + availableRooms);
    }
}
public class MeetingRoomBooking {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.addRoom(new Room("001", "Boardroom", 20, EnumSet.of(Feature.PROJECTOR, Feature.CONFERENCE_PHONE, Feature.AIR_CONDITIONING)));
        scheduler.addRoom(new Room("002", "Strategy Room", 10, EnumSet.of(Feature.WHITEBOARD, Feature.AIR_CONDITIONING)));
        scheduler.bookRoom("001", EnumSet.of(Feature.PROJECTOR, Feature.CONFERENCE_PHONE));
        scheduler.listAvailableRooms(EnumSet.of(Feature.AIR_CONDITIONING));
    }
}
