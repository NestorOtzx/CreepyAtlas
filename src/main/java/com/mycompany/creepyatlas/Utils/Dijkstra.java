package com.mycompany.creepyatlas.Utils;
import java.util.*;

public class Dijkstra {

    private static final int[][] DIRECTIONS = {
        {0, 1},   
        {1, 0},   
        {0, -1},  
        {-1, 0}
    };

    private static class Node implements Comparable<Node> {
        int x, y;
        int dist;

        Node(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.dist, other.dist);
        }
    }

    public static List<int[]> findPath(char[][] map, int startX, int startY, int endX, int endY) {
        int rows = map.length;
        int cols = map[0].length;

        int[][] dist = new int[rows][cols];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        boolean[][] visited = new boolean[rows][cols];
        int[][] parentX = new int[rows][cols];
        int[][] parentY = new int[rows][cols];
        for (int[] row : parentX) Arrays.fill(row, -1);
        for (int[] row : parentY) Arrays.fill(row, -1);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist[startY][startX] = 0;
        pq.offer(new Node(startX, startY, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (visited[current.y][current.x]) continue;
            visited[current.y][current.x] = true;

            if (current.x == endX && current.y == endY) {
                return reconstructPath(parentX, parentY, endX, endY);
            }

            for (int[] dir : DIRECTIONS) {
                int nx = current.x + dir[1];
                int ny = current.y + dir[0];

                if (nx >= 0 && nx < cols && ny >= 0 && ny < rows) {
                    if (map[ny][nx] == ' ' && !visited[ny][nx]) {
                        int newDist = current.dist + 1;
                        if (newDist < dist[ny][nx]) {
                            dist[ny][nx] = newDist;
                            parentX[ny][nx] = current.x;
                            parentY[ny][nx] = current.y;
                            pq.offer(new Node(nx, ny, newDist));
                        }
                    }
                }
            }
        }

        return null;
    }

    private static List<int[]> reconstructPath(int[][] parentX, int[][] parentY, int endX, int endY) {
        List<int[]> path = new ArrayList<>();
        int x = endX, y = endY;
        while (x != -1 && y != -1) {
            path.add(new int[]{y, x});
            int px = parentX[y][x];
            int py = parentY[y][x];
            x = px;
            y = py;
        }
        Collections.reverse(path);
        return path;
    }
}
