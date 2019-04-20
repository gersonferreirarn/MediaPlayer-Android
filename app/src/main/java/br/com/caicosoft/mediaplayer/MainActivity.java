package br.com.caicosoft.mediaplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    SeekBar sbVolume;
    Button btnPlay, btnPause, btnStop;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sbVolume = findViewById(R.id.sbVolume);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);

        criarMediaPlayer();
        inicializarSeekBar();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executarSom();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausarSom();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pararSom();
            }
        });
    }

    private void inicializarSeekBar(){
        // configura o audio manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); // recupera o servico de audio

        int volumeMaximo = audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC); // pega o volume maximo das musicas
        int volumeAtual = audioManager.getStreamVolume(audioManager.STREAM_MUSIC); // pega volume atual da musica

        // configura os volumes maximo para o seekbar
        sbVolume.setMax(volumeMaximo);

        // configura os volumes atual para o seekbar
        sbVolume.setProgress(volumeAtual);

        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // passa qual stream que alterar, passa o valor a ser setado. a flag é configuração adicional
                audioManager.setStreamVolume(audioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void criarMediaPlayer() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
    }

    public void executarSom(){
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    public void pausarSom(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void pararSom(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            criarMediaPlayer();
        }
    }

    // quando for fechado o app
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // se estiver uma musica na player e ela estiver sendo executada
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop(); // para  a musica
            mediaPlayer.release(); // libera a parte de midia do celular
            mediaPlayer = null; // libera espaço de memoria
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // PAUSAR A MUSICA QUANDO O USUARIO SAIR DO APP, IGUAL O SPOTIFY
        if(mediaPlayer.isPlaying()){
            pausarSom();
        }
    }
}
