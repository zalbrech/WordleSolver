package com.company;

import java.util.HashMap;
import java.util.Map;

// Node to store board states and guesses
public class Guess {
    private String word;
    private Map<String, Guess> infoMap;

    private String[] boards;

    public Guess() {
        initBoards();
//        for(String s : boards) System.out.println(s);
    }

    public Guess(String theWord) {
        this.word = theWord;
        infoMap = new HashMap<>();
        initBoards();
//        for(String s : boards) System.out.println(s);
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setInfoMap(Map<String, Guess> infoMap) {
        this.infoMap = infoMap;
    }

    private void initBoards() {
        this.boards = new String[] {
                "BBBBB", "BBBBY", "BBBBG", "BBBYB", "BBBYY",
                "BBBYG", "BBBGB", "BBBGY", "BBBGG", "BBYBB",
                "BBYBY", "BBYBG", "BBYYB", "BBYYY", "BBYYG",
                "BBYGB", "BBYGY", "BBYGG", "BBGBB", "BBGBY",
                "BBGBG", "BBGYB", "BBGYY", "BBGYG", "BBGGB",
                "BBGGY", "BBGGG", "BYBBB", "BYBBY", "BYBBG",
                "BYBYB", "BYBYY", "BYBYG", "BYBGB", "BYBGY",
                "BYBGG", "BYYBB", "BYYBY", "BYYBG", "BYYYB",
                "BYYYY", "BYYYG", "BYYGB", "BYYGY", "BYYGG",
                "BYGBB", "BYGBY", "BYGBG", "BYGYB", "BYGYY",
                "BYGYG", "BYGGB", "BYGGY", "BYGGG", "BGBBB",
                "BGBBY", "BGBBG", "BGBYB", "BGBYY", "BGBYG",
                "BGBGB", "BGBGY", "BGBGG", "BGYBB", "BGYBY",
                "BGYBG", "BGYYB", "BGYYY", "BGYYG", "BGYGB",
                "BGYGY", "BGYGG", "BGGBB", "BGGBY", "BGGBG",
                "BGGYB", "BGGYY", "BGGYG", "BGGGB", "BGGGY",
                "BGGGG", "YBBBB", "YBBBY", "YBBBG", "YBBYB",
                "YBBYY", "YBBYG", "YBBGB", "YBBGY", "YBBGG",
                "YBYBB", "YBYBY", "YBYBG", "YBYYB", "YBYYY",
                "YBYYG", "YBYGB", "YBYGY", "YBYGG", "YBGBB",
                "YBGBY", "YBGBG", "YBGYB", "YBGYY", "YBGYG",
                "YBGGB", "YBGGY", "YBGGG", "YYBBB", "YYBBY",
                "YYBBG", "YYBYB", "YYBYY", "YYBYG", "YYBGB",
                "YYBGY", "YYBGG", "YYYBB", "YYYBY", "YYYBG",
                "YYYYB", "YYYYY", "YYYYG", "YYYGB", "YYYGY",
                "YYYGG", "YYGBB", "YYGBY", "YYGBG", "YYGYB",
                "YYGYY", "YYGYG", "YYGGB", "YYGGY", "YYGGG",
                "YGBBB", "YGBBY", "YGBBG", "YGBYB", "YGBYY",
                "YGBYG", "YGBGB", "YGBGY", "YGBGG", "YGYBB",
                "YGYBY", "YGYBG", "YGYYB", "YGYYY", "YGYYG",
                "YGYGB", "YGYGY", "YGYGG", "YGGBB", "YGGBY",
                "YGGBG", "YGGYB", "YGGYY", "YGGYG", "YGGGB",
                "YGGGY", "YGGGG", "GBBBB", "GBBBY", "GBBBG",
                "GBBYB", "GBBYY", "GBBYG", "GBBGB", "GBBGY",
                "GBBGG", "GBYBB", "GBYBY", "GBYBG", "GBYYB",
                "GBYYY", "GBYYG", "GBYGB", "GBYGY", "GBYGG",
                "GBGBB", "GBGBY", "GBGBG", "GBGYB", "GBGYY",
                "GBGYG", "GBGGB", "GBGGY", "GBGGG", "GYBBB",
                "GYBBY", "GYBBG", "GYBYB", "GYBYY", "GYBYG",
                "GYBGB", "GYBGY", "GYBGG", "GYYBB", "GYYBY",
                "GYYBG", "GYYYB", "GYYYY", "GYYYG", "GYYGB",
                "GYYGY", "GYYGG", "GYGBB", "GYGBY", "GYGBG",
                "GYGYB", "GYGYY", "GYGYG", "GYGGB", "GYGGY",
                "GYGGG", "GGBBB", "GGBBY", "GGBBG", "GGBYB",
                "GGBYY", "GGBYG", "GGBGB", "GGBGY", "GGBGG",
                "GGYBB", "GGYBY", "GGYBG", "GGYYB", "GGYYY",
                "GGYYG", "GGYGB", "GGYGY", "GGYGG", "GGGBB",
                "GGGBY", "GGGBG", "GGGYB", "GGGYY", "GGGYG",
                "GGGGB", "GGGGY", "GGGGG"
        };
    }


}
