package com.example.welkon;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.welkon.Utils.MainDBHelper2;
import com.example.welkon.quiz.QuizAnswer;
import com.example.welkon.quiz.QuizQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.welkon.BasicScan.KEY_FOR_NUMBER_OF_QUIZ;

public class QuizActivity extends AppCompatActivity {

    private ArrayList<String> questionArray = new ArrayList<>();
    private ArrayList<Boolean> resArray = new ArrayList<>();
    TextView tvQuestion;
    TextView tvResult;

    private MainDBHelper2 dbHelper2;
    boolean[] checkedAnswers;
    int selected[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide();
        // получаем элемент ListView
        final ListView lvAnswers = (ListView) findViewById(R.id.lvAnswers);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvResult   = (TextView) findViewById(R.id.tvResult);
        //---------------------------------------------------------------

        //---------------------------------------------------------------

        Intent intent = getIntent();
        String keyQuestion = intent.getStringExtra(KEY_FOR_NUMBER_OF_QUIZ);
        if (keyQuestion != null) {
            // получаем ресурс
            int key = Integer.parseInt(keyQuestion);

            //------------------------------------------
            QuizQuestion quizQuestion = setQuestions(key);
            String text = quizQuestion.getQuestion();
            tvQuestion.setText(text);
            //---------------------------------------------

            //---------------------------------------------------------------
            List<QuizAnswer> answerList = setQListAnswer(key);
            resArray.clear();

            for (int i = 0; i < answerList.size(); i++) {
                questionArray.add(answerList.get(i).getAnswer());
                resArray.add(answerList.get(i).isResult());
            }
            //---------------------------------------------------------------


            lvAnswers.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            // создаем адаптер
            ArrayAdapter<String> adapter = new ArrayAdapter(this,
                    R.layout.simple_list_item_multiple_choice, questionArray);

            checkedAnswers = new boolean[answerList.size()];
            Arrays.fill(checkedAnswers,false);

            // устанавливаем для списка адаптер
            lvAnswers.setAdapter(adapter);
            lvAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    // Очистим TextView перед вставкой нового контента.
                    tvResult.setText("");

                    int k=0;



                    SparseBooleanArray sparseBooleanArray = lvAnswers.getCheckedItemPositions();
                    selected = new int[sparseBooleanArray.size()];

                    for (int i = 0; i < sparseBooleanArray.size(); i++) {

                        boolean value = sparseBooleanArray.valueAt(i);
                        int kkey = sparseBooleanArray.keyAt(i);
                        checkedAnswers[kkey] = value;
                    }
                }
            });

        }

    }

    public void BtnCheck(View view) {
        if(selected != null){
            boolean[] trueFactResultBool = new boolean[resArray.size()];

            for (int i4 = 0;i4<resArray.size();i4++){
                trueFactResultBool[i4] = resArray.get(i4);
            }
            if (Arrays.equals(trueFactResultBool, checkedAnswers)){

                tvResult.setText("Все правильно!");
                Animation animation = AnimationUtils.loadAnimation(QuizActivity.this, R.anim.size_text);
                tvResult.startAnimation(animation);
            }else {
                tvResult.setText("Неправильный ответ");
            }
        }
    }

    private QuizQuestion setQuestions(int key){
        dbHelper2 = new MainDBHelper2(this);

        try {
            dbHelper2.checkAndCopyDatabase();
            dbHelper2.openDatabase();
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        return dbHelper2.QOneQuestion(key);
    }

    private List<QuizAnswer> setQListAnswer(int key){
        List<QuizAnswer> answers = new ArrayList<>();
        dbHelper2 = new MainDBHelper2(this);

        try {
            dbHelper2.checkAndCopyDatabase();
            dbHelper2.openDatabase();
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        answers = dbHelper2.QAnswers(key);
        return answers;
    }
}
