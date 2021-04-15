import com.usian.mapper.TbItemCatMapper;
import com.usian.pojo.TbItemCat;
import com.usian.pojo.TbItemCatExample;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TE {

    @Autowired
    TbItemCatMapper tbItemCatMapper;

    @Test
    public void selectItemCategoryAll() {
        ArrayList arrayList = new ArrayList();


        //查出根类标题集合
        List<TbItemCat> oneitemCats = this.getParentId(Long.parseLong("0"));

        //遍历父节点
        for (TbItemCat itemCat : oneitemCats) {
            HashMap<String, Object> hash = new HashMap<>();
            hash.put("n",itemCat.getName());
            hash.put("i",this.getchildrehashmap(itemCat));
            arrayList.add(hash);
        }
        System.out.println(arrayList);
    }


    public ArrayList<Object> getchildrehashmap(TbItemCat itemCat) {
        ArrayList<Object> arrayList = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();
        List<TbItemCat> parentId = this.getParentId(itemCat.getId());
        for (TbItemCat tbItemCat : parentId) {
            if (tbItemCat.getIsParent()){
                map.put(tbItemCat.getName(), this.getchildrehashmap(tbItemCat));
                arrayList.add(map);
                return arrayList;
            }
            arrayList.add(tbItemCat.getName());
        }
        return arrayList;
    }
    /**
     * 把循环查询的example提出来变成一个方法降低代码重复
     */
    public  List<TbItemCat> getParentId(Long id){
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbItemCat> itemCats = tbItemCatMapper.selectByExample(tbItemCatExample);
        return itemCats;
    }
}
