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

public class ColorPicker extends AppCompatActivity {

    Button selectColorButton = null;
    RadioGroup colorsRadioGroup = null;
    Map<Integer, Integer> idsColors = null;
    int selectedColor = -1;

    int[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, -1 };
    Map<Integer, String> colorsLabels = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_color_picker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Setup();
    }

    private void Setup()
    {
        colorsRadioGroup = (RadioGroup) findViewById(R.id.colors_radio_group);
        selectColorButton = (Button) findViewById(R.id.select_color_button);

        idsColors = new HashMap<>();
        colorsLabels = new HashMap<>();

        SetupLabels();
        SetupRadioButtons();
        SetupButton();
    }

    private void SetupLabels()
    {
        if(colorsLabels != null)
        {
            colorsLabels.put(Color.RED, getString(R.string.red_label));
            colorsLabels.put(Color.GREEN, getString(R.string.green_label));
            colorsLabels.put(Color.BLUE, getString(R.string.blue_label));
            colorsLabels.put(Color.YELLOW, getString(R.string.yellow_label));
            colorsLabels.put(-1, getString(R.string.random_color_label));

        }
    }

    private void SetupRadioButtons()
    {
        RadioButton radioButton;

        int id;
        for(int color : colors)
        {
            radioButton = new RadioButton(this);
            id = View.generateViewId();
            idsColors.put(id, color);
            radioButton.setId(id);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            int marginInPixels = (int) (16 * getResources().getDisplayMetrics().density);
            layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
            layoutParams.gravity = Gravity.CENTER;

            radioButton.setLayoutParams(layoutParams);
            if(colorsLabels != null) radioButton.setText(colorsLabels.get(color));
            if(color == -1) radioButton.setChecked(true);
            colorsRadioGroup.addView(radioButton);
        }
        colorsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(idsColors != null) selectedColor = idsColors.getOrDefault(i, -1);
                else selectedColor = -1;
            }
        });
    }

    private void SetupButton()
    {
        selectColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("color", selectedColor);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}