package com.example.dam_proiect_var_activity.util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dam_proiect_var_activity.R;
import com.example.dam_proiect_var_activity.model.Card;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddCardActivity extends AppCompatActivity {
    public static final String CARD_KEY = "card_key";
    private Intent intent;

    private TextInputEditText tietExpirationMonth;
    private TextInputEditText tietExpirationYear;
    private TextInputEditText tietSerialNo;
    private TextInputEditText tietCurrentBalance;
    private TextInputEditText tietFirstName;
    private TextInputEditText tietSecondName;
    private Spinner spnType;
    private Button btnSave;

    private Card card = null;
    private Button btnRemember2;
    public static final String SHARED_PREF = "shared pref";

    public static final String FIRST_NAME = "firstname";
    public static final String SECOND_NAME = "secondname";
    private String fName;
    private String sName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        initComponents();
        intent = getIntent();
        //daca am primit cheia de obiect
        if (intent.hasExtra(CARD_KEY)){
            card = (Card) intent.getSerializableExtra(CARD_KEY);
            buildViewsFromCard(card);
        }
        btnRemember2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        if (!intent.hasExtra(CARD_KEY)){
            loadData();
            updateViews();}
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIRST_NAME,tietFirstName.getText().toString());
        editor.putString(SECOND_NAME,tietSecondName.getText().toString());
        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        fName= sharedPreferences.getString(FIRST_NAME,"");
        sName= sharedPreferences.getString(SECOND_NAME,"");
    }
    public void updateViews(){
        tietFirstName.setText(fName);
        tietSecondName.setText((sName));
    }

    //sa populeze campurile din formular
    private void buildViewsFromCard(Card card) {
        if (card == null){
            return;
        }
        tietExpirationMonth.setText(String.valueOf(card.getExpirationMonth()));
        tietExpirationYear.setText(String.valueOf(card.getExpirationYear()));

        if (card.getFirstName() != null){
            tietFirstName.setText(card.getFirstName());
        }
        if (card.getSecondName() != null){
            tietSecondName.setText(card.getSecondName());
        }
        if (card.getCurrent_balance()  >=0){
            tietCurrentBalance.setText(String.valueOf(card.getCurrent_balance()));
        }

        if (card.getSerialNo() >=0){
            tietSerialNo.setText( String.valueOf(card.getSerialNo()));
        }

        selectType(card);

    }

    private void selectType(Card card) {
        ArrayAdapter adapter = (ArrayAdapter) spnType.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            String item = (String) adapter.getItem(i);
            if (item != null && item.equals(card.getType())) {
                spnType.setSelection(i);
                break;
            }
        }
    }

    private void initComponents() {
        btnSave = findViewById(R.id.add_card_btn_save);
        tietExpirationMonth = findViewById(R.id.add_tiet_card_expirationMonth);
        tietExpirationYear = findViewById(R.id.add_tiet_card_expirationYear);
        tietSerialNo = findViewById(R.id.add_tiet_card_serialNo);
        tietCurrentBalance = findViewById(R.id.add_acount_tiet_currBalance);
        tietFirstName = findViewById(R.id.add_account_tiet_firstName);
        tietSecondName = findViewById(R.id.add_account_tiet_secondName);
        spnType = findViewById(R.id.spn_add_accountType);
        btnRemember2 = findViewById(R.id.btn_remember2);
        addTypeAdapter();

        btnSave.setOnClickListener(saveCardEventListener());
    }

    private View.OnClickListener saveCardEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validInputs()){
                    createFromViews();
                    intent.putExtra(CARD_KEY, card);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private boolean validInputs() {
        if (tietFirstName.getText() == null ||  tietFirstName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    R.string.add_card_firstName_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietSecondName.getText() == null ||  tietSecondName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    R.string.add_card_firstName_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietCurrentBalance.getText() == null || tietCurrentBalance.getText().toString().isEmpty()
                || Double.parseDouble(tietCurrentBalance.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_card_currBalance_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietExpirationMonth.getText() == null || tietExpirationMonth.getText().toString().isEmpty()
                || Integer.parseInt(tietExpirationMonth.getText().toString()) > 12 || Integer.parseInt(tietExpirationMonth.getText().toString()) < 1 ) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_card_expMonth_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietExpirationYear.getText() == null || tietExpirationYear.getText().toString().isEmpty()
                || Integer.parseInt(tietExpirationYear.getText().toString()) < 2019 ) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_card_expYear_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietSerialNo.getText() == null || tietSerialNo.getText().toString().isEmpty()
                || Integer.parseInt(tietSerialNo.getText().toString()) < 0 ) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_card_Serialno_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }


    //creare obiect prin apasare save din formular
    private void createFromViews() {
        int expirationMonth = Integer.parseInt(tietExpirationMonth.getText().toString());
        int expirationYear = Integer.parseInt(tietExpirationYear.getText().toString());
        int serialNo =Integer.parseInt(tietSerialNo.getText().toString());
        double currentBalance = Double.parseDouble(tietCurrentBalance.getText().toString());
        String firstName = tietFirstName.getText().toString();
        String secondName = tietSecondName.getText().toString();
        String type = spnType.getSelectedItem().toString();

        //daca ob e gol, facem adaugare
        if( card == null){
            card = new Card(firstName,secondName,expirationMonth,expirationYear,serialNo,type,currentBalance);
        } else {
            //daca ob nu este null, facem update
            card.setExpirationMonth(expirationMonth);
            card.setExpirationYear(expirationYear);
            card.setSerialNo(serialNo);
            card.setCurrent_balance(currentBalance);
            card.setFirstName(firstName);
            card.setSecondName(secondName);
            card.setType(type);
        }
    }

    private void addTypeAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.add_card_type_values,
                android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(adapter);
    }
}
