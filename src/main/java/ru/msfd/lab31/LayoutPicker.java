package ru.msfd.lab31;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutPicker extends AppCompatActivity {

    Button selectLayoutButton = null;
    List<Integer> toggleIds = null;
    RadioGroup colorsRadioGroup = null;
    LinearLayout layoutsColumn = null;
    int selectedToggleId = -1;
    int layoutNumber = 1;

    final static int TOTAL_LAYOUTS = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layout_picker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Setup();
    }

    private void Setup()
    {
        layoutsColumn = (LinearLayout) findViewById(R.id.layouts_column_id);
        colorsRadioGroup = (RadioGroup) findViewById(R.id.colors_radio_group);
        selectLayoutButton = (Button) findViewById(R.id.select_layout_button);
        toggleIds = new ArrayList<>();

        for(int i = 0; i < TOTAL_LAYOUTS; i++) toggleIds.add(View.generateViewId());
        SetupToggleButtons();
        SetupButton();
    }

    private void SetupButton()
    {
        selectLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("layoutNumber", layoutNumber);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void SetupToggleButtons()
    {
        ToggleButton toggleButton;
        View.OnClickListener toggleButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleButton toggle = (ToggleButton) view;
                boolean state = toggle.isChecked();
                for(int id : toggleIds)
                {
                    ((ToggleButton) findViewById(id)).setChecked(false);
                }
                if(!state)
                {
                    ((ToggleButton) findViewById(toggleIds.get(0))).setChecked(true);
                    selectedToggleId = toggleIds.get(0);
                    layoutNumber = 0;
                }
                else
                {
                    toggle.setChecked(true);
                    selectedToggleId = toggle.getId();
                    layoutNumber = toggleIds.indexOf(selectedToggleId);
                }
            }
        };

        String baseText = "Layout ";
        int layoutNumber = 1;
        if(toggleIds != null)
        {
            for (int id : toggleIds)
            {
                toggleButton = new ToggleButton(this);
                toggleButton.setId(id);
                toggleButton.setText(baseText + layoutNumber);
                toggleButton.setTextOn(baseText + layoutNumber + " ON");
                toggleButton.setTextOff(baseText + layoutNumber + " OFF");
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                int marginInPixels = (int) (16 * getResources().getDisplayMetrics().density);
                layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
                layoutParams.gravity = Gravity.CENTER;

                toggleButton.setLayoutParams(layoutParams);
                toggleButton.setOnClickListener(toggleButtonOnClickListener);
                layoutsColumn.addView(toggleButton);
                layoutNumber++;
            }
        }
    }
}