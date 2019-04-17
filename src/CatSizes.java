// de facut map
public enum CatSizes {

    VERY_BIG("VERY_BIG", 1),
    BIG("BIG", 2),
    MEDIUM("MEDIUM", 3),
    SMALL("SMALL", 4),
    VERY_SMALL("VERY_SMALL", 5),
    SMALLEST("SMALLEST", 6);

    private final String text;
    private final int number;

    CatSizes(String text, int number)
    {
        this.text = text;
        this.number = number;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public int toNumber(){
        return this.number;
    }
}
