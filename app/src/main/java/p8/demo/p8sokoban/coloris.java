package p8.demo.p8sokoban;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

// declaration de notre activity heritee de Activity
public class coloris extends Activity {

    private colorisview mColorisview;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // initialise notre activity avec le constructeur parent    	
        super.onCreate(savedInstanceState);
        // charge le fichier main.xml comme vue de l'activit�
        setContentView(R.layout.main);
     

        // recuperation de la vue une voie cree � partir de son id
        mColorisview = (colorisview)findViewById(R.id.SokobanView);
        // rend visible la vue
        mColorisview.setVisibility(View.VISIBLE);
    }																						
}