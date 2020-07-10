package com.example.photoalbum;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity implements OnStateChanged,OnButtonPressListener,OnCheckChanged {
    Button m_next;
    Button m_previous;
    public static FragmentManager fragmentManager;
    listFragment lstfrg;
    int position=0;
    int animals[] = {R.drawable.animal13, R.drawable.animal14,
            R.drawable.animal15,
            R.drawable.animal16, R.drawable.animal17,
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstfrg=new listFragment();
        fragmentManager = getSupportFragmentManager();
        if(findViewById(R.id.container) != null) {

            if(savedInstanceState != null) {


                return;
            }
            //code for managing fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            imageFragment imgfrag = imageFragment.newInstance("data",0);
            fragmentTransaction.add(R.id.container, imgfrag, null);
            fragmentTransaction.commit();
            //disabling the previous button
            m_previous=findViewById(R.id.previous);
            m_previous.setEnabled(false);

        }

    }

    //code for the checkbox gallery-list view
    @Override
    public void onChanged(int state) {
        // when the gallery checkbox is checked
        if(state==1){
            //existing fragment is replaced with list fragment
            fragmentManager.beginTransaction().replace(R.id.container,lstfrg).commit();
        }
        // when the gallery checkbox is unchecked
        else{
            //list fragment is replaced with image fragment
            imageFragment imgfrg=imageFragment.newInstance("data",0);
            fragmentManager.beginTransaction().replace(R.id.container,imgfrg).commit();
        }
    }

    //code for the next and previous buttons
    @Override
    public void onButtonPressed(int i) {
        //code for next button
        if(i==2){
            m_next=findViewById(R.id.next);
            imageFragment imgfrg= imageFragment.newInstance("data",position);
            fragmentManager.beginTransaction().replace(R.id.container, imgfrg, null).addToBackStack(null).commit();
            if(position!=0) {
                m_previous.setEnabled(true);
            }
            if(position<animals.length) {
                m_next.setEnabled(true);
                position++;

            }
            if(position>=animals.length){
                m_next.setEnabled(false);
                position = animals.length-1;
            }

        }
        //code for previous button
        else{
            m_previous=findViewById(R.id.previous);
            if(position>0) {
                m_previous.setEnabled(true);
                if(position==animals.length) {
                    m_next.setEnabled(false);
                }
                else{
                    m_next.setEnabled(true);
                }

                position--;
            }
            else if(position<=0) {
                position=0;
                m_previous.setEnabled(false);
            }

            imageFragment imgfrg=imageFragment.newInstance("data",position);
            fragmentManager.beginTransaction().replace(R.id.container, imgfrg, null).commit();
        }
    }
    //code for the slide show checkbox
    @Override
    public void OnChecked(int state) {
        //slide show checkbox is checked
        if(state==1){
            int a;
            position=0;
            //handler is used to have a delay
            Handler handler1 = new Handler();
            for(a=0;a<animals.length;a++) {

                handler1.postDelayed(new Runnable(){
                    public void run() {

                        imageFragment imgfrg=imageFragment.newInstance("data",position);
                        fragmentManager.beginTransaction().replace(R.id.container, imgfrg, null).addToBackStack(null).commit();
                        position=position+1;

                    }
                    },3000*a);

            }
        }
        //slide show checkbox is unchecked
        else{
            position=0;
            imageFragment imgfrg=imageFragment.newInstance("data",position);
            fragmentManager.beginTransaction().replace(R.id.container, imgfrg, null).addToBackStack(null).commit();
        }

    }
}
