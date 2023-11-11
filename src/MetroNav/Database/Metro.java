package MetroNav.Database;

import java.util.*;

public class Metro {
    private final Map<String, Map<String, Integer>> stationConnections;

    public Metro() {
        stationConnections = new HashMap<>();
    }

    // Метод для додавання нової станції до метро
    public void addStation(String stationName) {
        stationConnections.put(stationName, new HashMap<>());
    }

    // Метод для додавання з'єднання між двома станціями та визначення їхньої відстані
    public void addConnection(String station1, String station2, int distance) {
        stationConnections.get(station1).put(station2, distance);
        stationConnections.get(station2).put(station1, distance);
    }

    // Метод для отримання відстані між двома станціями
    public int getDistanceBetweenStations(String station1, String station2) {
        if (stationConnections.get(station1) != null && stationConnections.get(station1).containsKey(station2)) {
            return stationConnections.get(station1).get(station2);
        }
        return -1; // Повертає від'ємне значення, щоб позначити, що станції не з'єднані.
    }

    // Метод для отримання списку всіх станцій в метро
    public List<String> getStations() {
        return new ArrayList<>(stationConnections.keySet());
    }

    // Метод для отримання списку сусідніх станцій для заданої станції
    public List<String> getConnectedStations(String station) {
        List<String> connectedStations = new ArrayList<>();

        if (stationConnections.containsKey(station)) {
            connectedStations.addAll(stationConnections.get(station).keySet());
        }

        return connectedStations;
    }

    // Метод для отримання списку всіх станцій в метро
    public List<String> getAllStations() {
        List<String> stations = new ArrayList<>(stationConnections.keySet());
        Collections.sort(stations); // Сортування за алфавітом
        return stations;
    }
}





