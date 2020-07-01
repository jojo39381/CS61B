/**
 * TableFilter to filter for entries greater than a given string.
 *
 * @author Matthew Owen
 */
public class GreaterThanFilter extends TableFilter {
    private String column;
    private String comparison;
    private Table table;
    public GreaterThanFilter(Table input, String colName, String ref) {
        super(input);
        column = colName;
        comparison = ref;
        table = input;
    }

    @Override
    protected boolean keep() {
        return _next.getValue(table.colNameToIndex(column)).compareTo(comparison) > 0;

    }

    // FIXME: Add instance variables?
}
