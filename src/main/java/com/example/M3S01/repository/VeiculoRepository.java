package com.example.M3S01.repository;

import com.example.M3S01.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  VeiculoRepository extends JpaRepository<Veiculo,String> {

}
