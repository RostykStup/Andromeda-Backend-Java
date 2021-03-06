package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.SubcategoryRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.category.SubcategoryResponse;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;


import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/subcategory")
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;

    @PostMapping()
    public void save(@RequestBody SubcategoryRequest request) {
        subcategoryService.save(request);
    }

    @PutMapping()
    public void update(Long id, @RequestBody SubcategoryRequest request) {
        subcategoryService.update(request, id);
    }

    @GetMapping("/all")
    public List<SubcategoryResponse> findAll() {
        return subcategoryService.findAll();
    }

    @GetMapping("/category")
    public List<SubcategoryResponse> findAllByCategoryId(Long id) {
        return subcategoryService.findAllByCategoryId(id);
    }

    @GetMapping()
    public PageResponse<SubcategoryResponse> findPage(PaginationRequest request) {
        return subcategoryService.findPage(request);
    }

    @GetMapping("/page")
    public PageResponse<SubcategoryResponse> findPageByCategoryId(Long id, PaginationRequest request) {
        return subcategoryService.findPageByCategoryId(id,request);
    }

    @DeleteMapping
    public void delete(Long id) {
        subcategoryService.delete(id);
    }

}

