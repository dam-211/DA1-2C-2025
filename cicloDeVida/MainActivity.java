package ar.edu.ifts18.ciclodevida;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CicloVidaDemo";
    private static final String KEY_POSITION = "pos";
    private static final String KEY_WAS_PLAYING = "wasPlaying";

    private MediaPlayer mediaPlayer;
    private TextView txtLog, txtStatus;
    private ScrollView scrolling;
    private Button btnPlayPause, btnStop;

    private int playbackPosition = 0;
    private boolean wasPlayingBeforePause = false;
    private void logEvent (String mensaje){
        Log.d(TAG, mensaje);
        if(txtLog != null) {
            txtLog.append("* " + mensaje + "\n");
        }
    }

    private void updateStatus() {
        String estado;

        if(mediaPlayer == null) {
            estado = "Sin reproducir";

        }else if (mediaPlayer.isPlaying()) {
            estado = "Reproduciendo (ms = +" + mediaPlayer.getCurrentPosition() + ")";
        } else {

            estado = "En pausa/detenido (ms= " +  playbackPosition + ")";
        }

        txtStatus.setText("Estado: " + estado);
        btnPlayPause.setText((mediaPlayer != null && mediaPlayer.isPlaying()) ? "Pausar" : "Reproducir");
    }



    private void setupMediaPlayerIfNeeded() {
        if (mediaPlayer == null) {
            // MP3 local en res/raw/audio_demo.mp3
            mediaPlayer = MediaPlayer.create(this, R.raw.zvuk_litvina);
            logEvent("MediaPlayer creado");

            if (mediaPlayer == null) {
                logEvent("ERROR: MediaPlayer.create devolvió null (revisa que el MP3 exista en res/raw)");
                return;
            }

            mediaPlayer.setOnCompletionListener(mp -> {
                logEvent("onCompletion(): terminó el audio, reseteando a 0");
                playbackPosition = 0;
                wasPlayingBeforePause = false;
                updateStatus();
            });
        }
    }

    // ---------- Ciclo de vida del Activity ----------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logEvent("onCreate()");
        setContentView(R.layout.activity_main);

        txtLog = findViewById(R.id.txtLog);
        txtStatus = findViewById(R.id.txtStatus);
        scrolling = findViewById(R.id.scrollLog);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnStop = findViewById(R.id.btnStop);

        setupMediaPlayerIfNeeded();
        updateStatus();

        btnPlayPause.setOnClickListener(v -> {
            setupMediaPlayerIfNeeded();
            if (mediaPlayer == null) return;

            if (mediaPlayer.isPlaying()) {
                // Pausa manual del usuario
                playbackPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                wasPlayingBeforePause = false;
                logEvent("Botón: Pausar (pos=" + playbackPosition + " ms)");
            } else {
                // Reproducir desde la última posición recordada
                mediaPlayer.seekTo(playbackPosition);
                mediaPlayer.start();
                wasPlayingBeforePause = true;
                logEvent("Botón: Reproducir desde " + playbackPosition + " ms");
            }
            updateStatus();
        });

        btnStop.setOnClickListener(v -> {
            if (mediaPlayer == null) return;
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            mediaPlayer.seekTo(0);
            playbackPosition = 0;
            wasPlayingBeforePause = false;
            logEvent("Botón: Detener (pos=0)");
            updateStatus();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        logEvent("onStart()");
        // Aquí la actividad es visible (pero aún no en primer plano).
        // No iniciamos audio automáticamente para dejar claro el control del usuario.
        updateStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        logEvent("onResume()");
        // Volvemos a primer plano. Si el usuario estaba reproduciendo antes de un evento temporal (ej. rotación),
        // retomamos automáticamente (patrón común en apps de media).
        setupMediaPlayerIfNeeded();
        if (mediaPlayer != null && wasPlayingBeforePause) {
            mediaPlayer.seekTo(playbackPosition);
            mediaPlayer.start();
            logEvent("Auto-Resume: continuando desde " + playbackPosition + " ms");
        }
        updateStatus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        logEvent("onPause()");
        // Interrupciones temporales: Home, multi-ventana, diálogo encima, llamada entrante, etc.
        // Pausamos y recordamos estado.
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            playbackPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            wasPlayingBeforePause = true;
            logEvent("onPause(): pausado en " + playbackPosition + " ms");
        }
        updateStatus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        logEvent("onStop()");
        // La actividad ya no es visible. En una demo didáctica mantenemos el MediaPlayer
        // para reanudar rápido. En producción podrías liberar aquí si quieres ahorrar recursos.
        updateStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logEvent("onDestroy()");
        // Limpieza final: liberar recursos nativos del reproductor
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            logEvent("MediaPlayer liberado");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logEvent("onRestart()");
        // Vuelve después de onStop(). Nada especial para el audio en esta demo.
    }

    // ---------- Guardar/restaurar estado en cambios de configuración ----------
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guardamos la posición actual y si estaba reproduciendo
        if (mediaPlayer != null) {
            playbackPosition = mediaPlayer.getCurrentPosition();
            outState.putInt(KEY_POSITION, playbackPosition);
            outState.putBoolean(KEY_WAS_PLAYING, mediaPlayer.isPlaying() || wasPlayingBeforePause);
            logEvent("onSaveInstanceState(): pos=" + playbackPosition + ", wasPlaying=" + (mediaPlayer.isPlaying() || wasPlayingBeforePause));
        } else {
            outState.putInt(KEY_POSITION, playbackPosition);
            outState.putBoolean(KEY_WAS_PLAYING, wasPlayingBeforePause);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        playbackPosition = savedInstanceState.getInt(KEY_POSITION, 0);
        wasPlayingBeforePause = savedInstanceState.getBoolean(KEY_WAS_PLAYING, false);
        logEvent("onRestoreInstanceState(): pos=" + playbackPosition + ", wasPlaying=" + wasPlayingBeforePause);
        updateStatus();
    }
}
