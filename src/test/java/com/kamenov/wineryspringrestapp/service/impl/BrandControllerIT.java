package com.kamenov.wineryspringrestapp.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.Order;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.repository.BrandRepository;
import com.kamenov.wineryspringrestapp.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class BrandControllerIT {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        brandRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void testGetById() throws Exception {
        //arrange

        var actualBrand =  createBrand();
//act
        ResultActions result =
                mockMvc.perform(get("/brands/all/{id}",actualBrand.getId())
                        .contentType(MediaType.APPLICATION_JSON));
        //assert
        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id",is(actualBrand.getId())))
                .andExpect((ResultMatcher) jsonPath("$.description",is(actualBrand.getDescription())))
                .andExpect((ResultMatcher) jsonPath("$.name",is(actualBrand.getName())))
                .andExpect((ResultMatcher) jsonPath("$.categories",is(actualBrand.getCategories())));

    }

    @Test
    public void testCreateBrand() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/brands/add/{id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
        {
        "id": 1,
        "name": "Kamenovi",
        "description": "Bulgarian most family popular winery",
        "categories": []
        }
        """
                        ))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        String body = result.getResponse().getContentAsString();

        Long id = JsonPath.read(body,"$.id");

        Optional<BrandEntity> createBrandOpt =   brandRepository.findById(id);

        Assertions.assertTrue(createBrandOpt.isPresent());

        BrandEntity  createBrand = createBrandOpt.get();
        Assertions.assertEquals("Kamenovi", createBrand.getName());
        Assertions.assertEquals("Bulgarian most family popular winery",
                createBrand.getDescription());
    }
    @Test
    public void testDeleteBrand() throws Exception {

        var brand =   createBrand();

        mockMvc.perform(delete("/brands/{id}",brand.getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        Assertions.assertTrue(
                brandRepository.findById(brand.getId()).isEmpty()
        );
    }

    private BrandEntity createBrand() {

        return     brandRepository.save(
                new BrandEntity()
                        .setDescription("pako"))
                        .setName("pako wine")
                        .setCategories(List.of());

    }


    @Test
    public void testBrandNotFound() throws Exception {
         mockMvc.perform(get("/brands/add/{id}","12")
                        .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isNotFound());

    }

}
