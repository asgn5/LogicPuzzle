import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * The representation of a box within a SubGrid.
 * Contains a fill value corresponding to it's state ( --> how many user clicks )
 * Knowled
 * Specializes Rectangle
 */
public class Box extends Rectangle {

    /**
     * The specified path for the X symbol representing a click
     */
    private static final String xMark = "file:../resources/x28.png";

    /**
     * An "X" -- the fill of the rectangle that represents that there is not a relationship
     */
    public static final ImagePattern fillX = new ImagePattern(new Image(xMark));

    /**
     * The specified path for the O symbol.
     */
    private static final String circle = "file:../resources/circle28.png";

    /**
     * An "O" -- the fill of the rectangle that represents that there is a relationship
     */
    public static final ImagePattern fillC = new ImagePattern(new Image(circle));

    /**
     * The specified path for a blank fill. Needed for the cycle of states of a rectangle (i.e. empty)
     */
    private static final String white = "file:../resources/white_fill.png";

    /**
     * "Empty" -- the fill of the rectangle that represents that no selection has been made.
     */
    public static final ImagePattern fillW = new ImagePattern(new Image(white));

    /**
     * A simple map with each "key" corresponding to the current fill
     * and the "value" corresponds to the next proper fill.
     */
    private static Map<ImagePattern, ImagePattern> imageMap = new HashMap<>();

    /**
     * Initializes the imageMap with keys that correspond to the current state
     * with a respective value that represents the next state ( the next fill ) of the box.
     */
    static {
        imageMap.put(fillW, fillX);
        imageMap.put(fillX, fillC);
        imageMap.put(fillC, fillW);
    }

    /**
     * The string representation of the item to the left and the item above.
     */
    private String left, above;

    /**
     * The box's row index and column index respectively (Within it's SubGrid)
     */
    private int row, column;

    /**
     * A representation of the current state of the rectangle's fill.
     * Each time a color is set, the corresponding ImagePattern is pushed onto the stack.
     * The rectangles current color may be found by calling peek() ( which is the last ImagePattern added)
     * Useful for keeping track of the previous state of the puzzle.
     */
    private Stack<ImagePattern> images;

    /**
     * The correct ImagePattern corresponding to the correct ImagePattern that the rectangle
     * is filled with.
     */
    private ImagePattern answerImage;

    /**
     * @param itemLeft The item to the left of  the box ( the row item )
     * @param itemAbove The item above the box ( the column item )
     * @param row The index of the box's row within the SubGrid
     * @param column The index of the box's column within the SubGrid
     * @param answerImage The correct fill of this box
     */
    public Box(String itemLeft, String itemAbove, int row, int column, ImagePattern answerImage) {
        super(50, 50);
        this.above = itemAbove;
        this.left = itemLeft;
        this.row = row;
        this.column = column;
        this.answerImage = answerImage;
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);
        images = new Stack<>();
        images.push(fillW);
        this.setFill(fillW);
    }

    /**
     * @return The Item above the box
     */
    public String getItemAbove() {
        return above;
    }

    /**
     * @return The Item to the left of the box.
     */
    public String getItemLeft() {
        return left;
    }

    /**
     * @return The box's row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return The box's column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return The fill corresponding to the proper solution fill of this box.
     */
    public ImagePattern getAnswerImage() {
        return answerImage;
    }

    /**
     * @return the current ImagePattern fill of the Box
     */
    public ImagePattern getImage() {
        return images.peek();
    }

    /**
     * @return The next fill for the box
     * The current fill is the key
     * the next fill is the value that corresponds to itself in the map.
     */
    public ImagePattern nextImage() {
        ImagePattern tmp = imageMap.get(images.peek());
        this.setFill(tmp);
        images.push(tmp);
        return tmp;
    }

    /**
     * @param pattern The pattern to fill the rectangle with.
     * @return The current fill of the box.
     */
    public ImagePattern setImage(ImagePattern pattern) {
        images.push(pattern);
        this.setFill(pattern);
        return images.peek();
    }

    /**
     * Removes the last state change of the box and returns to previous state.
     * @return The current fill of the box.
     */
    public ImagePattern removeImage() {
        images.pop();
        this.setFill(images.peek());
        return images.peek();
    }

    /**
     * Resets the contents of the stack and reinitialize
     * the box's fill value with the ImagePattern for empty
     */
    public void clearImage() {
        images = new Stack<>();
        this.setFill(fillW);
        images.push(fillW);
    }

    /**
     * @return The String representation of the box.
     */
    @Override
    public String toString() {
        return String.format("{%12s | %-12s}{%d,%d},{%s}{%s}", left, above, row, column, current(), getColorName(answerImage));
    }

    /**
     * From below down are simply testing functions
     * Utility function for testing purposes.
     *
     * @param pattern The color to pretty print
     * @return the color in string form with color for console
     */
    public String getColorName(ImagePattern pattern) {
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String WHITE = "\u001B[37m";
        String ret = "N/A";
        if (pattern.equals(fillW))
            ret = WHITE + "WHITE" + RESET;
        else if (pattern.equals(fillX))
            ret = RED + "RED" + RESET;
        else if (pattern.equals(fillC))
            ret = GREEN + "GREEN" + RESET;
        return ret;
    }

    /**
     * @return the current fill of the box.
     */
    public String current() {
        return getColorName(images.peek());
    }

    /**
     * @return The string representation of the contents of the stack.
     */
    public String getStack() {
        StringBuilder sb = new StringBuilder("| ");
        for (ImagePattern c : images)
            sb.append(getColorName(c)).append(" | ");
        return sb.toString();
    }


}
