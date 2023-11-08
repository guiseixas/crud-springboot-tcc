package com.lead.CatalagoFilmes.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Filme {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @NotEmpty
    private String titulo;

    @Column(nullable = false)
    @NotBlank
    @NotEmpty
    private String sinopse;

    private String imagem;

    @Column(nullable = false)
    @NotBlank
    @NotEmpty
    private String dataLancamento;

    @Column(nullable = false)
    @NotBlank
    @NotEmpty
    private String duracao;

    @ManyToOne
    private Idioma idioma;

    @ManyToOne
    private Categoria categoria;
}