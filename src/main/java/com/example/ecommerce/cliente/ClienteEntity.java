package com.example.ecommerce.cliente;

import com.example.ecommerce.cliente.dto.ClienteEntityDto;
import com.example.ecommerce.cliente.endereco.EnderecoEntity;
import com.example.ecommerce.produto.dto.ProdutoEntityDto;
import com.example.ecommerce.security.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
public class ClienteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @Column(length = 14, nullable = false)
    private String cpfCnpj;

    @Column(length = 20)
    private String celular;

    @Column(length = 20)
    private String telefoneFixo;

    @Column(nullable = true)
    private LocalDate dataNascimento;

    private LocalDateTime dataCadastro;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }

    private LocalDateTime dataAtualizacao;

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnderecoEntity> enderecos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    public ClienteEntity(ClienteEntityDto dto) {
        this.nome = dto.nome();
        this.cpfCnpj = dto.cpfCnpj();
        this.celular = dto.celular();
        this.telefoneFixo = dto.telefoneFixo();
        this.dataNascimento = dto.dataNascimento();
    }
}
