/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
public class ColumnMatchFilter extends TableFilter {
    private String col1;
    private String col2;
    private Table table;

    public ColumnMatchFilter(Table input, String colName1, String colName2) {
        super(input);
        col1 = colName1;
        col2 = colName2;
        table = input;
    }

    @Override
    protected boolean keep() {
        return _next.getValue(table.colNameToIndex(col1)).equals(_next.getValue(table.colNameToIndex(col2)));
    }

    // FIXME: Add instance variables?
}
