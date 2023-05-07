package com.example.M3S01.service;
import com.example.M3S01.model.Veiculo;
import com.example.M3S01.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;




@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository repo;

    //Não pode ser cadastrado mais de um veículo com a mesma placa
    public Veiculo inserir(Veiculo veiculo) {
        boolean isPlacaJaCadastrada = repo.existsById(veiculo.getPlaca());
        if(isPlacaJaCadastrada){
            throw new RuntimeException();
        }
        veiculo = repo.save(veiculo);
        return veiculo;
    }

    public List<Veiculo> consultar() {
        List<Veiculo> personagens = repo.findAll();
        return personagens;
    }

    //Quando a consulta por um veículo nao tiver resultado, deve ser lançado exceção no Service,
    // e retornado o código http 404 pro cliente da API.
    public Veiculo consultarPorPlaca(String placa) {
        Optional<Veiculo> personagemOpt = repo.findById(placa);
        return personagemOpt.orElseThrow(RuntimeException::new);
    }

    public Veiculo adicionarMulta(String placa) {
        Optional<Veiculo> veiculoOpt = repo.findById(placa);
        if (veiculoOpt.isEmpty()) {

            throw new RuntimeException();
        }
        Veiculo veiculo = veiculoOpt.get();
        int qtd = veiculo.getQtdMultas() + 1;
        veiculo.setQtdMultas(qtd);
        veiculo = repo.save(veiculo);
        return veiculo;
    }

    //Não pode ser excluído um veículo que tenha multas cadastradas para ele (multas > 0)
    public void excluir(String placa) {
        var veiculo = repo.findById(placa)
                .orElseThrow(RuntimeException::new);
        if (veiculo.getQtdMultas() > 0){
            throw new RuntimeException();
        }
        repo.delete(veiculo);
    }




}
