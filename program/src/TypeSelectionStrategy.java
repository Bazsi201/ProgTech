public class TypeSelectionStrategy implements IselectionStrategy{
    @Override
    public String[] getSelectionArray() {
        return new String[]{
                "Book",
                "Game"};
    }
}
