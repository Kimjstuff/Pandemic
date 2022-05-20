import java.awt.*;

public class Player {

    private String playerNum;
    private int xPostmp, yPostmp, sizeHead;
    private Color color;

    public Player(String playerNum, Color color, int xPostmp, int yPostmp, int sizeHead) {
        this.playerNum = playerNum;
        this.color = color;
        this.xPostmp = xPostmp;
        this.yPostmp = yPostmp;
        this.sizeHead = sizeHead;
    }

    public String getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(String playerNum) {
        this.playerNum = playerNum;
    }

    public int getxPostmp() {
        return xPostmp;
    }

    public void setxPostmp(int xPostmp) {
        this.xPostmp = xPostmp;
    }

    public int getyPostmp() {
        return yPostmp;
    }

    public void setyPostmp(int yPostmp) {
        this.yPostmp = yPostmp;
    }

    public int getSizeHead() {
        return sizeHead;
    }

    public void setSizeHead(int sizeHead) {
        this.sizeHead = sizeHead;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
