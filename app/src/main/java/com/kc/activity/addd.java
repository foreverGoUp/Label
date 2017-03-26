//package com.bcinfo.TripAway;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.net.Uri.Builder;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//import java.io.File;
//import java.io.PrintStream;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class FileListActivity extends Activity
//{
//    public static String RootFilePath = "/storage/sdcard0/sgclient";
//    private Button btnParent;
//    File[] currentFiles;
//    File currentParent;
//    private ListView lvFiles;
//    private TextView tvpath;
//
//    public static void doSearch(String paramString1, String paramString2, ArrayList<File> paramArrayList)
//    {
//        paramString2 = new File(paramString2);
//        if (paramString2.exists())
//        {
//            paramString2 = paramString2.listFiles();
//            int i = 0;
//            if (i < paramString2.length)
//            {
//                Object localObject = paramString2[i];
//                String str;
//                if (localObject.isDirectory())
//                {
//                    str = localObject.getName();
//                    if (!str.startsWith("."));
//                }
//                while (true)
//                {
//                    i += 1;
//                    break;
//                    if (str.compareToIgnoreCase("datas") != 0)
//                    {
//                        doSearch(paramString1, localObject.getPath(), paramArrayList);
//                        continue;
//                        if (localObject.getName().compareToIgnoreCase(paramString1) == 0)
//                            paramArrayList.add(localObject);
//                    }
//                }
//            }
//        }
//    }
//
//    public static Intent getAllIntent(String paramString)
//    {
//        Intent localIntent = new Intent();
//        localIntent.addFlags(268435456);
//        localIntent.setAction("android.intent.action.VIEW");
//        localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "*/*");
//        return localIntent;
//    }
//
//    public static Intent getApkFileIntent(String paramString)
//    {
//        Intent localIntent = new Intent();
//        localIntent.addFlags(268435456);
//        localIntent.setAction("android.intent.action.VIEW");
//        localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "application/vnd.android.package-archive");
//        return localIntent;
//    }
//
//    public static Intent getAudioFileIntent(String paramString)
//    {
//        Intent localIntent = new Intent("android.intent.action.VIEW");
//        localIntent.addFlags(67108864);
//        localIntent.putExtra("oneshot", 0);
//        localIntent.putExtra("configchange", 0);
//        localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "audio/*");
//        return localIntent;
//    }
//
//    public static Intent getChmFileIntent(String paramString)
//    {
//        Intent localIntent = new Intent("android.intent.action.VIEW");
//        localIntent.addCategory("android.intent.category.DEFAULT");
//        localIntent.addFlags(268435456);
//        localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "application/x-chm");
//        return localIntent;
//    }
//
//    public static Intent getExcelFileIntent(String paramString)
//    {
//        Intent localIntent = new Intent("android.intent.action.VIEW");
//        localIntent.addCategory("android.intent.category.DEFAULT");
//        localIntent.addFlags(268435456);
//        localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "application/vnd.ms-excel");
//        return localIntent;
//    }
//
//    public static Intent getHtmlFileIntent(String paramString)
//    {
//        paramString = Uri.parse(paramString).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(paramString).build();
//        Intent localIntent = new Intent("android.intent.action.VIEW");
//        localIntent.setDataAndType(paramString, "text/html");
//        return localIntent;
//    }
//
//    public static Intent getImageFileIntent(String paramString)
//    {
//        Intent localIntent = new Intent("android.intent.action.VIEW");
//        localIntent.addCategory("android.intent.category.DEFAULT");
//        localIntent.addFlags(268435456);
//        localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "image/*");
//        return localIntent;
//    }
//
//    public static Intent getPdfFileIntent(String paramString)
//    {
//        Intent localIntent = new Intent("android.intent.action.VIEW");
//        localIntent.addCategory("android.intent.category.DEFAULT");
//        localIntent.addFlags(268435456);
//        localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "application/pdf");
//        return localIntent;
//    }
//
//    public static Intent getPptFileIntent(String paramString)
//    {
//        Intent localIntent = new Intent("android.intent.action.VIEW");
//        localIntent.addCategory("android.intent.category.DEFAULT");
//        localIntent.addFlags(268435456);
//        localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "application/vnd.ms-powerpoint");
//        return localIntent;
//    }
//
//    public static Intent getTextFileIntent(String paramString, boolean paramBoolean)
//    {
//        Intent localIntent = new Intent("android.intent.action.VIEW");
//        localIntent.addCategory("android.intent.category.DEFAULT");
//        localIntent.addFlags(268435456);
//        if (paramBoolean)
//        {
//            localIntent.setDataAndType(Uri.parse(paramString), "text/plain");
//            return localIntent;
//        }
//        localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "text/plain");
//        return localIntent;
//    }
//
//    public static Intent getVideoFileIntent(String paramString)
//    {
//        Intent localIntent = new Intent("android.intent.action.VIEW");
//        localIntent.addFlags(67108864);
//        localIntent.putExtra("oneshot", 0);
//        localIntent.putExtra("configchange", 0);
//        localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "video/*");
//        return localIntent;
//    }
//
//    public static Intent getWordFileIntent(String paramString)
//    {
//        Intent localIntent = new Intent("android.intent.action.VIEW");
//        localIntent.addCategory("android.intent.category.DEFAULT");
//        localIntent.addFlags(268435456);
//        localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "application/msword");
//        return localIntent;
//    }
//
//    private void inflateListView(File[] paramArrayOfFile)
//    {
//        ArrayList localArrayList = new ArrayList();
//        int i = 0;
//        if (i < paramArrayOfFile.length)
//        {
//            HashMap localHashMap = new HashMap();
//            if (paramArrayOfFile[i].isDirectory())
//                localHashMap.put("icon", Integer.valueOf(2130837561));
//            while (true)
//            {
//                localHashMap.put("filename", paramArrayOfFile[i].getName());
//                long l = paramArrayOfFile[i].lastModified();
//                SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                System.out.println(localSimpleDateFormat.format(new Date(l)));
//                localHashMap.put("modify", localSimpleDateFormat.format(new Date(l)));
//                localArrayList.add(localHashMap);
//                i += 1;
//                break;
//                localHashMap.put("icon", Integer.valueOf(2130837560));
//            }
//        }
//        paramArrayOfFile = new SimpleAdapter(this, localArrayList, 2130903069, new String[] { "filename", "icon", "modify" }, new int[] { 2131296387, 2131296386, 2131296388 });
//        this.lvFiles.setAdapter(paramArrayOfFile);
//        try
//        {
//            this.tvpath.setText("" + this.currentParent.getCanonicalPath());
//            return;
//        }
//        catch (Exception paramArrayOfFile)
//        {
//            paramArrayOfFile.printStackTrace();
//        }
//    }
//
//    public static Intent openFile(String paramString)
//    {
//        Object localObject = new File(paramString);
//        if (!((File)localObject).exists())
//            return null;
//        localObject = ((File)localObject).getName().substring(((File)localObject).getName().lastIndexOf(".") + 1, ((File)localObject).getName().length()).toLowerCase();
//        if ((((String)localObject).equals("m4a")) || (((String)localObject).equals("mp3")) || (((String)localObject).equals("mid")) || (((String)localObject).equals("xmf")) || (((String)localObject).equals("ogg")) || (((String)localObject).equals("wav")))
//            return getAudioFileIntent(paramString);
//        if ((((String)localObject).equals("3gp")) || (((String)localObject).equals("mp4")))
//            return getAudioFileIntent(paramString);
//        if ((((String)localObject).equals("jpg")) || (((String)localObject).equals("gif")) || (((String)localObject).equals("png")) || (((String)localObject).equals("jpeg")) || (((String)localObject).equals("bmp")))
//            return getImageFileIntent(paramString);
//        if (((String)localObject).equals("apk"))
//            return getApkFileIntent(paramString);
//        if (((String)localObject).equals("ppt"))
//            return getPptFileIntent(paramString);
//        if (((String)localObject).equals("xls"))
//            return getExcelFileIntent(paramString);
//        if (((String)localObject).equals("doc"))
//            return getWordFileIntent(paramString);
//        if (((String)localObject).equals("pdf"))
//            return getPdfFileIntent(paramString);
//        if (((String)localObject).equals("chm"))
//            return getChmFileIntent(paramString);
//        if (((String)localObject).equals("txt"))
//            return getTextFileIntent(paramString, false);
//        if ((((String)localObject).equals("m4a")) || (((String)localObject).equals("mp3")) || (((String)localObject).equals("mid")) || (((String)localObject).equals("xmf")) || (((String)localObject).equals("ogg")) || (((String)localObject).equals("wav")))
//            return getAudioFileIntent(paramString);
//        if ((((String)localObject).equals("3gp")) || (((String)localObject).equals("mp4")))
//            return getAudioFileIntent(paramString);
//        if ((((String)localObject).equals("jpg")) || (((String)localObject).equals("gif")) || (((String)localObject).equals("png")) || (((String)localObject).equals("jpeg")) || (((String)localObject).equals("bmp")))
//            return getImageFileIntent(paramString);
//        if (((String)localObject).equals("apk"))
//            return getApkFileIntent(paramString);
//        if (((String)localObject).equals("ppt"))
//            return getPptFileIntent(paramString);
//        if (((String)localObject).equals("chm"))
//            return getChmFileIntent(paramString);
//        if (((String)localObject).equals("txt"))
//            return getTextFileIntent(paramString, false);
//        return getAllIntent(paramString);
//    }
//
//    public void onCreate(Bundle paramBundle)
//    {
//        super.onCreate(paramBundle);
//        setContentView(2130903067);
//        this.lvFiles = ((ListView)findViewById(2131296380));
//        this.tvpath = ((TextView)findViewById(2131296379));
//        this.btnParent = ((Button)findViewById(2131296378));
//        paramBundle = getIntent().getStringExtra("filename");
//        ArrayList localArrayList;
//        if ((paramBundle != null) && (paramBundle.length() > 0))
//        {
//            localArrayList = new ArrayList();
//            doSearch(paramBundle, RootFilePath, localArrayList);
//            this.currentFiles = new File[localArrayList.size()];
//            localArrayList.toArray(this.currentFiles);
//            inflateListView(this.currentFiles);
//            this.currentParent = new File("/storage/sdcard0/sgclient/temp");
//        }
//        while (true)
//        {
//            this.lvFiles.setOnItemClickListener(new AdapterView.OnItemClickListener()
//            {
//                public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
//                {
//                    if (FileListActivity.this.currentFiles[paramAnonymousInt].isFile())
//                    {
//                        paramAnonymousAdapterView = FileListActivity.openFile(FileListActivity.this.currentFiles[paramAnonymousInt].getPath());
//                        FileListActivity.this.startActivity(paramAnonymousAdapterView);
//                        return;
//                    }
//                    paramAnonymousAdapterView = FileListActivity.this.currentFiles[paramAnonymousInt].listFiles();
//                    if ((paramAnonymousAdapterView == null) || (paramAnonymousAdapterView.length == 0))
//                    {
//                        Toast.makeText(FileListActivity.this, "当前路径不可访问或者该路径下没有文件", 1).show();
//                        return;
//                    }
//                    paramAnonymousView = new ArrayList();
//                    int i = 0;
//                    if (i < paramAnonymousAdapterView.length)
//                    {
//                        Object localObject = paramAnonymousAdapterView[i];
//                        if (localObject.isDirectory())
//                            paramAnonymousView.add(localObject);
//                        while (true)
//                        {
//                            i += 1;
//                            break;
//                            if ((localObject.getPath().toLowerCase().endsWith(".txt")) || (localObject.getPath().toLowerCase().endsWith(".pdf")) || (localObject.getPath().toLowerCase().endsWith(".doc")) || (localObject.getPath().toLowerCase().endsWith(".xls")))
//                                paramAnonymousView.add(localObject);
////                        }
//                    }
//                    FileListActivity.this.currentParent = FileListActivity.this.currentFiles[paramAnonymousInt];
//                    FileListActivity.this.currentFiles = new File[paramAnonymousView.size()];
//                    paramAnonymousView.toArray(FileListActivity.this.currentFiles);
//                    FileListActivity.this.inflateListView(FileListActivity.this.currentFiles);
//                }
//            });
//            this.btnParent.setOnClickListener(new View.OnClickListener()
//            {
//                public void onClick(View paramAnonymousView)
//                {
//                    try
//                    {
//                        if (!FileListActivity.this.currentParent.getCanonicalPath().equals("/mnt/sdcard"))
//                        {
//                            FileListActivity.this.currentParent = FileListActivity.this.currentParent.getParentFile();
//                            FileListActivity.this.currentFiles = FileListActivity.this.currentParent.listFiles();
//                            FileListActivity.this.inflateListView(FileListActivity.this.currentFiles);
//                        }
//                        return;
//                    }
//                    catch (Exception paramAnonymousView)
//                    {
//                    }
//                }
//            });
//            return;
//            paramBundle = new File("/storage/sdcard0/sgclient");
//            if (paramBundle.exists())
//            {
//                this.currentParent = paramBundle;
//                paramBundle = paramBundle.listFiles();
//                localArrayList = new ArrayList();
//                int i = 0;
//                if (i < paramBundle.length)
//                {
//                    Object localObject = paramBundle[i];
//                    if (localObject.isDirectory())
//                        if (!localObject.getName().startsWith("."));
//                    while (true)
//                    {
//                        i += 1;
//                        break;
//                        if (localObject.getName().compareToIgnoreCase("datas") != 0)
//                        {
//                            localArrayList.add(localObject);
//                            continue;
//                            if ((localObject.getPath().toLowerCase().endsWith(".txt")) || (localObject.getPath().toLowerCase().endsWith(".pdf")) || (localObject.getPath().toLowerCase().endsWith(".doc")) || (localObject.getPath().toLowerCase().endsWith(".xls")))
//                                localArrayList.add(localObject);
//                        }
//                    }
//                }
//                this.currentFiles = new File[localArrayList.size()];
//                localArrayList.toArray(this.currentFiles);
//                inflateListView(this.currentFiles);
//            }
//        }
//    }
//}