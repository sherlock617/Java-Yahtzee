import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Yahtzee extends GraphicsProgram implements YahtzeeConstants{
    public static void main(String[] args){
        new Yahtzee().start(args);
    }
    public void run() {
        IODialog dialog = getDialog();
        nPlayers = dialog.readInt("Enter number of players");
        playerNames = new String[nPlayers];
        for(int i = 1; i <= nPlayers; i++){
            playerNames[i - 1] = dialog.readLine("Enter name for player" + i);
        }
        display = new YahtzeeDisplay(getGCanvas(), playerNames);
        playGame();
    }

    private void playGame() {
        dice = new int[N_DICE];
        int player = 1;
        int[] t_score = new int[nPlayers];
        int[] l_score = new int[nPlayers];
        int[] u_score = new int[nPlayers];
        int[] start = new int[nPlayers];
        Category = new int[nPlayers][LOWER_SCORE];
        for(int i = 0; i < LOWER_SCORE; i++){Category[player - 1][i] = 0;}
        int category = 0;
        if(nPlayers > 1) {
            for (int i = 0; i < nPlayers; i++) {
                display.waitForPlayerToClickRoll(i + 1);
                for (int j = 0; j < N_DICE; j++) {
                    dice[i] = rgen.nextInt(1, 6);
                    start[i] += dice[i];
                }
                display.displayDice(dice);
                display.HighLight(i + 1);
            }
            max = 0;
            for (int i = 0; i < nPlayers; i++) {
                if (start[i] > max) {
                    max = start[i];
                    starter = i + 1;
                }
            }
            player = starter;
            display.HighLight(player);
        }
        while (u_sum + l_sum < LARGE_STRAIGHT * nPlayers) {
            if (player > nPlayers) {
                player = 1;
            }
            display.printMessage(playerNames[player - 1] +" 's turn! Click\"Roll Dice\" button to roll the dice.\n");
            display.waitForPlayerToClickRoll(player);

            for (int i = 0; i < N_DICE; i++) {
                dice[i] = rgen.nextInt(1, 6);
            }
            display.displayDice(dice);
            display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\".\n");
            for (int j = 0; j < 2; j++) {
                display.waitForPlayerToSelectDice();
                for (int i = 0; i < N_DICE; i++) {
                    if (display.isDieSelected(i)) {
                        dice[i] = rgen.nextInt(1, 6);
                    }
                }
                display.displayDice(dice);
                display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\".\n");
            }
            display.printMessage("Select a category for this roll.\n");
            category = display.waitForPlayerToSelectCategory();
            while(Category[player - 1][category] == 1) {
                display.printMessage("Select a category for this roll.\n");
                category = display.waitForPlayerToSelectCategory();
            }
            Category[player - 1][category] = 1;
            boolean p = YahtzeeMagicStub.checkCategory(dice, category);
            switch (category) {
                case ONES:
                    for(int i = 0; i < N_DICE; i++){
                        if(dice[i] == 1)
                            score += dice[i];
                    }
                    if(p) {
                        u_score[player - 1] += score;
                        t_score[player - 1] += score;
                        u_sum += Category[player - 1][category];
                    }
                    break;
                case TWOS:
                    for(int i = 0; i < N_DICE; i++){
                        if(dice[i] == 2)
                            score += dice[i];
                    }
                    if(p) {
                        u_score[player - 1] += score;
                        t_score[player - 1] += score;
                        u_sum += Category[player - 1][category];
                    }
                    break;
                case THERES:
                    for(int i = 0; i < N_DICE; i++){
                        if(dice[i] == 3)
                            score += dice[i];
                    }
                    if(p) {
                        u_score[player - 1] += score;
                        t_score[player - 1] += score;
                        u_sum += Category[player - 1][category];
                    }
                    break;
                case FOURS:
                    for(int i = 0; i < N_DICE; i++){
                        if(dice[i] == 4)
                            score += dice[i];
                    }
                    if(p) {
                        u_score[player - 1] += score;
                        t_score[player - 1] += score;
                        u_sum += Category[player - 1][category];
                    }
                    break;
                case FIVES:
                    for(int i = 0; i < N_DICE; i++){
                        if(dice[i] == 5)
                            score += dice[i];
                    }
                    if(p) {
                        u_score[player - 1] += score;
                        t_score[player - 1] += score;
                        u_sum += Category[player - 1][category];
                    }
                    break;
                case SIXES:
                    for(int i = 0; i < N_DICE; i++){
                        if(dice[i] == 6)
                            score += dice[i];
                    }
                    if(p) {
                        u_score[player - 1] += score;
                        t_score[player - 1] += score;
                        u_sum += Category[player - 1][category];
                    }
                        break;
                case THERE_OF_A_KIND:
                case FOUR_OF_A_KIND:
                case CHANCE:
                    score = 0;
                    for (int i = 0; i < N_DICE; i++) {
                        score += dice[i];
                    }
                    if(p) {
                        l_score[player - 1] += score;
                        t_score[player - 1] += score;
                    }
                    l_sum += Category[player - 1][category];
                    break;
                case FULL_HOUSE:
                    score = 25;
                    if(p) {
                        l_score[player - 1] += score;
                        t_score[player - 1] += score;
                    }
                    l_sum += Category[player - 1][category];
                    break;
                case SMALL_STRAIGHT:
                    score = 30;
                    if(p) {
                        l_score[player - 1] += score;
                        t_score[player - 1] += score;
                    }
                    l_sum += Category[player - 1][category];
                    break;
                case LARGE_STRAIGHT:
                    score = 40;
                    if(p) {
                        l_score[player - 1] += score;
                        t_score[player - 1] += score;
                    }
                    l_sum += Category[player - 1][category];
                    break;
                case YAHTZEE:
                    score = 50;
                    if(p) {
                        l_score[player - 1] += score;
                        t_score[player - 1] += score;
                    }
                    l_sum += Category[player - 1][category];
                    break;
                default:
                    break;
            }
            if(p) {
                display.updateScorecard(category, player, score);
                display.updateScorecard(TOTAL, player, t_score[player - 1]);
            }else{
                score = 0;
                display.updateScorecard(category, player, score);
            }
            score = 0;
            player += 1;
        }
        if(u_sum == 6 * nPlayers){
            for(int i = 0; i < nPlayers; i++) {
                display.updateScorecard(UPPER_SCORE, i + 1, u_score[i]);
                if (u_score[i] >= BONUS_TOTAL) {
                    score = BONUS_SCORE;
                    display.updateScorecard(UPPER_BONUS, i + 1, score);
                    t_score[i] += score;
                    display.updateScorecard(TOTAL, i + 1, score);
                } else {
                    display.updateScorecard(UPPER_BONUS, i + 1, 0);
                }
            }
        }
        for(int i = 0; i < nPlayers; i++) {
            if (l_sum == 7 * nPlayers) {
                display.updateScorecard(LOWER_SCORE, i + 1, l_score[i]);
            }
        }
        max = 0;
        for(int i = 1; i <= nPlayers; i++){
            if (t_score[i - 1] > max) {
                max = t_score[i - 1];
                winner = i;
            }
        }
        display.printMessage(playerNames[winner - 1] +"is winner!");
    }


    int[] dice;
    private int max;
    private int winner;
    private int starter;
    private int nPlayers;
    private int score;
    private int u_sum = 0;
    private int l_sum = 0;
    private int[][] Category;
    private String[] playerNames;
    private YahtzeeDisplay display;
    private RandomGenerator rgen = new RandomGenerator();
}
