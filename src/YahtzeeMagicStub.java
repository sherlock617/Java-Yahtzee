//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import acm.util.ErrorException;

public class YahtzeeMagicStub implements YahtzeeConstants {
    public YahtzeeMagicStub() {
    }


    public static boolean checkCategory(int[] dice, int category) {
        if (dice.length != 5) {
            throw new ErrorException("checkCategory: Illegal number of dice");
        } else {
            int[] counts = new int[7];
            int maxCount = 0;

            int pips;
            for(pips = 0; pips < 5; ++pips) {
                if (dice[pips] < 1 || dice[pips] > 6) {
                    throw new ErrorException("checkCategory: Illegal die value");
                }

                ++counts[dice[pips]];
                maxCount = Math.max(maxCount, counts[dice[pips]]);
            }

            switch(category) {
                case 1:
                    return true;
                case 2:
                    return true;
                case 3:
                    return true;
                case 4:
                    return true;
                case 5:
                    return true;
                case 6:
                    return true;
                case 7:
                case 8:
                default:
                    throw new ErrorException("checkCategory: Illegal category");
                case 9:
                    if (maxCount >= 3) {
                        return true;
                    }

                    return false;
                case 10:
                    if (maxCount >= 4) {
                        return true;
                    }

                    return false;
                case 11:
                    if (maxCount != 3) {
                        return false;
                    } else {
                        for(pips = 1; pips <= 6; ++pips) {
                            if (counts[pips] == 2) {
                                return true;
                            }
                        }

                        return false;
                    }
                case 12:
                    if (counts[1] > 0 && counts[2] > 0 && counts[3] > 0 && counts[4] > 0) {
                        return true;
                    } else if (counts[2] > 0 && counts[3] > 0 && counts[4] > 0 && counts[5] > 0) {
                        return true;
                    } else {
                        if (counts[3] > 0 && counts[4] > 0 && counts[5] > 0 && counts[6] > 0) {
                            return true;
                        }

                        return false;
                    }
                case 13:
                    if (counts[1] > 0 && counts[2] > 0 && counts[3] > 0 && counts[4] > 0 && counts[5] > 0) {
                        return true;
                    } else {
                        if (counts[2] > 0 && counts[3] > 0 && counts[4] > 0 && counts[5] > 0 && counts[6] > 0) {
                            return true;
                        }

                        return false;
                    }
                case 14:
                    if (maxCount == 5) {
                        return true;
                    }

                    return false;
                case 15:
                    return true;
            }
        }
    }
}

