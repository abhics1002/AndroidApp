package com.abhi.gk;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Test_questions extends Activity {

	ListView listView;
	Cursor result;
	int number_of_question = 1;
	int max_numberOfQuestion = 20;
	int score = 0 ;
	List<String> values = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_questions);
    
        listView = (ListView) findViewById(R.id.list);
        
        // Defined Array values to show in ListView
        // need to pass values from database.
        
        
      /*  String[] values = new String[] { "Android List View", 
                                         "Adapter implementation",
                                         "Simple List View In Android",
                                         "Create List View Android", 
                                         "Android Example", 
                                         "List View Source Code", 
                                         "List View Array Adapter", 
                                         "Android Example List View" 
                                        };*/


 //       new GetContactTask().execute((Object[]) null);
        getquestionfromdb();
        
        
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

         final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
        // ListView Item Click Listener
         
        listView.setOnItemClickListener(new OnItemClickListener() {
        	 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
              
             // ListView Clicked item index
             int itemPosition     = position;
             
             // ListView Clicked item value
             String  itemValue    = (String) listView.getItemAtPosition(position);
                
              // Show Alert 
//              Toast.makeText(getApplicationContext(),
//                "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
//                .show();

             if(itemPosition > 0)
             {
            	 Toast.makeText(getApplicationContext(), "you answered : " +itemValue , Toast.LENGTH_LONG).show();
             }
              String uttar = result.getString( result.getColumnIndex("answer"));
              Log.v("uttar", uttar);
              int answer_no= 0;
              
              if (uttar.equals("a"))
            	  answer_no = 1;
              else if (uttar.equals("b"))
            	  answer_no = 2; 
              else if (uttar.equals("c"))
            	  answer_no = 3;
              else if (uttar.equals("d"))
            	  answer_no = 4;
              
              Log.v("itemPosition", Integer.toString(itemPosition));
      	  	Log.v("Answer_no", Integer.toString(answer_no));
              		// calculating score 
            	  if (itemPosition == answer_no){
            		   score ++; 
            	  	Log.v("SCORE", Integer.toString(itemPosition));
            	  	Log.v("SCORE", Integer.toString(answer_no));
            	 	Log.v("SCORE", Integer.toString(score));
            	  }
            	  else 
            	  {
            		  Log.v("SCORE", "your answer is wrong");
            	  }
              
              result.moveToNext();
//              adapter.clear();
//              showQuestion(result); 
              Log.v("number_of_question", Integer.toString(number_of_question));
              if( number_of_question <= 20 )
              {  
            	  adapter.clear();
            	  showQuestion(result);
            	  adapter.notifyDataSetChanged();
              }
              else if (number_of_question >= 20 + 1)
              {
            	  // start score Activity.
            	  start_score_page();
              }
            //adapter.notifyDataSetChanged(); 
            }
        });
              
    }
       
    public void start_score_page()
    {
    	Intent viewScore = new Intent(Test_questions.this, ViewScore.class);
    	viewScore.putExtra("score", score);
    	viewScore.putExtra("question_answers", getquestionAnswer(result));
    	viewScore.putExtra("no_of_question", number_of_question-1);
		startActivity(viewScore);
    }
    
    
	public String[] getquestionAnswer(Cursor ques_answer)
    {
    	String[] question_answer_pair = new String[max_numberOfQuestion];
    	ques_answer.moveToFirst();
    	int i = 0;
    	while (i < max_numberOfQuestion)
    	{
    		String temp, temp1;
    		temp1= ques_answer.getString( ques_answer.getColumnIndex("answer"));
    		temp = "Ques: "+ques_answer.getString( ques_answer.getColumnIndex("question") )+ "\n"+"Ans: "+ques_answer.getString( ques_answer.getColumnIndex(temp1)); 
    		question_answer_pair[i] = (temp);
    		ques_answer.moveToNext();
    		i++;
    	}
		return question_answer_pair;
    	
    }
    public void getquestionfromdb()
    {	
    	System.out.print("trying to print this message on console");
    	Log.v("blah", "ABHI");
//    	values.add("Abhishek");
//    	values.add("Bharadwaj");
    	
    	DatabaseConnector databaseConnector = new DatabaseConnector(this);
    	
    	databaseConnector.populateDatabase();
    	databaseConnector.open();
    	result = databaseConnector.getQuestions();
    	result.moveToFirst();
    	showQuestion(result);
//    	while (number_of_question < 10 )
//    	{
//    		showQuestion(result);
//    		number_of_question= number_of_question + 1;
//    		result.moveToNext();
//    	}
    	//String test = result.getString( result.getColumnIndex("question") );
		Log.v("abhi", "setting the values");
//		values.add(result.getString( result.getColumnIndex("question") ) );
//		values.add(result.getString( result.getColumnIndex("a")) );
//		values.add(result.getString( result.getColumnIndex("b")) );
//		values.add(result.getString( result.getColumnIndex("c")) );
//		values.add(result.getString( result.getColumnIndex("d")) );
	//	values.add(result.getString( result.getColumnIndex("answer")) );
        databaseConnector.close();
    }
    
    public void showQuestion(Cursor ques)
    {
    	
    	String question = "Ques "+ number_of_question +": "+ ques.getString( ques.getColumnIndex("question") );
    	String option_a = "(A) "+ques.getString( ques.getColumnIndex("a"));
    	String option_b = "(B) "+ques.getString( ques.getColumnIndex("b"));
    	String option_c = "(C) "+ques.getString( ques.getColumnIndex("c"));
    	String option_d = "(D) "+ques.getString( ques.getColumnIndex("d"));
    	
    	values.add(question);
    	values.add(option_a);
    	values.add(option_b);
    	values.add(option_c);
    	values.add(option_d);
    	
    	number_of_question++;
//		values.add(ques.getString( ques.getColumnIndex("question") ) );
//    	values.add(ques.getString( ques.getColumnIndex("a")) );
//		values.add(ques.getString( ques.getColumnIndex("b")) );
//		values.add(ques.getString( ques.getColumnIndex("c")) );
//		values.add(ques.getString( ques.getColumnIndex("d")) );
		
		
		
    }
    
    protected void onResume()
    {
    	super.onResume();
    	
    	 // create new GetContactsTask and execute it 
     //   new GetContactTask().execute((Object[]) null);
    }
    
    
    
    class GetContactTask extends AsyncTask<Object,Object,Cursor>
    {
    	DatabaseConnector databaseConnector = new DatabaseConnector(Test_questions.this);
    	
    	protected void onPostExecute(Cursor result) {
          //  contactAdapter.changeCursor(result);
    		result.moveToFirst();
    		values.add(result.getString( result.getColumnIndex("question") ));
    		values.add(result.getString( result.getColumnIndex("a")) );
    		values.add(result.getString( result.getColumnIndex("b")) );
    		values.add(result.getString( result.getColumnIndex("c")) );
    		values.add(result.getString( result.getColumnIndex("d")) );
    		values.add(result.getString( result.getColumnIndex("answer")) );
            databaseConnector.close();
        }

		@Override
		protected Cursor doInBackground(Object... params) {
			// TODO Auto-generated method stub
			databaseConnector.open();
			return databaseConnector.getQuestions();
			
		}

    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_test_questions, menu);
        return true;
    }
    
    
}
