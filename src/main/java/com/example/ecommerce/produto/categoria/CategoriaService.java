package com.example.ecommerce.produto.categoria;

import com.example.ecommerce.produto.ProdutoEntity;
import com.example.ecommerce.produto.categoria.dto.CategoriaEntityDto;
import com.example.ecommerce.produto.categoria.dto.ReadCategoriaDto;
import com.example.ecommerce.produto.dto.ReadProdutoDto;
import com.example.ecommerce.shared.PaginatedResponse;
import com.example.ecommerce.shared.exception.MessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaEntity getOrCreateCategoria(CategoriaEntityDto dto) {
        if (dto.id() != null) {
            return categoriaRepository.findById(dto.id())
                    .orElseThrow(() -> new MessageException("MENSAGEM.CATEGORIA.NAO-ENCONTRADO"));
        } else {
            CategoriaEntity categoria = new CategoriaEntity();
            categoria.setDescricao(dto.descricao());
            return categoriaRepository.save(categoria);
        }
    }

    public PaginatedResponse<ReadCategoriaDto> findPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CategoriaEntity> categoriaPage = categoriaRepository.findAll(pageable);

        Page<ReadCategoriaDto> dtoPage = categoriaPage.map(this::categoriaToReadCategoriaDto);

        return new PaginatedResponse<>(
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages(),
                dtoPage.getSize(),
                dtoPage.getContent()
        );
    }

    public ReadCategoriaDto categoriaToReadCategoriaDto(CategoriaEntity categoria){
        return new ReadCategoriaDto(
                categoria.getId(),
                categoria.getDescricao()
        );
    }
}
