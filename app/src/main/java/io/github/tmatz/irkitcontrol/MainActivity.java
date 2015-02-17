package io.github.tmatz.irkitcontrol;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.content.Intent;
import org.apache.commons.io.IOUtils;
import java.io.IOException;

public class MainActivity extends Activity 
{
	private Button mButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		Toast.makeText(this, "Main Activity", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.main);
		
		mButton = (Button)findViewById(R.id.mainButton1);
		mButton.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View p1)
				{
					//startActivity(new Intent(MainActivity.this, SendMessageActivity.class));
					try
					{
						new SendMessageTask().execute("http://192.168.0.3/messages", IOUtils.toString(getAssets().open("data/tv-sony-rm-j244/timer.json"), "UTF-8"));
					}
					catch (IOException e)
					{}
				}
			});
    }
	
	
}
