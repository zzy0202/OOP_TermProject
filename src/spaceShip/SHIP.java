package model;

/**
 * @author shiran
 * 定义飞船和游戏剩余次数的枚举类
 */
public enum SHIP {
    BLUE("/resources/blue_ship.png", "/resources/blue_life.png"),
    GREEN("/resources/green_ship.png", "/resources/green_life.png"),
    ORANGE("/resources/orange_ship.png", "/resources/orange_life.png"),
    RED("/resources/red_ship.png", "/resources/red_life.png");

    String urlShip;
    String urlLife;

    private SHIP(String urlShip, String urlLife) {
        this.urlShip = urlShip;
        this.urlLife = urlLife;
    }

    public String getUrl() {
        return this.urlShip;
    }

    public String getUrlLife() {
        return this.urlLife;
    }
}
