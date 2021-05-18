package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class question extends AppCompatActivity implements View.OnClickListener{

    private TextView question, qcount;
    private Button option1, option2, option3, option4, next;
    private List<ques> questionList;
    int quesNum;
    private  int score=0;
    private FirebaseFirestore firestore;
    public  int category_id;
    int backQuesNum;
    ArrayList<Integer> evalute = new ArrayList<>();

    private static final long START_TIME_IN_MILLIS = 600000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question = findViewById(R.id.question);
        qcount = findViewById(R.id.quest_num);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        next = findViewById(R.id.next);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

       // setNo = getIntent().getIntExtra("SETNO", 1);
        category_id = getIntent().getIntExtra("Catogries", 1);
        firestore = FirebaseFirestore.getInstance();

        getQuestionList();
        startTimer();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                Intent intent = new Intent(question.this, ScoreActivity.class);
                intent.putExtra("SCORE", String.valueOf(score) + "/" + String.valueOf(questionList.size()));
                startActivity(intent);
                question.this.finish();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private  void getQuestionList() {
        questionList = new ArrayList<>();

      firestore.collection("QUIZ").document("CAT" +  String.valueOf(category_id))
              .collection("SET1" )
              .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @RequiresApi(api = Build.VERSION_CODES.KITKAT)
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
              if (task.isSuccessful()) {
                  QuerySnapshot questions = task.getResult();

                  for(QueryDocumentSnapshot doc : questions) {
                      questionList.add(new ques(doc.getString("QUESTION"),
                              doc.getString("A"),
                              doc.getString("B"),
                              doc.getString("C"),
                              doc.getString("D"),
                              Integer.valueOf(doc.getString("ANSWER"))
                              ));
                  }
                  setQuestion();
              }
              else {
                  Toast.makeText(question.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
              }
          }
      });



    }

    private  void setQuestion(){

        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());

        qcount.setText(1 + "/" + questionList.size());
        quesNum = 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        int selectedOption = 0;

        switch(v.getId())
        {
            case R.id.option1 :
               selectedOption = 1;
                 break;
            case R.id.option2 :
               selectedOption = 2;
                break;
            case R.id.option3 :
                selectedOption = 3;
                break;
            case R.id.option4 :
                selectedOption = 4;
                break;
            default:
        }
        checkAnswer(selectedOption, v);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private  void checkAnswer(int selectedOption, View view) {
        if(selectedOption == questionList.get(quesNum).getCorrectAns()) {
            //Right answer
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;
        }
        else {
            // wrong Answer
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            switch (questionList.get(quesNum).getCorrectAns()) {
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
            }
        }

        evalute.add(quesNum);
       Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        }, 2000);

    }
    private void changeQuestion() {
         quesNum++;
        backQuesNum = quesNum;
        if(quesNum == questionList.size() - 1)
            next.setText("SUBMIT");
        if(quesNum <= questionList.size() - 1) {
            playAnim(question, 0, 0);
            playAnim(option1, 0, 1);
            playAnim(option2, 0, 2);
            playAnim(option3, 0, 3);
            playAnim(option4, 0, 4);

            qcount.setText((quesNum + 1) + "/" + questionList.size());
        }
        else {
            //score activity
            Intent intent = new Intent(question.this, ScoreActivity.class);
            intent.putExtra("SCORE", String.valueOf(score) + "/" + String.valueOf(questionList.size()));
            startActivity(intent);
            question.this.finish();
        }
    }

    private  void playAnim(final View view, final int value, final int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(value == 0) {
                            switch (viewNum) {
                                case 0:
                                    ((TextView)view).setText(questionList.get(quesNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionA());
                                    break;
                                case 2:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionB());
                                    break;
                                case 3:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionC());
                                    break;
                                case 4:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionD());
                                    break;
                                default:
                            }
                            if(viewNum != 0) {

                                    ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));

                            }
                            playAnim(view, 1, viewNum);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    public void nextQuestion(View view) {
        if (quesNum < questionList.size() - 2) {
            next.setText("NEXT");
            changeQuestion();
        }
        else {
            next.setText("SUBMIT");
            changeQuestion();
        }
    }

    public void backQuestion(View view) {
        next.setText("NEXT");
        backChangeQuestion();
    }

    private void backChangeQuestion() {
        boolean check = true;
        for (int element : evalute) {
            if (element == backQuesNum - 1)
               check = false;
        }
        if (check) {
            if (backQuesNum > 0) {
                backPlayAnim(question, 0, 0);
                backPlayAnim(option1, 0, 1);
                backPlayAnim(option2, 0, 2);
                backPlayAnim(option3, 0, 3);
                backPlayAnim(option4, 0, 4);

                qcount.setText((backQuesNum) + "/" + questionList.size());
                backQuesNum--;
                quesNum = backQuesNum;
            } else {
                //score activity
                Toast.makeText(question.this, "This is first question", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private  void backPlayAnim(final View view, final int value, final int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(value == 0) {
                            switch (viewNum) {
                                case 0:
                                    ((TextView)view).setText(questionList.get(backQuesNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button)view).setText(questionList.get(backQuesNum).getOptionA());
                                    break;
                                case 2:
                                    ((Button)view).setText(questionList.get(backQuesNum).getOptionB());
                                    break;
                                case 3:
                                    ((Button)view).setText(questionList.get(backQuesNum).getOptionC());
                                    break;
                                case 4:
                                    ((Button)view).setText(questionList.get(backQuesNum).getOptionD());
                                    break;
                                default:
                            }
                            if(viewNum != 0) {

                                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));

                            }
                            playAnim(view, 1, viewNum);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

}