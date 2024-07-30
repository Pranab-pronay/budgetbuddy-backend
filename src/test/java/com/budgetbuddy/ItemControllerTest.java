package com.budgetbuddy;

import com.budgetbuddy.service.ItemService;
import com.budgetbuddy.shared.dto.ItemDto;
import com.budgetbuddy.shared.type.ItemCategory;
import com.budgetbuddy.ui.controller.ItemController;
import com.budgetbuddy.ui.modal.request.ItemRequest;
import com.budgetbuddy.ui.modal.response.AllItemsResponse;
import com.budgetbuddy.ui.modal.response.ItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ItemController itemController;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("user123");
    }

    @Test
    public void testGetAllItems() {
        List<ItemDto> allIncomesDto = new ArrayList<>();
        List<ItemDto> allExpensesDto = new ArrayList<>();

        when(itemService.getAllIncomesByUserId()).thenReturn(allIncomesDto);
        when(itemService.getAllExpensesByUserId()).thenReturn(allExpensesDto);

        Type type = new TypeToken<List<ItemResponse>>() {}.getType();
        List<ItemResponse> allIncomesResponse = new ArrayList<>();
        List<ItemResponse> allExpensesResponse = new ArrayList<>();
        when(modelMapper.map(allIncomesDto, type)).thenReturn(allIncomesResponse);
        when(modelMapper.map(allExpensesDto, type)).thenReturn(allExpensesResponse);

        ResponseEntity<AllItemsResponse> response = itemController.getAllItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getIncomes().size());
        assertEquals(0, response.getBody().getExpenses().size());
    }

    @Test
    public void testCreateItem() {
        ItemRequest itemRequest = new ItemRequest();
        ItemDto itemDto = new ItemDto();
        ItemDto storedItemDto = new ItemDto();
        storedItemDto.setItemId("123");

        when(modelMapper.map(itemRequest, ItemDto.class)).thenReturn(itemDto);
        when(itemService.createItem(any(ItemCategory.class), any(ItemDto.class))).thenReturn(storedItemDto);
        when(modelMapper.map(storedItemDto, ItemResponse.class)).thenReturn(new ItemResponse());

        ResponseEntity<ItemResponse> response = itemController.createItem(ItemCategory.INCOME, itemRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateItem() {
        ItemRequest itemRequest = new ItemRequest();
        ItemDto itemDto = new ItemDto();
        ItemDto storedItemDto = new ItemDto();
        storedItemDto.setItemId("123");

        when(modelMapper.map(itemRequest, ItemDto.class)).thenReturn(itemDto);
        when(itemService.updateItem(any(ItemCategory.class), any(ItemDto.class))).thenReturn(storedItemDto);
        when(modelMapper.map(storedItemDto, ItemResponse.class)).thenReturn(new ItemResponse());

        ResponseEntity<ItemResponse> response = itemController.updateItem(ItemCategory.INCOME, "123", itemRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testDeleteItem() {
        doNothing().when(itemService).deleteItem(any(ItemCategory.class), anyString());

        ResponseEntity<Void> response = itemController.deleteItem(ItemCategory.INCOME, "123");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(itemService, times(1)).deleteItem(any(ItemCategory.class), anyString());
    }
}
