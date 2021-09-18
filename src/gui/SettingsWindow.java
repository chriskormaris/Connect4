package gui;


import enumeration.AiType;
import enumeration.Color;
import enumeration.GameMode;
import enumeration.GuiStyle;
import utility.GameParameters;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsWindow extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 6651737783332653136L;

    private final JComboBox<String> gui_style_drop_down;
    private final JComboBox<String> game_mode_drop_down;
    private final JComboBox<String> ai_type_drop_down;
    private final JComboBox<Integer> max_depth1_drop_down;
    private final JComboBox<Integer> max_depth2_drop_down;
    private final JComboBox<String> player1_color_drop_down;
    private final JComboBox<String> player2_color_drop_down;
    private final JComboBox<Integer> checkers_in_a_row_drop_down;

    private final JButton apply;
    private final JButton cancel;

    public static int width = 450;
    public static int height = 410;


    public SettingsWindow() {
        super("Settings");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);

        EventHandler handler = new EventHandler();

        GuiStyle selectedGuiStyle = GUI.gameParameters.getGuiStyle();
        GameMode selectedMode = GUI.gameParameters.getGameMode();
        AiType selectedAiType = GUI.gameParameters.getAiType();
        int maxDepth1 = GUI.gameParameters.getAi1MaxDepth() - 1;
        int maxDepth2 = GUI.gameParameters.getAi2MaxDepth() - 1;
        Color selectedPlayer1Color = GUI.gameParameters.getPlayer1Color();
        Color selectedPlayer2Color = GUI.gameParameters.getPlayer2Color();
        int inARow = GUI.gameParameters.getCheckersInARow();

        JLabel guiStyleLabel = new JLabel("GUI style");
        JLabel gameModeLabel = new JLabel("Game mode");
        JLabel aiTypeLabel = new JLabel("AI type");
        JLabel maxDepth1Label = new JLabel("AI 1 depth");
        JLabel maxDepth2Label = new JLabel("AI 2 depth (AiVsAi)");
        JLabel player1ColorLabel = new JLabel("Player 1 color");
        JLabel player2ColorLabel = new JLabel("Player 2 color");
        JLabel inARowLabel = new JLabel("Checkers in a Row");

        add(guiStyleLabel);
        add(gameModeLabel);
        add(aiTypeLabel);
        add(maxDepth1Label);
        add(maxDepth2Label);
        add(player1ColorLabel);
        add(player2ColorLabel);
        add(inARowLabel);

        gui_style_drop_down = new JComboBox<>();
        gui_style_drop_down.addItem("System style");
        gui_style_drop_down.addItem("Cross-platform style");
        gui_style_drop_down.addItem("Nimbus style");

        if (selectedGuiStyle == GuiStyle.SYSTEM_STYLE) {
            gui_style_drop_down.setSelectedIndex(0);
        } else if (selectedGuiStyle == GuiStyle.CROSS_PLATFORM_STYLE) {
            gui_style_drop_down.setSelectedIndex(1);
        } else if (selectedGuiStyle == GuiStyle.NIMBUS_STYLE) {
            gui_style_drop_down.setSelectedIndex(2);
        }

        game_mode_drop_down = new JComboBox<>();
        game_mode_drop_down.addItem("Human Vs AI");
        game_mode_drop_down.addItem("Human Vs Human");
        game_mode_drop_down.addItem("AI Vs AI");

        if (selectedMode == GameMode.HUMAN_VS_AI) {
            game_mode_drop_down.setSelectedIndex(0);
        } else if (selectedMode == GameMode.HUMAN_VS_HUMAN) {
            game_mode_drop_down.setSelectedIndex(1);
        } else if (selectedMode == GameMode.AI_VS_AI) {
            game_mode_drop_down.setSelectedIndex(2);
        }

        ai_type_drop_down = new JComboBox<>();
        ai_type_drop_down.addItem("Minimax AI");
        ai_type_drop_down.addItem("Random AI");

        if (selectedAiType == AiType.MINIMAX_AI) {
            ai_type_drop_down.setSelectedIndex(0);
        } else if (selectedAiType == AiType.RANDOM_AI) {
            ai_type_drop_down.setSelectedIndex(1);
        }

        max_depth1_drop_down = new JComboBox<>();
        max_depth1_drop_down.addItem(1);
        max_depth1_drop_down.addItem(2);
        max_depth1_drop_down.addItem(3);
        max_depth1_drop_down.addItem(4);
        max_depth1_drop_down.addItem(5);
        max_depth1_drop_down.addItem(6);
        max_depth1_drop_down.addItem(7);

        max_depth2_drop_down = new JComboBox<>();
        max_depth2_drop_down.addItem(1);
        max_depth2_drop_down.addItem(2);
        max_depth2_drop_down.addItem(3);
        max_depth2_drop_down.addItem(4);
        max_depth2_drop_down.addItem(5);
        max_depth2_drop_down.addItem(6);
        max_depth2_drop_down.addItem(7);

        max_depth1_drop_down.setSelectedIndex(maxDepth1);
        max_depth2_drop_down.setSelectedIndex(maxDepth2);

        player1_color_drop_down = new JComboBox<>();
        player1_color_drop_down.addItem("Red");
        player1_color_drop_down.addItem("Yellow");
        player1_color_drop_down.addItem("Black");
        player1_color_drop_down.addItem("Green");
        player1_color_drop_down.addItem("Orange");
        player1_color_drop_down.addItem("Purple");

        if (selectedPlayer1Color == Color.RED) {
            player1_color_drop_down.setSelectedIndex(0);
        } else if (selectedPlayer1Color == Color.YELLOW) {
            player1_color_drop_down.setSelectedIndex(1);
        } else if (selectedPlayer1Color == Color.BLACK) {
            player1_color_drop_down.setSelectedIndex(2);
        } else if (selectedPlayer1Color == Color.GREEN) {
            player1_color_drop_down.setSelectedIndex(3);
        } else if (selectedPlayer1Color == Color.ORANGE) {
            player1_color_drop_down.setSelectedIndex(4);
        } else if (selectedPlayer1Color == Color.PURPLE) {
            player1_color_drop_down.setSelectedIndex(5);
        }

        player2_color_drop_down = new JComboBox<>();
        player2_color_drop_down.addItem("Red");
        player2_color_drop_down.addItem("Yellow");
        player2_color_drop_down.addItem("Black");
        player2_color_drop_down.addItem("Green");
        player2_color_drop_down.addItem("Orange");
        player2_color_drop_down.addItem("Purple");

        if (selectedPlayer2Color == Color.RED) {
            player2_color_drop_down.setSelectedIndex(0);
        } else if (selectedPlayer2Color == Color.YELLOW) {
            player2_color_drop_down.setSelectedIndex(1);
        } else if (selectedPlayer2Color == Color.BLACK) {
            player2_color_drop_down.setSelectedIndex(2);
        } else if (selectedPlayer2Color == Color.GREEN) {
            player2_color_drop_down.setSelectedIndex(3);
        } else if (selectedPlayer2Color == Color.ORANGE) {
            player2_color_drop_down.setSelectedIndex(4);
        } else if (selectedPlayer2Color == Color.PURPLE) {
            player2_color_drop_down.setSelectedIndex(5);
        }

        checkers_in_a_row_drop_down = new JComboBox<>();
        checkers_in_a_row_drop_down.addItem(4);
        checkers_in_a_row_drop_down.addItem(5);

        checkers_in_a_row_drop_down.setSelectedIndex(inARow - 4);

        add(gui_style_drop_down);
        add(game_mode_drop_down);
        add(ai_type_drop_down);
        add(max_depth1_drop_down);
        add(max_depth2_drop_down);
        add(player1_color_drop_down);
        add(player2_color_drop_down);
        add(checkers_in_a_row_drop_down);

        guiStyleLabel.setBounds(25, 25, 200, 25);
        gameModeLabel.setBounds(25, 60, 200, 25);
        aiTypeLabel.setBounds(25, 95, 200, 25);
        maxDepth1Label.setBounds(25, 130, 200, 25);
        maxDepth2Label.setBounds(25, 165, 200, 25);
        player1ColorLabel.setBounds(25, 200, 200, 25);
        player2ColorLabel.setBounds(25, 235, 200, 25);
        inARowLabel.setBounds(25, 270, 200, 25);

        gui_style_drop_down.setBounds(195, 25, 200, 25);
        game_mode_drop_down.setBounds(195, 60, 200, 25);
        ai_type_drop_down.setBounds(195, 95, 200, 25);
        max_depth1_drop_down.setBounds(195, 130, 200, 25);
        max_depth2_drop_down.setBounds(195, 165, 200, 25);
        player1_color_drop_down.setBounds(195, 200, 200, 25);
        player2_color_drop_down.setBounds(195, 235, 200, 25);
        checkers_in_a_row_drop_down.setBounds(195, 270, 200, 25);

        apply = new JButton("Apply");
        cancel = new JButton("Cancel");
        add(apply);
        add(cancel);

        int distance = 10;
        apply.setBounds((width / 2) - 110 - (distance / 2), 320, 100, 30);
        apply.addActionListener(handler);
        cancel.setBounds((width / 2) - 10 + (distance / 2), 320, 100, 30);
        cancel.addActionListener(handler);
    }


    private class EventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {

            if (ev.getSource() == cancel) {
                dispose();
            } else if (ev.getSource() == apply) {
                try {

                    GuiStyle guiStyle =
                            GuiStyle.valueOf(gui_style_drop_down.getSelectedItem().toString().toUpperCase().replace("-", "_").replace(" ", "_"));
                    GameMode gameMode =
                            GameMode.valueOf(game_mode_drop_down.getSelectedItem().toString().toUpperCase().replace(" ", "_"));
                    AiType aiType =
                            AiType.valueOf(ai_type_drop_down.getSelectedItem().toString().toUpperCase().replace(" ", "_"));
                    int maxDepth1 = (int) max_depth1_drop_down.getSelectedItem();
                    int maxDepth2 = (int) max_depth2_drop_down.getSelectedItem();
                    Color player1Color =
                            Color.valueOf(player1_color_drop_down.getSelectedItem().toString().toUpperCase());
                    Color player2Color =
                            Color.valueOf(player2_color_drop_down.getSelectedItem().toString().toUpperCase());
                    int checkersInARow = (int) checkers_in_a_row_drop_down.getSelectedItem();
                    int numOfRows = (checkersInARow == 4) ? 6 : 7;
                    int numOfColumns = (checkersInARow == 4) ? 7 : 8;

                    if (player1Color == player2Color) {
                        JOptionPane.showMessageDialog(null,
                                "Player 1 and Player 2 cannot have the same color of checkers!",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Change game parameters based on settings.
                    GUI.newGameParameters = new GameParameters(guiStyle, gameMode, aiType, maxDepth1, maxDepth2,
                            player1Color, player2Color, numOfRows, numOfColumns, checkersInARow);

                    JOptionPane.showMessageDialog(null,
                            "Game settings have been changed.\nThe changes will be applied in the next new game.",
                            "Notice", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (Exception e) {
                    System.err.println("ERROR : " + e.getMessage());
                }

            }  // else if.

        }  // action performed.

    }  // inner class.


}  // class end.
