package com.example.gestionreparacionesapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Pantalla principal (demo cámara).
 * Temas: permisos en runtime, Activity Result API, edge-to-edge.
 */
public class MainActivity extends AppCompatActivity {

    private ImageView imgPreview;

    // Launchers (se inicializan en onCreate para evitar IllegalStateException)
    private ActivityResultLauncher<String> requestCameraPermission;
    private ActivityResultLauncher<Intent> takePictureLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajuste de insets (status/navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencias UI
        imgPreview = findViewById(R.id.imgPreview);
        Button btnFoto = findViewById(R.id.btnFoto);

        // ✅ Registrar Activity Result Launchers dentro de onCreate
        requestCameraPermission = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        abrirCamara();
                    } else {
                        Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        if (extras != null) {
                            Bitmap imageBitmap = (Bitmap) extras.get("data"); // thumbnail
                            imgPreview.setImageBitmap(imageBitmap);
                        }
                    }
                }
        );

        // Acción botón
        btnFoto.setOnClickListener(v -> verificarPermisoYTomarFoto());
    }

    /** Pide permiso si hace falta y abre la cámara. */
    private void verificarPermisoYTomarFoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            abrirCamara();
        } else {
            // ⬇️ Lanza el prompt de permiso (debe estar declarado en el Manifest)
            requestCameraPermission.launch(Manifest.permission.CAMERA);
        }
    }

    /** Lanza intent de captura (thumbnail) si hay app de cámara disponible. */
    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            takePictureLauncher.launch(intent);
        } else {
            Toast.makeText(this, "No hay app de cámara disponible", Toast.LENGTH_SHORT).show();
        }
    }
}
