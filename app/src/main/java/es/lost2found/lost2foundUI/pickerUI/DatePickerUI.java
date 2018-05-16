package es.lost2found.lost2foundUI.pickerUI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import es.lost2found.R;

public class DatePickerUI extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if(getActivity() != null) {
            return new DatePickerDialog(getActivity(), this, year, month, day);
        } else {
            return null;
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if(getActivity().findViewById(R.id.date_show) != null) {
            EditText date =  getActivity().findViewById(R.id.date_show);
            String yearT = String.valueOf(year);
            String monthT = String.valueOf(month+1);
            String dayT = String.valueOf(day);

            date.setText(String.format("%s/%s/%s", yearT, monthT, dayT));
            date.setText(String.format("%s/%s/%s", dayT, monthT, yearT));
            date.setTextSize(15);
        }
    }

}
