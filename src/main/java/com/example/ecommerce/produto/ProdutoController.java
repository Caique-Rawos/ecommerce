package com.example.ecommerce.produto;

import com.example.ecommerce.produto.dto.ProdutoEntityDto;
import com.example.ecommerce.produto.dto.ReadProdutoDto;
import com.example.ecommerce.shared.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping()
    public PaginatedResponse<ReadProdutoDto> getProdutosPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return produtoService.findPaginated(page, size, sortBy, sortDir);
    }

    @GetMapping(value = "/{id}")
    public ReadProdutoDto getById(@PathVariable Long id) {
        return produtoService.findReadProdutoDtoById(id);
    }

    @PostMapping()
    public ReadProdutoDto createProduto(@RequestBody ProdutoEntityDto dto) {
        return produtoService.create(dto);
    }

    @PatchMapping("/{id}")
    public ReadProdutoDto updateProduto(@PathVariable Long id, @RequestBody ProdutoEntityDto dto) {
        return produtoService.update(id, dto);
    }
}
