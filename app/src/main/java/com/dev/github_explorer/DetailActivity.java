package com.dev.github_explorer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.github_explorer.models.Repository;

public class DetailActivity extends AppCompatActivity {
     static final String EXTRA_REPO = "extra_repo";

    public static void start(Context ctx, Repository repo) {
        Intent i = new Intent(ctx, DetailActivity.class);
        i.putExtra(EXTRA_REPO, repo); // This will now work with Parcelable
        ctx.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Use getParcelableExtra for Parcelable objects
        Repository repo = (Repository) getIntent().getSerializableExtra(EXTRA_REPO);
        if (repo == null) {
            finish();
            return;
        }

        TextView nameTv = findViewById(R.id.full_name);
        TextView ownerTv = findViewById(R.id.owner);
        TextView descTv = findViewById(R.id.desc);
        TextView starsTv = findViewById(R.id.stars);
        TextView forksTv = findViewById(R.id.forks);
        TextView issuesTv = findViewById(R.id.issues);
        TextView langTv = findViewById(R.id.language);
        TextView updatedTv = findViewById(R.id.updated);

        nameTv.setText(repo.getFull_name());
        ownerTv.setText(repo.getOwner() != null ? repo.getOwner().getLogin() : "—");
        descTv.setText(repo.getDescription() != null ? repo.getDescription() : "No description");
        starsTv.setText(String.valueOf(repo.getStargazers_count()));
        forksTv.setText(String.valueOf(repo.getForks_count()));
        issuesTv.setText(String.valueOf(repo.getOpen_issues_count()));
        langTv.setText(repo.getLanguage() != null ? repo.getLanguage() : "—");
        updatedTv.setText(repo.getUpdated_at() != null ? repo.getUpdated_at() : "—");

        Button openBtn = findViewById(R.id.open_btn);
        openBtn.setOnClickListener(v -> {
            String url = repo.getHtml_url();
            if (url != null && !url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}