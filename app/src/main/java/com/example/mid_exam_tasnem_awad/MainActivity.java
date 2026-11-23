package com.example.mid_exam_tasnem_awad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static class Artwork {
        private final int imageResId;
        private final String title;
        private final String artist;
        private final String year;
        private final String webUrl;

        public Artwork(int imageResId, String title, String artist, String year, String webUrl) {
            this.imageResId = imageResId;
            this.title = title;
            this.artist = artist;
            this.year = year;
            this.webUrl = webUrl;
        }

        public int getImageResId() {
            return imageResId;
        }

        public String getTitle() {
            return title;
        }

        public String getArtist() {
            return artist;
        }

        public String getYear() {
            return year;
        }

        public String getWebUrl() {
            return webUrl;
        }
    }

    private Artwork[] artworks;
    private int currentIndex = 0;

    private TextView textPosition;
    private ImageView imageView;
    private TextView textArtworkTitle;
    private TextView textArtist;
    private Button buttonPrevious;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize 6 real JPG image artworks
        artworks = new Artwork[]{
                new Artwork(R.drawable._15, "Mona Lisa", "Leonardo da Vinci", "1503", "https://en.wikipedia.org/wiki/Mona_Lisa"),
                new Artwork(R.drawable._69, "The Starry Night", "Vincent van Gogh", "1889", "https://en.wikipedia.org/wiki/The_Starry_Night"),
                new Artwork(R.drawable.images__1_, "The Card Players", "Paul Cézanne", "حوالي 1890–1895", "https://en.wikipedia.org/wiki/The_Persistence_of_Memory"),
                new Artwork(R.drawable._5, "The Scream", "Edvard Munch", "1893", "https://en.wikipedia.org/wiki/The_Scream"),
                new Artwork(R.drawable._999, "Outside the Gare Saint-Lazare, Sunlight Effect", "Claude Monet", "1877", "https://en.wikipedia.org/wiki/Girl_with_a_Pearl_Earring"),
                new Artwork(R.drawable.images__2_, "The Card Players1", "Paul Cézanne (بول سيزان)", "حوالي 1890–1892", "https://en.wikipedia.org/wiki/The_Night_Watch")
        };

        // Bind Views
        textPosition = findViewById(R.id.textPosition);
        imageView = findViewById(R.id.imageView);
        textArtworkTitle = findViewById(R.id.textArtworktitle);
        textArtist = findViewById(R.id.textArtist);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("CURRENT_INDEX", 0);
        }

        // Set Click Listeners
        buttonNext.setOnClickListener(v -> {
            if (currentIndex < artworks.length - 1) {
                currentIndex++;
                updateUI();
            }
        });

        buttonPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateUI();
            }
        });

        // Make artwork ImageView clickable to launch ArtworkDetailsActivity via Explicit Intent
        imageView.setOnClickListener(v -> {
            Artwork currentArtwork = artworks[currentIndex];
            Intent intent = new Intent(MainActivity.this, ArtworkDetailsActivity.class);
            intent.putExtra("EXTRA_TITLE", currentArtwork.getTitle());
            intent.putExtra("EXTRA_ARTIST", currentArtwork.getArtist());
            intent.putExtra("EXTRA_YEAR", currentArtwork.getYear());
            intent.putExtra("EXTRA_IMAGE_RES_ID", currentArtwork.getImageResId());
            intent.putExtra("EXTRA_WEB_URL", currentArtwork.getWebUrl());
            startActivity(intent);
        });

        // Initial UI Update
        updateUI();
    }

    private void updateUI() {
        Artwork currentArtwork = artworks[currentIndex];
        textPosition.setText(String.format(Locale.getDefault(), "%d / %d", currentIndex + 1, artworks.length));
        imageView.setImageResource(currentArtwork.getImageResId());
        textArtworkTitle.setText(currentArtwork.getTitle());
        String artistAndYear = currentArtwork.getArtist() + " (" + currentArtwork.getYear() + ")";
        textArtist.setText(artistAndYear);

        // Requirement 2: Disable Previous on first artwork, disable Next on last artwork
        buttonPrevious.setEnabled(currentIndex > 0);
        buttonNext.setEnabled(currentIndex < artworks.length - 1);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CURRENT_INDEX", currentIndex);
    }
}
