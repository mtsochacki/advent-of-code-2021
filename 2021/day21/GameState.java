import java.util.Objects;

public class GameState {
    int position1;
    int position2;
    int score1;
    int score2;

    public GameState(int position1, int position2, int score1, int score2) {
        this.position1 = position1;
        this.position2 = position2;
        this.score1 = score1;
        this.score2 = score2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return position1 == gameState.position1 && position2 == gameState.position2 && score1 == gameState.score1 && score2 == gameState.score2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position1, position2, score1, score2);
    }
}
