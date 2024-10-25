package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.exceptions.NoSuchOrderFound;
import com.kamenov.wineryspringrestapp.models.entity.*;
import com.kamenov.wineryspringrestapp.repository.OrderRepository;
import com.kamenov.wineryspringrestapp.repository.ShoppingCartRepository;
import com.kamenov.wineryspringrestapp.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private CartService cartService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private UserEntity user;
    private ShoppingCart shoppingCart;
    private CartItem cartItem;
    private Order order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new UserEntity();
        user.setId(1L);

        WineEntity wine = new WineEntity();
        wine.setId(1L);
        wine.setName("Test Wine");

        cartItem = new CartItem();
        cartItem.setWine(wine);
        cartItem.setQuantity(2);

        shoppingCart = new ShoppingCart();
        shoppingCart.setUserEntity(user);
        shoppingCart.setItems(Arrays.asList(cartItem));
        shoppingCart.setCompleted(false);

        order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
    }

    @Test
    public void testCheckOutSuccess() {
        // Arrange
        when(shoppingCartRepository.findByUserEntityAndCompletedFalse(user)).thenReturn(Optional.of(shoppingCart));
        when(orderRepository.save(any(Order.class))).
                thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // Act
        Order result = orderService.checkOut(user);

        // Assert
        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(1, result.getOrders().size(), "Expected 1 order item in the order but found: " + result.getOrders().size());
        assertEquals(cartItem.getWine(), result.getOrders().get(0).getWine());
        assertEquals(cartItem.getQuantity(), result.getOrders().get(0).getQuantity());
        assertTrue(shoppingCart.isCompleted());

        verify(shoppingCartRepository, times(1)).save(shoppingCart);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testCheckOutNoActiveCartThrowsException() {
        // Arrange
        when(shoppingCartRepository.findByUserEntityAndCompletedFalse(user)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.checkOut(user));
        assertEquals("No active cart found", exception.getMessage());

        verify(shoppingCartRepository, never()).save(any());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void testFindByIdSuccess() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act
        Order result = orderService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(order, result);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdOrderNotFoundThrowsException() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchOrderFound exception = assertThrows(NoSuchOrderFound.class, () -> orderService.findById(1L));
        assertEquals("No order found", exception.getMessage());

        verify(orderRepository, times(1)).findById(1L);
    }
}

