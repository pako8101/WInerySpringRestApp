package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.exceptions.WineNotFoundException;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.view.WIneViewModel;
import com.kamenov.wineryspringrestapp.repository.WineRepository;
import com.kamenov.wineryspringrestapp.service.WineService;
import jakarta.transaction.NotSupportedException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/wines")
public class WineRestController {
private final RestClient restClient;
    private final WineService wineService;
    private final ModelMapper modelMapper;
    private final WineRepository wineRepository;

    public WineRestController(RestClient restClient, WineService wineService,
                              ModelMapper modelMapper, WineRepository wineRepository) {
        this.restClient = restClient;
        this.wineService = wineService;
        this.modelMapper = modelMapper;
        this.wineRepository = wineRepository;
    }
    @GetMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WIneViewModel>> findAll(){
        List<WIneViewModel> wIneViewModels = wineService
                .getAllWInes()
                .stream()
                .map(wine -> {
                    WIneViewModel viewModel = modelMapper.map(
                            wine,WIneViewModel.class);
                    viewModel.setDescription(wine.getDescription());
                    return  viewModel;
                }).toList();

        return  ResponseEntity.ok()
                .body(wIneViewModels);

    }


    @PutMapping("/api/{id}")
    public ResponseEntity<WIneViewModel> updateArticle(@PathVariable Long id, @RequestBody WIneViewModel wIneViewModel) throws NotSupportedException {
        return wineRepository.findById(id)
                .map(wine -> {
                    wine.setDescription(wIneViewModel.getDescription());
                    wineRepository.save(wine);
                    WIneViewModel updatedViewModel = modelMapper.map(wine, WIneViewModel.class);
                    return ResponseEntity.ok(updatedViewModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(WineNotFoundException.class)
    @ResponseBody
    public NotFoundErrorInfo handleApiObjectNotFoundException(WineNotFoundException apiObjectNotFoundException) {
        return new NotFoundErrorInfo("NOT FOUND", apiObjectNotFoundException.getClass());
    }


    public record NotFoundErrorInfo(String code, Object id) {

    }
}
