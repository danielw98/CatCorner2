


public class LevelsAttributes
{

    private Byte[] levelSize;
    private Byte[] levelCats;

    public LevelsAttributes()
    {
        initializeLevelSize();
        initializeLevelCats();
    }

    void initializeLevelSize() {

        levelSize = new Byte[21];
        levelSize[0] = 0;

        levelSize[1] = 5;
        levelSize[2] = 6;
        levelSize[3] = 7;
        levelSize[4] = 7;

        levelSize[5] = 8;
        levelSize[6] = 8;
        levelSize[7] = 8;
        levelSize[8] = 9;

        levelSize[9] = 9;
        levelSize[10] = 9;
        levelSize[11] = 10;
        levelSize[12] = 10;

        levelSize[13] = 10;
        levelSize[14] = 10;
        levelSize[15] = 10;
        levelSize[16] = 10;

        levelSize[17] = 10;
        levelSize[18] = 10;
        levelSize[19] = 10;
        levelSize[20] = 10;

    }

    void initializeLevelCats() {
        levelCats = new Byte[21];
        levelCats[0] = 0;

        levelCats[1] = 2;
        levelCats[2] = 2;
        levelCats[3] = 2;
        levelCats[4] = 3;

        levelCats[5] = 2;
        levelCats[6] = 3;
        levelCats[7] = 4;
        levelCats[8] = 3;

        levelCats[9] = 4;
        levelCats[10] = 5;
        levelCats[11] = 4;
        levelCats[12] = 5;

        levelCats[13] = 5;
        levelCats[14] = 5;
        levelCats[15] = 5;
        levelCats[16] = 5;

        levelCats[17] = 5;
        levelCats[18] = 5;
        levelCats[19] = 5;
        levelCats[20] = 5;

    }




    Byte getLevelSize(int level)
    {
        return levelSize[level];
    }

    Byte getLevelCats(int level)
    {
        return levelCats[level];
    }
}
