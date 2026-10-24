package com.delegrego.api_alunos_testes.service;

import com.delegrego.api_alunos_testes.dto.AlunoRequest;
import com.delegrego.api_alunos_testes.dto.AlunoResponse;
import com.delegrego.api_alunos_testes.model.Aluno;
import com.delegrego.api_alunos_testes.repository.AlunoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @InjectMocks
    AlunoService service;

    @Mock
    AlunoRepository repository;

    @Test
    void deveSalvarERetornarAluno() {

        // Arrange
        AlunoRequest request = new AlunoRequest(
                "Henrique", "henrique@gmail.com", "senha",
                LocalDate.of(2001, 10, 11), 8);

        // Stub
        Mockito.when(repository.existsByEmailIgnoreCase(request.getEmail())).thenReturn(false);

        Aluno aluno = new Aluno(
                1L, request.getNome(), request.getEmail(), request.getSenha(),
               request.getDataNascimento(), request.getMedia()
        );

        Mockito.when(repository.save(Mockito.any(Aluno.class))).thenReturn(aluno);

        // Act
        AlunoResponse resultado = service.cadastrarAluno(request);

        // Assert
        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getId()).isGreaterThan(0);

    }

    @Test
    void deveRetornarListDeAlunos() {

        // Arrange
        List<Aluno> alunos = new ArrayList<>();
        Aluno aluno1 = new Aluno(1L,
                "Henrique", "henrique@gmail.com", "senha",
                LocalDate.of(2001, 10, 11), 8);
        Aluno aluno2 = new Aluno(2L,
                "Matheus", "matheus@gmail.com", "senha123",
                LocalDate.of(2001, 10, 11), 9);
        alunos.add(aluno1);
        alunos.add(aluno2);

        // Stub
        Mockito.when(repository.findAll()).thenReturn(alunos);

        // Act
        List<AlunoResponse> alunosResponse = service.listarAlunos();

        // Assert
        Assertions.assertThat(alunosResponse).hasSize(2);

    }

    @Test
    void deveRetornarAlunoPorId() {

        // Arrange
        Aluno aluno1 = new Aluno(3L,
                "Henrique", "henrique@gmail.com", "senha",
                LocalDate.of(2001, 10, 11), 8);

        // Stub
        Mockito.when(repository.findById(3L)).thenReturn(Optional.of(aluno1));


        // Act
        AlunoResponse alunoResponse = service.obterAlunoPorId(3L);


        // Assert
        Assertions.assertThat(alunoResponse.getId()).isEqualTo(3L);

    }

    @Test
    void deveAtualizarAluno() {

        // Arrange
        Aluno aluno = new Aluno(
                1L, "Henrique", "henrique@gmail.com", "senha",
                LocalDate.of(2001, 10, 11), 8);

        AlunoRequest requestAlunoAtualizado = new AlunoRequest(
        "Henrique Novo", "henriquenovo@gmail.com", "senhanova",
                LocalDate.of(2001, 10, 11), 9);

        // Stub
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(aluno));
        Mockito.when(repository.save(Mockito.any(Aluno.class))).thenReturn(aluno);

        // Act
        AlunoResponse alunoResponse = service.atualizarAluno(1L, requestAlunoAtualizado);

        // Assert
        Assertions.assertThat(alunoResponse.getId()).isEqualTo(1L);
        Assertions.assertThat(alunoResponse.getNome()).isEqualTo(requestAlunoAtualizado.getNome());
        Assertions.assertThat(alunoResponse.getEmail()).isEqualTo(requestAlunoAtualizado.getEmail());
        Assertions.assertThat(alunoResponse.getDataNascimento()).isEqualTo(requestAlunoAtualizado.getDataNascimento());
        Assertions.assertThat(alunoResponse.getMedia()).isEqualTo(requestAlunoAtualizado.getMedia());

    }




}
