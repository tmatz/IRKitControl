package io.github.tmatz.irkitcontrol;
import android.app.*;
import android.os.*;
import android.content.*;
import java.io.*;
import android.net.Uri;
import android.widget.Toast;

public class SendMessageActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//Toast.makeText(this, "SendMessageActivity", Toast.LENGTH_SHORT).show();
		Intent intent = getIntent();
		if (intent != null && intent.getData() != null)
		{
			//Toast.makeText(this, intent.getDataString(), Toast.LENGTH_SHORT).show();
			try
			{
				final InputStream stream = getContentResolver().openInputStream(intent.getData());
				new ReadStreamTask(stream) {
					@Override
					protected void onPostExecute(String result)
					{
						//Toast.makeText(SendMessageActivity.this, "Content: " + result, Toast.LENGTH_SHORT).show();
						new SendMessageTask()
						{
							@Override
							protected void onPostExecute(String result)
							{
								super.onPostExecute(result);
								SendMessageActivity.this.finish();
							}
							
							@Override
							protected void onCancelled()
							{
								super.onCancelled();
								SendMessageActivity.this.finish();
							}
						}.execute("http://192.168.0.3/messages", result);
					}
					
					@Override
					protected void onCancelled()
					{
						super.onCancelled();
						SendMessageActivity.this.finish();
					}
				}.execute();
			}
			catch (FileNotFoundException e)
			{
				Toast.makeText(SendMessageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
		finish();
	}
}
