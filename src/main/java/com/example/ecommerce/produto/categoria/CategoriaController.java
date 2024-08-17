package com.example.ecommerce.produto.categoria;

import com.example.ecommerce.produto.categoria.dto.ReadCategoriaDto;
import com.example.ecommerce.shared.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/produto/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping()
    public PaginatedResponse<ReadCategoriaDto> getCategoriaPaginated(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir
    ) {
        return categoriaService.findPaginated(page, size, sortBy, sortDir);
    }
}
