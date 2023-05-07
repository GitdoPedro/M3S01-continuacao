package com.example.M3S01.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class VeiculoRequest {

    @NotNull(message = "tipo deve ser informado")
    public String tipo;
    @NotNull(message = "cor deve ser informada")
    public String cor;
    @NotNull(message = "ano deve ser informado")
    public Integer anoDeFabricacao;
    @NotNull(message = "quantidade de multas deve ser informada")
    public Integer qtdMultas;
}
