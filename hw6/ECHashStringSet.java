import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** A set of String values.
 *  @author
 */
class ECHashStringSet implements StringSet {
    int _max;
    double _min;
    int _size;
    LinkedList<String>[] _list;

    public ECHashStringSet() {
        _max = 5;
        _min = 0.2;
        _size = 0;
        int listSize = (int) (1/_min);
        _list = new LinkedList[listSize];
    }
    @Override
    public void put(String s) {
        if (s != null) {
            putHelper(s, s.hashCode());
        }
    }
    public void putHelper(String s, int c) {
        int hash = (c ^ (c >> 31)) - (c >> 31);
        int ind = hash % _list.length;
        double load = (double) _size/ (double) _list.length;
        if (s == null) {
            System.out.println("error");
        }
        else {
            if (load > _max) {
                resize();
            }
            if (_list[ind] == null) {
                _list[ind] = new LinkedList<String>();

            }
            _list[ind].add(s);
            _size += 1;

        }
    }
    public void resize() {
        LinkedList<String>[] original = _list;
        _list = new LinkedList[2*_list.length];
        _size = 0;
        for (LinkedList<String> ele : original) {
            if (ele != null) {
                for (String str : ele) {
                    put(str);
                }
            }
        }

    }
    @Override
    public boolean contains(String s) {
        if (s != null) {
            return containsHelper(s, s.hashCode());
        }
        return false;
    }

    public boolean containsHelper(String s, int c) {
        if (s == null) {
            return false;
        }
        else {
            int hash = (c ^ (c >> 31)) - (c >> 31);
            int ind = hash % _list.length;
            if (_list[ind] == null) {
                return false;

            }
            return _list[ind].contains(s);
        }

        
    }

    @Override
    public List<String> asList() {
        ArrayList<String> list = new ArrayList<>();
        for (LinkedList<String> ele : _list) {
            if (ele != null) {
                list.addAll(ele);
            }
        }
        return list;
    }
}
