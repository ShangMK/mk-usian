package com.usian.mapper;

import com.usian.pojo.SearchItem;

import java.util.List;

public interface SearchItemMapper {

	List<SearchItem> getItemList();
	SearchItem getItemById(Long itemId);
}
