import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

class BOJ2573 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        maps = new int[N][M];
        maps2 = new int[N][M];
        visit = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        stack = new Stack<>();
        year = 0;
        loop : while(true) {
            iceCount = 0;
            visit = new boolean[N][M];
            if (year % 2 == 0) {
                dfsEven();
            } else {
                dfsOdd();
            }
            year++;
            if (Arrays.deepEquals(maps, new int[N][M]) || Arrays.deepEquals(maps2, new int[N][M])) {
                break loop;
            }
        }
        System.out.println(0);
    }
    static void dfsEven(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (visit[i][j]) {
                    continue;
                }
                if (maps[i][j] == 0) {
                    maps2[i][j] = 0;
                    continue;
                }
                stack.push(new Ice(i, j, maps[i][j]));
                visit[i][j] = true;
                iceCount++;
                if (iceCount == 2) {
                    System.out.println(year);
                    System.exit(0);
                }
                while (!stack.isEmpty()) {
                    Ice now = stack.pop();
                    maps2[now.x][now.y] = now.height;
                    for (int dir = 0; dir < 4; dir++) {
                        int x = now.x + dx[dir];
                        int y = now.y + dy[dir];
                        if (visit[x][y]) {
                            continue;
                        }
                        if (maps[x][y] == 0) {
                            maps2[now.x][now.y]--;
                            if(maps2[now.x][now.y] < 0 ) maps2[now.x][now.y] = 0;
                        } else if (maps[x][y] > 0) {
                            stack.push(new Ice(x, y, maps[x][y]));
                            visit[x][y] = true;
                        }
                    }
                }
            }
        }
    }
    static void dfsOdd(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (visit[i][j]) {
                    continue;
                }
                if (maps2[i][j] == 0) {
                    maps[i][j] = 0;
                    continue;
                }
                stack.push(new Ice(i, j, maps2[i][j]));
                visit[i][j] = true;
                iceCount++;
                if (iceCount == 2) {
                    System.out.println(year);
                    System.exit(0);
                }
                while (!stack.isEmpty()) {
                    Ice now = stack.pop();
                    maps[now.x][now.y] = now.height;
                    for (int dir = 0; dir < 4; dir++) {
                        int x = now.x + dx[dir];
                        int y = now.y + dy[dir];
                        if (visit[x][y]) {
                            continue;
                        }
                        if (maps2[x][y] == 0) {
                            maps[now.x][now.y]--;
                            if(maps[now.x][now.y] < 0) maps[now.x][now.y] = 0;
                        } else if (maps2[x][y] > 0) {
                            stack.push(new Ice(x, y, maps2[x][y]));
                            visit[x][y] = true;
                        }
                    }
                }
            }
        }
    }
    static int[][] maps;
    static int[][] maps2;
    static int year;
    static int iceCount;
    static Stack<Ice> stack;
    static int N;
    static int M;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static boolean[][] visit;
}
class Ice{
    int x;
    int y;
    int height;

    public Ice(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }
}
