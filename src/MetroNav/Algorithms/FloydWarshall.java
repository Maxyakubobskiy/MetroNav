package MetroNav.Algorithms;

import MetroNav.Database.Metro;

public class FloydWarshall {

    // Метод для знаходження найкоротшої відстані між двома станціями за допомогою алгоритму Флойда-Уоршалла
    public int findShortestDistance(Metro metro, String startStation, String endStation) {
        int numStations = metro.getStations().size();
        // Матриця для зберігання довжин найкоротших шляхів між станціями
        int[][] distances = new int[numStations][numStations];

        // Ініціалізація початкових відстаней
        for (int i = 0; i < numStations; i++) {
            for (int j = 0; j < numStations; j++) {
                // Встановлення відстані між станціями на нескінченність, крім випадку, коли це одна і та ж станція
                if (i == j) {
                    distances[i][j] = 0;
                } else {
                    distances[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        // Заповнення початкових відстаней на основі інформації з метро
        for (String station : metro.getStations()) {
            int stationIndex = metro.getStations().indexOf(station);
            for (String neighbor : metro.getConnectedStations(station)) {
                int neighborIndex = metro.getStations().indexOf(neighbor);
                int distance = metro.getDistanceBetweenStations(station, neighbor);
                distances[stationIndex][neighborIndex] = distance;
                distances[neighborIndex][stationIndex] = distance;
            }
        }

        // Виконання алгоритму Флойда-Уоршалла для знаходження найкоротших шляхів
        for (int k = 0; k < numStations; k++) {
            for (int i = 0; i < numStations; i++) {
                for (int j = 0; j < numStations; j++) {
                    if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE
                            && distances[i][k] + distances[k][j] < distances[i][j]) {
                        distances[i][j] = distances[i][k] + distances[k][j];
                    }
                }
            }
        }

        // Отримання індексів початкової та кінцевої станцій
        int startIndex = metro.getStations().indexOf(startStation);
        int endIndex = metro.getStations().indexOf(endStation);

        // Повернення найкоротшої відстані між початковою та кінцевою станціями
        return distances[startIndex][endIndex];
    }
}


