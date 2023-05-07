package com.example.M3S01.controller;


import com.example.M3S01.dto.VeiculoRequest;
import com.example.M3S01.dto.VeiculoResponse;
import com.example.M3S01.model.Veiculo;
import com.example.M3S01.service.VeiculoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@CrossOrigin
public class VeiculosController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VeiculoService service;

    @PostMapping
    public ResponseEntity inserir(@RequestBody @Valid VeiculoRequest request) {
        Veiculo veiculo = modelMapper.map(request, Veiculo.class);
        veiculo = service.inserir(veiculo);
        VeiculoResponse resp = modelMapper.map(veiculo, VeiculoResponse.class);
        return ResponseEntity.created(URI.create(veiculo.getPlaca())).body(resp);  // 201
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> consultar() {
        List<Veiculo> veiculos = service.consultar();
        List<VeiculoResponse> resp = veiculos.stream()
                .map(p -> modelMapper.map(p, VeiculoResponse.class)).toList();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("{placa}")
    public ResponseEntity<VeiculoResponse> consultar(@PathVariable("placa") String placa) {
        Veiculo veiculo  = service.consultarPorPlaca(placa);
        VeiculoResponse resp = modelMapper.map(veiculo, VeiculoResponse.class);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("{placa}")
    public ResponseEntity excluir(@PathVariable("placa") String placa) {
        service.excluir(placa);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{placa}/multas")
    public ResponseEntity<VeiculoResponse> atualizarQtdMultas(@PathVariable String placa) {
        Veiculo veiculoAtualizado = service.adicionarMulta(placa);
        VeiculoResponse resp = modelMapper.map(veiculoAtualizado, VeiculoResponse.class);
        return ResponseEntity.ok(resp);
    }



}
