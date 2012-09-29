package hr_fisher.user;

import hr_fisher.banking.DropFish;
import hr_fisher.banking.F1D1;
import hr_fisher.banking.NormalBank;
import hr_fisher.banking.UseStiles;
import hr_fisher.locations.BarbarianAssault;
import hr_fisher.locations.Karamja;
import org.powerbot.game.api.methods.tab.Skills;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;


// Created using JFormDesigner

public class FishingGUI extends JFrame {
    public FishingGUI() {
        initComponents();
    }

    private void powerFishActionPerformed(ActionEvent e) {
        stilesRadioButton.setSelected(false);
        bankRadioButton.setSelected(false);
        f1d1RadioButton.setSelected(false);
        powerFishRadioButton.setSelected(true);
    }

    private void bankActionPerformed(ActionEvent e) {
        stilesRadioButton.setSelected(false);
        powerFishRadioButton.setSelected(false);
        f1d1RadioButton.setSelected(false);
        bankRadioButton.setSelected(true);
    }

    private void stilesActionPerformed(ActionEvent e) {
        powerFishRadioButton.setSelected(false);
        bankRadioButton.setSelected(false);
        f1d1RadioButton.setSelected(false);
        stilesRadioButton.setSelected(true);
    }

    private void f1d1ActionPerformed(ActionEvent e) {
        powerFishRadioButton.setSelected(false);
        bankRadioButton.setSelected(false);
        stilesRadioButton.setSelected(false);
        f1d1RadioButton.setSelected(true);
    }

    private void startButtonPressed(ActionEvent e) {

        Variables.dropTuna = dropTunaCheckBox.isSelected();

        Variables.chosenLocation = Variables.locations[locationComboBox.getSelectedIndex()];
        Variables.chosenFishingType = Variables.chosenLocation.getFishingTypes()[typeComboBox.getSelectedIndex()];
        Variables.startTime = System.currentTimeMillis();
        Variables.startLevel = Skills.getLevel(Skills.FISHING);
        Variables.startXP = Skills.getExperience(Skills.FISHING);
        Variables.fishCaught = new int[Variables.chosenFishingType.getPossibleFish().length];
        Variables.fishPrice = new int[Variables.fishCaught.length];

        if (powerFishRadioButton.isSelected())
            Variables.chosenLocation.bankingMethod = new DropFish();
        else if (stilesRadioButton.isSelected())
            Variables.chosenLocation.bankingMethod = new UseStiles();
        else if(f1d1RadioButton.isSelected())
            Variables.chosenLocation.bankingMethod = new F1D1();

        for (int i = 0; i < Variables.fishPrice.length; i++) {
            try {
                Variables.fishPrice[i] = Util.getPriceOfItem(Variables.chosenFishingType.getPossibleFish()[i]);
            } catch (IOException e1) {
            }
        }

        Variables.hasStarted = true;

        setVisible(false);
        dispose();
    }

    private void locationBoxChanged(ItemEvent e) {
        Util.FishingTypes[] fishingTypes = Variables.locations[locationComboBox.getSelectedIndex()].getFishingTypes();
        String[] model = new String[fishingTypes.length];

        for (int i = 0; i < fishingTypes.length; i++) {
            model[i] = fishingTypes[i].getName();
        }

        typeComboBox.setModel(new DefaultComboBoxModel<>(model));

        if (Variables.locations[locationComboBox.getSelectedIndex()] instanceof Karamja) {
            stilesRadioButton.setEnabled(true);
        } else if (stilesRadioButton.isEnabled()) {
            if (stilesRadioButton.isSelected()) {
                bankRadioButton.setSelected(true);
            }
            stilesRadioButton.setSelected(false);
            stilesRadioButton.setEnabled(false);
        }

        if (Variables.locations[locationComboBox.getSelectedIndex()] instanceof BarbarianAssault) {
            if (bankRadioButton.isSelected()) {
                powerFishRadioButton.setSelected(true);
            }

            bankRadioButton.setSelected(false);
            bankRadioButton.setEnabled(false);

        } else if (!bankRadioButton.isEnabled()) {

            bankRadioButton.setEnabled(true);
        }

        dropTunaCheckBox.setSelected(false);
        dropTunaCheckBox.setEnabled(false);

    }

