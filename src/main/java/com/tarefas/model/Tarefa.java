package com.tarefas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "tarefas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da tarefa é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequencia frequencia;

    // Horário de início (opcional)
    private LocalTime horario;

    // Prazo: deve ser feita antes deste horário (opcional)
    private LocalTime prazo;

    // Para frequência SEMANAL: lista de dias da semana (0=Dom, 1=Seg, ... 6=Sáb)
    @ElementCollection
    @CollectionTable(name = "tarefa_dias_semana", joinColumns = @JoinColumn(name = "tarefa_id"))
    @Column(name = "dia_semana")
    private List<Integer> diasSemana;

    // Para frequência DATA_ESPECIFICA
    private LocalDate dataEspecifica;

    // Se a tarefa foi concluída
    @Column(nullable = false)
    private boolean concluida = false;

    // Data em que foi concluída
    private LocalDate dataConclusao;
}
