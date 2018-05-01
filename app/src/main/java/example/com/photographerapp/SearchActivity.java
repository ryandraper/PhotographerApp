package example.com.photographerapp;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SearchActivity extends AppCompatActivity {

    public static final String START_DATE_TAG = "START_DATE_TAG";
    public static final String END_DATE_TAG = "END_DATE_TAG";

    static TextView startText;
    static TextView endText;
    static Long startDateLong;
    static Long endDateLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        startText = (TextView) findViewById(R.id.startDateText);
        endText = (TextView) findViewById(R.id.endDateText);
    }

    public void showStartDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), START_DATE_TAG);
    }


    public void showEndDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), END_DATE_TAG);
    }

    public void cancel(final View v) {
        finish();
    }

    public void search(final View v) {
        Intent i = new Intent();
        i.putExtra("STARTDATE", startDateLong);
        i.putExtra("ENDDATE", endDateLong);
        setResult(RESULT_OK, i);
        finish();
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String theTag = getTag();
            if( theTag == START_DATE_TAG){
                Date date = new GregorianCalendar(year, month, day).getTime();
                startDateLong = date.getTime();
                startText.setText(year+"/"+month+"/"+day);

            }else if( theTag == END_DATE_TAG ){
                Date date = new GregorianCalendar(year, month, day).getTime();
                endDateLong = date.getTime();
                endText.setText(year+"/"+month+"/"+day);
            }
        }


    }


}
