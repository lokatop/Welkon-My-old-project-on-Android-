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

import com.example.welkon.Utils.MainDBHelper2;
import com.example.welkon.quiz.QuizAnswer;
import com.example.welkon.quiz.QuizQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.welkon.BasicScan.KEY_FOR_NUMBER_OF_QUIZ;

public class QuizActivity extends AppCompatActivity {

    //private MyArrayAdapter mArrayAdapter;
    private ArrayList<String> questionArray = new ArrayList<>();
    String[] question;
    TextView tvQuestion;
    TextView tvResult;
    int[] trueResult;

    private MainDBHelper2 dbHelper2;
    QuizAnswer qAnswer;
    QuizQuestion qQuestion;
    public static List<QuizQuestion> quizQuestionList;

    ArrayList<Integer> SelectedResult = new ArrayList<>();
    int selected[];

    int selectedTrue[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide();
        // получаем элемент ListView
        final ListView lvAnswers = (ListView) findViewById(R.id.lvAnswers);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvResult   = (TextView) findViewById(R.id.tvResult);


        int[] numberOfAnswersId =
                {R.array.answers1,R.array.answers2,
                        R.array.answers3,R.array.answers4,
                        R.array.answers5,R.array.answers6,
                        R.array.answers7,R.array.answers8,
                        R.array.answers9,R.array.answers10};
        int[] numberOfQuestionId =
                {R.string.question_1,R.string.question_2,
                        R.string.question_3,R.string.question_4,
                        R.string.question_5,R.string.question_6,
                        R.string.question_7,R.string.question_8,
                        R.string.question_9,R.string.question_10};
        int[] numberOfTrueAnswersId =
                {
                        R.array.trueAnswers1,R.array.trueAnswers2,
                        R.array.trueAnswers3,R.array.trueAnswers4,
                        R.array.trueAnswers5,R.array.trueAnswers6,
                        R.array.trueAnswers7,R.array.trueAnswers8,
                        R.array.trueAnswers9,R.array.trueAnswers10
                };
        //---------------------------------------------------------------

        //---------------------------------------------------------------

        Intent intent = getIntent();
        String keyQuestion = intent.getStringExtra(KEY_FOR_NUMBER_OF_QUIZ);
        if (keyQuestion != null) {
            // получаем ресурс
            int key = Integer.parseInt(keyQuestion) - 1;

            question = getResources().getStringArray(numberOfAnswersId[key]);
            //tvQuestion.setText(numberOfQuestionId[key]);

            QuizQuestion quizQuestion = setQuestions(key);
            String text = quizQuestion.getQuestion();
            int int2 = quizQuestion.getNumberOfQR();
            tvQuestion.setText(text);

            for (int i = 0; i < question.length; i++) {
                questionArray.add(question[i]);
            }
            trueResult = getResources().getIntArray(numberOfTrueAnswersId[key]);

            lvAnswers.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            // создаем адаптер
            ArrayAdapter<String> adapter = new ArrayAdapter(this,
                    R.layout.simple_list_item_multiple_choice, questionArray);

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
                        if (sparseBooleanArray.valueAt(i)){
                            selected[i] = sparseBooleanArray.keyAt(i)+1;
                        }
                    }
                }
            });

        }

    }

    public void BtnCheck(View view) {
        if(selected != null){
            //очищаем перед повторным использованием
            SelectedResult.clear();

            //смотрим количество выбранных элементов(при 0 - false) и выбранные ответы записываем
            //в другой ArrayList
            for (int i = 0; i < selected.length;i++){
                if(selected[i] != 0){SelectedResult.add(selected[i]);}
            }

            //создаем массив чтобы не писать лишнего и просто сравнить два массива int
            int[] trueResFinal = new int[SelectedResult.size()];
            int i = 0;
            for (int ar: SelectedResult){
                trueResFinal[i] = ar;
                i++;
            }
            if (Arrays.equals(trueResult, trueResFinal)){

                tvResult.setText("Все правильно!");
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.size_text);
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
}
