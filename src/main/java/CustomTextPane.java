import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CustomTextPane extends JTextPane {
    private final String placeholder;
    private boolean showingPlaceholder = true;

    public CustomTextPane(String placeholder) {
        this.placeholder = placeholder;
        setText(placeholder);
        setForeground(Color.BLACK);

        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder) {
                    setText("");
                    setForeground(Color.BLACK);
                    showingPlaceholder = false;
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.BLACK);
                    showingPlaceholder = true;
                }
            }
        });
    }

    @Override
    public String getText() {
        return showingPlaceholder ? "" : super.getText();
    }
}
