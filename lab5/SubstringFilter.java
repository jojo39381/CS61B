/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {
    private String column;
    private String sub;
    private Table table;
    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        column = colName;
        sub = subStr;
        table = input;
    }

    @Override
    protected boolean keep() {
        return _next.getValue(table.colNameToIndex(column)).contains(sub);

    }

    // FIXME: Add instance variables?
}
