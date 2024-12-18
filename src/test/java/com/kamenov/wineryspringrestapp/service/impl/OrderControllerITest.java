package com.kamenov.wineryspringrestapp.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.Order;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.repository.BrandRepository;
import com.kamenov.wineryspringrestapp.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.apache.http.client.methods.RequestBuilder.post;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerITest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private MockMvc mockMvc;
@Mock
    private UserEntity userEntity;

    @Test
    @WithMockUser
    public void testGetById() throws Exception {
        //arrange

    var actualOrder =  createOrder();
//act
        ResultActions result =
mockMvc.perform(get("/order/checkout/{orderId}",actualOrder.getId())
                .contentType(MediaType.APPLICATION_JSON));
        //assert
        result.andExpect(status().isOk())
        .andExpect((ResultMatcher) jsonPath("$.id",is(actualOrder.getId())))
        .andExpect((ResultMatcher) jsonPath("$.orders",is(actualOrder.getOrders())))
        .andExpect((ResultMatcher) jsonPath("$.orderDate",is(actualOrder.getOrderDate())))
        .andExpect((ResultMatcher) jsonPath("$.user",is(actualOrder.getUser())));

    }


    private Order createOrder() {
        UserEntity user = new UserEntity()
                .setUsername("pako4")
                .setFullName("pako4")
                .setPassword("1234")
                .setEmail("pako4@example.com");
        return     orderRepository.save(
                new Order()
                        .setOrders(List.of())
                        .setOrderDate(LocalDateTime.now())
                        .setUser(user)
        );
    }
}
