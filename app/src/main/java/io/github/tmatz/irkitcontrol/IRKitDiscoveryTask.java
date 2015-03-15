package io.github.tmatz.irkitcontrol;
import android.net.nsd.NsdServiceInfo;
import android.os.AsyncTask;
import android.net.nsd.NsdManager;
import android.content.Context;
import android.app.Application;
import android.app.ApplicationErrorReport;
import javax.security.auth.PrivateCredentialPermission;

public class IRKitDiscoveryTask extends AsyncTask<String, String, NsdServiceInfo>
{
	private Object mWaitObject = new Object();
	private NsdManager mNsdManager;
	private NsdServiceInfo mNsdServiceInfo;
	
	private NsdManager.DiscoveryListener mDiscoveryListener = new NsdManager.DiscoveryListener()
	{
		private void stopServiceDiscovery()
		{
			IRKitDiscoveryTask.this.stopServiceDiscovery();
		}
		
		@Override
		public void onStartDiscoveryFailed(String p1, int p2)
		{
			stopServiceDiscovery();
		}

		@Override
		public void onStopDiscoveryFailed(String p1, int p2)
		{
			stopServiceDiscovery();
		}

		@Override
		public void onDiscoveryStarted(String p1)
		{
		}

		@Override
		public void onDiscoveryStopped(String p1)
		{
			stopServiceDiscovery();
		}

		@Override
		public void onServiceFound(NsdServiceInfo p1)
		{
			mNsdManager.resolveService(p1, mResolveListener);
		}

		@Override
		public void onServiceLost(NsdServiceInfo p1)
		{
		}
	};

	private NsdManager.ResolveListener mResolveListener = new NsdManager.ResolveListener()
	{
		private void stopServiceDiscovery()
		{
			IRKitDiscoveryTask.this.stopServiceDiscovery();
		}
		
		@Override
		public void onResolveFailed(NsdServiceInfo p1, int p2)
		{
		}

		@Override
		public void onServiceResolved(NsdServiceInfo p1)
		{
			IRKitDiscoveryTask.this.mNsdServiceInfo = p1;
			stopServiceDiscovery();
		}
	};
	
	public IRKitDiscoveryTask(Context context)
	{
		mNsdManager = (NsdManager)context.getSystemService(context.NSD_SERVICE);
	}

	NsdServiceInfo nsdServiceInfo = null;.
	try
	{
		nsdServiceInfo = new AsyncTask<String, String, NsdServiceInfo>()
		{
			private Object waitObject = new Object();
			private NsdServiceInfo nsdServiceInfo = null;

			@Override
			protected NsdServiceInfo doInBackground(String[] p1)
			{
				final NsdManager nsdManager = (NsdManager)SendMessageActivity.this.getSystemService(NSD_SERVICE);

				if (nsdManager != null)
				{
					NsdManager.DiscoveryListener listener = new NsdManager.DiscoveryListener()
					nsdManager.stopServiceDiscovery(listener);
				}

				return nsdServiceInfo;
			}

			protected void onCanc()
			{}
		}.execute().get();
	}
	catch (ExecutionException e)
	{}
	catch (InterruptedException e)
	{}

	if (nsdServiceInfo == null)
	{
		Toast.makeText(this, "IRKit not found", Toast.LENGTH_SHORT).show();
		finish();
		return;
	}
	else
	{
		Toast.makeText(this, "IRKit" + nsdServiceInfo.getHost() + ":" + nsdServiceInfo.getPort(), Toast.LENGTH_SHORT).show();
	}
	
	private void stopServiceDiscovery()
	{
		synchronized (mWaitObject)
		{
			mNsdManager.stopServiceDiscovery(
				IRKitDiscoveryTask.this.mDiscoveryListener);
			mWaitObject.notify();
		}
	}
	
	
}
