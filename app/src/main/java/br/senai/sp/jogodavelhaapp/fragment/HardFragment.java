package br.senai.sp.jogodavelhaapp.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.sp.jogodavelhaapp.R;
import br.senai.sp.jogodavelhaapp.databinding.FragmentHardBinding;
import br.senai.sp.jogodavelhaapp.databinding.FragmentJogoBinding;
import br.senai.sp.jogodavelhaapp.util.PrefsUtil;

public class HardFragment extends Fragment {
    //variavel para acessar os elementos da view
    private FragmentHardBinding binding;
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
    private int placar1 = 0, placar2 = 0, placarVelha = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //habilitar o menu
        setHasOptionsMenu(true);

        //instanciar o binding
        binding = FragmentHardBinding.inflate(inflater, container, false);

        //instanciar o vetor
        botoes = new Button[25];

        //associar o vetor aos botoes
        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt03;
        botoes[4] = binding.bt04;
        botoes[5] = binding.bt10;
        botoes[6] = binding.bt11;
        botoes[7] = binding.bt12;
        botoes[8] = binding.bt13;
        botoes[9] = binding.bt14;
        botoes[10] = binding.bt20;
        botoes[11] = binding.bt21;
        botoes[12] = binding.bt22;
        botoes[13] = binding.bt23;
        botoes[14] = binding.bt24;
        botoes[15] = binding.bt30;
        botoes[16] = binding.bt31;
        botoes[17] = binding.bt32;
        botoes[18] = binding.bt33;
        botoes[19] = binding.bt34;
        botoes[20] = binding.bt40;
        botoes[21] = binding.bt41;
        botoes[22] = binding.bt42;
        botoes[23] = binding.bt43;
        botoes[24] = binding.bt44;

        //associa o listener aos botoes
        for (Button bt : botoes){
            bt.setOnClickListener((listenerBotoes));
        }

        //instanciar tabulerio
        tabuleiro = new String[5][5];

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

        //define os símbolos do jogador1 e do jogador2
        simbJog1 = PrefsUtil.getSimboloJog1(getContext());
        simbJog2 = PrefsUtil.getSimboloJog2(getContext());

        //atualizar o placar com os símbolos
        binding.tvJog1.setText(getResources().getString(R.string.jogador1, simbJog1));
        binding.tvJog2.setText(getResources().getString(R.string.jogador2, simbJog2));

        //instancia o random
        random = new Random();

        //sorteia quem iniciará o jogo
        sorteio();
        //mostra quem começa
        atualizaVez();

        //retorna a view root do binding
        return binding.getRoot();
    }
    @Override
    public void onStart(){
        super.onStart();
        //pega a referência pra activity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        minhaActivity.getSupportActionBar().show();
        //desabilita a seta de retornar
        minhaActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private AlertDialog dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        placarVelha = 0;
                        placar1 = 0;
                        placar2 = 0;
                        atualizaPlacar();
                        resetar();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void atualizaPlacar(){
        binding.tvPlacarJog1.setText(placar1+"");
        binding.tvPlacarJog2.setText(placar2+"");
        binding.tvPlacarVelha.setText(placarVelha+"");
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
        for (int i = 0; i < 25; i++){
            botoes[i].setBackgroundColor(getResources().getColor(R.color.gray_600));
            botoes[i].setText("");
            botoes[i].setClickable(true);
        }
        //limpar a matriz
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                tabuleiro[i][j] = "";
            }
        }
        //zerar o número de jogadas
        numJogadas = 0;

        sorteio();

        atualizaVez();
    }
    //adicionar o menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }
    //método que chama o parâmetro clicado
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //verificar qual item do menu foi selecionado
        switch (item.getItemId()){
            //caso seja a opção de resetar
            case R.id.menu_resetar:
                dialog().show();
                break;
            //caso seja a opção de voltar ao inicio
            case R.id.menu_inicio:
                NavHostFragment.findNavController(HardFragment.this).navigate(R.id.action_hardFragment_to_inicioFragment);
                break;
            //caso seja a opção de preferências
            case R.id.menu_preferencias:
                NavHostFragment.findNavController(HardFragment.this).navigate(R.id.action_hardFragment_to_prefFragment);
                break;
            case R.id.menu_hard:
                NavHostFragment.findNavController(HardFragment.this).navigate(R.id.action_hardFragment_to_jogoFragment);
                break;
        }

        return true;
    }

    private boolean vencedor(){
        //verifica se venceu nas linhas
        for(int li = 0; li < 5; li++){
            if(tabuleiro[li][0].equals(simbolo) && tabuleiro[li][1].equals(simbolo) && tabuleiro[li][2].equals(simbolo) && tabuleiro[li][3].equals(simbolo) && tabuleiro[li][4].equals(simbolo)) {
                return true;
            }
        }
        //verifica se venceu nas colunas
        for(int col = 0; col < 5; col++){
            if(tabuleiro[0][col].equals(simbolo) && tabuleiro[1][col].equals(simbolo) && tabuleiro[2][col].equals(simbolo) && tabuleiro[3][col].equals(simbolo) && tabuleiro[4][col].equals(simbolo)) {
                return true;
            }
        }
        //verifica se venceu nas diagonais
        if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo) && tabuleiro[3][3].equals(simbolo) && tabuleiro[4][4].equals(simbolo)){
            return true;
        }
        if (tabuleiro[0][4].equals(simbolo) && tabuleiro[1][3].equals(simbolo) && tabuleiro[2][2].equals(simbolo) && tabuleiro[3][1].equals(simbolo) && tabuleiro[4][0].equals(simbolo)) {
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

        if(numJogadas >= 9 && vencedor()){
            //informa que houve um vencedor
            Toast.makeText(getContext(), R.string.parabens, Toast.LENGTH_LONG).show();
            //verifica quem venceu
            if(simbolo.equals(simbJog1)){
                placar1++;
            }else{
                placar2++;
            }
            //atualiza placar
            atualizaPlacar();
            resetar();

        }else if(numJogadas == 25){
            //informa se deu velha
            Toast.makeText(getContext(), R.string.velha, Toast.LENGTH_LONG).show();
            placarVelha++;
            atualizaPlacar();
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