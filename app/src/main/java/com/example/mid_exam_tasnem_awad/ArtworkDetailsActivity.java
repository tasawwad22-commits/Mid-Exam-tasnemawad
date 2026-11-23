package com.example.mid_exam_tasnem_awad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ArtworkDetailsActivity extends AppCompatActivity {

    private TextView textDetailsInfo;
    private Button buttonVisitWebsite;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artwork_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detailsMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textDetailsInfo = findViewById(R.id.textDetailsInfo);
        buttonVisitWebsite = findViewById(R.id.buttonVisitWebsite);
        buttonBack = findViewById(R.id.buttonBack);

        Intent intent = getIntent();
        String title = intent.getStringExtra("EXTRA_TITLE");
        String artist = intent.getStringExtra("EXTRA_ARTIST");
        String year = intent.getStringExtra("EXTRA_YEAR");
        String webUrl = intent.getStringExtra("EXTRA_WEB_URL");

        String info = "Artwork Title: " + (title != null ? title : "") + "\n\n"
                + "Artist Name: " + (artist != null ? artist : "") + "\n\n"
                + "Creation Year: " + (year != null ? year : "");
        textDetailsInfo.setText(info);

        buttonVisitWebsite.setOnClickListener(v -> {
            String url = (webUrl != null && !webUrl.isEmpty()) ? webUrl : "https://en.wikipedia.org/wiki/Work_of_art";
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(webIntent);
        });

        buttonBack.setOnClickListener(v -> finish());
    }
}
