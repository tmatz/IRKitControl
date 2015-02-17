package io.github.tmatz.irkitcontrol;
import android.os.AsyncTask;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import java.io.IOException;

public class ReadStreamTask extends AsyncTask<String, String, String>
{
	private InputStream mStream;
	
	public ReadStreamTask(InputStream stream)
	{
		mStream = stream;
	}
	
	@Override
	protected String doInBackground(String... params)
	{
		try
		{
			return IOUtils.toString(mStream, "UTF-8");
		}
		catch (IOException e)
		{
			return "";
		}
	}
}
