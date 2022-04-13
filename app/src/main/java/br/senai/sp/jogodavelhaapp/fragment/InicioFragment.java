package br.senai.sp.jogodavelhaapp.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.senai.sp.jogodavelhaapp.R;
import br.senai.sp.jogodavelhaapp.databinding.FragmentInicioBinding;

public class InicioFragment extends Fragment {
    private FragmentInicioBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // instancia o binding
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        binding.btPlayer.setOnClickListener(v -> {
            NavHostFragment.findNavController(InicioFragment.this).
                    navigate(R.id.action_inicioFragment_to_jogoFragment);
        });
        binding.btRobo.setOnClickListener(v -> {
            Toast.makeText(getContext(), R.string.atualizacao, Toast.LENGTH_SHORT).show();
        });        
        return binding.getRoot();

    }
    @Override
    public void onStart(){
        super.onStart();
        //pega a referência pra activity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        // oculta a action bar
        minhaActivity.getSupportActionBar().hide();
    }
}