package cz.muni.fi.pv168.project;

import com.formdev.flatlaf.FlatLightLaf;
import cz.muni.fi.pv168.project.ui.ApplicationErrorHandler;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.action.NuclearQuitAction;
import cz.muni.fi.pv168.project.ui.action.QuitAction;
import cz.muni.fi.pv168.project.wiring.ProductionDependencyProvider;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        System.setProperty("isDeveloperVersion", "false"); // TODO: set up this thing somehow from Maven

        var errorHandler = new ApplicationErrorHandler();
        Thread.setDefaultUncaughtExceptionHandler(errorHandler);

        initFlatLightLafLookAndFeel();

        EventQueue.invokeLater(() -> {
            try {
                new MainWindow(new ProductionDependencyProvider()).show();
            } catch (Exception ex) {
                showInitializationFailedDialog(ex);
            }
        });


    }

    private static void initFlatLightLafLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "FlatLightLaf layout initialization failed", ex);
        }

    }

    private static void showInitializationFailedDialog(Exception ex) {
        EventQueue.invokeLater(() -> {
            ex.printStackTrace();
            Object[] options = {
                    new JButton(new QuitAction()),
                    new JButton(new NuclearQuitAction())
            };
            JOptionPane.showOptionDialog(
                    null,
                    "Application initialization failed.\nWhat do you want to do?",
                    "Initialization Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]
            );
        });
    }
}
