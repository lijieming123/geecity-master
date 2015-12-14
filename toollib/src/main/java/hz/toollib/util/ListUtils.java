package hz.toollib.util;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 列表工具类
 * Created by LiuPeng on 15/9/7.
 */
public class ListUtils<T> {

    public List<T> removeDuplicate(List<T> list) {
        Set set = new LinkedHashSet<T>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }
}
