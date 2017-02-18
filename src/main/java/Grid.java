import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 * The logical components of the logic puzzle
 * Contains an array of a specified number of SubGrids,
 * a reference to the number of items and categories,
 * as well as reference to all of the Category names
 * Specializes GridPane for its graphical representation only.
 */
public class Grid extends GridPane {

    /**
     * The number of categories and items in each category respectively.
     */
    private int categories, items;

    /**
     * Reference to the name of each category
     */
    private String[] c;

    /**
     * Reference to all og the SubGrids within this Grid.
     */
    private SubGrid[] s;

    /**
     * @param categories The number of categories
     * @param items the number of items
     * @param c A reference to the name of the categories
     * @param s A reference to the SubGrids
     */
    public Grid(int categories, int items, String[] c, SubGrid[] s) {
        this.categories = categories;
        this.items = items;
        this.c = c;
        this.s = s;

        for (SubGrid subGrid : s)
            this.add(subGrid, subGrid.getCordY(), subGrid.getCordX());
    }

    /**
     * @return s The SubGrids
     */
    public SubGrid[] getS() {
        return s;
    }

    /**
     * @return items A reference to the number of items in each category
     */
    public int numItems() {
        return items;
    }

    /**
     * @return c The categories
     */
    public String[] getCategories() {
        return c;
    }

    /**
     * @return categories Reference to the number of categories
     */
    public int numCategories() {
        return categories;
    }

    /**
     * Communicates to each SubGrid to reset all of its contents.
     */
    public void startOver() {
        for (SubGrid tmp : s) tmp.clearAll();
    }

    /**
     * Asks each SubGrid to check for errors.
     */
    public void checkError() {
        for (SubGrid subGrid : s) subGrid.checkError();
    }

    /**
     * Initializes the category labels on the x-axis
     * @return
     */
    public GridPane drawCategoryX() {
        GridPane gp = new GridPane();
        Rectangle[] r = new Rectangle[3];
        StackPane[] panes = new StackPane[3];
        for (int i = 0; i < s.length; i++) {
            r[i] = new Rectangle(210, 50);
            r[i].setFill(Color.BLACK);
            Label text = new Label();
            if (s[i].getCordX() == 0 && s[i].getCordY() == 1) {
                text = new Label(s[i].getCatLeft());
                panes[i] = new StackPane(r[i], text);
                panes[i].setMaxSize(200, 50);
                panes[i].setTranslateX(6);
                panes[i].setTranslateY(-350);
                gp.add(panes[i], i+1, 0);
            } else if (s[i].getCordX() == 0 && s[i].getCordY() == 2) {
                text = new Label(s[i].getCatLeft());
                panes[i] = new StackPane(r[i], text);
                panes[i].setMaxSize(200, 50);
                panes[i].setTranslateX(-5);
                panes[i].setTranslateY(-350);
                gp.add(panes[i], i+1, 0);
            }
            text.setTextFill(Color.WHITE);

        }
        gp.setAlignment(Pos.CENTER);
        return gp;
    }

    /**
     * Initializes the category labels on the y-axis
     */
    public GridPane drawCategoryY() {
        GridPane gp = new GridPane();
        Rectangle[] r = new Rectangle[3];
        StackPane[] panes = new StackPane[3];
        for (int i = 0; i < s.length; i++) {
            r[i] = new Rectangle(50, 203);
            r[i].setFill(Color.BLACK);
            r[i].setStroke(Color.BLACK);
            Label text = new Label();
            if (s[i].getCordX() == 0 && s[i].getCordY() == 1) {
                text = new Label(s[i].getCatAbove());
                text.setRotate(270);
                text.setTextFill(Color.WHITE);
                panes[i] = new StackPane(r[i], text);
                panes[i].setTranslateX(-350);
                gp.add(panes[i], 0, i+1);
            }
            else if (s[i].getCordX() == 0 && s[i].getCordY() == 2) {
                text = new Label(s[i].getCatLeft());
                text.setRotate(270);
                text.setTextFill(Color.WHITE);
                panes[i] = new StackPane(r[i], text);
                panes[i].setTranslateX(-350);
                gp.add(panes[i], 0, i+1);
            }

        }
        gp.setAlignment(Pos.CENTER);
        return gp;
    }


    /**
     * Creates the Labels corresponding to each index of a subgrid
     * @return gp
     */
    public GridPane drawLabels() {
        GridPane gp = new GridPane();
        Rectangle[] r = new Rectangle[items * items];
        StackPane[] panes = new StackPane[items * items];
        for (int j = 0; j < items * items; j++) {
            Label text;
            if ( j < (items*items)/2) {
                r[j] = new Rectangle(120, 50);
                r[j].setFill(Color.WHITE);
                r[j].setStroke(Color.BLACK);
                if (j / 4 < 1) text = new Label(s[0].getBoxItemLeft(0,j % 4));
                else text = new Label(s[2].getBoxItemLeft(0, j%4));
                panes[j] = new StackPane(r[j], text);
                panes[j].setMaxSize(120, 50);
                gp.add(panes[j], 0, j + 1);
            } else {
                r[j] = new Rectangle(50, 120);
                r[j].setFill(Color.WHITE);
                r[j].setStroke(Color.BLACK);
                if (j / ((items * 2) + items) < 1) text = new Label(s[0].getBoxItemAbove( j % 4,0));
                else text = new Label(s[1].getBoxItemAbove(j % 4,0));
                text.setRotate(270);
                text.setAlignment(Pos.BOTTOM_RIGHT);
                panes[j] = new StackPane(r[j], text);
                panes[j].setMaxSize(50, 120);
                gp.add(panes[j], (j % 8) + 1, 0);
            }
            text.setFont(Font.font(null, FontWeight.BOLD,12));
        }
        gp.setHgap(0);
        gp.setMaxSize(520,520);
        return gp;
    }


    /**
     * @return The String representation of each SubGrid that is a member of this Grid.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SubGrid a : s) sb.append(a);
        return sb.toString();
    }
}