    private void typeBoxChanged(ItemEvent e) {
        Util.FishingTypes chosenFishingType = Variables.locations[locationComboBox.getSelectedIndex()].getFishingTypes()[typeComboBox.getSelectedIndex()];
        if (chosenFishingType != Util.FishingTypes.TYPE_HARPOON_TUNA) {
            dropTunaCheckBox.setSelected(false);
            dropTunaCheckBox.setEnabled(false);
        } else {
            dropTunaCheckBox.setEnabled(true);
        }
    }

    private void initComponents() {

        headerLabel = new JLabel();
        authorLabel = new JLabel();
        leftPanel = new JPanel();
        fishingLocationLabel = new JLabel();
        fishingOptionLabel = new JLabel();
        locationComboBox = new JComboBox<>();
        typeComboBox = new JComboBox<>();
        rightPanel = new JPanel();
        label3 = new JLabel();
        powerFishRadioButton = new JRadioButton();
        bankRadioButton = new JRadioButton();
        bankingOptionsLabel = new JLabel();
        stilesRadioButton = new JRadioButton();
        f1d1RadioButton = new JRadioButton();
        button1 = new JButton();
        panel1 = new JPanel();
        dropTunaCheckBox = new JCheckBox();

        //======== this ========
        Container contentPane = getContentPane();

        //---- headerLabel ----
        headerLabel.setText("AIO Fisher v" + Variables.VERSION);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setFont(headerLabel.getFont().deriveFont(headerLabel.getFont().getSize() + 4f));

        //---- authorLabel ----
        authorLabel.setText("By H3avY Ra1n");
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        authorLabel.setFont(authorLabel.getFont().deriveFont(authorLabel.getFont().getSize() + 2f));

        //======== leftPanel ========
        {
            leftPanel.setBorder(new EtchedBorder());

            //---- fishingLocationLabel ----
            fishingLocationLabel.setText("Fishing Location:");
            fishingLocationLabel.setFont(fishingLocationLabel.getFont().deriveFont(fishingLocationLabel.getFont().getSize() + 3f));

            //---- fishingOptionLabel ----
            fishingOptionLabel.setText("Fishing Type:");
            fishingOptionLabel.setFont(fishingOptionLabel.getFont().deriveFont(fishingOptionLabel.getFont().getSize() + 3f));

            //---- locationComboBox ----
            String[] locationsModel = new String[Variables.locations.length];
            for (int i = 0; i < locationsModel.length; i++) {
                locationsModel[i] = Variables.locations[i].toString();
            }

            locationComboBox.setModel(new DefaultComboBoxModel<>(locationsModel));

            locationComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    locationBoxChanged(e);
                }
            });

            locationComboBox.setSelectedIndex(0);

            //---- typeComboBox ----
            Util.FishingTypes[] fishingTypes = Variables.locations[locationComboBox.getSelectedIndex()].getFishingTypes();
            String[] model = new String[fishingTypes.length];

            for (int i = 0; i < fishingTypes.length; i++) {
                model[i] = fishingTypes[i].getName();
            }

            typeComboBox.setModel(new DefaultComboBoxModel<>(model));
            typeComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    typeBoxChanged(e);
                }
            });

            GroupLayout leftPanelLayout = new GroupLayout(leftPanel);
            leftPanel.setLayout(leftPanelLayout);
            leftPanelLayout.setHorizontalGroup(
                    leftPanelLayout.createParallelGroup()
                            .addGroup(leftPanelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(leftPanelLayout.createParallelGroup()
                                            .addGroup(leftPanelLayout.createSequentialGroup()
                                                    .addComponent(fishingLocationLabel, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(locationComboBox, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                                            .addGroup(leftPanelLayout.createSequentialGroup()
                                                    .addComponent(fishingOptionLabel, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(typeComboBox, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)))
                                    .addContainerGap())
            );
            leftPanelLayout.setVerticalGroup(
                    leftPanelLayout.createParallelGroup()
                            .addGroup(leftPanelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(fishingLocationLabel, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                                            .addComponent(locationComboBox, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(leftPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(fishingOptionLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(typeComboBox, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(19, Short.MAX_VALUE))
            );
        }

        //======== rightPanel ========
        {
            rightPanel.setBorder(new EtchedBorder());

            //---- powerFishRadioButton ----
            powerFishRadioButton.setText("Powerfish");
            powerFishRadioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    powerFishActionPerformed(e);
                }
            });

            //---- bankRadioButton ----
            bankRadioButton.setText("Bank");
            bankRadioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    bankActionPerformed(e);
                }
            });

            bankRadioButton.setSelected(true);

            //---- bankingOptionsLabel ----
            bankingOptionsLabel.setText("BankingMethod Options");

            //---- stilesRadioButton ----
            stilesRadioButton.setText("Use Stiles (Karamja Only)");
            stilesRadioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stilesActionPerformed(e);
                }
            });
            stilesRadioButton.setEnabled(false);

            //---- f1d1RadioButton ----
            f1d1RadioButton.setText("F1D1");
            f1d1RadioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    f1d1ActionPerformed(e);
                }
            });

            GroupLayout rightPanelLayout = new GroupLayout(rightPanel);
            rightPanel.setLayout(rightPanelLayout);
            rightPanelLayout.setHorizontalGroup(
                    rightPanelLayout.createParallelGroup()
                            .addGroup(rightPanelLayout.createSequentialGroup()
                                    .addGroup(rightPanelLayout.createParallelGroup()
                                            .addGroup(rightPanelLayout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(powerFishRadioButton)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(label3))
                                            .addGroup(rightPanelLayout.createSequentialGroup()
                                                    .addGap(20, 20, 20)
                                                    .addComponent(bankingOptionsLabel))
                                            .addGroup(rightPanelLayout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(stilesRadioButton))
                                            .addGroup(rightPanelLayout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(f1d1RadioButton))
                                            .addGroup(rightPanelLayout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(bankRadioButton)))
                                    .addContainerGap(11, Short.MAX_VALUE))
            );
            rightPanelLayout.setVerticalGroup(
                    rightPanelLayout.createParallelGroup()
                            .addGroup(rightPanelLayout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(bankingOptionsLabel)
                                    .addGroup(rightPanelLayout.createParallelGroup()
                                            .addGroup(rightPanelLayout.createSequentialGroup()
                                                    .addGap(32, 32, 32)
                                                    .addComponent(label3)
                                                    .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(powerFishRadioButton)))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(bankRadioButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(f1d1RadioButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(stilesRadioButton)
                                    .addGap(16, 16, 16))
            );
        }

        //---- button1 ----
        button1.setText("Start");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButtonPressed(e);
            }
        });

        //======== panel1 ========
        {
            panel1.setBorder(new EtchedBorder());

            //---- dropTunaCheckBox ----
            dropTunaCheckBox.setText("Drop Tuna");
            dropTunaCheckBox.setEnabled(false);

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addComponent(dropTunaCheckBox)
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(dropTunaCheckBox, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                    .addContainerGap())
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addComponent(button1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(leftPanel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(panel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rightPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                                .addGap(0, 154, Short.MAX_VALUE)
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(authorLabel))
                                                        .addComponent(headerLabel))
                                                .addGap(162, 162, 162))))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(headerLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(authorLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(leftPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(rightPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addComponent(button1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
    }

    private JLabel headerLabel;
    private JLabel authorLabel;
    private JPanel leftPanel;
    private JLabel fishingLocationLabel;
    private JLabel fishingOptionLabel;
    private JComboBox<String> locationComboBox;
    private JComboBox<String> typeComboBox;
    private JPanel rightPanel;
    private JLabel label3;
    private JRadioButton powerFishRadioButton;
    private JRadioButton bankRadioButton;
    private JLabel bankingOptionsLabel;
    private JRadioButton stilesRadioButton;
    private JRadioButton f1d1RadioButton;
    private JButton button1;
    private JPanel panel1;
    private JCheckBox dropTunaCheckBox;
}
