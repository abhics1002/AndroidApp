package com.abhi.gk;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewScore extends Activity {

	public TextView score_display;
	int no_of_question = 0;
	int max_numberOfQuestion = 20;
	int score = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);
        
        score_display = (TextView) findViewById(R.id.scoretextView);
//        share_FB = (TextView) findViewById(R.id.fbsharetextView);
//        see_answer = (TextView) findViewById(R.id.seeanswertextView);
//        replay = (TextView) findViewById(R.id.replaytextView);
        Bundle bundle_obj = getIntent().getExtras();
        score = bundle_obj.getInt("score");
        
        no_of_question = bundle_obj.getInt("no_of_question");
        String test = "You have answered "+score+ " questions out of "+no_of_question+" correctly";
        score_display.setText(test);
        
        Button replayButton = (Button) findViewById(R.id.replayButton);
        Button shareFBButton = (Button) findViewById(R.id.shareFBButton);
        Button seeAnswerButton = (Button) findViewById(R.id.seeAnswerButton);
        replayButton.setOnClickListener(replayButtonClicked);
        shareFBButton.setOnClickListener(shareFBButtonClicked);
        seeAnswerButton.setOnClickListener(seeAnswerButtonClicked);
    }

    OnClickListener replayButtonClicked = new OnClickListener()
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(ViewScore.this, Test_questions.class);
			startActivity(i);
		}
    	
	};
	
	OnClickListener shareFBButtonClicked = new OnClickListener()
	{
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("text/html");
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>hey this is awesome.</p>"));
			startActivity(Intent.createChooser(sharingIntent,"Share using"));
			
		}
		
	};
	
	OnClickListener seeAnswerButtonClicked = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String[] question_answers = new String[max_numberOfQuestion];
			Bundle bundle_obj = getIntent().getExtras();
			question_answers = bundle_obj.getStringArray("question_answers");
			Log.v("question_answer", question_answers[0]);
			Log.v("question_answer", question_answers[1]);
			Log.v("question_answer", question_answers[2]);
			Log.v("question_answer", question_answers[3]);
			Log.v("question_answer", question_answers[4]);
			Log.v("question_answer", question_answers[5]);
			Log.v("question_answer", question_answers[6]);
			Log.v("question_answer", question_answers[7]);
			Log.v("question_answer", question_answers[8]);
			Log.v("question_answer", question_answers[9]);
			Intent seeAnswer = new Intent(ViewScore.this, ViewAnswers.class);
			seeAnswer.putExtra("question_answers", question_answers);
			startActivity(seeAnswer);
		}
	};
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_score, menu);
        return true;
    }
}
