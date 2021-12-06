//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.util.ErrorException;
import acm.util.JTFTools;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class YahtzeeDisplay implements YahtzeeDisplayConstants, ActionListener, MouseListener{
    private static final int WAIT_FOR_ROLL = 1;
    private static final int WAIT_FOR_REROLL = 2;
    private static final int WAIT_FOR_CATEGORY = 3;
    private GDie[] diceArray;
    private GScorecardEntry[][] scorecard;
    private GCanvas canvas;
    private GButton rollButton;
    private GLabel messageArea;
    private int mode;
    private int selectedCategory;
    private boolean mouseDown;

    public YahtzeeDisplay(GCanvas gc, String[] playerNames) {
        this.canvas = gc;
        this.canvas.setBackground(YahtzeeDisplayConstants.BACKGROUND_COLOR);
        if (playerNames.length > 4) {
            throw new ErrorException("Too many players");
        } else {
            this.createButton();
            this.createDice();
            this.createScorecard(playerNames);
            this.createMessageArea();
        }
    }

    public void waitForPlayerToClickRoll(int player) {
        if (player >= 1 && player < this.scorecard.length) {
            this.mode = 1;
            this.rollButton.setLabel("Roll Dice");
            this.rollButton.setEnabled(true);
            this.scorecard[player][0].setHighlighted(true);
            for(int i = 0; i < 5; ++i) {
                this.diceArray[i].set(0);
            }

            try {
                synchronized(this) {
                    this.wait();
                }
            } catch (InterruptedException var4) {
            }/*finally {
                *//*this.scorecard[player][0].setHighlighted(false);
                if(player == this.scorecard.length - 1)
                    player = 1;
                this.scorecard[player + 1][0].setHighlighted(true);*//*
            }*/

            this.messageArea.setLabel("");
        } else {
            throw new ErrorException("waitForPlayerToClickRoll: Illegal player number " + player);
        }
    }

    public void displayDice(int[] dice) {
        this.messageArea.setLabel("");
        if (dice.length != 5) {
            throw new ErrorException("Dice array has incorrect length");
        } else {
            for(int i = 0; i < 5; ++i) {
                this.diceArray[i].set(dice[i]);
                this.diceArray[i].setHighlighted(false);
                JTFTools.pause(100.0D);
            }
        }
    }

    public void waitForPlayerToSelectDice() {
        this.mode = 2;
        this.rollButton.setLabel("Roll again");
        this.rollButton.setEnabled(true);

        try {
            synchronized(this) {
                this.wait();
            }
        } catch (InterruptedException var3) {
        }

        this.messageArea.setLabel("");
    }

    public boolean isDieSelected(int index) {
        if (index >= 0 && index < 5) {
            return this.diceArray[index].isHighlighted();
        } else {
            throw new ErrorException("isDieSelected: Index is out of range");
        }
    }

    public int waitForPlayerToSelectCategory() {
        this.mode = 3;
        this.rollButton.setLabel("Roll Dice");
        this.rollButton.setEnabled(false);

        try {
            synchronized(this) {
                this.wait();
            }
        } catch (InterruptedException var3) {
        }

        this.messageArea.setLabel("");
        return this.selectedCategory;
    }

    public void updateScorecard(int category, int player, int score) {
        this.messageArea.setLabel("");
        if (category >= 1 && category <= 17) {
            if (player >= 1 && player < this.scorecard.length) {
                this.scorecard[player][0].setHighlighted(false);
                this.scorecard[player][category].setLabel("" + score);
            } else {
                throw new ErrorException("updateScorecard: Illegal player number " + player);
            }
        } else {
            throw new ErrorException("updateScorecard: Illegal category");
        }
    }

    public void printMessage(String message) {
        this.messageArea.setLabel(message);
    }

    public void actionPerformed(ActionEvent e) {
        if (this.mode == 1 || this.mode == 2) {
            synchronized(this) {
                this.notifyAll();
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        if (this.mode == 2 && e.getSource() instanceof GDie) {
            GDie die = (GDie)e.getSource();
            die.setHighlighted(!die.isHighlighted());
        }

    }

    public void mousePressed(MouseEvent e) {
        if (this.mode == 3 && e.getSource() instanceof GScorecardEntry) {
            GScorecardEntry entry = (GScorecardEntry)e.getSource();
            int category = entry.getCategory();
            if (this.isSelectableCategory(category)) {
                for(int i = 0; i < this.scorecard.length; ++i) {
                    this.scorecard[i][category].setHighlighted(true);
                }

                this.mouseDown = true;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (this.mouseDown) {
            this.mouseDown = false;
            if (this.mode == 3 && e.getSource() instanceof GScorecardEntry) {
                GScorecardEntry entry = (GScorecardEntry)e.getSource();
                if (entry.isHighlighted()) {
                    int category = entry.getCategory();

                    for(int i = 0; i < this.scorecard.length; ++i) {
                        this.scorecard[i][category].setHighlighted(false);
                    }

                    this.selectedCategory = category;
                    synchronized(this) {
                        this.notifyAll();
                    }
                }
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
        if (this.mouseDown) {
            if (this.mode == 3 && e.getSource() instanceof GScorecardEntry) {
                GScorecardEntry entry = (GScorecardEntry)e.getSource();
                int category = entry.getCategory();
                if (this.isSelectableCategory(category)) {
                    for(int i = 0; i < this.scorecard.length; ++i) {
                        this.scorecard[i][category].setHighlighted(true);
                    }

                }
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        if (this.mouseDown) {
            if (this.mode == 3 && e.getSource() instanceof GScorecardEntry) {
                GScorecardEntry entry = (GScorecardEntry)e.getSource();
                int category = entry.getCategory();
                if (this.isSelectableCategory(category)) {
                    for(int i = 0; i < this.scorecard.length; ++i) {
                        this.scorecard[i][category].setHighlighted(false);
                    }

                }
            }
        }
    }
    public void HighLight(int player){
        this.scorecard[player][0].setHighlighted(false);
    }

    private void createButton() {
        this.rollButton = new GButton("Roll Dice");
        this.rollButton.addActionListener(this);
        this.canvas.add(this.rollButton, 12.0D, 10.0D);
    }

    private void createDice() {
        this.diceArray = new GDie[5];

        for(int i = 0; i < 5; ++i) {
            this.diceArray[i] = new GDie();
            this.diceArray[i].addMouseListener(this);
            this.canvas.add(this.diceArray[i], 33.0D, (double)(40 + i * 50));
        }

    }

    private void createScorecard(String[] playerNames) {
        this.scorecard = new GScorecardEntry[playerNames.length + 1][18];
        double x = 110.0D;
        double y = 10.0D;
        GScorecardEntry entry = new GScorecardEntry(140.0D, 20.0D, 0);
        entry.setLabel("Category");
        this.canvas.add(entry, x, y);
        y += 20.0D;

        int i;
        for(i = 1; i <= 17; ++i) {
            entry = new GScorecardEntry(140.0D, 15.0D, i);
            if (this.isSelectableCategory(i)) {
                entry.setAlignment(1);
                entry.setTextColor(YahtzeeDisplayConstants.CATEGORY_TEXT);
                entry.addMouseListener(this);
            } else {
                entry.setAlignment(0);
                entry.setTextColor(YahtzeeDisplayConstants.FIXED_CATEGORY_TEXT);
            }

            entry.setFont(YahtzeeDisplayConstants.CATEGORY_FONT);
            entry.setHighlightColor(YahtzeeDisplayConstants.CATEGORY_TEXT);
            entry.setBackgroundColor(YahtzeeDisplayConstants.CATEGORY_BACKGROUND);
            entry.setLabel(this.categoryName(i));
            this.scorecard[0][i] = entry;
            this.canvas.add(entry, x, y);
            y += 15.0D;
        }

        x = 251.0D;

        for(i = 1; i <= playerNames.length; ++i) {
            y = 10.0D;
            entry = new GScorecardEntry(65.0D, 20.0D, 0);
            entry.setLabel(playerNames[i - 1]);
            this.scorecard[i][0] = entry;
            this.canvas.add(entry, x, y);
            y += 20.0D;

            for(int c = 1; c <= 17; ++c) {
                entry = new GScorecardEntry(65.0D, 15.0D, c);
                entry.setAlignment(3);
                if (this.isSelectableCategory(c)) {
                    entry.setHighlightColor(YahtzeeDisplayConstants.CATEGORY_TEXT);
                    entry.setFont(YahtzeeDisplayConstants.SCORE_FONT);
                    entry.addMouseListener(this);
                } else {
                    entry.setFont(YahtzeeDisplayConstants.FIXED_SCORE_FONT);
                }

                this.scorecard[i][c] = entry;
                this.canvas.add(entry, x, y);
                y += 15.0D;
            }

            x += 65.0D;
        }

    }

    private void createMessageArea() {
        this.messageArea = new GLabel("");
        this.messageArea.setColor(Color.white);
        this.messageArea.setFont(YahtzeeDisplayConstants.MESSAGE_FONT);
        this.canvas.add(this.messageArea, 33.0D, 315.0D);
    }

    private String categoryName(int category) {
        switch(category) {
            case 1:
                return "Ones";
            case 2:
                return "Twos";
            case 3:
                return "Threes";
            case 4:
                return "Fours";
            case 5:
                return "Fives";
            case 6:
                return "Sixes";
            case 7:
                return "Upper Score";
            case 8:
                return "Upper Bonus (35)";
            case 9:
                return "Three of a Kind";
            case 10:
                return "Four of a Kind";
            case 11:
                return "Full House (25)";
            case 12:
                return "Small Straight (30)";
            case 13:
                return "Large Straight (40)";
            case 14:
                return "Yahtzee! (50)";
            case 15:
                return "Chance";
            case 16:
                return "Lower Score";
            case 17:
                return "TOTAL";
            default:
                throw new ErrorException("Illegal category");
        }
    }

    private boolean isSelectableCategory(int category) {
        return category >= 1 && category <= 6 || category >= 9 && category <= 15;
    }
}

