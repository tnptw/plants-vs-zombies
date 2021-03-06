package entity;

import entity.base.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Objects;

/**
 * The type Side element.
 */
public class SideElement extends Entity {

    private static int cardSelected = -1;
    private int cooldownTime;
    private boolean isDisabled = false;
    private static ImageView selectedBorder;
    private static HashMap<Integer, SideElement> allElements;
    private final int cost;

    /**
     * Instantiates a new Side element.
     *
     * @param x      the x
     * @param y      the y
     * @param path   the path
     * @param width  the width
     * @param height the height
     * @param cost   the cost
     */
    public SideElement(int x, int y, String path, int width, int height, int cost) {
        super(x, y, width, height, path);
        this.cost = cost;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets side elements.
     *
     * @param level the level
     * @param pane  the pane
     */
    public static void getSideElements(int level, Pane pane) {
        String path;
        int x;
        int y;
        int width = 97;
        int height = 58;
        SideElement.allElements = new HashMap<>();
        if (level >= 1) {
            path = "/images/sunflowerCard.png";
            x = 24;
            y = 79;
            SideElement sunflowerCard = new SideElement(x, y, path, width, height, 50);
            sunflowerCard.buildImage(pane);
            sunflowerCard.cooldownTime = 5000;
            SideElement.allElements.put(1, sunflowerCard);
            sunflowerCard.getImage().setOnMouseClicked(e -> handler(sunflowerCard, 1));
        }
        x = 22;
        if (level >= 1) {
            path = "/images/peashooterCard.png";
            y = 147;
            SideElement peashooterCard = new SideElement(x, y, path, width, height, 100);
            peashooterCard.buildImage(pane);
            peashooterCard.cooldownTime = 6000;
            SideElement.allElements.put(2, peashooterCard);
            peashooterCard.getImage().setOnMouseClicked(e -> handler(peashooterCard, 2));
        }
        if (level >= 2) {
            path = "/images/wallnutCard.png";
            y = 217;
            SideElement wallnutCard = new SideElement(x, y, path, width, height, 50);
            wallnutCard.buildImage(pane);
            wallnutCard.cooldownTime = 7000;
            SideElement.allElements.put(3, wallnutCard);
            wallnutCard.getImage().setOnMouseClicked(e -> handler(wallnutCard, 3));
        }
        if (level >= 3) {
            path = "/images/cherrybombCard.png";
            y = 284;
            SideElement cherrybombCard = new SideElement(x, y, path, width, height, 150);
            cherrybombCard.buildImage(pane);
            cherrybombCard.cooldownTime = 15000;
            SideElement.allElements.put(4, cherrybombCard);
            cherrybombCard.getImage().setOnMouseClicked(e -> handler(cherrybombCard, 4));
        }
        if (level >= 4) {
            path = "/images/repeaterCard.png";
            x = 23;
            y = 352;
            SideElement repeaterCard = new SideElement(x, y, path, width, height, 200);
            repeaterCard.buildImage(pane);
            repeaterCard.cooldownTime = 10000;
            SideElement.allElements.put(5, repeaterCard);
            repeaterCard.getImage().setOnMouseClicked(e -> handler(repeaterCard, 5));
        }
        if (level >= 5) {
            path = "/images/chilliPepperCard.png";
            x = 24;
            y = 420;
            SideElement chilliPepperCard = new SideElement(x, y, path, width, height, 125);
            chilliPepperCard.buildImage(pane);
            chilliPepperCard.cooldownTime = 12000;
            SideElement.allElements.put(6, chilliPepperCard);
            chilliPepperCard.getImage().setOnMouseClicked(e -> handler(chilliPepperCard, 6));

        }
        String border_path = Objects.requireNonNull(SideElement.class.getResource("/images/selectedCardBorder.png")).toString();
        selectedBorder = new ImageView(new Image(border_path, 110.0, 72.0, false, false));
        pane.getChildren().add(selectedBorder);
        selectedBorder.setVisible(false);
        selectedBorder.setDisable(true);
    }

    /**
     * Handler.
     *
     * @param sideElement  the side element
     * @param cardSelected the card selected
     */
    public static void handler(SideElement sideElement, int cardSelected) {
        if (!sideElement.isDisabled) {
            setCardSelected(cardSelected);
            Shovel.getInstance().disable();
        }
    }

    /**
     * Gets card selected.
     *
     * @return the card selected
     */
    public static int getCardSelected() {
        return cardSelected;
    }

    private static void setCardSelected(int cardSelected) {
        SideElement.cardSelected = cardSelected;
        selectedBorder.setVisible(true);
        selectedBorder.setX(allElements.get(SideElement.cardSelected).getX() - 5);
        selectedBorder.setY(allElements.get(SideElement.cardSelected).getY() - 5);
    }

    /**
     * Sets card selected to null.
     */
    public static void setCardSelectedToNull() {
        cardSelected = -1;
        selectedBorder.setVisible(false);
    }

    /**
     * Gets element.
     *
     * @param element the element
     * @return the element
     */
    public static SideElement getElement(int element) {
        return allElements.getOrDefault(element, null);
    }

    /**
     * Sets disabled on.
     *
     * @param pane the pane
     */
    public void setDisabledOn(Pane pane) {
        isDisabled = true;
        ImageView image = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/lock.png")).toString(), 50.0, 50.0, false, false));
        image.setX(getX() + 20);
        image.setY(getY());
        pane.getChildren().add(image);
        new Thread(() -> {
            try {
                Thread.sleep(cooldownTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isDisabled = false;
            image.setVisible(false);
            image.setDisable(true);
        }).start();
    }
}
