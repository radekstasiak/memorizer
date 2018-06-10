package radev.com.memorizer.apiTranslator;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import javax.inject.Inject;

import radev.com.memorizer.AlarmScheduler;
import radev.com.memorizer.app.MemorizerApp;
import radev.com.memorizer.app.Settings;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Inject
    Settings settings;

    @Inject
    AlarmScheduler alarmScheduler;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MemorizerApp.getInstance().getComponent().inject(this);

        final Calendar c = Calendar.getInstance();
        int hour = settings.getCurrentAlarmHour();
        int minute = settings.getCurrentAlarmMinute();

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        settings.setCurrentAlarmHours(hourOfDay);
        settings.setCurrentAlarmMinute(minute);
        alarmScheduler.schedulerNextAlarm();
    }
}
