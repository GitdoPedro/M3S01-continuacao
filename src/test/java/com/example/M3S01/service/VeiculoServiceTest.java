package com.example.M3S01.service;

import com.example.M3S01.model.Veiculo;
import com.example.M3S01.repository.VeiculoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock   // criar objeto mock da dependencia da classe testada
    private VeiculoRepository veiculoRepo;

    @InjectMocks
    private VeiculoService service;

    @Test
    @DisplayName("Quando existe veiculo com a placa informada, deve retornar o veículo")
    void consultar() {
        // given
        String placa = "rio100";
        Veiculo veiculo = Veiculo.builder()
                .cor("azul").placa(placa).tipo("duas rodas")
                .anoDeFabricacao(1995).qtdMultas(0).build();
        Mockito.when(veiculoRepo.findById(Mockito.anyString()))
                .thenReturn(Optional.of(veiculo));
        // when
        Veiculo resultado = service.consultarPorPlaca(placa);
        // then
        assertNotNull(resultado);
        assertEquals(placa, resultado.getPlaca());
    }

    @Test
    @DisplayName("Quando nao existe carro com a placa informada, deve lancar excecao")
    void consultar_naoEncontrado() {
        String placa = "100";
        assertThrows(RuntimeException.class, () -> service.consultarPorPlaca(placa));
    }

    @Test
    @DisplayName("Quando nao ha registros de veiculos, deve retornar lista vazia")
    void consultar_semRegistros() {
        List<Veiculo> lista = service.consultar();
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }

    @Test
    @DisplayName("Quando ha registros de veiculos, deve retornar lista com valores")
    void consultar_listaComRegistros() {
        // given
        List<Veiculo> veiculos = List.of(
                new Veiculo("100rio","duas portas","azul",2008,0),
                new Veiculo("100sp","quatro portas","preto",2005,4)
        );
        Mockito.when(veiculoRepo.findAll()).thenReturn(veiculos);
        // when
        List<Veiculo> lista = service.consultar();
        // then
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertEquals(veiculos.size(), lista.size());
    }

    @Test
    @DisplayName("Quando tentativa de inserir veiculo com placa já cadastrada, deve lancar excecao")
    void inserir_placaJaExistente() {
        Veiculo veiculo = Veiculo.builder()
                .cor("azul").placa("100rio").tipo("duas rodas")
                .anoDeFabricacao(1995).qtdMultas(0).build();
        Mockito.when(veiculoRepo.existsById(Mockito.anyString()))
                .thenReturn(true);
        assertThrows(RuntimeException.class, () -> service.inserir(veiculo));
    }

    @Test
    @DisplayName("Quando tentativa de inserir veiculo com placa nao cadastrada, deve inserir veiculo")
    void inserir() {
        // given
        Veiculo veiculoOriginal = Veiculo.builder()
                .cor("azul").placa("100rio").tipo("duas rodas")
                .anoDeFabricacao(1995).qtdMultas(0).build();
        Veiculo veiculoInserido = Veiculo.builder()
                .cor("azul").placa("100rio").tipo("duas rodas")
                .anoDeFabricacao(1995).qtdMultas(0).build();
        Mockito.when(veiculoRepo.existsById(Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(veiculoRepo.save(Mockito.any(Veiculo.class)))
                .thenReturn(veiculoInserido);
        // when
        Veiculo resultado = service.inserir(veiculoOriginal);
        // then
        assertNotNull(resultado);
        assertEquals(veiculoInserido.getPlaca(), resultado.getPlaca());
    }

    @Test
    @DisplayName("Quando existe veiculo com o placa informado, deve retornar a placa")
    void consultar_placaExistente() {
        // given
        String placa = "rio100";
        Veiculo veiculo = Veiculo.builder()
                .cor("azul").placa("rio100").tipo("duas rodas")
                .anoDeFabricacao(1995).qtdMultas(0).build();
        Mockito.when(veiculoRepo.findById(Mockito.anyString()))
                .thenReturn(Optional.of(veiculo));
        // when
        Veiculo resultado = service.consultarPorPlaca(placa);
        // then
        assertNotNull(resultado);
        assertEquals(placa, resultado.getPlaca());
    }

    @Test
    @DisplayName("Quando nao existe veiculo com a placa informado, deve lancar excecao")
    void consultarPorPlaca_naoEncontrado() {
        assertThrows(RuntimeException.class, () -> service.consultarPorPlaca("100Rio"));
    }

    @Test
    @DisplayName("Quando existe veiculo com a placa indicada, deve excluir o veiculo")
    void excluir() {
        String placa = "rio100";
        Veiculo veiculo = Veiculo.builder()
                .cor("azul").placa("rio100").tipo("duas rodas")
                .anoDeFabricacao(1995).qtdMultas(0).build();
        Mockito.when(veiculoRepo.findById(Mockito.anyString()))
                .thenReturn(Optional.of(veiculo));
        assertDoesNotThrow(() -> service.excluir(placa));
    }

    @Test
    @DisplayName("Quando nao existe veiculo com a placa indicada, deve lacar excecao")
    void excluir_inexistente() {
        assertThrows(Exception.class, () -> service.excluir("100rio"));
    }



}