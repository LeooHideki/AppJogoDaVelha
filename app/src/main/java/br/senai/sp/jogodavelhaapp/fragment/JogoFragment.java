package br.senai.sp.jogodavelhaapp.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.sp.jogodavelhaapp.R;
import br.senai.sp.jogodavelhaapp.databinding.FragmentJogoBinding;

public class JogoFragment extends Fragment {
    //variavel para acessar os elementos da view
    private FragmentJogoBinding binding;
    //vetor de botoes para referenciar os botoes
    private Button[] botoes;
    //matriz de String que representa o tabuleiro
    private String[][] tabuleiro;
    //variáveis para os símbolos
    private String simbJog1, simbJog2, simbolo;
    //variavel Random para sortear qurm inicia
    private Random random;
    //variavel para controlar o número de jogadas
    private int numJogadas = 0;
    //variaveis para placar
    private int placar1 = 0;
    private int placar2 = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //instanciar o binding
        binding = FragmentJogoBinding.inflate(inflater, container, false);

        //instanciar o vetor
        botoes = new Button[9];

        //associar o vetor aos botoes
        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt10;
        botoes[4] = binding.bt11;
        botoes[5] = binding.bt12;
        botoes[6] = binding.bt20;
        botoes[7] = binding.bt21;
        botoes[8] = binding.bt22;

        //associa o listener aos botoes
        for (Button bt : botoes){
            bt.setOnClickListener((listenerBotoes));
        }

        //instanciar tabulerio
        tabuleiro = new String[3][3];

        /*
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                tabuleiro[i][j] = "";
            }
        }
        */
        //preencher a Matriz com ""
        for(String[] vetor : tabuleiro){
            Arrays.fill(vetor, "");
        }

        //define os simbolos do jogador1 e do jogador2
        simbJog1 = "X";
        simbJog2 = "O";

        //instancia o random
        random = new Random();

        //sorteia quem iniciara o jogo
        sorteio();
        //mostra quem começa
        atualizaVez();

        //retorna a view root do binding
        return binding.getRoot();
    }
    private void sorteio(){
        //se gerar um valor verdadeiro, jogador 1 começa
        //caso contrario jogador 2 começa
        if(random.nextBoolean()){
            simbolo = simbJog1;
        }else{
            simbolo = simbJog2;
        }
    }
    private void atualizaVez(){
        if(simbolo.equals(simbJog1)){
            binding.linearJog1.setBackgroundColor(getResources().getColor(R.color.gray_300));
            binding.linearJog2.setBackgroundColor(getResources().getColor(R.color.gray_50));
        }else{
            binding.linearJog2.setBackgroundColor(getResources().getColor(R.color.gray_300));
            binding.linearJog1.setBackgroundColor(getResources().getColor(R.color.gray_50));
        }
    }
    private void resetar(){
        //percorrer os botões do vetor, voltando o background inicial
        //tornando-os clicáveis novamente e limpando os textos
        for (int i = 0; i < 9; i++){
            botoes[i].setBackgroundColor(getResources().getColor(R.color.gray_600));
            botoes[i].setText("");
            botoes[i].setClickable(true);
        }
        //limpar a matriz
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                tabuleiro[i][j] = "";
            }
        }
        //zerar o número de jogadas
        numJogadas = 0;
    }

    private boolean vencedor(){
        //verifica se venceu nas linhas
        for(int li = 0; li < 3; li++){
            if(tabuleiro[li][0].equals(simbolo) && tabuleiro[li][1].equals(simbolo) && tabuleiro[li][2].equals(simbolo)) {
                return true;
            }
        }
        //verifica se venceu nas colunas
        for(int col = 0; col < 3; col++){
            if(tabuleiro[0][col].equals(simbolo) && tabuleiro[1][col].equals(simbolo) && tabuleiro[2][col].equals(simbolo)) {
                return true;
            }
        }
        //verifica se venceu nas diagonais
        if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)){
            return true;
        }
        if (tabuleiro[0][2].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)) {
            return true;
        }
        return false;
    }
    //listener para os botoes
    private View.OnClickListener listenerBotoes = btPress -> {
        //incrementa numero de jogadas
        numJogadas++;

        //obtem o nome do botão
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        //extrai a posição através do nome botão
        String posicao = nomeBotao.substring(nomeBotao.length()-2);

        //extrai linha e coluna da string posicao
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));
        // preencher a posição da matriz com o simbolo da vez
        tabuleiro[linha][coluna] = simbolo;
        //faz um casting de View pra Button
        Button botao = (Button) btPress;
        //"seta" o simbolo no botão pressionado
        botao.setText(simbolo);
        //trocar o background do botão pra branco
        botao.setBackgroundColor(Color.WHITE);
        //desabilitar o botão que foi pressionado
        botao.setClickable(false);
        //verifica se venceu

        if(numJogadas >= 5 && vencedor()){
            //informa que houve um vencedor
            Toast.makeText(getContext(), R.string.parabens, Toast.LENGTH_LONG).show();
            resetar();

        }else if(numJogadas == 9){
            //informa se deu velha
            Toast.makeText(getContext(), R.string.velha, Toast.LENGTH_LONG).show();
            resetar();
        }else{
            /*
            if(simbolo.equals(simbJog1)){
                simbolo = simbJog2;
            }else{
               simbolo = simbJog1;
            }
            */
            //inverte o símbolo
            simbolo = simbolo.equals(simbJog1) ? simbJog2 : simbJog1;

            //atualiza a vez
            atualizaVez();
        }
    };
}