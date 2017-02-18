import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Represents a smaller size of the larger grid
 * for example in the 3x4 puzzle, this SubGrid would contain 4 rows and 4 columns
 * Important for the creation of Boxes, as well as containing them.
 * Manages the overall communication between larger components and the boxes.
 */
public class SubGrid extends GridPane {
    /**
     * The number of items within the list.
     */
    private int numItems;

    /**
     * The actual contents of the SubGrid in a 2-Dimensional array of boxes
     */
    private Box[][] box;

    /**
     * The position of the SubGrid within the larger Grid
     */
    private int cordX, cordY;

    /**
     * The name of the category above
     */
    private String catAbove;

    /**
     * The name of the category to the left.
     */
    private String catBelow;

    /**
     * @param numItems The number of items in the sub-grid
     * @param box The 2-dimensional array of boxes
     * @param cordX The x location
     * @param cordY The y location
     * @param catAbove The category above
     * @param catBelow The category to the left (misnomer)
     */
    public SubGrid(int numItems, Box[][] box, int cordX, int cordY, String catAbove, String catBelow) {
        this.numItems = numItems;
        this.box = box;
        this.cordX = cordX;
        this.cordY = cordY;
        this.catAbove = catAbove;
        this.catBelow = catBelow;
        for (int i = 0; i < numItems; i++)
            for (int j = 0; j < numItems; j++) {
                System.out.println(box[i][j] + " {" +  box[i][j].getRow() + " " + box[i][j].getColumn() + " cordx " + cordX + " " + cordY + "}\n");
                this.add(box[i][j],box[i][j].getRow(),box[i][j].getColumn());
                box[i][j].setOnMouseClicked(event);
                /* below are events needed to be completed */
		//box[i][j].setOnMouseEntered(onEnter);
		//box[i][j].setOnMouseExited(onExit);
            }
    }

    /**
     * @return The x-coordinate
     */
    public int getCordX() {
        return cordX;
    }

    /**
     *
     * @return The y-coordinate
     */
    public int getCordY() {
        return cordY;
    }

    /**
     * Given an index in the 2-dimensional array,
     * @param i The row
     * @param j The column
     * @return the string representation of the item above it
     */
    public String getBoxItemAbove(int i, int j) {
        return box[i][j].getItemAbove();
    }

    /**
     * Given an index in the 2-dimensional array,
     * @param i The row
     * @param j The column
     * @return The string representation of the item to the left.
     */
    public String getBoxItemLeft(int i, int j) {
        return box[i][j].getItemLeft();
    }


    /**
     * @return The string representation of the category above.
     */
    public String getCatAbove() {
        return catAbove;
    }

    /**
     * @return The string representation of the category to the left.
     */
    public String getCatLeft() {
        return catBelow;
    }

    /**
     * Iterates through the boxes in the 2d-array and checks to see if it is
     * filled with it's respective answer fill (ie, X or O).
     * If not, clears the image
     */
    public void checkError() {
        for (int i = 0; i < numItems; i++)
            for (int j = 0; j < numItems; j++)
                if ((box[i][j].getImage() != Box.fillW) && (box[i][j].getImage() != box[i][j].getAnswerImage()))
                    box[i][j].clearImage();
    }

    /**
     * Clears all of the fills in every box
     */
    public void clearAll() {
        for (int i = 0; i < numItems; i++)
            for (int j = 0; j < numItems; j++)
                box[i][j].clearImage();
    }

    /**
     * Checks to see if there is already a circle present in the row or column
     * @param boxRow the row
     * @param boxColumn the column index
     * @return true if there is already a circle, otherwise false
     */
    private boolean checkCircle(int boxRow, int boxColumn/**/) {
        for (int i = 0; i < numItems; i++) {
            if (box[boxRow][i].getFill().equals(Box.fillC) || box[i][boxColumn].getFill().equals(Box.fillC)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles the user's clicks with a corresponding value
     * representing the number of times it has been pressed
     * in relation to whether there is a relationship present or not
     * between the item above the particular box and the item
     * to the left
     */
    private EventHandler<MouseEvent> event = mouseEvent -> {

        Box as = (Box) mouseEvent.getSource();
        System.out.println("BOX: Left " + as.getItemLeft() + " Above " + as.getItemAbove());
        if (as.getFill().equals(Box.fillW))
            as.nextImage();
        else if (as.getFill().equals(Box.fillX)) {
            if (!(checkCircle(as.getRow(),as.getColumn()))) {
                for (int i = 0; i < numItems; i++) {
                    box[as.getRow()][i].setImage(Box.fillX);
                    box[i][as.getColumn()].setImage(Box.fillX);
                }
                as.nextImage();
            }
        }
        else if (as.getFill().equals(Box.fillC)) {
            for (int q = 0; q < numItems; q++) {
                if (q != as.getRow())
                    box[q][as.getColumn()].removeImage();
                if (q != as.getColumn())
                    box[as.getRow()][q].removeImage();
            }
            as.clearImage();
        }
    };

    /**
     * Returns the string representation of the boxes within
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < numItems; j++) {
                sb.append(box[i][j]).append(" ");
                if (j == box[1].length - 1)
                    sb.append("\n");
            }
            if (i == box.length - 1)
                sb.append("\n");
        }
        return sb.toString();
    }

    /* Unimplemented methods for the future to highlight a box on hover */
//    private EventHandler<MouseEvent> onEnter = mouseEvent -> {
//        Box b = (Box) mouseEvent.getSource();
//        b.setStyle("-fx-background-color:#333333;");
//    };
//
//    private EventHandler<MouseEvent> onExit = mouseEvent -> {
//        Box b = (Box) mouseEvent.getSource();
//        b.setStyle("");
//        b.setOnMouseClicked(event);
//    };


}
