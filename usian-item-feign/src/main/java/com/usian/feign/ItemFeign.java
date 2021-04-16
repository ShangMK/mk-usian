package com.usian.feign;


import com.usian.pojo.TbContent;
import com.usian.pojo.TbContentCategory;
import com.usian.pojo.TbItem;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("usian-item-service")
public interface ItemFeign {

    @RequestMapping("service/selectTbItemAllByPage")
    Result selectTbItemAllByPage(@RequestParam Integer page, @RequestParam Integer rows);

    @RequestMapping("itemcat/selectItemCategoryByParentId")
    Result selectItemCategoryByParentId(@RequestParam Integer id);

    @RequestMapping("itemcat/selectItemParamByItemCatId/{id}")
    Result selectItemParamByItemCatId(@PathVariable Integer id);



    @RequestMapping("service/preUpdateItem")
    Result preUpdateItem(@RequestBody Long itemId);

    @RequestMapping("service/updateTbItem")
    Result updateTbItem(@RequestBody TbItem tbItem, @RequestParam String desc, @RequestParam String itemParams);

    @RequestMapping("service/deleteItemById")
    Result deleteItemById(@RequestBody Long itemId);


    @RequestMapping("service/insertTbItem")
    Result insertTbItem(@RequestBody TbItem tbitem, @RequestParam String desc, @RequestParam String itemParams);

    @RequestMapping("itemparam/selectItemParamAll")
    PageResult selectItemParamAll();

    @RequestMapping("itemparam/insertItemParam")
    Result insertItemParam(@RequestParam Long itemCatId, @RequestParam String paramData);

    @RequestMapping("itemparam/deleteItemParamById")
    Result deleteItemParamById(@RequestParam Long id);
}
