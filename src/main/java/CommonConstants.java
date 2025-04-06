import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CommonConstants {

    // Gui config
    public static final Dimension GUI_SIZE = new Dimension(1000, 700);

    // Add expense panel config
    public static final Dimension ADDEXPENSE_SIZE = new Dimension(330, 110);

    // Text field config
    public static final Dimension TEXTPANE_SIZE = new Dimension(150, 25);

    // Backgroung color
    public static final Color BACKGROUND_COLOR = new Color(238,238,238,255);

    // Chart panel config
    public static final Dimension CHART_SIZE = new Dimension(500, 400);

    //
    public static final Dimension EXPENSES_LIST_SIZE = new Dimension((int)(GUI_SIZE.width*0.40), (int)(GUI_SIZE.height*0.90));

    // Hashmap for months
    public static final Map<Integer, String> MONTH_NAMES = new HashMap<>();
    static {
        MONTH_NAMES.put(1, "January");
        MONTH_NAMES.put(2, "February");
        MONTH_NAMES.put(3, "March");
        MONTH_NAMES.put(4, "April");
        MONTH_NAMES.put(5, "May");
        MONTH_NAMES.put(6, "June");
        MONTH_NAMES.put(7, "July");
        MONTH_NAMES.put(8, "August");
        MONTH_NAMES.put(9, "September");
        MONTH_NAMES.put(10, "October");
        MONTH_NAMES.put(11, "November");
        MONTH_NAMES.put(12, "December");
    }


}
