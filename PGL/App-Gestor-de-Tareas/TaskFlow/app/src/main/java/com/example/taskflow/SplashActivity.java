package com.example.taskflow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * SplashActivity - Pantalla de carga inicial con animaciones
 *
 * Esta actividad muestra una pantalla de bienvenida animada cuando
 * la aplicación se inicia. Incluye:
 * - Animación del logo (fade in + rotación)
 * - Animación del texto (slide up)
 * - Transición automática a MainActivity después de 3 segundos
 */
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 3 segundos
    private ImageView logoImageView;
    private TextView appNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Vincular vistas
        logoImageView = findViewById(R.id.splashLogo);
        appNameTextView = findViewById(R.id.splashAppName);

        // Iniciar animaciones
        iniciarAnimaciones();

        // Navegar a MainActivity después del tiempo establecido
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

            // Agregar transición suave entre actividades
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, SPLASH_DURATION);
    }

    /**
     * Inicia las animaciones de la pantalla de carga
     */
    private void iniciarAnimaciones() {
        // Animación del logo: Fade in + Rotación
        Animation fadeInRotate = AnimationUtils.loadAnimation(this, R.anim.splash_fade_in_rotate);
        logoImageView.startAnimation(fadeInRotate);

        // Animación del texto: Slide up con delay
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.splash_slide_up);
        slideUp.setStartOffset(500); // Empieza 0.5 segundos después del logo
        appNameTextView.startAnimation(slideUp);

        // Animación adicional: Pulse (latido) continuo del logo
        logoImageView.animate()
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(800)
                .setStartDelay(1000)
                .withEndAction(() -> {
                    logoImageView.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(800)
                            .start();
                })
                .start();
    }
}