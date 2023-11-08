package cz.muni.fi.pv168.project.ui.UserInputFields;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class FloatTextField extends JTextField {

    public FloatTextField() {
        super();
        setDocument(new NumericDocument());
    }

    private static class NumericDocument extends javax.swing.text.PlainDocument {
        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;

            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (!Character.isDigit(c) && c != '.') {
                    return;
                }
            }
            if (str.equals(".") && getText(0, getLength()).contains(".")) {
                return;
            }
            if (str.equals(".") && offset == 0) {
                super.insertString(offset, "0", attr);
                offset++;
            }
            super.insertString(offset, str, attr);
        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {
            String text = getText(0, getLength());
            text = text.substring(0, offs) + text.substring(offs + len);
            if (text.equals(".")) {
                super.insertString(0, "0", null);
                offs++;
            }
            super.remove(offs, len);
        }
    }
}