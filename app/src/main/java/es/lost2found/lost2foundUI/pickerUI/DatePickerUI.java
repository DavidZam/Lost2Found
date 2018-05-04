package es.lost2found.lost2foundUI.pickerUI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import es.lost2found.R;

public class DatePickerUI extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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
        EditText date = (EditText) getActivity().findViewById(R.id.date_show);
        String yearT = String.valueOf(year);
        String monthT = String.valueOf(month+1);
        String dayT = String.valueOf(day);
        date.setText(dayT + "/" + monthT + "/" + yearT);
        date.setTextSize(15);
    }

}
