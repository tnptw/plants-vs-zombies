package entity.plant;

import entity.Plant;
import entity.Zombie;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import logic.GameController;

import java.util.ArrayList;

public class CherryBomb extends Plant {

    // Fields
    protected ImageView cherry;
    private ArrayList<Zombie> roastedZombies;

    // Constructor
    public CherryBomb(int x, int y, int row, int col) {
        super(x, y, 68, 90, "/assets/gif/anim_cherrybomb.gif", 4, row, col);
    }

    // Methods
    @Override
    public void attacking() {
    }

    @Override
    public void buildImage(GridPane lawn) {
        super.buildImage(lawn);
        Image img = new Image("/assets/gif/powie.gif");
        cherry = new ImageView(img);
        cherry.setFitHeight(180);
        cherry.setFitWidth(160);
        cherry.setX(x - 40);
        cherry.setY(y - 20);
        cherry.setVisible(false);
        cherry.setDisable(true);
        roastedZombies = new ArrayList<>();
    }

    @Override
    public void attacking(Pane pane) {
        pane.getChildren().add(cherry);
        new Thread(() -> {
            try {
                Thread.sleep(1700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Media blast = new Media(getClass().getResource("/assets/sounds/cherrybomb.wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(blast);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
            image.setVisible(false);
            image.setDisable(true);
            cherry.setVisible(true);
            synchronized (GameController.allZombies) {
                for (Zombie x : (Iterable<Zombie>) GameController.allZombies) {
                    if (x.getX() <= (getX() + 250) && x.getX() >= (getX() - 150)) {
                        if (x.getY() <= (getY() + 250) && x.getY() >= (getY() - 150)) {
                            roastedZombies.add(x);
                            x.burntZombie();
                        }
                    }
                }
            }
            for (Object plant : GameController.allPlants) {
                if (this == plant) {
                    GameController.allPlants.remove(plant);
                    break;
                }
            }
            for (Zombie roastedZombie : roastedZombies) {
                for (int i = 0; i < GameController.allZombies.size(); i++) {
                    if (roastedZombie == GameController.allZombies.get(i)) {
                        GameController.allZombies.remove(i);
                    }
                }
            }
            removeCherry();
        }).start();
    }

    public void removeCherry() {
        new Thread(() -> {
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cherry.setVisible(false);
        }).start();
        this.setHealthpoint(0);
    }

    public ArrayList<Zombie> getRoastedZombies() {
        return roastedZombies;
    }
}