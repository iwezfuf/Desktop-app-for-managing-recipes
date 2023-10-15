package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import java.awt.*;

/**
 * @author Marek Eibel
 */
public class CustomLabel extends JLabel {

    public int MAX_LABEL_TEXT_LENGTH = 67;

    public enum Fonts {
        SEGOE_UI("Segoe UI");

        private final String fontName;

        Fonts(String fontName) {
            this.fontName = fontName;
        }

        public String getFontName() {
            return fontName;
        }
    }

    private String labelText;
    private int fontSize;
    private Fonts fontName;
    private int fontFlag;
    private Font font;

    public CustomLabel(String labelText) {

        setLabel(labelText);
        this.fontSize = 18;
        this.fontName = Fonts.SEGOE_UI;
        this.fontFlag = Font.PLAIN;

        super.setText(this.labelText);
        this.font = new Font(fontName.getFontName(), fontFlag, fontSize);
        updateFont();
    }

    public void setFontSize(int size) {
        this.fontSize = size;
        updateFont();
    }

    public void makeBold() {
        this.fontFlag = Font.BOLD;
        updateFont();
    }

    public void makeItalic() {
        this.fontFlag = Font.ITALIC;
        updateFont();
    }

    public void makePlain() {
        this.fontFlag = Font.PLAIN;
        updateFont();
    }

    public void makeTextLabelUpperCase() {
        this.labelText = this.labelText.toUpperCase();
        updateFont();
    }

    public void changeMaxLabelTextLength(int maxLength) {
        this.MAX_LABEL_TEXT_LENGTH = maxLength;
    }

    public boolean isBold() {
        return this.fontFlag == Font.BOLD;
    }

    public boolean isPlain() {
        return this.fontFlag == Font.PLAIN;
    }

    public boolean isItalic() {
        return this.fontFlag == Font.ITALIC;
    }

    public void setLabel(String labelText) {
        this.labelText = labelText.substring(0, Math.min(MAX_LABEL_TEXT_LENGTH, labelText.length()));
        if (this.labelText.length() == MAX_LABEL_TEXT_LENGTH) {
            this.labelText = labelText.substring(0, MAX_LABEL_TEXT_LENGTH - 3) + "...";
        }
    }

    private void updateFont() {
        this.font = new Font(fontName.getFontName(), fontFlag, fontSize);
        setFont(this.font);
        super.setText(this.labelText);
    }

    public String getLabelText() {
        return labelText;
    }

    public int getFontSize() {
        return fontSize;
    }

    public Fonts getFontName() {
        return fontName;
    }
}
