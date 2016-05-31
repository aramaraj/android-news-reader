package adalwin.com.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adalwin.com.fragments.DatePickerFragment;
import adalwin.com.models.Settings;
import adalwin.com.models.SortOrder;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{

    public Settings settings;
    StringBuilder sb = new StringBuilder(4);


    private ArrayAdapter<SortOrder> sortOrderAdapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    CheckBox cbArts;
    CheckBox cbSports;
    CheckBox cbFashion;
    EditText etStartdate;
    Spinner spinnerSort;
    Button btnSave;
    String desk;
    String beginDate;
    String sortOrder;
    List<String> selectedOptions;

    Map<Long, String> sortMap = new HashMap<>();
    {
        sortMap.put(0L, "newest");
        sortMap.put(1L, "oldest");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();// or getActionBar();
        String title = actionBar.getTitle().toString();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        settings = new Settings();
        cbArts=(CheckBox)findViewById(R.id.cbArts);
        cbSports = (CheckBox)findViewById(R.id.cbArts);
        cbFashion = (CheckBox)findViewById(R.id.cbArts);
        etStartdate = (EditText)findViewById(R.id.dtBeginDate);

        spinnerSort = (Spinner)findViewById(R.id.spinnerSort);
        btnSave= (Button)findViewById(R.id.btnSave);;
        sortOrderAdapter = new ArrayAdapter<SortOrder>(this, android.R.layout.simple_spinner_item, SortOrder.values());
        spinnerSort.setAdapter(sortOrderAdapter);
        etStartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                onSave(view);
            }
        });
        }
        // attach to an onclick handler to show the date picker
        public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
        }
        public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        selectedOptions=new ArrayList<String>(3);
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.cbArts:
                if (checked)
                    selectedOptions.add(String.valueOf(cbArts.getText()));
                else
                // Remove the meat
                break;
            case R.id.cbSports:
                if (checked)
                    selectedOptions.add(String.valueOf(cbSports.getText()));
                else
                // I'm lactose intolerant
                break;
            case R.id.cbFashion:
                if (checked)
                    selectedOptions.add(String.valueOf(cbFashion.getText()));
                else
                // I'm lactose intolerant
                break;
        }
    }
    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        beginDate=new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        etStartdate.setText(new SimpleDateFormat("MM-dd-yyyy").format(c.getTime()));

    }

    public void onSave(View view) {
        Intent intent = new Intent();
        settings.newsOptions = selectedOptions;
        settings.startDate = beginDate;
        settings.sortOrder = sortMap.get(((Spinner)findViewById(R.id.spinnerSort)).getSelectedItemId());
        intent.putExtra("settings",settings);
        setResult(RESULT_OK, intent);
        this.finish();

    }



}
