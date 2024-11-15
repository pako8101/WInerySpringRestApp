package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.dto.WIneAddDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.service.WineServiceModel;
import com.kamenov.wineryspringrestapp.service.WineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.ui.Model;
//import static org.springframework.http.server.reactive.MockServerHttpRequest.get;

import static com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.WireMockHelpers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static reactor.core.publisher.Mono.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class WIneControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WineService wineService;

    @Test
    public void testAddWine() throws Exception {
        WIneAddDto wIneAddDto = new WIneAddDto();
        WineServiceModel wineServiceModel = new WineServiceModel();

        BrandDto brandDto = new BrandDto();
        BrandEntity brand = new BrandEntity();
        WineEntity newWineEntity = new WineEntity();
        brand.setId(1L);

        when(wineService.addWIne(wineServiceModel,brand)).thenReturn(new WineEntity());

        mockMvc.perform(get("/wines/add{id}")
                .param("wine", wIneAddDto.getName())
                ).andExpect((ResultMatcher) jsonPath("$.wine").value(wIneAddDto))
               ;


    }
}
