package com.example.ecommerce.cliente.endereco;

import com.example.ecommerce.cliente.ClienteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente_endereco")
public class EnderecoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;

    private String numero;

    private String bairro;

    @Column(length = 8)
    private String cep;

    private String cidade;

    @Column(length = 2)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;
}
