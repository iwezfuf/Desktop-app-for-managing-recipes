package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AboutUsDialog extends JDialog {

    public AboutUsDialog(JFrame parent) {
        super(parent, "", true);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("About Us");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        getRootPane().setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), titledBorder));

        JLabel appNameLabel = new JLabel("Easy Food Recipe Book");
        JLabel versionLabel = new JLabel("Version: 1.0");
        JLabel developersLabel = new JLabel("Developed by:");
        JLabel vojtechLabel = new JLabel("Vojtěch Stárek");
        JLabel marekLabel = new JLabel("Marek Eibel");
        JLabel martinLabel = new JLabel("Martin Dražkovec");
        JLabel ondrejLabel = new JLabel("Ondřej Marek");
        JLabel projectLabel = new JLabel("Project for PV168");

        add(appNameLabel, gbc);
        gbc.gridy++;
        add(versionLabel, gbc);
        gbc.gridy++;
        add(developersLabel, gbc);
        gbc.gridy++;
        add(vojtechLabel, gbc);
        gbc.gridy++;
        add(marekLabel, gbc);
        gbc.gridy++;
        add(martinLabel, gbc);
        gbc.gridy++;
        add(ondrejLabel, gbc);
        gbc.gridy++;
        add(projectLabel, gbc);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(parent);
    }
}
