package com.fourstars.gosilent.gosilent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fourstars.gosilent.gosilent.R;
import com.fourstars.gosilent.gosilent.databaseanddao.DatabaseHandler;
import com.fourstars.gosilent.gosilent.databaseanddao.LocationBox;
import com.fourstars.gosilent.gosilent.databaseanddao.MyApplication;

/**
 * Created by Jayant on 29-06-2017.
 */

public class SetLocationBoxProps extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    LocationBox mLocationBox;

    private EditText meditText;
    private Button mbutton4;
    private Button mbutton5;
    private RadioGroup mradiogroup;
    private RadioButton mradiobutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlocationboxprops);

        databaseHandler = new DatabaseHandler(this);
        mLocationBox = new LocationBox();

        meditText = (EditText) findViewById(R.id.editText);
        mbutton4 = (Button) findViewById(R.id.button4);
        mbutton5 = (Button) findViewById(R.id.button5);
        mradiogroup = (RadioGroup) findViewById(R.id.radiogroup);



        mLocationBox = ((MyApplication)this.getApplication()).getMyLocationBox();

        mbutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationBox.setName(meditText.getText().toString());
                int selectedid=mradiogroup.getCheckedRadioButtonId();
                mradiobutton=(RadioButton)findViewById(selectedid);
                mLocationBox.setMode(mradiobutton.getText().toString());
                mLocationBox.setStatus("OFF");
                Log.e("msg",mLocationBox.toString());
                databaseHandler.save(mLocationBox);
                Toast.makeText(SetLocationBoxProps.this,"Location Box Successfully Saved.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SetLocationBoxProps.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mbutton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetLocationBoxProps.this,PolygonActivity.class);
                startActivity(intent);
                finish();



            }
        });

    }
}
