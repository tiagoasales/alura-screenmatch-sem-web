package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    private Scanner leitor = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();

    public void exibeMenu() {
        System.out.println("Digite o nome da série para consultar:");
        var nomeSerie = leitor.nextLine();

        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        ConverteDados conversor = new ConverteDados();
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<DadosTemporada>();

        for (int i = 1; i < dados.totalTemporadas(); i++) {
            json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + "&apikey=6585022c");
            DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(temporada);
        }

        temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
