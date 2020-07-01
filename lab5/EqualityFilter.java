/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {
    private String column;
    private String equal;
    private Table table;
    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        column = colName;
        equal = match;
        table = input;

    }

    @Override
    protected boolean keep() {
        return _next.getValue(table.colNameToIndex(column)).equals(equal);

    }

    // FIXME: Add instance variables?
}
