PhonenumGeoProvider
===================
提供号码地理位置的 Provider 

Usage
-----

    Cursor query = contentResolver.query(Uri.parse("content://com.android.i18n.phonenumbers.geocoding/CN/15110111111"), null, null, null, null);
    query.moveToFirst();
    
    x = query.getString(0);
