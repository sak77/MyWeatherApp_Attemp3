package com.saket.myweatherapp_attemp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.saket.myweatherapp_attemp3.ui.CityListFragment;
import com.saket.myweatherapp_attemp3.viewmodel.CityViewModel;

/**
 * 3rd attempt at weather app. Starting at 6.15am stopped at 7.24am
 * Next day spent another 1.5hrs. And now i have a working proto-type. Just over 3 hrs, not bad!
 * Well actually it took more time once i included repository package.
 *
 * Learnings -
 * Its not necessary to use Rxjava for handling multiple async cascading network api calls. Here i used only Retrofit and livedata.
 * I used transformations to map each city to the livedata list of cities which is observed by city list fragment.
 * Transformations.map can return only liveData instance and not MutableLiveData instance.
 * The transformation.map should only be set once otherwise each new map will override the previous map.
 * Use liveData to communicate between viewmodel and repository.
 * Transformations.map() has to be declared before creating observers for transformation. Otherwise
 * the observer will not be notified.
 *
 * ViewModelProvider constructor with single argument is available only in more recent versions of android lifecycle library. Had to add it separately.
 * Rxjava observables are only executed when they are subscribed.
 * Some Rxjava operators like fromIterable do not execute the observable. However others like merge do.
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pop-stack
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if (count > 0) {
                    getSupportFragmentManager().popBackStack();
                }
            }
        });

        //Load cityweather fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, CityListFragment.newInstance(),null)
                .commit();
    }

    public void displayNavIcon() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}