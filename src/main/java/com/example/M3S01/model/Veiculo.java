package com.example.M3S01.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Veiculo {
    @Id
    public String placa;
    public String tipo;
    public String cor;
    public Integer anoDeFabricacao;
    public Integer qtdMultas;

    public Veiculo(String placa, String tipo, String cor, Integer anoDeFabricacao) {
        this.placa = placa;
        this.tipo = tipo;
        this.cor = cor;
        this.anoDeFabricacao = anoDeFabricacao;
        this.qtdMultas = 0;
    }
}
