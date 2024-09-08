package com.example.ecommerce.venda;

import com.example.ecommerce.cliente.ClienteEntity;
import com.example.ecommerce.cliente.ClienteRepository;
import com.example.ecommerce.cliente.ClienteService;
import com.example.ecommerce.cliente.dto.ReadClienteDto;
import com.example.ecommerce.cliente.endereco.dto.ReadEnderecoDto;
import com.example.ecommerce.produto.ProdutoEntity;
import com.example.ecommerce.produto.ProdutoRepository;
import com.example.ecommerce.security.user.UserEntity;
import com.example.ecommerce.security.user.UserService;
import com.example.ecommerce.venda.dto.ReadVendaDto;
import com.example.ecommerce.venda.dto.VendaEntityDto;
import com.example.ecommerce.venda.vendaitem.VendaItemEntity;
import com.example.ecommerce.venda.vendaitem.dto.ReadVendaItemDto;
import com.example.ecommerce.venda.vendaitem.dto.VendaItemEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;
    private final UserService userService;

    public ReadVendaDto createUpdate(VendaEntityDto vendaDTO, JwtAuthenticationToken token) {
        VendaEntity venda = this.toEntity(vendaDTO, token);
        return this.vendaToReadVendaDto(vendaRepository.save(venda));
    }

    public VendaEntity toEntity(VendaEntityDto vendaDTO, JwtAuthenticationToken token) {
        VendaEntity venda = new VendaEntity();
        venda.setId(vendaDTO.id());

        UserEntity usuario = userService.getById(UUID.fromString(token.getName()));

        ClienteEntity cliente = clienteRepository.findByUser(usuario)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        venda.setCliente(cliente);

        venda.setDataVenda(vendaDTO.dataVenda());

        List<VendaItemEntity> itens = vendaDTO.itens().stream()
                .map(dto -> {
                    VendaItemEntity item = new VendaItemEntity();
                    item.setId(dto.id());
                    item.setVenda(venda);

                    ProdutoEntity produto = produtoRepository.findById(dto.produtoId())
                            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID: " + dto.produtoId()));
                    item.setProduto(produto);

                    item.setQuantidade(dto.quantidade());
                    item.setPrecoUnitario(dto.precoUnitario());
                    return item;
                })
                .collect(Collectors.toList());

        venda.setItens(itens);
        venda.atualizarValorTotal();

        return venda;
    }

    public static ReadVendaDto vendaToReadVendaDto(VendaEntity venda) {
        UUID clienteId = venda.getCliente().getId();

        List<ReadVendaItemDto> vendaItemDtos = venda.getItens().stream()
                .map(vendaItem -> new ReadVendaItemDto(
                        vendaItem.getId(),
                        vendaItem.getProduto().getId(),
                        vendaItem.getQuantidade(),
                        vendaItem.getPrecoUnitario()
                ))
                .collect(Collectors.toList());

        return new ReadVendaDto(
                venda.getId(),
                clienteId,
                venda.getValorTotal(),
                venda.getDataVenda(),
                vendaItemDtos
        );
    }

}
