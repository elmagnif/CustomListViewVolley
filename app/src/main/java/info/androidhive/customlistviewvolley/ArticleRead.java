package info.androidhive.customlistviewvolley;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by apple on 06/01/15.
 */
public class ArticleRead extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Affichage Button Back
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.article_read);

        //Fin affichage Button Back
//        setContentView(R.layout.article_read);


       TextView txtId = (TextView) findViewById(R.id.product_id);
       Intent i = getIntent();
        //startActivity(i);
        // getting attached intent data
       String num = i.getStringExtra("numero");
        Log.d("Valeur Numero Recupéré ", "elsoft " + num);
        // displaying selected product name
       txtId.setText(num);



    }

    //Action Retour Button Back
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        overridePendingTransition(R.animator.slidefromleftin, R.animator.slidetorightout);

        return true;


    }

    //ENd button back


}
