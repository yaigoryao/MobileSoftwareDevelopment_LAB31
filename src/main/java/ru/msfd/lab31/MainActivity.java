package ru.msfd.lab31;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textView = null;
    EditText editText = null;
    Button colorsMenuButton = null;
    Button layoutsMenuButton = null;

    final static int COLOR_PICKER_MENU_OPEN = 1;
    final static int LAYOUT_PICKER_MENU_OPEN = 2;

    int currentColor = 1;
    String currentText = "";

    ActivityResultLauncher<Intent> startColorPickerActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK)
                    {
                        Intent data = o.getData();
                        currentColor = data.getIntExtra("color", -1);
                        Integer[] textViewsIds = { R.id.first_layout_text_view, R.id.second_layout_text_view, R.id.third_layout_text_view };

                        if(currentColor == -1)
                        {
                            Random random = new Random();
                            currentColor = Color.rgb(random.nextInt(256),
                                    random.nextInt(256),
                                    random.nextInt(256) );
                        }
//                        else ((TextView) findViewById(textViewsIds[data.getIntExtra("layoutNumber", 0)])).setTextColor(currentColor);
                        for(Integer id : textViewsIds)
                            ((TextView) findViewById(id)).setTextColor(currentColor);
                    }

                }
            });

    ActivityResultLauncher<Intent> startLayoutPickerActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK)
                    {
                        Intent data = o.getData();
                        //int layoutId = R.layout.layout1;
                        Integer[] layoutsIds = { R.id.first_layout, R.id.second_layout, R.id.third_layout };
                        for(Integer id : layoutsIds) ((LinearLayout) findViewById(id)).setVisibility(View.INVISIBLE);
                        ((LinearLayout) findViewById(layoutsIds[data.getIntExtra("layoutNumber", 0)])).setVisibility(View.VISIBLE);
//                        switch (data.getIntExtra("layoutNumber", 0))
//                        {
//                            case 0: layoutId = R.layout.layout1; break;
//                            case 1: layoutId = R.layout.layout2; break;
//                            case 2: layoutId = R.layout.layout3; break;
//                        }
                        //setContentView(layoutId);
                        //Setup();
                        Integer[] textViewsIds = { R.id.first_layout_text_view, R.id.second_layout_text_view, R.id.third_layout_text_view };
                        for( Integer id : textViewsIds)
                        {
                            ((TextView) findViewById(id)).setTextColor(currentColor);
                            ((TextView) findViewById(id)).setText(currentText);
                        }

                    }

                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Setup();
    }

    private void Setup()
    {
        //textView = (TextView) findViewById(R.id.main_text_view);
        editText = (EditText) findViewById(R.id.main_edit_text);
        colorsMenuButton = (Button) findViewById(R.id.colors_menu_button);
        layoutsMenuButton = (Button) findViewById(R.id.layouts_menu_button);

        SetupText();
        SetupButtons();
    }

    private void SetupButtons()
    {
        colorsMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.colorpicker");
                startColorPickerActivityForResult.launch(intent);
               // startActivityForResult(intent, COLOR_PICKER_MENU_OPEN);
            }
        });

        layoutsMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.colorpicker"
                );
                startLayoutPickerActivityForResult.launch(intent);
                //startActivityForResult(intent, LAYOUT_PICKER_MENU_OPEN);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        if( resultCode == RESULT_OK && data != null )
//        {
//            switch (requestCode)
//            {
//                case COLOR_PICKER_MENU_OPEN:
//                    currentColor = data.getIntExtra("color", -1);
//                    if(currentColor == -1)
//                    {
//                        Random random = new Random();
//                        currentColor = Color.rgb(random.nextInt(256),
//                                random.nextInt(256),
//                                random.nextInt(256) );
//                        textView.setTextColor(currentColor);
//                    }
//                    else textView.setTextColor(currentColor);
//                    break;
//                case LAYOUT_PICKER_MENU_OPEN:
//                    int layoutId = R.layout.layout1;
//
//                    switch (data.getIntExtra("layoutNumber", 0))
//                    {
//                        case 0: layoutId = R.layout.layout1; break;
//                        case 1: layoutId = R.layout.layout2; break;
//                        case 2: layoutId = R.layout.layout3; break;
//                    }
//                    setContentView(layoutId);
//                    Setup();
//
//                    textView.setTextColor(currentColor);
//                    textView.setText(currentText);
//                    break;
//            }
//        }
//    }

    private void SetupText()
    {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer[] textViewsIds = { R.id.first_layout_text_view, R.id.second_layout_text_view, R.id.third_layout_text_view };
                currentText = charSequence.toString();
                for(Integer id : textViewsIds) ((TextView) findViewById(id)).setText(currentText);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}