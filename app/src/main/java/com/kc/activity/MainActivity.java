//package com.bcinfo.TripAway;
//
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
//import android.content.Intent;
//import android.content.res.AssetManager;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.View;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.bcinfo.TripAway.helper.DBHelper;
//import com.bcinfo.TripAway.helper.WebServiceHelper;
//import com.bcinfo.TripAway.helper.pathHelper;
//import com.bcinfo.TripAway.helper.portHelper;
//import com.bcinfo.TripAway.helper.scanHelper;
//import com.bcinfo.TripAway.helper.waitHelper;
//import com.bcinfo.TripAway.models.db;
//import com.bcinfo.TripAway.models.dbRooms;
//import com.bcinfo.TripAway.models.dbRooms.SGCubicle;
//import com.bcinfo.TripAway.models.dbRooms.SGDevice;
//import com.bcinfo.TripAway.models.dbRooms.SGRoom;
//import com.bcinfo.TripAway.service.ResourceService;
//import com.bcinfo.TripAway.tab.TabSettings;
//import com.bcinfo.TripAway.utils.HttpDownloader;
//import com.bcinfo.TripAway.utils.Log;
//import com.bcinfo.TripAway.views.slidemenu.SlidingMenu;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Iterator;
//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//import retrofit.mime.TypedByteArray;
//
//public class MainActivity extends FragmentActivity
//        implements AdapterView.OnItemClickListener
//{
//    public static String RemotePath = "http://7xrtuk.com1.z0.glb.clouddn.com/";
//    private static final int SCANNIN_GREQUEST_CODE = 1;
//    private static MainActivity activeMain = null;
//    public static int curDbLocation = 0;
//    private static TextView tabTitle;
//    private boolean bSearching = false;
//    private RadioButton docButton;
//    private FragmentManager fragmentManager;
//    Handler handler = new Handler()
//    {
//        public void handleMessage(Message paramAnonymousMessage)
//        {
//            if (paramAnonymousMessage.arg1 == 1)
//                MainActivity.activeMain.initdata();
//            while (paramAnonymousMessage.arg1 != 2)
//                return;
//            MainActivity.activeMain.loadlocalurl(MainActivity.this.urlpath);
//        }
//    };
//    private RadioButton homeButton;
//    private ImageView imageView;
//    private RadioButton kakuButton;
//    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
//    {
//        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
//        {
//            switch (paramAnonymousInt)
//            {
//                case -2:
//                default:
//                    return;
//                case -1:
//            }
//            MainActivity.this.finish();
//        }
//    };
//    private SlidingMenu menu;
//    private ListView menuListView;
//    private RadioButton mycardButton;
//    private RadioGroup radioGroup;
//    public String urlpath = "";
//    private WebView webView;
//
//    private Boolean CopyAssetsDir(String paramString1, String paramString2)
//    {
//        Boolean localBoolean = Boolean.valueOf(true);
//        String[] arrayOfString;
//        try
//        {
//            arrayOfString = getResources().getAssets().list(paramString1);
//            if (arrayOfString.length == 0)
//            {
//                paramString1 = CopyAssetsFile(paramString1, paramString2);
//                localObject = paramString1;
//                if (paramString1.booleanValue())
//                    break label171;
//                return paramString1;
//            }
//        }
//        catch (IOException paramString1)
//        {
//            return Boolean.valueOf(false);
//        }
//        File localFile = new File(paramString2 + "/" + paramString1);
//        Object localObject = localBoolean;
//        if (!localFile.exists())
//        {
//            if (localFile.mkdir())
//            {
//                int i = 0;
//                localObject = localBoolean;
//                while (i < arrayOfString.length)
//                {
//                    localObject = CopyAssetsDir(paramString1 + "/" + arrayOfString[i], paramString2);
//                    if (!((Boolean)localObject).booleanValue())
//                        return localObject;
//                    i += 1;
//                }
//            }
//            return Boolean.valueOf(false);
//        }
//        label171: return localObject;
//    }
//
//    private Boolean CopyAssetsFile(String paramString1, String paramString2)
//    {
//        Object localObject = getAssets();
//        try
//        {
//            localObject = ((AssetManager)localObject).open(paramString1);
//            paramString1 = new FileOutputStream(paramString2 + "/" + paramString1);
//            try
//            {
//                paramString2 = new byte[1024];
//                while (true)
//                {
//                    int i = ((InputStream)localObject).read(paramString2);
//                    if (i == -1)
//                        break;
//                    paramString1.write(paramString2, 0, i);
//                }
//            }
//            catch (Exception paramString1)
//            {
//            }
//            label74: paramString1.printStackTrace();
//            return Boolean.valueOf(false);
//            ((InputStream)localObject).close();
//            paramString1.flush();
//            paramString1.close();
//            return Boolean.valueOf(true);
//        }
//        catch (Exception paramString1)
//        {
//            break label74;
//        }
//    }
//
//    public static void SetDB(String paramString)
//    {
//        if (activeMain != null);
//        try
//        {
//            paramString = activeMain;
//            tabTitle.setText(DBHelper.ActiveDB.name);
//            label22: activeMain.loadlocalurl(pathHelper.getindexurl());
//            return;
//        }
//        catch (Exception paramString)
//        {
//            break label22;
//        }
//    }
//
//    private void doScan(final String paramString)
//    {
//        try
//        {
//            if (paramString.startsWith("C:"))
//            {
//                String str = pathHelper.geturl(paramString.substring(2));
//                localObject = str;
//                if (str.contains("#"))
//                    localObject = str.replaceAll("#", "%23");
//                if (((String)localObject).length() <= 0)
//                    break label359;
//                this.webView.loadUrl((String)localObject);
//                return;
//            }
//            if (paramString.startsWith("F:"))
//            {
//                new Thread(new Runnable()
//                {
//                    public void run()
//                    {
//                        MainActivity.activeMain.urlpath = this.val$scan.doScan(paramString);
//                        Message localMessage = new Message();
//                        localMessage.arg1 = 2;
//                        MainActivity.this.handler.sendMessage(localMessage);
//                    }
//                }).start();
//                this.webView.loadUrl(pathHelper.getwaiturl());
//                return;
//            }
//        }
//        catch (Exception paramString)
//        {
//            paramString.printStackTrace();
//            return;
//        }
//        if (paramString.startsWith("Q:"))
//        {
//            new Thread(new Runnable()
//            {
//                public void run()
//                {
//                    HttpDownloader localHttpDownloader = new HttpDownloader();
//                    String[] arrayOfString = localHttpDownloader.download(this.val$urlstring).split(",");
//                    int i = 0;
//                    if (i < arrayOfString.length)
//                    {
//                        String str2 = arrayOfString[i];
//                        String str1;
//                        if (str2.startsWith("C:"))
//                        {
//                            str1 = MainActivity.RemotePath + DBHelper.ActiveDB.file + "/c_" + str2.substring(2) + ".html";
//                            str2 = "/c_" + str2.substring(2) + ".html";
//                            localHttpDownloader.downFile(str1, DBHelper.StoragePath + DBHelper.ActiveDB.file, str2);
//                        }
//                        while (true)
//                        {
//                            i += 1;
//                            break;
//                            if (str2.startsWith("F:"))
//                            {
//                                str1 = "/vport_" + str2.substring(2) + ".html";
//                                localHttpDownloader.downFile(MainActivity.RemotePath + DBHelper.ActiveDB.file + str1, DBHelper.StoragePath + DBHelper.ActiveDB.file, str1);
//                            }
//                        }
//                    }
//                }
//            }).start();
//            return;
//        }
//        if (paramString.startsWith("D:"))
//        {
//            paramString = paramString.substring(2);
//            WebServiceHelper.createResourceService().getRoom(paramString, new Callback()
//            {
//                public void failure(RetrofitError paramAnonymousRetrofitError)
//                {
//                    new String(((TypedByteArray)paramAnonymousRetrofitError.getResponse().getBody()).getBytes());
//                    Log.d("xxx", "fail");
//                }
//
//                public void success(final dbRooms paramAnonymousdbRooms, Response paramAnonymousResponse)
//                {
//                    new Thread(new Runnable()
//                    {
//                        public void run()
//                        {
//                            HttpDownloader localHttpDownloader = new HttpDownloader();
//                            Iterator localIterator1 = paramAnonymousdbRooms.Data.iterator();
//                            if (localIterator1.hasNext())
//                            {
//                                Iterator localIterator2 = ((dbRooms.SGRoom)localIterator1.next()).cubicles.iterator();
//                                while (localIterator2.hasNext())
//                                {
//                                    Object localObject1 = (dbRooms.SGCubicle)localIterator2.next();
//                                    Object localObject2 = "/cable" + ((dbRooms.SGCubicle)localObject1).cubicleid + ".html";
//                                    try
//                                    {
//                                        localHttpDownloader.downFile(MainActivity.RemotePath + URLEncoder.encode(new StringBuilder().append(MainActivity.7.this.val$dbname).append("/").append((String)localObject2).toString(), "utf-8"), DBHelper.StoragePath + MainActivity.7.this.val$dbname, (String)localObject2);
//                                        localObject1 = ((dbRooms.SGCubicle)localObject1).devices.iterator();
//                                        if (((Iterator)localObject1).hasNext())
//                                        {
//                                            localObject2 = (dbRooms.SGDevice)((Iterator)localObject1).next();
//                                            str = "/device" + ((dbRooms.SGDevice)localObject2).deviceid + ".html";
//                                        }
//                                    }
//                                    catch (UnsupportedEncodingException localUnsupportedEncodingException2)
//                                    {
//                                        try
//                                        {
//                                            while (true)
//                                            {
//                                                String str;
//                                                localHttpDownloader.downFile(MainActivity.RemotePath + URLEncoder.encode(new StringBuilder().append(MainActivity.7.this.val$dbname).append("/").append(str).toString(), "utf-8"), DBHelper.StoragePath + MainActivity.7.this.val$dbname, str);
//                                                localObject2 = "/vport_d_" + ((dbRooms.SGDevice)localObject2).deviceid + ".html";
//                                                try
//                                                {
//                                                    localHttpDownloader.downFile(MainActivity.RemotePath + URLEncoder.encode(new StringBuilder().append(MainActivity.7.this.val$dbname).append("/").append((String)localObject2).toString(), "utf-8"), DBHelper.StoragePath + MainActivity.7.this.val$dbname, (String)localObject2);
//                                                }
//                                                catch (UnsupportedEncodingException localUnsupportedEncodingException1)
//                                                {
//                                                    localUnsupportedEncodingException1.printStackTrace();
//                                                }
//                                            }
//                                            localUnsupportedEncodingException2 = localUnsupportedEncodingException2;
//                                            localUnsupportedEncodingException2.printStackTrace();
//                                        }
//                                        catch (UnsupportedEncodingException localUnsupportedEncodingException3)
//                                        {
//                                            while (true)
//                                                localUnsupportedEncodingException3.printStackTrace();
//                                        }
//                                    }
//                                }
//                            }
//                            Toast.makeText(MainActivity.this, "下载文件完成!", 0).show();
//                        }
//                    }).start();
//                }
//            });
//            return;
//        }
//        if (paramString.startsWith("FILES:"))
//        {
//            new Thread(new Runnable()
//            {
//                // ERROR //
//                public void run()
//                {
//                    // Byte code:
//                    //   0: new 30	com/bcinfo/TripAway/utils/HttpDownloader
//                    //   3: dup
//                    //   4: invokespecial 31	com/bcinfo/TripAway/utils/HttpDownloader:<init>	()V
//                    //   7: aload_0
//                    //   8: getfield 21	com/bcinfo/TripAway/MainActivity$8:val$urlstring	Ljava/lang/String;
//                    //   11: invokevirtual 35	com/bcinfo/TripAway/utils/HttpDownloader:download	(Ljava/lang/String;)Ljava/lang/String;
//                    //   14: ldc 37
//                    //   16: invokevirtual 43	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
//                    //   19: astore_3
//                    //   20: iconst_0
//                    //   21: istore_1
//                    //   22: iload_1
//                    //   23: aload_3
//                    //   24: arraylength
//                    //   25: if_icmpge +119 -> 144
//                    //   28: aload_3
//                    //   29: iload_1
//                    //   30: aaload
//                    //   31: astore_2
//                    //   32: new 45	java/lang/StringBuilder
//                    //   35: dup
//                    //   36: invokespecial 46	java/lang/StringBuilder:<init>	()V
//                    //   39: getstatic 49	com/bcinfo/TripAway/MainActivity:RemotePath	Ljava/lang/String;
//                    //   42: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
//                    //   45: new 45	java/lang/StringBuilder
//                    //   48: dup
//                    //   49: invokespecial 46	java/lang/StringBuilder:<init>	()V
//                    //   52: getstatic 59	com/bcinfo/TripAway/helper/DBHelper:ActiveDB	Lcom/bcinfo/TripAway/models/db;
//                    //   55: getfield 64	com/bcinfo/TripAway/models/db:file	Ljava/lang/String;
//                    //   58: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
//                    //   61: ldc 66
//                    //   63: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
//                    //   66: aload_2
//                    //   67: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
//                    //   70: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
//                    //   73: ldc 72
//                    //   75: invokestatic 78	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
//                    //   78: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
//                    //   81: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
//                    //   84: astore 4
//                    //   86: new 30	com/bcinfo/TripAway/utils/HttpDownloader
//                    //   89: dup
//                    //   90: invokespecial 31	com/bcinfo/TripAway/utils/HttpDownloader:<init>	()V
//                    //   93: astore 5
//                    //   95: aload 5
//                    //   97: aload 4
//                    //   99: new 45	java/lang/StringBuilder
//                    //   102: dup
//                    //   103: invokespecial 46	java/lang/StringBuilder:<init>	()V
//                    //   106: getstatic 81	com/bcinfo/TripAway/helper/DBHelper:StoragePath	Ljava/lang/String;
//                    //   109: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
//                    //   112: getstatic 59	com/bcinfo/TripAway/helper/DBHelper:ActiveDB	Lcom/bcinfo/TripAway/models/db;
//                    //   115: getfield 64	com/bcinfo/TripAway/models/db:file	Ljava/lang/String;
//                    //   118: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
//                    //   121: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
//                    //   124: aload_2
//                    //   125: invokevirtual 85	com/bcinfo/TripAway/utils/HttpDownloader:downFile	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
//                    //   128: pop
//                    //   129: iload_1
//                    //   130: iconst_1
//                    //   131: iadd
//                    //   132: istore_1
//                    //   133: goto -111 -> 22
//                    //   136: astore_2
//                    //   137: aload_2
//                    //   138: invokevirtual 88	java/io/UnsupportedEncodingException:printStackTrace	()V
//                    //   141: goto -12 -> 129
//                    //   144: return
//                    //   145: astore_2
//                    //   146: goto -9 -> 137
//                    //
//                    // Exception table:
//                    //   from	to	target	type
//                    //   32	95	136	java/io/UnsupportedEncodingException
//                    //   95	129	145	java/io/UnsupportedEncodingException
//                }
//            }).start();
//            return;
//        }
//        Object localObject = new ArrayList();
//        FileListActivity.doSearch(paramString, FileListActivity.RootFilePath, (ArrayList)localObject);
//        if (((ArrayList)localObject).size() == 0)
//        {
//            localObject = new AlertDialog.Builder(this);
//            ((AlertDialog.Builder)localObject).setTitle("提示");
//            ((AlertDialog.Builder)localObject).setMessage("未找到标签或文档：" + paramString);
//            ((AlertDialog.Builder)localObject).setPositiveButton("确认", new DialogInterface.OnClickListener()
//            {
//                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
//                {
//                    paramAnonymousDialogInterface.dismiss();
//                }
//            });
//            ((AlertDialog.Builder)localObject).create().show();
//            return;
//        }
//        if (((ArrayList)localObject).size() == 1)
//        {
//            startActivity(FileListActivity.openFile(((File)((ArrayList)localObject).get(0)).getPath()));
//            return;
//        }
//        localObject = new Intent();
//        ((Intent)localObject).setClass(this, FileListActivity.class);
//        ((Intent)localObject).putExtra("filename", paramString);
//        startActivity((Intent)localObject);
//        return;
//        label359: localObject = new AlertDialog.Builder(this);
//        ((AlertDialog.Builder)localObject).setTitle("提示");
//        ((AlertDialog.Builder)localObject).setMessage("扫描到的二维码不可用:" + paramString);
//        ((AlertDialog.Builder)localObject).setPositiveButton("确认", new DialogInterface.OnClickListener()
//        {
//            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
//            {
//                paramAnonymousDialogInterface.dismiss();
//            }
//        });
//        ((AlertDialog.Builder)localObject).create().show();
//    }
//
//    private Fragment getInstanceByIndex(int paramInt)
//    {
//        switch (paramInt)
//        {
//            case 2131296313:
//            case 2131296314:
//            case 2131296315:
//            case 2131296316:
//            default:
//                return null;
//            case 2131296317:
//        }
//        return new TabSettings();
//    }
//
//    private void loadlocalurl(String paramString)
//    {
//        try
//        {
//            if (!new File(paramString.substring("file://".length())).exists())
//                return;
//            String str = paramString;
//            if (paramString.contains("#"))
//                str = paramString.replaceAll("#", "%23");
//            this.webView.loadUrl(str);
//            return;
//        }
//        catch (Exception paramString)
//        {
//            paramString.printStackTrace();
//        }
//    }
//
//    private void readHtmlFormAssets()
//    {
//        String str = "file:///storage/sdcard0/sgclient/datas/fzc/index.html";
//        try
//        {
//            if ("file:///storage/sdcard0/sgclient/datas/fzc/index.html".contains("#"))
//                str = "file:///storage/sdcard0/sgclient/datas/fzc/index.html".replaceAll("#", "%23");
//            this.webView.loadUrl(str);
//            return;
//        }
//        catch (Exception localException)
//        {
//            localException.printStackTrace();
//        }
//    }
//
//    public RadioButton getHomeButton()
//    {
//        return this.homeButton;
//    }
//
//    public RadioButton getHuodongButton()
//    {
//        return this.docButton;
//    }
//
//    public RadioButton getKakuButton()
//    {
//        return this.kakuButton;
//    }
//
//    public RadioButton getMycardButton()
//    {
//        return this.mycardButton;
//    }
//
//    public void initdata()
//    {
//        DBHelper.loadDBS();
//        this.webView.loadUrl(pathHelper.getindexurl());
//    }
//
//    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
//    {
//        super.onActivityResult(paramInt1, paramInt2, paramIntent);
//        switch (paramInt1)
//        {
//            default:
//            case 1:
//        }
//        do
//            return;
//        while (paramInt2 != -1);
//        paramIntent = paramIntent.getExtras().getString("result");
//        waitHelper localwaitHelper = new waitHelper(this);
//        doScan(paramIntent);
//        localwaitHelper.finish();
//    }
//
//    public void onClick(View paramView)
//    {
//        switch (paramView.getId())
//        {
//            default:
//                return;
//            case 2131296320:
//        }
//        this.webView.goBack();
//    }
//
//    protected void onCreate(Bundle paramBundle)
//    {
//        super.onCreate(paramBundle);
//        setContentView(2130903044);
//        new Thread(new Runnable()
//        {
//            public void run()
//            {
//                Object localObject = Environment.getExternalStorageDirectory().getPath();
//                if (!new File((String)localObject + "/sgclient").exists())
//                    MainActivity.this.CopyAssetsDir("sgclient", Environment.getExternalStorageDirectory().getPath());
//                DBHelper.StoragePath = (String)localObject + "/sgclient/datas/";
//                localObject = new Message();
//                ((Message)localObject).arg1 = 1;
//                MainActivity.this.handler.sendMessage((Message)localObject);
//            }
//        }).start();
//        DBHelper.loadDBS();
//        this.fragmentManager = getSupportFragmentManager();
//        this.radioGroup = ((RadioGroup)findViewById(2131296312));
//        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//            public void onCheckedChanged(RadioGroup paramAnonymousRadioGroup, int paramAnonymousInt)
//            {
//                boolean bool = true;
//                MainActivity.this.radioGroup.check(2131296318);
//                if (paramAnonymousInt == 2131296313)
//                    MainActivity.this.loadlocalurl(pathHelper.getindexurl());
//                if (paramAnonymousInt == 2131296314)
//                {
//                    paramAnonymousRadioGroup = new Intent();
//                    paramAnonymousRadioGroup.setClass(MainActivity.this, FileListActivity.class);
//                    paramAnonymousRadioGroup.putExtra("title", "文档查询");
//                    MainActivity.this.startActivity(paramAnonymousRadioGroup);
//                }
//                do
//                {
//                    return;
//                    if (paramAnonymousInt == 2131296315)
//                    {
//                        paramAnonymousRadioGroup = new Intent();
//                        paramAnonymousRadioGroup.setClass(MainActivity.this, QRScanActivity.class);
//                        paramAnonymousRadioGroup.setFlags(67108864);
//                        MainActivity.this.startActivityForResult(paramAnonymousRadioGroup, 1);
//                        return;
//                    }
//                    if (paramAnonymousInt == 2131296316)
//                    {
//                        paramAnonymousRadioGroup = MainActivity.this;
//                        if (!MainActivity.this.bSearching);
//                        while (true)
//                        {
//                            MainActivity.access$402(paramAnonymousRadioGroup, bool);
//                            if (MainActivity.this.bSearching)
//                                break;
//                            MainActivity.this.webView.loadUrl("javascript:hide();");
//                            return;
//                            bool = false;
//                        }
//                        MainActivity.this.webView.loadUrl("javascript:show();");
//                        return;
//                    }
//                }
//                while (paramAnonymousInt != 2131296317);
//                paramAnonymousRadioGroup = new Intent(MainActivity.this, AboutActivity.class);
//                MainActivity.this.startActivity(paramAnonymousRadioGroup);
//            }
//        });
//        tabTitle = (TextView)findViewById(2131296319);
//        tabTitle.setText(DBHelper.ActiveDB.name);
//        this.homeButton = ((RadioButton)findViewById(2131296313));
//        this.docButton = ((RadioButton)findViewById(2131296314));
//        this.kakuButton = ((RadioButton)findViewById(2131296315));
//        this.mycardButton = ((RadioButton)findViewById(2131296316));
//        this.homeButton.setChecked(true);
//        this.menu = new SlidingMenu(this);
//        this.menu.setMode(0);
//        this.menu.setTouchModeAbove(2);
//        this.menu.setShadowWidthRes(2131230747);
//        this.menu.setShadowDrawable(2130837632);
//        this.menu.setBehindOffsetRes(2131230746);
//        this.menu.attachToActivity(this, 1);
//        paramBundle = getLayoutInflater().inflate(2130903045, null);
//        ListView localListView = (ListView)paramBundle.findViewById(2131296323);
//        localListView.setAdapter(new ArrayAdapter(this, 2130903059, getResources().getStringArray(2131099648)));
//        localListView.setOnItemClickListener(this);
//        this.menu.setMenu(paramBundle);
//        this.webView = ((WebView)findViewById(2131296269));
//        this.webView.setWebViewClient(new WebViewClient()
//        {
//            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
//            {
//                Object localObject;
//                if (paramAnonymousString.startsWith("file:///android_asset/"))
//                {
//                    localObject = paramAnonymousString.substring("file:///android_asset/".length());
//                    paramAnonymousString = (String)localObject;
//                    if (((String)localObject).length() < 4)
//                        paramAnonymousString = "vport_d_" + (String)localObject;
//                    localObject = pathHelper.geturl(paramAnonymousString.replace('*', '_').replace('@', '_'));
//                    if (!new File(((String)localObject).substring("file://".length())).exists())
//                        return true;
//                    paramAnonymousWebView.loadUrl((String)localObject);
//                }
//                while (true)
//                {
//                    paramAnonymousWebView = new Intent();
//                    paramAnonymousWebView.setClass(MainActivity.this, WebViewActivity.class);
//                    paramAnonymousWebView.putExtra("url", (String)localObject);
//                    MainActivity.this.startActivity(paramAnonymousWebView);
//                    return true;
//                    if (paramAnonymousString.startsWith("file:///android_asset_selectdevice/"))
//                    {
//                        paramAnonymousWebView = new portHelper().loadwithport(paramAnonymousString);
//                        localObject = paramAnonymousString;
//                        if (paramAnonymousWebView.length() > 0)
//                            localObject = paramAnonymousWebView;
//                    }
//                    else
//                    {
//                        localObject = paramAnonymousString;
//                        if (paramAnonymousString.startsWith("file:///android_asset_device/"))
//                        {
//                            localObject = pathHelper.geturl("/vport_d_" + paramAnonymousString.substring("file:///android_asset_device/".length()));
//                            if (!new File(((String)localObject).substring("file://".length())).exists())
//                                break;
//                            paramAnonymousWebView.loadUrl((String)localObject);
//                        }
//                    }
//                }
//            }
//        });
//        paramBundle = this.webView.getSettings();
//        paramBundle.setLoadWithOverviewMode(true);
//        paramBundle.setJavaScriptEnabled(true);
//        paramBundle.setUseWideViewPort(true);
//        this.webView.getSettings().setBuiltInZoomControls(true);
//        this.webView.getSettings().setSupportZoom(true);
//        this.webView.getSettings().setSupportMultipleWindows(true);
//        this.webView.getSettings().setDefaultTextEncodingName("UTF-8");
//        this.webView.setInitialScale(75);
//        this.webView.loadUrl(pathHelper.getindexurl());
//        activeMain = this;
//    }
//
//    public boolean onCreateOptionsMenu(Menu paramMenu)
//    {
//        return true;
//    }
//
//    protected void onDestroy()
//    {
//        super.onDestroy();
//    }
//
//    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
//    {
//        switch (paramInt)
//        {
//            default:
//                return;
//            case 0:
//                this.menu.toggle(true);
//                return;
//            case 1:
//                paramAdapterView = new Intent();
//                paramAdapterView.setClass(this, WebViewActivity.class);
//                paramAdapterView.putExtra("title", "微卡投放");
//                startActivity(paramAdapterView);
//                this.menu.toggle(true);
//                return;
//            case 2:
//                this.menu.toggle(true);
//                return;
//            case 3:
//        }
//        startActivity(new Intent(this, AboutActivity.class));
//        this.menu.toggle(true);
//    }
//
//    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
//    {
//        if (paramInt == 4)
//        {
//            paramKeyEvent = new AlertDialog.Builder(this).create();
//            paramKeyEvent.setTitle("系统提示");
//            paramKeyEvent.setMessage("确定要退出吗");
//            paramKeyEvent.setButton("确定", this.listener);
//            paramKeyEvent.setButton2("取消", this.listener);
//            paramKeyEvent.show();
//        }
//        return false;
//    }
//
//    protected void onPause()
//    {
//        super.onPause();
//    }
//
//    protected void onRestart()
//    {
//        super.onRestart();
//    }
//
//    protected void onResume()
//    {
//        super.onResume();
//    }
//
//    protected void onStop()
//    {
//        super.onStop();
//    }
//
//    public void setHomeButton(RadioButton paramRadioButton)
//    {
//        this.homeButton = paramRadioButton;
//    }
//
//    public void setHuodongButton(RadioButton paramRadioButton)
//    {
//        this.docButton = paramRadioButton;
//    }
//
//    public void setKakuButton(RadioButton paramRadioButton)
//    {
//        this.kakuButton = paramRadioButton;
//    }
//
//    public void setMycardButton(RadioButton paramRadioButton)
//    {
//        this.mycardButton = paramRadioButton;
//    }
//}