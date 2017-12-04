package dbzgroup.mytube;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import dbzgroup.mytube.Model.Video;
import dbzgroup.mytube.Model.VideoAdapter;

public class NavigationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    VideoAdapter adapter;
    List<Video> videoList;

    private TextView mTextMessage;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) { //Fragment Selector
                case R.id.navigation_search:
                    return true;
                case R.id.navigation_favorites:
                    return true;
                case R.id.navigation_logout:
                    mAuth.signOut();
                    Toast.makeText(NavigationActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // For RecyclerView
        videoList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        videoList.add(new Video("Shinobu", "62000", "www.youtube.com", "12/03/2017"));
        videoList.add(new Video("Combat Arms", "21000", "www.youtube.com", "12/03/2007"));

        adapter = new VideoAdapter(this, videoList);
        recyclerView.setAdapter(adapter);


        //For Logging Out
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Logout Listener
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(NavigationActivity.this, SignIn.class));
                }

            }
        };
    }
}
