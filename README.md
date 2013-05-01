PhonenumGeoProvider
===================
提供号码地理位置的 Android 应用

会在来电时候显示一个 Toast

Why Another
-----------
因为我对权限比较敏感，又没有找到开源的产品就自己作了一个

Usage
-----

    Cursor query = contentResolver.query(Uri.parse("content://com.android.i18n.phonenumbers.geocoding/CN/15110111111"), null, null, null, null);
    query.moveToFirst();
    
    x = query.getString(0);
