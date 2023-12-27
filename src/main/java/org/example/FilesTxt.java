package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FilesTxt {
    private String nomeFicheiro;
    private String caminhoFicheiro;

    public FilesTxt() {
        this.caminhoFicheiro = "..\\TrabalhoPratico2\\testes";
    }

    public void readFile(String nomeFicheiro){
        try {
            File arquivo = new File(caminhoFicheiro + "\\" + nomeFicheiro + ".txt");
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);

            //Variável para armazenar linha a linha do ficheiro
            String linha;

            //Leitura do arquivo
            while((linha = br.readLine()) != null) {
                System.out.println(linha);
            }

            br.close();
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
