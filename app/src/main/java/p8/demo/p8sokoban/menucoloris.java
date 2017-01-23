package p8.demo.p8sokoban;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class menucoloris extends Activity {

    public	Menu	mMenu;
    private boolean soundlanched = false;
    private menucoloris instance;
    private MediaPlayer mMediaPlayerbckgnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.coloris);

        final Button ButtonQuitter = (Button)findViewById(R.id.buttonquitter);
        final Button ButtonScores  = (Button)findViewById(R.id.buttonhighscores);
        final Button ButtonJouer   = (Button)findViewById(R.id.buttonJouer);
        final Button Buttonson   = (Button)findViewById(R.id.btson);

        mMediaPlayerbckgnd = MediaPlayer.create(instance.getBaseContext(), R.raw.bg_music);
        mMediaPlayerbckgnd.setLooping(true);

        ButtonQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ButtonScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ButtonJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(instance, coloris.class);
                startActivity(intent);
            }
        });
        Buttonson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundlanched = ! soundlanched;
                if (soundlanched) {
                    Buttonson.setText(R.string.btn_sonoui);
                    mMediaPlayerbckgnd.start();
                }
                else{
                    Buttonson.setText(R.string.btn_sonnon);
                    if(mMediaPlayerbckgnd.isPlaying()){
                        mMediaPlayerbckgnd.pause();
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:

                return true;
            case R.id.help:

                return true;
            default:
                return false;
        }
    }


    }



