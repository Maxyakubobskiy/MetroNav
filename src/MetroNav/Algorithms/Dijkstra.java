package MetroNav.Algorithms;
import MetroNav.Database.Metro;

import java.util.*;

public class Dijkstra {
    private final List<String> shortestPath; // Список для зберігання точок найкоротшого шляху
    private int shortestDistance; // Змінна для зберігання довжини найкоротшого шляху

    public Dijkstra() {
        shortestPath = new ArrayList<>();
        shortestDistance = 0;
    }

    // Метод для знаходження найкоротшого шляху між двома станціями
    public int findShortestDistance(Metro metro, String startStation, String endStation) {

        Map<String, Integer> shortestDistances = new HashMap<>();
        Set<String> unvisitedStations = new HashSet<>(metro.getStations());// невідвідані станції

        // Ініціалізація всіх станцій з нескінченною довжиною шляху
        for (String station : metro.getStations()) {
            shortestDistances.put(station, Integer.MAX_VALUE);
        }

        // Встановлення початкової станції з довжиною шляху 0
        shortestDistances.put(startStation, 0);

        while (!unvisitedStations.isEmpty()) {
            // Вибір станції з найменшою відомою довжиною шляху
            String currentStation = null;
            for (String station : unvisitedStations) {
                if (currentStation == null || shortestDistances.get(station) < shortestDistances.get(currentStation)) {
                    currentStation = station;
                }
            }

            if (currentStation == null || shortestDistances.get(currentStation) == Integer.MAX_VALUE) {
                break;
            }

            // Видалення поточної станції зі списку невідвіданих
            unvisitedStations.remove(currentStation);

            // Оновлення відомих довжин шляху до сусідніх станцій
            for (String neighbor : metro.getConnectedStations(currentStation)) {
                int distanceToNeighbor = metro.getDistanceBetweenStations(currentStation, neighbor);
                int totalDistance = shortestDistances.get(currentStation) + distanceToNeighbor;

                // Оновлення довжини шляху, якщо новий шлях є коротшим
                if (totalDistance < shortestDistances.get(neighbor)) {
                    shortestDistances.put(neighbor, totalDistance);
                }
            }
        }

        // Отримання найкоротшого шляху та його довжини
        findShortestPath(metro, startStation, endStation, shortestDistances);
        return shortestDistance;
    }

    // Метод для знаходження точок найкоротшого шляху та його довжини
    public void findShortestPath(Metro metro, String startStation, String endStation, Map<String, Integer> shortestDistances) {
        shortestPath.clear();
        shortestDistance = shortestDistances.get(endStation);

        // Відновлення найкоротшого шляху, починаючи з кінцевої станції
        String currentStation = endStation;
        while (!currentStation.equals(startStation)) {
            shortestPath.add(currentStation);
            for (String neighbor : metro.getConnectedStations(currentStation)) {
                int distanceToNeighbor = metro.getDistanceBetweenStations(currentStation, neighbor);
                int totalDistance = shortestDistances.get(currentStation) - distanceToNeighbor;

                // Перехід до попередньої станції в шляху
                if (totalDistance == shortestDistances.get(neighbor)) {
                    currentStation = neighbor;
                    break;
                }
            }
        }
        shortestPath.add(startStation);

        // Перевернення списку, щоб мати правильний порядок від початкової до кінцевої станції
        Collections.reverse(shortestPath);
    }

    // Метод для отримання списку точок найкоротшого шляху
    public List<String> getShortestPath() {
        return shortestPath;
    }
}




