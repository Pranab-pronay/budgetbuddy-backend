package com.budgetbuddy.service;

import com.budgetbuddy.shared.dto.ItemDto;
import com.budgetbuddy.shared.type.ItemCategory;

import java.util.List;

public interface ItemService {

    List<ItemDto> getAllIncomesByUserId();

    List<ItemDto> getAllExpensesByUserId();

    ItemDto createItem(ItemCategory itemCategory, ItemDto itemDto);

    ItemDto updateItem(ItemCategory itemCategory, ItemDto itemDto);

    void deleteItem(ItemCategory itemCategory, String itemId);
}
