package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;
import java.util.List;

/**
 * @author Marek Eibel
 */
public class InvalidDataDialog {

    private List<String> errors;

    public InvalidDataDialog(List<String> errors) {

        this.errors = errors;
        String errorMessage = refineErrorMessage();
        new JOptionPane().showMessageDialog(null, "<html><b>Invalid data were entered:</b>" + errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String refineErrorMessage()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (var mess: this.errors) {
            builder.append("<html><br>•</br> ").append(mess).append(".");
        }

        return builder.toString();
    }
}
