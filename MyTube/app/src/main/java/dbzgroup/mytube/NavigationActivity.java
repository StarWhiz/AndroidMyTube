package dbzgroup.mytube;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import dbzgroup.mytube.Model.Video;
import dbzgroup.mytube.Model.VideoAdapter;

public class NavigationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private List<Video> videoList;

    private List<Video> searchResults;
    private EditText searchInput;
    private Handler handler;


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
        ////videoList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ////videoList.add(new Video("Shinobu", "62000", "www.youtube.com", "12/03/2017"));
        ////videoList.add(new Video("Combat Arms", "21000", "www.youtube.com", "12/03/2007"));

        ////adapter = new VideoAdapter(this, videoList);
        ////recyclerView.setAdapter(adapter);


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

        // For Search
        searchInput = findViewById(R.id.searchInput);
        handler = new Handler();

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    System.out.println("True Condition Occurred.");
                    searchOnYoutube(v.getText().toString());
                    return false;
                }
                System.out.println("Else Condition Occurred.");
                System.out.println(v.getText().toString());
                //searchOnYoutube(v.getText().toString());
                return true;
            }
        });

    }

    private void searchOnYoutube(final String keywords) {
        new Thread(){
            @Override
            public void run() {
                System.out.println("searchOnYoutubeFunctionCalled RUNNING");
                YoutubeConnector yc = new YoutubeConnector(NavigationActivity.this);
                videoList = yc.search(keywords);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateVideosFound();
                    }
                });
            }
        }.start();
        System.out.println("searchOnYoutubeFunctionCalled");
        System.out.println(videoList);

    }

    private void updateVideosFound(){
        adapter = new VideoAdapter(this, videoList);
        System.out.println(videoList);
        recyclerView.setAdapter(adapter);

        /*
        ArrayAdapter<VideoItem> adapter = new ArrayAdapter<VideoItem>(getApplicationContext(), R.layout.video_item, searchResults){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                TextView description = (TextView)convertView.findViewById(R.id.video_description);

                VideoItem searchResult = searchResults.get(position);

                Picasso.with(getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                description.setText(searchResult.getDescription());
                return convertView;
            }
        };*/

    }
}
