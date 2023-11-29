package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import java.awt.*;

/**
 * @author Marek Eibel
 */
public class CustomLabel extends JLabel {

    public enum Fonts {
        SEGOE_UI("Segoe UI"),
        CONSOLAS("Monospaced");

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
    private final Fonts fontName;
    private int fontFlag;
    private int maxLength;
    private Font font;

    private final int MAX_LENGTH = 1000;

    public CustomLabel(String labelText, int maxLength) {

        this.fontSize = 12;
        this.fontName = Fonts.SEGOE_UI;
        this.fontFlag = Font.PLAIN;
        this.maxLength = maxLength;

        setLabel(labelText);

        super.setText(this.labelText);
        this.font = new Font(fontName.getFontName(), fontFlag, fontSize);
        updateFont();
    }

    public CustomLabel(String labelText) {

        this.fontSize = 16;
        this.fontName = Fonts.CONSOLAS;
        this.fontFlag = Font.PLAIN;
        this.maxLength = MAX_LENGTH;

        setLabel(labelText);

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
        if (labelText.length() >= getMaxLength()) {
            this.labelText = labelText.substring(0, getMaxLength() - 3) + "...";
        } else if (labelText.length() < getMaxLength()) {
            int numberOfSpaces = getMaxLength() - labelText.length();
            this.labelText = labelText + " ".repeat(numberOfSpaces);
        }
    }

    public static int getTextSizeForCustomLabel(String string) {
        return string.length() + 3;
    }

    private void updateFont() {
        this.font = new Font(fontName.getFontName(), fontFlag, fontSize);
        setFont(this.font);
        super.setText(this.labelText);
        repaint();
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

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
