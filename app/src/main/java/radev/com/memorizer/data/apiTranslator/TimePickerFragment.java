package radev.com.memorizer.data.apiTranslator;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import javax.inject.Inject;

import radev.com.memorizer.AlarmHelper;
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

    @Inject
    AlarmHelper alarmHelper;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MemorizerApp.getInstance().getComponent().inject(this);

        int hour = settings.getCurrentAlarmHour();
        int minute = settings.getCurrentAlarmMinute();

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        alarmScheduler.cancelCurrentAlarm();
        alarmHelper.saveAlarmDetails(hourOfDay, minute);
        alarmScheduler.schedulerNextAlarm();
    }
}
