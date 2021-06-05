package com.example.dam_proiect_var_activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dam_proiect_var_activity.asyncTask.AsyncTaskRunner;
import com.example.dam_proiect_var_activity.asyncTask.Callback;
import com.example.dam_proiect_var_activity.database.service.CardService;
import com.example.dam_proiect_var_activity.database.service.TransactionService;
import com.example.dam_proiect_var_activity.model.Card;
import com.example.dam_proiect_var_activity.model.Transaction;
import com.example.dam_proiect_var_activity.network.HttpManager;
import com.example.dam_proiect_var_activity.util.AddCardActivity;
import com.example.dam_proiect_var_activity.util.DiscountCouponsActivity;
import com.example.dam_proiect_var_activity.util.JsonParser;
import com.example.dam_proiect_var_activity.util.Login;
import com.example.dam_proiect_var_activity.util.PartnerBankActivity;
import com.example.dam_proiect_var_activity.util.TransactionActivity;
import com.example.dam_proiect_var_activity.util.adapters.CardAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_CARD_REQUEST_CODE = 230;
    public static final String TRANSACTII_KEY = "TRANSACTII_KEY";
    private static final int UPDATE_CARD_REQUEST_CODE = 233;
    public static final String OWNER_ID = "owner_id";
    public static final String SELECT_THE_ACTION = "Select The Action";
    public static final String DELETE = "Delete";
    public static final String TRANSACTIONS = "Transactions";

    private ImageButton IbAdd;
    private ImageButton IbBanks;
    private ImageButton IbCoupons;
    private ListView lvCards;
    private List<Card> cards = new ArrayList<>();
    private CardService cardService;
    private TransactionService transactionService;
    private ImageView ivLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        registerForContextMenu(lvCards);
        transactionService = new TransactionService(getApplicationContext());
        cardService = new CardService(getApplicationContext());
        cardService.getAll(getAllfromDbCallback());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menu.setHeaderTitle(SELECT_THE_ACTION);
        AdapterView.AdapterContextMenuInfo adapterContext = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.add(DELETE).setOnMenuItemClickListener(DeleteEvListener(adapterContext));
        menu.add(TRANSACTIONS).setOnMenuItemClickListener(TransactionsEvListener(adapterContext));
    }

    private MenuItem.OnMenuItemClickListener TransactionsEvListener(AdapterView.AdapterContextMenuInfo adapterContext) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                transactionService.getAllWithId(getAllWithIdDbCallback(cards.get(adapterContext.position).getCard_id()), adapterContext.position);
                return false;
            }
        };
    }

    private MenuItem.OnMenuItemClickListener DeleteEvListener(AdapterView.AdapterContextMenuInfo adapterContext) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                cardService.delete(deleteToDbCallback(adapterContext.position),cards.get(adapterContext.position));
                return false;
            }
        };
    }

    private Callback<List<Transaction>> getAllWithIdDbCallback(long owner_id) {
      return new Callback<List<Transaction>>() {
          @Override
          public void runResultOnUiThread(List<Transaction> result) {
              if (result != null){
                  Intent intent = new Intent(getApplicationContext(), TransactionActivity.class);
                  intent.putExtra(OWNER_ID,owner_id);
                  intent.putExtra(TRANSACTII_KEY, (Serializable) result);
                  startActivity(intent);
              }
          }
      };
    }

    private Callback<Integer> deleteToDbCallback(int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if ( result != -1){
                    cards.remove(position);
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<List<Card>> getAllfromDbCallback() {
        return new Callback<List<Card>>() {
            @Override
            public void runResultOnUiThread(List<Card> result) {
                if (result != null){
                    cards.clear();
                    cards.addAll(result);
                    notifyAdapter();
                }
            }
        };
    }

    private void initComponents() {
        lvCards = findViewById(R.id.lv_main_card);
        IbAdd = findViewById(R.id.btn_main_add);
        IbBanks = findViewById(R.id.btn_main_banks);
        IbCoupons = findViewById(R.id.btn_main_coupons);
        ivLogout = findViewById(R.id.iv_main_logout);
        addCardAdapter();
        IbAdd.setOnClickListener(getAddCardEventListener());
        IbBanks.setOnClickListener(getPartnerBanksEventListener());
        lvCards.setOnItemClickListener(updateEventListener());
        ivLogout.setOnClickListener(logoutEventListener());
        IbCoupons.setOnClickListener(getCouponEventListener());
    }

    private View.OnClickListener logoutEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        };
    }

    private View.OnClickListener getCouponEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiscountCouponsActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getPartnerBanksEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartnerBankActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //daca e ok obiectul, il preluam
        if(resultCode == RESULT_OK && data != null){
            Card card = (Card) data.getSerializableExtra(AddCardActivity.CARD_KEY);
            //daca req code este pt add => facem inserare
          if(requestCode == ADD_CARD_REQUEST_CODE){
              cardService.insert(insertIntoDbCallback(), card);
              //daca req code este pt update => facem update
          } else if (requestCode == UPDATE_CARD_REQUEST_CODE){
              cardService.update(updateIntoDbCallback(), card);
          }
        }
    }

    private Callback<Card> updateIntoDbCallback() {
        return new Callback<Card>() {
            @Override
            public void runResultOnUiThread(Card result) {
                if (result != null){
                    for (Card card : cards){
                        if(card.getCard_id() == result.getCard_id()) {
                            card.setFirstName(result.getFirstName());
                            card.setSecondName(result.getSecondName());
                            card.setExpirationMonth(result.getExpirationMonth());
                            card.setExpirationYear(result.getExpirationYear());
                            card.setSerialNo(result.getSerialNo());
                            card.setCurrent_balance(result.getCurrent_balance());
                            card.setType(result.getType());
                            break;
                        }
                    }
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Card> insertIntoDbCallback() {
        return new Callback<Card>() {
            @Override
            public void runResultOnUiThread(Card result) {
                if (result != null){
                    cards.add(result);
                    notifyAdapter();
                }
            }
        };
    }

    private AdapterView.OnItemClickListener updateEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddCardActivity.class);
                intent.putExtra(AddCardActivity.CARD_KEY, cards.get(position));
                startActivityForResult(intent, UPDATE_CARD_REQUEST_CODE);
            }
        };
    }

    private View.OnClickListener getAddCardEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddCardActivity.class);
                startActivityForResult(intent, ADD_CARD_REQUEST_CODE);
            }
        };
    }

    private void addCardAdapter() {
        CardAdapter adapter = new CardAdapter(getApplicationContext(), R.layout.activity_card_view,
                cards, getLayoutInflater());
        lvCards.setAdapter(adapter);
    }

    private void notifyAdapter(){
        CardAdapter adapter = (CardAdapter) lvCards.getAdapter();
        adapter.notifyDataSetChanged();
    }

}